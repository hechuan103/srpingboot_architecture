package com.hc.architecture.biz.permission.service.impl;

import com.hc.architecture.biz.permission.bo.UserPermissionBo;
import com.hc.architecture.biz.permission.entity.User;
import com.hc.architecture.biz.permission.entity.UserRole;
import com.hc.architecture.biz.permission.mapper.RolePermissionMapper;
import com.hc.architecture.biz.permission.mapper.RoleToUserMapper;
import com.hc.architecture.biz.permission.mapper.UserMapper;
import com.hc.architecture.biz.permission.service.UserInfoService;
import com.hc.architecture.biz.permission.shiro.AccountProfile;
import com.hc.architecture.biz.permission.vo.UserVo;
import com.hc.architecture.config.common.ApiResult;
import com.hc.architecture.config.common.CodeMessageEnum;
import com.hc.architecture.config.common.Constants;
import com.hc.architecture.config.common.exception.BizException;
import com.hc.architecture.config.common.page.PageList;
import com.hc.architecture.config.common.util.EncryptionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: hechuan
 * @Date: 2020/5/26 16:48
 * @Description: 用户service
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleToUserMapper roleToUserMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;


    @Override
    public User userLogin(String userName) {
        AccountProfile profile = new AccountProfile();
        User user = userMapper.loginSearchUser(userName);

        return user;
    }

    @Override
    public Integer getUserIdByUserName(String userName) {
        return userMapper.selectIdByUserName(userName);
    }

    @Override
    public List<UserPermissionBo> getRolePermission(Integer userId) {
        List<UserPermissionBo> rolePermissions = Collections.emptyList();
        //获取用户对应的角色id
        List<Integer> roleIds = roleToUserMapper.selectRoleIdByUserId(userId);
        if (roleIds.size() > 0) {
            //通过角色id获取角色对应的权限
            rolePermissions = rolePermissionMapper.selectRolePermissionsByRoleId(roleIds);
        } else {
            throw new BizException(CodeMessageEnum.USER_ROLE_IS_NULL.getI18n(), "", "");
        }
        // 获取用户组的权限

        return rolePermissions;
    }

    @Override
    public PageList<UserVo> getUserList(UserVo userSearch) {
        Map<String, Object> paramMap = getParamMap(userSearch);
        PageList<UserVo> pageList = null;
        //查询数据总数
        int total = userMapper.selectCountUser(paramMap);
        pageList = new PageList<>(total, userSearch.getPageNo(), userSearch.getPageSize());
        if (total > 0) {
            paramMap.put("start", pageList.getStartIndex());
            paramMap.put("pageSize", pageList.getPageSize());
            //查询数据
            List<UserVo> users = userMapper.selectUserPage(paramMap);
            pageList.setRows(users);
        }
        paramMap.clear();
        return pageList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addUser(UserVo userAdd) {

        User user = new User();
        BeanUtils.copyProperties(userAdd, user);
        //将用户密码加密
        String salt = EncryptionUtil.saltCreator();
        String password = EncryptionUtil.encryption(userAdd.getPassword(), salt);
        user.setPassword(password);
        //获取时间戳
        Long createTm = System.currentTimeMillis();
        user.setCreateTm(createTm.toString());
        user.setStatus(Constants.ENABLE);
        user.setUpdateTm(new Date());
        user.setSalt(salt);

        int value = userMapper.insert(user);
        if (userAdd.getRoleIds()!= null && userAdd.getRoleIds().size() > 0) {
            List<UserRole> userRoles = new ArrayList<>(userAdd.getRoleIds().size());
            createUserRole(userAdd.getRoleIds(),user.getId(),userRoles);
            roleToUserMapper.batchInsert(userRoles);
        }

        return value;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateUser(UserVo userUpdate) {
        userUpdate.setUpdateTm(new Date());
        User user = new User();
        BeanUtils.copyProperties(userUpdate,user);
        // 删除用户对应的关系
        roleToUserMapper.deleteUserByUserId(userUpdate.getId());
        // 重新插入对应关系
        List<Integer> roleIds = userUpdate.getRoleIds();
        if (roleIds.size() > 0) {
            List<UserRole> userRoles = new ArrayList<>(roleIds.size());
            createUserRole(roleIds,user.getId(),userRoles);
            roleToUserMapper.batchInsert(userRoles);
        }

        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public int updateStatus(Integer userId, Integer status) {
        Map<String, Object> paramMap = new HashMap<>(2);
        paramMap.put("id", userId);
        paramMap.put("status", status);
        return userMapper.updateUserStatus(paramMap);
    }

    @Override
    public int restPassword(Integer userId) {
        String salt = EncryptionUtil.saltCreator();
        String password = EncryptionUtil.encryption(Constants.DEFAULT_PASSWORD, salt);
        Map<String, Object> paramMap = new HashMap<>(3);
        paramMap.put("id", userId);
        paramMap.put("salt", salt);
        paramMap.put("password", password);

        return userMapper.restPassword(paramMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResult<String> updatePassword(String oldPassword, String newPassword, Integer currentUserId) {
        //查询用户
        User user = userMapper.selectUserById(currentUserId);
        Optional<User> optional = Optional.ofNullable(user);
        if (optional.isPresent()) {
            if (EncryptionUtil.decryption(oldPassword, user.getPassword(), user.getSalt())) {
                //更新密码
                String password = EncryptionUtil.encryption(newPassword, user.getSalt());
                Map<String, Object> paramMap = new HashMap<>(2);

                paramMap.put("id", currentUserId);
                paramMap.put("password", password);
                paramMap.put("salt", user.getSalt());
                userMapper.restPassword(paramMap);
                paramMap.clear();
                return ApiResult.success();
            } else {
                return ApiResult.error(CodeMessageEnum.UPDATE_USER_OLD_PASSWORD_ERROR.getCode(), CodeMessageEnum.UPDATE_USER_OLD_PASSWORD_ERROR.getI18n());
            }
        } else {
            return ApiResult.error(CodeMessageEnum.UPDATE_USER_PASSWORD_ERROR.getCode(), CodeMessageEnum.UPDATE_USER_PASSWORD_ERROR.getI18n());
        }
    }

    @Override
    public List<Integer> getUserRoleId(Integer userId) {
        return roleToUserMapper.selectRoleIdByUserId(userId);
    }



    private Map<String, Object> getParamMap(UserVo request) {
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("name", request.getName());
        paramMap.put("email", request.getEmail());
        paramMap.put("status", request.getStatus());

        return paramMap;
    }


    private void createUserRole(List<Integer> roleIds, Integer userId, List<UserRole> userRoles) {
        for (Integer roleId : roleIds) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            userRole.setCreateTm(String.valueOf(System.currentTimeMillis()));
            userRole.setUpdateTm(new Date());
            userRoles.add(userRole);
        }
    }
}

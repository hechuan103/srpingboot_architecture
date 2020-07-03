package com.hc.architecture.biz.permission.service.impl;

import com.hc.architecture.biz.permission.bo.RoleToUserBo;
import com.hc.architecture.biz.permission.entity.Menu;
import com.hc.architecture.biz.permission.entity.Role;
import com.hc.architecture.biz.permission.entity.RolePermission;
import com.hc.architecture.biz.permission.mapper.MenuMapper;
import com.hc.architecture.biz.permission.mapper.RoleMapper;
import com.hc.architecture.biz.permission.mapper.RolePermissionMapper;
import com.hc.architecture.biz.permission.mapper.RoleToUserMapper;
import com.hc.architecture.biz.permission.service.RoleService;
import com.hc.architecture.biz.permission.vo.AuthorRoleMenuVo;
import com.hc.architecture.biz.permission.vo.RoleVo;
import com.hc.architecture.config.common.ApiResult;
import com.hc.architecture.config.common.CodeMessageEnum;
import com.hc.architecture.config.common.Constants;
import com.hc.architecture.config.common.exception.BizException;
import com.hc.architecture.config.common.page.PageList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: hechuan
 * @Date: 2020/5/29 14:37
 */
@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleToUserMapper roleToUserMapper;
    @Autowired
    private RolePermissionMapper permissionMapper;
    @Autowired
    private MenuMapper menuMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResult<String> addRole(RoleVo roleVo) {
        Role dataRole = roleMapper.selectRoleByName(roleVo.getName());
        if (dataRole != null) {
            return ApiResult.error(CodeMessageEnum.ROLE_ADD_ERROR_DUPLICATE.getI18n());
        }
        // bean 转换
        Role role = new Role();
        BeanUtils.copyProperties(roleVo,role);

        role.setCreateTm(String.valueOf(System.currentTimeMillis()));
        role.setStatus(Constants.ENABLE);
        role.setUpdateTm(new Date());
        int insert = roleMapper.insert(role);
        if (insert > 0) {
            return ApiResult.success();
        }
        return ApiResult.error(CodeMessageEnum.ROLE_ADD_ERROR_INSERT.getI18n());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int enableRole(RoleVo roleVo) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("roleId", roleVo.getId());
        paramMap.put("status", roleVo.getStatus());
        // 禁用角色 角色相应权限也被禁用
        permissionMapper.updateRolePerStatus(paramMap);

        int value = roleMapper.updateRoleStatus(paramMap);
        paramMap.clear();
        return value;
    }

    @Override
    public ApiResult<String> updateRole(RoleVo roleVo) {
        Role dataRole = roleMapper.selectRoleByName(roleVo.getName());
        // 不是同一条记录
        if (!roleVo.getId().equals(dataRole.getId())) {
            return ApiResult.error(CodeMessageEnum.ROLE_ADD_ERROR_DUPLICATE.getI18n());
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleVo,role);

        int updateCount = roleMapper.updateByPrimaryKey(role);
        if (updateCount > 0) {
            return ApiResult.success();
        }
        return ApiResult.error(CodeMessageEnum.ROLE_UPDATE_ERROR_UPDATE.getI18n());
    }

    @Override
    public PageList<Role> getRoleList(RoleVo roleVo) {
        Map<String, Object> paramMap = new HashMap<>();
        PageList<Role> pageList = null;
        paramMap.put("name", roleVo.getName());
        paramMap.put("code", roleVo.getCode());
        paramMap.put("status", roleVo.getStatus());
        //查询角色总数
        int total = roleMapper.selectRoleCount(paramMap);
        pageList = new PageList<Role>(total, roleVo.getPageNo(), roleVo.getPageSize());
        if (total > 0) {
            paramMap.put("start", pageList.getStartIndex());
            paramMap.put("pageSize", pageList.getPageSize());
            List<Role> roles = roleMapper.selectRoleByPage(paramMap);
            pageList.setRows(roles);
        }
        paramMap.clear();

        return pageList;
    }

    @Override
    public PageList<RoleToUserBo> getRoleUser(RoleVo roleVo) {
        Map<String, Object> paramMap = new HashMap<>();
        PageList<RoleToUserBo> pageList = null;
        paramMap.put("roleId", roleVo.getId());
        paramMap.put("name", roleVo.getName());
        //查询角色对应的用户数量
        int total = roleToUserMapper.selectRoleUserCount(paramMap);
        pageList = new PageList<>(total, roleVo.getPageNo(), roleVo.getPageSize());
        if (total > 0) {
            paramMap.put("start", pageList.getStartIndex());
            paramMap.put("pageSize", pageList.getPageSize());
            List<RoleToUserBo> roleToUserBos = roleToUserMapper.selectRoleUserPage(paramMap);
            pageList.setRows(roleToUserBos);
        }
        paramMap.clear();

        return pageList;
    }

    @Override
    public int deleteRoleUser(RoleVo roleVo) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",roleVo.getUserId());
        paramMap.put("roleId",roleVo.getId());
        int value = roleToUserMapper.deleteUserById(paramMap);
        paramMap.clear();

        return value;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void authorRole(AuthorRoleMenuVo menuBo) {
        // 查询menuId的父id: 因为传上来的只有被选中的菜单id，这里需要查询一下菜单把他们父级菜单做默认
        // 勾选
        List<Menu> menus = menuMapper.selectAllMenuId();

        Set<Integer> roleMenuIds = getRoleMenuId(new HashSet<>(menuBo.getMenuIds()), menus);

        List<RolePermission> rolePermissions = new ArrayList<>();
        try {
            Integer roleId = menuBo.getRoleId();
            //构建角色权限
            for (Integer menuId : roleMenuIds) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setMenuId(menuId);
                rolePermission.setRoleCode(menuBo.getRoleCode());
                rolePermission.setRoleId(roleId);
                rolePermission.setStatus(Constants.ENABLE);
                rolePermission.setCreateTm(String.valueOf(System.currentTimeMillis()));
                rolePermission.setUpdateTm(new Date());

                rolePermissions.add(rolePermission);
            }
            //删除旧角色对应的功能
            permissionMapper.deletePermissionByRoleId(roleId);
            permissionMapper.batchInsert(rolePermissions);
        } catch (Exception e) {
            logger.error("授权异常", e);
            throw new BizException("todo", "授权异常", "授权模块");
        }
    }

    @Override
    public List<Role> getAllRole() {
        return roleMapper.selectAllRole();
    }

    private Set<Integer> getRoleMenuId(Set<Integer> roleMenuId, List<Menu> menus) {

        Set<Integer> newMenuIds = new HashSet<>();
        Iterator<Integer> iterator = roleMenuId.iterator();
        while (iterator.hasNext()) {
            Integer selectId = iterator.next();
            for (Menu menu : menus) {
                if (selectId.equals(menu.getId()) && menu.getParentId() != null) {
                    newMenuIds.add(menu.getParentId());
                    break;
                }
            }
        }
        if (newMenuIds.size() > 0) {
            Set<Integer> parentId = getRoleMenuId(newMenuIds,menus);
            roleMenuId.addAll(parentId);
        }

        return roleMenuId;
    }
}


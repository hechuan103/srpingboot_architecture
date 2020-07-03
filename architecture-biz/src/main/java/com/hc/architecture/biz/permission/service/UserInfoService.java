package com.hc.architecture.biz.permission.service;

import com.hc.architecture.biz.permission.bo.UserPermissionBo;
import com.hc.architecture.biz.permission.entity.User;
import com.hc.architecture.biz.permission.vo.UserVo;
import com.hc.architecture.config.common.ApiResult;
import com.hc.architecture.config.common.page.PageList;

import java.util.List;

/**
 * @Author: hechuan
 * @Date: 2020/5/27 16:07
 * @Description: 用户信息
 */
public interface UserInfoService {

    /**
     * 登录校验
     *
     * @param userName 用户名
     * @return
     */
    User userLogin(String userName);

    /**
     * 获取用户的ID
     * @param userName 用户名
     * @return
     */
    Integer getUserIdByUserName(String userName);


    /**
     * 用户权限
     *
     * @param userId 用户id
     * @return
     */
    List<UserPermissionBo> getRolePermission(Integer userId);

    /**
     * 用户列表
     *
     * @param userVo
     * @return
     */
    PageList getUserList(UserVo userVo);


    /**
     * 新增用户
     *
     * @param userAdd
     */
    int addUser(UserVo userAdd);

    /**
     * 更新用户
     *
     * @param userUpdate
     * @return
     */
    int updateUser(UserVo userUpdate);

    /**
     * 启用/禁用 用户
     *
     * @param userId 用户id
     * @param status 状态
     * @return
     */
    int updateStatus(Integer userId, Integer status);

    /**
     * 重置用户密码
     *
     * @param userId 用户id
     * @return
     */
    int restPassword(Integer userId);

    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param currentUserId 当前用户id
     * @return
     */
    ApiResult<String> updatePassword(String oldPassword, String newPassword,Integer currentUserId);

    /**
     * 查询用户对应的角色
     *
     */
    List<Integer> getUserRoleId(Integer userId);
}

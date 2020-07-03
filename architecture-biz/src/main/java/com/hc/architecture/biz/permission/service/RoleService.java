package com.hc.architecture.biz.permission.service;

import com.hc.architecture.biz.permission.bo.RoleToUserBo;
import com.hc.architecture.biz.permission.vo.AuthorRoleMenuVo;
import com.hc.architecture.biz.permission.entity.Role;
import com.hc.architecture.biz.permission.vo.RoleVo;
import com.hc.architecture.config.common.ApiResult;
import com.hc.architecture.config.common.page.PageList;

import java.util.List;

/**
 * @Author: hechuan
 * @Date: 2020/5/29 14:21
 * @Description: 角色业务
 */
public interface RoleService {
    /**
     * 新增角色
     * @param roleVo 角色
     * @return int
     */
    ApiResult<String> addRole(RoleVo roleVo);

    /**
     * 启用/禁用角色
     * @return
     */
    int enableRole(RoleVo roleVo);


    /**
     * 修改角色
     * @param roleVo 角色
     * @return
     */
    ApiResult<String> updateRole(RoleVo roleVo);

    /**
     * 分页查询角色信息
     * @param roleVo
     * @return
     */
    PageList<Role> getRoleList(RoleVo roleVo);

    /**
     * 查询角色的所有用户
     * @param roleVo
     * @return
     */
    PageList<RoleToUserBo> getRoleUser(RoleVo roleVo);

    /**
     * 删除某个角色下的用户
     * @param roleVo 用户id ,角色id
     * @return
     */
    int deleteRoleUser(RoleVo roleVo);

    /**
     * 授权角色权限
     * @param menuBo 菜单授权对象
     */
    void authorRole(AuthorRoleMenuVo menuBo);

    /**
     * 获取所有可用角色
     * @return
     */
    List<Role> getAllRole();
}

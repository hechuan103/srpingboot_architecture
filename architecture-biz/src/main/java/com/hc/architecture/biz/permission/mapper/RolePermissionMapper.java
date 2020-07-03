package com.hc.architecture.biz.permission.mapper;


import com.hc.architecture.biz.permission.bo.UserPermissionBo;
import com.hc.architecture.biz.permission.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface RolePermissionMapper {


    int insert(RolePermission record);

    List<UserPermissionBo> selectRolePermissionsByRoleId(List<Integer> roleIds);

    List<Integer> selectSignalRolePermission(Integer roleId);

    int updateRolePerStatus(Map<String,Object> paramMap);

    /**
     * 根据菜单id 更新角色对应菜单表
     * @param paramMap 参数封装
     * @return
     */
    int updateRoleMenuStatus(Map<String,Object> paramMap);

    /**
     * 删除角色权限
     * @param roleId
     */
    int deletePermissionByRoleId(Integer roleId);

    /**
     * 批量插入角色权限
     * @param rolePermissions 角色权限
     */
    void batchInsert(List<RolePermission> rolePermissions);
}
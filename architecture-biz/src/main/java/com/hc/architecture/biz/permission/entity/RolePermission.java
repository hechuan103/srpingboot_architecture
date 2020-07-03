package com.hc.architecture.biz.permission.entity;

import lombok.Data;

/**
 * @Author: hechuan
 * @Date: 2020/5/26 17:32
 * @Description: 角色对应权限
 */
@Data
public class RolePermission extends BaseEntity{


    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 功能id
     */
    private Integer menuId;


    /**
     * 角色code
     */
    private String roleCode;



}

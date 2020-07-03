package com.hc.architecture.biz.permission.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: hechuan
 * @Date: 2020/5/26 16:52
 * @Description: 用户权限 配合shiro使用
 */
@Data
public class UserPermissionBo implements Serializable {

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

    /**
     * 菜单国际化编码
     */
    private String i18nCode;

    /**
     * 功能类型
     */
    private int type;


    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父级菜单
     */
    private Integer parentId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 菜单code
     */
    private String menuCode;

    /**
     * icon 图标
     */
    private String icon;

    /**
     * 路径
     */
    private String path;




}

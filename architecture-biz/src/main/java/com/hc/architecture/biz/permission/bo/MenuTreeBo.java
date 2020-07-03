package com.hc.architecture.biz.permission.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: hechuan
 * @Date: 2020/5/29 17:32
 * @Description: 菜单树
 */
@Data
public class MenuTreeBo implements Serializable {

    /**
     * 菜单id
     */
    private Integer menuId;

    /**
     * 选择框是否勾选
     */
    private boolean select;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单id
     */
    private Integer parentId;

    /**
     * 国际化编码
     */
    private String i18nCode;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 图标
     */
    private String icon;

    /**
     * 子菜单
     */
    List<MenuTreeBo> children;
}

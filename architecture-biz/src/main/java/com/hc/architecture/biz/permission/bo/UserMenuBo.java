package com.hc.architecture.biz.permission.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: hechuan
 * @Date: 2020/5/28 09:30
 * @Description: 用户左侧菜单
 */
@Data
public class UserMenuBo implements Serializable {

    private Integer id;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 国际化编码
      */
    private String i18nCode;

    /**
     * 子菜单
     */
    private List<UserMenuBo> children;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 图标
     */
    private String icon;

    /**
     * 路径
     */
    private String path;

    /**
     * 菜单名称
     */
    private String name;
}

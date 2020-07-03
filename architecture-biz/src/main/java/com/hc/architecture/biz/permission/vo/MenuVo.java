package com.hc.architecture.biz.permission.vo;

import lombok.Data;

/**
 * @Author: hechuan
 * @Date: 2020/6/22 11:18
 * @Description: 菜单展示
 */
@Data
public class MenuVo extends PageSearchVo {

    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 地址
     */
    private String url;

    /**
     * 父级id
     */
    private Integer parentId;

    /**
     * 1-一级菜单2-二级菜单3-三级菜单4-按钮
     */
    private Integer type;

    /**
     * 功能code
     */
    private String code;

    /**
     * 菜单国际化码
     */
    private String i18nCode;

    /**
     *
     * icon图标
     */
    private String icon;


    /**
     * 排序字段
     */
    private Integer sort;
}

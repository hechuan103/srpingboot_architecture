package com.hc.architecture.biz.permission.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: hechuan
 * @Date: 2020/5/26 17:19
 * @Description: 功能表
 */
@ApiModel
@Data
public class Menu extends BaseEntity{


    /**
     * 名称
     */
    @ApiModelProperty(required = true, notes = "菜单名称", example = "0")
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

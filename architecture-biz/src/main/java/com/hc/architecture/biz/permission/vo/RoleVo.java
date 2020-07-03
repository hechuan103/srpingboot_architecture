package com.hc.architecture.biz.permission.vo;

import lombok.Data;

/**
 * @Author: hechuan
 * @Date: 2020/6/22 13:48
 * @Description: 角色展示
 */
@Data
public class RoleVo extends PageSearchVo{

    private Integer id;
    /**
     * 用户名
     */
    private String name;

    /**
     * 角色code
     */
    private String code;

    /**
     * 用户id
     */
    private Integer userId;
}

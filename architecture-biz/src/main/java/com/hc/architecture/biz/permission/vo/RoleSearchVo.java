package com.hc.architecture.biz.permission.vo;

import lombok.Data;

/**
 * @Author: hechuan
 * @Date: 2020/5/29 14:28
 * @Description: 角色查询条件
 */
@Data
public class RoleSearchVo extends PageSearchVo {

    private Integer roleId;

    private String name;

    private String code;

    private Integer status;

    private Integer userId;

}

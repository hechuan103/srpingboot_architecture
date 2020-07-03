package com.hc.architecture.biz.permission.entity;

import lombok.Data;

/**
 * @Author: hechuan
 * @Date: 2020/5/26 17:29
 * @Description: 用户对应角色
 */
@Data
public class UserRole extends BaseEntity{


    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 角色id
     */
    private Integer roleId;


}

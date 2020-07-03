package com.hc.architecture.biz.permission.entity;

import lombok.Data;

/**
 * @Author: hechuan
 * @Date: 2020/5/26 16:58
 * @Description: 用户实体
 */
@Data
public class User extends BaseEntity{


    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     *
     */
    private String mobilePhone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 密码盐
     *
     */
    private String salt;


}

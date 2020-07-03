package com.hc.architecture.biz.permission.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: hechuan
 * @Date: 2020/6/18 19:00
 * @Description: 用户查询修改vo
 */
@Data
public class UserVo extends PageSearchVo {

    /**
     * id
     */
    private Integer id;

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
     * 角色id
     */
    private List<Integer> roleIds;

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     *
     * 新建时间 时间戳（考虑国际化）
     */
    private String createTm;

    /**
     * 更新时间
     */
    private Date updateTm;



}

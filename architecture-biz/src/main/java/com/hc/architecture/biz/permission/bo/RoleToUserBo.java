package com.hc.architecture.biz.permission.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: hechuan
 * @Date: 2020/5/29 15:09
 * @Description: 角色对应的用户
 */
@Data
public class RoleToUserBo implements Serializable {


    private String userName;

    private String email;

    private String userId;

    private Integer status;



}

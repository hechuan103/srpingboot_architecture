package com.hc.architecture.biz.permission.shiro;


import java.io.Serializable;

/**
 * @Author: hechuan
 * @Date: 2020/6/20 15:03
 * @Description: shiro 登录用户信息
 */
public class AccountProfile implements Serializable {

    private Integer id;

    private String userName;

    private String email;

    private String token;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}

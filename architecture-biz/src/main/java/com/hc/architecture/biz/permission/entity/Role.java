package com.hc.architecture.biz.permission.entity;

import lombok.Data;

/**
 * @Author: hechuan
 * @Date: 2020/5/26 17:05
 * @Description: 角色实体
 */
@Data
public class Role extends BaseEntity{

    /**
     * 用户名
     */
    private String name;

    /**
     * 角色code
     */
    private String code;

}

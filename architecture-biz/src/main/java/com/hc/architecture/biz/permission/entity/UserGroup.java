package com.hc.architecture.biz.permission.entity;

import lombok.Data;

/**
 * @Author: hechuan
 * @Date: 2020/5/26 17:13
 * @Description: 用户组
 */
@Data
public class UserGroup extends BaseEntity{

    /**
     * 组名
     */
    private String name;

    /**
     * 父类组
     */
    private Integer parentId;



}

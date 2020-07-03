package com.hc.architecture.biz.permission.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: hechuan
 * @Date: 2020/5/30 16:29
 * @Description: 授权角色菜单
 */
@Data
public class AuthorRoleMenuVo implements Serializable {

    private Integer roleId;

    private List<Integer> menuIds;

    private String roleCode;


}

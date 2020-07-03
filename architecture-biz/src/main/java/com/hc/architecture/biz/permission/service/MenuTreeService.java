package com.hc.architecture.biz.permission.service;

import com.hc.architecture.biz.permission.bo.MenuTreeBo;

import java.util.List;

/**
 * @Author: hechuan
 * @Date: 2020/5/29 17:30
 * @Description: 菜单树
 */
public interface MenuTreeService {

    /**
     * 角色菜单树
     * @param roleId 角色id
     * @return
     */
    List<MenuTreeBo> createMenuTree(Integer roleId);

}

package com.hc.architecture.biz.permission.service;

import com.hc.architecture.biz.permission.bo.MenuTypeBo;
import com.hc.architecture.biz.permission.bo.UserMenuBo;
import com.hc.architecture.biz.permission.entity.Menu;
import com.hc.architecture.biz.permission.vo.MenuVo;
import com.hc.architecture.config.common.ApiResult;
import com.hc.architecture.config.common.page.PageList;

import java.util.List;

/**
 * @Author: hechuan
 * @Date: 2020/5/28 09:07
 * @Description: 菜单service
 */
public interface MenuService {

    /**
     * 新增菜单
     * @param menuVo 菜单
     * @return
     */
    ApiResult<String> addMenu(MenuVo menuVo);

    /**
     * 更新菜单
     * @param menuVo 菜单
     * @return
     */
    int updateMenu(MenuVo menuVo);

    /**
     * 分页查询
     *
     */
    PageList<Menu> searchMenuByPage(MenuVo menuVo);

    /**
     * 更新菜单状态
     * @param menuId 菜单id
     * @param status 状态
     * @return
     */
    int updateMenuStatus(Integer menuId,Integer status);

    /**
     * 用户菜单
     */
    List<UserMenuBo> getUserMenu(int userId);


    /**
     * 获取menu的type
     * @return
     */
    List<MenuTypeBo> getMenuType();

    /**
     * 获取所有非功能菜单
     * @return
     */
    List<Menu> searchAllMenu();
}

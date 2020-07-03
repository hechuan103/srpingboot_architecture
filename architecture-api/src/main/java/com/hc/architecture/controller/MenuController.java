package com.hc.architecture.controller;

import com.hc.architecture.biz.permission.bo.UserMenuBo;
import com.hc.architecture.biz.permission.entity.Menu;
import com.hc.architecture.biz.permission.service.MenuService;
import com.hc.architecture.biz.permission.service.MenuTreeService;
import com.hc.architecture.biz.permission.vo.MenuVo;
import com.hc.architecture.config.common.ApiResult;
import com.hc.architecture.config.common.page.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: hechuan
 * @Date: 2020/5/30 17:30
 * @Description: 菜单管理 api
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuTreeService menuTreeService;


    @PostMapping("/menuList")
    public ApiResult<Object> getMenuList(@RequestBody MenuVo menuVo) {

        PageList<Menu> pageList = menuService.searchMenuByPage(menuVo);

        return ApiResult.success(pageList);
    }

    @GetMapping("/allMenu")
    public ApiResult<Object> getAllMenu() {

        return ApiResult.success(menuService.searchAllMenu());
    }

    @GetMapping("/userMenu")
    public ApiResult<Object> getUserMenu(Integer userId){
        List<UserMenuBo> userMenu = menuService.getUserMenu(userId);
        return ApiResult.success(userMenu);
    }

    @GetMapping("/menuTree")
    public ApiResult getMenuTree(Integer roleId) {

        return ApiResult.success(menuTreeService.createMenuTree(roleId));
    }

    @PostMapping("/addMenu")
    public ApiResult<String> addMenu(@RequestBody MenuVo menuVo) {

        // TODO 参数 校验
        return menuService.addMenu(menuVo);
    }

    @PostMapping("/updateMenu")
    public ApiResult<String> updateMenu(@RequestBody MenuVo menu) {
        int value = menuService.updateMenu(menu);
        if (value > 0) {
            return ApiResult.success();
        }

        return ApiResult.error();
    }


    @PostMapping("/enableMenu")
    public ApiResult<String> enableMenu(@RequestBody MenuVo menu) {
        int value = menuService.updateMenuStatus(menu.getId(), menu.getStatus());
        if (value >0) {
            return ApiResult.success();
        }
        return ApiResult.error();
    }


    @GetMapping("/menuType")
    public ApiResult<Object> getMenuType() {
        return ApiResult.success(menuService.getMenuType());
    }

}

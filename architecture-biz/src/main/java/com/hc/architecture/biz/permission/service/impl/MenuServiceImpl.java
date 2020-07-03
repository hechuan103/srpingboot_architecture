package com.hc.architecture.biz.permission.service.impl;

import com.hc.architecture.biz.permission.bo.MenuTypeBo;
import com.hc.architecture.biz.permission.bo.UserMenuBo;
import com.hc.architecture.biz.permission.bo.UserPermissionBo;
import com.hc.architecture.biz.permission.entity.Menu;
import com.hc.architecture.biz.permission.mapper.MenuMapper;
import com.hc.architecture.biz.permission.mapper.RolePermissionMapper;
import com.hc.architecture.biz.permission.service.MenuService;
import com.hc.architecture.biz.permission.service.UserInfoService;
import com.hc.architecture.biz.permission.vo.MenuVo;
import com.hc.architecture.config.common.ApiResult;
import com.hc.architecture.config.common.CodeMessageEnum;
import com.hc.architecture.config.common.Constants;
import com.hc.architecture.config.common.MenuLevelEnum;
import com.hc.architecture.config.common.i18n.MessageResource;
import com.hc.architecture.config.common.page.PageList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: hechuan
 * @Date: 2020/5/28 10:24
 * @Description: 菜单service 接口
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MessageResource messageResource;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResult<String> addMenu(MenuVo menuVo) {
//        if (menu.getUrl())
        int count = menuMapper.selectMenuByUrl(menuVo.getUrl());
        if (count > 0) {
            // 已经存在
            return ApiResult.error(CodeMessageEnum.MENU_ADD_ERROR_DUPLICATE.getI18n());
        }
        // 转换成entity menu
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuVo,menu);
        menu.setStatus(Constants.ENABLE);
        menu.setCreateTm(String.valueOf(System.currentTimeMillis()));
        menu.setUpdateTm(new Date());
        count = menuMapper.insert(menu);
        if (count > 0) {
            return ApiResult.success();
        }
        return ApiResult.error(CodeMessageEnum.MENU_ADD_ERROR_INSERT.getI18n());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateMenu(MenuVo menuVo) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuVo,menu);
        menu.setUpdateTm(new Date());
        return menuMapper.updateById(menu);
    }

    @Override
    public PageList<Menu> searchMenuByPage(MenuVo menuVo) {
        Map<String, Object> paramMap = new HashMap<>(8);
        paramWrapper(paramMap, menuVo);
        PageList<Menu> pageList = null;
        //查询菜单总数
        int total = menuMapper.selectCountMenu(paramMap);
        pageList = new PageList<>(total, menuVo.getPageNo(), menuVo.getPageSize());
        if (total > 0) {
            paramMap.put("start", pageList.getStartIndex());
            paramMap.put("pageSize", pageList.getPageSize());
            List<Menu> menus = menuMapper.selectMenuByPage(paramMap);
            for (Menu menu : menus) {
                //菜单名称国际化
                Optional<String> name = Optional.ofNullable(messageResource.getMessage(menu.getI18nCode(),null));
                name.ifPresent(menu::setName);
            }
            pageList.setRows(menus);
        }
        paramMap.clear();

        return pageList;
    }



    @Override
    public int updateMenuStatus(Integer menuId, Integer status) {
        /**
         *  更新角色对应菜单的状态：逻辑如果菜单禁用，那么角色对应的菜单也将禁用反之启用一样的。
         *  如果禁用的菜单有子菜单，那么子菜单也被会被禁用。
         * */
        // 获取子菜单的id
        List<Integer> menuIds = menuMapper.selectMenuIdByParentId(menuId);
        menuIds.add(menuId);

        Map<String,Object> paramMap = new HashMap<>(2);
        paramMap.put("menuIds",menuIds);
        paramMap.put("status",status);

        rolePermissionMapper.updateRoleMenuStatus(paramMap);
        menuMapper.updateMenuStatus(paramMap);
        paramMap.clear();
        return Constants.ENABLE;
    }

    @Override
    public List<UserMenuBo> getUserMenu(int userId) {
        //获取用户权限信息
        List<UserPermissionBo> userPermissionBos = userInfoService.getRolePermission(userId);
        //构造用户菜单
        return createUserMenu(userPermissionBos);
    }

    @Override
    public List<MenuTypeBo> getMenuType() {
        List<MenuTypeBo> menuTypeBos = new ArrayList<>(4);
        for (MenuLevelEnum levelEnum : MenuLevelEnum.values()) {
            MenuTypeBo menuTypeBo = new MenuTypeBo();
            Optional<String> name = Optional.ofNullable(messageResource.getMessage(levelEnum.getI18nCode(),null));
            name.ifPresent(menuTypeBo::setName);
            menuTypeBo.setType(levelEnum.getLevel());
            menuTypeBos.add(menuTypeBo);
        }
        return menuTypeBos;
    }

    @Override
    public List<Menu> searchAllMenu() {
        List<Menu> menus = menuMapper.selectAllMenuName();
        for (Menu menu : menus) {
            Optional<String> menuName = Optional.ofNullable(messageResource.getMessage(menu.getI18nCode(), null));
            menuName.ifPresent(menu::setName);
        }
        return menus;
    }

    private List<UserMenuBo> createUserMenu(List<UserPermissionBo> userPermissions) {

        List<UserMenuBo> userMenus = new ArrayList<>();
        Iterator<UserPermissionBo> permissionIter = userPermissions.iterator();
        while (permissionIter.hasNext()) {
            UserPermissionBo permissionBo = permissionIter.next();
            if (MenuLevelEnum.FIRST_LEVEL_MENU_TYPE.getLevel() == permissionBo.getType()) {
                userMenus.add(convertUserPermission(permissionBo));
                permissionIter.remove();
            }
        }
        //排序
        menuListSort(userMenus);
        for (UserMenuBo userMenu : userMenus) {
            List<UserMenuBo> userMenuChilds = userMenuChild(userMenu.getId(), userPermissions);
            menuListSort(userMenuChilds);
            userMenu.setChildren(userMenuChilds);
        }
        //释放userPermissions
        userPermissions.clear();
        return userMenus;
    }

    /**
     * 子菜单设置
     *
     * @param rootId             角色id
     * @param userPermissionList 用户权限信息
     * @return
     */
    private List<UserMenuBo> userMenuChild(Integer rootId, List<UserPermissionBo> userPermissionList) {
        List<UserMenuBo> userMenuBoList = Collections.emptyList();
        if (userPermissionList.size() > 0) {
            userMenuBoList = new ArrayList<>();
            Iterator<UserPermissionBo> iterator = userPermissionList.iterator();
            while (iterator.hasNext()) {
                UserPermissionBo userPermissionBo = iterator.next();
                //功能菜单不需要展示到右侧
                if (MenuLevelEnum.FOURTH_LEVEL_MENU_TYPE.getLevel() == userPermissionBo.getType()) {continue;}
                if (rootId.equals(userPermissionBo.getParentId())) {
                    userMenuBoList.add(convertUserPermission(userPermissionBo));
                    iterator.remove();
                }
            }
        }
        if (userMenuBoList.size() < 1) {
            return userMenuBoList;
        }

        for (UserMenuBo userMenuBo : userMenuBoList) {
            List<UserMenuBo> userMenuChilds = userMenuChild(userMenuBo.getId(), userPermissionList);
            menuListSort(userMenuChilds);
            userMenuBo.setChildren(userMenuChilds);
        }

        return userMenuBoList;
    }


    /**
     * 对象转换 数据对象转前端现实对象
     *
     * @param userPermissionBo 数据角色权限对象
     * @return
     */
    private UserMenuBo convertUserPermission(UserPermissionBo userPermissionBo) {
        UserMenuBo userMenuBo = new UserMenuBo();
        //国际化菜单名称
        Optional<String> name = Optional.ofNullable(messageResource.getMessage(userPermissionBo.getI18nCode(),null));
        if (name.isPresent()) {
            name.ifPresent(userMenuBo::setName);
        } else {
            //返回自己的名字
            userMenuBo.setName(userPermissionBo.getMenuName());
        }
        userMenuBo.setI18nCode(userPermissionBo.getI18nCode());
        userMenuBo.setSort(userPermissionBo.getSort());
        userMenuBo.setIcon(userPermissionBo.getIcon());
        userMenuBo.setId(userPermissionBo.getMenuId());
        userMenuBo.setPath(userPermissionBo.getPath());

        return userMenuBo;
    }

    /**
     * 参数封装
     *
     * @param paramMap
     * @param menuVo
     */
    private void paramWrapper(Map<String, Object> paramMap, MenuVo menuVo) {
        paramMap.put("name", menuVo.getName());
        paramMap.put("code", menuVo.getCode());
        paramMap.put("url", menuVo.getUrl());
        paramMap.put("status", menuVo.getStatus());
    }

    /**
     * 菜单排序按sort升序
     *
     * @param list 待排序list
     */
    private void menuListSort(List<UserMenuBo> list) {
        list.sort(new Comparator<UserMenuBo>() {
            @Override
            public int compare(UserMenuBo userMenuBo, UserMenuBo userMenuBoNext) {
                int value = userMenuBo.getSort() - userMenuBoNext.getSort();
                return Integer.compare(value, 0);
            }
        });
    }


}

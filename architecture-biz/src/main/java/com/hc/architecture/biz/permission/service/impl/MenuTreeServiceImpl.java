package com.hc.architecture.biz.permission.service.impl;

import com.hc.architecture.biz.permission.bo.MenuTreeBo;
import com.hc.architecture.biz.permission.mapper.MenuMapper;
import com.hc.architecture.biz.permission.mapper.RolePermissionMapper;
import com.hc.architecture.biz.permission.entity.Menu;
import com.hc.architecture.biz.permission.service.MenuTreeService;
import com.hc.architecture.config.common.MenuLevelEnum;
import com.hc.architecture.config.common.i18n.MessageResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: hechuan
 * @Date: 2020/5/29 17:40
 */

@Service
public class MenuTreeServiceImpl implements MenuTreeService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private MessageResource messageResource;

    @Override
    public List<MenuTreeBo> createMenuTree(Integer roleId) {
        //查询菜单
        List<Menu> menus = menuMapper.selectAllMenu();
        //查询角色对应的权限
        List<Integer> rolePermissions = rolePermissionMapper.selectSignalRolePermission(roleId);

        List<MenuTreeBo> treeRoot = new ArrayList<>();
        Iterator<Menu> iterator = menus.iterator();
        while (iterator.hasNext()) {
            Menu menu = iterator.next();
            if (MenuLevelEnum.FIRST_LEVEL_MENU_TYPE.getLevel() == menu.getType()) {
                treeRoot.add(menuConvertMenuTree(menu, rolePermissions));
                //从菜单中移除一级菜单
                iterator.remove();
            }
        }
        //一级菜单添加子菜单
        for (MenuTreeBo root : treeRoot) {
            List<MenuTreeBo> childs = getChild(root.getMenuId(), menus, rolePermissions);
            menuListSort(childs);
            root.setChildren(childs);
        }
        // 树形选择判断 逻辑:当父菜单下的所有子菜单被选中时父菜单才能被选中
        checkedSelected(treeRoot);

        return treeRoot;
    }


    private List<MenuTreeBo> getChild(Integer parentId, List<Menu> menus, List<Integer> rolePers) {
        List<MenuTreeBo> childList = new ArrayList<>();
        Iterator<Menu> childIterator = menus.iterator();
        //加入子菜单同时在列表中删除
        while (childIterator.hasNext()) {
            Menu menu = childIterator.next();
            if (menu.getParentId().equals(parentId)) {
                childList.add(menuConvertMenuTree(menu, rolePers));
                childIterator.remove();
            }
        }
        //遍历子菜单的子菜单
        for (MenuTreeBo childMenu : childList) {
            List<MenuTreeBo> childTrees = getChild(childMenu.getMenuId(), menus, rolePers);
            menuListSort(childTrees);
            childMenu.setChildren(childTrees);
        }
        return childList;
    }

    private MenuTreeBo menuConvertMenuTree(Menu menu, List<Integer> rolePermission) {
        MenuTreeBo menuTreeBo = new MenuTreeBo();
        menuTreeBo.setMenuId(menu.getId());
        //国际化菜单
        Optional<String> menuName = Optional.ofNullable(messageResource.getMessage(menu.getI18nCode(), null));
        if (menuName.isPresent()) {
            menuName.ifPresent(menuTreeBo::setMenuName);
        } else {
            menuTreeBo.setMenuName(menu.getName());
        }
        menuTreeBo.setI18nCode(menu.getI18nCode());
        menuTreeBo.setParentId(menu.getParentId());
        menuTreeBo.setSort(menu.getSort());
        menuTreeBo.setIcon(menu.getIcon());
        menuTreeBo.setSelect(false);
        //判断是否选择
        if (rolePermission.size() > 0) {
            Iterator<Integer> iterator = rolePermission.iterator();
            while (iterator.hasNext()) {
                Integer menuId = iterator.next();
                if (menu.getId().equals(menuId)) {
                    menuTreeBo.setSelect(true);
                    iterator.remove();
                }
            }
        }
        return menuTreeBo;
    }

    /**
     * 菜单排序按sort升序
     *
     * @param list 待排序list
     */
    private void menuListSort(List<MenuTreeBo> list) {
        list.sort(new Comparator<MenuTreeBo>() {
            @Override
            public int compare(MenuTreeBo userMenuBo, MenuTreeBo userMenuBoNext) {
                int value = userMenuBo.getSort() - userMenuBoNext.getSort();
                return Integer.compare(value, 0);
            }
        });
    }

    /**
     * @param menuTreeBo 树形菜单是否能被全选方法判断
     */
    private boolean checkedSelected(List<MenuTreeBo> menuTreeBo) {
        boolean parentSelectedFlag = true;
        boolean childSelectedFlag;

        for (MenuTreeBo menuTree : menuTreeBo) {
            if (menuTree.getChildren().size() > 0) {
                childSelectedFlag = checkedSelected(menuTree.getChildren());
                if (!childSelectedFlag) {
                    menuTree.setSelect(false);
                    parentSelectedFlag = false;
                    continue;
                }
            }
            if (!menuTree.isSelect()) {
                return menuTree.isSelect();
            }
        }
        return parentSelectedFlag;
    }
}

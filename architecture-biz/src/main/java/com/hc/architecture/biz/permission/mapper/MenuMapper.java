package com.hc.architecture.biz.permission.mapper;


import com.hc.architecture.biz.permission.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface MenuMapper {

    int insert(Menu record);

    Menu selectById(Integer id);

    /**
     * 更新菜单状态
     * @param record
     * @return
     */
    int updateById(Menu record);

    /**
     * 分页查询
     * @param paramMap
     * @return
     */
    List<Menu> selectMenuByPage(Map<String,Object> paramMap);


    /**
     * 更新菜单状态
     * @param paramMap
     * @return
     */
    int updateMenuStatus(Map<String,Object> paramMap);


    /**
     * 查询满足条件的菜单数量
     * @param paramMap
     * @return
     */
    int selectCountMenu(Map<String, Object> paramMap);

    /**
     * 查询所有状态不0的menu
     * @return
     */
    List<Menu> selectAllMenu();

    /**
     * 查询所有状态不为0的menu 只需要 id，name,parent_id
     * @return
     */
    List<Menu> selectAllMenuName();

    /**
     * 根据url查询菜单
     * @param url 连接地址
     */
    int selectMenuByUrl(String url);

    /**
     * 获取子菜单的id
     * @param menuId 菜单id
     * @return
     */
    List<Integer> selectMenuIdByParentId(Integer menuId);

    /**
     * 查询所有菜单的menuId
     * @return
     */
    List<Menu> selectAllMenuId();
}
package com.hc.architecture.biz.permission.mapper;


import com.hc.architecture.biz.permission.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface RoleMapper {

    int insert(Role record);

    Role selectById(Integer id);

    List<Role> selectRoleByPage(Map<String,Object> paramMap);

    int updateRoleStatus(Map<String,Object> paramMap);

    int updateByPrimaryKey(Role record);

    /**
     * 统计角色数量
     * @param paramMap
     * @return
     */
    int selectRoleCount(Map<String, Object> paramMap);

    /**
     *  查询角色信息
     * @param name 角色名称
     */
    Role selectRoleByName(String name);

    /**
     * 获取所有可用的角色
     * @return
     */
    List<Role> selectAllRole();
}
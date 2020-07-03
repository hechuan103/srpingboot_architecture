package com.hc.architecture.biz.permission.mapper;


import com.hc.architecture.biz.permission.bo.RoleToUserBo;
import com.hc.architecture.biz.permission.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface RoleToUserMapper {

    int insert(UserRole record);


    List<Integer> selectRoleIdByUserId(@Param("id") Integer id);

    /**
     * 查询角色对应的用户数量
     * @param paramMap
     * @return
     */
    int selectRoleUserCount(Map<String, Object> paramMap);

    List<RoleToUserBo> selectRoleUserPage(Map<String, Object> paramMap);

    /**
     * 删除角色对应的某个用户
     * @param paramMap
     * @return
     */
    int deleteUserById(Map<String,Object> paramMap);

    /**
     * 批量插入用户和角色对应表
     * @param userRoles 实例
     */
    int batchInsert(List<UserRole> userRoles);

    /**
     * 删除用户与角色对应的关系
     * @param userId 用户id
     * @return
     */
    int deleteUserByUserId(Integer userId);
}
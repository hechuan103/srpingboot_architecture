package com.hc.architecture.biz.permission.mapper;


import com.hc.architecture.biz.permission.entity.User;
import com.hc.architecture.biz.permission.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface UserMapper {

    /**
     * 插入用户
     * @param record 用户信息
     * @return
     */
    int insert(User record);

    /**
     * 登录查询
     * @param userName 用户名
     * @return
     */
    User loginSearchUser(String userName);

    /**
     * 用户列表
     * @param paramMap 参数map
     * @return
     */
    List<UserVo> selectUserPage(Map<String,Object> paramMap);


    int selectCountUser(Map<String,Object> paramMap);


    /**
     * 更新用户状态
     * @param paramMap 用户id、状态
     * @return
     */
    int updateUserStatus(Map<String,Object> paramMap);


    int updateByPrimaryKey(User record);

    /**
     * 重置密码
     * @param paramMap 参数
     * @return
     */
    int restPassword(Map<String, Object> paramMap);

    /**
     * 查询用户
     * @param currentUserId 用户id
     * @return
     */
    User selectUserById(Integer currentUserId);

    /**
     * 查询用户id
     * @param userName 用户名
     * @return
     */
    Integer selectIdByUserName(String userName);
}
package com.hc.architecture.controller;

import com.hc.architecture.biz.permission.entity.User;
import com.hc.architecture.biz.permission.service.UserInfoService;
import com.hc.architecture.biz.permission.shiro.AccountProfile;
import com.hc.architecture.biz.permission.vo.UserVo;
import com.hc.architecture.config.common.ApiResult;
import com.hc.architecture.config.common.CodeMessageEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @Author: hechuan
 * @Date: 2020/5/29 10:06
 * @Description: 用户管理 api
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;


    @PostMapping("/userList")
    public ApiResult<Object> getUserList(@RequestBody UserVo userVo) {

        return ApiResult.success(userInfoService.getUserList(userVo));
    }


    @PostMapping("/addUser")
    public ApiResult<String> addUser(@RequestBody UserVo userAdd) {

        //TODO 校验user

        Integer userId = userInfoService.getUserIdByUserName(userAdd.getName());
        if (Optional.ofNullable(userId).isPresent()) {
            return ApiResult.error("user.add.error.username.exists");
        }

        int value = userInfoService.addUser(userAdd);
        if (value > 0) {
            return ApiResult.success();
        }

        return ApiResult.error(CodeMessageEnum.ADD_USER_ERROR.getCode(),CodeMessageEnum.ADD_USER_ERROR.getI18n());
    }

    @PostMapping("/updateUser")
    public ApiResult<String> updateUser(@RequestBody UserVo userUpdate) {

        // 校验user
        int value = userInfoService.updateUser(userUpdate);

        if (value > 0) {
            return ApiResult.success();
        }

        return ApiResult.error(CodeMessageEnum.UPDATE_USER_ERROR.getCode(),CodeMessageEnum.UPDATE_USER_ERROR.getI18n());
    }

    @PostMapping("/enableUser")
    public ApiResult<String> enableUser(@RequestBody User user) {
        //TODO 校验参数
        int value = userInfoService.updateStatus(user.getId(),user.getStatus());

        if (value > 0) {
            return ApiResult.success();
        }

        return ApiResult.error();
    }


    @GetMapping("/restPassword")
    public ApiResult<String> restPassword(Integer userId) {
        int value = userInfoService.restPassword(userId);
        if (value > 0) {
            return ApiResult.success(CodeMessageEnum.RESET_USER_PASSWORD_SUCCESS.getI18n());
        }
        return ApiResult.error(CodeMessageEnum.RESET_USER_PASSWORD_ERROR.getI18n());
    }

    @PostMapping("/updatePassword")
    public ApiResult<String> updatePassword(@RequestBody UserVo userVo) {
        if (StringUtils.isEmpty(userVo.getOldPassword())) {
            return ApiResult.error(CodeMessageEnum.UPDATE_USER_OLD_PASSWORD_NULL.getI18n());
        }
        if (StringUtils.isEmpty(userVo.getPassword())) {
            return ApiResult.error(CodeMessageEnum.UPDATE_USER_PASSWORD_NULL.getI18n());
        }
        Object principal = SecurityUtils.getSubject().getPrincipal();
        AccountProfile accountProfile = (AccountProfile) principal;


        return userInfoService.updatePassword(userVo.getOldPassword(), userVo.getPassword(),accountProfile.getId());

    }

    @GetMapping("/getUserRoleId")
    public ApiResult<Object> getUserRoleId(Integer userId) {


        return ApiResult.success(userInfoService.getUserRoleId(userId));
    }




}

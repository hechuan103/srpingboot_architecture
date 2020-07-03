package com.hc.architecture.controller;

import com.hc.architecture.biz.permission.bo.UserBo;
import com.hc.architecture.biz.permission.service.UserInfoService;
import com.hc.architecture.biz.permission.shiro.AccountProfile;
import com.hc.architecture.config.common.ApiResult;
import com.hc.architecture.config.common.CodeMessageEnum;
import com.hc.architecture.config.common.util.RedisUtil;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: hechuan
 * @Date: 2020/5/19 17:24
 * @Description: 用户登录
 */
@Api(authorizations = {@Authorization("hc")}, protocols = "http")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserInfoService userInfoService;


    @ApiOperation(value = "userLogin", notes = "登录接口login")
    //@ApiOperationSupport(ignoreParameters = {"password"}) 一级参数有用
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "userName", required = true, dataType = "String", example = "admin"),
            @ApiImplicitParam(name = "password", value = "password", required = true, dataType = "String", example = "123456")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "ok"),
            @ApiResponse(code = 600, message = "error")
    })
    @PostMapping("/userLogin")
    public ApiResult userLogin(@RequestBody UserBo userBo) {

        if (StringUtils.isEmpty(userBo.getUserName()) || StringUtils.isEmpty(userBo.getPassword())) {
            return ApiResult.error(CodeMessageEnum.USER_PASSWORD_IS_NOT_NULL.getI18n());
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken passwordToken = new UsernamePasswordToken(userBo.getUserName(), userBo.getPassword());
        try {
            // 放到shiro中做登录认证
            subject.login(passwordToken);
            // 获取sessionid
             String token = (String) subject.getSession().getId();
            ((AccountProfile) subject.getPrincipal()).setToken(token);

        } catch (AuthenticationException e) {
            if (e instanceof UnknownAccountException) {
                return ApiResult.error(CodeMessageEnum.USER_IS_NOT_EXIST.getI18n());
            } else if (e instanceof LockedAccountException) {
                return ApiResult.error(CodeMessageEnum.USER_STATUS_IS_DISABLE.getI18n());
            } else if (e instanceof IncorrectCredentialsException) {
                return ApiResult.error(CodeMessageEnum.USER_PASSWORD_ERROR.getI18n());
            } else {
                return ApiResult.error(CodeMessageEnum.USER_AUTH_ERROR.getI18n());
            }
        }

        return ApiResult.success(subject.getPrincipal());
    }


    @GetMapping("/unLogin")
    public ApiResult unLogin() {

        return ApiResult.error(CodeMessageEnum.USER_UN_LOGIN.getCode(), CodeMessageEnum.USER_UN_LOGIN.getI18n());
    }

    @GetMapping("/logout")
    public ApiResult logout() {
        SecurityUtils.getSubject().logout();
        return ApiResult.success();
    }


}

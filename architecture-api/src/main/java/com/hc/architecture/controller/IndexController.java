package com.hc.architecture.controller;

import com.alibaba.fastjson.JSON;
import com.hc.architecture.biz.permission.bo.UserBo;
import com.hc.architecture.config.common.ApiResult;
import com.hc.architecture.config.common.util.RedisUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: hechuan
 * @Date: 2020/5/14 10:47
 * @Description: 首页controller
 */
@Api(value = "登录接口")
@RestController
public class IndexController {

    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    MessageSource messageSource;

    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "返回成功"),
            @ApiResponse(code = 400, message = "出错了")
    })
    @ApiOperation(value = "index 接口", notes = "测试index接口")
    @GetMapping("/index")
//    @LogAspect
    public ApiResult index() {
        logger.info("首页接口");

//        messageSource.getMessage()
        return ApiResult.success();
    }

    @PostMapping("/add")
    @ApiOperation(value = "设置缓存", notes = "设置缓存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "key", required = true, dataType = "String", paramType = "path", example = "test"),
            @ApiImplicitParam(name = "value", value = "value", required = true, dataType = "String", paramType = "path", example = "test2")
    }
    )
    public ApiResult<String> addCache(String key, String value) {
            redisUtil.set(key, value);

        return ApiResult.success();
    }

    @GetMapping("/getCache")
    @ApiOperation(value = "得到缓存", notes = "得到缓存")
    @ApiImplicitParam(name = "key", value = "key", required = true, dataType = "String", paramType = "path", example = "test")
    public ApiResult getCache(String key) {
        return ApiResult.success(redisUtil.get(key));
    }


    /**
     * json 传参转java对象
     * @param userBo
     * @return
     */
    @PostMapping(value = "/addUser")
    public ApiResult<String> addUser(@RequestBody UserBo userBo) {
        logger.info("结果:{}", JSON.toJSONString(userBo));

        return ApiResult.success();
    }

    /**
     * json 传参转java对象
     * @param userName
     * @return
     */
    @PostMapping(value = "/getUser")
    public ApiResult<Object> getUser(String userName) {

        UserBo userBo = new UserBo();
        userBo.setUserName("test");

        return ApiResult.success(userBo);
    }
}

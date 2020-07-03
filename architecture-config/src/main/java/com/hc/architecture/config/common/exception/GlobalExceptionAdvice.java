package com.hc.architecture.config.common.exception;

import com.hc.architecture.config.common.ApiResult;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: hechuan
 * @Date: 2020/5/15 18:01
 * @Description: 统一异常处理 所有controller 抛出的异常可以在这里配置返回格式
 *
 */
@ControllerAdvice
public class GlobalExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);


    /**
     * 无权限
     * @param req
     * @param unauthorizedException
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ApiResult unauthorizedException(HttpServletRequest req, UnauthorizedException unauthorizedException) {

        // 记录错误信息
        logger.error("uri = {}",req.getRequestURI(),unauthorizedException);
        //获取具体的国际化提示信息
        return ApiResult.error("user.without.permission");
    }


    /**
     * 业务异常
     * @param bizException 异常类
     * @return
     */
    @ExceptionHandler(BizException.class)
    @ResponseBody
    public ApiResult<String> bizExceptionHandler(BizException bizException){
        // 记录错误信息
        logger.error("message={}  modelName={}", bizException.getMessage(),bizException.getModelName());
        //获取具体的国际化提示信息
        String i18nCode = bizException.getI18nCode();

        return ApiResult.error(i18nCode);
    }

    /**
     * 工具类异常
     * @param utilException 工具类异常
     * @return
     */
    @ExceptionHandler(UtilException.class)
    @ResponseBody
    public ApiResult<String> utilExceptionHandler(UtilException utilException){
        // 记录错误信息
        logger.error("message={}", utilException.getMessage(),utilException.getException());
        //获取具体的国际化提示信息
        String i18nCode = utilException.getI18nCode();

        return ApiResult.error(i18nCode);
    }

    /**
     * 未知异常统一exception
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResult<String> exceptionHandler(Exception exception) {
        //记录错误信息
        logger.error("未知异常", exception);
        //统一回复系统异常
        return ApiResult.error("SYSTEM_ERROR");

    }
}

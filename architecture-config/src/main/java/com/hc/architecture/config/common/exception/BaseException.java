package com.hc.architecture.config.common.exception;

import lombok.Data;

/**
 * @Author: hechuan
 * @Date: 2020/5/19 14:40
 * @Description: 基础异常类
 */
@Data
public class BaseException extends RuntimeException{

    /**
     * 异常国际化编码
     */
    private String i18nCode;

    /**
     * 自定义异常信息
     */
    private String message;

    public BaseException(String i18nCode, String message) {
        this.i18nCode = i18nCode;
        this.message = message;
    }
}

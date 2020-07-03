package com.hc.architecture.config.common.exception;

import lombok.Data;

/**
 * @Author: hechuan
 * @Date: 2020/5/19 14:38
 * @Description: 工具类异常
 */
@Data
public class UtilException extends BaseException {
    private Exception exception;

    public UtilException(Exception exception,String i18nCode,String message) {
        super(i18nCode,message);
        this.exception = exception;
    }
}

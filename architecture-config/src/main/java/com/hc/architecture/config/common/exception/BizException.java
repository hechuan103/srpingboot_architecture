package com.hc.architecture.config.common.exception;

import lombok.Data;

/**
 * @Author: hechuan
 * @Date: 2020/5/15 17:57
 * @Description: 业务异常配置
 */
@Data
public class BizException extends BaseException{

    /**
     * 模块名称
     */
    private String modelName;

    public BizException(String i18nCode,String message,String modelName) {
        super(i18nCode,message);
        this.modelName = modelName;

    }

}

package com.hc.architecture.config.common.i18n;

import com.hc.architecture.config.common.ApiResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Author: hechuan
 * @Date: 2020/5/18 16:28
 * @Description: 统一返回处理:主要处理国际化编码
 */
@ControllerAdvice
public class EasiResponseAdvice implements ResponseBodyAdvice<Object> {
    @Autowired
    private MessageResource messageResource;

    /**
     * 获取国际化码的文本信息
     */
    @Override
    public Object beforeBodyWrite(Object obj, MethodParameter methodParameter, MediaType mediaType, Class aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
       //统一返回对象
        if (obj instanceof ApiResult) {
            ApiResult result = (ApiResult) obj;
            //获取国际化码
            String i18nCode = result.getI18nCode();
            if (StringUtils.isNotEmpty(i18nCode)) {
                String i18nMessage = messageResource.getMessage(i18nCode,null);
                result.setMessage(i18nMessage);
            }
        }

        return obj;
    }


    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }
}

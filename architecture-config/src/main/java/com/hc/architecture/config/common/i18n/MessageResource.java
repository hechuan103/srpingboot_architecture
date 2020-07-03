package com.hc.architecture.config.common.i18n;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @Author: hechuan
 * @Date: 2020/6/12 15:54
 * @Description: 国际化信息读取
 */
@Component
public class MessageResource {
    private final static Logger logger = LoggerFactory.getLogger(MessageResource.class);
    @Autowired
    private MessageSource messageSource;

    /**
     * 获取编码文件的参数
     *
     * @param i18nCode 国际化编码
     * @param agrs     参数数组 {0} {1}
     * @return
     */
    public String getMessage(String i18nCode, Object[] agrs) {
        if (StringUtils.isEmpty(i18nCode)) {
            return i18nCode;
        }
        // 获取请求参数
        Locale locale = LocaleContextHolder.getLocale();
        // 获取
        try {
            return messageSource.getMessage(i18nCode, agrs, locale);
        } catch (Exception e) {
            logger.error("i18n message resource 缺少i18nCode={}", i18nCode);
            return null;
        }
    }

}

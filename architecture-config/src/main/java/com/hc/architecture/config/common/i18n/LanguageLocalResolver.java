package com.hc.architecture.config.common.i18n;

import com.hc.architecture.config.common.util.EasiRequestHeaderUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @Author: hechuan
 * @Date: 2020/5/18 15:22
 * @Description: 国际化语言配置
 */
public class LanguageLocalResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        //获取客户端语言参数
        String lang = httpServletRequest.getHeader(EasiRequestHeaderUtil.USER_LANGUAGE);
        //获取默认的参数
        Locale locale = httpServletRequest.getLocale();
        if (!StringUtils.isEmpty(lang)) {
            locale = new Locale(lang);
        }

        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }



}

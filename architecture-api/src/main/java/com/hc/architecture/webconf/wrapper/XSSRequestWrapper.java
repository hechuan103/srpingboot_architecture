package com.hc.architecture.webconf.wrapper;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @Author: hechuan
 * @Date: 2020/5/15 10:55
 * @Description: xss 过滤
 */
public class XSSRequestWrapper extends HttpServletRequestWrapper {

    public XSSRequestWrapper(HttpServletRequest request) {
        super(request);
    }


    @Override
    public String[] getParameterValues(String name) {

        String[] values = super.getParameterValues(name);
        String[] encodeValues = new String[values.length];
        if (values.length <= 0) {
            return null;
        }
        for (int i = 0; i < values.length; i++) {
            encodeValues[i] = xssDeal(values[i]);
        }

        return encodeValues;
    }

    @Override
    public String getHeader(String name) {
        String header = super.getHeader(name);


        return xssDeal(header);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return xssDeal(value);
    }


    /**
     * 使用commons-lang3提供的StringEscapeUtils 对传入参数过滤
     *
     * @param value
     * @return
     */
    private String xssDeal(String value) {

        if (value != null) {
            StringEscapeUtils.escapeHtml4(value);
            StringEscapeUtils.escapeEcmaScript(value);
        }

        return value;
    }
}

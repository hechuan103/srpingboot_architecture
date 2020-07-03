package com.hc.architecture.webconf.filter;


import com.hc.architecture.webconf.wrapper.XSSRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: hechuan
 * @Date: 2020/5/15 10:52
 * @Description: xss 过滤器(防止xss攻击)
 */
//@Component
public class XssFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        filterChain.doFilter(new XSSRequestWrapper((HttpServletRequest) servletRequest),servletResponse);
    }

    @Override
    public void destroy() {

    }
}

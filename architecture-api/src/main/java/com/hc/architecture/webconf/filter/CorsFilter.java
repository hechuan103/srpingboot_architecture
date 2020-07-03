package com.hc.architecture.webconf.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: hechuan
 * @Date: 2020/5/14 17:09
 * @Description: 跨域配置（前后端分离会有不能访问需要此类提供允许）
 */
//@Component
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //设置能通过请求域名（一般配置）
        response.setHeader("Access-Control-Allow-Origin", "*");
        //设置请求方法
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        //设置缓存时间
        response.setHeader("Access-Control-Max-Age", "1800");
        //设置请求头
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        // 责任链模式继续请求
        filterChain.doFilter(servletRequest,servletResponse);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}

package com.hc.architecture.webconf.shiro;

import com.hc.architecture.config.common.util.EasiRequestHeaderUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @Author: hechuan
 * @Date: 2020/6/20 17:19
 * @Description: 指定shiro session管理器前后端分离
 */
public class EasiSessionManager extends DefaultWebSessionManager {


    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        // 获取请求头token信息或者sessionId
        String token = WebUtils.toHttp(request).getHeader(EasiRequestHeaderUtil.USER_TOKEN);
        if (StringUtils.isEmpty(token)) {
            // 新生成sessionId
            return super.getSessionId(request,response);
        } else {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "heard");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, token);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return token;
        }

    }
}

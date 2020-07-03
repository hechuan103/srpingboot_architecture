package com.hc.architecture.webconf.intercept;

import com.hc.architecture.config.common.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: hechuan
 * @Date: 2020/5/19 14:14
 * @Description: 登录拦截器配置
 */
public class EasiLoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取请求中的token
//        String token = request.getHeader(EasiRequestHeader.USER_TOKEN);
//        if (StringUtils.isEmpty(token)) {
//            throw new BizException("login.user.token.null","用户token为空","登录拦截器");
//        } else {
//            Object  value = redisUtil.get(token);
//            if (value == null) {
//                throw new BizException("login.user.token.expire","用户token过期","登录拦截器");
//            }
//        }
//        // 刷新token过期时间
//        if (!redisUtil.expireTime(token,60000)) {
//            throw new BizException("set.user.token.expire.error","重置用户token时间失败","登录拦截器");
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

package com.hc.architecture.webconf.aspect;

import com.hc.architecture.config.common.util.EasiRequestHeaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.UUID;

/**
 * @Author: hechuan
 * @Date: 2020/6/4 10:00
 * @Description: 请求日志切面
 */
@Component
@Aspect
@Slf4j
public class LogAspect {
    private static String REQUESTLOG = "requestId:%s ip:%s method:%s params:%s uri:%s lang:%s token:%s";

    @Pointcut("execution(* com.hc.architecture.controller..*(..))")
    public void  requestLog(){}

    @Before("requestLog()")
    public void before(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        Object[] args = joinPoint.getArgs(); //请求参数
        String params = Arrays.asList(args).toString();
        String classMethod = joinPoint.getSignature().getName(); //请求类方法
        String api = request.getServletPath(); // 请求资源路径
        String ip = getRequestIp(request);
        String lang = request.getHeader(EasiRequestHeaderUtil.USER_LANGUAGE);
        String token = request.getHeader(EasiRequestHeaderUtil.USER_TOKEN);
        String requestId = UUID.randomUUID().toString().replaceAll("-","").toLowerCase();

        log.info(String.format(REQUESTLOG,requestId,ip,classMethod,params,api,lang,token));

    }



    private String getRequestIp(HttpServletRequest request) {
        String unknown = "unknown";

        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED");
        }
        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
//        String agent = request.getHeader("User-Agent").substring(0, 12);

        return String.format("%s", ip);
    }

}

package com.hc.architecture.config.common;

/**
 * @Author: hechuan
 * @Date: 2020/5/14 14:20
 * @Description: 常量信息
 */
public class Constants {

    public static String SUCCESS_I18N = "SYSTEM_OK";
    public static String ERROR_I18N = "SYSTEM_ERROR";

    public static String DEFAULT_PASSWORD = "Abc123"; //默认密码

    /**
     * 自定义异常code区分http的状态码
     * 所以从600开始
     */
    public static int ERROR_CODE = 600; //系统异常

    /**
     * code 0 表示成功 参考重庆团队接口
     */
    public static int SUCCESS_CODE = 0;


    public static int ENABLE = 1; //启用

    public static int DISABLE = 0; //禁用

    // session key
    public static String WEB_USER = "current_user";
}

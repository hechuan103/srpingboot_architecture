package com.hc.architecture.config.common.util;

/**
 * @Author: hechuan
 * @Date: 2020/5/18 11:10
 * @Description: 请求头公共参数
 */
public class EasiRequestHeaderUtil {


    /**
     * 城市id
     * 必填: 否 -1 表示空
     */
    public static final String CITY_ID = "City-ID";
    /**
     * 用户语言
     * 必填: 是
     */
    public static final String USER_LANGUAGE = "User-Language";

    /**
     * 用户经纬度
     * 必填：否
     * 格式：纬度，经度 (-37.8425468,145.018809)
     */
    public static final String USER_LOCATION = "User-Location";

    /**
     * 用户token，登录后获取
     * 必填：需要登录才能访问的接口必填，其他不必填
     */
    public static final String USER_TOKEN = "User-Token";

    /**
     * 客户端通道
     * 必填：是
     */
    public static final String APP_CHANNEL = "App-Channel";

    /**
     * 公钥
     * 必填：否
     */
    public static final String ACCESS_KEY = "accessKey";

    /**
     * 随机数
     * 必填：否
     */
    public static final String NONCE = "nonce";

    /**
     * 时间戳
     * 必填： 否
     */
    public static final String TIMESTAMP = "timestamp";

    /**
     * 签名
     * 必填： 否
     */
    public static final String SIGN = "sign";


}

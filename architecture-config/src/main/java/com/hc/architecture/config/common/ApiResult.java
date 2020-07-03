package com.hc.architecture.config.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: hechuan
 * @Date: 2020/5/14 14:10
 * @Description: 统一返回对象
 */
@ApiModel("统一返回对象")
@Data
public class ApiResult<T> {

    /**
     * 不准new
     *
     * @param code     状态码
     * @param i18nCode 国际化提示信息码
     * @param data 数据
     */
    private ApiResult(int code, String i18nCode, T data) {
        this.code = code;
        this.i18nCode = i18nCode;
        this.data = data;
    }

    @ApiModelProperty(required = true, notes = "状态码", example = "0")
    private int code; //状态码

    @ApiModelProperty(required = true, notes = "提示信息/错误信息", example = "操作成功")
    private String message; //信息

    @JsonIgnore
    private String i18nCode; //国际化提示信息码

    @ApiModelProperty(required = true, notes = "返回数据", example = "{\"user\":{\"name\":\"test\"}}")
    private T data;//数据


    /**
     * 不带信息返回
     *
     * @return 默认成功信息
     */
    public static ApiResult<String> success() {
        return new ApiResult<>(Constants.SUCCESS_CODE, Constants.SUCCESS_I18N, null);
    }

    /**
     * api 层返回
     * 成功返回
     *
     * @param data 传入数据
     * @return 适用查询返回
     */
    public static ApiResult<Object> success(Object data) {
        return new ApiResult<>(Constants.SUCCESS_CODE, Constants.SUCCESS_I18N, data);
    }

    /**
     * 自定义返回信息和状态码
     *
     * @param i18nCode 国际化提示信息码
     * @return 适用增删改操作返回
     */
    public static ApiResult<String> success(String i18nCode) {
        return new ApiResult<>(Constants.SUCCESS_CODE, i18nCode, null);
    }


    /**
     * 出错返回
     *
     * @return 适用直接提示"系统异常"
     */
    public static ApiResult<String> error() {
        return new ApiResult<>(Constants.ERROR_CODE, Constants.ERROR_I18N, null);
    }

    /**
     * 默认错误code自定义错误信息
     *
     * @param i18nCode 信息国际化码
     * @return 自定义信息国际化码
     */
    public static ApiResult<String> error(String i18nCode) {
        return new ApiResult<>(Constants.ERROR_CODE, i18nCode, null);
    }

    /**
     * 自定义状态码和错误信息返回
     *
     * @param code     状态码
     * @param i18nCode 信息国际化码
     * @return 适用自定义状态码，自定义信息国际化码
     */
    public static ApiResult<String> error(int code, String i18nCode) {
        return new ApiResult<>(code, i18nCode, null);
    }


}

package com.hc.architecture.biz.permission.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: hechuan
 * @Date: 2020/5/19 17:49
 * @Description: 用户bo
 */
@Data
public class UserBo implements Serializable {

    @ApiModelProperty(value = "",name = "userName",example = "admin_doc")
    private String userName;

    @ApiModelProperty(name = "password",value = "")
    private String password;

    @ApiModelProperty(value = "token",name = "token",example = "agdsuf0ss...")
    private String token;
    @ApiModelProperty(value = "",name = "id")
    private Integer id;




}

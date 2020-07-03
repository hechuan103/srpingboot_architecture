package com.hc.architecture.biz.permission.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: hechuan
 * @Date: 2020/5/26 17:35
 * @Description: 基础entity,公用属性
 */
@Data
public class BaseEntity implements Serializable {


    private Integer id;
    /**
     * 状态1-启用0-冻结
     *
     */
    private Integer status;

    /**
     *
     * 新建时间 时间戳（考虑国际化）
     */
    private String createTm;

    /**
     * 更新时间
     */
    private Date updateTm;

}

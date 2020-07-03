package com.hc.architecture.biz.permission.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: hechuan
 * @Date: 2020/5/29 14:30
 * @Description: 分页查询基础
 */
@Data
public class PageSearchVo implements Serializable {


    /**
     * 当前页
     */
    private Integer pageNo;

    /**
     * 页数据大小
     */
    private Integer pageSize;

    /**
     * 状态1-启用0-冻结
     *
     */
    private Integer status;

}

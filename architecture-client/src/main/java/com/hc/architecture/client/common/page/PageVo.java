package com.hc.architecture.client.common.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageVo<T> implements Serializable {
    private static final long serialVersionUID = 3337590592211627537L;

    private List<T> list;

    private Integer pageIndex;

    private Integer pageSize;

    private Long total;

}

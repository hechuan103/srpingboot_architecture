package com.hc.architecture.config.common.page;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: hechuan
 * @Date: 2019/11/2 15:20
 * @Description: 分页类
 */
public class PageList<T> implements Serializable {

    private static final int DEFAULT_PAGE_SIZE = 10;
    /**
     * 总数
     */
    private int totalCount;

    /**
     * 当前页
     */
    private int pageNo;

    /**
     * 分页数
     */
    private int totalPage;

    /**
     * 每页条数
     */
    private int pageSize;

    /**
     * 分页起始
     */
    @JsonIgnore
    private int startIndex;


    /**
     * 数据
     */
    private List<T> rows;

    public PageList(int totalCount, int pageNo, int pageSize) {

        this.totalCount = totalCount;
        this.pageNo = pageNo;
        this.pageSize = (pageSize == 0 ? DEFAULT_PAGE_SIZE : pageSize);
        this.totalPage = totalCount % getPageSize() == 0 ? totalCount / getPageSize() : (totalCount / getPageSize() + 1);
        this.startIndex = (pageNo-1) * pageSize;

    }


    public int getPageSize() {
        return pageSize;
    }


    public int getTotalCount() {
        return totalCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getStartIndex(){
        return startIndex;
    }
}

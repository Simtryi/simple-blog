package com.simple.blog.common.api;

import com.github.pagehelper.Page;

import java.util.List;

/**
 * 分页数据封装类
 */
public class CommonPage<T> {

    /**
     * 当前页码
     */
    private long pageNum;

    /**
     * 每页数量
     */
    private long pageSize;

    /**
     * 总页数
     */
    private long totalPage;

    /**
     * 总条目
     */
    private long total;

    /**
     * 分页数据
     */
    private List<T> data;

    public CommonPage(Page<T> page) {
        pageNum = page.getPageNum();
        pageSize = page.getPageSize();
        total = page.getTotal();
        totalPage = (long) Math.ceil(total * 1.0 / pageSize);
        data = page;
    }

    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}



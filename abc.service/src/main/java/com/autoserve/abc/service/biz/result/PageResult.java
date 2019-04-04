package com.autoserve.abc.service.biz.result;

import com.autoserve.abc.dao.common.PageCondition;

public class PageResult<T> extends ListResult<T> {

    private static final long serialVersionUID = 1453224020829563569L;

    private int               totalCount;
    private int               pageSize;
    private int               currentPage;

    public PageResult(PageCondition condition) {
        this.currentPage = condition.getPage();
        this.pageSize = condition.getPageSize();
    }

    public PageResult(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getDataSize() {
        return this.getData().size();
    }

}

package com.autoserve.abc.dao.common;

/**
 * 分页查询条件
 */
public class PageCondition {
    /**
     * 当前第几页
     */
    private int                 page     = 1;

    /**
     * 当前页大小
     */
    private int                 pageSize = 10;

    /**
     * 排序字段
     */
    private String              orderField;

    /**
     * 排序规则
     */
    private Order               orderSort;

    /**
     * 默认分页条件
     */
    public static PageCondition DEFAULT  = new PageCondition();

    public PageCondition() {
    }

    public PageCondition(int page, int pageSize) {
        if (page > 0) {
            this.page = page;
        }
        if (pageSize > 0) {
            this.pageSize = pageSize;
        }
    }

    public PageCondition(int page, int pageSize, String orderField, Order orderSort) {
        this.page = page;
        this.pageSize = pageSize;
        this.orderField = orderField;
        this.orderSort = orderSort;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageOffset() {
        return (page - 1) * pageSize;
    }

    public String getOrderField() {
        return orderField;
    }

    public Order getOrderSort() {
        return orderSort;
    }

    /**
     * 排序规则
     */
    public static enum Order {
        /**
         * 升序
         */
        ASC,

        /**
         * 降序
         */
        DESC;
    }

}

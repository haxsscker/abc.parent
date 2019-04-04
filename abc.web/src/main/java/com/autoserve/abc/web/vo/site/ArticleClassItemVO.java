package com.autoserve.abc.web.vo.site;

import java.util.List;

public class ArticleClassItemVO {
    private Integer              total;
    private List<ArticleClassVO> rows;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<ArticleClassVO> getRows() {
        return rows;
    }

    public void setRows(List<ArticleClassVO> rows) {
        this.rows = rows;
    }

}

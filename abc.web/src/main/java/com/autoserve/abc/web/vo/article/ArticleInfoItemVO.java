package com.autoserve.abc.web.vo.article;

import java.util.List;

public class ArticleInfoItemVO {
    private Integer             total;
    private List<ArticleInfoVO> rows;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<ArticleInfoVO> getRows() {
        return rows;
    }

    public void setRows(List<ArticleInfoVO> rows) {
        this.rows = rows;
    }

}

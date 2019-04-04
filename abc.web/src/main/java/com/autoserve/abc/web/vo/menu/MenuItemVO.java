package com.autoserve.abc.web.vo.menu;

import java.util.List;

import com.autoserve.abc.service.biz.entity.MenuItem;

public class MenuItemVO {

    private Integer        total;
    private List<MenuItem> rows;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<MenuItem> getRows() {
        return rows;
    }

    public void setRows(List<MenuItem> rows) {
        this.rows = rows;
    }

}

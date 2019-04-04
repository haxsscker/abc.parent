package com.autoserve.abc.web.vo.menu;

import java.util.List;

import com.autoserve.abc.service.biz.entity.WeChatMenuItem;

public class WeChatMenuItemVO {
    private Integer              total;
    private List<WeChatMenuItem> rows;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<WeChatMenuItem> getRows() {
        return rows;
    }

    public void setRows(List<WeChatMenuItem> rows) {
        this.rows = rows;
    }
}

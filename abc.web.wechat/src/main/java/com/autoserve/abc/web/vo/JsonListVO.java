package com.autoserve.abc.web.vo;

import java.util.List;

public class JsonListVO<T> extends JsonBaseVO {

    private List<T> rows;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}

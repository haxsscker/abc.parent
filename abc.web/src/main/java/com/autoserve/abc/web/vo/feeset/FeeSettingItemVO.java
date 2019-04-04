package com.autoserve.abc.web.vo.feeset;

import java.util.List;

public class FeeSettingItemVO {

    private Integer            total;
    private List<FeeSettingVO> rows;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<FeeSettingVO> getRows() {
        return rows;
    }

    public void setRows(List<FeeSettingVO> rows) {
        this.rows = rows;
    }

}

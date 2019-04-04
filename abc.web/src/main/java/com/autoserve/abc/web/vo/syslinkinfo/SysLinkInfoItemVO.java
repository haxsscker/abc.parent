package com.autoserve.abc.web.vo.syslinkinfo;

import java.util.List;

public class SysLinkInfoItemVO {

    private Integer             total;
    private List<SysLinkInfoVO> rows;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<SysLinkInfoVO> getRows() {
        return rows;
    }

    public void setRows(List<SysLinkInfoVO> rows) {
        this.rows = rows;
    }

}

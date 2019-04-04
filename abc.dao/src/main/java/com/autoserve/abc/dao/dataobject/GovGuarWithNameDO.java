package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;

/**
 * 机构信息与担保机构关联,包含机构的名称
 */
public class GovGuarWithNameDO {
    /**
     * abc_gov_guar.gg_id
     */
    private Integer ggId;

    /**
     * 机构ID
     * abc_gov_guar.gg_gov_id
     */
    private Integer ggGovId;

    /**
     * 担保机构ID
     * abc_gov_guar.gg_guar_id
     */
    private Integer ggGuarId;

    /**
     * 担保机构名称
     */
    private String guarName;

    /**
     * 已担保额度
     * abc_gov_guar.gg_guar_amount
     */
    private BigDecimal ggGuarAmount;

    public Integer getGgId() {
        return ggId;
    }

    public void setGgId(Integer ggId) {
        this.ggId = ggId;
    }

    public Integer getGgGovId() {
        return ggGovId;
    }

    public void setGgGovId(Integer ggGovId) {
        this.ggGovId = ggGovId;
    }

    public Integer getGgGuarId() {
        return ggGuarId;
    }

    public void setGgGuarId(Integer ggGuarId) {
        this.ggGuarId = ggGuarId;
    }

    public BigDecimal getGgGuarAmount() {
        return ggGuarAmount;
    }

    public void setGgGuarAmount(BigDecimal ggGuarAmount) {
        this.ggGuarAmount = ggGuarAmount;
    }

    public String getGuarName() {
        return guarName;
    }

    public void setGuarName(String guarName) {
        this.guarName = guarName;
    }
}
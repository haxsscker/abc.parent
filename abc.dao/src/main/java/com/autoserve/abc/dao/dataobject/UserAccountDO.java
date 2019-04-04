package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * UserAccountDO abc_user_account
 */
public class UserAccountDO {
    /**
     * 主键id abc_user_account.ua_id
     */
    private Integer    uaId;

    /**
     * 资金总额 abc_user_account.ua_total_money
     */
    private BigDecimal uaTotalMoney;

    /**
     * 可用余额 abc_user_account.ua_useable_money
     */
    private BigDecimal uaUseableMoney;

    /**
     * 冻结金额 abc_user_account.ua_frozen
     */
    private BigDecimal uaFrozen;

    /**
     * 用户账户id abc_user_account.ua_account_id
     */
    private String     uaAccountNo;

    /**
     * 操作日期 abc_user_account.ua_operate_date
     */
    private Date       uaOperateDate;

    public Integer getUaId() {
        return uaId;
    }

    public void setUaId(Integer uaId) {
        this.uaId = uaId;
    }

    public BigDecimal getUaTotalMoney() {
        return uaTotalMoney;
    }

    public void setUaTotalMoney(BigDecimal uaTotalMoney) {
        this.uaTotalMoney = uaTotalMoney;
    }

    public BigDecimal getUaUseableMoney() {
        return uaUseableMoney;
    }

    public void setUaUseableMoney(BigDecimal uaUseableMoney) {
        this.uaUseableMoney = uaUseableMoney;
    }

    public BigDecimal getUaFrozen() {
        return uaFrozen;
    }

    public void setUaFrozen(BigDecimal uaFrozen) {
        this.uaFrozen = uaFrozen;
    }

    public Date getUaOperateDate() {
        return uaOperateDate;
    }

    public void setUaOperateDate(Date uaOperateDate) {
        this.uaOperateDate = uaOperateDate;
    }

    public String getUaAccountNo() {
        return uaAccountNo;
    }

    public void setUaAccountNo(String uaAccountNo) {
        this.uaAccountNo = uaAccountNo;
    }
}

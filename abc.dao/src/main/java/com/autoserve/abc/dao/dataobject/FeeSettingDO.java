package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  FeeSettingDO
 *  abc_fee_setting
 */
public class FeeSettingDO {
    /**
     * 主键id
     * abc_fee_setting.fs_id
     */
    private Integer fsId;

    /**
     * 费用类型，对应FeeType枚举
     * abc_fee_setting.fs_type
     */
    private Integer fsType;

    /**
     * 项目类型，对应LoanCategory枚举
     * abc_fee_setting.fs_loan_category
     */
    private Integer fsLoanCategory;

    /**
     * 收费类型，对应ChargeType枚举
     * abc_fee_setting.fs_charge_type
     */
    private Integer fsChargeType;

    /**
     * 收费比例，适用于收费类型为“按比例收费”
     * abc_fee_setting.fs_rate
     */
    private Double fsRate;

    /**
     * 最小金额，适用于收费类型为“按比例收费”
     * abc_fee_setting.fs_min_amount
     */
    private BigDecimal fsMinAmount;

    /**
     * 最大金额，适用于收费类型为“按比例收费”
     * abc_fee_setting.fs_max_amount
     */
    private BigDecimal fsMaxAmount;

    /**
     * 确定的金额，适用于收费类型为“按每笔收费”
     * abc_fee_setting.fs_accurate_amount
     */
    private BigDecimal fsAccurateAmount;

    /**
     * 更新时间
     * abc_fee_setting.fs_update_time
     */
    private Date fsUpdateTime;

    /**
     * 是否逻辑删除 0否 1是 默认0未删除
     * abc_fee_setting.fs_deleted
     */
    private Integer fsDeleted = 0;

    @Override
    public String toString() {
        return "FeeSettingDO {\n" +
                "fsId=" + fsId + ", \n" +
                "fsType=" + fsType + ", \n" +
                "fsRate=" + fsRate + ", \n" +
                "fsLoanCategory=" + fsLoanCategory + ", \n" +
                "fsChargeType=" + fsChargeType + ", \n" +
                "fsMinAmount=" + fsMinAmount + ", \n" +
                "fsMaxAmount=" + fsMaxAmount + ", \n" +
                "fsAccurateAmount=" + fsAccurateAmount + ", \n" +
                "fsUpdateTime=" + fsUpdateTime + ", \n" +
                "fsDeleted=" + fsDeleted + "\n" +
                '}';
    }

    public Integer getFsId() {
        return fsId;
    }

    public void setFsId(Integer fsId) {
        this.fsId = fsId;
    }

    public Integer getFsType() {
        return fsType;
    }

    public void setFsType(Integer fsType) {
        this.fsType = fsType;
    }

    public Double getFsRate() {
        return fsRate;
    }

    public void setFsRate(Double fsRate) {
        this.fsRate = fsRate;
    }

    public Integer getFsLoanCategory() {
        return fsLoanCategory;
    }

    public void setFsLoanCategory(Integer fsLoanCategory) {
        this.fsLoanCategory = fsLoanCategory;
    }

    public Integer getFsChargeType() {
        return fsChargeType;
    }

    public void setFsChargeType(Integer fsChargeType) {
        this.fsChargeType = fsChargeType;
    }

    public BigDecimal getFsMinAmount() {
        return fsMinAmount;
    }

    public void setFsMinAmount(BigDecimal fsMinAmount) {
        this.fsMinAmount = fsMinAmount;
    }

    public BigDecimal getFsMaxAmount() {
        return fsMaxAmount;
    }

    public void setFsMaxAmount(BigDecimal fsMaxAmount) {
        this.fsMaxAmount = fsMaxAmount;
    }

    public BigDecimal getFsAccurateAmount() {
        return fsAccurateAmount;
    }

    public void setFsAccurateAmount(BigDecimal fsAccurateAmount) {
        this.fsAccurateAmount = fsAccurateAmount;
    }

    public Date getFsUpdateTime() {
        return fsUpdateTime;
    }

    public void setFsUpdateTime(Date fsUpdateTime) {
        this.fsUpdateTime = fsUpdateTime;
    }

    public Integer getFsDeleted() {
        return fsDeleted;
    }

    /**
     * 注意：禁止手动设置
     * @param Integer fsDeleted (abc_fee_setting.fs_deleted )
     */
    /*public void setFsDeleted(Integer fsDeleted) {
        this.fsDeleted = fsDeleted;
    }*/
}

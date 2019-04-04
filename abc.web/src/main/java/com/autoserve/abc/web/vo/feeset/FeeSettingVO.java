package com.autoserve.abc.web.vo.feeset;

import java.math.BigDecimal;
import java.util.Date;

import com.autoserve.abc.service.biz.enums.LoanCategory;

/**
 * 类FeeSetingVO.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月8日 下午6:40:03
 */
public class FeeSettingVO {
    private int        sys_fee_id;

    /**
     * 费用类型，对应FeeType枚举
     */
    private int        sys_fee_type;

    /**
     * 项目类型，对应LoanCategory枚举
     */
    private int        sys_product_id;
    
    /**
     * 项目类型名
     */
    private String        sys_product_name;

    /**
     * 收费类型，对应ChargeType枚举
     */
    private int        sys_collect_type;

    /**
     * 收费比例，适用于收费类型为“按比例收费”
     */
    private double     sys_fee_rate;

    /**
     * 最小金额，适用于收费类型为“按比例收费”
     */
    private BigDecimal sys_max_money;

    /**
     * 最大金额，适用于收费类型为“按比例收费”
     */
    private BigDecimal sys_min_money;

    /**
     * 确定的金额，适用于收费类型为“按每笔收费”
     */
    private BigDecimal accurateAmount;

    private Date       updateTime;

    public int getSys_fee_id() {
        return sys_fee_id;
    }

    public void setSys_fee_id(int sys_fee_id) {
        this.sys_fee_id = sys_fee_id;
    }

    public int getSys_fee_type() {
        return sys_fee_type;
    }

    public void setSys_fee_type(int sys_fee_type) {
        this.sys_fee_type = sys_fee_type;
    }

    public int getSys_product_id() {
        return sys_product_id;
    }

    public void setSys_product_id(int sys_product_id) {
        this.sys_product_id = sys_product_id;
        this.sys_product_name = LoanCategory.valueOf(this.sys_product_id).getPrompt();
    }

    public int getSys_collect_type() {
        return sys_collect_type;
    }

    public void setSys_collect_type(int sys_collect_type) {
        this.sys_collect_type = sys_collect_type;
    }

    public double getSys_fee_rate() {
        return sys_fee_rate;
    }

    public void setSys_fee_rate(double sys_fee_rate) {
        this.sys_fee_rate = sys_fee_rate;
    }

    public BigDecimal getSys_max_money() {
        return sys_max_money;
    }

    public void setSys_max_money(BigDecimal sys_max_money) {
        this.sys_max_money = sys_max_money;
    }

    public BigDecimal getSys_min_money() {
        return sys_min_money;
    }

    public void setSys_min_money(BigDecimal sys_min_money) {
        this.sys_min_money = sys_min_money;
    }

    public BigDecimal getAccurateAmount() {
        return accurateAmount;
    }

    public void setAccurateAmount(BigDecimal accurateAmount) {
        this.accurateAmount = accurateAmount;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getSys_product_name() {
		return LoanCategory.valueOf(this.sys_product_id).getPrompt();
	}

	public void setSys_product_name(String sys_product_name) {
		this.sys_product_name = sys_product_name;
	}

}

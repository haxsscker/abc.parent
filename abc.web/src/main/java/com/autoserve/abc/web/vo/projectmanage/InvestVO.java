/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.vo.projectmanage;

import com.autoserve.abc.service.biz.entity.Invest;

/**
 * 投资概要信息
 *
 * @author segen189 2014年12月25日 下午12:18:56
 */
public class InvestVO extends Invest {
    /**
     * 投资时间字符串
     */
    private String investTimeStr;

    /**
     * 投资人姓名
     */
    private String investerName;

    /**
     * 投资状态
     */
    private String investStateStr;

    public String getInvestTimeStr() {
        return investTimeStr;
    }

    public void setInvestTimeStr(String investTimeStr) {
        this.investTimeStr = investTimeStr;
    }

    public String getInvesterName() {
        return investerName;
    }

    public void setInvesterName(String investerName) {
        this.investerName = investerName;
    }

    public String getInvestStateStr() {
        return investStateStr;
    }

    public void setInvestStateStr(String investStateStr) {
        this.investStateStr = investStateStr;
    }

}

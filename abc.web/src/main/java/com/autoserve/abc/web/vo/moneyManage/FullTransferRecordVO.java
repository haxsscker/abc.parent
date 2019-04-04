/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.vo.moneyManage;

import com.autoserve.abc.dao.dataobject.FullTransferRecordJDO;

public class FullTransferRecordVO extends FullTransferRecordJDO {

    /**
     * 满标时间
     */
    private String proFullDateStr;

    /**
     * 收购时间
     */
    private String proBuyDateStr;

    /**
     * 转让时间
     */
    private String proTransferDateStr;

    /**
     * 划转时间
     */
    private String proStartDateStr;

    public String getProFullDateStr() {
        return proFullDateStr;
    }

    public void setProFullDateStr(String proFullDateStr) {
        this.proFullDateStr = proFullDateStr;
    }

    public String getProBuyDateStr() {
        return proBuyDateStr;
    }

    public void setProBuyDateStr(String proBuyDateStr) {
        this.proBuyDateStr = proBuyDateStr;
    }

    public String getProTransferDateStr() {
        return proTransferDateStr;
    }

    public void setProTransferDateStr(String proTransferDateStr) {
        this.proTransferDateStr = proTransferDateStr;
    }

    public String getProStartDateStr() {
        return proStartDateStr;
    }

    public void setProStartDateStr(String proStartDateStr) {
        this.proStartDateStr = proStartDateStr;
    }

}

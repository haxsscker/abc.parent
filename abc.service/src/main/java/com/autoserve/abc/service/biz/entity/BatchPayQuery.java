package com.autoserve.abc.service.biz.entity;

import java.util.Date;

import com.autoserve.abc.service.biz.enums.EasyPayConfig;
import com.autoserve.abc.service.util.DateUtil;

/**
 * 代收代付按照批次查询接口
 * 
 * @author pp 2014-11-26 下午04:09:46
 */
public class BatchPayQuery {

    private String transCode;   //必输项  交易代码
    private String customerNo;  //必输项 商户编号
    private String customerKey; //必输项 安全校验码
    private String batchVersion; //必输项 版本号
    private String tranDate;    //必输项 交易日期
    private String tranTime;    //必输项 交易时间
    private String fSeqno;      //必输项 指令包序列号
    private String tradeBatchNo; //必输项 交易批次号
    private String signTime;    //必输项 签名时间
    private String tradeNum;    //选填项
    private String reqReserved; //选填项 请求备用字段

    public BatchPayQuery() {
        batchVersion = EasyPayConfig.BATCH_VERSION;
        customerNo = EasyPayConfig.PARTNER;
        customerKey = EasyPayConfig.KEY;
        tranDate = DateUtil.formatDate(new Date(), DateUtil.DEFAULT_DAY_STYLE_NOSPACE);
        fSeqno = DateUtil.formatDate(new Date(), DateUtil.DEFAULT_FULL);
        tranTime = DateUtil.formatDate(new Date(), DateUtil.DEFAULT_HAOMIAO);
        signTime = DateUtil.formatDate(new Date(), DateUtil.DEFAULT_FULL);
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(String customerKey) {
        this.customerKey = customerKey;
    }

    public String getBatchVersion() {
        return batchVersion;
    }

    public void setBatchVersion(String batchVersion) {
        this.batchVersion = batchVersion;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getTranTime() {
        return tranTime;
    }

    public void setTranTime(String tranTime) {
        this.tranTime = tranTime;
    }

    public String getFSeqno() {
        return fSeqno;
    }

    public void setFSeqno(String seqno) {
        fSeqno = seqno;
    }

    public String getTradeBatchNo() {
        return tradeBatchNo;
    }

    public void setTradeBatchNo(String tradeBatchNo) {
        this.tradeBatchNo = tradeBatchNo;
    }

    public String getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(String tradeNum) {
        this.tradeNum = tradeNum;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getReqReserved() {
        return reqReserved;
    }

    public void setReqReserved(String reqReserved) {
        this.reqReserved = reqReserved;
    }

}

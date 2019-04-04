package com.autoserve.abc.service.biz.entity;

import com.autoserve.abc.service.biz.enums.EasyPayConfig;
import com.autoserve.abc.service.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BatchPayData {
    
    private String transCode;   //必须 交易代码  参考 BatchPayConfig的TransCodeType  目前仅有代付功能
    private String customerNo;  //必须 商户编号 客户注册时的编号 partner号
    private String customerKey; //必须 安全校验码  客户注册时的安全校验码
    private String batchVersion;//必须 版本号 商户批量提交接口版本号
    private String tranDate;    //必须 交易日期  交易日期，格式是yyyyMMdd
    private String tranTime;    //必须 交易时间 格式如hhmmssaaa，精确到毫秒
    private String fSeqno;      //必须 指令包序列 永远不能重复  有接口可以根据此字段查询状态
    private Integer batchCount; //必须 指令包内的交易笔数
    private String tradeBatchNo;//必须 交易批次号
    private BigDecimal batchAmount;//必须  总金额   无正负号，以元作单位
    private String  signTime;    //必须  签名时间  格式是 yyyyMMddhhmmssSSS
    private String  reqReserved; //非必须 请求备用字段 目前无意义 
    private List<ItemOfBatchPay> items; //必须 转账细节
    
    public BatchPayData(){
        this.transCode=EasyPayConfig.TransCodeType.AGENTPAY.value;
        this.customerNo=EasyPayConfig.PARTNER;
        this.customerKey=EasyPayConfig.KEY;
        this.batchVersion=EasyPayConfig.BATCH_VERSION;
        Date now=new Date();
        this.tranDate=DateUtil.formatDate(now, DateUtil.DEFAULT_DAY_STYLE_NOSPACE);
        this.tranTime=DateUtil.formatDate(now, DateUtil.DEFAULT_HAOMIAO);
        this.fSeqno=DateUtil.formatDate(now, DateUtil.DEFAULT_FULL);
        this.signTime=DateUtil.formatDate(now, DateUtil.DEFAULT_FULL);
        
    }
    
    public String getTransCode() {
        return transCode;
    }
    public String getCustomerNo() {
        return customerNo;
    }
    public String getCustomerKey() {
        return customerKey;
    }
    public String getBatchVersion() {
        return batchVersion;
    }
    public String getTranDate() {
        return tranDate;
    }
    public String getTranTime() {
        return tranTime;
    }
    public String getFSeqno() {
        return fSeqno;
    }
    public Integer getBatchCount() {
        return batchCount;
    }
    public void setBatchCount(Integer batchCount) {
        this.batchCount = batchCount;
    }
    public String getTradeBatchNo() {
        return tradeBatchNo;
    }
    public void setTradeBatchNo(String tradeBatchNo) {
        this.tradeBatchNo = tradeBatchNo;
    }
    public BigDecimal getBatchAmount() {
        return batchAmount;
    }
    public void setBatchAmount(BigDecimal batchAmount) {
        this.batchAmount = batchAmount;
    }
    public String getSignTime() {
        return signTime;
    }
    public String getReqReserved() {
        return reqReserved;
    }
    public List<ItemOfBatchPay> getItems() {
        return items;
    }
    public void setItems(List<ItemOfBatchPay> items) {
        this.items = items;
    }
    
}

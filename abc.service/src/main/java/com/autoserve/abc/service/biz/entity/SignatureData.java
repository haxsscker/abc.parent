package com.autoserve.abc.service.biz.entity;

import com.autoserve.abc.service.biz.enums.EasyPayConfig;

public class SignatureData {

    private String        batchBizid;                //合作伙伴
    private String        batchVersion;              //版本  默认01
    private String        batchContent;              //通过RSA加密后的内容

    public SignatureData() {
        batchVersion = EasyPayConfig.BATCH_VERSION;
    }

    public SignatureData(String batchBizid, String batchContent) {
        this.batchBizid = batchBizid;
        batchVersion = EasyPayConfig.BATCH_VERSION;
        this.batchContent = batchContent;
    }

    public String getBatchBizid() {
        return batchBizid;
    }

    public void setBatchBizid(String batchBizid) {
        this.batchBizid = batchBizid;
    }

    public String getBatchVersion() {
        return batchVersion;
    }

    public void setBatchVersion(String batchVersion) {
        this.batchVersion = batchVersion;
    }

    public String getBatchContent() {
        return batchContent;
    }

    public void setBatchContent(String batchContent) {
        this.batchContent = batchContent;
    }

}

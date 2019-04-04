package com.autoserve.abc.service.biz.entity;

/**
 * 批量查询返回数据
 * 
 * @author pp 2014-11-25 上午10:14:57
 */
public class BatchPayReply {

    private String status; //参见BatchPayConfig的ReplyStatus
    private String reason; //成功   无内容

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}

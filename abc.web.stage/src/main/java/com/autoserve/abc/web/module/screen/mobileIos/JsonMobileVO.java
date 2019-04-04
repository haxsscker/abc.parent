package com.autoserve.abc.web.module.screen.mobileIos;

import com.autoserve.abc.service.biz.result.BaseResult;

/**
 * 手机端返回
 * 
 * @author Bo.Zhang
 */
public class JsonMobileVO extends BaseResult {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public String             resultCode       = "200";

    public String             resultMessage    = "Success";

    public Object             result;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}

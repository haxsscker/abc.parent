package com.autoserve.abc.web.module.screen.mobile;

/**
 * 手机端返回
 * 
 * @author Bo.Zhang
 *
 */
public class JsonMobileVO {
	
	public String resultCode;
	
	public String resultMessage;
	
	public Object result;

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

package com.autoserve.abc.web.module.screen.account.myLoan;

public class RepayState {

	/*还款是否成功1 ：成功 0 ：失败*/
	private Integer isOk;
	/*返回的信息*/
	private String message;
	public Integer getIsOk() {
		return isOk;
	}
	public void setIsOk(Integer isOk) {
		this.isOk = isOk;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}

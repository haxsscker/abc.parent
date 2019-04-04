package com.autoserve.abc.dao.dataobject.enums;
/**
 * 提前还款申请状态
 * @author zhangkang
 *
 */
public enum AheadRepayState {
	/**待审核*/
	WAIT_AUDIT("待审核"),
	/**审核未通过*/
	AUDIT_NOT_PASS("审核未通过"),
	/**处理中*/
	AUDIT_WAITING("处理中"),
	/**审核通过*/
	AUDIT_PASS("审核通过");
	
	public final String desc;
	
	AheadRepayState(String desc){
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return this.desc;
	}
}

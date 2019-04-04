package com.autoserve.abc.service.biz.intf.cash;

import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.result.BaseResult;

public interface TiedCardService {
	/**
	 * 绑卡
	 * @param bankInfo
	 * @return
	 */
	public BaseResult tiedCard(BankInfo bankInfo);
	/**
	 * 解绑
	 * @param bankInfo
	 * @return
	 */
	public BaseResult removeTiedCard(BankInfo bankInfo);

}

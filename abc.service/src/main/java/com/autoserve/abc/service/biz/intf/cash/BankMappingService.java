/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.cash;

import java.util.List;

import com.autoserve.abc.dao.dataobject.BankMappingDO;

/**
 * 银行信息service
 * 
 * @author J.YL 2014年11月17日 下午4:02:26
 */
public interface BankMappingService {
	/**
	 * 查询银行卡列表
	 * @param bankInfo 必选：userId,userType 可选：cardNo, cardState
	 * @return
	 */
	List<BankMappingDO> findBankMapping();

	/**
	 * 根据银行卡编码查询银行卡
	 * @param code
	 * @return
	 */
	BankMappingDO findBankMappingByCode(String code);
}

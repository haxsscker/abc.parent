/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.cash;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.dataobject.BankMappingDO;
import com.autoserve.abc.dao.intf.BankMappingDao;
import com.autoserve.abc.service.biz.intf.cash.BankMappingService;

/**
 * 银行信息
 * 
 * @see 暂时未用
 * @author J.YL 2014年11月17日 下午4:02:43
 */
@Service
public class BankMappingServiceImpl implements BankMappingService {

    @Resource
    private BankMappingDao bankDao;


	@Override
	public List<BankMappingDO> findBankMapping(){
		return bankDao.queryAllBankInfo();
	}
	
	@Override
	public BankMappingDO findBankMappingByCode(String code){
		return bankDao.findBankMappingByCode(code);
	}

}

package com.autoserve.abc.service.biz.impl.user;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.dataobject.AssessLevelDO;
import com.autoserve.abc.dao.intf.AssLevelDao;
import com.autoserve.abc.service.biz.intf.user.UserAssessService;

@Service
public class UserAssessServiceImpl implements UserAssessService {

	@Resource
	private AssLevelDao assLevelDao;
	
	@Override
	public AssessLevelDO findById(int assId) {
		
		return assLevelDao.findById(assId);
	}

	@Override
	public void updateUserAssess(int assScore, int userId) {
		
		assLevelDao.updateUserAssess(assScore, userId);
	}
	
	@Override
	public void updateUserTsignId(String tsignAccountId, int userId) {
		
		assLevelDao.updateUserTsignId(tsignAccountId, userId);
	}

	@Override
	public int isValidInvest(Double investedMoney, int userId, int assId) {
		
		return assLevelDao.isValidInvest(BigDecimal.valueOf(investedMoney), userId, assId);
	}

}

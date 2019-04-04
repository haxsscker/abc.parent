package com.autoserve.abc.service.biz.intf.user;

import com.autoserve.abc.dao.dataobject.AssessLevelDO;

/**
 * @author RJQ 2014/12/26 21:50.
 */
public interface UserAssessService {
    
	//查询等级信息
	AssessLevelDO findById(int assId);
	
	//更新用户投资等级	
	void updateUserAssess(int assScore, int userId);
	
	//判断投资是否满足评估
	int isValidInvest(Double investedMoney, int userId, int assId);
	
	void updateUserTsignId(String tsignAccountId, int userId);
}

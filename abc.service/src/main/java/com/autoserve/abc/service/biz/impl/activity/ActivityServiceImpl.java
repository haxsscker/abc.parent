package com.autoserve.abc.service.biz.impl.activity;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.dataobject.ActHolidayDO;
import com.autoserve.abc.dao.dataobject.ActPrizeDO;
import com.autoserve.abc.dao.dataobject.ActUserDO;
import com.autoserve.abc.dao.intf.ActHolidayDao;
import com.autoserve.abc.dao.intf.ActPrizeDao;
import com.autoserve.abc.dao.intf.ActUserDao;
import com.autoserve.abc.service.biz.intf.activity.ActivityService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;

/**
 * @author RJQ 2014/11/20 17:47.
 */
@Service
public class ActivityServiceImpl implements ActivityService,InitializingBean{
	
//	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private ActPrizeDao actPrizeDao;

	@Resource
	private ActUserDao actUserDao;
	
	@Resource
	private ActHolidayDao actHolidayDao;

	@Override
	public void afterPropertiesSet() throws Exception {
		// Auto-generated method stub
	}

	@Override
	public BaseResult createActUser(ActUserDO actUserDO) {
		BaseResult baseResult = new BaseResult();
		if (actUserDO.getActId() == null || actUserDO.getApId() == null || actUserDO.getAuUserId() == null) {
			baseResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "参数缺失");
			return baseResult;
		}
		
		int val = actUserDao.insert(actUserDO);
		if (val <= 0) {
			baseResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "新增中奖失败");
			return baseResult;
		}
		return baseResult;
	}

	@Override
	public int countUserToday(int actId, int userId) {
		return actUserDao.countUserToday(actId, userId);
	}
	
	@Override
	public int countUserPrize(int actId, int userId) {
		return actUserDao.countUserPrize(actId, userId);
	}

	@Override
	public ListResult<ActUserDO> findTopUser(int actId, int topNum) {
		ListResult<ActUserDO> result = new ListResult<ActUserDO>();

		try {
			result.setData(actUserDao.findTopUser(actId, topNum));
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}

		return result;
	}

	@Override
	public ListResult<ActPrizeDO> findAllPrize(int actId) {
		ListResult<ActPrizeDO> result = new ListResult<ActPrizeDO>();

		try {
			result.setData(actPrizeDao.findAllPrize(actId));
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}

		return result;
	}

	@Override
	public int countPrizeUser(int apId,int actId) {
		return actPrizeDao.countPrizeUser(apId, actId);
	}

	@Override
	public ListResult<ActUserDO> findUserPrize(int actId, int userId) {
		
		ListResult<ActUserDO> result = new ListResult<ActUserDO>();

		try {
			result.setData(actUserDao.findUserPrize(actId, userId));
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}

		return result;
	}

	@Override
	public ActHolidayDO findAllHoliday(int actId, String ahDay) {
		
		ActHolidayDO result = null;

		try {
			List<ActHolidayDO> list = actHolidayDao.findAllHoliday(actId, ahDay);
			if (null != list && !list.isEmpty())
			{
				result = list.get(0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public boolean isInvestUser(int userId, int invMoney, String startTime,
			String endTime) {
		
		try {
			return actUserDao.isInvestUser(userId, invMoney, startTime, endTime) > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int getActType(int actId) {
		return actUserDao.getActType(actId);
	}
	
	@Override
	public int isSign(String uName, String uPhone)
	{
		return actUserDao.isSign(uName, uPhone);
	}
	
	@Override
	public void actSignIn(String uName, String uPhone, String uNote)
	{
		actUserDao.actSignIn(uName, uPhone, uNote);
	}
	
	@Override
	public boolean isLoanPriceActive(int actId)
	{
		return actUserDao.isLoanPriceActive(actId) > 0;
	}
}

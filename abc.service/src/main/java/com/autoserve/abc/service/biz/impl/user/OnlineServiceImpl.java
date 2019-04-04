package com.autoserve.abc.service.biz.impl.user;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.dataobject.OnlineDO;
import com.autoserve.abc.dao.intf.OnlineDao;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.ScoreType;
import com.autoserve.abc.service.biz.intf.user.OnlineService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.DateUtil;

@Service
public class OnlineServiceImpl implements OnlineService {
	
	@Resource
	private UserService userService;
	
	@Resource
	private OnlineDao onlineDao;

	@Override
	public PlainResult<OnlineDO> getOnlineInfo(User user, Date dayDate) {
		PlainResult<OnlineDO> result = new PlainResult<OnlineDO>();
		List<OnlineDO> list = onlineDao.findByUserIdAndDayDate(user.getUserId(), dayDate);
		OnlineDO onlineDO = new OnlineDO();
		if(list.size() > 0) {
			onlineDO = list.get(0);
		} else {
			onlineDO = new OnlineDO();
			onlineDO.setUserId(user.getUserId());
			onlineDO.setDayDate(dayDate);
			onlineDO.setOnlineLength(0);
			onlineDO.setScoreNum(0);
		}
		result.setData(onlineDO);
		return result;
	}

	@Override
	public PlainResult<OnlineDO> addOnlineLength(OnlineDO onlineDO, Integer interval) {
		PlainResult<OnlineDO> plainResult = new PlainResult<OnlineDO>();
		plainResult.setMessage("NOTICE_SCORE_NO");
		Date heartTime = new Date();
		List<OnlineDO> list = onlineDao.findByUserIdAndDayDate(onlineDO.getUserId(), onlineDO.getDayDate());
		if(list.size() > 0) {
			//修改操作
			OnlineDO oldEntity = list.get(0);
			if(oldEntity.getScoreNum() >= 3) {
				plainResult.setData(oldEntity);
			} else {
				int addedTime = DateUtil.diff(oldEntity.getHeartTime(),
						heartTime, TimeUnit.SECONDS) > interval ? interval
						: DateUtil.diff(oldEntity.getHeartTime(), heartTime,
								TimeUnit.SECONDS);
				int oldScoreNum = oldEntity.getScoreNum();
				int oldLength = oldEntity.getOnlineLength();
				int newLength = oldLength + addedTime;
				if(oldScoreNum < newLength / (20 * 60)) {
					//送积分
					userService.modifyUserScore(onlineDO.getUserId(), ScoreType.LOGIN_SCORE, null);
					onlineDO.setHeartTime(heartTime);
					onlineDO.setOnlineLength(newLength);
					onlineDO.setScoreNum(newLength / (20 * 60));
					onlineDao.updateByUserIdAndDayDate(onlineDO);
					plainResult.setData(onlineDO);
					plainResult.setMessage("NOTICE_SCORE_YES");
				} else {
					//更新length
					onlineDO.setHeartTime(heartTime);
					onlineDO.setOnlineLength(newLength);
					onlineDO.setScoreNum(oldScoreNum);
					onlineDao.updateByUserIdAndDayDate(onlineDO);
					plainResult.setData(onlineDO);
				}
			}
			
		} else {
			// 添加操作
			onlineDO.setHeartTime(heartTime);
			onlineDO.setOnlineLength(interval);
			onlineDO.setScoreNum(0);
			onlineDao.insert(onlineDO);
			plainResult.setData(onlineDO);
		}
		return plainResult;
	}

}

package com.autoserve.abc.service.biz.impl.user;

import com.autoserve.abc.dao.dataobject.UserHouseDO;
import com.autoserve.abc.dao.intf.UserHouseDao;
import com.autoserve.abc.service.biz.convert.UserHouseConverter;
import com.autoserve.abc.service.biz.entity.UserHouse;
import com.autoserve.abc.service.biz.intf.user.UserHouseService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/26 21:45.
 */
@Service
public class UserHouseServiceImpl implements UserHouseService {
    @Resource
    private UserHouseDao userHouseDao;

    @Override
    public PlainResult<UserHouse> findUserHouseByUserId(int id) {
        UserHouseDO userHouseDO = userHouseDao.findByUserId(id);
        PlainResult<UserHouse> result = new PlainResult<UserHouse>();
        if (null == userHouseDO) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定信息!");
            return result;
        }
        result.setData(UserHouseConverter.toUserHouse(userHouseDO));
        return result;
    }

	@Override
	public BaseResult modifyUserHouse(UserHouse userHouse) {
		BaseResult result = new BaseResult();
		int val = this.userHouseDao.update(UserHouseConverter.toUserHouseDO(userHouse));
		if(val<=0){
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "更新失败!");
			return result;
		}
		return result;
	}

	@Override
	public BaseResult createUserHouse(UserHouse userHouse) {
		BaseResult result = new BaseResult();
		int val = this.userHouseDao.insert(UserHouseConverter.toUserHouseDO(userHouse));
		if(val<=0){
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "创建失败!");
			return result;
		}
		return result;
	}
}

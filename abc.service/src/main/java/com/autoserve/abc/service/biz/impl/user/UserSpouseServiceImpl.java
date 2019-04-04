package com.autoserve.abc.service.biz.impl.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.UserSpouseDO;
import com.autoserve.abc.dao.intf.UserDao;
import com.autoserve.abc.dao.intf.UserSpouseDao;
import com.autoserve.abc.service.biz.convert.UserSpouseConverter;
import com.autoserve.abc.service.biz.entity.UserSpouse;
import com.autoserve.abc.service.biz.intf.user.UserSpouseService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author RJQ 2014/12/26 21:50.
 */
@Service
public class UserSpouseServiceImpl implements UserSpouseService {

    @Resource
    private UserSpouseDao userSpouseDao;

    @Resource
    private UserDao userDao;
    
    @Override
    public PlainResult<UserSpouse> findUserSpouseByUserId(int id) {
        UserSpouseDO userSpouseDO = userSpouseDao.findByUserId(id);
        PlainResult<UserSpouse> result = new PlainResult<UserSpouse>();
        if (null == userSpouseDO) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定信息!");
            return result;
        }
        result.setData(UserSpouseConverter.toUserSpouse(userSpouseDO));
        return result;
    }

	@Override
	public BaseResult modifyUserSpouse(UserSpouse userSpouse, Integer userMarry) {
		BaseResult result = new BaseResult();
        int val = userSpouseDao.update(UserSpouseConverter.toUserSpouseDO(userSpouse));
        UserDO userDO = userDao.findById(userSpouse.getUsUserId());
        userDO.setUserMarry(userMarry);
        userDao.update(userDO);
        if(val <= 0){
            result.setErrorMessage(CommonResultCode.BIZ_ERROR,"更新失败！");
            return result;
        }
        return result;
	}

	@Override
	public BaseResult createUserSpouse(UserSpouse userSpouse, Integer userMarry) {
		BaseResult result = new BaseResult();
        int val = userSpouseDao.insert(UserSpouseConverter.toUserSpouseDO(userSpouse));
        UserDO userDO = userDao.findById(userSpouse.getUsUserId());
        userDO.setUserMarry(userMarry);
        userDao.update(userDO);
        if(val <= 0){
            result.setErrorMessage(CommonResultCode.BIZ_ERROR,"创建失败！");
            return result;
        }
        return result;
	}
}

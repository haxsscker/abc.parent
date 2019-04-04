package com.autoserve.abc.service.biz.impl.user;

import com.autoserve.abc.dao.dataobject.UserOwnerDO;
import com.autoserve.abc.dao.intf.UserOwnerDao;
import com.autoserve.abc.service.biz.convert.UserOwnerConverter;
import com.autoserve.abc.service.biz.entity.UserOwner;
import com.autoserve.abc.service.biz.intf.user.UserOwnerService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/26 21:48.
 */
@Service
public class UserOwnerServiceImpl implements UserOwnerService {

    @Resource
    private UserOwnerDao userOwnerDao;

    @Override
    public PlainResult<UserOwner> findUserOwnerByUserId(int id) {
        UserOwnerDO userOwnerDO = userOwnerDao.findByUserId(id);
        PlainResult<UserOwner> result = new PlainResult<UserOwner>();
        if (null == userOwnerDO) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定信息!");
            return result;
        }
        result.setData(UserOwnerConverter.toUserOwner(userOwnerDO));
        return result;
    }

	@Override
	public BaseResult modifyUserOwner(UserOwner userOwner) {
        BaseResult result = new BaseResult();
        int val = userOwnerDao.update(UserOwnerConverter.toUserOwnerDO(userOwner));
        if(val <= 0){
            result.setErrorMessage(CommonResultCode.BIZ_ERROR,"更新失败！");
            return result;
        }
        return result;
	}

	@Override
	public BaseResult createUserOwner(UserOwner userOwner) {
        BaseResult result = new BaseResult();
        int val = userOwnerDao.insert(UserOwnerConverter.toUserOwnerDO(userOwner));
        if(val <= 0){
            result.setErrorMessage(CommonResultCode.BIZ_ERROR,"插入失败！");
            return result;
        }
        return result;
	}
}

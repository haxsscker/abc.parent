package com.autoserve.abc.service.biz.impl.user;

import com.autoserve.abc.dao.dataobject.UserContactDO;
import com.autoserve.abc.dao.intf.UserContactDao;
import com.autoserve.abc.service.biz.convert.UserContactConverter;
import com.autoserve.abc.service.biz.entity.UserContact;
import com.autoserve.abc.service.biz.intf.user.UserContactService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/26 10:01.
 */
@Service
public class UserContactServiceImpl implements UserContactService {
    @Resource
    private UserContactDao userContactDao;

    @Override
    public BaseResult modifyUserContact(UserContactDO userContactDO) {
        BaseResult result = new BaseResult();
        int val = userContactDao.updateByPrimaryKeySelective(userContactDO);
        if(val <= 0){
            result.setErrorMessage(CommonResultCode.BIZ_ERROR,"更新失败！");
            return result;
        }
        return result;
    }

    @Override
    public PlainResult<UserContact> findUserContactByUserId(int id) {
        UserContactDO userContactDO = userContactDao.findByUserId(id);
        PlainResult<UserContact> result = new PlainResult<UserContact>();
        if (null == userContactDO) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定信息!");
            return result;
        }
        result.setData(UserContactConverter.toUserHouse(userContactDO));
        return result;
    }

	@Override
	public BaseResult CreateUserContactByUserId(UserContactDO userContactDO) {
		BaseResult result = new BaseResult();
		int val = this.userContactDao.insert(userContactDO);
        if(val <= 0){
            result.setErrorMessage(CommonResultCode.BIZ_ERROR,"创建失败！");
            return result;
        }
		return result;
	}
}

package com.autoserve.abc.service.biz.impl.user;

import com.autoserve.abc.dao.dataobject.UserEducationDO;
import com.autoserve.abc.dao.intf.UserEducationDao;
import com.autoserve.abc.service.biz.convert.UserEducationConverter;
import com.autoserve.abc.service.biz.entity.UserEducation;
import com.autoserve.abc.service.biz.intf.user.UserEducationService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/26 21:52.
 */
@Service
public class UserEducationServiceImpl implements UserEducationService {
    @Resource
    private UserEducationDao userEducationDao;

    @Override
    public PlainResult<UserEducation> findUserEducationByUserId(int id) {
        UserEducationDO userEducationDO = userEducationDao.findByUserId(id);
        PlainResult<UserEducation> result = new PlainResult<UserEducation>();
        if (null == userEducationDO) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定信息!");
            return result;
        }
        result.setData(UserEducationConverter.toUserEducation(userEducationDO));
        return result;
    }

	@Override
	public BaseResult modifyUserEducation(UserEducation userEducation) {
		 BaseResult result = new BaseResult();
	        int val = userEducationDao.update(UserEducationConverter.toUserEducationDO(userEducation));
	        if(val <= 0){
	            result.setErrorMessage(CommonResultCode.BIZ_ERROR,"更新失败！");
	            return result;
	        }
	        return result;
	}

	@Override
	public BaseResult createUserEducation(UserEducation userEducation) {
		 BaseResult result = new BaseResult();
	        int val = userEducationDao.insert(UserEducationConverter.toUserEducationDO(userEducation));
	        if(val <= 0){
	            result.setErrorMessage(CommonResultCode.BIZ_ERROR,"创建失败！");
	            return result;
	        }
	        return result;
	}
}

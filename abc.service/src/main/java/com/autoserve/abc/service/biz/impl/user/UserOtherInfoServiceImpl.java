package com.autoserve.abc.service.biz.impl.user;

import com.autoserve.abc.dao.dataobject.UserOtherInfoDO;
import com.autoserve.abc.dao.intf.UserOtherInfoDao;
import com.autoserve.abc.service.biz.convert.UserOtherInfoConverter;
import com.autoserve.abc.service.biz.entity.UserOtherInfo;
import com.autoserve.abc.service.biz.intf.user.UserOtherInfoService;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/26 21:54.
 */
@Service
public class UserOtherInfoServiceImpl implements UserOtherInfoService {

    @Resource
    private UserOtherInfoDao userOtherInfoDao;

    @Override
    public PlainResult<UserOtherInfo> findUserOtherInfoByUserId(int id) {
        UserOtherInfoDO userOtherInfoDO = userOtherInfoDao.findByUserId(id);
        PlainResult<UserOtherInfo> result = new PlainResult<UserOtherInfo>();
        if (null == userOtherInfoDO) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定信息!");
            return result;
        }
        result.setData(UserOtherInfoConverter.toUserHouse(userOtherInfoDO));
        return result;
    }
}

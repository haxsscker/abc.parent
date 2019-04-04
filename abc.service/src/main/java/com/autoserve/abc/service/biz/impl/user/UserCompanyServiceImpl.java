package com.autoserve.abc.service.biz.impl.user;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.dataobject.UserCompanyDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.UserCompanyDao;
import com.autoserve.abc.dao.intf.UserDao;
import com.autoserve.abc.service.biz.convert.UserCompanyConverter;
import com.autoserve.abc.service.biz.entity.UserCompany;
import com.autoserve.abc.service.biz.intf.user.UserCompanyService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author RJQ 2014/12/26 10:06.
 */
@Service
public class UserCompanyServiceImpl implements UserCompanyService {
	@Resource
    private UserCompanyDao userCompanyDao;
    @Resource
    private UserDao userDao;
    
    @Override
    @Transactional
    public BaseResult modifyUserCompany(UserCompanyDO userCompanyDO, BigDecimal userMonthIncome) {
        BaseResult result = new BaseResult();
        int val = userCompanyDao.updateByPrimaryKeySelective(userCompanyDO);
        UserDO userDO = userDao.findById(userCompanyDO.getUcUserId());
        userDO.setUserMonthIncome(userMonthIncome);
        userDao.update(userDO);
        if (val <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "更新失败！");
            return result;
        }
        return result;
    }

    @Override
    public PlainResult<UserCompany> queryUserCompanyByUserId(int id) {
        PlainResult<UserCompany> result = new PlainResult<UserCompany>();

        UserCompanyDO userCompanyDO = userCompanyDao.findByUserId(id);
        if (null == userCompanyDO) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定信息!");
            return result;
        }

        result.setData(UserCompanyConverter.toUserCompany(userCompanyDO));
        return result;
    }

    @Override
    public ListResult<UserCompany> queryUserCompaniesByUserIds(List<Integer> ids) {
        ListResult<UserCompany> result = new ListResult<UserCompany>();
        List<UserCompanyDO> userCompanyDOs = userCompanyDao.findListByIds(ids);
        if (userCompanyDOs == null || userCompanyDOs.isEmpty()) {
            result.setData(new ArrayList<UserCompany>());
            return result;
        }
        result.setData(UserCompanyConverter.convertList(userCompanyDOs));
        return result;
    }

	@Override
	public BaseResult createUserHouse(UserCompanyDO userCompanyDO, BigDecimal userMonthIncome) {
        BaseResult result = new BaseResult();
        int val = userCompanyDao.insert(userCompanyDO);
        UserDO userDO = userDao.findById(userCompanyDO.getUcUserId());
        userDO.setUserMonthIncome(userMonthIncome);
        userDao.update(userDO);
        if (val <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "创建失败！");
            return result;
        }
        return result;
	}
}

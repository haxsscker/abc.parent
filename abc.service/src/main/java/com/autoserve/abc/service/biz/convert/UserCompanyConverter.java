package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.dao.dataobject.UserCompanyDO;
import com.autoserve.abc.service.biz.entity.UserCompany;
import com.autoserve.abc.service.biz.enums.CompanyType;
import com.autoserve.abc.service.biz.enums.IndustryType;
import com.autoserve.abc.service.biz.enums.WorkYear;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * @author yuqing.zheng Created on 2014-12-13,12:30
 */
public class UserCompanyConverter {
    public static UserCompany toUserCompany(UserCompanyDO userCompanyDO) {
        UserCompany userCompany = new UserCompany();

        userCompany.setId(userCompanyDO.getUcId());
        userCompany.setUserId(userCompanyDO.getUcUserId());
        userCompany.setCompanyName(userCompanyDO.getUcCompanyName());
        userCompany.setPosition(userCompanyDO.getUcPosition());
        userCompany.setLevel(userCompanyDO.getUcLevel());
        userCompany.setPhone(userCompanyDO.getUcPhone());
        userCompany.setWorkPeriod(userCompanyDO.getUcWorkPeriod());
        userCompany.setCompanySite(userCompanyDO.getUcCompanySite());
        userCompany.setCompanyAddress(userCompanyDO.getUcCompanyAddress());
        userCompany.setNote(userCompanyDO.getUcNote());
        Integer industry = userCompanyDO.getUcCompanyIndustry();
        userCompany.setCompanyIndustry(IndustryType.valueOf(industry));
        Integer comType = userCompanyDO.getUcCompanyType();
        userCompany.setCompanyType(CompanyType.valueOf(comType));
        Integer workYear = userCompanyDO.getUcWorkYear();
        userCompany.setWorkYear(WorkYear.valueOf(workYear));
        
        return userCompany;
    }

    public static List<UserCompany> convertList(List<UserCompanyDO> list) {
        if (list == null || list.isEmpty())
            throw new BusinessException("传入的list为空");
        List<UserCompany> result = new ArrayList<UserCompany>();
        for (UserCompanyDO edo : list) {
            result.add(toUserCompany(edo));
        }
        return result;
    }
}

package com.autoserve.abc.web.convert;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import com.autoserve.abc.service.biz.entity.UserCompany;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.web.vo.user.UserCompanyVO;

public class UserCompanyVOConverter {
    public static UserCompanyVO convertToUserCompanyVO(UserCompany userCompany) {

        UserCompanyVO vo = new UserCompanyVO();
        BeanCopier beanCopier = BeanCopier.create(UserCompany.class, UserCompanyVO.class, false);
        beanCopier.copy(userCompany, vo, null);
        return vo;
    }

    /**
     * @param userCompany
     * @return
     */
    public static List<UserCompanyVO> convertToList(List<UserCompany> userCompanys) {
        if (userCompanys == null || userCompanys.isEmpty())
            throw new BusinessException("传入的list为空");
        List<UserCompanyVO> result = new ArrayList<UserCompanyVO>();

        for (UserCompany userCompany : userCompanys) {
            result.add(convertToUserCompanyVO(userCompany));
        }
        return result;
    }
}

package com.autoserve.abc.web.convert;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import org.joda.time.DateTime;

import com.autoserve.abc.service.biz.entity.UserHouse;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.user.UserHouseVO;

public class UserHouseVOConverter {
    public static UserHouseVO convertToUserHouseVO(UserHouse userHouse) {

        UserHouseVO vo = new UserHouseVO();
        BeanCopier beanCopier = BeanCopier.create(UserHouse.class, UserHouseVO.class, false);
        beanCopier.copy(userHouse, vo, null);
        vo.setUhDate(new DateTime(userHouse.getUhDate()).toString(DateUtil.DATE_FORMAT));
        return vo;
    }

    /**
     * @param userHouse
     * @return
     */
    public static List<UserHouseVO> convertToList(List<UserHouse> userhouses) {
        if (userhouses == null || userhouses.isEmpty())
            throw new BusinessException("传入的list为空");
        List<UserHouseVO> result = new ArrayList<UserHouseVO>();

        for (UserHouse userHouse : userhouses) {
            result.add(convertToUserHouseVO(userHouse));
        }
        return result;
    }
}

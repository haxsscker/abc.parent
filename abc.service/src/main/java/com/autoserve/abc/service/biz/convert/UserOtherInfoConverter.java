package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.UserOtherInfoDO;
import com.autoserve.abc.service.biz.entity.UserOtherInfo;
import net.sf.cglib.beans.BeanCopier;

/**
 * @author RJQ 2014/12/13 13:53.
 */
public class UserOtherInfoConverter {
    public static UserOtherInfo toUserHouse(UserOtherInfoDO userOtherInfoDO) {
        UserOtherInfo userOtherInfo = new UserOtherInfo();
        BeanCopier beanCopier = BeanCopier.create(UserOtherInfoDO.class, UserOtherInfo.class, false);
        beanCopier.copy(userOtherInfoDO, userOtherInfo, null);

        return userOtherInfo;
    }
}

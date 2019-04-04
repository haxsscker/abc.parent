package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.UserHouseDO;
import com.autoserve.abc.service.biz.entity.UserHouse;
import net.sf.cglib.beans.BeanCopier;

/**
 * @author RJQ 2014/12/13 13:53.
 */
public class UserHouseConverter {
    public static UserHouse toUserHouse(UserHouseDO userHouseDO){
        UserHouse userHouse = new UserHouse();
        BeanCopier beanCopier = BeanCopier.create(UserHouseDO.class, UserHouse.class, false);
        beanCopier.copy(userHouseDO, userHouse, null);

        return userHouse;
    }
    
    public static UserHouseDO toUserHouseDO(UserHouse userHouse){
        UserHouseDO userHouseDO = new UserHouseDO();
        BeanCopier beanCopier = BeanCopier.create(UserHouse.class, UserHouseDO.class, false);
        beanCopier.copy(userHouse, userHouseDO, null);

        return userHouseDO;
    }
}

package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.UserSpouseDO;
import com.autoserve.abc.service.biz.entity.UserSpouse;
import net.sf.cglib.beans.BeanCopier;

/**
 * @author RJQ 2014/12/13 14:02.
 */
public class UserSpouseConverter {
    public static UserSpouse toUserSpouse(UserSpouseDO userSpouseDO) {
        UserSpouse userSpouse = new UserSpouse();
        BeanCopier beanCopier = BeanCopier.create(UserSpouseDO.class, UserSpouse.class, false);
        beanCopier.copy(userSpouseDO, userSpouse, null);

        return userSpouse;
    }

	public static UserSpouseDO toUserSpouseDO(UserSpouse userSpouse) {
		UserSpouseDO userSpouseDO = new UserSpouseDO();
	        BeanCopier beanCopier = BeanCopier.create(UserSpouse.class, UserSpouseDO.class, false);
	        beanCopier.copy(userSpouse, userSpouseDO, null);

	        return userSpouseDO;
	}
}

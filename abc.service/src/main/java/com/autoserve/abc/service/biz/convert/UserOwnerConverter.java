package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.UserOwnerDO;
import com.autoserve.abc.service.biz.entity.UserOwner;
import net.sf.cglib.beans.BeanCopier;

/**
 * @author RJQ 2014/12/13 13:57.
 */
public class UserOwnerConverter {
    public static UserOwner toUserOwner(UserOwnerDO userOwnerDO) {
        UserOwner userOwner = new UserOwner();
        BeanCopier beanCopier = BeanCopier.create(UserOwnerDO.class, UserOwner.class, false);
        beanCopier.copy(userOwnerDO, userOwner, null);
        return userOwner;
    }

	public static UserOwnerDO toUserOwnerDO(UserOwner userOwner) {
		UserOwnerDO userOwnerDO = new UserOwnerDO();
        BeanCopier beanCopier = BeanCopier.create(UserOwner.class, UserOwnerDO.class, false);
        beanCopier.copy(userOwner, userOwnerDO, null);
        return userOwnerDO;
	}
}

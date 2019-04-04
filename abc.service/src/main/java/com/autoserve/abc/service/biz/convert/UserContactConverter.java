package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.UserContactDO;
import com.autoserve.abc.service.biz.entity.UserContact;
import net.sf.cglib.beans.BeanCopier;

/**
 * @author RJQ 2014/12/13 13:53.
 */
public class UserContactConverter {
    public static UserContact toUserHouse(UserContactDO userContactDO) {
        UserContact userContact = new UserContact();
        BeanCopier beanCopier = BeanCopier.create(UserContactDO.class, UserContact.class, false);
        beanCopier.copy(userContactDO, userContact, null);

        return userContact;
    }
}

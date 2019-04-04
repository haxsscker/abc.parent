package com.autoserve.abc.web.convert;

import net.sf.cglib.beans.BeanCopier;

import com.autoserve.abc.service.biz.entity.UserContact;
import com.autoserve.abc.web.vo.user.UserContactVO;

/**
 * 类UserContactVOConverter.java的实现描述：TODO 类实现描述
 * 
 * @author fangrui 2014年12月18日 上午10:24:39
 */
public class UserContactVOConverter {
    public static UserContactVO convertToUserContactVO(UserContact userContact) {
        UserContactVO userContactVO = new UserContactVO();
        BeanCopier beanCopier = BeanCopier.create(UserContact.class, UserContactVO.class, false);
        beanCopier.copy(userContact, userContactVO, null);

        return userContactVO;
    }
}

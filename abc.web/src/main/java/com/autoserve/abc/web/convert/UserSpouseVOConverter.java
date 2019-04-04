package com.autoserve.abc.web.convert;

import net.sf.cglib.beans.BeanCopier;

import com.autoserve.abc.service.biz.entity.UserSpouse;
import com.autoserve.abc.web.vo.user.UserSpouseVO;

/**
 * 类UserSpouseVOConverter.java的实现描述：TODO 类实现描述
 * 
 * @author fangrui 2014年12月17日 下午7:13:02
 */
public class UserSpouseVOConverter {
    public static UserSpouseVO convertToUserSpouseVO(UserSpouse userSpouse) {
        UserSpouseVO userSpouseVO = new UserSpouseVO();
        BeanCopier beanCopier = BeanCopier.create(UserSpouse.class, UserSpouseVO.class, false);
        beanCopier.copy(userSpouse, userSpouseVO, null);

        return userSpouseVO;
    }
}

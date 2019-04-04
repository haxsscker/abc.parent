package com.autoserve.abc.web.convert;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import org.joda.time.DateTime;

import com.autoserve.abc.service.biz.entity.UserOwner;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.user.UserOwnerVO;

/**
 * 类UserOwnerVOConverter.java的实现描述：TODO 类实现描述
 * 
 * @author dongxuejiao 2014年12月18日 下午1:09:14
 */
/** 私营业主资料 */
public class UserOwnerVOConverter {
    public static UserOwnerVO convertToUserOwnerVO(UserOwner userOwner) {
        UserOwnerVO vo = new UserOwnerVO();
        BeanCopier beanCopier = BeanCopier.create(UserOwner.class, UserOwnerVO.class, false);
        beanCopier.copy(userOwner, vo, null);
        vo.setUoDate(new DateTime(userOwner.getUoDate()).toString(DateUtil.DATE_FORMAT));
        return vo;
    }

    public static List<UserOwnerVO> convertToList(List<UserOwner> userOwners) {
        if (userOwners == null || userOwners.isEmpty())
            throw new BusinessException("传入的list为空");
        List<UserOwnerVO> result = new ArrayList<UserOwnerVO>();
        for (UserOwner userOwner : userOwners) {
            result.add(convertToUserOwnerVO(userOwner));
        }
        return result;
    }
}

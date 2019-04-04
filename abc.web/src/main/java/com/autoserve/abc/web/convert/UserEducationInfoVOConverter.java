package com.autoserve.abc.web.convert;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import org.joda.time.DateTime;

import com.autoserve.abc.service.biz.entity.UserEducation;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.user.UserEducationInfoVO;

/**
 * 类UserEducationInfoVOConverter.java的实现描述：TODO 类实现描述
 * 
 * @author lipeng 2014年12月18日 上午10:13:06
 */
public class UserEducationInfoVOConverter {

    public static UserEducationInfoVO convertToUserEducationInfoVO(UserEducation userEducation) {

        UserEducationInfoVO vo = new UserEducationInfoVO();
        BeanCopier beanCopier = BeanCopier.create(UserEducation.class, UserEducationInfoVO.class, false);
        beanCopier.copy(userEducation, vo, null);
        vo.setUeStartTime(new DateTime(userEducation.getUeStartTime()).toString(DateUtil.DATE_FORMAT));
        vo.setUeEndTime(new DateTime(userEducation.getUeEndTime()).toString(DateUtil.DATE_FORMAT));

        return vo;
    }

    /**
     * @param userEducations
     * @return
     */
    public static List<UserEducationInfoVO> convertToList(List<UserEducation> userEducations) {
        if (userEducations == null || userEducations.isEmpty())
            throw new BusinessException("传入的list为空");
        List<UserEducationInfoVO> result = new ArrayList<UserEducationInfoVO>();

        for (UserEducation userEducation : userEducations) {
            result.add(convertToUserEducationInfoVO(userEducation));
        }
        return result;
    }

}

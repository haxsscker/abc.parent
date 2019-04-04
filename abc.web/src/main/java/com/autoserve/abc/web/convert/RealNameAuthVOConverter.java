package com.autoserve.abc.web.convert;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import org.joda.time.DateTime;

import com.autoserve.abc.service.biz.entity.RealnameAuth;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.user.RealnameAuthVO;

/**
 * 类RealNameAuthVOConverter.java的实现描述：TODO 类实现描述
 * 
 * @author fangrui 2014年12月23日 下午6:17:31
 */
public class RealNameAuthVOConverter {
    public static RealnameAuthVO convertToRealNameAuthVO(RealnameAuth realnameAuth) {
        RealnameAuthVO realnameAuthVO = new RealnameAuthVO();
        BeanCopier beanCopier = BeanCopier.create(RealnameAuth.class, RealnameAuthVO.class, false);
        beanCopier.copy(realnameAuth, realnameAuthVO, null);
        realnameAuthVO.setRnpBirthday(new DateTime(realnameAuth.getRnpBirthday()).toString(DateUtil.DATE_FORMAT));
        realnameAuthVO.setRnpApplyDate(new DateTime(realnameAuth.getRnpApplyDate()).toString(DateUtil.DATE_FORMAT));
        realnameAuthVO.setRnpReviewDate(new DateTime(realnameAuth.getRnpReviewDate()).toString(DateUtil.DATE_FORMAT));
        return realnameAuthVO;
    }

    public static List<RealnameAuthVO> convertToList(List<RealnameAuth> users) {
        if (users == null || users.isEmpty())
            throw new BusinessException("传入的list为空");
        List<RealnameAuthVO> result = new ArrayList<RealnameAuthVO>();
        for (RealnameAuth user : users) {
            result.add(convertToRealNameAuthVO(user));
        }
        return result;
    }
}

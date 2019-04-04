package com.autoserve.abc.web.convert;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import org.joda.time.DateTime;

import com.autoserve.abc.service.biz.entity.RealnameAuthJ;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.user.RealnameAuthJVO;

/**
 * 类RealNameAuthJVOConverter.java的实现描述：TODO 类实现描述
 * 
 * @author fangrui 2014年12月23日 下午6:17:22
 */
public class RealNameAuthJVOConverter {
    public static RealnameAuthJVO convertToRealNameAuthJVO(RealnameAuthJ realnameAuthJ) {
        RealnameAuthJVO realnameAuthJVO = new RealnameAuthJVO();
        BeanCopier beanCopier = BeanCopier.create(RealnameAuthJ.class, RealnameAuthJVO.class, false);
        beanCopier.copy(realnameAuthJ, realnameAuthJVO, null);
        realnameAuthJVO.setRnpBirthday(new DateTime(realnameAuthJ.getRnpBirthday()).toString(DateUtil.DATE_FORMAT));
        realnameAuthJVO.setRnpApplyDate(new DateTime(realnameAuthJ.getRnpApplyDate()).toString(DateUtil.DATE_FORMAT));
        realnameAuthJVO.setRnpReviewDate(new DateTime(realnameAuthJ.getRnpReviewDate()).toString(DateUtil.DATE_FORMAT));
        return realnameAuthJVO;
    }

    public static List<RealnameAuthJVO> convertToList(List<RealnameAuthJ> users) {
        if (users == null || users.isEmpty())
            throw new BusinessException("传入的list为空");
        List<RealnameAuthJVO> result = new ArrayList<RealnameAuthJVO>();
        for (RealnameAuthJ user : users) {
            result.add(convertToRealNameAuthJVO(user));
        }
        return result;
    }
}

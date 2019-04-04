package com.autoserve.abc.web.convert;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import com.autoserve.abc.service.biz.entity.UserOtherInfo;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.web.vo.user.UserOtherInfoVO;

/**
 * 类UserOtherInfoVOConverter.java的实现描述：TODO 类实现描述
 * 
 * @author lipeng 2014年12月17日 下午7:14:12
 */
public class UserOtherInfoVOConverter {
    public static UserOtherInfoVO convertToUserOtherInfoVO(UserOtherInfo userOtherInfo) {

        UserOtherInfoVO vo = new UserOtherInfoVO();
        BeanCopier beanCopier = BeanCopier.create(UserOtherInfo.class, UserOtherInfoVO.class, false);
        beanCopier.copy(userOtherInfo, vo, null);

        return vo;
    }

    /**
     * @param userOtherInfos
     * @return
     */
    public static List<UserOtherInfoVO> convertToList(List<UserOtherInfo> userOtherInfos) {
        if (userOtherInfos == null || userOtherInfos.isEmpty())
            throw new BusinessException("传入的list为空");
        List<UserOtherInfoVO> result = new ArrayList<UserOtherInfoVO>();

        for (UserOtherInfo userOtherInfo : userOtherInfos) {
            result.add(convertToUserOtherInfoVO(userOtherInfo));
        }
        return result;
    }
}

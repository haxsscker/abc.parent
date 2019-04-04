package com.autoserve.abc.web.convert;

import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.user.UserVO;
import net.sf.cglib.beans.BeanCopier;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RJQ 2014/12/16 15:51.
 */
public class UserVOConverter {
    public static UserVO convertToUserVO(User user) {
        UserVO vo = new UserVO();
        BeanCopier beanCopier = BeanCopier.create(User.class, UserVO.class, false);
        beanCopier.copy(user, vo, null);
        if(null !=user.getUserBirthday() && !"".equals(user.getUserBirthday()))
        {
          vo.setUserBirthday(new DateTime(user.getUserBirthday()).toString(DateUtil.DATE_FORMAT));
        }
        vo.setUserScoreLastmodifytime(new DateTime(user.getUserScoreLastmodifytime()).toString(DateUtil.DATE_FORMAT));
        vo.setUserRegisterDate(new DateTime(user.getUserRegisterDate()).toString(DateUtil.DATE_FORMAT));
        vo.setUserMobileVerifyDate(new DateTime(user.getUserMobileVerifyDate()).toString(DateUtil.DATE_FORMAT));
        vo.setUserType(user.getUserType() == null ? null : user.getUserType().getDes());
        vo.setUserSex(user.getUserSex() == null ? null : user.getUserSex().getDes());
        vo.setUserState(user.getUserState() == null ? null : user.getUserState().getDes());
        vo.setUserMarry(user.getUserMarry() == null ? null : user.getUserMarry().getDes());
        vo.setUserMobileIsbinded(user.getUserMobileIsbinded() == null ? null : user.getUserMobileIsbinded().getDes());
        vo.setUserEmailIsbinded(user.getUserEmailIsbinded() == null ? null : user.getUserEmailIsbinded().getDes());

        return vo;
    }

    public static List<UserVO> convertToList(List<User> users) {
        if (users == null || users.isEmpty())
            throw new BusinessException("传入的list为空");
        List<UserVO> result = new ArrayList<UserVO>();
        for (User user : users) {
            result.add(convertToUserVO(user));
        }
        return result;
    }
}

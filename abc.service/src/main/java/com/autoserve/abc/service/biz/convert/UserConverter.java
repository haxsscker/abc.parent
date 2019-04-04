package com.autoserve.abc.service.biz.convert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.EmailState;
import com.autoserve.abc.service.biz.enums.EntityState;
import com.autoserve.abc.service.biz.enums.MaritalStatus;
import com.autoserve.abc.service.biz.enums.MobileState;
import com.autoserve.abc.service.biz.enums.SexType;
import com.autoserve.abc.service.biz.enums.UserAuthorizeFlag;
import com.autoserve.abc.service.biz.enums.UserBusinessState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * @author RJQ 2014/12/3 14:06.
 */
public class UserConverter {
    public static User toUser(UserDO userDO) {
        User user = new User();
        BeanCopier beanCopier = BeanCopier.create(UserDO.class, User.class, false);
        beanCopier.copy(userDO, user, null);
        if (userDO.getUserMarry() != null) {
            user.setUserMarry(MaritalStatus.valueOf(userDO.getUserMarry()));
        }
        if (userDO.getUserState() != null) {
            user.setUserState(EntityState.valueOf(userDO.getUserState()));
        }
        if (userDO.getUserSex() != null) {
            user.setUserSex(SexType.valueOf(userDO.getUserSex()));
        }
        if (userDO.getUserType() != null) {
            user.setUserType(UserType.valueOf(userDO.getUserType()));
        }
        if (userDO.getUserMobileIsbinded() != null) {
            user.setUserMobileIsbinded(MobileState.valueOf(userDO.getUserMobileIsbinded()));
        }
        if (userDO.getUserEmailIsbinded() != null) {
            user.setUserEmailIsbinded(EmailState.valueOf(userDO.getUserEmailIsbinded()));
        }
        if (userDO.getUserBusinessState() != null) {
            user.setUserBusinessState(UserBusinessState.valueOf(userDO.getUserBusinessState()));
        }
        if (userDO.getUserAuthorizeFlag() != null) {
            user.setUserAuthorizeFlag(UserAuthorizeFlag.valueOf(userDO.getUserAuthorizeFlag()));
        }
        if (userDO.getAuthorizeInvestAmount() == null) {
            user.setAuthorizeInvestAmount(BigDecimal.ZERO);
        }
        if (userDO.getAuthorizeFeeAmount() == null) {
            user.setAuthorizeFeeAmount(BigDecimal.ZERO);
        }
        if (userDO.getAuthorizeRepayAmount() == null) {
            user.setAuthorizeRepayAmount(BigDecimal.ZERO);
        }
        user.setUserHeadImg(userDO.getUserHeadImg());
        user.setUserRealnameIsproven(userDO.getUserRealnameIsproven());
        user.setAssId(userDO.getAssId());
        user.setAssTime(userDO.getAssTime());
        return user;
    }

    public static List<User> convertList(List<UserDO> list) {
        if (list == null || list.isEmpty())
            throw new BusinessException("传入的list为空");
        List<User> result = new ArrayList<User>();
        for (UserDO udo : list) {
            result.add(toUser(udo));
        }
        return result;
    }

    public static UserDO toUserDO(User user) {
        UserDO userDO = new UserDO();
        BeanCopier beanCopier = BeanCopier.create(User.class, UserDO.class, false);
        beanCopier.copy(user, userDO, null);
        if (user.getUserBusinessState() != null)
            userDO.setUserBusinessState(user.getUserBusinessState().getState());
        if (user.getUserState() != null) {
            userDO.setUserState(user.getUserState().getState());
        }
        if (user.getUserSex() != null) {
            userDO.setUserSex(user.getUserSex().getSex());
        }
        if (user.getUserType() != null) {
            userDO.setUserType(user.getUserType().getType());
        }
        if (user.getUserMobileIsbinded() != null) {
            userDO.setUserMobileIsbinded(user.getUserMobileIsbinded().getState());
        }
        if (user.getUserEmailIsbinded() != null) {
            userDO.setUserEmailIsbinded(user.getUserEmailIsbinded().getState());
        }
        if (user.getUserBusinessState() != null) {
            userDO.setUserBusinessState(user.getUserBusinessState().getState());
        }
        if (user.getUserAuthorizeFlag() != null)
            userDO.setUserAuthorizeFlag(user.getUserAuthorizeFlag().getState());
  
        return userDO;
    }
}

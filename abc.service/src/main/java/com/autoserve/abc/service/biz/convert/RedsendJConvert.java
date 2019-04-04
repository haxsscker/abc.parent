package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import com.autoserve.abc.dao.dataobject.RedsendJDO;
import com.autoserve.abc.service.biz.entity.RedsendJ;
import com.autoserve.abc.service.biz.enums.BankcardState;
import com.autoserve.abc.service.biz.enums.EmailState;
import com.autoserve.abc.service.biz.enums.EntityState;
import com.autoserve.abc.service.biz.enums.MobileState;
import com.autoserve.abc.service.biz.enums.RealnameState;
import com.autoserve.abc.service.biz.enums.RedenvelopeType;
import com.autoserve.abc.service.biz.enums.RsState;
import com.autoserve.abc.service.biz.enums.UserType;

/**
 * 类RedsendJConvert.java的实现描述：TODO 类实现描述
 * 
 * @author lipeng 2014年12月28日 下午1:53:14
 */
public class RedsendJConvert {
    public static RedsendJ toRedsendJ(RedsendJDO redsendJDO) {
        RedsendJ redsendJ = new RedsendJ();
        BeanCopier beanCopier = BeanCopier.create(RedsendJDO.class, RedsendJ.class, false);
        beanCopier.copy(redsendJDO, redsendJ, null);
        redsendJ.setRsType(RedenvelopeType.valueOf(redsendJDO.getRsType()));
        redsendJ.setRsState(RsState.valueOf(redsendJDO.getRsState()));
        redsendJ.setUserType(UserType.valueOf(redsendJDO.getUserType()));
        redsendJ.setUserState(EntityState.valueOf(redsendJDO.getUserState()));
        redsendJ.setRedType(RedenvelopeType.valueOf(redsendJDO.getRedType()));
        redsendJ.setUserRealnameIsproven(RealnameState.valueOf(redsendJDO.getUserRealnameIsproven()));
        redsendJ.setUserMobileIsbinded(MobileState.valueOf(redsendJDO.getUserMobileIsbinded()));
        redsendJ.setUserEmailIsbinded(EmailState.valueOf(redsendJDO.getUserEmailIsbinded()));
        redsendJ.setUserBankcardIsbinded(BankcardState.valueOf(redsendJDO.getUserBankcardIsbinded()));

        return redsendJ;
    }

    public static RedsendJDO toRedsendJDO(RedsendJ redsendJ) {
        if (redsendJ == null) {
            return null;
        }
        RedsendJDO redsendJDO = new RedsendJDO();
        redsendJDO.setRsId(redsendJ.getRsId());
        redsendJDO.setRsRedId(redsendJ.getRsRedId());
        redsendJDO.setRsAmt(redsendJ.getRsAmt());
        redsendJDO.setRsTheme(redsendJ.getRsTheme());
        redsendJDO.setRsValidAmount(redsendJ.getRsValidAmount());
        if (redsendJ.getRsType() == null) {
            redsendJDO.setRsType(null);
        } else {
            redsendJDO.setRsType(redsendJ.getRsType().type);
        }
        redsendJDO.setRsUserid(redsendJ.getRsUserid());
        redsendJDO.setRsBizid(redsendJ.getRsBizid());
        redsendJDO.setRsUseScope(redsendJ.getRsUseScope());
        redsendJDO.setRsLifetime(redsendJ.getRsLifetime());
        redsendJDO.setRsInvestAmount(redsendJ.getRsInvestAmount());
        redsendJDO.setRsStarttime(redsendJ.getRsStarttime());
        redsendJDO.setRsClosetime(redsendJ.getRsClosetime());
        redsendJDO.setRsSendtime(redsendJ.getRsSendtime());
        redsendJDO.setRsSender(redsendJ.getRsSender());
        if (redsendJ.getRsState() == null) {
            redsendJDO.setRsState(null);
        } else {
            redsendJDO.setRsState(redsendJ.getRsState().state);
        }
        redsendJDO.setUserName(redsendJ.getUserName());
        redsendJDO.setRedName(redsendJ.getRedName());
        if (redsendJ.getRedType() == null) {
            redsendJDO.setRedType(null);
        } else {
            redsendJDO.setRedType(redsendJ.getRedType().type);
        }
        redsendJDO.setRedAmount(redsendJ.getRedAmount());
        if (redsendJ.getUserType() == null) {
            redsendJDO.setUserType(null);
        } else {
            redsendJDO.setUserType(redsendJ.getUserType().type);
        }
        if (redsendJ.getUserState() == null) {
            redsendJDO.setUserState(null);
        } else {
            redsendJDO.setUserState(redsendJ.getUserState().state);
        }
        if (redsendJ.getRedState() == null) {
            redsendJDO.setRedState(null);
        } else {
            redsendJDO.setRedState(redsendJ.getRedState().state);
        }
        if (redsendJ.getUserRealnameIsproven() == null) {
            redsendJDO.setUserRealnameIsproven(null);
        } else {
            redsendJDO.setUserRealnameIsproven(redsendJ.getUserRealnameIsproven().state);
        }
        if (redsendJ.getUserMobileIsbinded() == null) {
            redsendJDO.setUserMobileIsbinded(null);
        } else {
            redsendJDO.setUserMobileIsbinded(redsendJ.getUserMobileIsbinded().state);
        }
        if (redsendJ.getUserEmailIsbinded() == null) {
            redsendJDO.setUserEmailIsbinded(null);
        } else {
            redsendJDO.setUserEmailIsbinded(redsendJ.getUserEmailIsbinded().state);
        }
        if (redsendJ.getUserBankcardIsbinded() == null) {
            redsendJDO.setUserBankcardIsbinded(null);
        } else {
            redsendJDO.setUserBankcardIsbinded(redsendJ.getUserBankcardIsbinded().state);
        }
        redsendJDO.setRuUsetime(redsendJ.getRuUsetime());
        redsendJDO.setRuAmount(redsendJ.getRuAmount());
        redsendJDO.setRuDeductDiscount(redsendJ.getRuDeductDiscount());
        redsendJDO.setRuDeductFee(redsendJ.getRuDeductFee());
        redsendJDO.setRuDesc(redsendJ.getRuDesc());
        redsendJDO.setRuRemainderAmount(redsendJ.getRuRemainderAmount());
        redsendJDO.setRuUsecount(redsendJ.getRuUsecount());
        redsendJDO.setTotalAmount(redsendJ.getTotalAmount());
        redsendJDO.setRedRewardNumber(redsendJ.getRedRewardNumber());

        return redsendJDO;
    }

    public static List<RedsendJ> toRedsendList(List<RedsendJDO> redsendJDOList) {
        if (redsendJDOList == null) {
            return null;
        }
        List<RedsendJ> RedsendJList = new ArrayList<RedsendJ>(redsendJDOList.size());
        for (RedsendJDO redsendJDO : redsendJDOList) {
            RedsendJList.add(toRedsendJ(redsendJDO));
        }
        return RedsendJList;
    }
}

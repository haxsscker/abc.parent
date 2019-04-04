package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import com.autoserve.abc.dao.dataobject.RedsendDO;
import com.autoserve.abc.service.biz.entity.Redsend;
import com.autoserve.abc.service.biz.enums.RedenvelopeType;
import com.autoserve.abc.service.biz.enums.RsState;

/**
 * 类RedsendConvert.java的实现描述：redsend 表dao层实体和service层实体的convert
 * 
 * @author lipeng 2014年12月26日 下午8:48:38
 */
public class RedsendConverter {
    public static Redsend toRedsend(RedsendDO redsendDO) {
        Redsend redsend = new Redsend();
        BeanCopier beanCopier = BeanCopier.create(RedsendDO.class, Redsend.class, false);
        beanCopier.copy(redsendDO, redsend, null);
        redsend.setRsType(RedenvelopeType.valueOf(redsendDO.getRsType()));
        redsend.setRsState(RsState.valueOf(redsendDO.getRsState()));

        return redsend;
    }

    public static RedsendDO toRedsendDO(Redsend redsend) {
        if (redsend == null) {
            return null;
        }
        RedsendDO redsendDO = new RedsendDO();
        redsendDO.setRsId(redsend.getRsId());
        redsendDO.setRsRedId(redsend.getRsRedId());
        redsendDO.setRsAmt(redsend.getRsAmt());
        redsendDO.setRsTheme(redsend.getRsTheme());
        redsendDO.setRsValidAmount(redsend.getRsValidAmount());
        if (redsend.getRsType() == null) {
            redsendDO.setRsType(null);
        } else {
            redsendDO.setRsType(redsend.getRsType().type);
        }
        redsendDO.setRsUserid(redsend.getRsUserid());
        redsendDO.setRsBizid(redsend.getRsBizid());
        redsendDO.setRsUseScope(redsend.getRsUseScope());
        redsendDO.setRsLifetime(redsend.getRsLifetime());
        redsendDO.setRsInvestAmount(redsend.getRsInvestAmount());
        redsendDO.setRsStarttime(redsend.getRsStarttime());
        redsendDO.setRsClosetime(redsend.getRsClosetime());
        redsendDO.setRsSendtime(redsend.getRsSendtime());
        redsendDO.setRsSender(redsend.getRsSender());
        if (redsend.getRsState() == null) {
            redsendDO.setRsState(null);
        } else {
            redsendDO.setRsState(redsend.getRsState().state);
        }
        return redsendDO;
    }

    public static List<Redsend> toRedsendList(List<RedsendDO> redsendDOList) {
        if (redsendDOList == null) {
            return null;
        }
        List<Redsend> RedsendList = new ArrayList<Redsend>(redsendDOList.size());
        for (RedsendDO redsendDO : redsendDOList) {
            RedsendList.add(toRedsend(redsendDO));
        }
        return RedsendList;
    }

    public static List<RedsendDO> toRedsendDOList(List<Redsend> redsendList) {
        if (redsendList == null) {
            return null;
        }
        List<RedsendDO> RedsendDOList = new ArrayList<RedsendDO>(redsendList.size());
        for (Redsend redsend : redsendList) {
            RedsendDOList.add(toRedsendDO(redsend));
        }
        return RedsendDOList;
    }
}

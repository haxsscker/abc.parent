package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.MonBankInfoDO;
import com.autoserve.abc.service.biz.entity.MonBankInfo;

/**
 * 类MonBankInfoConverter.java的实现描述：TODO 类实现描述
 * 
 * @author dengjingyu 2014年12月23日 下午2:24:15
 */
public class MonBankInfoConverter {
    public static MonBankInfoDO toMonBankInfoDO(MonBankInfo monBankInfo) {
        MonBankInfoDO monBankInfoDO = new MonBankInfoDO();
        monBankInfoDO.setMonBankId(monBankInfo.getMonBankId());
        monBankInfoDO.setFunFundName(monBankInfo.getFunFundName());
        monBankInfoDO.setMonBankCard(monBankInfo.getMonBankCard());
        monBankInfoDO.setMonBankName(monBankInfo.getMonBankName());
        monBankInfoDO.setMonFundId(monBankInfo.getMonFundId());
        monBankInfoDO.setMonUserNamer(monBankInfo.getMonUserNamer());
        return monBankInfoDO;
    }

    public static MonBankInfo toMonBankInfo(MonBankInfoDO monBankInfoDO) {
        MonBankInfo monBankInfo = new MonBankInfo();
        monBankInfo.setMonBankId(monBankInfoDO.getMonBankId());
        monBankInfo.setFunFundName(monBankInfoDO.getFunFundName());
        monBankInfo.setMonBankCard(monBankInfoDO.getMonBankCard());
        monBankInfo.setMonBankName(monBankInfoDO.getMonBankName());
        monBankInfo.setMonFundId(monBankInfoDO.getMonFundId());
        monBankInfo.setMonUserNamer(monBankInfoDO.getMonUserNamer());
        return monBankInfo;

    }

}

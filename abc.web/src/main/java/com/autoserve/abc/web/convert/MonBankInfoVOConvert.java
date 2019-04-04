package com.autoserve.abc.web.convert;

import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.dao.dataobject.MonBankInfoDO;
import com.autoserve.abc.service.biz.entity.MonBankInfo;
import com.autoserve.abc.web.vo.bank.MonBankInfoVO;

/**
 * 类MonBankInfoVOConvert.java的实现描述：TODO 类实现描述
 * 
 * @author dengjingyu 2014年12月24日 上午11:12:29
 */
public class MonBankInfoVOConvert {
    public static MonBankInfo toMonBankInfo(MonBankInfoVO monBankInfoVO) {
        MonBankInfo bankInfo = new MonBankInfo();
        bankInfo.setMonBankId(monBankInfoVO.getMonBankId());
        bankInfo.setMonBankName(monBankInfoVO.getMonBankName());
        bankInfo.setFunFundName(monBankInfoVO.getFunFundName());
        bankInfo.setMonBankCard(monBankInfoVO.getMonBankCard());
        bankInfo.setMonFundId(monBankInfoVO.getMonFundId());
        bankInfo.setMonUserNamer(monBankInfoVO.getMonUserNamer());
        return bankInfo;
    }

    public static MonBankInfoVO toMonBankInfoVO(MonBankInfo monBankInfo) {
        MonBankInfoVO monBankInfoVO = new MonBankInfoVO();
        monBankInfoVO.setMonBankId(monBankInfo.getMonBankId());
        monBankInfoVO.setMonBankName(monBankInfo.getMonBankName());
        monBankInfoVO.setFunFundName(monBankInfo.getFunFundName());
        monBankInfoVO.setMonBankCard(monBankInfo.getMonBankCard());
        monBankInfoVO.setMonFundId(monBankInfo.getMonFundId());
        monBankInfoVO.setMonUserNamer(monBankInfo.getMonUserNamer());

        return monBankInfoVO;
    }

    /*
     * 有限合伙人DO转VO
     */
    public static MonBankInfoVO toMonBankInfoVO(MonBankInfoDO monBankInfoDO) {
        MonBankInfoVO monBankInfoVO = new MonBankInfoVO();
        monBankInfoVO.setMonBankId(monBankInfoDO.getMonBankId());
        monBankInfoVO.setMonBankName(monBankInfoDO.getMonBankName());
        monBankInfoVO.setFunFundName(monBankInfoDO.getFunFundName());
        monBankInfoVO.setMonBankCard(monBankInfoDO.getMonBankCard());
        monBankInfoVO.setMonFundId(monBankInfoDO.getMonFundId());
        monBankInfoVO.setMonUserNamer(monBankInfoDO.getMonUserNamer());
        return monBankInfoVO;
    }

    public static List<MonBankInfoVO> convertVoList(List<MonBankInfoDO> list) {
        List<MonBankInfoVO> result = new ArrayList<MonBankInfoVO>();
        for (MonBankInfoDO mondo : list) {
            result.add(toMonBankInfoVO(mondo));
        }
        return result;
    }
}

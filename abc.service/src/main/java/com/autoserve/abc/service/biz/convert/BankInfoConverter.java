package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.enums.CardStatus;
import com.autoserve.abc.service.biz.enums.CardType;

/**
 * 銀行信息轉換類
 * 
 * @author liuwei 2015年1月28日 下午3:04:53
 */
public class BankInfoConverter {

    public static BankInfo toBankInfo(BankInfoDO bankInfoDO) {
        if (bankInfoDO == null) {
            return null;
        }
        BankInfo bankInfo = new BankInfo();
        bankInfo.setAreaCode(bankInfoDO.getAreaCode());
        bankInfo.setBankCode(bankInfoDO.getBankCode());
        bankInfo.setBankId(bankInfoDO.getBankId());
        bankInfo.setBankLawer(bankInfoDO.getBankLawer());
        bankInfo.setBankName(bankInfoDO.getBankName());
        bankInfo.setBankNo(bankInfoDO.getBankNo());
        bankInfo.setBankUserId(bankInfoDO.getBankUserId());
        bankInfo.setBankUserType(bankInfoDO.getBankUserType());
        bankInfo.setCardType(CardType.valueOf(bankInfoDO.getCardType()));
        bankInfo.setCardStatus(CardStatus.valueOf(bankInfoDO.getCardStatus()));
        bankInfo.setBindDate(bankInfoDO.getBindDate());
        bankInfo.setBankAccountType(bankInfoDO.getBankAccountType());
        return bankInfo;
    }

    public static BankInfoDO toBankInfoDO(BankInfo bankInfo) {
        if (bankInfo == null) {
            return null;
        }
        BankInfoDO bankInfoDO = new BankInfoDO();
        bankInfoDO.setAreaCode(bankInfo.getAreaCode());
        bankInfoDO.setBankCode(bankInfo.getBankCode());
        bankInfoDO.setBankId(bankInfo.getBankId());
        bankInfoDO.setBankLawer(bankInfo.getBankLawer());
        bankInfoDO.setBankName(bankInfo.getBankName());
        bankInfoDO.setBankNo(bankInfo.getBankNo());
        bankInfoDO.setBankUserId(bankInfo.getBankUserId());
        bankInfoDO.setBankUserType(bankInfo.getBankUserType());
        bankInfoDO.setBankAccountType(bankInfo.getBankAccountType());
        if (bankInfo.getCardType() != null)
            bankInfoDO.setCardType(bankInfo.getCardType().getType());
        if(bankInfo.getCardStatus()!=null)
        	bankInfoDO.setCardStatus(bankInfo.getCardStatus().state);
        	bankInfoDO.setBindDate(bankInfo.getBindDate());
        return bankInfoDO;
    }

}

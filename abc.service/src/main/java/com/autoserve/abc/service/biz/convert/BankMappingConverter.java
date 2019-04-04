package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.BankMappingDO;
import com.autoserve.abc.service.biz.entity.BankMapping;

/**
 * 銀行信息轉換類
 * 
 * @author liuwei 2015年1月28日 下午3:04:53
 */
public class BankMappingConverter {

    public static BankMapping toBankMapping(BankMappingDO bankInfoDO) {
        if (bankInfoDO == null) {
            return null;
        }
        BankMapping bankInfo = new BankMapping();
        bankInfo.setBankCode(bankInfoDO.getBankCode());
        bankInfo.setBankId(bankInfoDO.getBankId());
        bankInfo.setBankName(bankInfoDO.getBankName());
        return bankInfo;
    }

    public static BankMappingDO toBankMappingDO(BankMapping bankInfo) {
        if (bankInfo == null) {
            return null;
        }
        BankMappingDO bankInfoDO = new BankMappingDO();
        bankInfoDO.setBankCode(bankInfo.getBankCode());
        bankInfoDO.setBankId(bankInfo.getBankId());
        bankInfoDO.setBankName(bankInfo.getBankName());
        return bankInfoDO;
    }

}

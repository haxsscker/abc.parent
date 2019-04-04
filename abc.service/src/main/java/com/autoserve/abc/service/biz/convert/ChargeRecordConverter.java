package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.ChargeRecordDO;
import com.autoserve.abc.service.biz.entity.ChargeRecord;
import com.autoserve.abc.service.biz.enums.FeeType;

/**
 * 收费记录Converter
 * 
 * @author J.YL 2015年1月11日 下午9:07:39
 */

public class ChargeRecordConverter {

    public static ChargeRecord toChargeRecord(ChargeRecordDO chargeRecordDO) {

        ChargeRecord chargeRecord = new ChargeRecord();
        if (chargeRecordDO == null) {
            return chargeRecord;
        }
        chargeRecord.setId(chargeRecordDO.getCrId());
        chargeRecord.setLoanId(chargeRecordDO.getCrLoanId());
        chargeRecord.setLoanType(chargeRecordDO.getCrLoanType());
        chargeRecord.setFee(chargeRecordDO.getCrFee());
        chargeRecord.setFeeType(FeeType.valueOf(chargeRecordDO.getCrFeeType()));
        chargeRecord.setFeeDate(chargeRecordDO.getCrFeeDate());
        chargeRecord.setSeqNo(chargeRecordDO.getCrSeqNo());
        return chargeRecord;
    }
}

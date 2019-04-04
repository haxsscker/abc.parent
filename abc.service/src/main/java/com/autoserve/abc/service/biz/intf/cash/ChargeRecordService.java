/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.cash;

import java.util.List;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ChargeRecordDO;
import com.autoserve.abc.service.biz.entity.ChargeRecord;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 收费记录Service
 * 
 * @author J.YL 2014年11月17日 下午5:18:18
 */
public interface ChargeRecordService {

    public PlainResult<Integer> createChargeRecord(ChargeRecordDO chargeRecord);

    public ListResult<ChargeRecordDO> queryListChargeRecord(int loanId, PageCondition pageCondition);

    ListResult<ChargeRecord> queryChargeRecordByLoanId(List<Integer> loanIds);
}

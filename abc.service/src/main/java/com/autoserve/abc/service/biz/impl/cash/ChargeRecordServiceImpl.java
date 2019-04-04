/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.cash;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ChargeRecordDO;
import com.autoserve.abc.dao.intf.ChargeRecordDao;
import com.autoserve.abc.service.biz.convert.ChargeRecordConverter;
import com.autoserve.abc.service.biz.entity.ChargeRecord;
import com.autoserve.abc.service.biz.intf.cash.ChargeRecordService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 收费记录表service
 * 
 * @author J.YL 2014年11月18日 下午1:21:53
 */
@Service
public class ChargeRecordServiceImpl implements ChargeRecordService {

    @Resource
    private ChargeRecordDao dao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public PlainResult<Integer> createChargeRecord(ChargeRecordDO chargeRecord) {
        PlainResult<Integer> result = new PlainResult<Integer>();
        int flag = dao.insert(chargeRecord);
        boolean success = true;
        if (flag <= 0) {
            success = false;
        }
        result.setSuccess(success);
        result.setData(chargeRecord.getCrId());
        return result;
    }

    @Override
    public ListResult<ChargeRecordDO> queryListChargeRecord(int loanId, PageCondition pageCondition) {
        ListResult<ChargeRecordDO> result = new ListResult<ChargeRecordDO>();
        List<ChargeRecordDO> temp = dao.queryListByLoanId(loanId, pageCondition);
        result.setData(temp);
        result.setCount(temp.size());
        return result;
    }

    /**
     * 查出所有
     */
    @Override
    public ListResult<ChargeRecord> queryChargeRecordByLoanId(List<Integer> loanIds) {
        ListResult<ChargeRecord> result = new ListResult<ChargeRecord>();
        List<ChargeRecordDO> temp = dao.queryList(loanIds);
        List<ChargeRecord> list = new ArrayList<ChargeRecord>();
        for (ChargeRecordDO chargeRecordDO : temp) {
            ChargeRecord chargeRecord = ChargeRecordConverter.toChargeRecord(chargeRecordDO);
            list.add(chargeRecord);
        }
        result.setData(list);
        result.setCount(list.size());
        return result;
    }
}

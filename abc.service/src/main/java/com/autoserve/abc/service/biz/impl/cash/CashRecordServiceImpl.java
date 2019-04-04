/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.cash;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.dataobject.CashRecordDO;
import com.autoserve.abc.dao.intf.CashRecordDao;
import com.autoserve.abc.service.biz.enums.CashOperateState;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 第三方接口交易记录表
 * 
 * @author J.YL 2014年11月18日 上午11:38:59
 */
@Service
public class CashRecordServiceImpl implements CashRecordService {

    @Resource
    private CashRecordDao dao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public PlainResult<Integer> createCashRecord(CashRecordDO cashRecord) {
        PlainResult<Integer> result = new PlainResult<Integer>();
        cashRecord.setCrResponseState(CashOperateState.NOCALLBACK.getState());
        int flag = dao.insert(cashRecord);
        boolean success = true;
        if (flag <= 0) {
            throw new BusinessException(CommonResultCode.ERROR_DB.getCode(), "插入cashRecord出错");
        }
        result.setSuccess(success);
        result.setData(cashRecord.getCrId());
        return result;
    }

    @Override
    public PlainResult<CashRecordDO> queryCashRecordBySeqNo(String seqNo) {
        PlainResult<CashRecordDO> result = new PlainResult<CashRecordDO>();
        CashRecordDO cashRecord = dao.findBySeqNo(seqNo);
        result.setData(cashRecord);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult modifyCashRecordState(CashRecordDO cashRecord) {
    	 CashRecordDO cashRecordDo=dao.findBySeqNo(cashRecord.getCrSeqNo());
    	 cashRecord.setCrId(cashRecordDo.getCrId());
        int flag = dao.updateStateById(cashRecord);
        if (flag <= 0) {
            throw new BusinessException(CommonResultCode.ERROR_DB.getCode(), String.format("资金操作记录交易流水号：%s 无记录",
                    cashRecord.getCrSeqNo()));
        }
        return new BaseResult();
    }

	@Override
	public PlainResult<CashRecordDO> queryCashRecordById(Integer id) {
		PlainResult<CashRecordDO> result = new PlainResult<CashRecordDO>();
        CashRecordDO cashRecord = dao.findById(id);
        result.setData(cashRecord);
        return result;
	}
}

/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.cash;

import com.autoserve.abc.dao.dataobject.CashRecordDO;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 资金交易记录Service
 * 
 * @author J.YL 2014年11月17日 下午5:16:20
 */
public interface CashRecordService {

    /**
     * 创建资金操作记录
     * 
     * @param cashRecord
     * @return
     */
    public PlainResult<Integer> createCashRecord(CashRecordDO cashRecord);

    /**
     * 更新资金操作记录状态
     * 
     * @param cashRecord
     * @return
     */
    public BaseResult modifyCashRecordState(CashRecordDO cashRecord);

    public PlainResult<CashRecordDO> queryCashRecordBySeqNo(String seqNo);
    /**
     * 查询资金记录
     * @param id主键
     * @return
     */
    public PlainResult<CashRecordDO> queryCashRecordById(Integer id);
   
}

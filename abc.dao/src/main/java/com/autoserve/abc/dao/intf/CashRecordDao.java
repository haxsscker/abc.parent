/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.CashRecordDO;

/**
 * 资金接口操作记录Dao
 * 
 * @author J.YL 2014年11月15日 上午9:25:15
 */
public interface CashRecordDao extends BaseDao<CashRecordDO, Integer> {
    public CashRecordDO findBySeqNo(String seqNo);
    /**
     * 查询资金记录
     */
    @Override
	public CashRecordDO findById(Integer id);

    public int updateStateById(CashRecordDO cashRecord);
    
   
}

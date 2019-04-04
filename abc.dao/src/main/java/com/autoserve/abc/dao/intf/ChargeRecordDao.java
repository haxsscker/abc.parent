/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.intf;

import java.util.List;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ChargeRecordDO;

/**
 * 收费记录表Dao
 * 
 * @author J.YL 2014年11月15日 下午6:31:46
 */
public interface ChargeRecordDao extends BaseDao<ChargeRecordDO, Integer> {
    public List<ChargeRecordDO> queryListByLoanId(Integer loanId, PageCondition pageCondition);

    public List<ChargeRecordDO> queryList(List<Integer> loanIds);
}

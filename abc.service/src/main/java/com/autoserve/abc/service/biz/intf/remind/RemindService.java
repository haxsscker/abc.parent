/*
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.remind;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.LoanSearchDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * 系统提醒
 * 
 */
public interface RemindService {


    /**
     * 查询分页的普通标列表
     * 
     * @param searchDO 查询条件，可选
     * @param pageCondition 分页条件，必选
     * @return PageResult<Loan>
     */
    PageResult<Loan> loanExpireList(LoanSearchDO searchDO,Integer expireDay, PageCondition pageCondition);


}

package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.LoanCustDO;
import org.apache.ibatis.annotations.Param;

public interface LoanCustDao extends BaseDao<LoanCustDO, Integer> {
    public LoanCustDO findByLoanId(@Param("loanId") int loanId);
}

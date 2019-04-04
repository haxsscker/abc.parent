package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.LoanCarDO;

public interface LoanCarDao extends BaseDao<LoanCarDO, Integer> {

    List<LoanCarDO> findListByLoanId(@Param("loanId") int loanId);

    int updateDeletedByLoanId(@Param("loanId") int loanId, @Param("isDeleted") boolean isDeleted);

}

package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.search.LoanSearchDO;

public interface RemindDao{

    int countLoanExpireList(@Param("searchDO") LoanSearchDO searchDO,@Param("expireDay") Integer expireDay);

    List<LoanDO> loanExpireList(@Param("searchDO") LoanSearchDO searchDO,@Param("expireDay") Integer expireDay,
                                       @Param("pageCondition") PageCondition pageCondition);

}

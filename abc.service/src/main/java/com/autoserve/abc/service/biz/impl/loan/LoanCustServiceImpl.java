package com.autoserve.abc.service.biz.impl.loan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.dataobject.LoanCustDO;
import com.autoserve.abc.dao.intf.LoanCustDao;
import com.autoserve.abc.service.biz.convert.LoanCustConverter;
import com.autoserve.abc.service.biz.entity.LoanCust;
import com.autoserve.abc.service.biz.intf.loan.LoanCustService;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author yuqing.zheng Created on 2014-12-15,11:26
 */
@Service
public class LoanCustServiceImpl implements LoanCustService {
    private static final Logger logger = LoggerFactory.getLogger(LoanCustServiceImpl.class);

    @Autowired
    private LoanCustDao         loanCustDao;

    @Override
    public PlainResult<LoanCust> queryByLoanId(int loanId) {
        PlainResult<LoanCust> result = new PlainResult<LoanCust>();
        if (loanId <= 0) {
            return result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
        }
        LoanCustDO loanCustDO = loanCustDao.findByLoanId(loanId);
        result.setData(LoanCustConverter.toLoanCust(loanCustDO));

        return result;
    }
}

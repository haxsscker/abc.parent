package com.autoserve.abc.service.biz.impl.remind;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.LoanSearchDO;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.RemindDao;
import com.autoserve.abc.service.biz.convert.LoanConverter;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.intf.remind.RemindService;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * 系统提醒
 * 
 */
@Service
public class RemindServiceImpl implements RemindService {
    private static final Logger logger = LoggerFactory.getLogger(RemindServiceImpl.class);

    @Resource
    private LoanDao             loanDao;
    @Resource
    private RemindDao remindDao;

    @Override
    public PageResult<Loan> loanExpireList(LoanSearchDO searchDO,Integer expireDay, PageCondition pageCondition) {
        PageResult<Loan> result = new PageResult<Loan>(pageCondition);

        int totalCount = remindDao.countLoanExpireList(searchDO,expireDay);
        if (totalCount > 0) {
            result.setData(LoanConverter.toLoanList(remindDao.loanExpireList(searchDO,expireDay, pageCondition)));
            result.setTotalCount(totalCount);
        }
        logger.info("yehuiLoan" + result.getData());
        return result;
    }

    
}

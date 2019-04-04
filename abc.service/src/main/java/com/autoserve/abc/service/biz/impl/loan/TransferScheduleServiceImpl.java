package com.autoserve.abc.service.biz.impl.loan;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.TransferScheduleDO;
import com.autoserve.abc.dao.intf.TransferScheduleDao;
import com.autoserve.abc.service.biz.convert.TransferScheduleConvert;
import com.autoserve.abc.service.biz.entity.TransferSchedule;
import com.autoserve.abc.service.biz.intf.loan.TransferScheduleService;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * 转让进度
 *
 * @author liuwei 2015年1月6日 下午3:19:10
 */
@Service
public class TransferScheduleServiceImpl implements TransferScheduleService {

    @Resource
    private TransferScheduleDao transferScheduleDao;

    @Override
    public PageResult<TransferSchedule> queryByLoanId(int loanId, PageCondition pageCondition) {

        PageResult<TransferSchedule> result = new PageResult<TransferSchedule>(pageCondition);

        int count = this.transferScheduleDao.countSelectByLoanId(loanId, pageCondition);

        if (count > 0) {

            List<TransferScheduleDO> listDO = this.transferScheduleDao.selectByLoanId(loanId, pageCondition);

            List<TransferSchedule> list = new ArrayList<TransferSchedule>();

            for (TransferScheduleDO transferScheduleDO : listDO) {

                list.add(TransferScheduleConvert.toTransferSchedule(transferScheduleDO));
            }

            result.setData(list);
            result.setTotalCount(count);
        }

        return result;
    }
}

package com.autoserve.abc.service.biz.impl.refund;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RefundRecordDO;
import com.autoserve.abc.dao.dataobject.search.RefundRecordSearchDO;
import com.autoserve.abc.dao.intf.RefundRecordDao;
import com.autoserve.abc.service.biz.intf.refund.RefundRecordService;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * 类RefundRecordServiceImpl.java的实现描述：退费记录的怎删改查
 * 
 * @author liuwei 2014年12月18日 上午11:34:02
 */
@Service
public class RefundRecordServiceImpl implements RefundRecordService {
    @Resource
    private RefundRecordDao RefundRecordDao;

    @Override
    public PageResult<RefundRecordDO> queryList(RefundRecordSearchDO refundRecordSearchDO, PageCondition pageCondition) {
        PageResult<RefundRecordDO> result = new PageResult<RefundRecordDO>(pageCondition);
        List<RefundRecordDO> listDO = this.RefundRecordDao.findList(refundRecordSearchDO, pageCondition);
        int total = RefundRecordDao.countBySearchDO(refundRecordSearchDO);
        result.setData(listDO);
        result.setTotalCount(total);
        return result;
    }

    @Override
    public RefundRecordDO queryById(Integer refundRecordId) {
        RefundRecordDO refundRecord = RefundRecordDao.findById(refundRecordId);
        if (refundRecord == null) {
            refundRecord = new RefundRecordDO();
        }
        return refundRecord;
    }

}

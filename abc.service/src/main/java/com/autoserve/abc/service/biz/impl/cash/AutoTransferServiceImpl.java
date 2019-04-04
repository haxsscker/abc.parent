package com.autoserve.abc.service.biz.impl.cash;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AutoTransferDO;
import com.autoserve.abc.dao.intf.AutoTransferDao;
import com.autoserve.abc.service.biz.convert.AutoTransferConvert;
import com.autoserve.abc.service.biz.entity.AutoTransfer;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.intf.cash.AutoTransferService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 类AutoTransferService.java的实现描述：TODO 类实现描述
 * 
 * @author Tiuwer 2015年4月23日 上午11:35:27
 */
@Service
public class AutoTransferServiceImpl implements AutoTransferService {
    private static final Logger logger = LoggerFactory.getLogger(AutoTransferServiceImpl.class);
    @Resource
    private AutoTransferDao     autoTransferDao;
    @Resource
    private ReviewService       reviewService;

    @Override
    public PlainResult<AutoTransfer> createAutoTransfer(AutoTransfer autoTransfer) {
        AutoTransferDO autoTransferDO = AutoTransferConvert.toAutoTransferDO(autoTransfer);
        PlainResult<AutoTransfer> result = new PlainResult<AutoTransfer>();
        int num = this.autoTransferDao.insert(autoTransferDO);
        if (num > 0) {
            result.setData(AutoTransferConvert.toAutoTransfer(autoTransferDO));
            //发送审核
            this.initReview(autoTransferDO.getAtId());
        }
        return result;
    }

    @Override
    public BaseResult removeAutoTransfer(int id) {
        this.autoTransferDao.deleteByPrimaryKey(id);
        return new BaseResult();
    }

    @Override
    public PlainResult<AutoTransfer> modifyAutoTransfer(AutoTransfer autoTransfer) {
        PlainResult<AutoTransfer> result = new PlainResult<AutoTransfer>();
        int num = this.autoTransferDao.updateByPrimaryKeySelective(AutoTransferConvert.toAutoTransferDO(autoTransfer));
        if (num > 0) {
            result.setData(autoTransfer);
        }
        return result;
    }

    @Override
    public PageResult<AutoTransfer> showList(PageCondition pageCondition) {
        PageResult<AutoTransfer> result = new PageResult<AutoTransfer>(pageCondition);
        int count = this.autoTransferDao.countAll();
        if (count > 0) {
            List<AutoTransferDO> list = this.autoTransferDao.selectAll(pageCondition);
            result.setTotalCount(count);
            result.setData(AutoTransferConvert.toAutoTransferList(list));
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public BaseResult initReview(int creditId) {
        BaseResult result = new BaseResult();

        Review review = new Review();
        review.setApplyId(creditId);
        review.setType(ReviewType.AUTO_TRANSFER_REVIEW);
        review.setCurrRole(BaseRoleType.PLATFORM_FINANCIAL);

        BaseResult reviewRes = reviewService.initiateReview(review);
        if (!reviewRes.isSuccess()) {
            logger.error("发起转账审核失败！creditId={}", creditId);
            return result.setError(CommonResultCode.BIZ_ERROR, "发起转账审核失败");
        }

        return result;
    }

    @Override
    public PlainResult<AutoTransfer> queryAutoTransfer(int id) {
        PlainResult<AutoTransfer> result = new PlainResult<AutoTransfer>();
        AutoTransferDO AutoTransferDO = this.autoTransferDao.selectByPrimaryKey(id);
        result.setData(AutoTransferConvert.toAutoTransfer(AutoTransferDO));
        return result;
    }

    @Override
    public PageResult<AutoTransfer> queryList(AutoTransferDO at, PageCondition pageCondition) {
        PageResult<AutoTransfer> result = new PageResult<AutoTransfer>(pageCondition);
        int count = this.autoTransferDao.count(at);
        if (count > 0) {
            List<AutoTransferDO> list = this.autoTransferDao.findList(at, pageCondition);
            result.setTotalCount(count);
            result.setData(AutoTransferConvert.toAutoTransferList(list));
        }
        return result;
    }
}

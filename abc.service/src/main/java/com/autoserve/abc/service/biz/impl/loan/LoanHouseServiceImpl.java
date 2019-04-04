package com.autoserve.abc.service.biz.impl.loan;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.LoanHouseDO;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.LoanHouseDao;
import com.autoserve.abc.service.biz.convert.LoanHouseConverter;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanHouse;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.intf.loan.LoanHouseService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.intf.upload.FileUploadInfoService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 房屋抵押贷服务
 * 
 * @author yuqing.zheng Created on 2014-12-15,11:26
 */
@Service
public class LoanHouseServiceImpl implements LoanHouseService {
    private static final Logger   logger = LoggerFactory.getLogger(LoanHouseServiceImpl.class);

    @Resource
    private LoanHouseDao          loanHouseDao;

    @Resource
    private LoanDao               loanDao;

    @Resource
    private LoanService           loanService;

    @Resource
    private FileUploadInfoService uploadInfoService;

    @Resource
    private ReviewService         reviewService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public PlainResult<Integer> createHouseLoan(Loan pojo, List<LoanHouse> houseList) {
        PlainResult<Integer> result = new PlainResult<Integer>();

        if (CollectionUtils.isEmpty(houseList)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "抵押房屋信息不能为空");
        }

        // 发起项目基本信息
        PlainResult<Integer> basicLoanResult = loanService.createLoan(pojo);
        if (!basicLoanResult.isSuccess()) {
            throw new BusinessException(basicLoanResult.getMessage());
        }

        // 添加房屋抵押信息
        for (LoanHouse loanHouse : houseList) {
            loanHouse.setLoanId(basicLoanResult.getData());
        }
        loanHouseDao.batchInsert(LoanHouseConverter.toLoanHouseDOList(houseList));

        //        // 发起项目初审
        //        BaseResult initiateResult = loanService.initiateFirstReview(basicLoanResult.getData());
        //        if (!initiateResult.isSuccess()) {
        //            throw new BusinessException("发起审核流程失败");
        //        }

        result.setData(basicLoanResult.getData());
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public BaseResult modifyHouseLoan(Loan pojo, List<LoanHouse> houseList) {
        BaseResult result = new BaseResult();

        if (pojo == null || pojo.getLoanId() <= 0) {
            throw new BusinessException("项目id不能为空");
        }

        if (CollectionUtils.isEmpty(houseList)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "抵押汽车信息不能为空");
        }

        // 发起项目基本信息
        BaseResult basicLoanResult = loanService.modifyLoanInfo(pojo, null);
        if (!basicLoanResult.isSuccess()) {
            throw new BusinessException(basicLoanResult.getMessage());
        }

        // 修改房屋抵押信息
        loanHouseDao.updateDeletedByLoanId(pojo.getLoanId(), true);

        for (LoanHouse loanHouse : houseList) {
            loanHouse.setLoanId(pojo.getLoanId());
        }
        loanHouseDao.batchInsert(LoanHouseConverter.toLoanHouseDOList(houseList));

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public BaseResult infoSupplementForHouseLoan(Loan pojo, List<LoanHouse> houseList) {
        BaseResult result = new BaseResult();

        if (pojo == null || pojo.getLoanId() <= 0) {
            throw new BusinessException("项目id不能为空");
        }

        if (CollectionUtils.isEmpty(houseList)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "抵押汽车信息不能为空");
        }

        // 只有融资维护待审核、待项目初审、项目初审已退回的项目，才可以编辑或资料补全
        LoanDO loanDO = loanDao.findById(pojo.getLoanId());
        if (loanDO == null) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "项目不存在");
        } else if (loanDO.getLoanState().equals(LoanState.WAIT_MAINTAIN_REVIEW.getState())) {
            // PASS
        } else if (loanDO.getLoanState().equals(LoanState.WAIT_PROJECT_REVIEW.getState())) {
            // PASS
        } else if (loanDO.getLoanState().equals(LoanState.PROJECT_REVIEW_BACK.getState())) {
            // PASS
        } else {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "只有融资维护待审核、待项目初审、项目初审已退回的项目，才可以编辑或资料补全");
        }

        // 融资申请资料补全，更新审核的version
        BaseResult reviewUpdateResult = reviewService.updateVersion(ReviewType.FINANCING_REVIEW, pojo.getLoanId());
        if (!reviewUpdateResult.isSuccess()) {
            throw new BusinessException("更新审核的version失败");
        }

        // 编辑项目基本信息、房屋抵押信息
        return modifyHouseLoan(pojo, houseList);
    }

    @Override
    public ListResult<LoanHouse> queryByLoanId(int loanId) {
        ListResult<LoanHouse> result = new ListResult<LoanHouse>();
        if (loanId <= 0) {
            return result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
        }
        List<LoanHouseDO> loanHouseDOList = loanHouseDao.findListByLoanId(loanId);
        result.setData(LoanHouseConverter.toLoanHouseList(loanHouseDOList));
        result.setCount(loanHouseDOList.size());
        return result;
    }

}

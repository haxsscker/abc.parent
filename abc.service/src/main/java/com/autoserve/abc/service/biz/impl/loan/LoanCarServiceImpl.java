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

import com.autoserve.abc.dao.dataobject.LoanCarDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.intf.LoanCarDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.service.biz.convert.LoanCarConverter;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanCar;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.intf.loan.LoanCarService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * @author yuqing.zheng Created on 2014-12-15,11:25
 */
@Service
public class LoanCarServiceImpl implements LoanCarService {
    private static final Logger logger = LoggerFactory.getLogger(LoanCarServiceImpl.class);

    @Resource
    private LoanCarDao          loanCarDao;

    @Resource
    private LoanDao             loanDao;

    @Resource
    private LoanService           loanService;

    @Resource
    private ReviewService       reviewService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public PlainResult<Integer> createCarLoan(Loan pojo, List<LoanCar> loanCarList) {
        PlainResult<Integer> result = new PlainResult<Integer>();

        if (CollectionUtils.isEmpty(loanCarList)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "抵押汽车信息不能为空");
        }

        // 创建项目基本信息
        PlainResult<Integer> basicLoanResult = loanService.createLoan(pojo);
        if (!basicLoanResult.isSuccess()) {
            throw new BusinessException("项目基本信息创建失败");
        }

        // 添加汽车抵押信息
        for (LoanCar loanCar : loanCarList) {
            loanCar.setLoanId(basicLoanResult.getData());
        }
        loanCarDao.batchInsert(LoanCarConverter.toLoanCarDOList(loanCarList));

        //        // 发起审核流程
        //        BaseResult initiateResult = loanService.initiateFirstReview(basicLoanResult.getData());
        //        if (!initiateResult.isSuccess()) {
        //            throw new BusinessException("发起审核流程失败");
        //        }

        result.setData(basicLoanResult.getData());
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public BaseResult modifyCarLoan(Loan pojo, List<LoanCar> carList) {
        BaseResult result = new BaseResult();

        if (pojo == null || pojo.getLoanId() <= 0) {
            throw new BusinessException("项目id不能为空");
        }

        if (CollectionUtils.isEmpty(carList)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "抵押汽车信息不能为空");
        }

        // 修改项目基本信息
        BaseResult updateLoanResult = loanService.modifyLoanInfo(pojo, null);
        if (!updateLoanResult.isSuccess()) {
            throw new BusinessException("项目基本信息创建失败");
        }

        // 修改汽车抵押信息
        loanCarDao.updateDeletedByLoanId(pojo.getLoanId(), true);

        for (LoanCar loanCar : carList) {
            loanCar.setLoanId(pojo.getLoanId());
        }
        loanCarDao.batchInsert(LoanCarConverter.toLoanCarDOList(carList));

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public BaseResult infoSupplementForCarLoan(Loan pojo, List<LoanCar> carList) {
        BaseResult result = new BaseResult();

        if (pojo == null || pojo.getLoanId() <= 0) {
            throw new BusinessException("项目id不能为空");
        }

        if (CollectionUtils.isEmpty(carList)) {
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

        // 编辑项目基本信息、修改汽车抵押信息
        return modifyCarLoan(pojo, carList);
    }

    @Override
    public ListResult<LoanCar> queryByLoanId(int loanId) {
        ListResult<LoanCar> result = new ListResult<LoanCar>();
        if (loanId <= 0) {
            return result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
        }
        List<LoanCarDO> loanCarDOList = loanCarDao.findListByLoanId(loanId);
        result.setData(LoanCarConverter.toLoanCarList(loanCarDOList));
        result.setCount(loanCarDOList.size());
        return result;
    }

}

package com.autoserve.abc.web.module.screen.autotransfer.json;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.intf.DealRecordDao;
import com.autoserve.abc.service.biz.entity.AutoTransfer;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.ReviewOpType;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.intf.cash.AutoTransferService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.review.ReviewOperationService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.LoginUserInfo;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;
import com.autoserve.abc.web.module.screen.credit.json.ReviewCreditApply;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 类AutoTransferCheck.java的实现描述：TODO 类实现描述
 * 
 * @author Tiuwer 2015年4月27日 上午9:06:24
 */
public class AutoTransferCheck {
    @Autowired
    private AutoTransferService    autoTransferService;

    @Autowired
    private ReviewOperationService reviewOperationService;

    @Autowired
    private DoubleDryService       doubleDryService;
    @Autowired
    private DealRecordDao dealRecordDao;

    private static final Logger    logger = LoggerFactory.getLogger(ReviewCreditApply.class);

    public JsonBaseVO execute(@Param("creId") int creId, @Param("opType") int opTypeIdx,
                              @Param("reviewType") int reviewTypeIdx, @Param("message") String message) {
        if (creId <= 0 || opTypeIdx <= 0) {
            logger.warn("参数不正确 creId={}, opType={}, ", creId, opTypeIdx);
            return new JsonBaseVO(false, "审核操作出错，请重试！");
        }

        PlainResult<AutoTransfer> result = this.autoTransferService.queryAutoTransfer(creId);

        PlainResult<Map<String, String>> doubleResult = this.doubleDryService.transferaudit(result.getData()
                .getOutSeqNo(), String.valueOf(opTypeIdx), "", "");
        Map<String, String> mapResult = doubleResult.getData();
        String resultCode = mapResult.get("ResultCode");
        if (!"88".equals(resultCode)) {
            return new JsonBaseVO(false, mapResult.get("Message"));
        }
        ReviewType reviewType = ReviewType.valueOf(reviewTypeIdx);
        ReviewOp reviewOp = buildReviewOp(opTypeIdx, message);

        AutoTransfer autoTransfer = new AutoTransfer();
//        if (ReviewState.valueOf(opTypeIdx) == ReviewState.PASS_REVIEW) {
            autoTransfer.setState(DealState.SUCCESS);
//        }
        autoTransfer.setId(creId);
        autoTransfer.setAuditState(ReviewState.valueOf(opTypeIdx));
        autoTransfer.setAtRemarks(message);
        BaseResult modifyResult = this.autoTransferService.modifyAutoTransfer(autoTransfer);
        if (!modifyResult.isSuccess()) {
            logger.warn("转账审核修改失败");
            return new JsonBaseVO(false, "转账审核修改失败");
        }
        //修改交易记录
        DealRecordDO dealRecord = new DealRecordDO();
        dealRecord.setDrOutSeqNo(mapResult.get("LoanNoList"));
        if(1==opTypeIdx){
        	dealRecord.setDrState(DealState.SUCCESS.state);
        } else {
        	dealRecord.setDrState(DealState.FAILURE.state);
        }
        dealRecord.setDrOperateDate(new Date());
        LoginUserInfo emp = LoginUserInfoHelper.getLoginUserInfo();
        dealRecord.setDrOperator(emp.getEmpId());
        dealRecordDao.updateByOutSeqNo(dealRecord);
        

        BaseResult opRes = reviewOperationService.doReview(reviewType, creId, reviewOp);
        if (!opRes.isSuccess()) {
            logger.warn("调用审核操作接口出错");
            return new JsonBaseVO(false, "审核操作出错，请重试！");
        }

        return JsonBaseVO.SUCCESS;
    }

    private ReviewOp buildReviewOp(int opTypeIdx, String message) {
        ReviewOp reviewOp = new ReviewOp();

        reviewOp.setOpType(ReviewOpType.valueOf(opTypeIdx));
        reviewOp.setMessage(StringUtils.defaultIfBlank(message, ""));

        LoginUserInfo emp = LoginUserInfoHelper.getLoginUserInfo();
        reviewOp.setEmployee(new Employee(emp.getEmpId()));

        return reviewOp;
    }

}

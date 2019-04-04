package com.autoserve.abc.web.module.screen.credit.json;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.CashSqDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.intf.cashsq.CashSqService;
import com.autoserve.abc.service.biz.intf.credit.CreditService;
import com.autoserve.abc.service.biz.intf.review.ReviewOperationService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * 审核信用额度
 *
 * @author RJQ 2015/1/6 20:50.
 */
public class CashSqCreditApply {

    @Autowired
    private CreditService creditService;
    @Autowired
    private UserService userService;
    @Autowired
    private CashSqService cashSqService;
    @Autowired
    private ReviewOperationService reviewOperationService;

    private static final Logger logger = LoggerFactory.getLogger(CashSqCreditApply.class);

    public JsonBaseVO execute(@Param("Id") int Id,
                              @Param("userId") int userId,
                              @Param("opType") int opTypeIdx,
                              @Param("reviewType") int reviewTypeIdx,
                              @Param("checkMoney") BigDecimal checkMoney,
                              @Param("message") String message) {
        if (Id <= 0 || opTypeIdx <= 0 || userId <= 0) {
            logger.warn("参数不正确 creId={}, opType={}, userId={}", Id, opTypeIdx, userId);
            return new JsonBaseVO(false, "审核操作出错，请重试！");
        }



        CashSqDO cashSqDO = new CashSqDO();
        cashSqDO.setId(Id);
        cashSqDO.setUserCashQuotaShadd(checkMoney);
        cashSqDO.setState(opTypeIdx);
        cashSqDO.setShyj(message);
        BaseResult modifyResult = cashSqService.modifyCreditApply(cashSqDO);
        if (!modifyResult.isSuccess()) {
            logger.warn("修改信用额度记录失败");
            return new JsonBaseVO(false, "修改信用额度记录失败");
        }

        UserDO userDO=new UserDO();
        userDO.setUserId(userId);
        userDO.setUserCashQuota(checkMoney);
        BaseResult opRes = userService.modifyInfo(userDO);
        if (!opRes.isSuccess()) {
            logger.warn("调用审核操作接口出错");
            return new JsonBaseVO(false, "审核操作出错，请重试！");
        }

        return JsonBaseVO.SUCCESS;
    }

    
}

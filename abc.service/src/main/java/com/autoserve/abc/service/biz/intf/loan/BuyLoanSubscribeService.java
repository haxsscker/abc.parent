package com.autoserve.abc.service.biz.intf.loan;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.entity.BuyLoanSubscribe;
import com.autoserve.abc.service.biz.enums.BuyLoanSubscribeState;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * 收购标认购人服务
 *
 * @author liuwei 2015年1月8日 下午4:48:36
 */
public interface BuyLoanSubscribeService {

    /**
     * 查询可认购收购标的人员的分页集合
     *
     * @param buyLoanId，必选
     * @param condition，必选
     * @return PageResult<BuyLoanSubscribe>
     */
    PageResult<BuyLoanSubscribe> queryBuySubscribe(int buyLoanId, PageCondition condition);

    /**
     * 修改认购人的认购状态
     *
     * @param buyLoanId 收购标id，必选
     * @param userId 用户id，必选
     * @param oldState 旧的状态，必选
     * @param newState 新的状态，必选
     * @return BaseResult
     */
    BaseResult modifySubscribeState(int buyLoanId, int userId, BuyLoanSubscribeState oldState,
                                    BuyLoanSubscribeState newState);

    /**
     * 修改认购信息
     *
     * @param toModify 待修改的认购信息，必选
     * @return BaseResult
     */
    BaseResult modifyByBuyLoanIdAndUserId(BuyLoanSubscribe toModify);

}

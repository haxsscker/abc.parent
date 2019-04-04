package com.autoserve.abc.service.biz.impl.loan;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.BuyLoanSubscribeDO;
import com.autoserve.abc.dao.intf.BuyLoanSubscribeDao;
import com.autoserve.abc.service.biz.convert.BuyLoanSubscribeConverter;
import com.autoserve.abc.service.biz.entity.BuyLoanSubscribe;
import com.autoserve.abc.service.biz.enums.BuyLoanSubscribeState;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanSubscribeService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * 收购标认购人服务
 *
 * @author liuwei 2015年1月8日 下午4:53:47
 */
@Service
public class BuyLoanSubscribeServiceImpl implements BuyLoanSubscribeService {

    @Resource
    private BuyLoanSubscribeDao buyLoanSubscribeDao;

    @Override
    public PageResult<BuyLoanSubscribe> queryBuySubscribe(int buyLoanId, PageCondition condition) {
        PageResult<BuyLoanSubscribe> result = new PageResult<BuyLoanSubscribe>(condition);

        List<BuyLoanSubscribeDO> listDO = this.buyLoanSubscribeDao.findByBuyId(buyLoanId);

        List<BuyLoanSubscribe> list = BuyLoanSubscribeConverter.toBuyLoanList(listDO);
        result.setData(list);
        return result;
    }

    @Override
    public BaseResult modifySubscribeState(int buyLoanId, int userId, BuyLoanSubscribeState oldState,
                                           BuyLoanSubscribeState newState) {
        BaseResult result = new BaseResult();

        int count = buyLoanSubscribeDao.updateState(buyLoanId, userId, oldState.getState(), newState.getState());
        if (count <= 0) {
            return result.setError(CommonResultCode.BIZ_ERROR, "修改认购人状态失败");
        }

        return result;
    }

    @Override
    public BaseResult modifyByBuyLoanIdAndUserId(BuyLoanSubscribe toModify) {
        BaseResult result = new BaseResult();

        if (toModify == null || toModify.getBuyId() == null || toModify.getUserId() == null) {
            return result.setError(CommonResultCode.ILLEGAL_PARAM);
        }

        int count = buyLoanSubscribeDao.updateByBuyLoanIdAndUserId(BuyLoanSubscribeConverter
                .toBuyLoanSubscribeDO(toModify));
        if (count <= 0) {
            return result.setError(CommonResultCode.BIZ_ERROR, "修改认购人状态失败");
        }

        return result;
    }

}

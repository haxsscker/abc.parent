package com.autoserve.abc.web.module.screen.moneyManage.json;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.entity.BuyLoanSubscribe;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanSubscribeService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;

/**
 * 收购满标划转 转让人
 *
 * @author liuwei 2015年1月8日 下午4:15:24
 */
public class BuyTransferList {

    @Resource
    private BuyLoanSubscribeService BuyLoanSubscribeService;

    public JsonPageVO<BuyLoanSubscribe> execute(@Param("buyLoanId") int buyLoanId, @Param("rows") int rows,
            @Param("page") int page) {

        PageCondition pageCondition = new PageCondition(page, rows);

        PageResult<BuyLoanSubscribe> result = this.BuyLoanSubscribeService.queryBuySubscribe(buyLoanId, pageCondition);

        return ResultMapper.toPageVO(result);

    }
}

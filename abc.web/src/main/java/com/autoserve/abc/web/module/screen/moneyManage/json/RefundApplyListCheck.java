package com.autoserve.abc.web.module.screen.moneyManage.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RefundRecordDO;
import com.autoserve.abc.dao.dataobject.search.RefundRecordSearchDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.enums.RefundState;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.RefundService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.moneyManage.RefundListCheckViewVO;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 退款审核页面
 * 
 * @author J.YL 2014年12月24日 下午8:42:24
 */
public class RefundApplyListCheck {
    @Resource
    private RefundService      refundService;
    @Resource
    private AccountInfoService accountInfoService;

    public JsonPageVO<RefundListCheckViewVO> execute(ParameterParser params) {
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        JsonPageVO<RefundListCheckViewVO> resultVO = new JsonPageVO<RefundListCheckViewVO>();
        PageCondition pageCondition = new PageCondition(page, rows);
        //此变量用于后面增加退费审核搜索使用
        RefundRecordSearchDO refund = new RefundRecordSearchDO();
        PageResult<RefundRecordDO> queryResult = refundService.queryListRefundRecord(refund, pageCondition);
        int total = queryResult.getTotalCount();
        if (total == 0) {
            resultVO.setMessage("无退款申请数据！");
            resultVO.setSuccess(true);
            resultVO.setTotal(total);
            resultVO.setRows(new ArrayList<RefundListCheckViewVO>());
            return resultVO;
        }
        List<RefundRecordDO> queryData = queryResult.getData();
        List<RefundListCheckViewVO> resultList = new ArrayList<RefundListCheckViewVO>();
        List<String> accountNos = Lists.transform(queryData, new Function<RefundRecordDO, String>() {
            @Override
            public String apply(RefundRecordDO input) {
                return input.getRefundAccountNo();
            }
        });

        ListResult<Account> accounts = accountInfoService.queryByAccountNos(accountNos);
        List<Account> accountList = accounts.getData();
        Map<String, Account> accountMap = Maps.uniqueIndex(accountList, new Function<Account, String>() {
            @Override
            public String apply(Account input) {
                return input.getAccountNo();
            }
        });
        for (RefundRecordDO rrd : queryData) {
            RefundListCheckViewVO temp = new RefundListCheckViewVO();
            String accountNo = rrd.getRefundAccountNo();
            temp.setRefundId(rrd.getRefundId());
            temp.setAccountNo(accountNo);
            temp.setApplyDate(DateUtil.formatDate(rrd.getRefundApplyDate(), DateUtil.DEFAULT_FORMAT_STYLE));
            temp.setRefundMoney(rrd.getRefundAmount());
            temp.setRefundReason(rrd.getRefundReason());
            temp.setReviewState(ReviewState.valueOf(rrd.getRefundApplyState()).getDes());
            temp.setUserName(accountMap.get(accountNo).getAccountUserName());
            temp.setUserPhone(accountMap.get(accountNo).getAccountUserPhone());
            if (ReviewState.valueOf(rrd.getRefundApplyState()).equals(ReviewState.PENDING_REVIEW)) {
                temp.setCanCheck(true);
            } else {
                temp.setCanCheck(false);
            }
            temp.setRefundState(RefundState.valueOf(rrd.getRefundState()).getDes());
            resultList.add(temp);
        }
        resultVO.setTotal(total);
        resultVO.setRows(resultList);
        return resultVO;
    }
}

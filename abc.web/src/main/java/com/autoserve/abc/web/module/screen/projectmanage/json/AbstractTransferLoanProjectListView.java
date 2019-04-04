/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.projectmanage.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;

import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.dao.dataobject.TransferLoanJDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.summary.LoanPaymentSummaryDO;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.TransferLoanVOConverter;
import com.autoserve.abc.web.vo.projectmanage.TransferLoanVO;

/**
 * 转让跟踪父类
 *
 * @author segen189 2014年12月23日 下午5:32:47
 */
public abstract class AbstractTransferLoanProjectListView {
    @Resource
    private LoanService        loanService;

    @Resource
    private UserService        userService;

    @Resource
    private GovernmentService  governmentService;

    @Resource
    private PaymentPlanService paymentPlanService;

    protected List<TransferLoanVO> convertTransferLoanVOFields(PageResult<TransferLoanJDO> result) {
        if (!result.isSuccess()) {
            return null;
        }

        List<TransferLoanVO> data = TransferLoanVOConverter.getInstance().convert(result.getData());

        if (CollectionUtils.isNotEmpty(data)) {
            // userIdset, govIds, loanIds
            Set<Integer> userIdSet = new HashSet<Integer>(data.size());
            Set<Integer> govIdSet = new HashSet<Integer>(data.size());
            Set<Integer> loanIdSet = new HashSet<Integer>(data.size());
            for (TransferLoanVO tranferLoanVO : data) {
                userIdSet.add(tranferLoanVO.getLoanUserId());
                userIdSet.add(tranferLoanVO.getUserId());
                govIdSet.add(tranferLoanVO.getLoanGov());
                loanIdSet.add(tranferLoanVO.getOriginId());
            }
            // 映射关系Map
            Map<Integer, String> userNameMap = new HashMap<Integer, String>();
            Map<Integer, String> govNameMap = new HashMap<Integer, String>();
            Map<Integer, Integer> paymentSummaryMap = new HashMap<Integer, Integer>();
            Map<Integer, String> userPhoneMap = new HashMap<Integer, String>();
            

            // 查询用户服务
            ListResult<UserDO> userResult = userService.findByList(new ArrayList<Integer>(userIdSet));
            if (userResult.isSuccess()) {
                for (UserDO userDO : userResult.getData()) {
                    userNameMap.put(userDO.getUserId(), userDO.getUserName());
                    userPhoneMap.put(userDO.getUserId(), userDO.getUserPhone());
                }
            }

            // 查询机构服务
            ListResult<GovernmentDO> govResult = governmentService.findByList(new ArrayList<Integer>(govIdSet));
            if (govResult.isSuccess()) {
                for (GovernmentDO govDO : govResult.getData()) {
                    govNameMap.put(govDO.getGovId(), govDO.getGovName());
                }
            }

            // 查询普通标还款概要
            ListResult<LoanPaymentSummaryDO> paymentSummaryResult = paymentPlanService
                    .querySummaryByIds(new ArrayList<Integer>(loanIdSet));
            if (paymentSummaryResult.isSuccess()) {
                for (LoanPaymentSummaryDO paySummary : paymentSummaryResult.getData()) {
                    paymentSummaryMap.put(paySummary.getLoanId(), paySummary.getPayedPaymentPeriod());
                }
            }

            // 用户id转换成用户名
            // 机构id转换成机构名
            // 普通标还款概要信息
            for (TransferLoanVO tranferLoanVO : data) {
                tranferLoanVO.setLoaneeName(userNameMap.get(tranferLoanVO.getLoanUserId()));
                tranferLoanVO.setLoanUserPhone(userPhoneMap.get(tranferLoanVO.getLoanUserId()));
                tranferLoanVO.setTranferUserName(userNameMap.get(tranferLoanVO.getUserId()));
                tranferLoanVO.setGovName(govNameMap.get(tranferLoanVO.getLoanGov()));
                tranferLoanVO.setPayedPaymentPeriod(paymentSummaryMap.get(tranferLoanVO.getOriginId()));
            }

            // remind gc
            userIdSet = null;
            govIdSet = null;
            loanIdSet = null;
            userNameMap = null;
            govNameMap = null;
            paymentSummaryMap = null;

        }

        return data;
    }
}

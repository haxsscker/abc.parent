/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.convert;

import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.projectmanage.InvestVO;

/**
 * @author segen189 2014年12月22日 下午8:34:03
 */
public class InvestVOConverter extends ResultConverter<Invest, InvestVO> {
    private static InvestVOConverter singleton = new InvestVOConverter();

    private InvestVOConverter() {
    }

    public static InvestVOConverter getInstance() {
        return singleton;
    }

    @Override
    public InvestVO convert(Invest invest) {
        if (invest == null) {
            return null;
        }

        InvestVO investVO = new InvestVO();

        investVO.setId(invest.getId());
        investVO.setUserId(invest.getUserId());
        investVO.setCreatetime(invest.getCreatetime());
        investVO.setModifytime(invest.getModifytime());
        investVO.setInvestMoney(invest.getInvestMoney());
        investVO.setValidInvestMoney(invest.getValidInvestMoney());
        investVO.setInvestState(invest.getInvestState());
        investVO.setInnerSeqNo(invest.getInnerSeqNo());
        investVO.setWithdrawSeqNo(invest.getWithdrawSeqNo());
        investVO.setOriginId(invest.getOriginId());
        investVO.setBidType(invest.getBidType());
        investVO.setBidId(invest.getBidId());
        investVO.setInvestTimeStr(DateUtil.formatDate(invest.getCreatetime(), DateUtil.DATE_TIME_FORMAT));
        if (invest.getInvestState() != null) {
            investVO.setInvestStateStr(invest.getInvestState().getPrompt());
        }

        return investVO;
    }

}

package com.autoserve.abc.service.biz.convert;

import java.util.Collections;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.dataobject.LoanIntentApplyDO;
import com.autoserve.abc.service.biz.entity.LoanIntentApply;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanPayType;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoaneeType;
import com.google.common.collect.Lists;

/**
 * LoanIntentApply的DO与Entity的转换器
 *
 * @author wuqiang.du 2014年12月20日 下午9:48:57
 * @author yuqing.zheng
 */
public class LoanIntentApplyConverter {

    public static LoanIntentApplyDO toLoanIntentApplyDO(LoanIntentApply loanIntentApply) {
        LoanIntentApplyDO loanIntentApplyDO = new LoanIntentApplyDO();
        if (loanIntentApply == null) {
            return loanIntentApplyDO;
        }

        loanIntentApplyDO.setLiaId(loanIntentApply.getId());

        if (loanIntentApply.getIntenteeType() != null) {
            loanIntentApplyDO.setLiaIntentEmpType(loanIntentApply.getIntenteeType().getType());
        }

        loanIntentApplyDO.setLiaUserId(loanIntentApply.getUserId());
        loanIntentApplyDO.setLiaUserName(loanIntentApply.getUserName());
        loanIntentApplyDO.setLiaPhone(loanIntentApply.getPhone());
        loanIntentApplyDO.setLiaIntentMoney(loanIntentApply.getIntentMoney());
        loanIntentApplyDO.setLiaArea(loanIntentApply.getArea());
        loanIntentApplyDO.setLiaNote(loanIntentApply.getNote());
        loanIntentApplyDO.setLiaIntentTime(loanIntentApply.getIntentTime());

        if (loanIntentApply.getIntentState() != null) {
            loanIntentApplyDO.setLiaIntentStatus(loanIntentApply.getIntentState().state);
        }
        loanIntentApplyDO.setLiaAuditTime(loanIntentApply.getAuditTime());
        loanIntentApplyDO.setLiaAuditOpinion(loanIntentApply.getAuditOpinion());
        loanIntentApplyDO.setLiaIntentRate(loanIntentApply.getIntentRate());
        loanIntentApplyDO.setLiaIntentTitle(loanIntentApply.getIntentTitle());

        if (loanIntentApply.getIntentCategory() != null) {
            loanIntentApplyDO.setLiaIntentCategory(loanIntentApply.getIntentCategory().category);
        }

        loanIntentApplyDO.setLiaIntentNo(loanIntentApply.getIntentNo());
        loanIntentApplyDO.setLiaIntentUse(loanIntentApply.getIntentUse());
        loanIntentApplyDO.setLiaIntentPeriod(loanIntentApply.getIntentPeriod());

        if (loanIntentApply.getIntentPeriodUnit() != null) {
            loanIntentApplyDO.setLiaIntentPeriodUnit(loanIntentApply.getIntentPeriodUnit().getUnit());
        }

        if (loanIntentApply.getIntentPayType() != null) {
            loanIntentApplyDO.setLiaIntentPayType(loanIntentApply.getIntentPayType().type);
        }

        loanIntentApplyDO.setLiaLoanId(loanIntentApply.getLoanId());
        loanIntentApplyDO.setLiaFileUrl(loanIntentApply.getFileUrl());

        return loanIntentApplyDO;
    }

    public static LoanIntentApply toLoanIntentApply(LoanIntentApplyDO loanIntentApplyDO) {
        LoanIntentApply loanIntentApply = new LoanIntentApply();
        if (loanIntentApplyDO == null) {
            return loanIntentApply;
        }

        loanIntentApply.setId(loanIntentApplyDO.getLiaId());
        loanIntentApply.setIntentNo(loanIntentApplyDO.getLiaIntentNo());
        loanIntentApply.setIntentTitle(loanIntentApplyDO.getLiaIntentTitle());
        loanIntentApply.setIntentCategory(LoanCategory.valueOf(loanIntentApplyDO.getLiaIntentCategory()));
        loanIntentApply.setIntentMoney(loanIntentApplyDO.getLiaIntentMoney());
        loanIntentApply.setIntentRate(loanIntentApplyDO.getLiaIntentRate());
        loanIntentApply.setArea(loanIntentApplyDO.getLiaArea());
        loanIntentApply.setIntentPeriod(loanIntentApplyDO.getLiaIntentPeriod());
        loanIntentApply.setIntentPeriodUnit(LoanPeriodUnit.valueOf(loanIntentApplyDO.getLiaIntentPeriodUnit()));
        loanIntentApply.setIntentUse(loanIntentApplyDO.getLiaIntentUse());
        loanIntentApply.setIntentPayType(LoanPayType.valueOf(loanIntentApplyDO.getLiaIntentPayType()));
        loanIntentApply.setIntenteeType(LoaneeType.valueOf(loanIntentApplyDO.getLiaIntentEmpType()));
        loanIntentApply.setUserId(loanIntentApplyDO.getLiaUserId());
        loanIntentApply.setUserName(loanIntentApplyDO.getLiaUserName());
        loanIntentApply.setPhone(loanIntentApplyDO.getLiaPhone());
        loanIntentApply.setNote(loanIntentApplyDO.getLiaNote());
        loanIntentApply.setIntentTime(loanIntentApplyDO.getLiaIntentTime());
        loanIntentApply.setIntentState(LoanState.valueOf(loanIntentApplyDO.getLiaIntentStatus()));
        loanIntentApply.setAuditTime(loanIntentApplyDO.getLiaAuditTime());
        loanIntentApply.setAuditOpinion(loanIntentApplyDO.getLiaAuditOpinion());
        loanIntentApply.setLoanId(loanIntentApplyDO.getLiaLoanId());
        loanIntentApply.setFileUrl(loanIntentApplyDO.getLiaFileUrl());

        return loanIntentApply;
    }

    public static List<LoanIntentApply> toLoanIntentApplyList(List<LoanIntentApplyDO> loanIntentApplyDOList) {
        if (CollectionUtils.isEmpty(loanIntentApplyDOList)) {
            return Collections.emptyList();
        }

        List<LoanIntentApply> loanIntentApplyList = Lists.newArrayListWithCapacity(loanIntentApplyDOList.size());
        for (LoanIntentApplyDO loanIntentApplyDO : loanIntentApplyDOList) {
            loanIntentApplyList.add(toLoanIntentApply(loanIntentApplyDO));
        }

        return loanIntentApplyList;
    }
}

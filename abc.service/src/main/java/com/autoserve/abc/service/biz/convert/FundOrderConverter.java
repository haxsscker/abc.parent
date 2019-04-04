package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.FundOrderApplyUserJDO;
import com.autoserve.abc.dao.dataobject.FundOrderDO;
import com.autoserve.abc.service.biz.entity.FundOrder;
import com.autoserve.abc.service.biz.entity.FundOrderApplyUser;
import com.autoserve.abc.service.biz.enums.FundOrderState;
import com.autoserve.abc.service.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;

public class FundOrderConverter {

    public static FundOrderDO toFundOrderDO(FundOrder fundOrder) {
        FundOrderDO fundOrderDO = new FundOrderDO();
        fundOrderDO.setFoOrderId(fundOrder.getFoOrderId());
        fundOrderDO.setFoFundId(fundOrder.getFoFundId());
        fundOrderDO.setFoUserId(fundOrder.getFoUserId());
        fundOrderDO.setFoOrderDate(fundOrder.getFoOrderDate());
        fundOrderDO.setFoUserName(fundOrder.getFoUserName());
        fundOrderDO.setFoUserPhone(fundOrder.getFoUserPhone());
        fundOrderDO.setFoOrderState(fundOrder.getFoOrderState().getOrderState());
        return fundOrderDO;
    }

    public static FundOrder toFundOrder(FundOrderDO fundOrderDO) {
        FundOrder fundOrder = new FundOrder();
        if(fundOrderDO == null){
            return fundOrder;
        }
        fundOrder.setFoOrderId(fundOrderDO.getFoOrderId());
        fundOrder.setFoFundId(fundOrderDO.getFoFundId());
        fundOrder.setFoUserId(fundOrderDO.getFoUserId());
        fundOrder.setFoOrderDate(fundOrderDO.getFoOrderDate());
        fundOrder.setFoUserName(fundOrderDO.getFoUserName());
        fundOrder.setFoUserPhone(fundOrderDO.getFoUserPhone());
        fundOrder.setFoOrderState(FundOrderState.valueOf(fundOrderDO.getFoOrderState()));
        return fundOrder;
    }

    public static FundOrderApplyUser toFundOrderApplyUser(FundOrderApplyUserJDO fundOrderApplyUserJDO) {
        FundOrderApplyUser fundOrderApplyUser = new FundOrderApplyUser();
        fundOrderApplyUser.setFcCheckId(fundOrderApplyUserJDO.getFcCheckId());
        fundOrderApplyUser.setFcOrderId(fundOrderApplyUserJDO.getFcOrderId());
        fundOrderApplyUser.setFcCheckEmp(fundOrderApplyUserJDO.getFcCheckEmp());
        fundOrderApplyUser.setFcCheckDate(fundOrderApplyUserJDO.getFcCheckDate());
        fundOrderApplyUser.setFcCheckIdear(fundOrderApplyUserJDO.getFcCheckIdear());
        fundOrderApplyUser.setFcRechargeMoney(fundOrderApplyUserJDO.getFcRechargeMoney());
        fundOrderApplyUser.setFcRechargeDate(fundOrderApplyUserJDO.getFcRechargeDate());
        fundOrderApplyUser.setFcCheckState(fundOrderApplyUserJDO.getFcCheckState());
        fundOrderApplyUser.setFoOrderId(fundOrderApplyUserJDO.getFcOrderId());
        fundOrderApplyUser.setFoFundId(fundOrderApplyUserJDO.getFaFundId());
        fundOrderApplyUser.setFoUserId(fundOrderApplyUserJDO.getFoUserId());
        fundOrderApplyUser.setFoOrderDate(fundOrderApplyUserJDO.getFoOrderDate());
        fundOrderApplyUser.setFoUserName(fundOrderApplyUserJDO.getFoUserName());
        fundOrderApplyUser.setFoUserPhone(fundOrderApplyUserJDO.getFoUserPhone());
        fundOrderApplyUser.setFoOrderState(FundOrderState.valueOf(fundOrderApplyUserJDO.getFoOrderState()));
        fundOrderApplyUser.setFaFundId(fundOrderApplyUserJDO.getFaFundId());
        fundOrderApplyUser.setFaFundNo(fundOrderApplyUserJDO.getFaFundNo());
        fundOrderApplyUser.setFaFundName(fundOrderApplyUserJDO.getFaFundName());
        fundOrderApplyUser.setFaFundComp(fundOrderApplyUserJDO.getFaFundComp());
        fundOrderApplyUser.setFaFundMoney(fundOrderApplyUserJDO.getFaFundMoney());
        fundOrderApplyUser.setFaFundPeriod(fundOrderApplyUserJDO.getFaFundPeriod());
        fundOrderApplyUser.setFaFundRate(fundOrderApplyUserJDO.getFaFundRate());
        fundOrderApplyUser.setFaMinMoney(fundOrderApplyUserJDO.getFaMinMoney());
        fundOrderApplyUser.setFaFundIndustry(fundOrderApplyUserJDO.getFaFundIndustry());
        fundOrderApplyUser.setFaPayType(fundOrderApplyUserJDO.getFaPayType());
        fundOrderApplyUser.setFaFundType(fundOrderApplyUserJDO.getFaFundType());
        fundOrderApplyUser.setFaMartgageRate(fundOrderApplyUserJDO.getFaMartgageRate());
        fundOrderApplyUser.setFaMartgageIntrol(fundOrderApplyUserJDO.getFaMartgageIntrol());
        fundOrderApplyUser.setFaFundIntrol(fundOrderApplyUserJDO.getFaFundIntrol());
        fundOrderApplyUser.setFaFundUse(fundOrderApplyUserJDO.getFaFundUse());
        fundOrderApplyUser.setFaFundRisk(fundOrderApplyUserJDO.getFaFundRisk());
        fundOrderApplyUser.setFaPayResource(fundOrderApplyUserJDO.getFaPayResource());
        fundOrderApplyUser.setFaProjectIntrol(fundOrderApplyUserJDO.getFaProjectIntrol());
        fundOrderApplyUser.setFaCompIntrol(fundOrderApplyUserJDO.getFaCompIntrol());
        fundOrderApplyUser.setFaFundState(fundOrderApplyUserJDO.getFaFundState());
        fundOrderApplyUser.setFaAddDate(fundOrderApplyUserJDO.getFaAddDate());
        fundOrderApplyUser.setUserId(fundOrderApplyUserJDO.getFoUserId());
        fundOrderApplyUser.setUserName(fundOrderApplyUserJDO.getFoUserName());
        fundOrderApplyUser.setUserRealName(fundOrderApplyUserJDO.getUserRealName());
        fundOrderApplyUser.setUserPwd(fundOrderApplyUserJDO.getUserPwd());
        fundOrderApplyUser.setUserDealPwd(fundOrderApplyUserJDO.getUserDealPwd());
        fundOrderApplyUser.setUserType(fundOrderApplyUserJDO.getUserType());
        fundOrderApplyUser.setUserDocType(fundOrderApplyUserJDO.getUserDocType());
        fundOrderApplyUser.setUserDocNo(fundOrderApplyUserJDO.getUserDocNo());
        fundOrderApplyUser.setUserSex(fundOrderApplyUserJDO.getUserSex());
        fundOrderApplyUser.setUserBirthday(fundOrderApplyUserJDO.getUserBirthday());
        fundOrderApplyUser.setUserNation(fundOrderApplyUserJDO.getUserNation());
        fundOrderApplyUser.setUserNative(fundOrderApplyUserJDO.getUserNative());
        fundOrderApplyUser.setUserPhone(fundOrderApplyUserJDO.getFoUserPhone());
        fundOrderApplyUser.setUserEmail(fundOrderApplyUserJDO.getUserEmail());
        fundOrderApplyUser.setUserMarry(fundOrderApplyUserJDO.getUserMarry());
        fundOrderApplyUser.setUserMonthIncome(fundOrderApplyUserJDO.getUserMonthIncome());
        fundOrderApplyUser.setUserLoginCount(fundOrderApplyUserJDO.getUserLoginCount());
        fundOrderApplyUser.setUserHeadImg(fundOrderApplyUserJDO.getUserHeadImg());
        fundOrderApplyUser.setUserScore(fundOrderApplyUserJDO.getUserScore());
        fundOrderApplyUser.setUserState(fundOrderApplyUserJDO.getUserState());

        return fundOrderApplyUser;
    }

    public static List<FundOrderApplyUser> convertList(List<FundOrderApplyUserJDO> list) {
        if (list == null || list.isEmpty())
            throw new BusinessException("传入的list为空");
        List<FundOrderApplyUser> result = new ArrayList<FundOrderApplyUser>();
        for (FundOrderApplyUserJDO rnao : list) {
            result.add(toFundOrderApplyUser(rnao));
        }
        return result;
    }
}

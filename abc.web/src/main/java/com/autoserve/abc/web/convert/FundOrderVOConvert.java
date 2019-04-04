/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.convert;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import org.joda.time.DateTime;

import com.autoserve.abc.dao.dataobject.FundOrderApplyUserJDO;
import com.autoserve.abc.dao.dataobject.FundOrderDO;
import com.autoserve.abc.service.biz.entity.FundOrder;
import com.autoserve.abc.service.biz.entity.FundOrderApplyUser;
import com.autoserve.abc.service.biz.enums.FundOrderState;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.fund.FundOrderApplyUserJVO;
import com.autoserve.abc.web.vo.fund.FundOrderVO;

/**
 * 类FundOrderVOConvert.java的实现描述：TODO 类实现描述
 * 
 * @author dongxuejiao 2014年12月23日 下午1:45:17
 */
public class FundOrderVOConvert {
    public static FundOrder toFundOrder(FundOrderVO fundOrderVO) {
        FundOrder fundOrder = new FundOrder();
        fundOrder.setFoOrderId(fundOrderVO.getFoOrderId());
        fundOrder.setFoFundId(fundOrderVO.getFoFundId());
        fundOrder.setFoUserId(fundOrderVO.getFoUserId());
        fundOrder.setFoOrderDate(new DateTime(fundOrderVO.getFoOrderDate()).toDate());
        fundOrder.setFoUserName(fundOrderVO.getFoUserName());
        fundOrder.setFoUserPhone(fundOrderVO.getFoUserPhone());

        fundOrder
                .setFoOrderState(FundOrderState.valueOf(Integer.valueOf(fundOrderVO.getFoOrderState() != null ? fundOrderVO
                        .getFoOrderState() : "0")));
        // 发布状态为空时设为0未发布状态
        return fundOrder;
    }

    /**
     * FundOrderDO -> FundOrderVO
     * @param fundOrderDO
     * @return
     */
    public static FundOrderVO toFundOrderVO(FundOrderDO fundOrderDO) {
        FundOrderVO fundOrderVO = new FundOrderVO();
        fundOrderVO.setFoOrderId(fundOrderDO.getFoOrderId());
        fundOrderVO.setFoFundId(fundOrderDO.getFoFundId());
        fundOrderVO.setFoUserId(fundOrderDO.getFoUserId());
        fundOrderVO.setFoOrderDate(new DateTime(fundOrderDO.getFoOrderDate()).toString(DateUtil.DATE_FORMAT));
        fundOrderVO.setFoUserName(fundOrderDO.getFoUserName());
        fundOrderVO.setFoUserPhone(fundOrderDO.getFoUserPhone());

        fundOrderVO.setFoOrderState(FundOrderState.valueOf(fundOrderVO.getFoOrderState()).getDes());
        return fundOrderVO;
    }

    /**
     * @param data
     * @return
     */
    public static List<FundOrderVO> convertVoList(List<FundOrderDO> list) {
        List<FundOrderVO> result = new ArrayList<FundOrderVO>();
        for (FundOrderDO fado : list) {
            result.add(toFundOrderVO(fado));
        }
        return result;
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

    /**
     * @param fundOrderApplyUser
     * @return
     */
    public static FundOrderApplyUserJVO toFundOrderApplyUserJVO(FundOrderApplyUserJDO fundOrderApplyUserJDO) {
        FundOrderApplyUserJVO fundOrderApplyUser = new FundOrderApplyUserJVO();
        fundOrderApplyUser.setFcCheckId(fundOrderApplyUserJDO.getFcCheckId());
        fundOrderApplyUser.setFcOrderId(fundOrderApplyUserJDO.getFcOrderId());
        fundOrderApplyUser.setFcCheckEmp(fundOrderApplyUserJDO.getFcCheckEmp());
        fundOrderApplyUser.setFcCheckDate(new DateTime(fundOrderApplyUserJDO.getFcCheckDate())
                .toString(DateUtil.DATE_FORMAT));
        fundOrderApplyUser.setFcCheckIdear(fundOrderApplyUserJDO.getFcCheckIdear());
        fundOrderApplyUser.setFcRechargeMoney(fundOrderApplyUserJDO.getFcRechargeMoney());
        fundOrderApplyUser.setFcRechargeDate(new DateTime(fundOrderApplyUserJDO.getFcRechargeDate())
                .toString(DateUtil.DATE_FORMAT));
        fundOrderApplyUser.setFcCheckState(fundOrderApplyUserJDO.getFcCheckState());
        fundOrderApplyUser.setFoOrderId(fundOrderApplyUserJDO.getFcOrderId());
        fundOrderApplyUser.setFoFundId(fundOrderApplyUserJDO.getFaFundId());
        fundOrderApplyUser.setFoUserId(fundOrderApplyUserJDO.getFoUserId());
        fundOrderApplyUser.setFoOrderState(fundOrderApplyUserJDO.getFoOrderState());
        fundOrderApplyUser.setFoOrderDate(new DateTime(fundOrderApplyUserJDO.getFoOrderDate())
                .toString(DateUtil.DATE_FORMAT));
        fundOrderApplyUser.setFoUserName(fundOrderApplyUserJDO.getFoUserName());
        fundOrderApplyUser.setFoUserPhone(fundOrderApplyUserJDO.getFoUserPhone());
        // fundOrderApplyUser.setFoOrderState(FundOrderState.valueOf(fundOrderApplyUserJDO.getFoOrderState()));
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
        fundOrderApplyUser.setFaAddDate(new DateTime(fundOrderApplyUserJDO.getFaAddDate())
                .toString(DateUtil.DATE_FORMAT));
        fundOrderApplyUser.setUserId(fundOrderApplyUserJDO.getFoUserId());
        fundOrderApplyUser.setUserName(fundOrderApplyUserJDO.getFoUserName());
        fundOrderApplyUser.setUserRealName(fundOrderApplyUserJDO.getUserRealName());
        fundOrderApplyUser.setUserPwd(fundOrderApplyUserJDO.getUserPwd());
        fundOrderApplyUser.setUserDealPwd(fundOrderApplyUserJDO.getUserDealPwd());
        fundOrderApplyUser.setUserType(fundOrderApplyUserJDO.getUserType());
        fundOrderApplyUser.setUserDocType(fundOrderApplyUserJDO.getUserDocType());
        fundOrderApplyUser.setUserDocNo(fundOrderApplyUserJDO.getUserDocNo());
        fundOrderApplyUser.setUserSex(fundOrderApplyUserJDO.getUserSex());
        fundOrderApplyUser.setUserBirthday(new DateTime(fundOrderApplyUserJDO.getUserBirthday())
                .toString(DateUtil.DATE_FORMAT));
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

    public static FundOrderApplyUserJVO toFundOrderApplyUserJVO(FundOrderApplyUser fundOrderApplyUser) {
        FundOrderApplyUserJVO fundOrderApplyUserJVO = new FundOrderApplyUserJVO();

        BeanCopier beanCopier = BeanCopier.create(FundOrderApplyUser.class, FundOrderApplyUserJVO.class, false);
        fundOrderApplyUserJVO.setFcRechargeDate(new DateTime(fundOrderApplyUser.getFcRechargeDate())
        .toString(DateUtil.DATE_FORMAT));
        beanCopier.copy(fundOrderApplyUser, fundOrderApplyUserJVO, null);

        return fundOrderApplyUserJVO;

    }

    public static List<FundOrderApplyUserJVO> convertToList(List<FundOrderApplyUserJDO> users) {
        if (users == null || users.isEmpty())
            throw new BusinessException("传入的list为空");
        List<FundOrderApplyUserJVO> result = new ArrayList<FundOrderApplyUserJVO>();
        for (FundOrderApplyUserJDO user : users) {
            result.add(toFundOrderApplyUserJVO(user));
        }
        return result;
    }
}

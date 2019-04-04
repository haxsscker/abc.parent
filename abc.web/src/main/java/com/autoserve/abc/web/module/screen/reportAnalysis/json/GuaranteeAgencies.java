/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.reportAnalysis.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CashBorrowerViewDO;
import com.autoserve.abc.service.biz.entity.UserCompany;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.CashBorrowerService;
import com.autoserve.abc.service.biz.intf.user.UserCompanyService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.reportAnalysis.GuaranteeAgenciesVO;
import com.google.common.base.Function;
import com.google.common.collect.Maps;

/**
 * 借款人资金对账
 * 
 * @author J.YL 2014年12月19日 上午10:45:51
 */
public class GuaranteeAgencies {
    @Resource
    private CashBorrowerService cashBorrower;
    @Resource
    private UserCompanyService  userCompanyService;

    public JsonPageVO<GuaranteeAgenciesVO> execute(@Param("borrowerName") String borrowerName, ParameterParser params) {
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        PageResult<CashBorrowerViewDO> result = cashBorrower.queryCashBorrower(borrowerName, pageCondition);
        List<CashBorrowerViewDO> cashBorrower = result.getData();
        JsonPageVO<GuaranteeAgenciesVO> resultVO = new JsonPageVO<GuaranteeAgenciesVO>();
        List<GuaranteeAgenciesVO> resultData = new ArrayList<GuaranteeAgenciesVO>();
        List<Integer> companyUserId = new ArrayList<Integer>();
        for (CashBorrowerViewDO cash : cashBorrower) {
            if (UserType.valueOf(cash.getUserType()).equals(UserType.ENTERPRISE)) {
                companyUserId.add(cash.getPpLoanee());
            }
        }
        Map<Integer, UserCompany> companyName = null;
        if (!companyUserId.isEmpty()) {
            ListResult<UserCompany> uc = userCompanyService.queryUserCompaniesByUserIds(companyUserId);
            if (!uc.isSuccess()) {
                resultVO.setSuccess(false);
                resultVO.setMessage(uc.getMessage());
                return resultVO;
            }
            List<UserCompany> userCompanies = uc.getData();
            companyName = Maps.uniqueIndex(userCompanies, new Function<UserCompany, Integer>() {
                @Override
                public Integer apply(UserCompany userCompany) {
                    return userCompany.getUserId();
                }
            });
        }
        for (CashBorrowerViewDO cash : cashBorrower) {
            GuaranteeAgenciesVO temp = new GuaranteeAgenciesVO();
            temp.setProposer(cash.getUserName());
            if (UserType.valueOf(cash.getUserType()).equals(UserType.ENTERPRISE) && companyName != null) {
                UserCompany userCompany = companyName.get(cash.getPpLoanee());
                String company = null;
                if (userCompany != null) {
                    company = userCompany.getCompanyName();
                }
                temp.setPro_user_name(company);
            } else {
                temp.setPro_user_name(cash.getUserName());
            }
            temp.setProposerRealName(cash.getUserRealName());
            temp.setProposertype(UserType.valueOf(cash.getUserType()).getDes());
            //融资总额为应还本金
            temp.setRecharge_amount(cash.getPpPayCapital());
            temp.setRecovery_principal(cash.getPpPayCollectCapital());
            temp.setRecovery_interest(cash.getPpPayCollectInterest());
            temp.setRecovery_amount(cash.getPpPayCollectFine());
            // add by 夏同同  20160419
         	temp.setRecovery_breach_amount(cash.getPpPayCollectBreachFine());
            temp.setPro_collect_service_fee(cash.getPpCollectServiceFee());
            temp.setPro_collect_guar_fee(cash.getPpCollectGuarFee());
            temp.setPro_collect_total(cash.getPpCollectTotal());
            temp.setForeclosure_investing(cash.getPpPayTotalMoney().subtract(cash.getPpCollectTotal()));
            temp.setNot_pay_money(cash.getPpPayCapital().subtract(cash.getPpPayCollectCapital()));
            temp.setNot_pay_over_rate(cash.getPpRemainFine());
            temp.setNot_pay_rate(cash.getPpPayInterest().subtract(cash.getPpPayCollectInterest()));
            temp.setNot_pay_service_fee(cash.getPpPayServiceFee().subtract(cash.getPpCollectServiceFee()));
            resultData.add(temp);
        }
        resultVO.setRows(resultData);
        resultVO.setTotal(result.getTotalCount());
        return resultVO;
    }
}

package com.autoserve.abc.web.module.control.loan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.service.biz.convert.IntentLoanConverter;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanIntentApply;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.loan.LoanIntentApplyService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author yuqing.zheng Created on 2014-12-25,15:34
 */
public class LoanInfoAddView {
    private static final Logger    log = LoggerFactory.getLogger(LoanInfoAddView.class);

    @Autowired
    private LoanIntentApplyService intentService;

    @Autowired
    private LoanQueryService       loanQueryService;

    @Autowired
    private UserService            userService;

    @Autowired
    private GovernmentService      governmentService;
    
    @Autowired
    private EmployeeService employeeService;

    public void execute(Context context) {
        try {
            boolean isIntent = "true".equals(context.get("isIntent").toString());
            context.put("loanCategories", LoanCategory.values());
            Loan loan;
            // isIntent为true，表明这是意向审核的资料补全，将意向申请中的信息填入对应的项目的字段中
            // 注意：判断当前意向是否未被补全过，如果是，将意向信息存入上下文；如果否，则从loan表取补全过的信息放入上下文
            if (isIntent) {
                int intentId = Integer.valueOf(context.get("intentId").toString());
                PlainResult<LoanIntentApply> intentResult = intentService.queryById(intentId);
                if (!intentResult.isSuccess()) {
                    log.warn("意向申请查询失败", intentResult.getMessage());
                    return;
                }

                LoanIntentApply intentApply = intentResult.getData();
                if (intentApply.getLoanId() == null) {
                    loan = IntentLoanConverter.toLoan(intentApply);
                } else {
                    loan = loanQueryService.queryById(intentApply.getLoanId()).getData();
                }
            }
            // isIntent为false 项目资料的新增或补全
            else {
                context.put("dataId", context.get("dataId").toString());
                int loanId = Integer.valueOf(context.get("loanId").toString());
                int loanType = Integer.valueOf(context.get("loanType").toString());
                context.put("loanType", loanType);
                // loanId<=0 表明这是对项目资料的新增
                if (loanId <= 0) {
                    return;
                }
                // loanId>0 表明这是对项目资料的补全, 加载loan信息
                PlainResult<Loan> loanResult = loanQueryService.queryById(loanId);
                if (!loanResult.isSuccess()) {
                    log.warn("普通标查询失败", loanResult.getMessage());
                    return;
                }

                loan = loanResult.getData();
            }
             
            if (loan.getLoanGuarGov() != null) {
                GovernmentDO govment = governmentService.findById(loan.getLoanGuarGov()).getData();
                context.put("govName", govment.getGovName());
            }

            User user = userService.findEntityById(loan.getLoanUserId()).getData();
            context.put("username", user.getUserName());
            
            //判断二次分配类型 查出分配收款人信息
            context.put("loan", loan);
            int k = Integer.valueOf(loan.getLoanSecondaryAllocation());//获取二次分配类型转化为整型
            if(k==0){
                if (loan.getLoanSecondaryUser() != null) {
                       User SecondUser = userService.findEntityById(loan.getLoanSecondaryUser()).getData();
                       context.put("SecondUser", SecondUser.getUserRealName());
                    }
            }
            if(k==1){
            	if (loan.getLoanSecondaryUser() != null) {
            		  // GovernmentDO govment = governmentService.findById(loan.getLoanSecondaryUser()).getData();
            		EmployeeDO employeeDO = employeeService.findById(loan.getLoanSecondaryUser()).getData();
            		GovernmentDO goverment = governmentService.findById(employeeDO.getEmpOrgId()).getData();
                    context.put("SecondUser", goverment.getGovName());
            	}
            }
            
            if (!StringUtil.isEmpty(loan.getLoanRedUseScopes())) {
                String[] redUseScopes = loan.getLoanRedUseScopes().split("\\|");
                context.put("redUseScopes", redUseScopes);
            }

        } catch (Exception e) {
            log.warn("参数转换失败", e);
        }
    }
}

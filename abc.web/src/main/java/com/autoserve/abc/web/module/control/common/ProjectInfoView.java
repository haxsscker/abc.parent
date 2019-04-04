package com.autoserve.abc.web.module.control.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanCar;
import com.autoserve.abc.service.biz.entity.LoanHouse;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserCompany;
import com.autoserve.abc.service.biz.entity.UserContact;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.loan.LoanCarService;
import com.autoserve.abc.service.biz.intf.loan.LoanCustService;
import com.autoserve.abc.service.biz.intf.loan.LoanHouseService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.user.UserCompanyService;
import com.autoserve.abc.service.biz.intf.user.UserContactService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author yuqing.zheng Created on 2014-12-15,12:09
 */
public class ProjectInfoView {
    private static final Logger logger = LoggerFactory.getLogger(ProjectInfoView.class);

    @Autowired
    private LoanQueryService    loanQueryService;

    @Autowired
    private UserService         userService;

    @Autowired
    private UserContactService  userContactService;

    @Autowired
    private UserCompanyService  userCompanyService;

    @Autowired
    private LoanCarService      loanCarService;

    @Autowired
    private LoanHouseService    loanHouseService;

    @Autowired
    private LoanCustService     loanCustService;

    @Autowired
    private GovernmentService   govService;
    @Autowired
    private EmployeeService employeeService;

    public void execute(Context context) {
        Integer loanId = Integer.valueOf(context.get("loanId").toString());
        if (loanId == null || loanId <= 0) {
            logger.warn("查看项目信息页面传入的loanId出错，loanId={}", loanId);
            return;
        }

        // 普通标信息
        PlainResult<Loan> loanResult = loanQueryService.queryById(loanId);
        if (!loanResult.isSuccess()) {
            logger.warn("项目信息查询失败:{}", loanResult.getMessage());
            return;
        }
        Loan loan = loanResult.getData();
        context.put("loan", loan);

        // 担保机构
        PlainResult<GovernmentDO> govResult = govService.findById(loan.getLoanGuarGov());
        if (govResult.isSuccess() && govResult.getData() != null) {
            context.put("gov", govResult.getData());
        } else {
            logger.info("没有查询到项目担保机构信息:{}, loanId={}", govResult.getMessage(), loanId);
        }

        //二次分配收款人
       /* if (loan.getLoanSecondaryUser() != null) {
            PlainResult<User> secondUserResult = userService.findEntityById(loan.getLoanSecondaryUser());
            User secondUser = secondUserResult.getData();
            context.put("secondUser", secondUser);
        }*/
        
        int k = Integer.valueOf(loan.getLoanSecondaryAllocation());//获取二次分配类型转化为整型
        if(k==0){
            if (loan.getLoanSecondaryUser() != null) {
                   User SecondUser = userService.findEntityById(loan.getLoanSecondaryUser()).getData();
                   context.put("SecondUserName", SecondUser.getUserRealName());
                }
        }
        if(k==1){
        	if (loan.getLoanSecondaryUser() != null) {
        		  // GovernmentDO govment = governmentService.findById(loan.getLoanSecondaryUser()).getData();
        		EmployeeDO employeeDO = employeeService.findById(loan.getLoanSecondaryUser()).getData();
        		GovernmentDO goverment = govService.findById(employeeDO.getEmpOrgId()).getData();
                context.put("SecondUserName", goverment.getGovName());
        	}
        }
        
        // 借款人
        PlainResult<User> userResult = userService.findEntityById(loan.getLoanUserId());
        if (!userResult.isSuccess()) {
            logger.warn("借款人信息查询失败:{}, loanId={}, userId={}", userResult.getMessage(), loanId, loan.getLoanUserId());
            return;
        } else {
            User user = userResult.getData();
            context.put("user", user);

            // 用户单位信息
            PlainResult<UserCompany> usrComRes = userCompanyService.queryUserCompanyByUserId(user.getUserId());
            UserCompany userCom = usrComRes.isSuccess() ? usrComRes.getData() : new UserCompany();
            context.put("userCom", userCom);

            // 用户联系方式信息
            PlainResult<UserContact> contactRes = userContactService.findUserContactByUserId(user.getUserId());
            UserContact contact = contactRes.isSuccess() ? contactRes.getData() : new UserContact();
            context.put("contact", contact);
        }

        // 根据loanId获取carList
        ListResult<LoanCar> carRes = loanCarService.queryByLoanId(loanId);

        ListResult<LoanHouse> houseRes = loanHouseService.queryByLoanId(loanId);
        if (carRes.isSuccess()) {
            context.put("carList", carRes.getData());
        }
        if (houseRes.isSuccess()) {
            context.put("houseList", houseRes.getData());
        }

    }
}

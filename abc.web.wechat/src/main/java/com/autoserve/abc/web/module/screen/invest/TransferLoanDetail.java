package com.autoserve.abc.web.module.screen.invest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserCompany;
import com.autoserve.abc.service.biz.entity.UserEducation;
import com.autoserve.abc.service.biz.entity.UserHouse;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.entity.UserOwner;
import com.autoserve.abc.service.biz.enums.UserBusinessState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedsendService;
import com.autoserve.abc.service.biz.intf.upload.FileUploadInfoService;
import com.autoserve.abc.service.biz.intf.user.UserCompanyService;
import com.autoserve.abc.service.biz.intf.user.UserEducationService;
import com.autoserve.abc.service.biz.intf.user.UserHouseService;
import com.autoserve.abc.service.biz.intf.user.UserOwnerService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.Arith;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.module.screen.Verification.json.CheckMoneyMoreMore;

public class TransferLoanDetail {
	@Resource
	private LoanService loanService;
	@Resource
	private UserService userService;
	@Resource
	private InvestQueryService investQueryService;
	@Autowired
	private HttpSession session;
	@Resource
	private DoubleDryService doubleDryService;
	@Resource
	private GovernmentService governmentService;
	@Resource
	private AccountInfoService accountInfoService;
	@Resource
	private DeployConfigService deployConfigService;
	@Resource
	private UserHouseService userHouseService;
	@Resource
	LoanQueryService loanQueryService;
	@Resource
	CheckMoneyMoreMore checkMoneyMoreMore;
	@Resource
	TransferLoanService transferLoanService;
	@Autowired
	private UserHouseService userhouseservice;
	@Autowired
	private UserCompanyService usercompanyservice;
	@Autowired
	private UserOwnerService userownerservice;
	@Autowired
	private UserEducationService usereducationservice;
	@Autowired
	private RedsendService redsendService;
	@Autowired
	private FileUploadInfoService fileUploadInfoService;

	public void execute(Context context, Navigator nav, ParameterParser params) {
		User user = (User) session.getAttribute("user");
		if(user!=null){
			context.put("userType", user.getUserType().getType());
		}
		Integer transferId = params.getInt("transferId");

		PlainResult<Loan> resu = new PlainResult<Loan>();

		Loan loan = new Loan();

		PlainResult<TransferLoan> result = null;
		TransferLoan transferLoan = new TransferLoan();
		transferLoan.setId(transferId);
		int loanId = this.transferLoanService.queryByParam(transferLoan)
				.getData().getOriginId();
		loan.setLoanId(loanId);
		resu = this.loanQueryService.queryByParam(loan);
		result = this.transferLoanService.queryByParam(transferLoan);

		context.put("loan", resu.getData());
		context.put("tLoan", result.getData());

//		context.put("timelimit", DateUtil.substractDay(resu.getData()
//				.getLoanExpireDate(), new Date()));
		Date end = resu.getData().getLoanExpireDate();
		int diff = 0;
		diff = DateUtil.diff(result.getData().getCreatetime(), end, TimeUnit.DAYS) + 1;
		
		context.put("timelimit", diff);
		context.put("loanCurrentInvestPercent", Arith.calcPercent(result
				.getData().getCurrentValidInvest(), result.getData()
				.getTransferMoney()));

		// 查询借款人信息
		PlainResult<UserDO> loanUserDO = this.userService.findById(resu
				.getData().getLoanUserId());
		context.put("loanUser", loanUserDO.getData());
		// 2、房產資料：
		PlainResult<UserHouse> userhouse = userhouseservice
				.findUserHouseByUserId(resu.getData().getLoanUserId());
		// 3、單位資料：
		PlainResult<UserCompany> usercompany = usercompanyservice
				.queryUserCompanyByUserId(resu.getData().getLoanUserId());
		// 4、私營業主資料：
		PlainResult<UserOwner> userowner = userownerservice
				.findUserOwnerByUserId(resu.getData().getLoanUserId());
		// 6、教育背景：
		PlainResult<UserEducation> usereducation = usereducationservice
				.findUserEducationByUserId(resu.getData().getLoanUserId());

		context.put("userhouse", userhouse);
		context.put("usercompany", usercompany);
		context.put("userowner", userowner);
		context.put("usereducation", usereducation);

		// 查询担保机构
		PlainResult<GovernmentDO> loanGuarGov = this.governmentService
				.findById(resu.getData().getLoanGuarGov());
		context.put("loanGuarGov", loanGuarGov.getData());

		// 参数回传
		context.put("loanId", loanId);
		context.put("transferId", transferId);

	}
}

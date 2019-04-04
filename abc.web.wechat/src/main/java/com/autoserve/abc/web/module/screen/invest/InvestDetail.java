package com.autoserve.abc.web.module.screen.invest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.RedSearchDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.Redsend;
import com.autoserve.abc.service.biz.entity.RedsendJ;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserCompany;
import com.autoserve.abc.service.biz.entity.UserEducation;
import com.autoserve.abc.service.biz.entity.UserHouse;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.entity.UserOwner;
import com.autoserve.abc.service.biz.enums.RedState;
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
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.Arith;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.module.screen.Verification.json.CheckMoneyMoreMore;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.util.MathUtil;

/**
 * 投资详情
 */
public class InvestDetail {

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

		Integer loanId = params.getInt("loanId");
		Integer transferId = params.getInt("transferId");
		Integer flagLoan = params.getInt("flagLoan");

		PlainResult<Loan> resu = new PlainResult<Loan>();
//		TransferLoan transferLoan = new TransferLoan();

		// 判断用户是否登录
		User user = (User) session.getAttribute("user");
		if (user != null) { // 用户登录,可见部分详情
			context.put("username", user.getUserName());
			if (user.getUserBusinessState() != UserBusinessState.REGISTERED
					&& user.getUserBusinessState() != null) {
				UserIdentity userIdentity = new UserIdentity();
				userIdentity.setUserId(user.getUserId());

				// 判断用户属于个人用户还是企业用户
				if (user.getUserType() == null
						|| user.getUserType().getType() == 1) {
					user.setUserType(UserType.PERSONAL);
					context.put("userType", UserType.PERSONAL.getType());
				} else {
					user.setUserType(UserType.ENTERPRISE);
					context.put("userType", UserType.ENTERPRISE.getType());
				}
				userIdentity.setUserType(user.getUserType());
				
				
			}
			context.put("user", user);
			// 根据userId获得该用户的UserDO,获得交易密码
			// PlainResult<UserDO> userDO
			// =userService.findById(user.getUserId());
			// String userDealPwd = userDO.getData().getUserDealPwd();
			// context.put("userDealPwd", userDealPwd);
		}
		/******************************** 公共部分 ***********************************************/
		// 以下为该项目的详细信息
		Loan loan = new Loan();
		String loanFileUrl = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currenttime = sdf.format(new Date());
		// 普通标详情页
		if (loanId != null && loanId > 0 && flagLoan == 1) {
			loan.setLoanId(loanId);
			resu = this.loanQueryService.queryByParam(loan);
			loanFileUrl = resu.getData().getLoanFileUrl();
			resu.getData().getLoanFileUrl();
			context.put("loanCreatetime",
					DateUtil.formatDate(resu.getData().getLoanCreatetime()));
			context.put("loanInvestEndtime",
					DateUtil.formatDate(resu.getData().getLoanInvestEndtime()));
			context.put("loan", resu.getData());
			context.put("currenttime", currenttime);
			context.put("sdf", sdf);
			context.put("loanNO", resu.getData().getLoanNo());
			context.put("loanUse", resu.getData().getLoanUse());
			context.put("loanMoney", resu.getData().getLoanMoney());
			context.put("loanRate", resu.getData().getLoanRate());
			context.put("loanPeriod", resu.getData().getLoanPeriod());
			context.put("loanPeriodUnit", resu.getData().getLoanPeriodUnit()
					.getUnit());
			context.put("loanCurrentInvest", resu.getData()
					.getLoanCurrentValidInvest());
			context.put("loanCurrentInvestPercent", Arith.calcPercent(resu
					.getData().getLoanCurrentValidInvest(), resu.getData()
					.getLoanMoney(), resu.getData().getLoanState()));
			context.put("loan", resu.getData());
			context.put("resloanState", resu.getData().getLoanState().state);

			long loanInvestEndtime = resu.getData().getLoanInvestEndtime()
					.getTime();
			long loanInvestStarttime = resu.getData().getLoanInvestStarttime()
					.getTime();
			long nowTime = new Date().getTime();
			long durStartTime = loanInvestStarttime - nowTime; // 距离投标开始时间
			long durEndTime = loanInvestEndtime - nowTime; // 距离投标结束的时间
			context.put("durStartTime", durStartTime);
			context.put("durEndTime", durEndTime);

			if (user != null) {
				// 普通标可以使用红包
				Redsend redsend = new Redsend();
				redsend.setRsUserid(user.getUserId());
				RedSearchDO redSearchDO = new RedSearchDO();
				PageCondition pageConditionx = new PageCondition(1, 65535);
				redSearchDO.setUserId(user.getUserId());
				redSearchDO.setUserScope(resu.getData().getLoanCategory()
						.getPrompt());
				redSearchDO.setRsState(RedState.EFFECTIVE.getState());
				redSearchDO.setRsClosetime(new SimpleDateFormat("yyyy-MM-dd")
						.format(new Date()));
				redSearchDO.setOrder("rs_closetime ASC");
				PageResult<RedsendJ> pageResult = redsendService.queryListJ(
						redSearchDO, pageConditionx);
				context.put("redSendList", pageResult.getData());
			}
		}

		// 转让标详情页
//		PlainResult<TransferLoan> result = null;
//		if (transferId != null && transferId > 0 && flagLoan == 2) {
//			transferLoan.setId(transferId);
//			loanId = this.transferLoanService.queryByParam(transferLoan)
//					.getData().getOriginId();
//			loan.setLoanId(loanId);
//			resu = this.loanQueryService.queryByParam(loan);
//			loanFileUrl = resu.getData().getLoanFileUrl();
//			result = this.transferLoanService.queryByParam(transferLoan);
//			context.put("loanCreatetime",
//					DateUtil.formatDate(result.getData().getCreatetime()));
//			context.put("loanInvestEndtime",
//					DateUtil.formatDate(result.getData().getInvestEndtime()));
//			context.put("loan", resu.getData());
//			context.put("transferLoan", result.getData());
//			context.put("loanNO", resu.getData().getLoanNo());
//			context.put("loanUse", resu.getData().getLoanUse());
//			context.put("loanMoney", result.getData().getTransferMoney());
//			context.put("loanRate", result.getData().getTransferRate());
//			context.put("loanPeriod", result.getData().getTransferPeriod());
//
//			long end = resu.getData().getLoanExpireDate().getTime();
//			long now = System.currentTimeMillis();
//			context.put("timelimit", (end - now) / (1000 * 60 * 60 * 24));
//
//			context.put("loanPeriodUnit", resu.getData().getLoanPeriodUnit()
//					.getUnit());
//			context.put("loanCurrentInvest", result.getData()
//					.getCurrentValidInvest());
//			// context.put(
//			// "loanCurrentInvestPercent",
//			// result.getData()
//			// .getCurrentValidInvest()
//			// .divide(result.getData().getTransferMoney(), 50,
//			// BigDecimal.ROUND_HALF_UP)
//			// .multiply(new BigDecimal(100)));
//			context.put("loanCurrentInvestPercent", Arith.calcPercent(result
//					.getData().getCurrentValidInvest(), result.getData()
//					.getTransferMoney()));
//			context.put("transferloan", result.getData());
//		}

		// 10000元 每天多少钱
//		double perDayMoney = MathUtil.div(
//				MathUtil.mul(10000, MathUtil.div(resu.getData().getLoanRate()
//						.doubleValue(), 100, 4)), 365, 10);
//		// 总共多少钱
//		int days = 0;
//		if (resu.getData().getLoanPeriodUnit().getUnit() == 1) { // 年
//			days = resu.getData().getLoanPeriod() * 365;
//		} else if (resu.getData().getLoanPeriodUnit().getUnit() == 2) { // 月
//			days = resu.getData().getLoanPeriod() * 30;
//		} else { // 日
//			days = resu.getData().getLoanPeriod();
//		}
//		double income = MathUtil.round(MathUtil.mul(perDayMoney, days), 2);
//		context.put("income", income);

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
		context.put("flagLoan", flagLoan);
		context.put("loanId", loanId);
		context.put("transferId", transferId);

	}
}

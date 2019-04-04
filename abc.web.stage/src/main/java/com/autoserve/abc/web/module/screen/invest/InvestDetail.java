package com.autoserve.abc.web.module.screen.invest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.common.PageCondition.Order;
import com.autoserve.abc.dao.dataobject.AssessLevelDO;
import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.dao.dataobject.FileUploadInfoDO;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchDO;
import com.autoserve.abc.dao.dataobject.search.RedSearchDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.entity.Redsend;
import com.autoserve.abc.service.biz.entity.RedsendJ;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserCompany;
import com.autoserve.abc.service.biz.entity.UserEducation;
import com.autoserve.abc.service.biz.entity.UserHouse;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.entity.UserOwner;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.FileUploadSecondaryClass;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.RedState;
import com.autoserve.abc.service.biz.enums.UserBusinessState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.AuthorizeUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedsendService;
import com.autoserve.abc.service.biz.intf.upload.FileUploadInfoService;
import com.autoserve.abc.service.biz.intf.user.UserAssessService;
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
import com.autoserve.abc.web.util.Pagebean;
import com.google.common.collect.Lists;

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
	@Resource
	private PaymentPlanService paymentPlanService;
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
	@Resource
	private UserAssessService userAssessService;
	
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
		// 投资记录标志
		String tzxmFlag = params.getString("tzxm");

		// 准备分页数据
		int currentPage = params.getInt("currentPage");
		int pageSize = params.getInt("pageSize");
		if (currentPage == 0)
			currentPage = 1;
		pageSize = 10;
		PageCondition pageCondition = new PageCondition(currentPage, pageSize);

		PlainResult<Loan> resu = new PlainResult<Loan>();
		TransferLoan transferLoan = new TransferLoan();

		// 判断用户是否登录
		User user = (User) session.getAttribute("user");
		if (user != null) { // 用户登录,可见部分详情
			user = userService.findEntityById(user.getUserId()).getData();
			context.put("username", user.getUserName());
//			if (user.getUserBusinessState() != UserBusinessState.REGISTERED
//					&& user.getUserBusinessState() != null) {
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
			
				//String accountMark = account.getData().getAccountMark();
				//投资账户
		    	userIdentity.setAccountCategory(AccountCategory.INVESTACCOUNT.getType());
			   	PlainResult<Account> account = accountInfoService.queryByUserId(userIdentity);
		   		String accountNo = account.getData().getAccountNo();
		   		context.put("accountNo", accountNo);
				// 网贷平台子账户可用余额|总可用余额(子账户可用余额+公共账户可用余额)|子账户冻结余额”（例:100.00|200.00|10.00）
				Double[] accountBacance = { 0.00, 0.00, 0.00 };
				if (accountNo != null && !"".equals(accountNo)) {
		            accountBacance = this.doubleDryService.queryBalance(accountNo, "1");
		            userService.modifyUserBusinessState(account.getData().getAccountUserId(),
		                    account.getData().getAccountUserType(), UserBusinessState.ACCOUNT_OPENED);
		        }
				context.put("accountBacance", accountBacance);
//			}
			context.put("user", user);
			//投标授权类型 11、投标 59、缴费 60、还款
	    	String authorizeInvest="";
	    	if("11".equals(user.getAuthorizeInvestType())){
	    		authorizeInvest=AuthorizeUtil.isAuthorize(user.getAuthorizeInvestStartDate(),user.getAuthorizeInvestEndDate());
	    	}
	    	context.put("authorizeInvest", authorizeInvest);
			// 根据userId获得该用户的UserDO,获得交易密码
			// PlainResult<UserDO> userDO
			// =userService.findById(user.getUserId());
			// String userDealPwd = userDO.getData().getUserDealPwd();
			// context.put("userDealPwd", userDealPwd);
		}
		/******************************** 公共部分 ***********************************************/
		// 以下为该项目的详细信息
		Loan loan = new Loan();
		int diff = 0;
		
		String loanFileUrl = null;
		// 普通标详情页
		if (loanId != null && loanId > 0 && flagLoan == 1) {
			loan.setLoanId(loanId);
			resu = this.loanQueryService.queryByParam(loan);
			loanFileUrl = resu.getData().getLoanFileUrl();
			resu.getData().getLoanFileUrl();
//			context.put("loanCreatetime",
//					DateUtil.formatDate(resu.getData().getLoanCreatetime()));
			context.put("loanReleaseTime", DateUtil.formatDate(resu.getData().getLoanReleaseDate(), DateUtil.DATE_TIME_FORMAT));
			context.put("loanInvestEndtime",
					DateUtil.formatDate(resu.getData().getLoanInvestEndtime()));
			context.put("loan", resu.getData());
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
					.getLoanMoney()));

			switch (resu.getData().getLoanPeriodUnit().getUnit()) 
			{
				case 1 : diff = 360 * resu.getData().getLoanPeriod();break;
				case 2 : diff = 30 * resu.getData().getLoanPeriod();break;
				case 3 : diff = resu.getData().getLoanPeriod();break;
			};

			long loanInvestEndtime = resu.getData().getLoanInvestEndtime()
					.getTime();
			long loanInvestStarttime = resu.getData().getLoanInvestStarttime()
					.getTime();
			long nowTime = new Date().getTime();
			long durStartTime = loanInvestStarttime - nowTime; // 距离投标开始时间
			long durEndTime = loanInvestEndtime - nowTime; // 距离投标结束的时间
//			long durStartTime = FormatHelper.compareToDate(resu.getData().getLoanInvestStarttime(), new Date());
//			long durEndTime = FormatHelper.compareToDate(resu.getData().getLoanInvestEndtime(), new Date());
			context.put("durStartTime", durStartTime);
			context.put("durEndTime", durEndTime);
			
			AssessLevelDO assLevelDO = null;
	    	if (null != resu.getData().getAssId())
	    	{
	    		assLevelDO = userAssessService.findById(Integer.valueOf(resu.getData().getAssId()));
	    	}
	    	context.put("assLevel", assLevelDO);

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
				
				double redTotal = 0.0;
				if (null != pageResult && null != pageResult.getData())
				{
					for (RedsendJ red : pageResult.getData())
					{
						redTotal += red.getRsValidAmount();
					}
				}
				
				context.put("redTotal", redTotal);
				context.put("redSendList", pageResult.getData());
			}
		}

		// 转让标详情页
		PlainResult<TransferLoan> result = null;
		if (transferId != null && transferId > 0 && flagLoan == 2) {
			transferLoan.setId(transferId);
			loanId = this.transferLoanService.queryByParam(transferLoan)
					.getData().getOriginId();
			loan.setLoanId(loanId);
			resu = this.loanQueryService.queryByParam(loan);
			loanFileUrl = resu.getData().getLoanFileUrl();
			result = this.transferLoanService.queryByParam(transferLoan);
//			context.put("loanCreatetime",
//					DateUtil.formatDate(result.getData().getCreatetime()));
			context.put("loanReleaseTime",
					DateUtil.formatDate(result.getData().getReleaseDate(), DateUtil.DATE_TIME_FORMAT));
			
			context.put("loanInvestEndtime",
					DateUtil.formatDate(resu.getData().getLoanInvestEndtime()));
			context.put("loan", resu.getData());
			context.put("transferLoan", result.getData());
			context.put("loanNO", resu.getData().getLoanNo());
			context.put("loanUse", resu.getData().getLoanUse());
			context.put("loanMoney", result.getData().getTransferMoney());
			context.put("loanRate", result.getData().getTransferRate());
			context.put("loanPeriod", result.getData().getTransferPeriod());

			Date end = resu.getData().getLoanExpireDate();
			diff = DateUtil.diff(result.getData().getCreatetime(), end, TimeUnit.DAYS) + 1;
			
			context.put("timelimit", diff);

			context.put("loanPeriodUnit", resu.getData().getLoanPeriodUnit()
					.getUnit());
			context.put("loanCurrentInvest", result.getData()
					.getCurrentValidInvest());
			context.put("loanCurrentInvestPercent", Arith.calcPercent(result
					.getData().getCurrentValidInvest(), result.getData()
					.getTransferMoney()));
			context.put("transferloan", result.getData());
			
			AssessLevelDO assLevelDO = null;
	    	if (null != resu.getData().getAssId())
	    	{
	    		assLevelDO = userAssessService.findById(Integer.valueOf(resu.getData().getAssId()));
	    	}
	    	context.put("assLevel", assLevelDO);
		}

		// 查询投资该项目的投资人
		InvestSearchDO investSearchDO = new InvestSearchDO();
		// 投资状态为2:支付成功,4:待收益 5:被转让 6:被收购 7:收益完成
		investSearchDO.setInvestStates(Arrays.asList(InvestState.PAID.state,
				InvestState.EARNING.state, InvestState.TRANSFERED.state,
				InvestState.BUYED.state, InvestState.EARN_COMPLETED.state));

//		investSearchDO.setLoanName(resu.getData().getLoanNo());
		if (flagLoan != null && flagLoan == 1) {
			investSearchDO.setBidId(resu.getData().getLoanId());
			investSearchDO.setBidType(BidType.COMMON_LOAN.getType());
		} else if (flagLoan != null && flagLoan == 2) {
			investSearchDO.setBidId(result.getData().getId());
			investSearchDO.setBidType(BidType.TRANSFER_LOAN.getType());
		}
		PageResult<Invest> invests = this.investQueryService.queryInvestList(
				investSearchDO, pageCondition);
		context.put("count", invests.getTotalCount()); // 投资数
		List<Invest> investList = invests.getData();
		List<InvestVO> investVOs = new ArrayList<InvestVO>();
		for (Invest invest : investList) {
			InvestVO investVO = new InvestVO();
			String userName = userService.findById(invest.getUserId())
					.getData().getUserName();
			investVO.setUserName(userName);
			String createTime = DateUtil.formatDate(invest.getCreatetime(),
					"yyyy-MM-dd HH:mm:ss");
			investVO.setCreatetime(createTime);
			investVO.setInvestState(invest.getInvestState());
			investVO.setInvestMoney(invest.getInvestMoney());
			investVO.setPrizeType(invest.getPrizeType());
			investVOs.add(investVO);
		}
		// 投资记录的分页
		Pagebean<InvestVO> pagebean = new Pagebean<InvestVO>(currentPage,
				pageSize, investVOs, invests.getTotalCount());
		context.put("pagebean", pagebean);
		if (tzxmFlag != null && !"".equals(tzxmFlag)) {
			context.put("tzxmFlag", tzxmFlag);
		}

		// 10000元 每天多少钱
		// double perDayMoney = MathUtil.div(MathUtil.mul(10000,
		// MathUtil.div(resu.getData().getLoanRate().doubleValue(), 100, 4)),
		// 365, 10);
		// 总共多少钱
		// int days = 0;
		// if(resu.getData().getLoanPeriodUnit().getUnit() == 1) { // 年
		// days = resu.getData().getLoanPeriod() * 365;
		// } else if(resu.getData().getLoanPeriodUnit().getUnit() == 2) { // 月
		// days = resu.getData().getLoanPeriod() * 30;
		// } else { // 日
		// days = resu.getData().getLoanPeriod();
		// }
		// double income = MathUtil.round(MathUtil.mul(perDayMoney, days), 2);
		// context.put("income", income);

		BigDecimal yearRate = resu.getData().getLoanRate();
//		int diff = DateUtil.diff(resu.getData().getLoanCreatetime(), resu
//				.getData().getLoanExpireDate(), TimeUnit.DAYS) + 1;
		// 公式： 10000*(年率/360)*天数
		BigDecimal income = yearRate.multiply(new BigDecimal("100"))
				.multiply(new BigDecimal(diff + ""))
				.divide(new BigDecimal("360"), 2);
		context.put("income", income);
		
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
		
		//贷后还款计划信息
		PaymentPlan paymentPlan = new PaymentPlan();
		paymentPlan.setLoanId(loanId);
		PageResult<PaymentPlan> paymentbean = paymentPlanService.queryPaymentPlanList(paymentPlan,
				new PageCondition(1, 65535, "loanPeriod", Order.ASC));
		context.put("paymentbean", paymentbean);

		/**
		 * IMAGE_DATA(1, "影像资料"), GUA_DATA(2, "担保机构"), QUA_DATA(3, "企业实地"),
		 * SPOT_DATA(4, "企业资质"), OTHER_DATA(5, "其他"), SAFE_DATA(6, "风控资料");
		 */

		// 查询相关图片
		Map<String, List<FileUploadInfoDO>> picGroup = new HashMap<String, List<FileUploadInfoDO>>();
		List<FileUploadInfoDO> guas = fileUploadInfoService.findListByFileUrl(
				loanFileUrl, FileUploadSecondaryClass.GUA_DATA.getType());
		List<FileUploadInfoDO> quas = fileUploadInfoService.findListByFileUrl(
				loanFileUrl, FileUploadSecondaryClass.QUA_DATA.getType());
		List<FileUploadInfoDO> spots = fileUploadInfoService.findListByFileUrl(
				loanFileUrl, FileUploadSecondaryClass.SPOT_DATA.getType());
		List<FileUploadInfoDO> others = fileUploadInfoService.findListByFileUrl(
				loanFileUrl, FileUploadSecondaryClass.IMAGE_DATA.getType());
		List<FileUploadInfoDO> safes = fileUploadInfoService.findListByFileUrl(
				loanFileUrl, FileUploadSecondaryClass.SAFE_DATA.getType());
		List<FileUploadInfoDO> posts = fileUploadInfoService.findListByFileUrl(
				loanFileUrl, FileUploadSecondaryClass.POST_DATA.getType());
		
		picGroup.put("guas", pickPicture(guas));
		picGroup.put("quas", pickPicture(quas));
		picGroup.put("spots", pickPicture(spots));
		picGroup.put("others", pickPicture(others));
		picGroup.put("safes", pickPicture(safes));
		picGroup.put("posts", pickPicture(posts));

		context.put("picGroup", picGroup);

		// 参数回传
		context.put("flagLoan", flagLoan);
		context.put("loanId", loanId);
		context.put("transferId", transferId);
	}
	
	/**
	 * 选取图片文件
	 * @param list
	 * @return
	 */
	List<FileUploadInfoDO> pickPicture(List<FileUploadInfoDO> list){
		List<FileUploadInfoDO> result = Lists.newArrayList();
		if(list!=null && list.size()!=0){
			for(FileUploadInfoDO file:list){
				if(isPicture(file.getFuiFileName())){
					result.add(file);
				}
			}
		}
		return result;
	}
	/**
	 * 判断文件名是否为图片
	 * @param fileName
	 * @return
	 */
	private boolean isPicture(String fileName) {
		if(StringUtils.isBlank(fileName)){
			return false;
		}
		Pattern p = Pattern.compile(".+\\.(jpg|gif|bmp|png|jpeg)", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(fileName);
		return m.matches();
	}
	

}

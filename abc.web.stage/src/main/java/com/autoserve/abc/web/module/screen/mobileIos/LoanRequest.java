package com.autoserve.abc.web.module.screen.mobileIos;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.TransferLoanJDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchDO;
import com.autoserve.abc.dao.dataobject.search.LoanSearchDO;
import com.autoserve.abc.dao.dataobject.search.RedSearchDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.Redsend;
import com.autoserve.abc.service.biz.entity.RedsendJ;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.FileUploadSecondaryClass;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.RedState;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedsendService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.upload.FileUploadInfoService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.Arith;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.util.DateUtils;
import com.autoserve.abc.web.util.Pagebean;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 项目投资
 * 
 * @author tabor
 */
public class LoanRequest {

	@Resource
	private LoanQueryService loanQueryService;
	@Resource
	private UserService userService;
	@Resource
	private AccountInfoService accountInfoService;
	@Resource
	private InvestQueryService investQueryService;
	@Resource
	private InvestService investService;
	@Resource
	private DealRecordService dealRecordService;
	@Resource
	private DoubleDryService doubleDryService;
	@Resource
	private FileUploadInfoService fileUploadInfoService;
	@Resource
	private RedsendService redsendService;
    @Resource
    private TransferLoanService transferLoanService;
    @Resource
	private SysConfigService sysConfigService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public JsonMobileVO execute(Context context, ParameterParser params)
			throws IOException {
		JsonMobileVO result = new JsonMobileVO();

		try {
			String catalog = params.getString("catalog");

			if ("1".equals(catalog)) {
				// 投资列表
				String loanType = params.getString("prodId");// 标的类型 1 2 3
																// 4，信用标，担保标等，不传查询所有
				Integer pageSize = params.getInt("pageSize");
				Integer showPage = params.getInt("showPage");
				Map<String, Object> objMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();
				Map<String, Object> loanMap = new HashMap<String, Object>();
				LoanSearchDO searchDO = new LoanSearchDO();
				if (!StringUtils.isBlank(loanType)) {
					searchDO.setLoanCategory(Integer.parseInt(loanType));
				}
				searchDO.setLoanState(Arrays.asList(
						LoanState.BID_INVITING.state,
						LoanState.FULL_WAIT_REVIEW.state,
						LoanState.FULL_REVIEW_PASS.state,
						LoanState.FULL_REVIEW_FAIL.state,
						LoanState.BID_CANCELED.state,
						LoanState.MONEY_TRANSFERING.state,
						LoanState.REPAYING.state));
				PageCondition pageCondition = new PageCondition(showPage,
						pageSize);
				searchDO.setFlag(0);// 排序
				searchDO.setIsShow(1);
				PageResult<Loan> pageResult = this.loanQueryService
						.queryLoanListBySearchParam(searchDO, pageCondition);
				Pagebean<Loan> pagebean = new Pagebean<Loan>(showPage,
						pageSize, pageResult.getData(),
						pageResult.getTotalCount());
				// 百分比
				Map<Integer, BigDecimal> resultLoanListMap = new HashMap<Integer, BigDecimal>();
				for (Loan temp : pagebean.getRecordList()) {
					BigDecimal percent = Arith.calcPercent(
							temp.getLoanCurrentInvest(), temp.getLoanMoney());
					resultLoanListMap.put(temp.getLoanId(), percent);
				}

				for (Loan loan3 : pagebean.getRecordList()) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("loanId", loan3.getLoanId());
					loanMap.put("loanTitle", loan3.getLoanNo());
					loanMap.put("loanMoney", loan3.getLoanMoney());
					loanMap.put("loanRate", loan3.getLoanRate());
					loanMap.put("loanPeriod", loan3.getLoanPeriod()
							+ loan3.getLoanPeriodUnit().getPrompt());
					loanMap.put(
							"loanProgress",
							Arith.calcPercent(
									loan3.getLoanCurrentValidInvest(),
									loan3.getLoanMoney()).intValue());
					loanMap.put("loanType", loan3.getLoanCategory().category);
					loanMap.put("loanInvestEndTime",
							DateUtil.formatDate(loan3.getLoanInvestEndtime()));
					loanMap.put("loanExpireTime",
							DateUtil.formatDate(loan3.getLoanExpireDate()));
					loanList.add(loanMap);
				}
				objMap.put("pageCount", pagebean.getRecordCount());
				objMap.put("list", JSON.toJSON(loanList));
				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("2".equals(catalog)) {
				// 投资详情
				// /mobile/loanRequest.json?catalog=2&loanId=474&userId=133
				Integer loanId = params.getInt("loanId");
				Integer userId = params.getInt("userId");
				
				Loan loanParam = new Loan();
				loanParam.setLoanId(loanId);
				Loan loan = loanQueryService.queryByParam(loanParam).getData();

				Map<String, Object> objMap = new HashMap<String, Object>();
				if(userId!=0){
					PlainResult<UserDO> userDoResult=userService.findById(userId);
					UserDO userDO = userDoResult.getData();
					// 获取用户信息
					if(UserType.PERSONAL.type==userDO.getUserType()){
						userDO.setAccountCategory(AccountCategory.INVESTACCOUNT.type);
						AccountInfoDO investAccount = accountInfoService.getAccountByCategory(userDO);
						if (null == investAccount || StringUtils.isEmpty(investAccount.getAccountNo())) {
							objMap.put("invest_isOpenAccount", "0");
						} else {
							objMap.put("invest_isOpenAccount", "1");
						}
					}
					userDO.setAccountCategory(AccountCategory.LOANACCOUNT.type);
					AccountInfoDO loanAccount = accountInfoService.getAccountByCategory(userDO);
					if (null == loanAccount || StringUtils.isEmpty(loanAccount.getAccountNo())) {
						objMap.put("loan_isOpenAccount", "0");
					} else {
						objMap.put("loan_isOpenAccount", "1");
					}
//					if(userDoResult.getData().getUserBusinessState()==null ||userDoResult.getData().getUserBusinessState()<2){
//						objMap.put("isOpenAccount", "0");//未开户
//					}else{
//						objMap.put("isOpenAccount", "1");//已开户
//					}
				}
				objMap.put("loanId", loan.getLoanId());
				objMap.put("loanTitle", loan.getLoanNo());
				objMap.put("loanStartTime",
						DateUtil.formatDate(loan.getLoanInvestStarttime()));
				objMap.put("loanMoney", loan.getLoanMoney());
				objMap.put("loanRate", loan.getLoanRate());
				objMap.put("effectiveDate",
						DateUtil.formatDate(loan.getLoanExpireDate()));
				objMap.put("loanPayType", loan.getLoanPayType().getPrompt());
				objMap.put("currentInvest", loan.getLoanCurrentValidInvest());
				objMap.put(
						"currentValidInvest",
						loan.getLoanMoney().subtract(
								loan.getLoanCurrentValidInvest()));
				objMap.put("period", loan.getLoanPeriod());
				objMap.put("period_type", loan.getLoanPeriodUnit());
				objMap.put("investEndtime",
						DateUtil.formatDate(loan.getLoanInvestEndtime()));
				objMap.put("minInvest", loan.getLoanMinInvest());
				objMap.put("maxInvest", loan.getLoanMaxInvest());
				objMap.put("loanState", loan.getLoanState().state);
				objMap.put("redRatio", loan.getInvestReduseRatio()); // 可使用红包比例
				if (userId != 0 && loan.getInvestReduseRatio() > 0) {
					List<RedsendJ> redList = this.avaiRedList(userId, loan);
					List<Map<String, Object>> list = Lists.newArrayList();
					for (RedsendJ red : redList) {
						Map<String, Object> map = Maps.newHashMap();
						map.put("id", red.getRsId());
						map.put("validAmount", red.getRsValidAmount());
						map.put("closeTime", DateUtil.formatDate(
								red.getRsClosetime(),
								DateUtil.DEFAULT_DAY_STYLE));
						list.add(map);
					}
					objMap.put("redList", list);
				}
				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("3".equals(catalog)) {
				// 投标记录
				// /mobile/loanRequest.json?catalog=3&loanId=609
				Integer loanId = params.getInt("loanId");
				Integer pageSize = params.getInt("pageSize");
				Integer showPage = params.getInt("showPage");

				InvestSearchDO searchDO = new InvestSearchDO();
				searchDO.setOriginId(loanId);
				searchDO.setBidType(BidType.COMMON_LOAN.getType());
				PageResult<Invest> pageResult = investQueryService
						.queryInvestList(searchDO, new PageCondition(showPage,
								pageSize));
				List<Invest> list = pageResult.getData();

				Map<String, Object> objMap = new HashMap<String, Object>();
				Map<String, Object> loanMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();

				for (Invest invest : list) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("investor",
							userService.findEntityById(invest.getUserId())
									.getData().getUserName());
					loanMap.put("investTime", new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss").format(invest
							.getCreatetime()));
					loanMap.put("investMoney", invest.getInvestMoney());
					loanList.add(loanMap);
				}

				// for (int i = 0; i < 1; i++) {
				// loanMap = new HashMap<String, Object>();
				// loanMap.put("investor", "xxxx");
				// loanMap.put("investTime", "2015-12-15 17:08:20");
				// loanMap.put("investAmount", "180.00");
				// loanList.add(loanMap);
				// }

				objMap.put("pageCount", pageResult.getTotalCount());
				objMap.put("list", JSON.toJSON(loanList));

				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("4".equals(catalog)) {
				// 还款计划
//				Map<String, Object> objMap = new HashMap<String, Object>();
//				Map<String, Object> loanMap = new HashMap<String, Object>();
//				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();
//
//				for (int i = 0; i < 11; i++) {
//					loanMap = new HashMap<String, Object>();
//					loanMap.put("planDate", "2015-12-15 17:08:20");
//					loanMap.put("interest", "5.00");
//					loanMap.put("pricipal", "200.00");
//					loanList.add(loanMap);
//				}
//
//				objMap.put("pageCount", "1");
//				objMap.put("list", JSON.toJSON(loanList));
//
//				result.setResultCode("200");
//				result.setResult(JSON.toJSON(objMap));
			} else if ("5".equals(catalog)) {
				// 投资操作
				// /mobile/loanRequest.json?catalog=5&userId=229&loanId=424&investAmount=200&dealPwd=123456
				Integer userId = params.getInt("userId");
				Integer loanId = params.getInt("loanId");
				Double investMoney = params.getDouble("investAmount");
				String dealPwd = params.getString("dealPwd");

				if (userId == 0) {
					result.setResultCode("201");
					result.setResultMessage("请求用户id不能为空");
					return result;
				} else if (loanId == 0) {
					result.setResultCode("201");
					result.setResultMessage("请求项目id不能为空");
					return result;
				} else if (investMoney <= 0) {
					result.setResultCode("201");
					result.setResultMessage("请输入投资金额");
					return result;
				} else if (dealPwd == null || "".equals(dealPwd)) {
					result.setResultCode("201");
					result.setResultMessage("请输入交易密码");
					return result;
				}
				// 检查用户信息
//				if (!MobileHelper.check(userService, userId, result)
//						.isSuccess()) {
//					return result;
//				}

				// 投资
				User user = userService.findEntityById(userId).getData();
				if (user != null) { // 校验交易密码
					PlainResult<UserDO> userDoResult = userService
							.findById(user.getUserId());
					if (dealPwd == null
							|| !CryptUtils.md5(dealPwd).equals(
									userDoResult.getData().getUserDealPwd())) {
						result.setResultCode("201");
						result.setResultMessage("交易密码不正确!");
						return result;
					}
				}
				// 判断账户余额
				UserIdentity userIdentity = new UserIdentity();
				userIdentity.setUserId(user.getUserId());
				if (user.getUserType() == null
						|| user.getUserType().getType() == 1) {
					user.setUserType(UserType.PERSONAL);
				} else {
					user.setUserType(UserType.ENTERPRISE);
				}
				userIdentity.setUserType(user.getUserType());
				Account account = accountInfoService
						.queryByUserId(userIdentity).getData();
				String accountNo = account.getAccountNo();

				// 网贷平台子账户可用余额|总可用余额(子账户可用余额+公共账户可用余额)|子账户冻结余额”（例:100.00|200.00|10.00）
				Double[] accountBacance = doubleDryService.queryBalance(
						accountNo, "1");
				if (investMoney - accountBacance[1] > 0) {
					result.setResultCode("201");
					result.setResultMessage("可用余额不足，请充值!");
					return result;
				}

				BidType bidType = BidType.COMMON_LOAN;

				Invest inv = new Invest();
				inv.setUserId(userId);
				inv.setBidType(bidType); // 设置标的类型
				inv.setBidId(loanId);
				inv.setOriginId(loanId);
				inv.setInvestMoney(BigDecimal.valueOf(investMoney));

				List<Integer> redsendIdList = null;
				String redIds = params.getString("red");
				//使用红包
				if (StringUtils.isNotEmpty(redIds)) {
					redsendIdList = new ArrayList<Integer>();
					String[] redsTemp = redIds.split(",");
					// 投资金额大于1000时才能使用红包
					if (redsTemp != null
							&& inv.getInvestMoney().compareTo(
									new BigDecimal("1000")) >= 0) {
						for (int i = 0; i < redsTemp.length; i++) {
							redsendIdList.add(Integer.parseInt(redsTemp[i]));
						}
					}
				}
				
				//判断单标投资次数
				int maxInvOfOneLoan = 10;
				PlainResult<SysConfig> maxInvNum = sysConfigService.querySysConfig(SysConfigEntry.MAX_INVEST_OF_ONE_LOAN);
		        if (maxInvNum.isSuccess()) {
		        	maxInvOfOneLoan = Integer.parseInt(maxInvNum.getData().getConfValue());
		        }
		        if (investService.investNumOfOneLoan(inv) >= maxInvOfOneLoan)
		        {
		        	result.setResultCode("201");
					result.setResultMessage("您的单标投资次数超过系统限制!");
					return result;
		        }
				
				PlainResult<Integer> investCreateResult = investService
						.createInvest(inv, redsendIdList);
				if (!investCreateResult.isSuccess()) {
					result.setResultCode("201");
					result.setResultMessage(investCreateResult.getMessage());
					return result;
				}

				result.setResultCode("200");
				result.setResultMessage("投资成功");
				return result;
			}
			// 推荐投资
			else if ("6".equals(catalog)) {
				return queryOptimization(context, params);
			}
			// 借款人简介
			else if ("9".equals(catalog)) {
				return borrowerIntroduction(params);
			}
			// 相关文件
			else if ("10".equals(catalog)) {
				return releventIntroduction(params);
			}
			// 风控信息
			else if ("11".equals(catalog)) {
				return riskIntroduction(params);
			} else {
				result.setResultCode("201");
				result.setResultMessage("loanRequest catalog not found!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultCode("201");
			result.setResultMessage("请求异常");
		}
		return result;
	}

	/**
	 * 可用红包列表
	 * 
	 * @param userId
	 * @param loan
	 * @return
	 */
	private List<RedsendJ> avaiRedList(Integer userId, Loan loan) {
		Redsend redsend = new Redsend();
		redsend.setRsUserid(userId);
		RedSearchDO redSearchDO = new RedSearchDO();
		PageCondition pageConditionx = new PageCondition(1, 65535);
		redSearchDO.setUserId(userId);
		redSearchDO.setUserScope(loan.getLoanCategory().getPrompt());
		redSearchDO.setRsState(RedState.EFFECTIVE.getState());
		redSearchDO.setRsClosetime(new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date()));
		redSearchDO.setOrder("rs_closetime ASC");
		PageResult<RedsendJ> pageResult = redsendService.queryListJ(
				redSearchDO, pageConditionx);
		return pageResult.getData();
	}

	/**
	 * 风控信息
	 * 
	 * @param params
	 * @return
	 */
	private JsonMobileVO riskIntroduction(ParameterParser params) {
		JsonMobileVO vo = new JsonMobileVO();
		Map<String, Object> objMap = new HashMap<String, Object>();
		vo.setResult(objMap);
		Integer loanId = params.getInt("loanId");
		Loan loanSearch = new Loan();
		loanSearch.setLoanId(loanId);
		Loan loan = loanQueryService.queryByParam(loanSearch).getData();
		objMap.put("riskIntroduction", loan.getRiskIntroduction());
		String loanFileUrl = loan.getLoanFileUrl();
		objMap.put("safes", fileUploadInfoService.findListByFileUrl(
				loanFileUrl, FileUploadSecondaryClass.SAFE_DATA.getType()));
		return vo;
	}

	/**
	 * 相关文件
	 * 
	 * @param params
	 * @return
	 */
	private JsonMobileVO releventIntroduction(ParameterParser params) {
		JsonMobileVO vo = new JsonMobileVO();
		Map<String, Object> objMap = new HashMap<String, Object>();
		vo.setResult(objMap);
		Integer loanId = params.getInt("loanId");
		Loan loanSearch = new Loan();
		loanSearch.setLoanId(loanId);
		Loan loan = loanQueryService.queryByParam(loanSearch).getData();
		objMap.put("releventIntroduction", loan.getRelevantIntroduction());
		String loanFileUrl = loan.getLoanFileUrl();
		// 实地资料
		objMap.put("spots", fileUploadInfoService.findListByFileUrl(
				loanFileUrl, FileUploadSecondaryClass.QUA_DATA.getType()));
		// 资质资料
		objMap.put("qualifys", fileUploadInfoService.findListByFileUrl(
				loanFileUrl, FileUploadSecondaryClass.SPOT_DATA.getType()));
		// 其他
		objMap.put("others", fileUploadInfoService.findListByFileUrl(
				loanFileUrl, FileUploadSecondaryClass.IMAGE_DATA.getType()));
		return vo;
	}

	/**
	 * 借款人简介
	 * 
	 * @param params
	 * @return
	 */
	private JsonMobileVO borrowerIntroduction(ParameterParser params) {
		JsonMobileVO vo = new JsonMobileVO();
		Map<String, Object> objMap = new HashMap<String, Object>();
		vo.setResult(objMap);
		Integer loanId = params.getInt("loanId");
		Loan loanSearch = new Loan();
		loanSearch.setLoanId(loanId);
		Loan loan = loanQueryService.queryByParam(loanSearch).getData();
		objMap.put("borrowerIntroduction", loan.getBorrowerIntroduction());
		String loanFileUrl = loan.getLoanFileUrl();
		// 借款人资料
		objMap.put("borrowerInfos", fileUploadInfoService.findListByFileUrl(
				loanFileUrl, FileUploadSecondaryClass.GUA_DATA.getType()));
		return vo;
	}

	/**
	 * 推荐投资
	 * 
	 * @param context
	 * @param params
	 * @return
	 */
	private JsonMobileVO queryOptimization(Context context,
			ParameterParser params) {
		Integer userId = params.getInt("userId");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(1);//xiatt 20161020 推荐表展示逻辑修改
		
		JsonMobileVO vo = new JsonMobileVO();
		vo.setResultCode("200");
		vo.setResultMessage("successful");
		Map<String, Object> r = new HashMap<String, Object>();
		vo.setResult(r);
		
		List<Loan> loanList = this.loanQueryService.queryOptimizationFy(1).getData();
    	Loan loan = loanList.size() > 0 ? loanList.get(0) : null;
    	
    	List<TransferLoanJDO> transferYxList = transferLoanService.queryTrOptimization(1).getData();
        TransferLoanJDO  tranLoan = transferYxList.size() > 0 ? transferYxList.get(0) : null;
		
		if (userId != 0)
		{
			int lastLoanType = this.loanQueryService.queryLastLoanType(userId);
			if (1 == lastLoanType && null != tranLoan)
            {
				r.put("transferLoanYx", transferYxList);
        		BigDecimal percent = tranLoan.getTlCurrentValidInvest()
                        .divide(tranLoan.getTlTransferMoney(), 50, BigDecimal.ROUND_HALF_UP)
                        .multiply(new BigDecimal(100));
                r.put("transferLoanYxPercent", percent);
        		r.put("pageCount ", 1);
            }
            else
            {
            	Map<String, Object> loanMap = new HashMap<String, Object>();
    			loanMap.put("loanId", loan.getLoanId());
    			loanMap.put("loanType", loan.getLoanCategory().category);
    			loanMap.put("loanIsnew", 1);
    			loanMap.put("loanTitle", loan.getLoanNo());
    			loanMap.put("loanMoney", loan.getLoanMoney());
    			loanMap.put("loanRate", loan.getLoanRate());
    			loanMap.put("loanPeriod", loan.getLoanPeriod() + loan.getLoanPeriodUnit().getPrompt());//
    			loanMap.put("loanProgress",
    					Arith.calcPercent(loan.getLoanCurrentValidInvest(),
    							loan.getLoanMoney()).intValue());
    			loanMap.put("investEndtime", DateUtil.formatDate(loan.getLoanInvestEndtime()));
    			loanMap.put("loanExpireTime", DateUtil.formatDate(loan.getLoanExpireDate()));
    			list.add(loanMap);
    			r.put("pageCount ", 1);
    			r.put("list", list);
            }
		}
		else
		{
			if(loan.getLoanState().state == LoanState.BID_INVITING.state
        			&& DateUtils.substractDay(loan.getLoanInvestEndtime(), new Date())>0
        			&& loan.getLoanMoney().subtract(loan.getLoanCurrentValidInvest()).doubleValue() > 0) {
				Map<String, Object> loanMap = new HashMap<String, Object>();
				loanMap.put("loanId", loan.getLoanId());
				loanMap.put("loanType", loan.getLoanCategory().category);
				loanMap.put("loanIsnew", 1);
				loanMap.put("loanTitle", loan.getLoanNo());
				loanMap.put("loanMoney", loan.getLoanMoney());
				loanMap.put("loanRate", loan.getLoanRate());
				loanMap.put("loanPeriod", loan.getLoanPeriod() + loan.getLoanPeriodUnit().getPrompt());//
				loanMap.put("loanProgress",
						Arith.calcPercent(loan.getLoanCurrentValidInvest(),
								loan.getLoanMoney()).intValue());
				loanMap.put("investEndtime", DateUtil.formatDate(loan.getLoanInvestEndtime()));
				loanMap.put("loanExpireTime", DateUtil.formatDate(loan.getLoanExpireDate()));
				list.add(loanMap);
				r.put("pageCount ", 1);
				r.put("list", list);
			} else {
				//add by xiatt 20161019 优选推荐没找到，则找优选债券
	        	if(null != tranLoan)
	        	{
	        		r.put("transferLoanYx", transferYxList);
	        		BigDecimal percent = tranLoan.getTlCurrentValidInvest()
	                        .divide(tranLoan.getTlTransferMoney(), 50, BigDecimal.ROUND_HALF_UP)
	                        .multiply(new BigDecimal(100));
	                r.put("transferLoanYxPercent", percent);
	        		r.put("pageCount ", 1);
	        	}else{
	        		list = new ArrayList<Map<String, Object>>(1);
	        		if (loan != null) {
	        			Map<String, Object> loanMap = new HashMap<String, Object>();
	        			loanMap.put("loanId", loan.getLoanId());
	        			loanMap.put("loanType", loan.getLoanCategory().category);
	        			loanMap.put("loanIsnew", 1);
	        			loanMap.put("loanTitle", loan.getLoanNo());
	        			loanMap.put("loanMoney", loan.getLoanMoney());
	        			loanMap.put("loanRate", loan.getLoanRate());
	        			loanMap.put("loanPeriod", loan.getLoanPeriod() + loan.getLoanPeriodUnit().getPrompt());//
	        			loanMap.put("loanProgress",
	        					Arith.calcPercent(loan.getLoanCurrentValidInvest(),
	        							loan.getLoanMoney()).intValue());
	        			loanMap.put("investEndtime", DateUtil.formatDate(loan.getLoanInvestEndtime()));
	        			loanMap.put("loanExpireTime", DateUtil.formatDate(loan.getLoanExpireDate()));
	        			list.add(loanMap);
	        			r.put("pageCount ", 1);
	        			r.put("list", list);
	        		}
	        		else{
	        			r.put("pageCount ", 1);
	        			r.put("list", list);
	        		}
	        	}
			}
		}

		return vo;
	}

}

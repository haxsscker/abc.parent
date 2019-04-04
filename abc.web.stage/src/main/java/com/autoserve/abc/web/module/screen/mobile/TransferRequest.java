package com.autoserve.abc.web.module.screen.mobile;

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
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.TransferLoanJDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.TransferLoanSearchDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.Arith;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.util.Pagebean;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 债权转让
 * 
 * @author Bo.Zhang
 */
public class TransferRequest {

    @Resource
    private TransferLoanService transferLoanService;
    @Resource
    private LoanQueryService    loanQueryService;
    @Resource
    private UserService         userService;
    @Resource
    private AccountInfoService  accountInfoService;
    @Resource
    private InvestService       investService;
    @Resource
	private SysConfigService 	sysConfigService;
    @Resource
    private InvestQueryService  investQueryService;
    @Resource
    private DealRecordService   dealRecordService;
    @Resource
    private DoubleDryService    doubleDryService;
    @Autowired
    private HttpSession session;
    public JsonMobileVO execute(Context context, ParameterParser params) throws IOException {
        JsonMobileVO result = new JsonMobileVO();
        try {
            String catalog = params.getString("catalog");

            if ("1".equals(catalog)) {
                //债权转让列表
            	// /mobile/transferRequest.json?catalog=1
                Integer pageSize = params.getInt("pageSize");
                Integer showPage = params.getInt("showPage");

                Map<String, Object> objMap = new HashMap<String, Object>();
                List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();

                Map<String, Object> loanMap = new HashMap<String, Object>();

                TransferLoanSearchDO pojo = new TransferLoanSearchDO();
                if (pojo.getTransferLoanStates() == null || pojo.getTransferLoanStates().size() == 0) {
                    pojo.setTransferLoanStates(Arrays.asList(TransferLoanState.TRANSFERING.state,TransferLoanState.FULL_WAIT_REVIEW.state
      					  ,TransferLoanState.FULL_REVIEW_PASS.state,TransferLoanState.FULL_REVIEW_FAIL.state
    					  ,TransferLoanState.MONEY_TRANSFERING.state,TransferLoanState. MONEY_TRANSFERED.state));
                }

                PageCondition pageCondition = new PageCondition(showPage, pageSize);
                PageResult<TransferLoanJDO> pageResult = transferLoanService.queryListByParam(pojo, pageCondition);
                Pagebean<TransferLoanJDO> pagebean = new Pagebean<TransferLoanJDO>(showPage, pageSize,
                        pageResult.getData(), pageResult.getTotalCount());

                for (TransferLoanJDO transferLoanJDO : pagebean.getRecordList()) {
                    loanMap = new HashMap<String, Object>();
                    loanMap.put("transferId", transferLoanJDO.getTlId());
                    loanMap.put("loanTitle", transferLoanJDO.getLoanNo());
                 //  loanMap.put("transferLoanNo", transferLoanJDO.)
                    loanMap.put("transferLoanNo", transferLoanJDO.getTlLoanNo());
                    loanMap.put("transferMoney", transferLoanJDO.getTlTransferMoney());
                    loanMap.put("transferRate", transferLoanJDO.getLoanRate());
                    loanMap.put(
                            "transferPeriod",
                            transferLoanJDO.getLoanPeriod()
                                    + LoanPeriodUnit.valueOf(transferLoanJDO.getLoanPeriodType()).getPrompt());
                    loanMap.put("transferProgress", Arith.calcPercent(transferLoanJDO.getTlCurrentValidInvest(), transferLoanJDO.getTlTransferMoney()).intValue());
                    loanMap.put("loanExpireTime", DateUtil.formatDate(transferLoanJDO.getLoanExpireDate()));
                    loanList.add(loanMap);
                }

                objMap.put("pageCount", pagebean.getPageCount());
                objMap.put("list", JSON.toJSON(loanList));

                result.setResultCode("200");
                result.setResult(JSON.toJSON(objMap));
            } else if ("2".equals(catalog)) {
            	// /mobile/transferRequest.json?catalog=2&transferId=100
                //转让详情
                Integer transferId = params.getInt("transferId");
                Integer userId = params.getInt("userId");
                if(null == userId){
                	User user=(User)session.getAttribute("user");
                	userId = user.getUserId();
                }
                PlainResult<TransferLoan> transferPlainResult = transferLoanService.queryById(transferId);
                TransferLoan transferLoan = transferPlainResult.getData();
                PlainResult<Loan> loanPlainResult = loanQueryService.queryById(transferLoan.getOriginId());
                Loan loan = loanPlainResult.getData();

                Map<String, Object> objMap = new HashMap<String, Object>();
                // 获取用户信息
                PlainResult<UserDO> userDoResult=userService.findById(userId);
				UserDO userDO = userDoResult.getData();
				if(null != userDO){
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
				}
                objMap.put("transferId", transferLoan.getId());
                objMap.put("loanTitle", loan.getLoanNo());
                objMap.put("transferStartTime",
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(transferLoan.getCreatetime()));
                objMap.put("loanPayType", loan.getLoanPayType().getPrompt());
                objMap.put("transferMoney", transferLoan.getTransferMoney());
                objMap.put("transferRate", transferLoan.getTransferRate());
                objMap.put("transferPeriod",
                        loan.getLoanPeriod() + LoanPeriodUnit.valueOf(loan.getLoanPeriodUnit().getUnit()).getPrompt());
                objMap.put("currentInvest", transferLoan.getCurrentValidInvest());
                objMap.put("currentValidInvest",
                        transferLoan.getTransferMoney().subtract(transferLoan.getCurrentValidInvest()));
                objMap.put("transferState", transferLoan.getTransferLoanState().state);
                objMap.put("originLoanState", loan.getLoanState().state); //原始项目状态
                objMap.put("transferLoanNo", transferLoan.getTransferLoanNo());
                objMap.put("timelimit", DateUtil.substractDay(loan.getLoanExpireDate(), transferLoan.getCreatetime()));
                result.setResultCode("200");
                result.setResult(JSON.toJSON(objMap));
            } else if ("3".equals(catalog)) {
                //转让收购，没有用到吧？
                Integer userId = params.getInt("userId");
                Integer transferId = params.getInt("transferId");
                Double investMoney = params.getDouble("investCount");
                String dealPwd = params.getString("dealPwd");
                String verification = params.getString("smsCode");
                session.setAttribute("bhSmsVerification", verification);
                if (userId == 0) {
                    result.setResultCode("201");
                    result.setResultMessage("请求用户id不能为空");
                    return result;
                } else if (transferId == 0) {
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
                }else if (verification == null || "".equals(verification)) {
                    result.setResultCode("201");
                    result.setResultMessage("请输入短信验证码");
                    return result;
                }
                // 检查用户信息
//                if (!MobileHelper.check(userService, userId, result).isSuccess()) {
//                    return result;
//                }

                // 投资
                User user = userService.findEntityById(userId).getData();
                if (user != null) { // 校验交易密码
                    PlainResult<UserDO> userDoResult = userService.findById(user.getUserId());
                    if (dealPwd == null || !CryptUtils.md5(dealPwd).equals(userDoResult.getData().getUserDealPwd())) {
                        result.setResultCode("201");
                        result.setResultMessage("支付密码不正确!");
                        return result;
                    }
                }

                //判断账户余额
                UserIdentity userIdentity = new UserIdentity();
                userIdentity.setUserId(user.getUserId());
                if (user.getUserType() == null || user.getUserType().getType() == 1) {
                    user.setUserType(UserType.PERSONAL);
                } else {
                    user.setUserType(UserType.ENTERPRISE);
                }
                userIdentity.setUserType(user.getUserType());
                Account account = accountInfoService.queryByUserId(userIdentity).getData();
                String accountNo = account.getAccountNo();

                // 网贷平台子账户可用余额|总可用余额(子账户可用余额+公共账户可用余额)|子账户冻结余额”（例:100.00|200.00|10.00）
                Double[] accountBacance = doubleDryService.queryBalance(accountNo, "1");
                if (investMoney - accountBacance[1] > 0) {
                    result.setResultCode("200");
                    result.setResultMessage("可用余额不足，请充值!");
                    result.setResult("chongzhi");
                    return result;
                }

                BidType bidType = BidType.TRANSFER_LOAN;
                //				if (loanId != null && flagLoan == 1) { // 普通标
                //					bidType = BidType.COMMON_LOAN;
                //				}
                //				if (loanId != null && flagLoan == 2) { // 转让标
                //					bidType = BidType.TRANSFER_LOAN;
                //				}

                //				int userId = user.getUserId();
                //				if (bidType == null) {
                //					bidType = BidType.BUY_LOAN;
                //				}

                //				int bidId = params.getInt("loanId");
                Invest inv = new Invest();
                inv.setUserId(userId);
                inv.setBidType(bidType); // 设置标的类型

                //				if (BidType.COMMON_LOAN.equals(bidType)) {
                inv.setBidId(transferId);
                //				inv.setOriginId(transferId);
                //				} else {
                //					inv.setBidId(loanId);
                //				}

                //				Double investMoney = params.getDouble("investedMoney");
                //				if (investMoney != null) {
                inv.setInvestMoney(BigDecimal.valueOf(investMoney));
                //				}
                
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

                PlainResult<Integer> investCreateResult = investService.createInvest(inv,null);
                if (!investCreateResult.isSuccess()) {
                    result.setResultCode("201");
                    result.setResultMessage(investCreateResult.getMessage());
                    return result;
                }

                // 支付
                //				if (BidType.BUY_LOAN.equals(inv.getBidType())) {
                //					return JsonBaseVO.SUCCESS;
                //				} else {
//                PlainResult<Invest> investQueryResult = investQueryService.queryById(investCreateResult.getData());
//                if (!investQueryResult.isSuccess()) {
//                    result.setResultCode("201");
//                    result.setResultMessage(investCreateResult.getMessage());
//                    return result;
//                }
//
//                BaseResult invokeResult = dealRecordService.invokePayment(investQueryResult.getData().getInnerSeqNo());
//                if (!invokeResult.isSuccess()) {
//                    result.setResultCode("201");
//                    result.setResultMessage(investCreateResult.getMessage());
//                    return result;
//                }
                result.setResultCode("200");
                result.setResultMessage("认购成功");
                return result;
            }else if("4".equals(catalog)){
            	Integer userId = params.getInt("userId");
                User user = userService.findEntityById(userId).getData();
    			String telephone  = user.getUserPhone();
    			Map<String, String> resultMap = accountInfoService.querySmsCode(telephone);
    			if("000000".equals(resultMap.get("RespCode")))
    			{
    				result.setResultCode("200");
                    result.setResultMessage("短信发送成功！");
    			}
    			else{
    				result.setResultCode("201");
                    result.setResultMessage(resultMap.get("RespDesc"));
    			}
    			return result;
            }else {
                result.setResultCode("201");
                result.setResultMessage("请求参数异常");
            }
        } catch (Exception e) {
            result.setResultCode("201");
            result.setResultMessage("请求异常");
        }
        return result;
    }

}

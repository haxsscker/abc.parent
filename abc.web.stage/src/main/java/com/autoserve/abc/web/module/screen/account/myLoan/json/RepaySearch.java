package com.autoserve.abc.web.module.screen.account.myLoan.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.ChargeRecordDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.intf.CompanyCustomerDao;
import com.autoserve.abc.dao.intf.DealRecordDao;
import com.autoserve.abc.dao.intf.FullTransferRecordDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.SmsNotifyDao;
import com.autoserve.abc.dao.intf.TransferLoanDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.callback.center.CashCallBackCenter;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.FeeType;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.ChargeRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.invest.InvestOrderService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.message.mail.SendMailService;
import com.autoserve.abc.web.vo.JsonBaseVO;


/**
 * 查询还款交易状态
 * @author LZ
 *
 * 
 */
public class RepaySearch {
	private final static Logger logger = LoggerFactory.getLogger(RepaySearch.class);
	@Resource
    private LoanDao                   loanDao;
	@Resource
    private LoanService           loanService;
	@Resource
    private InvestQueryService        investQueryService;
	@Resource
    private UserService               userService;
	@Resource
    private DealRecordService         dealRecordService;
    @Resource
    private AccountInfoService        accountInfoService;
    @Resource
    private DealRecordDao             dealRecordDao;
    @Resource
    private InvestOrderService        investOrderService;
    @Resource
    private EmployeeService           employeeService;
    @Resource
    private IncomePlanService         incomePlanService;
    @Resource
    private PaymentPlanService        paymentPlanService;
    @Resource
    private FullTransferRecordDao     fullTransferRecordDao;
    @Resource
    private SendMailService       sendMailService;
    @Resource
    private SysConfigService sysConfigService;
    @Resource
    private SmsNotifyDao smsNotifyDao;
    @Resource
    private CompanyCustomerDao companyCustomerDao;
    @Resource
    private TransferLoanDao       transferLoanDao;
    @Resource
    private TransferLoanService   transferLoanService;
    @Resource
    private DoubleDryService doubleDryService;
	@Resource
	private ChargeRecordService chargeRecord;

	public JsonBaseVO execute(Context context, ParameterParser params) {
		JsonBaseVO result = new JsonBaseVO();
		String seqNo = params.getString("seqNo");
		String type = params.getString("type");
		if (StringUtil.isEmpty(seqNo) || StringUtil.isEmpty(type)) {
			result.setSuccess(false);
			result.setMessage("参数非法！");
			return result;
		}
		Map<String, String> resultMap = accountInfoService.queryTransStatus(
				seqNo, type);
		Map<String, String> paramMap = new HashMap<String, String>();
		String RespCode = resultMap.get("RespCode");
		String RespDesc = resultMap.get("RespDesc");
		String TransStat = resultMap.get("TransStat");
		String MerBillNo = resultMap.get("MerBillNo");
		paramMap.put("MerBillNo", MerBillNo);
		logger.info("RespCode：{}，TransStat：{}，RespDesc：{}",RespCode,TransStat,RespDesc);
		// 更新交易、资金操作记录
		if ("S1".equals(TransStat) || "S3".equals(TransStat)) {// S3放款解冻成功
			result.setSuccess(true);
			result.setMessage("还款成功");
			paramMap.put("RespDesc", "还款成功");
			paramMap.put("RespCode", "000000");
			try {
				result = this.doMoneyTransfer(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				result.setSuccess(false);
				result.setMessage(e.getMessage());
				return result;
			}
		} else if (null == TransStat || "F1".equals(TransStat)
				|| "R9".equals(TransStat)) {// R9审批拒绝
			result.setSuccess(false);
			result.setMessage("还款失败");
			paramMap.put("RespDesc", "还款失败");
			paramMap.put("RespCode", "111111");
			try {
				result = this.doMoneyTransfer(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				result.setSuccess(false);
				result.setMessage(e.getMessage());
				return result;
			}
		} else if ("W2".equals(TransStat)) {
			logger.info("请求处理中");
			result.setSuccess(false);
			result.setMessage("请求处理中");
		} else if ("W3".equals(TransStat)) {
			logger.info("系统受理中");
			result.setSuccess(false);
			result.setMessage("系统受理中");
		} else if ("W4".equals(TransStat)) {
			logger.info("银行受理中");
			result.setSuccess(false);
			result.setMessage("银行受理中");
		}
		return result;

	}
	/**
	 * 还款查询结果处理
	 * @param paramMap
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	private JsonBaseVO doMoneyTransfer(Map<String, String> params){
		JsonBaseVO result = new JsonBaseVO();
		logger.info("===================还款查询结果处理===================");
		String innerSeqNo = params.get("MerBillNo");// 
		String RespCode = params.get("RespCode");// 
		String RespDesc = params.get("RespDesc");// 
		result.setMessage(RespDesc);
		try {
			List<DealRecordDO> dealRecords = dealRecordDao
					.findDealRecordsByInnerSeqNo(innerSeqNo);
			if (CollectionUtils.isEmpty(dealRecords)) {
				logger.error("交易流水innerSeqNo："+innerSeqNo+"无对应的交易记录");
			}else{
				DealRecordDO dealRecordDo = new DealRecordDO();
				// 接口支持批量 故cashSeqNo和innerSeqNo相同 否则此处为outSeqNo
				dealRecordDo.setDrInnerSeqNo(innerSeqNo);
			
				int newState = DealState.NOCALLBACK.getState();
				if("000000".equals(RespCode)){
					logger.info("============还款成功===========");
					dealRecordDo.setDrState(DealState.SUCCESS.getState());
					newState = DealState.SUCCESS.getState();
				}else{
					result.setSuccess(false);
					logger.info("============还款失败====="+RespDesc);
					dealRecordDo.setDrState(DealState.FAILURE.getState());
					newState = DealState.FAILURE.getState();
				}
				
				// 先查看交易记录的状态，为等待响应则继续 否则只做docallback 不update表数据
				boolean goon = false;
				// 当前数据库中交易的状态
				int dealStatus = DealState.SUCCESS.getState();
				BaseResult notifyState = null;
				DealNotify notify = new DealNotify();
				for (DealRecordDO dr : dealRecords) {
					DealState ds = DealState.valueOf(dr.getDrState());
					switch (ds) {
						case FAILURE: {
							dealStatus = DealState.FAILURE.getState();
							break;
						}
						case NOCALLBACK: {
							goon = true;
							break;
						}
						case SUCCESS:
							break;
						default:
							break;
					}
				}
				if (!goon) {
					DealType type = DealType.valueOf(dealRecords.get(0).getDrType());
					Callback<DealNotify> callBack = CashCallBackCenter
							.getCallBackByType(type);
					notify.setInnerSeqNo(innerSeqNo);
					notify.setState(DealState.valueOf(dealStatus));
					notifyState = callBack.doCallback(notify);
					if (!notifyState.isSuccess()) {
						// 如果回调不成功，则认为业务处理失败，不发送确认
						logger.error("交易流水innerSeqNo："+innerSeqNo+"回调失败");
						throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),"交易流水innerSeqNo："+innerSeqNo+"回调失败");
					}
				}else{
					// 更新交易记录状态
					int flag = 0;
					for (DealRecordDO dr : dealRecords) {
						dr.setDrState(dealRecordDo.getDrState());
						flag += dealRecordDao.updateDealRecordStateById(dr);
					}
					if (flag <= 0) {// 更新交易
						logger.warn(
								"[DealRecordServiceImpl][modifyDealRecordState] 更新交易记录状态警告：无交易记录可更新。交易流水号：{}",
								innerSeqNo);
						throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),
								"交易状态更新失败");
					}
					// 根据获取交易的交易类型 非交易详细类型
					int dealType = dealRecords.get(0).getDrType();
					DealType type = DealType.valueOf(dealType);
					Callback<DealNotify> callBack = CashCallBackCenter
							.getCallBackByType(type);
					notify.setInnerSeqNo(innerSeqNo);
					notify.setState(DealState.valueOf(newState));
					notifyState = callBack.doCallback(notify);
					// 交易成功 更新用户账户资金状态和相关记录表
					if (DealState.valueOf(newState).equals(DealState.SUCCESS)) {
						// update记录表(暂时为收费记录表)
						modifyAccountTables(dealRecords);
					}
					// 根据notify结果返回
					if (!notifyState.isSuccess()) {
						// 如果回调不成功，则认为业务处理失败，不发送确认
						logger.error("交易流水innerSeqNo："+innerSeqNo+"回调失败");
						throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),"交易流水innerSeqNo："+innerSeqNo+"回调失败");
					}
				}
			}
		} catch (Exception e) {
			logger.error("[还款] error: ", e);
			throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),e.getMessage());
		}
		return result;
	}
		// 更新一堆记录表
		@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
		private BaseResult modifyAccountTables(List<DealRecordDO> dealRecords) {
			BaseResult result = new BaseResult();
			Set<String> accountSet = new HashSet<String>();
			Set<Integer> loanSet = new HashSet<Integer>();

			for (DealRecordDO record : dealRecords) {
				accountSet.add(record.getDrPayAccount());
				accountSet.add(record.getDrReceiveAccount());
				loanSet.add(record.getDrBusinessId());
			}
			Map<String, Integer> userAccountIdMapper = new HashMap<String, Integer>();
			ListResult<Account> queryResult = accountInfoService
					.queryByAccountNos(new ArrayList<String>(accountSet));
			if (queryResult.isSuccess()) {
				for (Account account : queryResult.getData()) {
					userAccountIdMapper.put(account.getAccountNo(),
							account.getAccountUserId());
				}
			}
			Map<Integer, Integer> loanMapper = new HashMap<Integer, Integer>();
			for (DealRecordDO record : dealRecords) {
				DealDetailType detailType = DealDetailType.valueOf(record.getDrDetailType());
				switch (detailType) {
				case INVESTE_MONEY: {
					break;
				}
				case PLA_FEE: {
					// 记录平台手续费
					ChargeRecordDO chargePla = new ChargeRecordDO();
					chargePla.setCrFee(record.getDrMoneyAmount());
					chargePla.setCrFeeType(FeeType.PLA_FEE.getType());
					Integer loanId = record.getDrBusinessId();
					chargePla.setCrLoanId(loanId);
					chargePla.setCrLoanType(loanMapper.get(loanId));
					chargePla.setCrSeqNo(record.getDrInnerSeqNo());
					PlainResult<Integer> createResult = chargeRecord
							.createChargeRecord(chargePla);
					if (!createResult.isSuccess()) {
						logger.error(
								"[DealRecordService][modifyAccountTables]记录平台手续费错误:{}",
								createResult.getMessage());
						throw new BusinessException("数据库插入错误");
					}
					break;
				}
				case PLA_SERVE_FEE: {
					// 记录平台服务费
					ChargeRecordDO chargePlaSer = new ChargeRecordDO();
					chargePlaSer.setCrFee(record.getDrMoneyAmount());
					chargePlaSer.setCrFeeType(FeeType.PLA_SERVE_FEE.getType());
					Integer loanId = record.getDrBusinessId();
					chargePlaSer.setCrLoanId(loanId);
					chargePlaSer.setCrLoanType(loanMapper.get(loanId));
					chargePlaSer.setCrSeqNo(record.getDrInnerSeqNo());
					PlainResult<Integer> createResult = chargeRecord
							.createChargeRecord(chargePlaSer);
					if (!createResult.isSuccess()) {
						logger.error(
								"[DealRecordService][modifyAccountTables]记录平台手续费错误:{}",
								createResult.getMessage());
						throw new BusinessException("数据库插入错误");
					}
					break;
				}
				case DEBT_TRANSFER_FEE: {
					// 转让手续费
					ChargeRecordDO chargeTransfer = new ChargeRecordDO();
					chargeTransfer.setCrFee(record.getDrMoneyAmount());
					chargeTransfer.setCrFeeType(FeeType.TRANSFER_FEE.getType());
					Integer loanId = record.getDrBusinessId();
					chargeTransfer.setCrLoanId(loanId);
					chargeTransfer.setCrLoanType(loanMapper.get(loanId));
					chargeTransfer.setCrSeqNo(record.getDrInnerSeqNo());
					PlainResult<Integer> createResult = chargeRecord.createChargeRecord(chargeTransfer);
					if (!createResult.isSuccess()) {
						logger.error(
								"[DealRecordService][modifyAccountTables]记录平台手续费错误:{}",
								createResult.getMessage());
						throw new BusinessException("数据库插入错误");
					}
					break;
				}
				case PURCHASE_FEE: {
					// 收购手续费
					ChargeRecordDO chargePurchase = new ChargeRecordDO();
					chargePurchase.setCrFee(record.getDrMoneyAmount());
					chargePurchase.setCrFeeType(FeeType.PURCHASE_FEE.getType());
					Integer loanId = record.getDrBusinessId();
					chargePurchase.setCrLoanId(loanId);
					chargePurchase.setCrLoanType(loanMapper.get(loanId));
					chargePurchase.setCrSeqNo(record.getDrInnerSeqNo());
					PlainResult<Integer> createResult = chargeRecord.createChargeRecord(chargePurchase);
					if (!createResult.isSuccess()) {
						logger.error(
								"[DealRecordService][modifyAccountTables]记录平台手续费错误:{}",
								createResult.getMessage());
						throw new BusinessException("数据库插入错误");
					}
					break;
				}
				case INSURANCE_FEE: {
					// 担保手续费
					ChargeRecordDO chargeInsurance = new ChargeRecordDO();
					chargeInsurance.setCrFee(record.getDrMoneyAmount());
					chargeInsurance.setCrFeeType(FeeType.INSURANCE_FEE.getType());
					Integer loanId = record.getDrBusinessId();
					chargeInsurance.setCrLoanId(loanId);
					chargeInsurance.setCrLoanType(loanMapper.get(loanId));
					chargeInsurance.setCrSeqNo(record.getDrInnerSeqNo());
					PlainResult<Integer> createResult = chargeRecord
							.createChargeRecord(chargeInsurance);
					if (!createResult.isSuccess()) {
						logger.error(
								"[DealRecordService][modifyAccountTables]记录平台手续费错误:{}",
								createResult.getMessage());
						throw new BusinessException("数据库插入错误");
					}
					break;
				}
				case PAYBACK_CAPITAL: {
					break;
				}
				case PAYBACK_INTEREST: {
					break;
				}
				case PAYBACK_OVERDUE_FINE: {
					break;
				}
				case REFUND_MONEY: {
					break;
				}
				case APPROPRIATE_MONEY: {
					break;
				}
				case RECHARGE_MONEY: {
					break;
				}
				case TOCASH_MONEY: {
					break;
				}
				case PURCHASE_MONEY: {
					break;
				}
				case WITHDRAWAL_INVESTER_MONEY: {
					break;
				}
				case DEBT_TRANSFER_MONEY: {
					break;
				}
				case ABORT_BID_MONEY: {
					break;
				}
				default:
					break;
				}
			}
			return result;
		}
}

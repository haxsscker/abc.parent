package com.autoserve.abc.web.module.screen.moneyManage.json;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.FullTransferRecordDO;
import com.autoserve.abc.dao.dataobject.InvestOrderDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.SmsNotifyCfg;
import com.autoserve.abc.dao.dataobject.SmsNotifyDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.CompanyCustomerDao;
import com.autoserve.abc.dao.intf.DealRecordDao;
import com.autoserve.abc.dao.intf.FullTransferRecordDao;
import com.autoserve.abc.dao.intf.IncomePlanDao;
import com.autoserve.abc.dao.intf.InvestOrderDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.PaymentPlanDao;
import com.autoserve.abc.dao.intf.SmsNotifyDao;
import com.autoserve.abc.dao.intf.TransferLoanDao;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanTraceRecord;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.IncomePlanState;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoanTraceOperation;
import com.autoserve.abc.service.biz.enums.OrderState;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data.SftpTool;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ExchangeDataUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileNameUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.SeqnoHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
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
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.message.mail.SendMailService;
import com.autoserve.abc.web.vo.JsonBaseVO;


/**
 * 查询交易状态
 * @author LZ
 *
 * 
 */
public class MoneyTransferSearch {
	private final static Logger logger = LoggerFactory.getLogger(MoneyTransferSearch.class);
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
    private InvestOrderDao investOrderDao;
    @Resource
    private PaymentPlanDao            paymentPlanDao;
    @Resource
    private IncomePlanDao 			incomePlanDao;

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
			result.setMessage("放款成功");
			paramMap.put("RespDesc", "放款成功");
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
			result.setMessage("放款失败");
			paramMap.put("RespDesc", "放款失败");
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
	 * 放款查询结果处理
	 * @param paramMap
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	private JsonBaseVO doMoneyTransfer(Map<String, String> params){
		JsonBaseVO result = new JsonBaseVO();
		logger.info("===================放款查询结果处理===================");
		String MerBillNo = params.get("MerBillNo");// 
		String RespCode = params.get("RespCode");// 
		String RespDesc = params.get("RespDesc");// 
		result.setMessage(RespDesc);
		try {
    		LoanDO loanDO = loanDao.findBySeqNo(MerBillNo);
    		FullTransferRecordDO fullTransferRecordDO = null;
    		if(null != loanDO){
    			fullTransferRecordDO = fullTransferRecordDao.findByInnerSeqNo(MerBillNo);
    		}
			if("000000".equals(RespCode)){
				logger.info("============放款银行返回成功===========");
			        if (null != fullTransferRecordDO && fullTransferRecordDO.getFtrDealState().equals(DealState.NOCALLBACK.getState())) {
			        	if (fullTransferRecordDO.getFtrBidType().equals(BidType.COMMON_LOAN.getType())) {
					        // 修改交易记录状态
					        logger.info("================修改交易记录状态================");
					        DealRecordDO updateDealRecord = new DealRecordDO();
					        updateDealRecord.setDrInnerSeqNo(fullTransferRecordDO.getFtrSeqNo());//与手续费记录、投资划转记录流水号一致
					        updateDealRecord.setDrState(DealState.SUCCESS.getState());
							int flag1 = dealRecordDao.updateDealRecordState(updateDealRecord);
							if (flag1 <= 0) {
								throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),"修改交易记录状态出错");
							}
			        		Loan toModify = new Loan();
			        		toModify.setLoanId(fullTransferRecordDO.getFtrBidId());
			        		toModify.setLoanState(LoanState.REPAYING);
			        		toModify.setLoanFullTransferedtime(new Date());
			        		
			        		// 普通标状态修改，项目跟踪状态记录
			        		logger.info("================普通标状态修改，项目跟踪状态记录================");
			        		LoanTraceRecord traceRecord = new LoanTraceRecord();
			        		traceRecord.setCreator(fullTransferRecordDO.getFtrOperator());
			        		traceRecord.setLoanId(toModify.getLoanId());
			        		traceRecord.setLoanTraceOperation(LoanTraceOperation.loanMoneyTransfer);
			        		traceRecord.setOldLoanState(LoanState.MONEY_TRANSFERING);
			        		traceRecord.setNewLoanState(LoanState.REPAYING);
			        		traceRecord.setNote("普通标项目满标资金划转成功");
			        		
			        		BaseResult modResult = loanService.modifyLoanInfo(toModify, traceRecord);
			        		if (!modResult.isSuccess()) {
			        			throw new BusinessException("普通标状态修改失败");
			        		}
			        		
			        		// 3. 更新还款计划表状态
			        		logger.info("================更新还款计划表状态================");
			        		PlainResult<Integer> modifyPayPlanStateResult = paymentPlanService.batchModifyStateByFullTransRecordId(
			        				fullTransferRecordDO.getFtrId(), PayState.INACTIVED, PayState.UNCLEAR);
			        		if (!modifyPayPlanStateResult.isSuccess()) {
			        			throw new BusinessException("批量修改还款计划表状态为未支付失败");
			        		}
			        		
			        		// 4. 更新收益计划表状态，投资状态
			        		logger.info("================更新收益计划表状态，投资状态================");
			        		PlainResult<Integer> newModResult = incomePlanService.batchModifyIncomePlanAndInvest(
			        				fullTransferRecordDO.getFtrId(), IncomePlanState.INACTIVED, IncomePlanState.GOING,
			        				InvestState.PAID, InvestState.EARNING);
			        		if (!newModResult.isSuccess()) {
			        			throw new BusinessException("批量修改投资人的收益计划状态和投资状态失败");
			        		}
					        //从abc_invest_order获取该原始标所有的红包投资记录
					        List<InvestOrderDO> listLoanSeq = investOrderService.findRedOrderByOriginId(loanDO.getLoanId());
					        if(null != listLoanSeq && listLoanSeq.size()>0){
					        	logger.info("================红包投资返还================");
					        	// 平台账户
					        	String platformAccount = ConfigHelper.getConfig("merchantId");
					        	// 红包返还记录
					        	List<DealRecordDO> reaRecordDoList = new ArrayList<DealRecordDO>();
					        	// 更新的红包投资订单记录,防止重复返还红包
					        	List<Integer> updateInvestOrderDoIdList = new ArrayList<Integer>();
					        	DealRecordDO dealRecord = null;
					        	Map<String,Object> redMap = null;
					        	for(InvestOrderDO investOrderDO:listLoanSeq){
					        		if(OrderState.UNPAID.getState() == investOrderDO.getIoOrderState()){//未返还的
					        			redMap = new HashMap<String, Object>();
					        			redMap.put("PlaCustId", investOrderDO.getInAccountNo());
					        			redMap.put("TransAmt", investOrderDO.getIoOrderMoney());
					        			//调用红包实时接口
					        			logger.info("================调用红包实时接口================");
					        			logger.info("PlaCustId:{},TransAmt:{}",investOrderDO.getInAccountNo(),investOrderDO.getIoOrderMoney());
					        			Map<String, String> resultMap = doubleDryService.returnRed(redMap);
					        			dealRecord = new DealRecordDO();
					        			dealRecord.setDrBusinessId(loanDO.getLoanId());
					        			dealRecord.setDrMoneyAmount(investOrderDO.getIoOrderMoney());
					        			dealRecord.setDrOperateDate(new Date());
					        			dealRecord.setDrPayAccount(platformAccount);
					        			dealRecord.setDrReceiveAccount(investOrderDO.getInAccountNo());
					        			dealRecord.setDrInnerSeqNo(InnerSeqNo.getInstance().getUniqueNo());
					        			dealRecord.setDrType(DealType.RETURN_RED.getType());
					        			dealRecord.setDrDetailType(DealDetailType.RED_MONEY.getType());
					        			if("000000".equals(resultMap.get("RespCode"))){
					        				logger.info("================红包实时返还成功================");
					        				dealRecord.setDrState(DealState.SUCCESS.getState());
					        				updateInvestOrderDoIdList.add(investOrderDO.getIoId());
					        			}else{
					        				//后面通过发送红包返还给用户
					        				logger.error("客户号:{},红包金额:{},红包实时返还失败:{}",investOrderDO.getInAccountNo(),investOrderDO.getIoOrderMoney(),resultMap.get("RespDesc"));
					        				dealRecord.setDrState(DealState.FAILURE.getState());
//					        			throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),"红包实时返还出错");
					        			}
					        			reaRecordDoList.add(dealRecord);
					        		}
					        	}
					        	if(CollectionUtils.isNotEmpty(updateInvestOrderDoIdList)){
					        		logger.info("================修改红包投资订单记录状态================");
					        		int r=investOrderDao.batchUpdateInvestOrderStateByList(updateInvestOrderDoIdList,OrderState.PAID.getState());
					        		if (r <= 0) {
					        			logger.error("修改红包投资订单记录状态出错");
//					        			throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),"修改红包投资订单记录状态出错");
					        		}
					        	}
					        	//插入红包返还记录
					        	if(CollectionUtils.isNotEmpty(reaRecordDoList)){
					        		logger.info("================插入红包返还记录================");
					        		int flag = dealRecordDao.batchInsert(reaRecordDoList);
					        		if (flag <= 0) {
					        			logger.error("插入红包返还记录出错");
//					        			throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),"插入红包返还记录出错");
					        		}
					        	}
					        }
			        		// 5. 更新满标资金划转记录状态
			        		logger.info("================更新满标资金划转记录状态================");
				            FullTransferRecordDO toModifyFullTransferRecordDO = new FullTransferRecordDO();
				            toModifyFullTransferRecordDO.setFtrId(fullTransferRecordDO.getFtrId());
				            toModifyFullTransferRecordDO.setFtrDealState(DealState.SUCCESS.getState());
				            int r=fullTransferRecordDao.update(toModifyFullTransferRecordDO);
				            if (r <= 0) {
			        			throw new BusinessException("更新满标资金划转记录状态失败");
			        		}
			        		try
			        		{
			        			logger.info("================批量借款发送附件合同================");
			        			LoanDO loanDo = loanDao.findByLoanId(fullTransferRecordDO.getFtrBidId());
			        			sendMailService.sendMailToInvestUser(fullTransferRecordDO.getFtrBidId(), loanDo.getLoanNo());
			        		}
			        		catch (Exception e)
			        		{
			        			logger.error("================批量借款发送附件合同失败================");
			        			e.printStackTrace();
			        		}
			        		
			        		try
			        		{
			        			logger.info("================向银行上传放款合同文件================");
			        			LoanDO loanDo2 = loanDao.findByLoanId(fullTransferRecordDO.getFtrBidId());
			        			String contractPath = loanDo2.getContractPath();
			        			String merBillNo = ConfigHelper.getConfig("merchantId")+SeqnoHelper.getId(16);
			        			String fileName = FileNameUtil.getFileName("ContractFileUpload", "zip",merBillNo);
			        			String newPdffileName = FileNameUtil.getFileName("ContractFileUpload", "pdf",merBillNo);
			        			// 本地路径
			        			File pdffile = new File(contractPath);
			        	        String localPath = contractPath.replace(pdffile.getName(), "");
			        	        File newpdffile = new File(localPath+newPdffileName);
			        	        //复制原文件，并重命名，防止中文文件名乱码
			        	        FileUtils.copyFileUsingFileStreams(pdffile,newpdffile);
			        			FileUtils.zipFiles(newpdffile,localPath+fileName);
			        			
			        			SftpTool ftp = new SftpTool();
			        	    	
			        	        // 目标路径
			        	        String remotePath = ConfigHelper.getSftpRemotePath()+FormatHelper.formatDate(new Date(), "yyyyMMdd");
			        	       
			        	        //上送成功标志文件名
			        	        String uploadSuccessFileName = FileNameUtil.getFileNameBySuffix(fileName, "OK");
			        	        ftp.connect();
			                	boolean isUploadSuccess = ftp.uploadFile(remotePath, fileName, localPath, fileName);
			                	isUploadSuccess = ftp.uploadFile(remotePath, uploadSuccessFileName, localPath, uploadSuccessFileName);
			                	ftp.disconnect();
			                	if(isUploadSuccess){
			                		//发送请求,获取数据
			                		Map<String, String> paramsMap = new LinkedHashMap<String, String>();
			                		paramsMap.put("biz_type", "ContractFileUpload");
			                		paramsMap.put("MerBillNo", merBillNo);
			                		paramsMap.put("BorrowId", String.valueOf(loanDo2.getLoanId()));
			                		paramsMap.put("FileName", fileName);
			                		paramsMap.put("MerPriv", "");
			                		Map<String,String> resultMap = ExchangeDataUtils.submitData(paramsMap);
			                		if("000000".equals(resultMap.get("RespCode"))){
			                			LoanDO loanDo = new LoanDO();
			                			loanDo.setLoanId(loanDo2.getLoanId());
			                			loanDo.setContractIssend("1");
			                			loanDao.update(loanDo);//修改合同发送状态
			                		}
			                		if(!"000000".equals(resultMap.get("RespCode"))){
			                			logger.error("投资合同接口上传调用失败，"+resultMap.get("RespDesc"));
			                		}
			                	}else{
			                		logger.error("放款合同文件上传失败，请重新上传!");
			                	}
			        	        
			        		}
			        		catch (Exception e)
			        		{
			        			logger.error("================向银行上传放款合同文件失败================");
			        			e.printStackTrace();
			        		}
			        	}

			            // 6. 短信通知
			            try {
			            	logger.info("================短信通知================");
			            	if (fullTransferRecordDO.getFtrBidType().equals(BidType.COMMON_LOAN.getType())) { // 普通标划转
			            		SmsNotifyCfg smsNotifyCfg = JSON.parseObject(sysConfigService.querySysConfig(SysConfigEntry.SMS_NOTIFY_COMMON_TRANSFER_CFG).getData().getConfValue(), SmsNotifyCfg.class);
			            		if(smsNotifyCfg.getSwitchState() == 1) {
			            			SmsNotifyDO smsNotifyDO = new SmsNotifyDO();
			            			String pattern = smsNotifyCfg.getContentTemplate();
			            			LoanDO loanDo = loanDao.findByLoanId(fullTransferRecordDO.getFtrBidId());
			                		UserDO userDo = userService.findById(loanDo.getLoanUserId()).getData();
			                		String phone = null;
			                		if(userDo.getUserType() == 1) {
			                			phone = userDo.getUserPhone();
			                		} else {
			                			phone = companyCustomerDao.findByUserId(userDo.getUserId()).getCcContactPhone();
			                		}
			                		String content = MessageFormat.format(pattern, loanDo.getLoanNo(), loanDo.getLoanMoney());
			                		smsNotifyDO.setReceivePhone(phone);
			                    	smsNotifyDO.setContent(content);
			                    	smsNotifyDO.setCreateTime(new Date());
			                    	smsNotifyDO.setSendStatus(0);
			                    	smsNotifyDO.setSendCount(0);
			                    	smsNotifyDao.insert(smsNotifyDO);
			            		}
			            	}
			    		} catch (Exception e) {
			    			logger.error("================短信通知失败================");
			    			e.printStackTrace();
			    		}
			        }
			        logger.info("============放款成功===========");
			}else{
				logger.info("放款失败====="+RespDesc);
				result.setSuccess(false);
				if (null != fullTransferRecordDO && fullTransferRecordDO.getFtrBidType().equals(BidType.COMMON_LOAN.getType())) {
					 // 修改交易记录状态
			        logger.info("================修改交易记录状态================");
			        DealRecordDO updateDealRecord = new DealRecordDO();
			        updateDealRecord.setDrInnerSeqNo(fullTransferRecordDO.getFtrSeqNo());//与手续费记录、投资划转记录流水号一致
			        updateDealRecord.setDrState(DealState.FAILURE.getState());
					int flag1 = dealRecordDao.updateDealRecordState(updateDealRecord);
					if (flag1 <= 0) {
						throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),"修改交易记录状态出错");
					}
					logger.info("================普通标状态修改，项目跟踪状态记录================");
		            Loan toModify = new Loan();
		            toModify.setLoanId(fullTransferRecordDO.getFtrBidId());
		            toModify.setLoanState(LoanState.FULL_REVIEW_PASS);
		            toModify.setLoanFullTransferedtime(new Date());

		            // 项目跟踪状态记录
		            LoanTraceRecord traceRecord = new LoanTraceRecord();
		            traceRecord.setCreator(fullTransferRecordDO.getFtrOperator());
		            traceRecord.setLoanId(fullTransferRecordDO.getFtrBidId());
		            traceRecord.setLoanTraceOperation(LoanTraceOperation.loanMoneyTransfer);
		            traceRecord.setOldLoanState(LoanState.MONEY_TRANSFERING);
		            traceRecord.setNewLoanState(LoanState.FULL_REVIEW_PASS);
		            traceRecord.setNote("普通标项目满标资金划转失败");

		            BaseResult modResult = loanService.modifyLoanInfo(toModify, traceRecord);
		            if (!modResult.isSuccess()) {
		            	logger.warn(modResult.getMessage());
		                throw new BusinessException("普通标状态修改失败");
		            }
		            logger.info("================更新满标资金划转记录状态================");
		            // 2. 更新满标资金划转记录状态
			        FullTransferRecordDO toModify1 = new FullTransferRecordDO();
			        toModify1.setFtrSeqNo(fullTransferRecordDO.getFtrSeqNo());
			        toModify1.setFtrDealState(DealState.FAILURE.getState());
			        fullTransferRecordDao.updateByInnerSeqNo(toModify1);
			        logger.info("================删除这个项目之前的还款计划================");
			        //删除这个项目之前的还款计划
			        paymentPlanDao.batchDeletePlanByLoanId(fullTransferRecordDO.getFtrBidId());
			        logger.info("================删除这个项目之前的收益还款计划================");
			        //删除这个项目之前的收益还款计划
			        incomePlanDao.batchDeletePlanByLoanId(fullTransferRecordDO.getFtrBidId());
		        }
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[放款] error: ", e.getMessage());
			throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),e.getMessage());
		}
		return result;
	}
}

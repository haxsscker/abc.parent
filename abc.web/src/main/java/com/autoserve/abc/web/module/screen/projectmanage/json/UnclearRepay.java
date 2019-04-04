package com.autoserve.abc.web.module.screen.projectmanage.json;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.AheadRepay;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.PaymentPlanDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.AheadRepayDao;
import com.autoserve.abc.dao.intf.DealRecordDao;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.PaymentPlanDao;
import com.autoserve.abc.service.biz.convert.DealRecordConverter;
import com.autoserve.abc.service.biz.convert.LoanConverter;
import com.autoserve.abc.service.biz.entity.DealRecord;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.enums.PayType;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data.SftpTool;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.AuthorizeUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ExchangeDataUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileNameUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.SeqnoHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
/**
 * 未结清还款
 * @author sunlu
 *
 */
public class UnclearRepay {
	private final static Logger logger = LoggerFactory.getLogger(UnclearRepay.class);
	@Resource
    private AccountInfoService    accountInfoService;
    @Resource
    private UserService           userService;
    @Resource
	private DealRecordDao dealRecordDao;
    @Resource
	private DoubleDryService doubleDryService;
    @Resource
    private LoanDao           loanDao;
    @Resource
    private PaymentPlanDao        paymentPlanDao;
    @Resource
    private InvestDao             investDao;
    @Autowired
	private AheadRepayDao aheadRepayDao;
	
	public JsonBaseVO execute(ParameterParser params) {
		BaseResult baseResult = new BaseResult();
		JSONObject formJson = JSON.parseObject(params.getString("main"));
		String loanNo=formJson.getString("loanNo");
		Integer userId=Integer.valueOf(formJson.getString("userId"));
		BigDecimal money=new BigDecimal(formJson.getString("money"));
		JsonBaseVO result = null;
		try {
			result = this.unclearRepay(loanNo, userId, money);
		} catch (Exception e) {
			logger.error("还款异常！！"+e.getMessage());
			e.printStackTrace();
			baseResult.setSuccess(false);
			baseResult.setMessage(e.getMessage());
			return ResultMapper.toBaseVO(baseResult);
		}
		return result;
	}
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	private JsonBaseVO unclearRepay(String loanNo,Integer userId,BigDecimal money){
		BaseResult baseResult = new BaseResult();
		LoanDO loanDO = new LoanDO();
		loanDO.setLoanNo(loanNo);
		loanDO = loanDao.findByParam(loanDO);
		if(null == loanDO){
			baseResult.setSuccess(false);
			baseResult.setMessage("项目不存在");
			return ResultMapper.toBaseVO(baseResult);
		}
		//查询原来的还款计划最后一期
        int maxRepayPeriod = paymentPlanDao.findMaxPeriodByLoanId(loanDO.getLoanId());
        
		if(LoanState.REPAY_COMPLETED.state != loanDO.getLoanState()){
			baseResult.setSuccess(false);
			baseResult.setMessage("项目不是已结清状态");
			return ResultMapper.toBaseVO(baseResult);
		}
		AheadRepay aheadRepay  = new AheadRepay();
		aheadRepay.setLoanId(loanDO.getLoanId());
		aheadRepay = aheadRepayDao.findOne(aheadRepay);
		if(null == aheadRepay){
			baseResult.setSuccess(false);
			baseResult.setMessage("项目没有做过提前还款");
			return ResultMapper.toBaseVO(baseResult);
		}
		PaymentPlanDO aheadPaymentPlanDO = paymentPlanDao.findAheadPaymentPlan(loanDO.getLoanId(),PayState.CLEAR.getState(),PayType.AHEAD_CLEAR.getType());
		if(null == aheadPaymentPlanDO){
			baseResult.setSuccess(false);
			baseResult.setMessage("还款计划中未查到提前还款记录");
			return ResultMapper.toBaseVO(baseResult);
		}
		if(aheadPaymentPlanDO.getPpLoanPeriod()==maxRepayPeriod){
			baseResult.setSuccess(false);
			baseResult.setMessage("项目提前还款期数已是最后一期");
			return ResultMapper.toBaseVO(baseResult);
		}
		//借款人查询
		UserDO userDO = userService.findById(loanDO.getLoanUserId()).getData();
        userDO.setAccountCategory(AccountCategory.LOANACCOUNT.type);
        AccountInfoDO loanAccount = accountInfoService.getAccountByCategory(userDO);
        if(null == loanAccount || StringUtil.isEmpty(loanAccount.getAccountNo())){
			baseResult.setSuccess(false);
			baseResult.setMessage("项目借款人融资账号未开户");
			return ResultMapper.toBaseVO(baseResult);
		}
        Double[] accountBacance = doubleDryService.queryBalanceDetail(loanAccount.getAccountNo());
        double loanAvlBal = accountBacance[3];
        if(loanAvlBal<money.doubleValue()){
        	baseResult.setSuccess(false);
			baseResult.setMessage("借款人融资户余额不足！");
			return ResultMapper.toBaseVO(baseResult);
        }
        //还款授权
		Date authorizeRepayStartDate=userDO.getAuthorizeRepayStartDate();
		Date authorizeRepayEndDate=userDO.getAuthorizeRepayEndDate();
		BigDecimal authorizeRepayAmount=null != userDO.getAuthorizeRepayAmount()?userDO.getAuthorizeRepayAmount():BigDecimal.ZERO;
		//还款授权判断
	      if(!"60".equals(userDO.getAuthorizeRepayType())){
	      	baseResult.setSuccess(false);
			baseResult.setMessage("借款人还未开启还款授权，请先去授权！");
			return ResultMapper.toBaseVO(baseResult);
		}else if(!"有效".equals(AuthorizeUtil.isAuthorize(authorizeRepayStartDate,authorizeRepayEndDate))){
			baseResult.setSuccess(false);
			baseResult.setMessage("借款人还款授权"+AuthorizeUtil.isAuthorize(authorizeRepayStartDate,authorizeRepayEndDate)+",请去修改！");
			return ResultMapper.toBaseVO(baseResult);
		}else if(money.doubleValue()>authorizeRepayAmount.doubleValue()){
			baseResult.setSuccess(false);
			baseResult.setMessage("还款金额超过借款人的还款授权金额,请去修改！");
			return ResultMapper.toBaseVO(baseResult);
		}
        //收款人查询
        UserDO invUserDO = userService.findById(userId).getData();
        invUserDO.setAccountCategory(AccountCategory.INVESTACCOUNT.type);
        AccountInfoDO invAccount = accountInfoService.getAccountByCategory(invUserDO);
        if(null == invAccount || StringUtil.isEmpty(invAccount.getAccountNo())){
			baseResult.setSuccess(false);
			baseResult.setMessage("收款人投资账号未开户");
			return ResultMapper.toBaseVO(baseResult);
		}
        InvestDO invest = new InvestDO();
        invest.setInBidId(loanDO.getLoanId());
        invest.setInUserId(userId);
        List<InvestDO> investList = investDao.findListByParam(invest, null);
        if(null == investList || investList.size() <= 0){
        	baseResult.setSuccess(false);
			baseResult.setMessage("收款人没有投资该项目");
			return ResultMapper.toBaseVO(baseResult);
        }
        //创建交易记录
        List<DealRecordDO> dealRecordDOList = new LinkedList<DealRecordDO>();
        List<DealRecord> dealRecordList = new ArrayList<DealRecord>();
        String seqno = InnerSeqNo.getMerchInstance().getUniqueNo();
        //追加利息
        DealRecordDO dealRecordDo = new DealRecordDO();
        dealRecordDo.setDrBusinessId(loanDO.getLoanId());
        dealRecordDo.setDrMoneyAmount(money);
        dealRecordDo.setDrOperateDate(new Date());
        dealRecordDo.setDrOperator(1);
        dealRecordDo.setDrPayAccount(loanAccount.getAccountNo());
        dealRecordDo.setDrReceiveAccount(invAccount.getAccountNo());
        dealRecordDo.setDrInnerSeqNo(seqno);
        dealRecordDo.setDrType(DealType.PAYBACK.type);
        dealRecordDo.setDrDetailType(DealDetailType.ADDITIONAL_INTEREST.type);
        dealRecordDo.setDrState(DealState.NOCALLBACK.getState());
        dealRecordDOList.add(dealRecordDo);
        dealRecordList.add(DealRecordConverter.toDealRecord(dealRecordDo));
        // 保存交易记录
 		int flag = dealRecordDao.batchInsert(dealRecordDOList);
 		if (flag <= 0) {// 插入不成功处理
			throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),
					"[DealRecordServiceImpl][createBusinessRecord] 批量插入交易记录出错");
		}
        //  执行交易
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("BorrowId", String.valueOf(loanDO.getLoanId()));
        paramsMap.put("MerBillNo", seqno);
    	paramsMap.put("RepayTyp", "1");//1-正常还款 2-担保还款（从担保账户扣款）
        paramsMap.put("repay_inst_tot", String.valueOf(maxRepayPeriod));//分期还款总期数 
        paramsMap.put("repay_inst_cur", String.valueOf(maxRepayPeriod));//分期还款当前期数,注意当前期数与总期数相同时视为还款结束。也就是若是提前还款这里要传总的期数
        Map<String, Object> ftpFileContentMap = getRePayFileData(LoanConverter.toLoan(loanDO),dealRecordList);
        paramsMap.put("fileData", FormatHelper.formatFtpFileContent(ftpFileContentMap));
        Map<String, String> resultMap = this.doRepay(paramsMap);
        if(!"000000".equals(resultMap.get("RespCode"))){
        	String errMsg = StringUtils.isNotEmpty(resultMap.get("RespDesc"))?resultMap.get("RespDesc"):"银行接口调用异常";
        	if("RED_TIMEOUT".equals(resultMap.get("RespCode"))){
        		baseResult.setSuccess(false);
    			baseResult.setMessage(resultMap.get("RespDesc"));
    			return ResultMapper.toBaseVO(baseResult);
            }else{
            	throw new BusinessException(errMsg);
            }
        }else{
        	baseResult.setMessage("还款申请成功，请等待处理结果！");
        }
		return ResultMapper.toBaseVO(baseResult);
	}
	/**
	 * @content:渤海银行接口，构造还款申请文件数据fileData
	 */
    private Map<String,Object> getRePayFileData(Loan loan,List<DealRecord> dealRecord){
    	if(loan == null || dealRecord == null || dealRecord.size() <= 0){
    		return null;
    	}
    	//获取借款人平台账号，因为担保代还时dealRecord里的付款账户是担保账户，不是借款人账户，但是接口要求是借款人账户，所以这里通过Loan来获取
    	UserDO userDO = userService.findById(loan.getLoanUserId()).getData();

        userDO.setAccountCategory(AccountCategory.LOANACCOUNT.type);
        AccountInfoDO loanAccount = accountInfoService.getAccountByCategory(userDO);

    	/** ftp文件数据*/
    	Map<String, Object> ftpFileContentMap = new HashMap<String, Object>();
    	/** 汇总数据*/
    	Map<String, String> summaryMap=new LinkedHashMap<String, String>();
    	/** 明细数据*/
    	List<Map<String, String>> detailListMap= new ArrayList<Map<String,String>>();
    	Map<String, Map<String,String>> detailMap = new HashMap<String, Map<String,String>>();
    	BigDecimal FeeAmt = BigDecimal.ZERO;
    	BigDecimal TransAmt = BigDecimal.ZERO;
    	for(int i = 0;i<dealRecord.size();i++){
    		DealRecord dr = dealRecord.get(i);
    		if(DealDetailType.ADDITIONAL_INTEREST.getType() == dr.getDetailType().intValue()){
    			TransAmt = TransAmt.add(dr.getMoneyAmount());
    			//获取收款账户
    			String receiveAccount = dr.getReceiveAccount();
    			if(!detailMap.containsKey(receiveAccount)){
    				Map<String, String> linkedMap = new LinkedHashMap<String, String>();
    				linkedMap.put("ID", SeqnoHelper.get8UUID());
        			linkedMap.put("PlaCustId", receiveAccount);
        			linkedMap.put("TransAmt", FormatHelper.changeY2F(0.00));
        			linkedMap.put("Interest", FormatHelper.changeY2F(0.00));
        			linkedMap.put("Inves_fee", FormatHelper.changeY2F(0.00));
    				detailMap.put(receiveAccount, linkedMap);
    			}
    			Map<String, String> linkedMap = detailMap.get(receiveAccount);
    			if(DealDetailType.PAYBACK_CAPITAL.getType() == dr.getDetailType().intValue()){
    				//累加到TransAmt字段
    				String money =  FormatHelper.changeY2F(dr.getMoneyAmount());
    				String dtTransAmt = linkedMap.get("TransAmt");
    				linkedMap.put("TransAmt",(new BigDecimal(dtTransAmt).add(new BigDecimal(money)))+"");
    			}else{
    				//累加到Interest字段
    				String money =  FormatHelper.changeY2F(dr.getMoneyAmount());
    				String dtInterest = linkedMap.get("Interest");
    				linkedMap.put("Interest",(new BigDecimal(dtInterest).add(new BigDecimal(money)))+"");
    			}
	    	}else{
	    		FeeAmt = FeeAmt.add(dr.getMoneyAmount());
	    	}
    	}
    	for(String key : detailMap.keySet()){
    		detailListMap.add(detailMap.get(key));
    	}
    	TransAmt = TransAmt.add(FeeAmt);
    	summaryMap.put("char_set", ConfigHelper.getConfig("charset"));
        summaryMap.put("partner_id", ConfigHelper.getConfig("merchantId"));
        summaryMap.put("MerBillNo", dealRecord.get(0).getInnerSeqNo());
        
        summaryMap.put("TransAmt", FormatHelper.changeY2F(TransAmt));
        summaryMap.put("FeeAmt", FormatHelper.changeY2F(FeeAmt));
        summaryMap.put("BorrowId", String.valueOf(loan.getLoanId()));
        
        summaryMap.put("BorrowerAmt", FormatHelper.changeY2F(loan.getLoanMoney()));
        summaryMap.put("BorrCustId", loanAccount.getAccountNo());
        summaryMap.put("MerPriv", dealRecord.get(0).getInnerSeqNo());
    	summaryMap.put("TotalNum", String.valueOf(detailListMap.size()));
    	
    	ftpFileContentMap.put("summaryMap", summaryMap);
        ftpFileContentMap.put("detailListMap", detailListMap);
    	return ftpFileContentMap;
    }
	 /**
     * 渤海银行接口，还款(调用接口)
     * 1、ftp上传放款文件明细
     * 2、放款申请接口
     */
    private Map<String, String> doRepay(Map<String, String> map) {
    	Map<String, String> resultMap = new HashMap<String, String>();
    	SftpTool ftp = new SftpTool();
    	// 本地路径
        String localPath = ConfigHelper.getSftpLocalPath();
        // 目标路径
        String remotePath = ConfigHelper.getSftpRemotePath()+FormatHelper.formatDate(new Date(), "yyyyMMdd");
        //还款临时修改
//        String remotePath = ConfigHelper.getSftpRemotePath()+"20190219";
        //上传文件名
        String merBillNo = map.get("MerBillNo");
        String fileName = FileNameUtil.getFileName("FileRepayment", "txt",merBillNo);
        //上送成功标志文件名
        String uploadSuccessFileName = FileNameUtil.getFileNameBySuffix(fileName, "OK");
        //数据写入文件
        String fileData = map.get("fileData");
        boolean isWriteSuccess = FileUtils.writeByBufferedWriter(fileData, localPath+fileName);
        if(isWriteSuccess){
        	//ftp上传文件
        	ftp.connect();
        	boolean isUploadSuccess = ftp.uploadFile(remotePath, fileName, localPath, fileName);
        	isUploadSuccess = ftp.uploadFile(remotePath, uploadSuccessFileName, localPath, uploadSuccessFileName);
        	ftp.disconnect();
        	if(isUploadSuccess){
        		//发送请求,获取数据
        		Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        		paramsMap.put("biz_type", "FileRepayment");
        		paramsMap.put("MerBillNo", merBillNo);
        		paramsMap.put("BorrowId", map.get("BorrowId"));
        		paramsMap.put("RepayTyp", map.get("RepayTyp"));
        		paramsMap.put("BgRetUrl", ConfigHelper.getConfig("notifyurlprefix") + "/bhyhNotify/unclearRepayNotify.json");
        		paramsMap.put("repay_inst_tot", map.get("repay_inst_tot"));//分期还款总期数 
        	    paramsMap.put("repay_inst_cur", map.get("repay_inst_cur"));//分期还款当前期数,注意当前期数与总期数相同时视为还款结束。也就是若是提前还款这里要传总的期数
        	    paramsMap.put("FileName", fileName);
        		paramsMap.put("MerPriv", "");
        		resultMap = ExchangeDataUtils.submitData(paramsMap);
        	}else{
        		resultMap.put("RespCode", "fail");
        		resultMap.put("RespDesc", "还款文件上传失败，请重新上传!");
        	}
        }else{
        	resultMap.put("RespCode", "fail");
    		resultMap.put("RespDesc", "还款文件写入失败!");
        }
        return resultMap;
    }
    
}


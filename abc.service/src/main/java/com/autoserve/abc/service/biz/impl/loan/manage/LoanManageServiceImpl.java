/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.dao.dataobject.InvestOrderDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.RedUseDO;
import com.autoserve.abc.dao.dataobject.TraceRecordDO;
import com.autoserve.abc.dao.intf.AccountInfoDao;
import com.autoserve.abc.dao.intf.DealRecordDao;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.TraceRecordDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.convert.LoanTraceRecordConverter;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.entity.DealDetail;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.DealRecord;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanTraceRecord;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoanTraceOperation;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.enums.RsState;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data.SftpTool;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ExchangeDataUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileNameUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.SeqnoHelper;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.invest.InvestOrderService;
import com.autoserve.abc.service.biz.intf.loan.AutoInvestLoanService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.manage.LoanManageService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 普通标项目管理服务
 *
 * @author segen189 2015年1月9日 下午5:13:21
 */
@Service
public class LoanManageServiceImpl implements LoanManageService {
    private static final Logger  log = LoggerFactory.getLogger(LoanManageServiceImpl.class);

    @Resource
    private LoanDao              loanDao;

    @Resource
    private LoanService          loanService;

    @Resource
    private InvestDao            investDao;

    @Resource
    private TraceRecordDao       traceRecordDao;

    @Resource
    private ReviewService        reviewService;

    @Resource
    private DealRecordService    dealRecordService;

    @Resource
    private Callback<DealNotify> loanCanceledCallback;
    
    @Resource
    private InvestOrderService    investOrderService;
    
    @Autowired
   	private AutoInvestLoanService autoInvestLoanService;
    @Resource
    private AccountInfoDao      accountDao;
    @Resource
    private DealRecordDao             dealRecordDao;
    @Resource
    private EmployeeService employeeService;
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult revokeToWaitProjectReview(int loanId, int operatorId, String note) {
        // 从待发布状态有条件地撤回到待项目初审
        LoanTraceRecord param = new LoanTraceRecord();
        param.setLoanId(loanId);
        param.setOldLoanState(LoanState.WAIT_RELEASE);
        param.setNewLoanState(LoanState.BID_INVITING);
        TraceRecordDO traceRecordDO = traceRecordDao.findOneByParam(LoanTraceRecordConverter.toTraceRecordDO(param));

        if (traceRecordDO != null) {
            BaseResult result = new BaseResult();
            return result.setError(CommonResultCode.BIZ_ERROR, "此时项目的状态不允许撤回");
        }
        LoanTraceRecord traceRecord = new LoanTraceRecord();

        traceRecord.setCreator(operatorId);
        traceRecord.setLoanId(loanId);
        traceRecord.setLoanTraceOperation(LoanTraceOperation.revokeToWaitProjectReview);
        traceRecord.setOldLoanState(LoanState.WAIT_RELEASE);
        traceRecord.setNewLoanState(LoanState.WAIT_PROJECT_REVIEW);
        traceRecord.setNote(note);

        BaseResult changeResult = loanService.changeLoanState(traceRecord);
        //将review的状态变更为待项目初审
        BaseResult reviewResult = reviewService.updateEndAndState(loanId, ReviewType.LOAN_FIRST_REVIEW);
        if (!reviewResult.isSuccess()) {
            throw new BusinessException(CommonResultCode.BIZ_ERROR.getCode(), "项目撤回失败！");
        }
        return changeResult;
    }

    @Override
    public BaseResult sendBackToWaitProjectReview(int loanId, int operatorId, String note) {
        // 把项目状态从待发布退回到待项目初审
        LoanTraceRecord traceRecord = new LoanTraceRecord();

        traceRecord.setCreator(operatorId);
        traceRecord.setLoanId(loanId);
        traceRecord.setLoanTraceOperation(LoanTraceOperation.sendBackToWaitProjectReview);
        traceRecord.setOldLoanState(LoanState.WAIT_RELEASE);
        traceRecord.setNewLoanState(LoanState.WAIT_PROJECT_REVIEW);
        traceRecord.setNote(note);

        BaseResult changeResult = loanService.changeLoanState(traceRecord);
        // 将review的状态变更为待项目初审
        BaseResult reviewResult = reviewService.updateEndAndState(loanId, ReviewType.LOAN_FIRST_REVIEW);
        if (!reviewResult.isSuccess()) {
            throw new BusinessException(CommonResultCode.BIZ_ERROR.getCode(), "项目回失败！");
        }
        return changeResult;
    }

    @Override
    public BaseResult sendToWaitRelease(int loanId, int operatorId, String note) {
        // 把项目状态从项目初审通过改为待发布
        LoanTraceRecord traceRecord = new LoanTraceRecord();

        traceRecord.setCreator(operatorId);
        traceRecord.setLoanId(loanId);
        traceRecord.setLoanTraceOperation(LoanTraceOperation.sendToWaitRelease);
        traceRecord.setOldLoanState(LoanState.PROJECT_REVIEW_PASS);
        traceRecord.setNewLoanState(LoanState.WAIT_RELEASE);
        traceRecord.setNote(note);

        BaseResult changeResult = loanService.changeLoanState(traceRecord);
        return changeResult;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult removeProject(int loanId, int operatorId, String note) {
        // 融资维护项目删除
        BaseResult result = new BaseResult();

        LoanTraceRecord param = new LoanTraceRecord();
        param.setLoanId(loanId);
        param.setOldLoanState(LoanState.WAIT_MAINTAIN_REVIEW);
        param.setNewLoanState(LoanState.BID_INVITING);

        TraceRecordDO traceRecordDO = traceRecordDao.findOneByParam(LoanTraceRecordConverter.toTraceRecordDO(param));
        if (traceRecordDO != null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "此时项目的状态不允许删除");
        }

        LoanTraceRecord traceRecord = new LoanTraceRecord();

        traceRecord.setCreator(operatorId);
        traceRecord.setLoanId(loanId);
        traceRecord.setLoanTraceOperation(LoanTraceOperation.removeProject);
        traceRecord.setOldLoanState(LoanState.WAIT_MAINTAIN_REVIEW);
        traceRecord.setNewLoanState(LoanState.DELETED);
        traceRecord.setNote(note);

        BaseResult changeResult = loanService.changeLoanState(traceRecord);
        if (!changeResult.isSuccess()) {
            throw new BusinessException("融资维护项目删除失败");
        }

        return changeResult;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult forceLoanToFull(int loanId, int operatorId, String note) {
        // 普通标强制满标
        Loan loan = new Loan();
        loan.setLoanId(loanId);
        loan.setLoanState(LoanState.FULL_WAIT_REVIEW);
        loan.setLoanInvestFulltime(new Date());

        LoanTraceRecord traceRecord = new LoanTraceRecord();

        traceRecord.setCreator(operatorId);
        traceRecord.setLoanId(loanId);
        traceRecord.setLoanTraceOperation(LoanTraceOperation.forceLoanToFull);
        traceRecord.setOldLoanState(LoanState.BID_INVITING);
        traceRecord.setNewLoanState(LoanState.FULL_WAIT_REVIEW);
        traceRecord.setNote(note);

        BaseResult changeResult = loanService.modifyLoanInfo(loan, traceRecord);
        if (!changeResult.isSuccess()) {
            log.error("普通标强制满标失败！{}", changeResult.getMessage());
            throw new BusinessException("普通标强制满标失败");
        }

        // 发起审核流程
        Review review = new Review();
        review.setApplyId(loanId);
        review.setType(ReviewType.LOAN_FULL_BID_REVIEW);
        review.setCurrRole(BaseRoleType.PLATFORM_SERVICE);
        BaseResult reviewRes = reviewService.initiateReview(review);
        if (!reviewRes.isSuccess()) {
            log.error("发起项目满标审核失败！LoanId={}", loanId);
            throw new BusinessException("发起项目满标审核失败");
        }

        return BaseResult.SUCCESS;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult cancelLoan(int loanId, int operatorId, String note) {
        boolean isBatInvestCancleFlag=true;
        // 获取所有投资的流水号集合
        InvestDO investParam = new InvestDO();
        investParam.setInBidId(loanId);
        investParam.setInBidType(BidType.COMMON_LOAN.getType());
        investParam.setInInvestState(InvestState.PAID.getState());
        List<InvestDO> investDOList = investDao.findListByParam(investParam, null);
        if (CollectionUtils.isEmpty(investDOList)) {
        	isBatInvestCancleFlag=false;
//            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "待解冻的投资列表查询为空");
        }else{
        	List<String> seqNos = new ArrayList<String>();
        	for (InvestDO investDO : investDOList) {
        		seqNos.add(investDO.getInInnerSeqNo());
        	}
        	// 根据流水号集合查询交易记录
        	ListResult<DealRecord> oldDealResult = dealRecordService.queryDealRecordsByInnerSeqNo(seqNos);
        	if (!oldDealResult.isSuccess() || CollectionUtils.isEmpty(oldDealResult.getData())) {
        		return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "待解冻的投资交易记录查询失败");
        	}
        }
        String innerSeqNo = ConfigHelper.getConfig("merchantId")+SeqnoHelper.getId(16);
        LoanState loanState=LoanState.BID_CANCELING;
        if(!isBatInvestCancleFlag){
        	loanState=LoanState.BID_CANCELED;
        }
        //  更新标的流水号
        LoanDO toModify = new LoanDO();
        toModify.setLoanId(loanId);
        toModify.setSeqNo(innerSeqNo);//修改标的流水号，方便回调处理
        int r = loanDao.update(toModify);
        if(r<=0){
        	log.error("更新标的流水号失败");
        	throw new BusinessException("更新标的流水号失败");
        }
        // 项目跟踪记录
        LoanTraceRecord traceRecord = new LoanTraceRecord();
        traceRecord.setCreator(operatorId);
        traceRecord.setLoanId(loanId);
        traceRecord.setLoanTraceOperation(LoanTraceOperation.cancelLoan);
        traceRecord.setOldLoanState(LoanState.FULL_WAIT_REVIEW);
        //end
        traceRecord.setNewLoanState(loanState);
        traceRecord.setNote(note);

        BaseResult changeResult = loanService.changeLoanState(traceRecord);
        if (!changeResult.isSuccess()) {
        	log.error("项目状态更改失败");
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "项目状态更改失败");
        }
        /**
         * 调用批量投标撤销接口
         */
        if(isBatInvestCancleFlag){
        	log.info("============================调用批量投标撤销接口============================");
        	String batchNo = innerSeqNo.substring(innerSeqNo.length()-10);//SeqnoHelper.getId(10);
        	//添加解冻待处理交易记录
        	List<DealRecordDO> dealRecordDoList = new ArrayList<DealRecordDO>();
        	DealRecordDO dealRecord = null;
        	for (InvestDO investDO : investDOList) {
        		dealRecord = new DealRecordDO();
        		dealRecord.setDrBusinessId(loanId);
        		dealRecord.setDrMoneyAmount(investDO.getInValidInvestMoney());
        		dealRecord.setDrOperateDate(new Date());
        		dealRecord.setDrOperator(operatorId);
        		dealRecord.setDrPayAccount("");
        		dealRecord.setDrReceiveAccount(investDO.getInAccountNo());
        		dealRecord.setDrInnerSeqNo(innerSeqNo);
        		dealRecord.setDrType(DealType.ABORT_BID.getType());
        		dealRecord.setDrDetailType(DealDetailType.ABORT_BID_MONEY.getType());
        		dealRecord.setDrState(DealState.NOCALLBACK.getState());
        		dealRecordDoList.add(dealRecord);
        	}
        	// 保存交易记录
        	int flag = dealRecordService.batchInsert(dealRecordDoList);
        	if (flag <= 0) {// 插入不成功处理
        		throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),"批量插入交易记录出错");
        	}
        	Map<String, String> paramsMap = new HashMap<String, String>();
        	paramsMap.put("BatchNo", batchNo);
        	paramsMap.put("MerBillNo", innerSeqNo);
        	//获取放款申请文件数据fileData
        	Map<String, Object> ftpFileContentMap= new HashMap<String, Object>();
        	Map<String, String> summaryMap=new LinkedHashMap<String, String>();
        	summaryMap.put("char_set", ConfigHelper.getConfig("charset"));
        	summaryMap.put("partner_id", ConfigHelper.getConfig("merchantId"));
        	summaryMap.put("BatchNo", batchNo);
        	summaryMap.put("TransDate", FormatHelper.formatDate(new Date(),"yyyyMMdd"));
        	summaryMap.put("TotalNum", String.valueOf(investDOList.size()));
        	List<Map<String, String>> detailListMap= new ArrayList<Map<String,String>>();
        	Map<String, String> detailMap =null;
        	for(int i=0,len=investDOList.size();i<len;i++){
        		detailMap = new LinkedHashMap<String, String>();
        		detailMap.put("ID", String.valueOf(investDOList.get(i).getInId()));
        		detailMap.put("OldTransId", investDOList.get(i).getIoOutSeqNo());
        		detailMap.put("PlaCustId", investDOList.get(i).getInAccountNo());
        		detailMap.put("TransAmt", FormatHelper.changeY2F(investDOList.get(i).getInValidInvestMoney()));
        		detailMap.put("FreezeId", investDOList.get(i).getInFreezeId());
        		detailListMap.add(detailMap);
        	}
        	ftpFileContentMap.put("summaryMap", summaryMap);
        	ftpFileContentMap.put("detailListMap", detailListMap);
        	paramsMap.put("fileData", FormatHelper.formatFtpFileContent(ftpFileContentMap));
        	Map<String, String> resultMap = this.batInvestCancle(paramsMap);
        	if(!"000000".equals(resultMap.get("RespCode"))){
        		log.info("调用批量投标撤销接口失败============================"+resultMap.get("RespDesc"));
        		throw new BusinessException(CommonResultCode.ERROR_DB.getCode(), resultMap.get("RespDesc"));
        	}
        }
        if(!isBatInvestCancleFlag){
        	log.info("============================调用流标接口============================");
        	//没有人投资则直接调用流标接口
			Map<String, String> map = new HashMap<String, String>();
			map.put("BorrowId", String.valueOf(loanId));
			Map<String, String> resultMap = this.cancelBid(map);
			if(!"000000".equals(resultMap.get("RespCode")) && !"MCG99993".equals(resultMap.get("RespCode"))){
				log.error("调用流标接口失败======"+ resultMap.get("RespDesc"));
				throw new BusinessException(CommonResultCode.ERROR_DB.getCode(), resultMap.get("RespDesc"));
			}else{
				return BaseResult.CANCELSUCCESS;
			}
        }
        return BaseResult.SUCCESS;
    }
    /**
     * 批量投标撤销接口
     * @param map
     * @return
     */
	public Map<String, String> batInvestCancle(Map<String, String> map){
    	Map<String, String> resultMap = new HashMap<String, String>();
    	SftpTool ftp = new SftpTool();
    	// 本地路径
        String localPath = ConfigHelper.getSftpLocalPath();
        // 目标路径
        String remotePath = ConfigHelper.getSftpRemotePath()+FormatHelper.formatDate(new Date(), "yyyyMMdd");
        String merBillNo = map.get("MerBillNo");
        String batchNo = map.get("BatchNo");
        //上传文件名
        String fileName = FileNameUtil.getFileName("BatInvestCancle", "txt",batchNo);
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
        		paramsMap.put("biz_type", "BatInvestCancle");
        		paramsMap.put("MerBillNo", merBillNo);
        		paramsMap.put("BatchNo", batchNo);
        		paramsMap.put("BgRetUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("batInvestCancleNotifyUrl"));
        		paramsMap.put("FileName", fileName);
        		paramsMap.put("MerPriv", "");
        		resultMap = ExchangeDataUtils.submitData(paramsMap);
        	}else{
        		resultMap.put("RespCode", "fail");
        		resultMap.put("RespDesc", "批量投标撤销文件上传失败，请重新上传!");
        	}
        }else{
        	resultMap.put("RespCode", "fail");
    		resultMap.put("RespDesc", "批量投标撤销文件写入失败!");
        }
        return resultMap;
    }
	/**
	 * 流标接口
	 * @param map
	 * @return
	 */
	@Override
	public Map<String, String> cancelBid(Map<String, String> map) {
		Map<String, String> resultMap = new HashMap<String, String>();
		//发送请求,获取数据
		Map<String, String> paramsMap = new LinkedHashMap<String, String>();
		paramsMap.put("biz_type", "CancelBid");
		paramsMap.put("MerBillNo", ConfigHelper.getConfig("merchantId")+SeqnoHelper.getId(16));
		paramsMap.put("BorrowId", map.get("BorrowId"));
		resultMap = ExchangeDataUtils.submitData(paramsMap);
		return resultMap;
	}
    @Override
    public BaseResult releaseLoan(final int loanId, int operatorId, String note) {
        BaseResult result = new BaseResult();
        // 发布普通标
        LoanDO loan = loanDao.findByLoanIdWithLock(loanId);
        if (loan == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "该借款标不存在");
        }
        AccountInfoDO loanUserAccount = accountDao.findAccountByUserIdAndUserType(loan.getLoanUserId(),loan.getLoanEmpType());
        String loanUserAccountNo=null != loanUserAccount?loanUserAccount.getAccountNo():"";
        if ("".equals(loanUserAccountNo)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "该借款人没有开户");
        }
        String loanGuarGovAccountNo = "";
        //平台暂不需要担保
        if(null != loan.getLoanGuarGov()){
        	PlainResult<Employee> employee= employeeService.findByGovId(loan.getLoanGuarGov());
            Integer empId = employee.getData().getEmpId();
        	AccountInfoDO loanGuarGovAccount = accountDao.findAccountByUserIdAndUserType(empId,3); 
        	loanGuarGovAccountNo = null != loanGuarGovAccount?loanGuarGovAccount.getAccountNo():"";
        }
        //调用标的发布接口
        //发送请求,获取数据
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("biz_type", "CreateBid");
        paramsMap.put("MerBillNo", ConfigHelper.getConfig("merchantId")+SeqnoHelper.getId(16));
        paramsMap.put("BorrowId", String.valueOf(loanId));
        paramsMap.put("BorrowTyp", 1==loan.getLoanEmpType()?"1":"2");//1-对私 2-对公
        paramsMap.put("BorrowerAmt", FormatHelper.changeY2F(loan.getLoanMoney()));
        paramsMap.put("BorrowerInterestAmt", String.valueOf(loan.getLoanRate().doubleValue()));
        paramsMap.put("BorrCustId", loanUserAccountNo);//借款人账户存管平台客户号
        paramsMap.put("GuaranteeNo",loanGuarGovAccountNo);//担保机构开户账号
        paramsMap.put("BorrowerStartDate", FormatHelper.formatDate(loan.getLoanInvestStarttime(), "yyyyMMdd"));
        paramsMap.put("BorrowerEndDate", FormatHelper.formatDate(loan.getLoanInvestEndtime(), "yyyyMMdd"));
        paramsMap.put("BorrowerRepayDate", FormatHelper.formatDate(loan.getLoanExpireDate(), "yyyyMMdd"));
        paramsMap.put("ReleaseType", "0");
        String investDateType="";
        String loanPeriodType=loan.getLoanPeriodType().toString();
        if("1".equals(loanPeriodType)){
        	investDateType="3";
        }else if("3".equals(loanPeriodType)){
        	investDateType="1";
        }else{
        	investDateType="2";
        }
        paramsMap.put("InvestDateType", investDateType);
        paramsMap.put("InvestPeriod", loan.getLoanPeriod().toString());
        paramsMap.put("BorrowerDetails", loan.getLoanUse());
        paramsMap.put("AutoFlag", "1");//0不允许，1允许 这里设置为允许，投资才可以使用后台方式不输入密码
        paramsMap.put("MerPriv", String.valueOf(loanId));
        log.info("============================调用银行建标接口============================");
        Map<String, String> resultMap = ExchangeDataUtils.submitData(paramsMap);
        if ("000000".equals(resultMap.get("RespCode"))) {//调用接口发布成功
        	Loan pojo = new Loan();
        	pojo.setLoanId(loanId);
        	if (loan.getLoanInvestStarttime() == null) {
        		pojo.setLoanInvestStarttime(new Date());
        	}
        	pojo.setLoanState(LoanState.BID_INVITING);
        	pojo.setLoanReleaseDate(new Date());
        	
        	LoanTraceRecord traceRecord = new LoanTraceRecord();
        	traceRecord.setCreator(operatorId);
        	traceRecord.setLoanId(loanId);
        	traceRecord.setLoanTraceOperation(LoanTraceOperation.releaseLoan);
        	traceRecord.setOldLoanState(LoanState.WAIT_RELEASE);
        	traceRecord.setNewLoanState(LoanState.BID_INVITING);
        	traceRecord.setNote(note);
        	
        	result = loanService.modifyLoanInfo(pojo, traceRecord);
        	
        	//是否自动投标 如果自动投标就执行
        	if("1".equals(loan.getLoanAutomaticBid())){
        		//调用自动投标-在到达投资开始时间时执行
        		Timer timer = new Timer();
        		timer.schedule(new TimerTask() {
        			@Override
        			public void run() {
        				autoInvestLoanService.run(loanId);
        			}
        		}, loan.getLoanInvestStarttime());
        	}
        }else{
        	 log.info("调用银行建标接口失败============================"+resultMap.get("RespDesc"));
        	 return result.setError(CommonResultCode.BIZ_ERROR, resultMap.get("RespDesc"));
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult cancelRelease(int loanId, int operatorId, String note) {
        // 取消发布普通标
        BaseResult result = new BaseResult();

        LoanDO loan = loanDao.findByLoanIdWithLock(loanId);
        if (loan == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "该借款标不存在");
        }

        // 注入的其他dao，仅仅可以用来做查询使用
        // investDao.findByParam 如果查出来有纪录，则说明有人投资了，应不予取消发布
        InvestDO pojo = new InvestDO();
        pojo.setInBidId(loanId);

        if (investDao.findByParam(pojo) != null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "该借款标已存在投资纪录，不予撤销发布");
        }

        LoanTraceRecord traceRecord = new LoanTraceRecord();

        traceRecord.setCreator(operatorId);
        traceRecord.setLoanId(loanId);
        traceRecord.setLoanTraceOperation(LoanTraceOperation.cancelRelease);
        traceRecord.setOldLoanState(LoanState.BID_INVITING);
        traceRecord.setNewLoanState(LoanState.WAIT_RELEASE);
        traceRecord.setNote(note);

        BaseResult changeResult = loanService.changeLoanState(traceRecord);
        return changeResult;
    }

}

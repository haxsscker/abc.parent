/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.dataMigration.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchDO;
import com.autoserve.abc.dao.intf.AccountInfoDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data.SftpTool;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ExchangeDataUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileNameUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.SeqnoHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.sys.OperateLogService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 存量标的迁移至渤海银行平台
 * 
 * @author LZ 2018年4月16日 上午10:23:19
 */
public class LoanDataTransferToBh {
    @Resource
    private AccountInfoService accountService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private OperateLogService  operateLogService;
    
    @Resource
    private LoanDao loanDao;
    
    @Resource
    private AccountInfoDao accountDao;
    
    @Resource
    private TransferLoanService transferLoanService;
    
    @Resource
    private InvestQueryService investQueryService;
    @Resource
    private EmployeeService employeeService;
    
    public JsonBaseVO execute(ParameterParser params) {
        JsonBaseVO returnResult = new JsonBaseVO();
        Integer  loanId = params.getInt("loanId");
        LoanDO loan = loanDao.findById(loanId);//查询需要迁移的标的信息
        //调用标的发布接口
        //发送请求,获取数据
    	AccountInfoDO loanUserAccount = accountDao.findAccountByUserIdAndUserType(loan.getLoanUserId(),loan.getLoanEmpType());
        String loanUserAccountNo=null != loanUserAccount?loanUserAccount.getAccountNo():"";
        if ("".equals(loanUserAccountNo)) {
        	returnResult.setSuccess(false);
    		returnResult.setMessage("该标的"+String.valueOf(loan.getLoanId())+"借款人没有开户");
        }
        String loanGuarGovAccountNo = "";
        if(null != loan.getLoanGuarGov()){
        	PlainResult<Employee> employee= employeeService.findByGovId(loan.getLoanGuarGov());
            Integer empId = employee.getData().getEmpId();
        	AccountInfoDO loanGuarGovAccount = accountDao.findAccountByUserIdAndUserType(empId,3); 
        	loanGuarGovAccountNo = null != loanGuarGovAccount?loanGuarGovAccount.getAccountNo():"";
        }
    	
        InvestSearchDO searchDO = new InvestSearchDO();
        searchDO.setBidId(loan.getLoanId());
        searchDO.setBidType(BidType.COMMON_LOAN.getType());
        searchDO.setInvestStates(Arrays.asList(InvestState.EARNING.getState()));

        ListResult<Invest> investResult = investQueryService.queryInvestList(searchDO);
        
        //查询相关转让标投资信息，因为渤海银行没有转让标概念，所以迁移时默认把对转让标的投资当做对原始标投资！
        InvestSearchDO searchDOForTrans = new InvestSearchDO();
        searchDOForTrans.setOriginId(loan.getLoanId());
        searchDOForTrans.setBidType(BidType.TRANSFER_LOAN.getType());
        searchDOForTrans.setInvestStates(Arrays.asList(InvestState.EARNING.getState()));
        ListResult<Invest> investResultForTrans = investQueryService.queryInvestList(searchDOForTrans);
        List<Invest> investListForTrans= investResultForTrans.getData();
        List<Invest> investList = investResult.getData();
        if(investResultForTrans.isSuccess()){
        	for(Invest transInv:investListForTrans){
            	investList.add(transInv);
            }
        }
        
        if ((!investResult.isSuccess()&&!investResultForTrans.isSuccess()) || CollectionUtils.isEmpty(investList)) {
        	returnResult.setSuccess(false);
    		returnResult.setMessage("该标的"+String.valueOf(loan.getLoanId())+"投资记录查询失败");
        }
    	
    	SftpTool ftp = new SftpTool();
    	// 本地路径
        String localPath = ConfigHelper.getSftpLocalPath();
        // 目标路径
        String remotePath = ConfigHelper.getSftpRemotePath()+FormatHelper.formatDate(new Date(), "yyyyMMdd");        	
    	String merBillNo = ConfigHelper.getConfig("merchantId")+SeqnoHelper.getId(16);
        String fileName = FileNameUtil.getFileName("FileLoanTransfer", "txt",merBillNo);
        //上送成功标志文件名
        String uploadSuccessFileName = FileNameUtil.getFileNameBySuffix(fileName, "OK");
        
        Map<String, Object> ftpFileContentMap= new HashMap<String, Object>();
        Map<String, String> summaryMap=new LinkedHashMap<String, String>();
        summaryMap.put("partner_id", ConfigHelper.getConfig("merchantId"));
        summaryMap.put("MerBillNo", merBillNo);
        summaryMap.put("BorrowId", String.valueOf(loan.getLoanId()));
        summaryMap.put("BorrowTyp", 1==loan.getLoanEmpType()?"1":"2");//1-对私 2-对公
        summaryMap.put("BorrowerAmt", FormatHelper.changeY2F(loan.getLoanMoney()));
        summaryMap.put("BorrowerInterestAmt", String.valueOf(loan.getLoanRate().doubleValue()));            
        summaryMap.put("BorrCustId", loanUserAccountNo);//借款人账户存管平台客户号
        if(1==loan.getLoanEmpType()){
        	summaryMap.put("AccountName","");//对公账户名
        }else if(2==loan.getLoanEmpType()){
        	summaryMap.put("AccountName",loanUserAccount.getAccountUserAccountName());//对公账户名
        }
        summaryMap.put("GuaranteeNo",loanGuarGovAccountNo);//担保机构开户账号
        summaryMap.put("BorrowerStartDate", FormatHelper.formatDate(loan.getLoanInvestStarttime(), "yyyyMMdd"));
        summaryMap.put("BorrowerEndDate", FormatHelper.formatDate(loan.getLoanInvestEndtime(), "yyyyMMdd"));
        summaryMap.put("BorrowerRepayDate", FormatHelper.formatDate(loan.getLoanExpireDate(), "yyyyMMdd"));
        summaryMap.put("ReleaseType", "0");
        String investDateType="";
        String loanPeriodType=loan.getLoanPeriodType().toString();
        if("1".equals(loanPeriodType)){
        	investDateType="3";
        }else if("3".equals(loanPeriodType)){
        	investDateType="1";
        }else{
        	investDateType="2";
        }
        summaryMap.put("InvestDateType", investDateType);
        summaryMap.put("InvestPeriod", loan.getLoanPeriod().toString());
        summaryMap.put("BorrowerDetails", loan.getLoanUse());
        summaryMap.put("TotalNum", String.valueOf(investList.size()));
        summaryMap.put("MerPriv1", String.valueOf(loan.getLoanId()));
        List<Map<String, String>> detailListMap= new ArrayList<Map<String,String>>();
        Map<String, String> detailMap =null;
        for(int i=0,len=investList.size();i<len;i++){
        	detailMap = new LinkedHashMap<String, String>();
        	detailMap.put("ID", String.valueOf(investList.get(i).getId()));
        	detailMap.put("InvestMerNo", ConfigHelper.getConfig("merchantId")+SeqnoHelper.getId(16));
        	detailMap.put("PlaCustId", investList.get(i).getInAccountNo());
        	detailMap.put("TransAmt", FormatHelper.changeY2F(investList.get(i).getValidInvestMoney()));
        	detailMap.put("MerPriv", String.valueOf(investList.get(i).getId()));
        	detailListMap.add(detailMap);
        }
        ftpFileContentMap.put("summaryMap", summaryMap);
        ftpFileContentMap.put("detailListMap", detailListMap);
        String fileData = FormatHelper.formatFtpFileContent(ftpFileContentMap);
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
        		paramsMap.put("biz_type", "FileLoanTransfer");
                paramsMap.put("MerBillNo", merBillNo);
                paramsMap.put("FileName", fileName);
                paramsMap.put("BorrowId", String.valueOf(loan.getLoanId()));
                paramsMap.put("BorrowerAmt", FormatHelper.changeY2F(loan.getLoanMoney()));
                paramsMap.put("BgRetUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("loanDataTransferNotifyUrl"));
                paramsMap.put("MerPriv", "");
                Map<String, String> resultMap = ExchangeDataUtils.submitData(paramsMap);
                if(!"000000".equals(resultMap.get("RespCode"))){
                	returnResult.setSuccess(false);
            		returnResult.setMessage("标的"+String.valueOf(loan.getLoanId())+resultMap.get("RespDesc"));
                }else{
                	LoanDO loando = new LoanDO();
                	loando.setLoanId(loanId);
                	loando.setLoanTransferSqNo(merBillNo);
                	loanDao.update(loando);
                }
        	}else{
        		returnResult.setSuccess(false);
        		returnResult.setMessage("标的"+String.valueOf(loan.getLoanId())+"投资文件上传失败，请重新上传!");
        	}
        }else{
        	returnResult.setSuccess(false);
    		returnResult.setMessage("标的"+String.valueOf(loan.getLoanId())+"投资文件文件写入失败!");
        }
        
        
        
        
        
        return returnResult;
    }
}

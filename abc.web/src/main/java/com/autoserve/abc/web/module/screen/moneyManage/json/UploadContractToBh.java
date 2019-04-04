package com.autoserve.abc.web.module.screen.moneyManage.json;

import java.io.File;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data.SftpTool;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ExchangeDataUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileNameUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.SeqnoHelper;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.fulltransfer.FullTransferService;
import com.autoserve.abc.service.message.mail.SendMailService;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 借款合同手动上传
 *
 * @author luzhi 2018年7月30日 下午5:50:22
 */
public class UploadContractToBh {
	private final static Logger logger = LoggerFactory.getLogger(UploadContractToBh.class);

    @Resource
    private FullTransferService fullTransferService;
    @Resource
    private LoanDao loanDao;
    @Resource
    private LoanService loanService;
    @Resource
    private SendMailService sendMailService;
    
    public JsonBaseVO execute(ParameterParser params, @Param("loanId") int loanId, Context context) {
    	JsonBaseVO jsonBaseVO = new JsonBaseVO();
    	try {
			logger.info("================批量借款发送附件合同================");
			LoanDO loanDo = loanDao.findByLoanId(loanId);
			sendMailService.sendMailToInvestUser(loanId, loanDo.getLoanNo());
		} catch (Exception e) {
			logger.error("================批量借款发送附件合同失败================");
			e.printStackTrace();
			jsonBaseVO.setMessage("批量借款发送附件合同失败");
            jsonBaseVO.setSuccess(false);
            return jsonBaseVO;
		}
		
		try {
			logger.info("================向银行上传放款合同文件================");
			LoanDO loanDo2 = loanDao.findByLoanId(loanId);
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

        			jsonBaseVO.setMessage("借款合同发送成功！");
                    jsonBaseVO.setSuccess(true);
                    return jsonBaseVO;
        		}
        		if(!"000000".equals(resultMap.get("RespCode"))){
        			logger.error("投资合同接口上传调用失败，"+resultMap.get("RespDesc"));
        			jsonBaseVO.setMessage("投资合同接口上传调用失败，"+resultMap.get("RespDesc"));
                    jsonBaseVO.setSuccess(false);
                    return jsonBaseVO;
        		}
        	}else{
        		logger.error("放款合同文件上传失败，请重新上传!");
        		jsonBaseVO.setMessage("放款合同文件上传失败，请重新上传!");
                jsonBaseVO.setSuccess(false);
                return jsonBaseVO;
        	}
	        
		}catch (Exception e) {
			logger.error("================向银行上传放款合同文件失败================");
			e.printStackTrace();
			jsonBaseVO.setMessage("向银行上传放款合同文件失败，请重新上传!");
            jsonBaseVO.setSuccess(false);
            return jsonBaseVO;
		}
		jsonBaseVO.setMessage("借款合同发送成功！");
        jsonBaseVO.setSuccess(true);
        return jsonBaseVO;
       
    }
}

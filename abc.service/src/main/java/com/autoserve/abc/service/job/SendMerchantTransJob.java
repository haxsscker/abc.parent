/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.job;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.autoserve.abc.dao.dataobject.CashRecordDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.dao.dataobject.TocashRecordDO;
import com.autoserve.abc.dao.intf.TocashRecordDao;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.enums.ToCashState;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data.SftpTool;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileNameUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 商户账户交易查询定时任务
 *
 * @author J.YL 2014年12月1日 下午2:08:27
 */
public class SendMerchantTransJob implements BaseJob {
    @Autowired
    private AccountInfoService  accountInfoService;
    @Resource
    private DealRecordService          dealRecordService;
    /**
     * 商户账户交易查询job
     */
    @Override
    public void run() {
        Logger logger = LoggerFactory.getLogger(SendMerchantTransJob.class);
        logger.info("商户账户交易查询任务开始"+new Date());
    	Map<String,String> resultMap = accountInfoService.queryMerchantTrans();
    	String RespCode = resultMap.get("RespCode");
    	String RespDesc = resultMap.get("RespDesc");
    	if("000000".equals(RespCode)){
    		//下载结果文件并解析文件
    		SftpTool ftp = new SftpTool();
    		// 本地路径
            String localPath = ConfigHelper.getSftpLocalPath();
            // 目标路径
            String remotePath = ConfigHelper.getSftpRemoteDownPath()+FormatHelper.formatDate(new Date(), "yyyyMMdd");
    		ftp.connect();
    		try{
    			//结果文件名
    	    	String fileName = resultMap.get("FileName");
    	        boolean isDownloadFileSuccess=ftp.downloadFile(remotePath, fileName, localPath, fileName);
    	        List<String> registeResult = new ArrayList<String>();
    	        if(isDownloadFileSuccess){
    				String fileContent=FileUtils.readByBufferedReader(localPath+fileName);
    				String[] fileStrArr = fileContent.split(FileUtils.LINE_SEPARATOR);
    				String lineStr="";
    				logger.info("=========================解析商户账户交易查询文件=========================");
    				//从结果文件第二行开始遍历
    				for(int i=1,len=fileStrArr.length;i<len;i++){
    					lineStr=fileStrArr[i];
    					registeResult.add(lineStr);
    				}
    			} else {
    				logger.info("=========================下载商户账户交易查询文件失败=========================");				
    			}
    	        if(registeResult.size()>0){
    	        	for(String lineStr:registeResult){
    					String TransId =lineStr.split("\\|")[0];
    					String CreDt =lineStr.split("\\|")[1];
    					String AcTyp =lineStr.split("\\|")[2];
    					String TransAmt =FormatHelper.changeF2Y(lineStr.split("\\|")[3]);
    					//String TransDesc =lineStr.split("\\|")[4];
    					DateFormat format1 = new SimpleDateFormat("yyyyMMdd"); 
    					Date date = format1.parse(CreDt); 
    				    if("820".equals(AcTyp)){//预付费账户
    				        List<DealRecordDO> dealRecords= dealRecordService.queryDealRecordsByInnerSeqNo(TransId);
    						if(dealRecords.size()==0){
        				        DealRecordDO dealRecord = new DealRecordDO();
      				    	    dealRecord.setDrInnerSeqNo(TransId);
        				    	  Random random = new Random();  
        				    	  String id="";  
        				    	  for (int i=0;i<6;i++)  
        				    	  {  
        				    	      id+=random.nextInt(10);  
        				    	  } 
        				    	  dealRecord.setDrId(Integer.parseInt(id));
        				    	  dealRecord.setDrMoneyAmount(new BigDecimal(TransAmt));
        				    	  dealRecord.setDrDetailType(21); //银行手续费
        				    	  dealRecord.setDrType(11);  //银行手续费
        				    	  dealRecord.setDrState(1);//成功
        				    	  dealRecord.setDrOperator(1);
        						  dealRecord.setDrPayAccount("800055100010001");
        						  dealRecord.setDrOperateDate(date);
        				    	  dealRecordService.insertRecord(dealRecord);
    						}
    				    }
    					
    				}
    	        }
    		}catch(Exception e){
    			e.printStackTrace();
    			logger.error(e.getMessage());
    		}
    	}else{
    		logger.error("SendMerchantTransJob:查询失败："+ RespDesc);
    	}
        logger.info("商户账户交易查询任务结束"+new Date());
    }
}

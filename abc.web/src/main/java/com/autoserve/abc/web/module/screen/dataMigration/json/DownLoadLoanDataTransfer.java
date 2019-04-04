package com.autoserve.abc.web.module.screen.dataMigration.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data.SftpTool;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileNameUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;

/**
 * 下载标的迁移结果信息
 * 
 * @author lz 2018年4月17日 下午4:18:33
 */
public class DownLoadLoanDataTransfer {
    private static Logger logger = LoggerFactory.getLogger(DownLoadLoanDataTransfer.class);

    @Resource
    private UserService   userService;
    @Resource
    private InvestDao investDao;
    @Resource
    private LoanDao loanDao;
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult execute(ParameterParser params) {
    	BaseResult result = new BaseResult();
    	//查询待处理的批次号
    	Integer  loanId = params.getInt("loanId");
        LoanDO loan = loanDao.findById(loanId);//查询需要迁移的标的信息
    	if(loan == null){
    		 return result.setError(CommonResultCode.BIZ_ERROR, "未查询到待下载结果文件的流水");
    	}
    	//下载结果文件并解析文件
		SftpTool ftp = new SftpTool();
		// 本地路径
        String localPath = ConfigHelper.getSftpLocalPath();
        // 目标路径
        String remotePath = ConfigHelper.getSftpRemoteDownPath()+FormatHelper.formatDate(new Date(), "yyyyMMdd");
		ftp.connect();
		try{
			//结果文件名
	        String fileName = "RESULT_"+FileNameUtil.getFileName("FileLoanTransfer", "txt",loan.getLoanTransferSqNo());
	        boolean isDownloadFileSuccess=ftp.downloadFile(remotePath, fileName, localPath, fileName);
	        List<String> registeResult = new ArrayList<String>();
	        if(isDownloadFileSuccess){
				String fileContent=FileUtils.readByBufferedReader(localPath+fileName);
				String[] fileStrArr = fileContent.split(FileUtils.LINE_SEPARATOR);
				String lineStr="";
				logger.info("=========================解析存量标的迁移结果文件=========================");
				//从结果文件第二行开始遍历
				for(int i=1,len=fileStrArr.length;i<len;i++){
					lineStr=fileStrArr[i];
					registeResult.add(lineStr);
				}
			} else {
				logger.info("=========================下载存量标的迁移文件失败=========================");				
			}
	        if(registeResult.size()>0){
	        	for(String lineStr:registeResult){
	        		try{
						String TransId =lineStr.split("\\|")[5];
						String inId =lineStr.split("\\|")[0];					
						if(null!=TransId&&!"".equals(TransId)){
							//修改投资信息
							InvestDO investDo = new InvestDO();
							investDo.setInId(Integer.valueOf(inId));
							investDo.setIoOutSeqNo(TransId);
							investDao.update(investDo);
							logger.info("===========修改投资人信息成功;  Id="+inId);	
						}
	        		}catch(Exception e){
						e.printStackTrace();
						logger.error(e.getMessage());
						continue;
					}
					
				}
	        }
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		ftp.disconnect();
        result.setMessage("存量标的迁移申请成功!");
        return result;
    }
}

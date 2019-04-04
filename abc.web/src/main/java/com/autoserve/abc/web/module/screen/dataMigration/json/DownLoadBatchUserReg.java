package com.autoserve.abc.web.module.screen.dataMigration.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.AccountInfoDao;
import com.autoserve.abc.dao.intf.UserDao;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data.SftpTool;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileNameUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;

/**
 * 下载批量用户注册信息
 * 
 * @author ipeng 2014年12月20日 下午1:18:33
 */
public class DownLoadBatchUserReg {
    private static Logger logger = LoggerFactory.getLogger(DownLoadBatchUserReg.class);

    @Resource
    private UserService   userService;
    @Resource
	private UserDao userDao;
    @Resource
    private AccountInfoDao accountInfoDao;
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult execute(ParameterParser params) {
    	BaseResult result = new BaseResult();
    	//查询待处理的批次号
    	List<String> hdBatchno =  accountInfoDao.queryHandlingBatchno();
    	if(hdBatchno == null || hdBatchno.size() <= 0){
    		 return result.setError(CommonResultCode.BIZ_ERROR, "未查询到待下载结果文件的批次");
    	}
    	//下载结果文件并解析文件
		SftpTool ftp = new SftpTool();
		// 本地路径
        String localPath = ConfigHelper.getSftpLocalPath();
        // 目标路径
        String remotePath = ConfigHelper.getSftpRemoteDownPath()+FormatHelper.formatDate(new Date(), "yyyyMMdd");
		ftp.connect();
		for(String batchNo : hdBatchno){
			try{
				//结果文件名
		        String fileName = "RESULT_"+FileNameUtil.getFileName("ExistUserRegister", "txt",batchNo);
		        boolean isDownloadFileSuccess=ftp.downloadFile(remotePath, fileName, localPath, fileName);
		        List<String> registeResult = new ArrayList<String>();
		        if(isDownloadFileSuccess){
					String fileContent=FileUtils.readByBufferedReader(localPath+fileName);
					String[] fileStrArr = fileContent.split(FileUtils.LINE_SEPARATOR);
					String lineStr="";
					logger.info("=========================解析批量存量用户注册结果文件=========================");
					//从结果文件第二行开始遍历
					for(int i=1,len=fileStrArr.length;i<len;i++){
						lineStr=fileStrArr[i];
						registeResult.add(lineStr);
					}
				} else {
					logger.info("=========================下载批量存量用户注册文件失败=========================");
					continue;
				}
		        if(registeResult.size()>0){
		        	StringBuffer registeErrorResult = new StringBuffer();
		        	String registeErrorResultFilePath =localPath+ "RESULT_ERROR"+FileNameUtil.getFileName("ExistUserRegister", "txt",batchNo);
		        	for(String lineStr:registeResult){
		        		try{
		        			String MobileNo = lineStr.split("\\|")[1];
							String respCode=lineStr.split("\\|")[2];
							String PlaCustId=lineStr.split("\\|")[3];
							String RespDesc=lineStr.split("\\|")[4];
							logger.info("MobileNo:{},respCode:{},PlaCustId:{},RespDesc:{},",MobileNo,respCode,PlaCustId,RespDesc);
							UserDO user=userDao.findByPhone(MobileNo);
							//修改账户信息
							AccountInfoDO account = new AccountInfoDO();
							account.setAccountModifyBatchno(batchNo);
							if(null != user){
								account.setAccountUserId(user.getUserId());
							}
//							account.setAccountUserPhone(MobileNo);
							if("000000".equals(respCode)){
								account.setAccountKind(AccountInfoDO.KIND_BOHAI);
								account.setAccountNo(PlaCustId);
							}else{
								registeErrorResult.append(MobileNo);
								registeErrorResult.append("====================");
								registeErrorResult.append(respCode);
								registeErrorResult.append("====================");
								registeErrorResult.append(RespDesc);
								registeErrorResult.append(FileUtils.LINE_SEPARATOR);
								account.setAccountKind(AccountInfoDO.KIND_DM);
							}
							accountInfoDao.updateByModifyBatchno(account);
		        		}catch(Exception e){
		        			e.printStackTrace();
		    				logger.error(e.getMessage());
		    				continue;
		        		}
					}
		        	if(StringUtils.isNotEmpty(registeErrorResult.toString())){
		        		logger.info("写入批量存量用户注册失败文件========================="+registeErrorResultFilePath);
		        		FileUtils.writeByBufferedWriter(registeErrorResult.toString(), registeErrorResultFilePath);
		        	}
		        }
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage());
				continue;
			}
		}
		ftp.disconnect();
        result.setMessage("批量注册申请成功，请等待处理结果！");
        return result;
    }
}

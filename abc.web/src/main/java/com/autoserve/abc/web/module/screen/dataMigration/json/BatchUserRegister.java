package com.autoserve.abc.web.module.screen.dataMigration.json;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.UserRecommendDO;
import com.autoserve.abc.dao.intf.AccountInfoDao;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data.SftpTool;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ExchangeDataUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileNameUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.SeqnoHelper;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 类发送渤海银行批量注册.java的实现描述：TODO 类实现描述
 * 
 * @author ipeng 2014年12月20日 下午1:18:33
 */
public class BatchUserRegister {
    private static Logger logger = LoggerFactory.getLogger(BatchUserRegister.class);

    @Resource
    private UserService   userService;
    @Resource
    private AccountInfoDao accountInfoDao;
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult execute(ParameterParser params) {
    	BaseResult result = new BaseResult();
    	logger.info("查询个人客户");
        UserDO userDO = new UserDO();
        String searchForm = params.getString("searchForm");
        if (StringUtils.isNotBlank(searchForm)) {
            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));
                    // 客户名称
                    if ("userName".equals(field)) {
                    	userDO.setUserName(value);
                    }
                    // 真实姓名
                    else if ("userRealName".equals(field)) {
                    	userDO.setUserRealName(value);
                    }
                    // 用户手机
                    else if ("userPhone".equals(field)) {
                    	userDO.setUserPhone(value);
                    }
                    // 账户体系类别
                    else if ("accountKind".equals(field)) {
                    	userDO.setAccountKind(value);
                    }
                    // 注册开始时间
                    else if ("startRegisterDate".equals(field)) {
                    	userDO.setStartRegisterDate(new SimpleDateFormat("yyyy-MM-dd").parse(value));
                    }// 注册结束时间
                    else if ("endRegisterDate".equals(field)) {
                    	userDO.setEndRegisterDate(new SimpleDateFormat("yyyy-MM-dd").parse(value));
                    }
                }
            } catch (Exception e) {
                logger.error("客户信息－个人客户－搜索查询 查询参数解析出错", e);
            }
        }
        //默认查询个人客户
        userDO.setUserType(UserType.PERSONAL.getType());
       List<UserDO> userList = userService.queryToBatchUserRegister(userDO);
        if(userList == null || userList.size()<=0){
        	 return result.setError(CommonResultCode.BIZ_ERROR, "未查询到待批量注册的用户");
        }
        
        //组织发送报文
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("MerBillNo", ConfigHelper.getConfig("merchantId")+SeqnoHelper.getId(16));
        paramsMap.put("BatchNo", SeqnoHelper.getId(10));
        Map<String, Object> ftpFileContentMap = getBatchRegisterFileData(userList,paramsMap);
        paramsMap.put("fileData", FormatHelper.formatFtpFileContent(ftpFileContentMap));
        Map<String, String> resultMap = this.doBatchRegister(paramsMap);
        if(!"000000".equals(resultMap.get("RespCode"))){
        	String errMsg = StringUtils.isNotEmpty(resultMap.get("RespDesc"))?resultMap.get("RespDesc"):"银行接口调用异常";
        	logger.error(errMsg);
        	return result.setError(CommonResultCode.BIZ_ERROR, "发送请求失败，银行返回"+errMsg);
        }else{
        	//修改数据状态，account_kind ——> HANDLING  account_modify_batchno ——>BatchNo  account_no_bak
        	 for(UserDO ud : userList){
        		 AccountInfoDO account = new AccountInfoDO();
        		 account.setAccountKind(AccountInfoDO.KIND_HANDLING);
        		 account.setAccountModifyBatchno(paramsMap.get("MerBillNo"));
        		 account.setAccountNoBak(ud.getAccountNo());
        		 account.setAccountUserId(ud.getUserId());
        		 accountInfoDao.updateAfterBatchReg(account);
        	 }
        	 result.setMessage("批量注册申请成功，请等待处理结果！");
        }
        return result;
    }
    
    /**
	 * @content:渤海银行接口，构造批量注册用户文件数据fileData
	 * @author:夏同同
	 * @date:2016年4月10日 上午11:01:23
	 */
    private Map<String,Object> getBatchRegisterFileData(List<UserDO> UserList,Map<String, String> paramsMap){
    	if(UserList == null || UserList.size() <= 0){
    		return null;
    	}
    	/** ftp文件数据*/
    	Map<String, Object> ftpFileContentMap = new HashMap<String, Object>();
    	/** 汇总数据*/
    	Map<String, String> summaryMap=new LinkedHashMap<String, String>();
    	summaryMap.put("char_set", ConfigHelper.getConfig("charset"));
        summaryMap.put("partner_id", ConfigHelper.getConfig("merchantId"));
        summaryMap.put("BatchNo", paramsMap.get("BatchNo"));
        summaryMap.put("TransDate", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        summaryMap.put("TotalNum", String.valueOf(UserList.size()));
        
    	/** 明细数据*/
    	List<Map<String, String>> detailListMap= new ArrayList<Map<String,String>>();
        for(UserDO ud : UserList){
        	Map<String, String> detlMap=new LinkedHashMap<String, String>();
        	detlMap.put("IdentType", "00");
        	detlMap.put("IdentNo", ud.getUserDocNo());
        	detlMap.put("UsrName", ud.getUserRealName());
        	detlMap.put("MobileNo", ud.getUserPhone());
        	detlMap.put("OpenBankId", ud.getBankCode());
        	detlMap.put("OpenAcctId", ud.getBankNo());
        	detailListMap.add(detlMap);
        }
    	ftpFileContentMap.put("summaryMap", summaryMap);
        ftpFileContentMap.put("detailListMap", detailListMap);
    	return ftpFileContentMap;
    }
    
    /**
     * 渤海银行接口，批量注册用户(调用接口)
     * 1、ftp上传放款文件明细
     */
    private Map<String, String> doBatchRegister(Map<String, String> map) {
    	Map<String, String> resultMap = new HashMap<String, String>();
    	SftpTool ftp = new SftpTool();
    	// 本地路径
        String localPath = ConfigHelper.getSftpLocalPath();
        // 目标路径
        String remotePath = ConfigHelper.getSftpRemotePath()+FormatHelper.formatDate(new Date(), "yyyyMMdd");
        //上传文件名
        String merBillNo = map.get("MerBillNo");
        String fileName = FileNameUtil.getFileName("ExistUserRegister", "txt",merBillNo);
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
        		paramsMap.put("biz_type", "ExistUserRegister");
        		paramsMap.put("MerBillNo", merBillNo);
        		paramsMap.put("BatchNo", map.get("BatchNo"));
        		paramsMap.put("BgRetUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("batchRegisterNotifyUrl"));
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

package com.autoserve.abc.web.module.screen.bhyhNotify;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.intf.AccountInfoDao;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 
    * @ClassName: BatchUserRegisterNotify  
    * @Description: 存量用户注册回调接口
    * @author xiatt  
    * @date 2018年4月15日  
    *
 */
public class BatchUserRegisterNotify {
	private final static Logger logger = LoggerFactory.getLogger(BatchUserRegisterNotify.class);
	@Resource
	private HttpServletResponse resp;
	@Resource
	private HttpServletRequest resq;
    @Resource
    private AccountInfoDao accountInfoDao;
    /**
     * 1.下载结果文件并解析文件
     * 2.存储用户渤海银行开户信息
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public void execute(Context context, Navigator nav, ParameterParser params) {
		logger.info("===================批量存量用户注册异步通知===================");
		String partner_id = FormatHelper.GBKDecodeStr(params.getString("partner_id"));// 
		String version_no = FormatHelper.GBKDecodeStr(params.getString("version_no"));//
		String biz_type = FormatHelper.GBKDecodeStr(params.getString("biz_type"));// 
		String sign_type = FormatHelper.GBKDecodeStr(params.getString("sign_type"));// 
		String BatchNo = FormatHelper.GBKDecodeStr(params.getString("BatchNo"));// 
		String MerBillNo = FormatHelper.GBKDecodeStr(params.getString("MerBillNo"));// 
		String RespCode = FormatHelper.GBKDecodeStr(params.getString("RespCode"));// 
		String RespDesc = FormatHelper.GBKDecodeStr(params.getString("RespDesc"));// 
		String mac = params.getString("mac");
		logger.info("=================================银行返回报文=================================");
		logger.info("partner_id:{},version_no:{},biz_type:{},sign_type:{},BatchNo:{},MerBillNo:{},RespCode:{},RespDesc:{},mac:{}",
				partner_id,version_no,biz_type,sign_type,BatchNo,MerBillNo,RespCode,RespDesc,mac);
		try {
			if("000000".equals(RespCode)){
				
				logger.info("============批量存量用户注册受理成功===========");
				resp.getWriter().print("SUCCESS");
			}else{
				logger.info("批量存量用户注册受理失败====="+RespDesc);
				//将该批的存量用户账户体系类别值还原为''
				AccountInfoDO account = new AccountInfoDO();
				account.setAccountModifyBatchno(MerBillNo);
				account.setAccountKind(AccountInfoDO.KIND_DM);
				accountInfoDao.updateByModifyBatchno(account);
		        resp.getWriter().print("SUCCESS");
			}
		} catch (Exception e) {
			logger.error("[批量存量用户注册] error: ", e.getMessage());
			throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),e.getMessage());
		}
	}
}

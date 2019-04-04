package com.autoserve.abc.web.module.screen.bhyhNotify;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.RedUseDO;
import com.autoserve.abc.dao.intf.DealRecordDao;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.dao.intf.InvestOrderDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.RedUseDao;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanTraceRecord;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoanTraceOperation;
import com.autoserve.abc.service.biz.enums.RsState;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data.SftpTool;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileNameUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.manage.LoanManageService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedsendService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.web.helper.LoginUserUtil;

public class batInvestCancleNotify {
	private final static Logger logger = LoggerFactory.getLogger(batInvestCancleNotify.class);
	@Resource
	private HttpServletResponse resp;
	@Resource
	private HttpServletRequest resq;
	@Resource
    private LoanDao                   loanDao;
	@Resource
    private LoanService           loanService;
	@Resource
    private UserService               userService;
    @Resource
    private DealRecordDao             dealRecordDao;
    @Resource
    private InvestDao            investDao;
    @Resource
    private InvestOrderDao investOrderDao;
    @Resource
    private RedUseDao               redUseDao;
    @Resource
    private RedsendService          redsendService;
    @Resource
    private LoanManageService loanManageService;
    /**
     * 1.下载结果文件并解析文件
     * 2.调用流标接口
     * 3.修改用户红包状态为未使用
     * 4.修改投资活动记录状态
     * 5.修改投资订单记录状态
     * 6.修改投资解冻记录状态
     * 7.修改普通标状态并添加项目跟踪状态记录
     */
	public void execute(Context context, Navigator nav, ParameterParser params) {
		logger.info("===================批量投标撤销异步通知===================");
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
    		LoanDO loanDO = loanDao.findBySeqNo(MerBillNo);
			if("000000".equals(RespCode)){
				logger.info("============批量投标撤销受理成功===========");
				resp.getWriter().print("SUCCESS");
			}else{
				logger.info("批量投标撤销受理失败====="+RespDesc);
				// 修改投资解冻交易记录状态
				DealRecordDO updateDealRecord = new DealRecordDO();
				updateDealRecord.setDrInnerSeqNo(MerBillNo);//与撤销申请流水号一致
				updateDealRecord.setDrState(DealState.FAILURE.getState());
				int flag1 = dealRecordDao.updateDealRecordState(updateDealRecord);
				if (flag1 <= 0) {
					throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),"修改投资解冻交易记录状态出错");
				}
				// 更新标的状态并添加项目跟踪状态记录
				Loan toModify = new Loan();
				toModify.setLoanId(loanDO.getLoanId());
				toModify.setLoanState(LoanState.BID_INVITING);
				
				LoanTraceRecord traceRecord = new LoanTraceRecord();
				traceRecord.setCreator(LoginUserUtil.getEmpId());
				traceRecord.setLoanId(toModify.getLoanId());
				traceRecord.setLoanTraceOperation(LoanTraceOperation.cancelLoan);
				traceRecord.setOldLoanState(LoanState.BID_CANCELING);
				traceRecord.setNewLoanState(LoanState.BID_INVITING);
				traceRecord.setNote("普通标项目流标失败");
				
				BaseResult modResult = loanService.modifyLoanInfo(toModify, traceRecord);
				if (!modResult.isSuccess()) {
					throw new BusinessException("普通标状态修改失败");
				}
		        resp.getWriter().print("SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[流标] error: ", e.getMessage());
		}
	}
}

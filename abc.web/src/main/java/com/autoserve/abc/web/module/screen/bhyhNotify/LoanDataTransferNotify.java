package com.autoserve.abc.web.module.screen.bhyhNotify;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.intf.DealRecordDao;
import com.autoserve.abc.dao.intf.FullTransferRecordDao;
import com.autoserve.abc.dao.intf.InvestOrderDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.TransferLoanDao;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.invest.InvestOrderService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;

public class LoanDataTransferNotify {
	private final static Logger logger = LoggerFactory.getLogger(LoanDataTransferNotify.class);
	@Resource
	private HttpServletResponse resp;
	@Resource
	private HttpServletRequest resq;
	@Resource
    private LoanDao                   loanDao;
	@Resource
    private LoanService           loanService;
	@Resource
    private InvestQueryService        investQueryService;
	@Resource
    private UserService               userService;
	@Resource
    private DealRecordService         dealRecordService;
    @Resource
    private AccountInfoService        accountInfoService;
    @Resource
    private DealRecordDao             dealRecordDao;
    @Resource
    private InvestOrderService        investOrderService;
    @Resource
    private IncomePlanService         incomePlanService;
    @Resource
    private PaymentPlanService        paymentPlanService;
    @Resource
    private FullTransferRecordDao     fullTransferRecordDao;
    @Resource
    private SysConfigService sysConfigService;
    @Resource
    private TransferLoanDao       transferLoanDao;
    @Resource
    private TransferLoanService   transferLoanService;
    @Resource
    private DoubleDryService doubleDryService;
    @Resource
    private InvestOrderDao investOrderDao;
    
    /**
     * 存量标的迁移
     * @param context
     * @param nav
     * @param params
     */
	public void execute(Context context, Navigator nav, ParameterParser params) {
		logger.info("===================存量标的迁移异步通知===================");
		String partner_id = FormatHelper.GBKDecodeStr(params.getString("partner_id"));// 
		String version_no = FormatHelper.GBKDecodeStr(params.getString("version_no"));//
		String biz_type = FormatHelper.GBKDecodeStr(params.getString("biz_type"));// 
		String sign_type = FormatHelper.GBKDecodeStr(params.getString("sign_type"));// 
		String MerBillNo = FormatHelper.GBKDecodeStr(params.getString("MerBillNo"));// 
		String RespCode = FormatHelper.GBKDecodeStr(params.getString("RespCode"));// 
		String RespDesc = FormatHelper.GBKDecodeStr(params.getString("RespDesc"));// 
		String mac = params.getString("mac");
		logger.info("=================================银行返回报文=================================");
		logger.info("partner_id:{},version_no:{},biz_type:{},sign_type:{},MerBillNo:{},RespCode:{},RespDesc:{},mac:{}",
				partner_id,version_no,biz_type,sign_type,MerBillNo,RespCode,RespDesc,mac);
		try {
			if("000000".equals(RespCode)){
				logger.info("============迁移成功===========");
			    resp.getWriter().print("SUCCESS");
			}else{
				logger.info("迁移失败====="+RespDesc);
			}
		} catch (Exception e) {
			logger.error("[迁移] error: ", e.getMessage());
		}
	}
}

package com.autoserve.abc.web.module.screen.loanpay.json;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.AheadRepay;
import com.autoserve.abc.dao.dataobject.enums.AheadRepayState;
import com.autoserve.abc.dao.intf.AheadRepayDao;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.PayType;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.loan.repay.RepayService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.google.common.collect.Lists;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 类RepaymentConfirmPaymentData.java的实现描述：TODO 类实现描述
 *
 * @author liuwei 2015年1月10日 下午11:43:32
 */
public class RepaymentConfirmPaymentData {
    @Resource
    private RepayService        repayService;
    @Resource
    private HttpServletRequest  request;
    @Resource
    private ImageCaptchaService imageCaptchaService;
    @Resource
    private SysConfigService    sysConfigService;
    @Resource
    private PaymentPlanService  paymentPlanService;
    @Autowired
	private AheadRepayDao aheadRepayDao;
    public JsonBaseVO execute(Context context, ParameterParser params, @Param("type") String type) {

        String sessionId = request.getSession().getId();

        Boolean isResponseCorrect = false;

        BaseResult result = new BaseResult();

        int loanId = params.getInt("loanId");

        int planId = params.getInt("planId");

        PayType payType = null;
        if (type.equals("Normal")) {
            payType = PayType.COMMON_CLEAR;
        }
        if (type.equals("Replace")) {
            payType = PayType.PLA_CLEAR;
        }
        if (type.equals("Compel")) {
            payType = PayType.FORCE_CLEAR;
        }
        if(type.equals("GuarReplace")){
        	payType = PayType.GUAR_CLEAR;
        }
        
        if (payType == null) {
            result.setMessage("还款类型异常！");
            result.setSuccess(false);
        }
        
        if (payType == PayType.PLA_CLEAR || payType == PayType.GUAR_CLEAR) {

            PlainResult<SysConfig> sysResult = this.sysConfigService
                    .querySysConfig(SysConfigEntry.CONTINUE_DESIGNATED_PAY);

            	PlainResult<PaymentPlan> paymentResult= paymentPlanService.queryNextPaymentPlan(loanId);
            	
            	Date paytime = null;
				try {
					paytime = DateUtil.parseDate(DateUtil.formatDate(paymentResult.getData().getPaytime(), DateUtil.DEFAULT_DAY_STYLE)+" 00:00:00",DateUtil.DEFAULT_FORMAT_STYLE);
				} catch (ParseException e) {
		            throw new BusinessException("系统错误，请稍后重试！");
				}
            	if(DateUtil.diff(new Date(),paytime,TimeUnit.SECONDS)>0){
            		result.setMessage("请于固定还款日当日或之后进行代还操作！");
                    result.setSuccess(false);
                    return ResultMapper.toBaseVO(result);
            	}
            	

        }

        String securityCode = params.getString("securityCode");

        isResponseCorrect = imageCaptchaService.validateResponseForID(sessionId, securityCode);

        if (isResponseCorrect) {
        	AheadRepay t = new AheadRepay();
    		t.setLoanId(loanId);
    		List<AheadRepayState> states = Lists.newArrayList(
    				AheadRepayState.WAIT_AUDIT, AheadRepayState.AUDIT_PASS);
    		t.setSearchState(states);
    		t = aheadRepayDao.findOne(t);
    		if (t != null) {
    			result.setSuccess(false);
    			result.setMessage("已存在提前还款申请，不可重复还款");
    			return ResultMapper.toBaseVO(result);
    		}
            result = this.repayService.repay(loanId, planId, payType, LoginUserUtil.getEmpId());
        } else {
            result.setMessage("验证码错误");
            result.setSuccess(false);
        }

        return ResultMapper.toBaseVO(result);

    }
}

package com.autoserve.abc.web.module.screen.moneyManage.json;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.enums.FullTransferType;
import com.autoserve.abc.service.biz.intf.loan.fulltransfer.FullTransferService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 资金管理-满标划转
 *
 * @author liuwei 2014年12月30日 下午6:23:07
 */
public class MoneyTransferAddData {
    @Resource
    private FullTransferService fullTransferService;
    @Resource
    private HttpSession         httpSession;

    public JsonBaseVO execute(@Param("loanId") int loanId, @Param("periods") int periods,
                              @Param("checkCode") String checkCode, @Param("lenCollectFee") double lenCollectFee, @Param("actualGuarFee") BigDecimal actualGuarFee) {

//        String code = (String) httpSession.getAttribute("VCode");
//        Date date = (Date) httpSession.getAttribute("VDate");
//        Integer id =LoginUserUtil.getEmpId();
        
//        DateTime dt1 = new DateTime(date);
//        DateTime dt2 = DateTime.now();

     /*   JsonBaseVO vo = new JsonBaseVO();
        if (Minutes.minutesBetween(dt1, dt2).getMinutes() > 30) {
            vo.setSuccess(false);
            vo.setMessage("验证码超时!");
            return vo;
        }
        if (!checkCode.equals(code)) {
            vo.setSuccess(false);
            vo.setMessage("验证码错误!");
            return vo;
        }*/
    	BaseResult result = new BaseResult();
        try {
			result = this.fullTransferService.commonBidMoneyTransfer(loanId, lenCollectFee, actualGuarFee, periods,
			        FullTransferType.COMMON_LOAN_FULL_TRANSFER, LoginUserUtil.getEmpId());
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, e.getMessage());
		}
        return ResultMapper.toBaseVO(result);
    }
}

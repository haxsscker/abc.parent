package com.autoserve.abc.web.convert;

import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.vo.plan.PaymentPlanVO;

/**
 * 类PaymentPlanVO.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月19日 下午1:15:37
 */
public class PaymentPlanVOConverter {

    public static PaymentPlanVO toPaymentPlanVO(PaymentPlan paymentPlan) {
        if (paymentPlan == null) {
            return null;
        }

        PaymentPlanVO vo = new PaymentPlanVO();
        vo.setDeInterest(paymentPlan.getPayFine());
        //add by 夏同同  新增违约罚金 20160412
        vo.setDeBreachInterest(paymentPlan.getPayBreachFine());
        if (paymentPlan.getCollecttime() != null) {
            vo.setPro_collect_date(DateUtil.formatDate(paymentPlan.getCollecttime(), DateUtil.DEFAULT_DAY_STYLE));
        }
        vo.setPro_collect_money(paymentPlan.getPayCollectCapital());
        vo.setPro_collect_over_rate(paymentPlan.getPayCollectFine());
        //add by 夏同同  新增已还违约罚金 20160412
        vo.setPro_collect_breach_rate(paymentPlan.getPayCollectBreachFine());
        vo.setPro_collect_rate(paymentPlan.getPayCollectInterest());
        if (paymentPlan.getIsClear() != null)
            vo.setPro_is_clear(paymentPlan.getIsClear().booleanValue());
        if (paymentPlan.getPaytime() != null)
            vo.setPro_pay_date(DateUtil.formatDate(paymentPlan.getPaytime(), DateUtil.DEFAULT_DAY_STYLE));
        vo.setPro_pay_money(paymentPlan.getPayCapital());
        vo.setPro_pay_rate(paymentPlan.getPayInterest());
        if (paymentPlan.getPayState() != null)
            vo.setPro_pay_type(paymentPlan.getPayState().getPrompt());
        vo.setPro_pay_total(paymentPlan.getPayTotalMoney());
        vo.setPro_pay_serve_fee(paymentPlan.getPayServiceFee());
        vo.setPro_collect_serve_fee(paymentPlan.getCollectServiceFee());
        return vo;
    }

    public static List<PaymentPlanVO> toPaymentPlanVOList(List<PaymentPlan> paymentPlanList) {
        if (paymentPlanList == null) {
            return null;
        }

        List<PaymentPlanVO> result = new ArrayList<PaymentPlanVO>(paymentPlanList.size());
        for (PaymentPlan paymentPlan : paymentPlanList) {
            result.add(toPaymentPlanVO(paymentPlan));
        }
        return result;
    }

}

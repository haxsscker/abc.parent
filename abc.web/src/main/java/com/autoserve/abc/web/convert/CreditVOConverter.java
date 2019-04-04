package com.autoserve.abc.web.convert;

import com.autoserve.abc.dao.dataobject.CreditJDO;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.credit.CreditApplyVO;
import net.sf.cglib.beans.BeanCopier;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RJQ 2015/1/6 17:20.
 */
public class CreditVOConverter {
    public static CreditApplyVO convertToVO(CreditJDO creditJDO) {
        CreditApplyVO vo = new CreditApplyVO();
        BeanCopier beanCopier = BeanCopier.create(CreditJDO.class, CreditApplyVO.class, false);
        beanCopier.copy(creditJDO, vo, null);
        vo.setCre_apply_id(creditJDO.getCreditApplyId());
        vo.setCre_user_id(creditJDO.getCreditUserId());
        vo.setCst_loan_credit(creditJDO.getCreditOldAmount());
        vo.setCre_apply_money(creditJDO.getCreditApplyAmount());
        vo.setCre_check_money(creditJDO.getCreditReviewAmount());
        vo.setCre_apply_date(new DateTime(creditJDO.getCreditApplyDate()).toString(DateUtil.DATE_FORMAT));
        vo.setCre_check_state(ReviewState.valueOf(creditJDO.getCreditReviewState()).getDes());
        vo.setCre_check_idear(creditJDO.getCreditApplyNote());
        vo.setCst_user_name(creditJDO.getUserName());
        vo.setCst_real_name(creditJDO.getUserRealName());
        vo.setCst_user_score(creditJDO.getUserScore());

        return vo;
    }

    public static List<CreditApplyVO> convertToVOList(List<CreditJDO> creditJDOs) {
        List<CreditApplyVO> result = new ArrayList<CreditApplyVO>();
        if (creditJDOs == null || creditJDOs.isEmpty()) {
            return result;
        }
        for (CreditJDO creditJDO : creditJDOs) {
            result.add(convertToVO(creditJDO));
        }
        return result;
    }
}

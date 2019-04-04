package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.CreditApplyDO;
import com.autoserve.abc.dao.dataobject.CreditJDO;
import com.autoserve.abc.service.biz.entity.CreditApply;
import com.autoserve.abc.service.biz.enums.CreditType;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.exception.BusinessException;
import net.sf.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RJQ 2015/1/6 16:34.
 */
public class CreditApplyConverter {
    public static CreditApply convertToEntity(CreditJDO creditJDO) {
        CreditApply creditApply = new CreditApply();
        BeanCopier beanCopier = BeanCopier.create(CreditApplyDO.class, CreditApply.class, false);
        beanCopier.copy(creditJDO, creditApply, null);
        creditApply.setCreditReviewState(creditJDO.getCreditReviewState() == null ? null : ReviewState.valueOf(creditJDO.getCreditReviewState()));
        creditApply.setCreditType(creditJDO.getCreditType() == null ? null : CreditType.valueOf(creditJDO.getCreditType()));

        return creditApply;
    }

    public static CreditApplyDO convertToDO(CreditApply creditApply) {
        CreditApplyDO creditApplyDO = new CreditApplyDO();
        BeanCopier beanCopier = BeanCopier.create(CreditApply.class, CreditApplyDO.class, false);
        beanCopier.copy(creditApply, creditApplyDO, null);
        creditApplyDO.setCreditReviewState(creditApply.getCreditReviewState() == null ? null : creditApply.getCreditReviewState().getState());
        creditApplyDO.setCreditType(creditApply.getCreditType() == null ? null : creditApply.getCreditType().getType());

        return creditApplyDO;
    }

    public static List<CreditApply> convertList(List<CreditJDO> list) {
        if (list == null || list.isEmpty())
            throw new BusinessException("传入的list为空");
        List<CreditApply> result = new ArrayList<CreditApply>();
        for (CreditJDO creditJDO : list) {
            result.add(convertToEntity(creditJDO));
        }
        return result;
    }
}

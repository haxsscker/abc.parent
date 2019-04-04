package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.UserFinanceDO;
import com.autoserve.abc.service.biz.entity.UserFinance;
import com.autoserve.abc.service.biz.enums.FinanceOwnState;
import net.sf.cglib.beans.BeanCopier;

/**
 * @author RJQ 2014/12/13 13:53.
 */
public class UserFinanceConverter {
    public static UserFinance toUserHouse(UserFinanceDO userFinanceDO) {
        UserFinance userFinance = new UserFinance();
        BeanCopier beanCopier = BeanCopier.create(UserFinanceDO.class, UserFinance.class, false);
        beanCopier.copy(userFinanceDO, userFinance, null);
        userFinance.setUfOwnCar(FinanceOwnState.valueOf(userFinanceDO.getUfOwnCar()));
        userFinance.setUfOwnHouse(FinanceOwnState.valueOf(userFinanceDO.getUfOwnHouse()));

        return userFinance;
    }
}

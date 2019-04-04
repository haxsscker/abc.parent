package com.autoserve.abc.service.biz.convert;

 

import net.sf.cglib.beans.BeanCopier;

import com.autoserve.abc.dao.dataobject.CashSqDO;
 
import com.autoserve.abc.service.biz.entity.CashSq;

 
 

/**
 * @author RJQ 2014/12/3 14:06.
 */
public class CashSqConverter {
	
  
    public static CashSq toCashSq(CashSqDO cashSqDO) {
    	CashSq cashSq = new CashSq();
        BeanCopier beanCopier = BeanCopier.create(CashSqDO.class, CashSq.class, false);
        beanCopier.copy(cashSqDO, cashSq, null);
         
    
        return cashSq;
    }

 

    public static CashSqDO toCashSqDO(CashSq cashSq) {
    	CashSqDO cashSqDO = new CashSqDO();
        BeanCopier beanCopier = BeanCopier.create(CashSq.class, CashSqDO.class, false);
        beanCopier.copy(cashSq, cashSqDO, null);
         
        return cashSqDO;
    }
}

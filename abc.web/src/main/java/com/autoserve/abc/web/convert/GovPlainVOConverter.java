package com.autoserve.abc.web.convert;

import com.autoserve.abc.dao.dataobject.GovPlainJDO;
import com.autoserve.abc.service.biz.enums.EnterpriseScale;
import com.autoserve.abc.service.biz.enums.EntityState;
import com.autoserve.abc.service.biz.enums.IsOfferGuar;
import com.autoserve.abc.web.vo.government.GovPlainVO;
import net.sf.cglib.beans.BeanCopier;

/**
 * @author RJQ 2014/12/16 15:51.
 */
public class GovPlainVOConverter {

    public static GovPlainVO convertToVO(GovPlainJDO govPlainJDO) {
        GovPlainVO vo = new GovPlainVO();
        BeanCopier beanCopier = BeanCopier.create(GovPlainJDO.class, GovPlainVO.class, false);
        beanCopier.copy(govPlainJDO, vo, null);
        vo.setGovIsEnable(EntityState.valueOf(govPlainJDO.getGovIsEnable()));
        vo.setGovIsOfferGuar(IsOfferGuar.valueOf(govPlainJDO.getGovIsOfferGuar()));
        vo.setGovScale(EnterpriseScale.valueOf(govPlainJDO.getGovScale()));

        return vo;
    }
}

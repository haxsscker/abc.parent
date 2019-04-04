package com.autoserve.abc.web.module.screen.credit;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.CreditJDO;
import com.autoserve.abc.service.biz.intf.credit.CreditService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.CreditVOConverter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author RJQ 2015/1/6 18:03.
 */
public class CreditCheckView {
    @Autowired
    private CreditService creditService;

    public void execute(Context context, ParameterParser params) {
        Integer creditId = params.getInt("creditId");
        if (creditId != 0) {
            PlainResult<CreditJDO> creditInfo = creditService.findFullCreditInfoById(creditId);
            if(creditInfo.getData() != null){
                context.put("credit", CreditVOConverter.convertToVO(creditInfo.getData()));
            }
        }

    }
}

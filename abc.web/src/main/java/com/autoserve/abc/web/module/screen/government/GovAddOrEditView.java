package com.autoserve.abc.web.module.screen.government;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.GovPlainJDO;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.GovPlainVOConverter;
import com.autoserve.abc.web.vo.government.GovPlainVO;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/2 16:38.
 */
public class GovAddOrEditView {
    @Resource
    private GovernmentService governmentService;

    public void execute(Context context, ParameterParser params) {
        String action = params.getString("modelAction");
        if (action != null && action.equals("Edit")) {
            Integer govId = params.getInt("govId");
            PlainResult<GovPlainJDO> plainResult = governmentService.findGovPlainById(govId);
            if (plainResult.isSuccess()) {
                GovPlainJDO govPlainJDO = plainResult.getData();
                GovPlainVO vo = GovPlainVOConverter.convertToVO(govPlainJDO);
                context.put("gov", vo);
            }
        }

        String duty = params.getString("duty");
        context.put("duty", duty);
    }
}

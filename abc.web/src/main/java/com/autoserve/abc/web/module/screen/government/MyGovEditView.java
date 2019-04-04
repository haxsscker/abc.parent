package com.autoserve.abc.web.module.screen.government;

import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.GovPlainJDO;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.LoginUserUtil;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/2 16:38.
 */
public class MyGovEditView {
    @Resource
    private GovernmentService governmentService;

    public void execute(Context context) {
        Integer empId = LoginUserUtil.getEmpId();
        PlainResult<GovPlainJDO> plainResult = governmentService.findGovPlainByEmpId(empId);
        if (plainResult.isSuccess()) {
            GovPlainJDO govPlainJDO = plainResult.getData();
            context.put("gov", govPlainJDO);
        }
    }
}

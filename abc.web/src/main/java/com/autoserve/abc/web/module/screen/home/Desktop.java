package com.autoserve.abc.web.module.screen.home;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.LoginUserUtil;

/**
 * @author yuqing.zheng Created on 2014-12-05,19:59
 */
public class Desktop {
    @Autowired
    private GovernmentService governmentService;

    @Autowired
    private LoanQueryService  loanQueryService;

    @Autowired
    private SysConfigService  SysConfigService;

    public void execute(Context context) {
        Integer orgId = LoginUserUtil.getEmpOrgId();
        PlainResult<GovernmentDO> govRes = governmentService.findById(orgId);

        PlainResult<SysConfig> result = SysConfigService.querySysConfig(SysConfigEntry.EXPIRE_REMIND);
        if (result.isSuccess()) {
            PlainResult<Integer> countResult = loanQueryService.queryCountLoanByExpire(Integer.valueOf(result.getData()
                    .getConfValue()));
            context.put("count", countResult.getData());
        }

        if (govRes.isSuccess()) {
            GovernmentDO orgDO = govRes.getData();
            context.put("userOrgName", orgDO.getGovName());
        }

    }
}

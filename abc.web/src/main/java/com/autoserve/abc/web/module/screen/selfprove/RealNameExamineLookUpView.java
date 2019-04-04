package com.autoserve.abc.web.module.screen.selfprove;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.RealnameAuthJDO;
import com.autoserve.abc.service.biz.convert.RealnameAuthJConverter;
import com.autoserve.abc.service.biz.entity.RealnameAuthJ;
import com.autoserve.abc.service.biz.intf.user.RealnameAuthService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.RealNameAuthJVOConverter;
import com.autoserve.abc.web.vo.user.RealnameAuthJVO;

import javax.annotation.Resource;

public class RealNameExamineLookUpView {
    @Resource
    private RealnameAuthService realnameAuthService;

    public void execute(Context context, ParameterParser params) {
        Integer cpiId = params.getInt("cpiId");
        if (!"0".equals(cpiId) && !"".equals(cpiId) && null != cpiId) {
            RealnameAuthJDO realnameAuthJDO = new RealnameAuthJDO();
            realnameAuthJDO.setRnpId(cpiId);
            PlainResult<RealnameAuthJDO> result = realnameAuthService.findRealNameAuthJDOById(realnameAuthJDO);
            if (result.isSuccess()) {
                RealnameAuthJ realnameAuthJ = RealnameAuthJConverter.toRealnameAuthJ(result.getData());
                RealnameAuthJVO realnameAuthJVO = RealNameAuthJVOConverter.convertToRealNameAuthJVO(realnameAuthJ);
                context.put("realnameAuthJVO", realnameAuthJVO);
            }
        }
    }
}

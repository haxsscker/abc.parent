package com.autoserve.abc.web.module.screen.bank;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.FundApplyDO;
import com.autoserve.abc.service.biz.intf.fund.FundApplyService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.web.convert.FundApplyVOConvert;
import com.autoserve.abc.web.vo.fund.FundApplyVO;

/**
 * 类AddMonBankInfo.java的实现描述：TODO 类实现描述
 * 
 * @author dengjingyu 2014年12月23日 下午4:27:07
 */
public class AddMonBankInfo {
    private static Logger logger = LoggerFactory.getLogger(AddMonBankInfo.class);

    @Resource
    FundApplyService      fundApplyService;

    public void execute(Context context) {

        FundApplyDO pojo = new FundApplyDO();
        ListResult<FundApplyDO> ListResult = fundApplyService.queryAllApplyList(pojo);
        if (ListResult.isSuccess()) {
            List<FundApplyVO> fundApplyVOList = FundApplyVOConvert.convertVoList(ListResult.getData());
            context.put("fundApplyVOList", fundApplyVOList);
        }

    }
}

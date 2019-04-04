package com.autoserve.abc.web.module.screen.bank;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.FundApplyDO;
import com.autoserve.abc.dao.dataobject.MonBankInfoDO;
import com.autoserve.abc.service.biz.convert.MonBankInfoConverter;
import com.autoserve.abc.service.biz.entity.MonBankInfo;
import com.autoserve.abc.service.biz.intf.fund.FundApplyService;
import com.autoserve.abc.service.biz.intf.mon.MonBankInfoService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.FundApplyVOConvert;
import com.autoserve.abc.web.convert.MonBankInfoVOConvert;
import com.autoserve.abc.web.vo.bank.MonBankInfoVO;
import com.autoserve.abc.web.vo.fund.FundApplyVO;

/**
 * 类AddMonBankInfo.java的实现描述：TODO 类实现描述
 * 
 * @author dengjingyu 2014年12月23日 下午4:27:07
 */
public class EditMonBankInfo {
    @Resource
    private MonBankInfoService bankInfoService;
    @Resource
    FundApplyService           fundApplyService;

    public void execute(Context context, @Param("cuiId") Integer monBankId) {

        PlainResult<MonBankInfo> plainResult = bankInfoService.queryById(monBankId);
        if (plainResult.isSuccess()) {
            MonBankInfoDO bankInfo = MonBankInfoConverter.toMonBankInfoDO(plainResult.getData());
            MonBankInfoVO vo = MonBankInfoVOConvert.toMonBankInfoVO(bankInfo);
            context.put("bankInfo", vo);
        }

        FundApplyDO pojo = new FundApplyDO();
        ListResult<FundApplyDO> ListResult = fundApplyService.queryAllApplyList(pojo);
        if (ListResult.isSuccess()) {
            List<FundApplyVO> fundApplyVOList = FundApplyVOConvert.convertVoList(ListResult.getData());
            context.put("fundApplyVOList", fundApplyVOList);
        }
    }

}

package com.autoserve.abc.web.module.screen.autotransfer.json;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.intf.DealRecordDao;
import com.autoserve.abc.service.biz.entity.AutoTransfer;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.TransferActionStste;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.AutoTransferService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.MapResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.Md5Util;
import com.autoserve.abc.service.util.SystemGetPropeties;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 类AutoTransferAddOrEdit.java的实现描述：TODO 类实现描述
 * 
 * @author Tiuwer 2015年4月25日 上午11:32:54
 */
public class AutoTransferAddOrEdit {
    @Resource
    private AutoTransferService autoTransferService;
    @Resource
    private AccountInfoService  accountInfoService;
    @Resource
    private DoubleDryService    doubleDryService;
    @Autowired
    private DealRecordDao dealRecordDao;

    public JsonBaseVO execute(ParameterParser params, @Params AutoTransfer autoTransfer) {
        BaseResult baseResult = new BaseResult();
        StringBuffer str = new StringBuffer();
        str.append(new Date().toString());
        AccountInfoDO accountInfoDO = this.accountInfoService.queryByAccountMark(autoTransfer.getReceibeUserId(),
                UserType.PERSONAL.type);
        MapResult<String, String> resultMap = this.doubleDryService.transfer(doubleDryService.loanJsonList(
                accountInfoDO.getAccountMark(), SystemGetPropeties.getStrString("PlatformMoneymoremore"),
                autoTransfer.getMoneyAmount(), SystemGetPropeties.getStrString("PlatformMoneymoremore"),
                Md5Util.md5(str.toString()), null), TransferActionStste.OTHER.state, "2", "2", "", null);
        String resultCode = resultMap.getData().get("ResultCode");
        if (!"88".equals(resultCode)) {
            return new JsonBaseVO(false, resultMap.getData().get("Message"));
        }
        String loanJsonList = resultMap.getData().get("LoanJsonList");
        JSONArray json = JSON.parseArray(loanJsonList);
        String loanNo = "";
        for (Object item : json) {
            JSONObject itemJson = JSON.parseObject(String.valueOf(item));
            loanNo = String.valueOf(itemJson.get("LoanNo"));
        }

        autoTransfer.setReceibeAccotunt(accountInfoDO.getAccountMark());
        autoTransfer.setOperator(LoginUserUtil.getEmpId());
        autoTransfer.setOperateDate(new Date());
        autoTransfer.setAuditState(ReviewState.PENDING_REVIEW);
        autoTransfer.setPayAccotunt(SystemGetPropeties.getStrString("PlatformMoneymoremore"));
        autoTransfer.setOutSeqNo(loanNo);
        autoTransfer.setState(DealState.NOCALLBACK);
        PlainResult<AutoTransfer> result = this.autoTransferService.createAutoTransfer(autoTransfer);
        //交易记录
        DealRecordDO dealRecord = new DealRecordDO();
        dealRecord.setDrType(DealType.AUTO_TRANSFER.type);
        dealRecord.setDrDetailType(DealDetailType.AUTO_TRANSFER.type);
        dealRecord.setDrMoneyAmount(new BigDecimal(autoTransfer.getMoneyAmount()));
        dealRecord.setDrPayAccount(SystemGetPropeties.getStrString("PlatformMoneymoremore"));
        dealRecord.setDrReceiveAccount(accountInfoDO.getAccountNo());
        dealRecord.setDrInnerSeqNo(Md5Util.md5(str.toString()));
        dealRecord.setDrOutSeqNo(loanNo);
        dealRecord.setDrState(DealState.NOCALLBACK.state);
        dealRecordDao.insertSelective(dealRecord);

        baseResult.setMessage(result.getMessage());
        baseResult.setSuccess(result.isSuccess());
        return ResultMapper.toBaseVO(new BaseResult());
    }
}

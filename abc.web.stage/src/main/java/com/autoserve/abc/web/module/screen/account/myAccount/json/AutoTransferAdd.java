package com.autoserve.abc.web.module.screen.account.myAccount.json;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.DealRecordDao;
import com.autoserve.abc.service.biz.entity.AutoTransfer;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.TransferActionStste;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.AutoTransferService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.MapResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.service.util.Md5Util;
import com.autoserve.abc.service.util.SystemGetPropeties;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class AutoTransferAdd {
	    @Resource
	    private AutoTransferService autoTransferService;
	    @Resource
	    private AccountInfoService  accountInfoService;
	    @Resource
	    private DoubleDryService    doubleDryService;
	    @Resource
	    private UserService userService;
	    @Autowired
	    private HttpSession session;
	    @Resource
		private HttpServletRequest request;
	    @Autowired
		private DeployConfigService deployConfigService;
	    @Autowired
	    private DealRecordDao dealRecordDao;
	    
    public JsonBaseVO execute(Context context, ParameterParser params,Navigator nav) {
    	//登录URL
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		nav.redirectToLocation(deployConfigService.getLoginUrl(request));
    		return null;
    	}  
    	String transferType=params.getString("transferType");     //转账类型    	
        Integer outUserId =params.getInt("outUserId");        //出款人用户id
        String inUserRealName =params.getString("inUserRealName");   //收款人真实姓名
        String inUserName =params.getString("inUserName");     //收款人用户名
        String money =params.getString("money");       //转账金额
        String note =params.getString("note");       //备注
        String dealPsw = params.getString("dealPsw");//交易密码
        BaseResult baseResult = new BaseResult();
        //时间字符串
        StringBuffer str = new StringBuffer();
        str.append(new Date().toString());
        String loanJsonListStr=null;   //转账参数
        AccountInfoDO inUserAccount=null;
        //出款人
        AccountInfoDO accountInfoDO = this.accountInfoService.queryByAccountMark(outUserId,
        		user.getUserType().getType());
        //验证交易密码
        PlainResult<UserDO> resultUserDO = userService.findById(outUserId);
		 UserDO userDO=resultUserDO.getData();
		 if(dealPsw==null || !userDO.getUserDealPwd().equals(CryptUtils.md5(dealPsw))){
			 baseResult.setSuccess(false);
			 baseResult.setMessage("交易密码错误");
			 return ResultMapper.toBaseVO(baseResult);
		 }
        //转给个人
        if(transferType!=null && "1".equals(transferType)){
            //收款人
            AccountInfoDO account = new AccountInfoDO();
            account.setAccountUserName(inUserName);
            List<AccountInfoDO>  accountList = accountInfoService.qureyAccountByUserId(account);
            if(accountList.isEmpty()){
            	baseResult.setMessage("收款用户不存在!");
            	baseResult.setSuccess(false);
            	return ResultMapper.toBaseVO(baseResult);
            }
            inUserAccount =accountList.get(0);
            if(!inUserRealName.equals(inUserAccount.getAccountLegalName())){
            	baseResult.setMessage("收款用户姓名不一致!");
            	baseResult.setSuccess(false);
            	return ResultMapper.toBaseVO(baseResult);
            }
            loanJsonListStr=doubleDryService.loanJsonList(
            		inUserAccount.getAccountMark(),accountInfoDO.getAccountMark(),
                    money, SystemGetPropeties.getStrString("PlatformMoneymoremore"),
                    Md5Util.md5(str.toString()), null);
        }else if(transferType!=null && "2".equals(transferType)){
        	loanJsonListStr=doubleDryService.loanJsonList(
        			SystemGetPropeties.getStrString("PlatformMoneymoremore"),accountInfoDO.getAccountMark(),
                    money, SystemGetPropeties.getStrString("PlatformMoneymoremore"),
                    Md5Util.md5(str.toString()), null);
        }
       //调用转账接口
        MapResult<String, String> resultMap = this.doubleDryService.transfer(loanJsonListStr, TransferActionStste.INVEST.state, "2", "2", "", null);
        String resultCode = resultMap.getData().get("ResultCode");
        if (!"88".equals(resultCode)) {
        	baseResult.setMessage(resultMap.getData().get("Message"));
        	baseResult.setSuccess(false);
        	return ResultMapper.toBaseVO(baseResult);
        }
        String loanJsonList = resultMap.getData().get("LoanJsonList");
        JSONArray json = JSON.parseArray(loanJsonList);
        String loanNo = "";
        for (Object item : json) {
            JSONObject itemJson = JSON.parseObject(String.valueOf(item));
            loanNo = String.valueOf(itemJson.get("LoanNo"));
        }
        AutoTransfer autoTransfer = new AutoTransfer();
        autoTransfer.setPayAccotunt(accountInfoDO.getAccountMark());   //付款人账号       
        //收款人账号
        if(transferType!=null && "1".equals(transferType)){
        	autoTransfer.setReceibeAccotunt(inUserAccount.getAccountMark());
        	autoTransfer.setReceibeUserId(inUserAccount.getAccountUserId());    //收款人ID
        }else{
        	autoTransfer.setReceibeAccotunt(SystemGetPropeties.getStrString("PlatformMoneymoremore"));
        }
        autoTransfer.setMoneyAmount(money);    //交易金额
        autoTransfer.setState(DealState.NOCALLBACK);    //交易状态        
        autoTransfer.setOperator(accountInfoDO.getAccountUserId());      //交易操作人
        autoTransfer.setOperateDate(new Date());       //交易创建时间
        autoTransfer.setAuditState(ReviewState.PENDING_REVIEW);      //审核状态
        autoTransfer.setOutSeqNo(loanNo);       //外部流水号    
        autoTransfer.setAtRemarks(note);       //转账备注
        PlainResult<AutoTransfer> result = this.autoTransferService.createAutoTransfer(autoTransfer);
        //交易记录
        DealRecordDO dealRecord = new DealRecordDO();
        dealRecord.setDrType(DealType.AUTO_TRANSFER.type);
        dealRecord.setDrDetailType(DealDetailType.AUTO_TRANSFER.type);
        dealRecord.setDrMoneyAmount(new BigDecimal(money));
        dealRecord.setDrPayAccount(accountInfoDO.getAccountNo());
        if("1".equals(transferType)){
        	dealRecord.setDrReceiveAccount(inUserAccount.getAccountNo());
        }else{
        	dealRecord.setDrReceiveAccount(SystemGetPropeties.getStrString("PlatformMoneymoremore"));
        }
        dealRecord.setDrInnerSeqNo(Md5Util.md5(str.toString()));
        dealRecord.setDrOutSeqNo(loanNo);
        dealRecord.setDrState(DealState.NOCALLBACK.state);
        dealRecordDao.insertSelective(dealRecord);
        
        baseResult.setMessage(result.getMessage());
        baseResult.setSuccess(result.isSuccess());
        return ResultMapper.toBaseVO(baseResult);        
    }
}

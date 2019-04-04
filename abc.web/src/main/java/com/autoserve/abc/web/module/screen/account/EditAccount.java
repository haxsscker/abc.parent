package com.autoserve.abc.web.module.screen.account;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.RealnameAuthDO;
import com.autoserve.abc.service.biz.convert.CashSqConverter;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.CashSq;
import com.autoserve.abc.service.biz.entity.RealnameAuth;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cashsq.CashSqService;
import com.autoserve.abc.service.biz.intf.user.RealnameAuthService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class EditAccount {
    @Resource
    private UserService         userService;
    @Resource
    private CashSqService         cashSqService;
    
    @Resource
    private AccountInfoService  accountInfoService;
    @Resource
    private RealnameAuthService realnameAuthService;

    private static Logger       logger = LoggerFactory.getLogger(EditAccount.class);

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public JsonBaseVO execute(ParameterParser params) {
        BaseResult result = new BaseResult();
        //添加增加免费提现额度申请
        CashSq cashSq = new CashSq();
        cashSq.setState(1);//申请状态
        cashSq.setUserId(params.getInt("act_user_id"));//申请用户
      if(params.getDouble("act_user_sqaddcash")!=0){
    	  cashSq.setUserCashQuotaSqadd(new BigDecimal(params.getDouble("act_user_sqaddcash")));
      }    
      logger.info("申请增加提现额度"+params.getDouble("act_user_sqaddcash"));
        logger.info("申请增加提现额度"+cashSq.getUserCashQuotaSqadd());
        cashSqService.insetcashsq(CashSqConverter.toCashSqDO(cashSq));
        
        
        
        
        User user = new User();
        user.setUserId(params.getInt("act_user_id"));
        user.setUserDocNo(params.getString("act_user_card"));
        user.setUserEmail(params.getString("act_user_email"));
        user.setUserPhone(params.getString("act_user_phone"));
        
        //user.setUserCashSqadd(new BigDecimal(params.getDouble("act_user_sqaddcash")));
       // user.setUserSqaddState(1);

//        if(params.getDouble("act_user_cash")!=0){
//        	 user.setUserCashQuota(new BigDecimal(params.getDouble("act_user_cash")));
//        }       
        BaseResult userResult = userService.modifyUserSelective(user);
        if (userResult.isSuccess()) {
            result = userResult;
        } else {
            result.setSuccess(false);
            result.setMessage("更新用户表失败");
            return ResultMapper.toBaseVO(result);
        }
        int userType = userService.findEntityById(params.getInt("act_user_id")).getData().getUserType().getType();
        Account accountInfo = new Account();
        accountInfo.setAccountUserId(params.getInt("act_user_id"));
        accountInfo.setAccountUserCard(params.getString("act_user_card"));
        accountInfo.setAccountUserEmail(params.getString("act_user_email"));
        accountInfo.setAccountUserPhone(params.getString("act_user_phone"));
        
        // 查询出用户是否有账户信息，如果有就更新账户信息
        AccountInfoDO accountInfoDO = accountInfoService.qureyAccountByUserIdAndUserType(params.getInt("act_user_id"),userType);
        if(null != accountInfoDO){
        	BaseResult accountResult = accountInfoService.modifyAccountInfo(accountInfo);
            if (accountResult.isSuccess()) {
                result = accountResult;
            } else {
                result.setSuccess(false);
                result.setMessage("更新账户表失败");
                return ResultMapper.toBaseVO(result);
            }
        }
        
        RealnameAuth realNameAuth = new RealnameAuth();
        PlainResult<RealnameAuthDO> plainResult = realnameAuthService
                .findRealNameAuthById(params.getInt("act_user_id"));
        if (plainResult.getData() == null) {
            return ResultMapper.toBaseVO(result);
        }
        realNameAuth.setRnpId(params.getInt("act_user_id"));
        realNameAuth.setRnpUserid(params.getInt("act_user_id"));
        realNameAuth.setRnpDocNo(params.getString("act_user_card"));
        BaseResult realnameResult = realnameAuthService.updateRealNameAudit(realNameAuth);
        if (realnameResult.isSuccess()) {
            result = realnameResult;
        } else {
            result.setSuccess(false);
            result.setMessage("更新失败");
            return ResultMapper.toBaseVO(result);
        }
        
        return ResultMapper.toBaseVO(result);
    }
}

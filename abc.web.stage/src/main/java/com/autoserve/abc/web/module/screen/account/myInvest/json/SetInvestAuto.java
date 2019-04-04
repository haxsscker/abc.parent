package com.autoserve.abc.web.module.screen.account.myInvest.json;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.InvestSet;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.InvestSetOpenState;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanPayType;
import com.autoserve.abc.service.biz.intf.invest.InvestSetService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.vo.JsonBaseVO;
/**
 * 
 * @author DS
 *
 * 2015上午11:40:25
 */
public class SetInvestAuto {
	@Autowired
	private HttpSession           session;
	@Resource
	private HttpServletRequest    request;
	@Autowired
	private DeployConfigService   deployConfigService;
	@Autowired
	private InvestSetService     investSetService;
	@Resource
	private UserService userService;
	
	public JsonBaseVO execute(Context context, @Params InvestSet investSet,ParameterParser params, Navigator nav) {
		 User user = (User) session.getAttribute("user");
	        if (user == null) {
	            nav.redirectToLocation(deployConfigService.getLoginUrl(request));
	            return null;
	        }
	        
	        if(params.getInt("flag")==1){
	        	
	        	String passw = params.getString("passw");
	        	PlainResult<UserDO> userDoResult=userService.findById(user.getUserId());
  				if(passw==null || !CryptUtils.md5(passw).equals(userDoResult.getData().getUserDealPwd())){
  					BaseResult baseResult=new BaseResult();
  					baseResult.setSuccess(false);
  					baseResult.setMessage("交易密码不正确!");
  					return directReturn(baseResult);
  				}
	        	
	 	    	investSet.setUserId(user.getUserId());
	 	    	//标类型
	 	        investSet.setLoanCategory(LoanCategory.valueOf(params.getInt("loanCategory")));
	 	        //还款方式
	 	    	investSet.setLoanType(LoanPayType.valueOf(params.getInt("loanType")));
	 	    	//默认启用
	 	    	investSet.setIsOpen(InvestSetOpenState.STATE_ENABLE);
	 	    	return directReturn(createInvestSet(investSet));
	        }else if(params.getInt("flag")==2){
	        	int id=params.getInt("id");
	        	return directReturn(removeInvestSet(id));
	        }else if(params.getInt("flag")==3){
	        	int id=params.getInt("id");
	        	String action=params.getString("action");
	        	return directReturn(modifyInvestSet(id,action));
	        }else{
	        	return null;
	        }
	}
	
	//添加
	public BaseResult createInvestSet(InvestSet investSet){
		 return investSetService.createInvest(investSet);
	}
	//删除
	public BaseResult removeInvestSet(Integer id){
		return investSetService.removeInvestById(id);
	}
	//开启自动投标
	public BaseResult modifyInvestSet(Integer id,String action){
		InvestSet pojo=new InvestSet();
		pojo.setId(id);
		if(action!=null && "open".equals(action)){
			pojo.setIsOpen(InvestSetOpenState.STATE_ENABLE);
		}else if(action!=null && "close".equals(action)){
			pojo.setIsOpen(InvestSetOpenState.STATE_DISABLE);
		}		
		return investSetService.modifyInvest(pojo);
	}
	
	private JsonBaseVO directReturn(BaseResult serviceResult) {
        JsonBaseVO result = new JsonBaseVO();
        result.setMessage(serviceResult.getMessage());
        result.setSuccess(serviceResult.isSuccess());
        return result;
	}
}

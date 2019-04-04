package com.autoserve.abc.web.module.screen.account.myInvest.json;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.service.biz.entity.InvestSet;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.InvestSetOpenState;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanPayType;
import com.autoserve.abc.service.biz.intf.invest.InvestSetService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.web.helper.DeployConfigService;
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
	public BaseResult execute(Context context, @Params InvestSet investSet,ParameterParser params, Navigator nav) {
		 User user = (User) session.getAttribute("user");
	        if (user == null) {
	            nav.redirectToLocation(deployConfigService.getLoginUrl(request));
	            return null;
	        }
	        if(params.getInt("flag")==1){
	 	    	investSet.setUserId(user.getUserId());
	 	    	//标类型
	 	        investSet.setLoanCategory(LoanCategory.valueOf( params.getInt("loanCategory")));
	 	        //还款方式
	 	    	investSet.setLoanType(LoanPayType.valueOf(params.getInt("loanType")));
	 	    	//默认关闭
	 	    	investSet.setIsOpen(InvestSetOpenState.STATE_DISABLE);
	 	    	return createInvestSet(investSet);
	        }else if(params.getInt("flag")==2){
	        	int id=params.getInt("id");
	        	return removeInvestSet(id);
	        }else if(params.getInt("flag")==3){
	        	int id=params.getInt("id");
	        	String action=params.getString("action");
	        	return modifyInvestSet(id,action);
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
}

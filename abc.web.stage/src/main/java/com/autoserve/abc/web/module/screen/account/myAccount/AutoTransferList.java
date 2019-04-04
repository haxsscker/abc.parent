/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.account.myAccount;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.AutoTransferDO;
import com.autoserve.abc.service.biz.entity.AutoTransfer;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.AutoTransferService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.AutoTransferVOConverter;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.Pagebean;
import com.autoserve.abc.web.vo.auto.AutoTransferVO;

/**
 * 自主转账列表
 * 
 * @author zhangkang 2015年6月25日 上午11:05:44
 */
public class AutoTransferList {
    @Resource
    private AutoTransferService autoTransferService;
    @Resource
    private UserService         userService;
    @Resource
    private EmployeeService     employeeService;
    @Resource
    private AccountInfoService  accountInfoService;
    @Autowired
    private HttpSession session;
    @Resource
	private HttpServletRequest request;
    @Autowired
	private DeployConfigService deployConfigService;

    public void execute(ParameterParser params, @Param("currentPage") int page, @Param("pageSize") int pageSize,
                        Context context,Navigator nav) {
    	//登录URL
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		nav.redirectToLocation(deployConfigService.getLoginUrl(request));
    		return;
    	}  
        if (page == 0) {
            page = 1;
        }
        if (pageSize == 0) {
            pageSize = 5;
        }
        PageCondition pageCondition = new PageCondition(page, pageSize);

        //查询此用户的开户号
      //出款人
        AccountInfoDO accountInfoDO = this.accountInfoService.queryByAccountMark(user.getUserId(),
        		user.getUserType().getType());
        AutoTransferDO autoTransferDO=new AutoTransferDO();
        autoTransferDO.setPayOrReciveAccount(accountInfoDO.getAccountMark());
        PageResult<AutoTransfer>  result=this.autoTransferService.queryList(autoTransferDO, pageCondition);
        ListResult<AutoTransferVO> listResult = new ListResult<AutoTransferVO>();
        if (result.getDataSize() > 0) {

            List<AutoTransferVO> list = AutoTransferVOConverter.toAutoTransferVOList(result.getData(), userService,
                    employeeService, accountInfoService);
            listResult.setData(list);
        }
        context.put("pagebean",
                new Pagebean<AutoTransferVO>(page, pageSize, listResult.getData(), result.getTotalCount()));
    }
}

package com.autoserve.abc.web.module.screen.account.myInvest.json;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.TransferLoanDO;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.download.FileDownloadService;
import com.autoserve.abc.web.helper.DeployConfigService;
/**
 * 
 * @author DS
 *
 * 2015上午10:13:43
 */
public class InvestRecordPdf {
	@Autowired
	private DeployConfigService deployConfigService;
	@Autowired
    private HttpSession session;
	@Autowired
	private FileDownloadService fileDownloadService;
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;
	@Resource
	private LoanDao loanDao;
	@Resource
	private InvestDao investDao;
	
    public void execute(Context context, ParameterParser params,Navigator nav) {
    	//登录URL
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		nav.forwardTo(deployConfigService.getLoginUrl(request));
    		return;
    	}
    	try {
    		String type = params.getString("type");
    		Integer loanId = params.getInt("loanId");
    		Integer investId = params.getInt("investId");
    		LoanDO loan = loanDao.findById(loanId);
    		InvestDO invest = null;
    		String path = "";
    		String fileName = loan.getLoanNo() + ".pdf";
    		if("jk".equals(type)) {
    			path = loan.getContractPath();
    		} else if("tz".equals(type)) {
    			invest = investDao.findById(investId);
    			path = invest.getInCommContract();
    		}
        	response.setCharacterEncoding("gb2312");
    		response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("GB2312"),"iso8859-1"));
    		response.setContentType("application/ynd.ms-excel;charset=UTF-8");
    		
    		fileDownloadService.download(path, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}

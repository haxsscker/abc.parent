package com.autoserve.abc.web.module.screen.article;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.service.biz.entity.ArticleInfo;
import com.autoserve.abc.service.biz.intf.article.ArticleClassService;
import com.autoserve.abc.service.biz.intf.article.ArticleInfoService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.result.PlainResult;

public class ArticleAddView {
	
	    @Resource
	    private ArticleInfoService articleInfoService;
	    
	    @Resource
	    private ArticleClassService articleClassService;
	    
	    @Resource
	    private EmployeeService employeeService;

	    public void execute(Context context, ParameterParser params) {
	    	int articleId = params.getInt("articleId");
	    	if(articleId!=0){
		        PlainResult<ArticleInfo>  result = this.articleInfoService.queryById(articleId);
		        
		        ArticleInfo articleInfo = result.getData();
		        EmployeeDO employee = employeeService.findById(articleInfo.getAiAddEmp()).getData();
		        context.put("employee", employee);
		        context.put("articleInfo", articleInfo);
	        }
	    }

}

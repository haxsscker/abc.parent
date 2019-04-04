package com.autoserve.abc.web.module.screen.statistic.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.web.module.screen.statistic.ExcelPlatformReport;

/**
 * 平台运营统计报表
 * 
 * @author zhangkang
 *
 */
public class PlatFormReport{

	@Resource
	private HttpServletResponse response;
	@Autowired
	private ExcelPlatformReport excelReport;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ServletContext application;

	public void execute(ParameterParser params) throws Exception {
		Date date = params.getDate("date", new SimpleDateFormat("yyyy-MM-dd"));
		//读取模版
		String path = application.getRealPath("/") + File.separator
				+ "excelTpl" + File.separator + "platformReport.xls";
		System.out.println(path);
		InputStream in = new FileInputStream(path);
		
		//导出
		String name = "新华久久贷运营统计表.xls";
		response.setCharacterEncoding("gb2312");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(name.getBytes("GB2312"), "iso8859-1"));
		response.setContentType("application/ynd.ms-excel;charset=UTF-8");
		excelReport.generateExcel(date, in, response.getOutputStream());
	}
}

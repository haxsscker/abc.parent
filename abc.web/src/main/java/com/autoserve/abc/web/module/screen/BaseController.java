package com.autoserve.abc.web.module.screen;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.autoserve.abc.service.util.ExcelFileGenerator;

public class BaseController {

	@Resource
	private HttpServletResponse response;

	/**
	 * 导出excel表
	 * @param fieldName 列
	 * @param fieldData 数据
	 * @param name 文件名
	 */
	public void ExportExcel(List<?> fieldName, List<?> fieldData, String name) {
		ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator(
				fieldName, fieldData);
		try {
			response.setCharacterEncoding("gb2312");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(name.getBytes("GB2312"), "iso8859-1"));
			response.setContentType("application/ynd.ms-excel;charset=UTF-8");
			excelFileGenerator.expordExcel(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

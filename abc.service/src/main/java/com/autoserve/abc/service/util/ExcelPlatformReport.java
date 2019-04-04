package com.autoserve.abc.service.util;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.autoserve.abc.dao.dataobject.PlatformReport;

public class ExcelPlatformReport {

	public static void generateExcel(PlatformReport report, OutputStream out)
			throws Exception {
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet sheet = workBook.createSheet("sheet1");
		//第一行
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell1 = row1.createCell((short)0);
		cell1.setCellValue("新华久久贷运营统计表");
		//第二行
		
		workBook.write(out);
		out.flush();
		out.close();
	}
	
	public static void main(String[] args) throws Exception{
		OutputStream out = new FileOutputStream("E:/test2.xls");
		PlatformReport report = new PlatformReport();
		ExcelPlatformReport.generateExcel(report, out);
		System.out.println("aa");
	}
}

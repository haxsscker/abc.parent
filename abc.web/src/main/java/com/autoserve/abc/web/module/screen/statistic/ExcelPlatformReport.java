package com.autoserve.abc.web.module.screen.statistic;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autoserve.abc.dao.dataobject.PlatformReport;
import com.autoserve.abc.dao.intf.PlatformReportDao;
import com.autoserve.abc.service.util.DateUtil;
/**
 * 平台运营统计表导出
 * @author zhangkang
 *
 */
@Component
public class ExcelPlatformReport {
	@Autowired
	private PlatformReportDao platfromReportDao;

	/**
	 * 根据模板生成运营统计表
	 * @param date 统计日期
	 * @param in 模板文件流
	 * @param out 输出流
	 * @throws Exception
	 */
	public void generateExcel(Date date, InputStream in, OutputStream out)
			throws Exception {
		PlatformReport[] reports = this.findReport(date);
		// 从模板读入excel
		HSSFWorkbook workBook = new HSSFWorkbook(in);
		HSSFSheet sheet = workBook.getSheetAt(0);

		for (int i = 0; i < 41; i++) {
			HSSFRow row = sheet.getRow(2 + i);
			for (int j = 0; j < 5; j++) {
				HSSFCell cell = row.getCell((short) (2 + j));
				// cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				setCellValue(i, j, cell, reports);
				//红包只统计本日
//				if(i==40 && j>0){
//					cell.setCellValue("");
//				}
			}
		}
		// 统计时间
		HSSFRow timeRow = sheet.getRow(44);
		HSSFCell timeCell = timeRow.getCell((short) 2);
		timeCell.setEncoding(HSSFCell.ENCODING_UTF_16);
		timeCell.setCellValue(DateUtil.formatDate(date, "yyyy年MM月dd日"));

		in.close();
		workBook.write(out);
		out.flush();
		out.close();
	}

	/**
	 * 统计当日，本周，本月，本年，平台累计数据
	 * 
	 * @param date
	 * @return
	 */
	private PlatformReport[] findReport(Date date) {
		PlatformReport[] reports = new PlatformReport[5];
		// 本日
		PlatformReport dayReport = new PlatformReport();
		dayReport.setBeginDate(DateUtil.clearTime(date));
		dayReport.setEndDate(DateUtil.fullTime(date));
		platfromReportDao.report(dayReport);
		// 本周
		PlatformReport weekReport = new PlatformReport();
		weekReport.setBeginDate(DateUtil.clearTime(DateUtil
				.getFirstDayOfWeek(date)));
		weekReport
				.setEndDate(DateUtil.fullTime(DateUtil.getLastDayOfWeek(date)));
		platfromReportDao.report(weekReport);
		// 本月
		PlatformReport monthReport = new PlatformReport();
		monthReport.setBeginDate(DateUtil.clearTime(DateUtil
				.getFirstDayOfMonth(date)));
		monthReport.setEndDate(DateUtil.fullTime(DateUtil
				.getLastDayOfMonth(date)));
		platfromReportDao.report(monthReport);
		// 本年
		PlatformReport yearReport = new PlatformReport();
		yearReport.setBeginDate(DateUtil.clearTime(DateUtil
				.getFirstDayOfYear(date)));
		yearReport
				.setEndDate(DateUtil.fullTime(DateUtil.getLastDayOfYear(date)));
		platfromReportDao.report(yearReport);
		// 总计
		PlatformReport allReport = new PlatformReport();
		allReport.setBeginDate(new DateTime(1970, 1, 1, 0, 0, 0).toDate());
		allReport.setEndDate(new DateTime(2038, 1, 1, 0, 0, 0).toDate());
		platfromReportDao.report(allReport);
		//
		reports[0] = dayReport;
		reports[1] = weekReport;
		reports[2] = monthReport;
		reports[3] = yearReport;
		reports[4] = allReport;
		return reports;
	}
	/**
	 * 设置单元格的值
	 * @param i
	 * @param j
	 * @param cell
	 * @param reports
	 */
	private void setCellValue(int i, int j, HSSFCell cell,
			PlatformReport[] reports) {
		switch (i) {
		case 0:
			cell.setCellValue(reports[j].getRegisterCount() + "");
			break;
		case 1:
			cell.setCellValue(reports[j].getInvestCount() + "");
			break;
		case 2:
			cell.setCellValue(reports[j].getOpenAccountCount() + "");
			break;
		case 3:
			cell.setCellValue(reports[j].getMonth1() + "");// 一月标
			break;
		case 4:
			cell.setCellValue(reports[j].getMonth1Money() + "");
			break;
		case 5:
			cell.setCellValue(reports[j].getMonth3() + "");
			break;
		case 6:
			cell.setCellValue(reports[j].getMonth3Money() + "");
			break;
		case 7:
			cell.setCellValue(reports[j].getMonth6() + "");
			break;
		case 8:
			cell.setCellValue(reports[j].getMonth6Money() + "");
			break;
		case 9:
			cell.setCellValue(reports[j].getMonth12() + "");
			break;
		case 10:
			cell.setCellValue(reports[j].getMonth12Money() + "");
			break;
		case 11:
			cell.setCellValue(reports[j].getYear1() + "");
			break;
		case 12:
			cell.setCellValue(reports[j].getYear1Money() + "");
			break;
		case 13:
			cell.setCellValue(reports[j].getLoanCount() + "");
			break;
		case 14:
			cell.setCellValue(reports[j].getLoanMoneySum() + "");
			break;
		case 15:
			cell.setCellValue(reports[j].getInvestMoney() + "");
			break;
		case 16:
			cell.setCellValue(reports[j].getFullMoney() + "");
			break;
		case 17:
			cell.setCellValue(reports[j].getWaitCapital() + "");
			break;
		case 18:
			cell.setCellValue(reports[j].getWaitInterest() + "");
			break;
		case 19:
			cell.setCellValue(reports[j].getTransferCount() + "");
			break;
		case 20:
			cell.setCellValue(reports[j].getTransferMoney() + "");
			break;
		case 21:
			cell.setCellValue(reports[j].getPayedInterest() + "");
			break;
		case 22:
			cell.setCellValue(reports[j].getCompleteLoanCount() + "");
			break;
		case 23:
			cell.setCellValue(reports[j].getCompleteLoanMoney() + "");
			break;
		case 24:
			cell.setCellValue(reports[j].getRechargeCount() + "");
			break;
		case 25:
			cell.setCellValue(reports[j].getRechargeMoney() + "");
			break;
		case 26:
			cell.setCellValue(reports[j].getWithdrawCount() + "");
			break;
		case 27:
			cell.setCellValue(reports[j].getWithdrawMoney() + "");
			break;
		case 28:
			cell.setCellValue(reports[j].getHandFeeCount() + "");
			break;
		case 29:
			cell.setCellValue(reports[j].getHandFeeMoney() + "");
			break;
		case 30:
			cell.setCellValue(reports[j].getTransferFeeCount() + "");//转让费笔数
			break;
		case 31:
			cell.setCellValue(reports[j].getTransferFeeMoney() + "");//转让费金额
			break;
		case 32:
			cell.setCellValue(reports[j].getGuarFeeCount() + "");
			break;
		case 33:
			cell.setCellValue(reports[j].getGuarFeeMoney() + "");// 担保费
			break;
		case 34:
			cell.setCellValue(reports[j].getRegisterRedMoney() + "");// 注册送红包
			break;
		case 35:
			cell.setCellValue(reports[j].getInvestRedMoney() + "");
			break;
		case 36:
			cell.setCellValue(reports[j].getInviteRedMoney() + "");
			break;
		case 37:
			cell.setCellValue(reports[j].getActivityRedMoney() + "");
			break;
		case 38:
			cell.setCellValue(reports[j].getScoreRedMoney() + ""); // 积分兑换红包
			break;
		case 39:
			cell.setCellValue(reports[j].getUseRedMoney() + "");
			break;
		case 40:
			cell.setCellValue(reports[j].getUndueRedMoney() + "");
			break;
		}
	}

}

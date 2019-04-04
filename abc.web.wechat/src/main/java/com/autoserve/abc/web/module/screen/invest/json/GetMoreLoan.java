package com.autoserve.abc.web.module.screen.invest.json;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.velocity.tools.generic.NumberTool;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.LoanSearchDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.Arith;

public class GetMoreLoan {

	@Resource
	LoanQueryService loanQueryService;

	public String[] execute(Context context, ParameterParser params) {
		LoanSearchDO searchDO = new LoanSearchDO();
		searchDO.setLoanState(Arrays.asList(LoanState.BID_INVITING.state,
				LoanState.FULL_WAIT_REVIEW.state,
				LoanState.FULL_REVIEW_PASS.state,
				LoanState.FULL_REVIEW_FAIL.state, LoanState.BID_CANCELED.state,
				LoanState.MONEY_TRANSFERING.state, LoanState.REPAYING.state,
				LoanState.REPAY_COMPLETED.state));
		int pageSize = 5;
		int currentPage = params.getInt("currentPage") + 1;
		PageCondition pageCondition = new PageCondition(currentPage, pageSize);
		searchDO.setFlag(0);//排序
		PageResult<Loan> resultLoanList = this.loanQueryService
				.queryLoanListBySearchParam(searchDO, pageCondition);
		// JsonListVO<String> jvo = new JsonListVO<String>();
		String[] jvo = new String[2];
		StringBuffer html = addHtml(resultLoanList.getData());
		// List<String> list = new ArrayList<String>();
		jvo[0] = html.toString();
		StringBuffer loanisd = new StringBuffer();
		for (Loan loan : resultLoanList.getData()) {
			loanisd.append(loan.getLoanId() + ",");
		}
		jvo[1] = loanisd.toString();
		return jvo;
	}

	private StringBuffer addHtml(List<Loan> loans) {
		StringBuffer html = new StringBuffer();
		String format = "###,###,###";
		NumberTool nt = new NumberTool();
		for (Loan l : loans) {
			html.append("<div class='row bg_white mt10 index_pro'>");
			html.append("<div class='text-center clearfix loanCategory'>");
			int loanCategory = l.getLoanCategory().category;
			if (loanCategory == 1) {
				html.append("<img src='/images/c-tzlist-xin.png'/>");
			} else if (loanCategory == 2) {
				html.append("<img src='/images/c-tzlist-di.png'/>");
			} else if (loanCategory == 3) {
				html.append("<img src='/images/c-tzlist-dan.png'/>");
			} else if (loanCategory == 4) {
				html.append("<img src='/images/c-tzlist-zong.png'/>");
			}
			html.append("</div>");

			html.append("<div class='col-xs-8 col-sm-8 pt15'>");
			html.append("<a href='/invest/investDetail?loanId=" + l.getLoanId()
					+ "&flagLoan=1' class='index_link'>");
			html.append("<p class='pt10'>");

			if (l.getLoanNo().length() > 25) {
				html.append(l.getLoanNo().substring(0, 25) + "...");
			} else {
				html.append(l.getLoanNo());
			}
			html.append("</p>");
			html.append("<ul class='list-unstyled clearfix'>");
			html.append("<li class='pull-left'><span class='xm_name'>金额</span><span class='xm_num'>"
					+ "￥"
					+ nt.format(format, l.getLoanMoney())
					+ "</span></li>");
			html.append("<li class='pull-left'><span class='xm_name'>年利率</span><span class='xm_num'>"
					+ l.getLoanRate() + "%</span></li> ");
			html.append("<li class='pull-left'><span class='xm_name'>期限</span><span class='xm_num'>"
					+ l.getLoanPeriod()
					+ l.getLoanPeriodUnit().getPrompt()
					+ "</span></li>");
			html.append("</ul>");
			html.append("</a>");
			html.append("</div>");
			html.append("<div class='col-xs-2 col-sm-2 mt20'><div class='percentBox mt20'><div id='bg_"
					+ l.getLoanId()
					+ "'></div><div id='txt_"
					+ l.getLoanId()
					+ "' class='pertxt'></div></div></div>");
//			BigDecimal percent = l.getLoanCurrentValidInvest()
//					.divide(l.getLoanMoney(), 50, BigDecimal.ROUND_HALF_UP)
//					.multiply(new BigDecimal(100));
			BigDecimal percent = Arith.calcPercent(l.getLoanCurrentValidInvest(), l.getLoanMoney(), l.getLoanState());
			html.append("<input type='hidden' id='loanid_" + l.getLoanId()
					+ "' value='" + l.getLoanId() + "'>");
			html.append("<input type='hidden' id='percent_" + l.getLoanId()
					+ "' value='" + percent.floatValue() + "'> ");
			html.append("</div>");
		}

		return html;
	}
}

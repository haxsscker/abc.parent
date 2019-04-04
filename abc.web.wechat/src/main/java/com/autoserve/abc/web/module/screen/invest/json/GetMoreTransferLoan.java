package com.autoserve.abc.web.module.screen.invest.json;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.velocity.tools.generic.NumberTool;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.TransferLoanJDO;
import com.autoserve.abc.dao.dataobject.search.TransferLoanSearchDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.Arith;

public class GetMoreTransferLoan {
	@Resource
	private TransferLoanService transferLoanService;
	@Resource
	private LoanService loanService;
	
	public String[] execute(Context context, ParameterParser params) {
		TransferLoanSearchDO pojo = new TransferLoanSearchDO();
		pojo.setTransferLoanStates(Arrays.asList(
				TransferLoanState.TRANSFERING.state,
				TransferLoanState.FULL_WAIT_REVIEW.state,
				TransferLoanState.FULL_REVIEW_PASS.state,
				TransferLoanState.FULL_REVIEW_FAIL.state,
				//TransferLoanState.BID_CANCELED.state,
				TransferLoanState.MONEY_TRANSFERING.state,
				TransferLoanState.MONEY_TRANSFERED.state));
		int currentPage = params.getInt("currentPage");
		int pageSize = params.getInt("pageSize");
		PageCondition pageCondition = new PageCondition(currentPage, pageSize);
		PageResult<TransferLoanJDO> result = transferLoanService
				.queryListByParam(pojo, pageCondition);
		String[] jvo = new String[2];
		jvo[0]  = addHtml(result.getData());
		StringBuffer tlIds = new StringBuffer();
		for (TransferLoanJDO loan : result.getData()) {
			tlIds.append(loan.getTlId() + ",");
		}
		jvo[1] = tlIds.toString();
		return jvo;
	}
	
	private String addHtml(List<TransferLoanJDO> loans) {
		StringBuffer html = new StringBuffer();
		String format = "###,###,###";
		NumberTool nt = new NumberTool();
		for (TransferLoanJDO l : loans) {
			html.append("<div class='row bg_white mt10 index_pro'>");
			html.append("<div class='text-center clearfix loanCategory'>");
			
			html.append("<img src='/images/c-tzlist-zhuan.png'/>");
			
			html.append("</div>");

			html.append("<div class='col-xs-8 col-sm-8 pt15'>");
			html.append("<a href='/invest/transferLoanDetail?transferId=" + l.getTlId()
					+ "' class='index_link'>");
			html.append("<p class='pt10'>");
			if(l.getTlLoanNo()==null){
				l.setTlLoanNo("");
			}	
			if (l.getTlLoanNo().length() > 25) {
				html.append(l.getTlLoanNo().substring(0, 25) + "...");
			} else {
				html.append(l.getTlLoanNo());
			}
			
			
			html.append("</p>");
			html.append("<ul class='list-unstyled clearfix'>");
			html.append("<li class='pull-left'><span class='xm_name'>金额</span><span class='xm_num'>"
					+ "￥"
					+ nt.format(format, l.getTlTransferMoney())
					+ "</span></li>");
			html.append("<li class='pull-left'><span class='xm_name'>年利率</span><span class='xm_num'>"
					+ l.getLoanRate() + "%</span></li> ");
			
			String	timeLimit = l.getTimelimit()+"天";
			
			html.append("<li class='pull-left'><span class='xm_name'>期限</span><span class='xm_num'>"
					+ timeLimit
					+ "</span></li>");
			html.append("</ul>");
			html.append("</a>");
			html.append("</div>");
			html.append("<div class='col-xs-2 col-sm-2 mt20'><div class='percentBox mt20'><div id='bg_"
					+ l.getTlId()
					+ "'></div><div id='txt_"
					+ l.getTlId()
					+ "' class='pertxt'></div></div></div>");
			BigDecimal percent = Arith.calcPercent(l.getTlCurrentValidInvest(), l.getTlTransferMoney());
			html.append("<input type='hidden' id='loanid_" + l.getTlId()
					+ "' value='" + l.getTlId() + "'>");
			html.append("<input type='hidden' id='percent_" + l.getTlId()
					+ "' value='" + percent.floatValue() + "'> ");
			html.append("</div>");
		}

		return html.toString();
	}
}

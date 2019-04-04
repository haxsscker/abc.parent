package com.autoserve.abc.web.module.screen.invest.json;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.SystemGetPropeties;

/**
 * 类QueryRecord.java的实现描述：动态记载交易记录
 * 
 * @author WangMing 2015年5月13日 下午4:09:13
 */
public class QueryRecord {

    @Autowired
    private HttpSession        session;
    @Autowired
    private ToCashService      tocashservice;
    @Autowired
    private RechargeService    rechargeservice;
    @Autowired
    private InvestQueryService investQueryService;
    @Autowired
    private IncomePlanService  incomePlanService;
    @Resource
    private DealRecordService  dealrecordservice;
    @Resource
    private AccountInfoService accountinfoservice;

    //日期格式化
    private SimpleDateFormat   dateFormat;

    public String execute(Context context, ParameterParser params) {
        User user = (User) session.getAttribute("user");
        UserIdentity userIdentity = new UserIdentity();
        if (user.getUserType() == null || "".equals(user.getUserType()) || user.getUserType() == UserType.PERSONAL) {
            user.setUserType(UserType.PERSONAL);
        } else {
            user.setUserType(UserType.ENTERPRISE);
        }
        userIdentity.setUserType(user.getUserType());
        userIdentity.setUserId(user.getUserId());
        //空数据
        String startDate = null;
        String endDate = null;
        int pageSize = 30;
        int currentPage = params.getInt("currentPage") + 1;
        PageCondition pageCondition = new PageCondition(currentPage, pageSize);
        DealRecordDO dealrecorddo = new DealRecordDO();
        PlainResult<Account> account = accountinfoservice.queryByUserId(userIdentity);
        dealrecorddo.setDrPayAccount(account.getData().getAccountNo());
       // String platformAcount = SystemGetPropeties.getStrString("MerCustId");
        PageResult<DealRecordDO> result = dealrecordservice.queryDealByParams(dealrecorddo, pageCondition, startDate,
                endDate);
        String payAccount = account.getData().getAccountNo();
        StringBuffer html = new StringBuffer();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (DealRecordDO dr : result.getData()) {
            html.append("<tr>");
            html.append("<td class='wdjf1_td1'> <span class='c-jyjl-lei'>");
			if (dr.getDrType() == 0) {
                html.append("投资冻结");
            }else if (dr.getDrType() == 1) {
                html.append("撤投");
            } else if (dr.getDrType() == 2 && dr.getDrPayAccount().equals(payAccount)
                    && dr.getDrDetailType() == 9) {
                html.append("投资划转");
            }else if(dr.getDrType() == 2 && dr.getDrPayAccount().equals(payAccount) && dr.getDrDetailType()==5){
            	html.append("平台服务费");
            }
            else if (dr.getDrType() == 2 && dr.getDrPayAccount().equals(payAccount)
            		&& dr.getDrDetailType()==12) {
                html.append("担保服务费");
            } else if (dr.getDrType() == 2 && dr.getDrPayAccount().equals(payAccount)
            		&& dr.getDrDetailType()==14) {
                html.append("转让手续费");
            } else if (dr.getDrType() == 2 && dr.getDrReceiveAccount().equals(payAccount)) {
            	if(dr.getDrDetailType()==DealDetailType.DEBT_TRANSFER_MONEY.type){
            		html.append("转让");
				}else{
					if(dr.getDrDetailType() == 16){
						html.append("红包");
					}else{
						html.append("借款");
					}
				}
            }else if (dr.getDrType() == 2 && dr.getDrPayAccount().equals(payAccount)) {
            	html.append("转让");
			} else if (dr.getDrType() == 3 ) {
				if (dr.getDrPayAccount().equals(payAccount)
						&& dr.getDrDetailType() == 5) {
					html.append("平台服务费");
				}else if(dr.getDrPayAccount().equals(payAccount)&& dr.getDrDetailType() != 11){
            		html.append("还款");
				}else if(dr.getDrReceiveAccount().equals(payAccount)){
					html.append("收款");
				}	
            } else if (dr.getDrType() == 4) {
                html.append("充值");
            } else if (dr.getDrType() == 5) {
                html.append("提现");
            } else if (dr.getDrType() == 6) {
                html.append("退款");
            } else if (dr.getDrType() == 7) {
                html.append("收购");
            } else if (dr.getDrType() == 8) {
                html.append("流标");
            }else if (dr.getDrType() == 9) {
                html.append("自主转账");
            }else if (dr.getDrType() == 10) {
                html.append("红包返还");
            } else {
                html.append(dr.getDrType());
            }
            html.append("</span></td>");
            html.append("<td class='wdjf1_td2  text-center color_blue'> <span class='c-jyjl-money1'>");
            if (dr.getDrReceiveAccount().equals(payAccount)) {
                html.append("+" + dr.getDrMoneyAmount());
            } else  {
                html.append("-" + dr.getDrMoneyAmount());
            } 
            html.append("</span></td>");
            html.append("<td class='wdjf1_td3 text-center'><span>" + dateFormat.format(dr.getDrOperateDate())
                    + "</span></td>");
            html.append("</tr>");
        }
        return html.toString();
    }

}

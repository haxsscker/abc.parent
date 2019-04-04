package com.autoserve.abc.web.module.screen.invest.json;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.TocashRecordDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 类QueryRecord.java的实现描述：动态记载交易记录
 * 
 * @author WangMing 2015年5月13日 下午4:09:13
 */
public class QueryWithdrawal {

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
        System.out.println(currentPage);
        PageCondition pageCondition = new PageCondition(currentPage, pageSize);
        DealRecordDO dealrecorddo = new DealRecordDO();
        PlainResult<Account> account = accountinfoservice.queryByUserId(userIdentity);

        TocashRecordDO tocashrecorddo = new TocashRecordDO();
        tocashrecorddo.setTocashUserId(user.getUserId());
        PageResult<TocashRecordDO> result = tocashservice.queryListByUserId(tocashrecorddo, pageCondition, startDate,
                endDate);
        StringBuffer html = new StringBuffer();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (TocashRecordDO r : result.getData()) {
            html.append("<tr>");
            html.append("<td class='wdjf_td1  text-center'><span class='c-jyjl-money1'><span>" + "￥"
                    + r.getTocashAmount() + "</span></td>");
            html.append("<td class='wdjf_td1  text-center'><span class='c-jyjl-money1'><span>" + r.getTocashSeqNo()
                    + "</span></td>");
            html.append("<td class='wdjf_td1  text-center'><span class='c-jyjl-money1'>");
            if (r.getTocashState() == 0)
                html.append("充值中");
            else if (r.getTocashState() == 2)
                html.append("失败");
            else
                html.append("成功");
            html.append("</span></td>");
            html.append("<td class='col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right'><span>"
                    + dateFormat.format(r.getTocashDate()) + "</span></td>");
            html.append("</tr>");
            System.out.println(r.getTocashSeqNo());
        }
        return html.toString();

    }

}

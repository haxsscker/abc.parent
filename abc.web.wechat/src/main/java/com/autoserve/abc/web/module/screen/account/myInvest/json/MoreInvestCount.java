/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.account.myInvest.json;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.IncomePlanState;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.vo.JsonListVO;

/**
 * 资产统计-（已收收益，待收收益，已收本金，待收本金）加载列表
 * 
 * @author zhangkang 2015年6月3日 下午1:33:18
 */
public class MoreInvestCount {

    @Autowired
    private DeployConfigService deployConfigService;
    @Autowired
    private HttpSession         session;
    @Autowired
    private IncomePlanService   incomePlanService;
    @Resource
    private HttpServletRequest  request;

    public JsonListVO<String> execute(Context context, ParameterParser params, Navigator nav) {
        //登录URL
        User user = (User) session.getAttribute("user");
        if (user == null) {
            nav.forwardTo(deployConfigService.getLoginUrl(request));
            return null;
        }

        Integer currentPage = params.getInt("currentPage");
        Integer pageSize = params.getInt("pageSize");

        if (currentPage == 0)
            currentPage = 1;//默认情况
        else {
            currentPage++;
        }
        pageSize = 10;//默认情况
        PageCondition pageCondition = new PageCondition(currentPage, pageSize);
        IncomePlan incomePlan = new IncomePlan();
        incomePlan.setBeneficiary(user.getUserId());

        String menu = params.getString("menu");
        if ("yssy".equals(menu) || "ysbj".equals(menu)) {
            //已收收益，已收本金
            incomePlan.setIncomePlanState(IncomePlanState.CLEARED);
        } else if ("dssy".equals(menu) || "dsbj".equals(menu)) {
            //待收收益，待收本金
            incomePlan.setIncomePlanState(IncomePlanState.GOING);
        }

        PageResult<IncomePlan> pageResult = incomePlanService.queryIncomePlanList(incomePlan, null, pageCondition);
        List<IncomePlan> rechargeRecorDO = pageResult.getData();
        //分页处理

        String html = "";
        html = addHtml(rechargeRecorDO, menu);
        List<String> list = new ArrayList<String>();
        list.add(html);
        JsonListVO<String> jvo = new JsonListVO<String>();
        jvo.setRows(list);
        return jvo;
    }

    public String addHtml(List<IncomePlan> incomePlan, String menu) {
        StringBuffer html = new StringBuffer();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //rechargeRecordDOs=pageresult.getdata
        for (IncomePlan income : incomePlan) {
            html.append("<div class='row'>");
            html.append("<div class='col-xs-5 col-sm-5 col-md-5 col-lg-5'><span class='c-jyjl-lei'>"
                    + income.getLoanNo() + "</span></div>");
            html.append("<div class='col-xs-3 col-sm-3 col-md-3 col-lg-3'><span class='c-jyjl-money5'>+");
            if ("yssy".equals(menu)) {
                //已收收益
                html.append(income.getCollectInterest());
            } else if ("dssy".equals(menu)) {
                //待收收益
                html.append(income.getPayInterest().subtract(income.getCollectInterest()));
            } else if ("ysbj".equals(menu)) {
                //已收本金
                html.append(income.getCollectCapital());
            } else if ("dsbj".equals(menu)) {
                //待收本金
                html.append(income.getPayCapital().subtract(income.getCollectCapital()));
            }
            html.append("</span></div>");
            html.append("<div class='col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right'><span>"
                    + dateFormat.format(income.getPaytime()) + "</span></div>");
            html.append("</div>");

        }
        return html.toString();
    }
}

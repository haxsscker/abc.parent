package com.autoserve.abc.web.module.screen.invest.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.InvestSearchDO;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.module.screen.invest.InvestVO;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.util.Pagebean;

/**
 * 类GetMoreIsInvestJSON.java的实现描述：Ajax 翻页加载投资记录
 * 
 * @author WangMing 2015年5月18日 下午3:34:22
 */
public class GetMoreIsInvestJSON {

    @Resource
    private InvestQueryService investQueryService;
    @Resource
    private UserService        userService;

    public String execute(Context context, ParameterParser params) {

        StringBuffer html = new StringBuffer();

        Integer loanId = params.getInt("loanId");
        Integer flagLoan = params.getInt("flagLoan");
        int currentPage = params.getInt("currentPage") + 1;
        int pageSize = 10;

        PageCondition pageCondition = new PageCondition(currentPage, pageSize);

        InvestSearchDO investSearchDO = new InvestSearchDO();
        //投资状态为2:支付成功,4:待收益 5:被转让 6:被收购 7:收益完成
        investSearchDO.setInvestStates(Arrays.asList(InvestState.PAID.state, InvestState.EARNING.state,
                InvestState.TRANSFERED.state, InvestState.BUYED.state, InvestState.EARN_COMPLETED.state));

        if (flagLoan != null && flagLoan == 1) {
            investSearchDO.setBidId(loanId);
            investSearchDO.setBidType(BidType.COMMON_LOAN.getType());
        } else if (flagLoan != null && flagLoan == 2) {
            investSearchDO.setBidId(loanId);
            investSearchDO.setBidType(BidType.TRANSFER_LOAN.getType());
        }
        PageResult<Invest> invests = this.investQueryService.queryInvestList(investSearchDO, pageCondition);
        context.put("count", invests.getTotalCount()); //投资数                                     
        List<Invest> investList = invests.getData();
        List<InvestVO> investVOs = new ArrayList<InvestVO>();
        for (Invest invest : investList) {
            InvestVO investVO = new InvestVO();
            String userName = userService.findById(invest.getUserId()).getData().getUserName();
            investVO.setUserName(userName);
            String createTime = DateUtil.formatDate(invest.getCreatetime(), "yyyy-MM-dd HH:mm:ss");
            investVO.setCreatetime(createTime);
            investVO.setInvestState(invest.getInvestState());
            investVO.setInvestMoney(invest.getInvestMoney());
            investVOs.add(investVO);
        }
        //投资记录的分页
        Pagebean<InvestVO> pagebean = new Pagebean<InvestVO>(currentPage, pageSize, investVOs, invests.getTotalCount());
        html = addInvestRecord(pagebean.getRecordList());
        return html.toString();
    }

    private StringBuffer addInvestRecord(List<InvestVO> investVOs) {
        StringBuffer html = new StringBuffer();
        for (InvestVO iv : investVOs) {
            html.append("<dd class='clearfix font_s fc_52'>");
            html.append("<span class='pull-left' style='width:15%'>" + iv.getUserName() + "</span>");
            html.append("<span class='pull-right' style='width:25%'>" + iv.getInvestMoney() + "</span>");
            html.append("<span class='pull-left' style='width:40%'>" + iv.getCreatetime() + "</span>");
            html.append("<span class='pull-right' style='width:20%'>");
            if (iv.getInvestState().state == 2) {
                html.append("投资成功");
            } else {
                html.append(iv.getInvestState().prompt);
            }
            html.append("</span>");
            html.append("</dd>");
        }
        return html;
    }
}

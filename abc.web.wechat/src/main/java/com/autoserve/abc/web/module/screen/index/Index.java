package com.autoserve.abc.web.module.screen.index;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.BanelDO;
import com.autoserve.abc.dao.dataobject.search.LoanSearchDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.intf.article.ArticleInfoService;
import com.autoserve.abc.service.biz.intf.banel.BanelService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.Arith;

/**
 * @ClassName: Index
 * @Description: 加载微信网页首页
 * @author Wang Ming
 * @date 2015年5月11日 上午10:05:16
 */
public class Index {

    @Resource
    private HttpSession         session;
    @Resource
    private ArticleInfoService  articleInfoService;
    @Resource
    private LoanService         loanService;
    @Resource
    private InvestQueryService  investQueryService;
    @Resource
    private UserService         userService;
    @Resource
    private TransferLoanService transferLoanService;
    @Resource
    LoanQueryService            loanQueryService;
    @Resource
    IncomePlanService           incomePlanService;
    @Resource
    private BanelService        banelService;

    public void execute(Context context, ParameterParser params, Navigator nav) {

        // 获取用户信息
        User user = (User) session.getAttribute("user");
        context.put("user", user);

        // 首页Banel
        ListResult<BanelDO> banelResult = banelService.queryListByGroup("app");
        if (banelResult.isSuccess()) {
            context.put("banels", banelResult.getData());
        }

        // 分页
        int pageSize = 5;
        int currentPage = 1;
        PageCondition pageCondition = new PageCondition(currentPage, pageSize);
        int loanCategory = 0;
        //获得loanType
        if (null != params.getString("loanType")) {
            loanCategory = Integer.parseInt(params.getString("loanType"));
        }

        // 查询投资理财
        LoanSearchDO searchDO = new LoanSearchDO();
        searchDO.setLoanState(Arrays.asList(LoanState.BID_INVITING.state, LoanState.FULL_WAIT_REVIEW.state,
                LoanState.FULL_REVIEW_PASS.state, LoanState.FULL_REVIEW_FAIL.state, LoanState.BID_CANCELED.state,
                LoanState.MONEY_TRANSFERING.state, LoanState.REPAYING.state, LoanState.REPAY_COMPLETED.state));
        if (loanCategory != 0) {
            searchDO.setLoanCategory(loanCategory);
        }
        searchDO.setFlag(0);//排序
        PageResult<Loan> resultLoanList = this.loanQueryService.queryLoanListBySearchParam(searchDO, pageCondition);
        // 百分比
        Map<Integer, BigDecimal> resultLoanListMap = new HashMap<Integer, BigDecimal>();
        // 距离开始时间间隔
        Map<Integer, Long> duarStartTimeMap = new HashMap<Integer, Long>();
        // 距离结束时间间隔
        Map<Integer, Long> duarEndTimeMap = new HashMap<Integer, Long>();
        Long currTime = System.currentTimeMillis();
        context.put("resultLoanList", resultLoanList.getData());
        context.put("pages", resultLoanList);

        //存储标的类型
        Map<Integer, Object> periodTypeMap = new HashMap<Integer, Object>();

        for (Loan tempLoan : resultLoanList.getData()) {
        	BigDecimal percent = Arith.calcPercent(tempLoan.getLoanCurrentValidInvest(), tempLoan.getLoanMoney(), tempLoan.getLoanState());
            resultLoanListMap.put(tempLoan.getLoanId(), percent);
            duarStartTimeMap.put(tempLoan.getLoanId(), tempLoan.getLoanInvestStarttime().getTime() - currTime);
            duarEndTimeMap.put(tempLoan.getLoanId(), currTime - tempLoan.getLoanInvestEndtime().getTime());
            context.put("resultLoanListPercent", resultLoanListMap);
            context.put("duarStartTimeMap", duarStartTimeMap);
            context.put("duarEndTimeMap", duarEndTimeMap);
        }

        context.put("periodTypeMap", periodTypeMap);
        //标的类型加载
        context.put("loanTypes", LoanCategory.values());
        
        context.put("menu", "index");
    }
}

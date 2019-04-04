package com.autoserve.abc.web.module.screen.invest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchDO;
import com.autoserve.abc.dao.dataobject.search.RedSearchDO;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.Redsend;
import com.autoserve.abc.service.biz.entity.RedsendJ;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.RedState;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedsendService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.util.Pagebean;
import com.autoserve.abc.web.util.SafeUtil;

public class InvestRecord {

    @Resource
    private UserService        userService;
    @Resource
    private InvestQueryService investQueryService;
    @Autowired
    private HttpSession        session;
    @Resource
    LoanQueryService           loanQueryService;
    @Resource
    TransferLoanService        transferLoanService;
    @Autowired
    private RedsendService     redsendService;

    public void execute(Context context, Navigator nav, ParameterParser params) {

        Integer loanId = params.getInt("loanId");
        Integer transferId = params.getInt("transferId");
        Integer flagLoan = params.getInt("flagLoan");
        //投资记录标志

        // 准备分页数据
        int currentPage = params.getInt("currentPage");
        int pageSize = 200;
        if (currentPage == 0)
            currentPage = 1;
        PageCondition pageCondition = new PageCondition(currentPage, pageSize);

        PlainResult<Loan> resu = new PlainResult<Loan>();
        TransferLoan transferLoan = new TransferLoan();

        //判断用户是否登录
        User user = (User) session.getAttribute("user");
        //以下为该项目的详细信息
        Loan loan = new Loan();
        //普通标详情页
        if (loanId != null && loanId > 0 && flagLoan == 1) {
            loan.setLoanId(loanId);
            resu = this.loanQueryService.queryByParam(loan);
            resu.getData().getLoanFileUrl();

            if (user != null) {
                //普通标可以使用红包
                Redsend redsend = new Redsend();
                redsend.setRsUserid(user.getUserId());
                RedSearchDO redSearchDO = new RedSearchDO();
                PageCondition pageConditionx = new PageCondition(1, 65535);
                redSearchDO.setUserId(user.getUserId());
                redSearchDO.setUserScope(resu.getData().getLoanCategory().getPrompt());
                redSearchDO.setRsState(RedState.EFFECTIVE.getState());
                redSearchDO.setRsClosetime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                PageResult<RedsendJ> pageResult = redsendService.queryListJ(redSearchDO, pageConditionx);
                context.put("redSendList", pageResult.getData());
            }
        }

        //转让标详情页
        PlainResult<TransferLoan> result = null;
        if (transferId != null && transferId > 0 && flagLoan == 2) {
            transferLoan.setId(transferId);
            loanId = this.transferLoanService.queryByParam(transferLoan).getData().getOriginId();
            loan.setLoanId(loanId);
            resu = this.loanQueryService.queryByParam(loan);
            result = this.transferLoanService.queryByParam(transferLoan);

        }

        //查询投资该项目的投资人    
        InvestSearchDO investSearchDO = new InvestSearchDO();
        //投资状态为2:支付成功,4:待收益 5:被转让 6:被收购 7:收益完成
        investSearchDO.setInvestStates(Arrays.asList(InvestState.PAID.state, InvestState.EARNING.state,
                InvestState.TRANSFERED.state, InvestState.BUYED.state, InvestState.EARN_COMPLETED.state));

        investSearchDO.setLoanName(resu.getData().getLoanNo());
        if (flagLoan != null && flagLoan == 1) {
            investSearchDO.setBidId(resu.getData().getLoanId());
            investSearchDO.setBidType(BidType.COMMON_LOAN.getType());
        } else if (flagLoan != null && flagLoan == 2) {
            investSearchDO.setBidId(result.getData().getId());
            investSearchDO.setBidType(BidType.TRANSFER_LOAN.getType());
        }
        PageResult<Invest> invests = this.investQueryService.queryInvestList(investSearchDO, pageCondition);
        context.put("count", invests.getTotalCount()); //投资数                                     
        List<Invest> investList = invests.getData();
        List<InvestVO> investVOs = new ArrayList<InvestVO>();
        for (Invest invest : investList) {
            InvestVO investVO = new InvestVO();
            UserDO userDO = userService.findById(invest.getUserId()).getData();
            investVO.setUserName(userDO.getUserName() == null ? "" : userDO.getUserName());
            investVO.setUserPhone(SafeUtil.hideMobile(userDO.getUserPhone() == null ? "" : userDO.getUserPhone()));
            String createTime = DateUtil.formatDate(invest.getCreatetime(), "yyyy-MM-dd HH:mm:ss");
            investVO.setCreatetime(createTime);
            investVO.setInvestState(invest.getInvestState());
            investVO.setInvestMoney(invest.getInvestMoney());
            investVOs.add(investVO);
        }
        //投资记录的分页
        Pagebean<InvestVO> pagebean = new Pagebean<InvestVO>(currentPage, pageSize, investVOs, invests.getTotalCount());
        context.put("pagebean", pagebean);
        context.put("loanId", loanId);
    }
}

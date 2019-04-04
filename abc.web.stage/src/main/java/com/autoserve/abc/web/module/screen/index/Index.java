package com.autoserve.abc.web.module.screen.index;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.BanelDO;
import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.dao.dataobject.TransferLoanJDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.dataobject.search.LoanSearchDO;
import com.autoserve.abc.dao.dataobject.search.TransferLoanSearchDO;
import com.autoserve.abc.service.biz.entity.ArticleInfo;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
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
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.Arith;
import com.autoserve.abc.web.util.DateUtils;

/**
 * 首页
 * 
 * @author DS 2015下午5:01:17
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

    public void execute(Context context, ParameterParser params) {
        Long currTime = System.currentTimeMillis();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            context.put("user", user);
        }

        //首页Banel
        ListResult<BanelDO> banelResult = banelService.queryListByGroup("index");
        if (banelResult.isSuccess()) {
            context.put("banels", banelResult.getData());
        }

        //查询官方公告,不可妄动
        int pageSize = 5;
        int currentPage = 1;
        PageCondition pageCondition = new PageCondition(currentPage, pageSize);
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setAiClassId(22);
        PageResult<ArticleInfo> result = articleInfoService.queryArticleInfoListByParam(articleInfo, pageCondition);
        List<ArticleInfo> articleList = result.getData();
        List<ArticleInfo> ciffciarticles = new ArrayList<ArticleInfo>();
        for (ArticleInfo articleInfo2 : articleList) {
            if (articleInfo2.getAiIsTop().getType() == 1) {
                ciffciarticles.add(articleInfo2);//先添加置顶的文章
            }
        }
        for (ArticleInfo articleInfo2 : articleList) {
            if (articleInfo2.getAiIsTop().getType() == 0) {
                ciffciarticles.add(articleInfo2);//再添加非置顶的文章
            }
        }
        context.put("ciffciarticles", ciffciarticles);
        //查询网站动态
        articleInfo.setAiClassId(23);
        result = articleInfoService.queryArticleInfoListByParam(articleInfo, pageCondition);
        articleList = result.getData();
        List<ArticleInfo> webSitearticles = new ArrayList<ArticleInfo>();
        for (ArticleInfo articleInfo2 : articleList) {
            if (articleInfo2.getAiIsTop().getType() == 1) {
                webSitearticles.add(articleInfo2);//先添加置顶的文章
            }
        }
        for (ArticleInfo articleInfo2 : articleList) {
            if (articleInfo2.getAiIsTop().getType() == 0) {
                webSitearticles.add(articleInfo2);//再添加非置顶的文章
            }
        }
        context.put("webSitearticles", webSitearticles);

        //行业资讯
        articleInfo.setAiClassId(41);
        result = articleInfoService.queryArticleInfoListByParam(articleInfo, new PageCondition(1, 5));
        articleList = result.getData();
        List<ArticleInfo> industryInfos = new ArrayList<ArticleInfo>();
        for (ArticleInfo articleInfo2 : articleList) {
            if (articleInfo2.getAiIsTop().getType() == 1) {
                industryInfos.add(articleInfo2);//先添加置顶的文章
            }
        }
        for (ArticleInfo articleInfo2 : articleList) {
            if (articleInfo2.getAiIsTop().getType() == 0) {
                industryInfos.add(articleInfo2);//再添加非置顶的文章
            }
        }
        context.put("industryInfos", industryInfos);
        //查询媒体报道
        articleInfo.setAiClassId(24);
        result = articleInfoService.queryArticleInfoListByParam(articleInfo, pageCondition);
        articleList = result.getData();
        List<ArticleInfo> Mediarticles = new ArrayList<ArticleInfo>();
        for (ArticleInfo articleInfo2 : articleList) {
            if (articleInfo2.getAiIsTop().getType() == 1) {
                Mediarticles.add(articleInfo2);//先添加置顶的文章
            }
        }
        for (ArticleInfo articleInfo2 : articleList) {
            if (articleInfo2.getAiIsTop().getType() == 0) {
                Mediarticles.add(articleInfo2);//再添加非置顶的文章
            }
        }
        context.put("Mediarticles", Mediarticles);

        //平台累计成交额
        PlainResult<BigDecimal> turnover = loanService.queryTotal();
        context.put("turnover", turnover.getData());
        //为投资人赚取的收益
        PlainResult<BigDecimal> profit = incomePlanService.findIncome();
        context.put("profit", profit.getData());
        //投资人数
        PlainResult<Integer> number = userService.queryTotal();
        context.put("number", number.getData());
        //最新投标
        NewInvestVO newInvestVO = null;
//        List<Integer> ids = null;
        PageResult<Invest> invests = investQueryService.queryInvestList(new InvestSearchDO(), new PageCondition(0, 20));
        List<NewInvestVO> listNewInvestVO = new ArrayList<NewInvestVO>();
        for (int i = 0; i < invests.getData().size(); i++) {
            Integer userid = invests.getData().get(i).getUserId();
            PlainResult<UserDO> userdos = userService.findById(userid);
            
            newInvestVO = new NewInvestVO();
            newInvestVO.setName(userdos.getData().getUserRealName());
            newInvestVO.setCreatetime(invests.getData().get(i).getCreatetime());
            newInvestVO.setInvestMoney(invests.getData().get(i).getInvestMoney());
//            ids = new ArrayList<Integer>();
//            ids.add(invests.getData().get(i).getOriginId());
//            ListResult<Loan> listResult = loanService.queryByIds(ids, new InvestSearchJDO());
//            if (listResult.getData().size() != 0) {
//                newInvestVO.setLoanno(listResult.getData().get(0).getLoanNo());
//            }
            listNewInvestVO.add(newInvestVO);
        }
        context.put("listNewInvestVO", listNewInvestVO);
//        //投资风云榜
//        ListResult<InvestDO> billboard = investQueryService.queryMoneyByUser(new PageCondition(0, 5));
//        List<BillboardVO> listBillboardVO = new ArrayList<BillboardVO>();
//        for (int i = 0; i < billboard.getData().size(); i++) {
//            Integer userid = billboard.getData().get(i).getInUserId();
//            PlainResult<UserDO> userdos = userService.findById(userid);
//            BillboardVO billboardVO = new BillboardVO();
//            billboardVO.setNumber(i + 1);
//            billboardVO.setUsername(userdos.getData().getUserName());
//            billboardVO.setInInvestMoney(billboard.getData().get(i).getInInvestMoney());
//            listBillboardVO.add(billboardVO);
//
//        }
//        context.put("listBillboardVO", listBillboardVO);

        //查询投资理财
        LoanSearchDO searchDO = new LoanSearchDO();
        searchDO.setLoanState(Arrays.asList(LoanState.BID_INVITING.state, LoanState.FULL_WAIT_REVIEW.state,
                LoanState.FULL_REVIEW_PASS.state, LoanState.FULL_REVIEW_FAIL.state, LoanState.BID_CANCELED.state,
                LoanState.MONEY_TRANSFERING.state, LoanState.REPAYING.state, LoanState.REPAY_COMPLETED.state));
        searchDO.setFlag(0);
        searchDO.setIsShow(1);
        
        PageResult<Loan> resultLoanList = this.loanQueryService.queryLoanListBySearchParam(searchDO, pageCondition);
        //百分比
        Map<Integer, BigDecimal> resultLoanListMap = new HashMap<Integer, BigDecimal>();
        //距离开始时间间隔
        Map<Integer, Long> duarStartTimeMap = new HashMap<Integer, Long>();
        //距离结束时间间隔
        Map<Integer, Long> duarEndTimeMap = new HashMap<Integer, Long>();
       
        context.put("resultLoanList", resultLoanList.getData());
        for (Loan tempLoan : resultLoanList.getData()) {
        	BigDecimal percent = Arith.calcPercent(tempLoan.getLoanCurrentValidInvest(), tempLoan.getLoanMoney());
            resultLoanListMap.put(tempLoan.getLoanId(), percent);
            duarStartTimeMap.put(tempLoan.getLoanId(), tempLoan.getLoanInvestStarttime().getTime() - currTime);
            duarEndTimeMap.put(tempLoan.getLoanId(), currTime - tempLoan.getLoanInvestEndtime().getTime());
        }
        context.put("resultLoanListPercent", resultLoanListMap);
        context.put("duarStartTimeMap", duarStartTimeMap);
        context.put("duarEndTimeMap", duarEndTimeMap);

        //查询债权转让
        TransferLoanSearchDO pojo = new TransferLoanSearchDO();
        pojo.setTransferLoanStates(Arrays.asList(TransferLoanState.TRANSFERING.state,
                TransferLoanState.FULL_WAIT_REVIEW.state, TransferLoanState.FULL_REVIEW_PASS.state,
                TransferLoanState.FULL_REVIEW_FAIL.state, 
                TransferLoanState.MONEY_TRANSFERING.state, TransferLoanState.MONEY_TRANSFERED.state));

        PageResult<TransferLoanJDO> transferLoanList = transferLoanService.queryListByParam(pojo, pageCondition);
        context.put("transferLoanList", transferLoanList.getData());
        Map<Integer, BigDecimal> transferLoanListMap = new HashMap<Integer, BigDecimal>();
        for (TransferLoanJDO tempTransferLoan : transferLoanList.getData()) {
            BigDecimal percent = tempTransferLoan.getTlCurrentValidInvest()
                    .divide(tempTransferLoan.getTlTransferMoney(), 50, BigDecimal.ROUND_HALF_UP)
                    .multiply(new BigDecimal(100));
            transferLoanListMap.put(tempTransferLoan.getTlId(), percent);
            context.put("transferLoanListPercent", transferLoanListMap);
        }
        
        //优选推荐
        List<Loan> loanList = this.loanQueryService.queryOptimizationFy(1).getData();
    	Loan loan = loanList.size() > 0 ? loanList.get(0) : null;
    	
    	List<TransferLoanJDO> transferYxList = transferLoanService.queryTrOptimization(1).getData();
        TransferLoanJDO  tranLoan = transferYxList.size() > 0 ? transferYxList.get(0) : null;
        
        if (user != null)
        {
            //last loan type
        	int lastLoanType = this.loanQueryService.queryLastLoanType(user.getUserId());
            if (1 == lastLoanType && null != tranLoan)
            {
            	context.put("transferLoanYx", tranLoan);
                BigDecimal percent = tranLoan.getTlCurrentValidInvest()
                        .divide(tranLoan.getTlTransferMoney(), 50, BigDecimal.ROUND_HALF_UP)
                        .multiply(new BigDecimal(100));
                context.put("transferLoanYxPercent", percent);
            }
            else
            {
            	 context.put("resultLoan", loan);
                 context.put("resultLoanPercent", Arith.calcPercent(loan.getLoanCurrentValidInvest(), loan.getLoanMoney()));
                 context.put("resultLoanStartTime", (loan.getLoanInvestStarttime().getTime()-currTime)/1000);//投资开始时间距现在时间
                 context.put("resultLoanEndTime", currTime-loan.getLoanInvestEndtime().getTime());
            }
        }
        else
        {
            if(loan != null){
            	if(loan.getLoanState().state == LoanState.BID_INVITING.state
            			&& DateUtils.substractDay(loan.getLoanInvestEndtime(), new Date())>0
            			&& loan.getLoanMoney().subtract(loan.getLoanCurrentValidInvest()).doubleValue() > 0)
            	{
            		context.put("resultLoan", loan);
                    context.put("resultLoanPercent", Arith.calcPercent(loan.getLoanCurrentValidInvest(), loan.getLoanMoney()));
                    context.put("resultLoanStartTime", (loan.getLoanInvestStarttime().getTime()-currTime)/1000);//投资开始时间距现在时间
                    context.put("resultLoanEndTime", currTime-loan.getLoanInvestEndtime().getTime());
            	}else{
                	//add by xiatt 20160630 优选推荐没找到，则找优选债券
                	if(tranLoan != null)
                	{
                		context.put("transferLoanYx", tranLoan);
                        BigDecimal percent = tranLoan.getTlCurrentValidInvest()
                                .divide(tranLoan.getTlTransferMoney(), 50, BigDecimal.ROUND_HALF_UP)
                                .multiply(new BigDecimal(100));
                        context.put("transferLoanYxPercent", percent);
                	}else{
                        context.put("resultLoan", loan);
                        context.put("resultLoanPercent", Arith.calcPercent(loan.getLoanCurrentValidInvest(), loan.getLoanMoney()));
                        context.put("resultLoanStartTime", (loan.getLoanInvestStarttime().getTime()-currTime)/1000);//投资开始时间距现在时间
                        context.put("resultLoanEndTime", currTime-loan.getLoanInvestEndtime().getTime());
                	}
                }
                //end add
            }
        }
        
        //新华品牌
        articleInfo.setAiClassId(25);
        List<ArticleInfo> listBrand = this.queryArticleInfo(articleInfo);
        if (listBrand != null && listBrand.size() != 0) {
            context.put("listBrand", listBrand);
        }
        //合作伙伴
        articleInfo.setAiClassId(43);
        List<ArticleInfo> listPartners = this.queryArticleInfo(articleInfo);
        if (listPartners != null && listPartners.size() != 0) {
            context.put("listPartners", listPartners);
        }
    }

    //查询文章列表
    private List<ArticleInfo> queryArticleInfo(ArticleInfo articleInfo) {
        ListResult<ArticleInfo> listBrandResult = this.articleInfoService.queryArticleInfoListByParam(articleInfo);
        return listBrandResult.getData();
    }
}

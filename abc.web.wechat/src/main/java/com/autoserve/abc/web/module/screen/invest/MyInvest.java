package com.autoserve.abc.web.module.screen.invest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.FileUploadInfoDO;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchDO;
import com.autoserve.abc.dao.dataobject.search.RedSearchDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.Redsend;
import com.autoserve.abc.service.biz.entity.RedsendJ;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserCompany;
import com.autoserve.abc.service.biz.entity.UserEducation;
import com.autoserve.abc.service.biz.entity.UserHouse;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.entity.UserOwner;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.FileUploadSecondaryClass;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.RedState;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.UserBusinessState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedsendService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.upload.FileUploadInfoService;
import com.autoserve.abc.service.biz.intf.user.UserCompanyService;
import com.autoserve.abc.service.biz.intf.user.UserEducationService;
import com.autoserve.abc.service.biz.intf.user.UserHouseService;
import com.autoserve.abc.service.biz.intf.user.UserOwnerService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.SystemGetPropeties;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.module.screen.Verification.json.CheckMoneyMoreMore;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.util.MathUtil;
import com.autoserve.abc.web.util.Pagebean;

public class MyInvest {
    @Resource
    private LoanService           loanService;
    @Resource
    private UserService           userService;
    @Resource
    private InvestQueryService    investQueryService;
    @Autowired
    private HttpSession           session;
    @Resource
    private HttpServletRequest    request;
    @Resource
    private GovernmentService     governmentService;
    @Resource
    private AccountInfoService    accountInfoService;
    @Resource
    private DeployConfigService   deployConfigService;
    @Resource
    private UserHouseService      userHouseService;
    @Resource
    LoanQueryService              loanQueryService;
    @Resource
    CheckMoneyMoreMore            checkMoneyMoreMore;
    @Resource
    TransferLoanService           transferLoanService;
    @Autowired
    private UserHouseService      userhouseservice;
    @Autowired
    private UserCompanyService    usercompanyservice;
    @Autowired
    private UserOwnerService      userownerservice;
    @Autowired
    private UserEducationService  usereducationservice;
    @Autowired
    private RedsendService        redsendService;
    @Autowired
    private FileUploadInfoService fileUploadInfoService;
    @Resource
    private HuifuPayService       huiFuPayServcice;
    @Resource
    private InvestService         investService;
    @Autowired
    private SysConfigService      sysConfigService;

    public void execute(Context context, Navigator nav, ParameterParser params) {

        Integer loanId = params.getInt("loanId");
        Integer transferId = params.getInt("transferId");
        Integer flagLoan = params.getInt("flagLoan");
        //投资记录标志
        String tzxmFlag = params.getString("tzxm");

        // 准备分页数据
        int currentPage = params.getInt("currentPage");
        int pageSize = params.getInt("pageSize");
        if (currentPage == 0)
            currentPage = 1;
        pageSize = 5;
        PageCondition pageCondition = new PageCondition(currentPage, pageSize);

        PlainResult<Loan> resu = new PlainResult<Loan>();
        TransferLoan transferLoan = new TransferLoan();

        //判断用户是否登录
        User user = (User) session.getAttribute("user");
        if (user != null) { //用户登录,可见部分详情
            context.put("username", user.getUserName());
            if (user.getUserBusinessState() != UserBusinessState.REGISTERED && user.getUserBusinessState() != null) {
                UserIdentity userIdentity = new UserIdentity();
                userIdentity.setUserId(user.getUserId());

                //判断用户属于个人用户还是企业用户
                if (user.getUserType() == null || user.getUserType().getType() == 1) {
                    user.setUserType(UserType.PERSONAL);
                    context.put("userType", UserType.PERSONAL.getType());
                } else {
                    user.setUserType(UserType.ENTERPRISE);
                    context.put("userType", UserType.ENTERPRISE.getType());
                }
                userIdentity.setUserType(user.getUserType());
            }
            PlainResult<UserDO> userDO = this.userService.findById(user.getUserId());
            context.put("user", userDO.getData());
        } else {
        	nav.redirectToLocation(deployConfigService.getLoginUrl(request));
            return;
        }
        /******************************** 公共部分 ***********************************************/
        //以下为该项目的详细信息
        Loan loan = new Loan();
        String loanFileUrl = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currenttime = sdf.format(new Date());
        //普通标详情页
        if (loanId != null && loanId > 0 && flagLoan == 1) {
            loan.setLoanId(loanId);
            resu = this.loanQueryService.queryByParam(loan);
            loanFileUrl = resu.getData().getLoanFileUrl();
            resu.getData().getLoanFileUrl();
            context.put("loanCreatetime", DateUtil.formatDate(resu.getData().getLoanCreatetime()));
            context.put("loanInvestEndtime", DateUtil.formatDate(resu.getData().getLoanInvestEndtime()));
            context.put("currenttime", currenttime);
            context.put("sdf", sdf);
            context.put("loan", resu.getData());
            context.put("loanNO", resu.getData().getLoanNo());
            context.put("loanUse", resu.getData().getLoanUse());
            context.put("loanMoney", resu.getData().getLoanMoney());
            context.put("loanRate", resu.getData().getLoanRate());
            context.put("loanPeriod", resu.getData().getLoanPeriod());
            context.put("loanPeriodUnit", resu.getData().getLoanPeriodUnit().getUnit());
            context.put("loanCurrentInvest", resu.getData().getLoanCurrentValidInvest());
            context.put(
                    "loanCurrentInvestPercent",
                    resu.getData().getLoanCurrentValidInvest()
                            .divide(resu.getData().getLoanMoney(), 50, BigDecimal.ROUND_HALF_UP)
                            .multiply(new BigDecimal(100)));
            context.put("canInvestMoney",
                    resu.getData().getLoanMoney().subtract(resu.getData().getLoanCurrentValidInvest()));
            context.put("loan", resu.getData());

            long loanInvestEndtime = resu.getData().getLoanInvestEndtime().getTime();
            long loanInvestStarttime = resu.getData().getLoanInvestStarttime().getTime();
            long nowTime = new Date().getTime();
            long durStartTime = loanInvestStarttime - nowTime; //距离投标开始时间
            long durEndTime = loanInvestEndtime - nowTime; //距离投标结束的时间
            context.put("durStartTime", durStartTime);
            context.put("durEndTime", durEndTime);

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

            loanFileUrl = resu.getData().getLoanFileUrl();
            result = this.transferLoanService.queryByParam(transferLoan);
            context.put("loanCreatetime", DateUtil.formatDate(result.getData().getCreatetime()));
            context.put("loanInvestEndtime", DateUtil.formatDate(result.getData().getInvestEndtime()));
            context.put("loan", resu.getData());
            context.put("transferLoan", result.getData());
            context.put("loanNO", resu.getData().getLoanNo());
            context.put("loanUse", resu.getData().getLoanUse());
            context.put("loanMoney", result.getData().getTransferMoney());
            context.put("loanRate", result.getData().getTransferRate());
            context.put("loanPeriod", result.getData().getTransferPeriod());

            long end = resu.getData().getLoanExpireDate().getTime();
            long now = System.currentTimeMillis();
            context.put("timelimit", (end - now) / (1000 * 60 * 60 * 24));

            context.put("loanPeriodUnit", resu.getData().getLoanPeriodUnit().getUnit());
            context.put("loanCurrentInvest", result.getData().getCurrentValidInvest());
//            context.put(
//                    "loanCurrentInvestPercent",
//                    result.getData().getCurrentValidInvest()
//                            .divide(result.getData().getTransferMoney(), 50, BigDecimal.ROUND_HALF_UP)
//                            .multiply(new BigDecimal(100)));
            context.put("transferloan", result.getData());
            context.put("canInvestMoney",
                    result.getData().getTransferMoney().subtract(result.getData().getCurrentValidInvest()));
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
        context.put("pagebean", pagebean);
        if (tzxmFlag != null && !"".equals(tzxmFlag)) {
            context.put("tzxmFlag", tzxmFlag);
        }

        // 10000元 每天多少钱
        double perDayMoney = MathUtil.div(
                MathUtil.mul(10000, MathUtil.div(resu.getData().getLoanRate().doubleValue(), 100, 4)), 365, 10);
        // 总共多少钱
        int days = 0;
        if (resu.getData().getLoanPeriodUnit().getUnit() == 1) { // 年
            days = resu.getData().getLoanPeriod() * 365;
        } else if (resu.getData().getLoanPeriodUnit().getUnit() == 2) { // 月
            days = resu.getData().getLoanPeriod() * 30;
        } else { // 日
            days = resu.getData().getLoanPeriod();
        }
        double income = MathUtil.round(MathUtil.mul(perDayMoney, days), 2);
        context.put("income", income);

        //查询借款人信息
        PlainResult<UserDO> loanUserDO = this.userService.findById(resu.getData().getLoanUserId());
        context.put("loanUser", loanUserDO.getData());
        //2、房產資料：
        PlainResult<UserHouse> userhouse = userhouseservice.findUserHouseByUserId(resu.getData().getLoanUserId());
        //3、單位資料：
        PlainResult<UserCompany> usercompany = usercompanyservice.queryUserCompanyByUserId(resu.getData()
                .getLoanUserId());
        //4、私營業主資料：
        PlainResult<UserOwner> userowner = userownerservice.findUserOwnerByUserId(resu.getData().getLoanUserId());
        //6、教育背景：
        PlainResult<UserEducation> usereducation = usereducationservice.findUserEducationByUserId(resu.getData()
                .getLoanUserId());

        context.put("userhouse", userhouse);
        context.put("usercompany", usercompany);
        context.put("userowner", userowner);
        context.put("usereducation", usereducation);

        //查询担保机构
        PlainResult<GovernmentDO> loanGuarGov = this.governmentService.findById(resu.getData().getLoanGuarGov());
        context.put("loanGuarGov", loanGuarGov.getData());

        /**
         * IMAGE_DATA(1, "影像资料"), GUA_DATA(2, "担保机构"), QUA_DATA(3, "企业实地"),
         * SPOT_DATA(4, "企业资质"), OTHER_DATA(5, "其他"), SAFE_DATA(6, "风控资料");
         */

        //查询相关图片
        Map<String, List<FileUploadInfoDO>> picGroup = new HashMap<String, List<FileUploadInfoDO>>();
        picGroup.put("guas",
                fileUploadInfoService.findListByFileUrl(loanFileUrl, FileUploadSecondaryClass.GUA_DATA.getType()));
        picGroup.put("quas",
                fileUploadInfoService.findListByFileUrl(loanFileUrl, FileUploadSecondaryClass.QUA_DATA.getType()));
        picGroup.put("spots",
                fileUploadInfoService.findListByFileUrl(loanFileUrl, FileUploadSecondaryClass.SPOT_DATA.getType()));
        picGroup.put("others",
                fileUploadInfoService.findListByFileUrl(loanFileUrl, FileUploadSecondaryClass.IMAGE_DATA.getType()));
        picGroup.put("safes",
                fileUploadInfoService.findListByFileUrl(loanFileUrl, FileUploadSecondaryClass.SAFE_DATA.getType()));

        context.put("picGroup", picGroup);

        //参数回传
        context.put("flagLoan", flagLoan);
        context.put("loanId", loanId);
        context.put("transferId", transferId);

        //返还红包基数：
//        PlainResult<SysConfig> sysConfigInfo = sysConfigService.querySysConfig(SysConfigEntry.RED_SPLIT_BASE);
//        SysConfig sysConfig = sysConfigInfo.getData();
//        context.put("baseSum", sysConfig.getConfValue());

    }
}

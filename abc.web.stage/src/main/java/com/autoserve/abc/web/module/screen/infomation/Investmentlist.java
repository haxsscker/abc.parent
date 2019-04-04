package com.autoserve.abc.web.module.screen.infomation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.intf.article.ArticleInfoService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.module.screen.index.BillboardVO;
import com.autoserve.abc.web.util.Pagebean;

public class Investmentlist {
    @Autowired
    private HttpSession        session;
    @Resource
    private UserService        userService;
    @Resource
    private ArticleInfoService articleInfoService;
    @Resource
    private InvestQueryService investQueryService;

    public void execute(Context context, ParameterParser params) {
        String time = params.getString("time");
        int currentPage = params.getInt("currentPage");
        if (currentPage == 0)
            currentPage = 1;
        int pageSize = 5;
        PageCondition pageCondition = new PageCondition(currentPage, pageSize);
        //投资风云榜
        PageResult<InvestDO> billboard = investQueryService.investmentList(time, pageCondition);
        List<BillboardVO> listBillboardVO = new ArrayList<BillboardVO>();
        int j = 0;
        if (currentPage != 1 && currentPage != 0) {
            j = (currentPage - 1) * pageSize;
        }
        for (int i = 0; i < billboard.getData().size(); i++) {
            Integer userid = billboard.getData().get(i).getInUserId();
            PlainResult<UserDO> userdos = userService.findById(userid);
            BillboardVO billboardVO = new BillboardVO();
            billboardVO.setNumber(j + 1);
            j++;
            billboardVO.setUsername(userdos.getData().getUserName());
            billboardVO.setInInvestMoney(billboard.getData().get(i).getInInvestMoney());
            listBillboardVO.add(billboardVO);
        }
        Pagebean<BillboardVO> pagebean = new Pagebean<BillboardVO>(currentPage, pageSize, listBillboardVO,
                billboard.getTotalCount());
        context.put("pagebean", pagebean);
        context.put("time", time);
    }
}

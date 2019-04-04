package com.autoserve.abc.web.module.screen.account.myAward;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.RedSearchDO;
import com.autoserve.abc.service.biz.entity.RedsendJ;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.RsState;
import com.autoserve.abc.service.biz.intf.redenvelope.RedsendService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.Pagebean;

/**
 * @author DS 2015下午3:13:08
 */
public class MyRedPaper {
    @Autowired
    private HttpSession    session;
    @Resource
    private RedsendService redSendService;

    public void execute(Context context, ParameterParser params) {

        User user = (User) session.getAttribute("user");

        int currentPage = params.getInt("currentPage");
        if (currentPage == 0)
            currentPage = 1;
        int pageSize = 10;
        PageCondition pageCondition = new PageCondition(currentPage, pageSize);
        RedSearchDO redSearchDO = new RedSearchDO();
        redSearchDO.setUserId(user.getUserId());
        redSearchDO.setOrder("FIELD(rs_state, 1, 2, 0), rs_closetime ASC");//排序按未使用、已使用、已过期       红包过期时间升序
        PageResult<RedsendJ> RedsendJPageResult = redSendService.queryListJ(redSearchDO, pageCondition);
        List<RedsendJ> redsendJList = RedsendJPageResult.getData();
        //判断红包是否过期
//        for (RedsendJ redsendJ : redsendJList) {
//            if (redsendJ.getRsClosetime().getTime() < new Date().getTime()
//                    && redsendJ.getRsState() == RsState.WITHOUT_USE) {
//                redsendJ.setRsState(RsState.FAILURE);
//            }
//
//        }

        //计算红包总额
        /*
         * double hbze = 0; for (RedsendJ redsendJ : redsendJList) { hbze +=
         * redsendJ.getRsValidAmount(); } context.put("hbze", hbze);
         */
        //查询可用红包总额
        //查询可用红包数量
        RedSearchDO isHaveRedSearchDO = new RedSearchDO();
        isHaveRedSearchDO.setUserId(user.getUserId());
        isHaveRedSearchDO.setRsState(RsState.WITHOUT_USE.getState());
        BigDecimal isHaveCount = new BigDecimal(redSendService.queryRedAmount(isHaveRedSearchDO));
        context.put("isHaveCount", isHaveCount);

        Pagebean<RedsendJ> pagebean = new Pagebean<RedsendJ>(currentPage, pageSize, redsendJList,
                RedsendJPageResult.getTotalCount());
        context.put("pagebean", pagebean);

    }
}

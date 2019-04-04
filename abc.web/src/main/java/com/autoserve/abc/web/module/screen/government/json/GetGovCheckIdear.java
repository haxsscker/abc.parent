package com.autoserve.abc.web.module.screen.government.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.entity.GovUpdateHistory;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.web.convert.GovUpdateHistoryVOConverter;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.government.GovUpdateHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author RJQ 2014/12/19 18:06.
 */
public class GetGovCheckIdear {
    @Autowired
    private GovernmentService governmentService;

    public JsonPageVO<GovUpdateHistoryVO> execute(ParameterParser params) {
        //分页查询条件
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);

        Integer govId = params.getInt("govId");
        JsonPageVO<GovUpdateHistoryVO> pageVO = new JsonPageVO<GovUpdateHistoryVO>();
        List<GovUpdateHistory> histories = governmentService.queryReviewOpLogByGovId(govId, pageCondition).getData();
        List<GovUpdateHistoryVO> vos = GovUpdateHistoryVOConverter.convertToList(histories);
        pageVO.setTotal(vos.size());
        pageVO.setRows(vos);
        return pageVO;
    }
}


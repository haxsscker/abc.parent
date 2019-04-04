package com.autoserve.abc.web.module.screen.redrewardmanage.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RedsendJDO;
import com.autoserve.abc.dao.dataobject.search.RedSearchDO;
import com.autoserve.abc.service.biz.intf.redenvelope.RedsendService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * web层 个人奖励红包查看
 * 
 * @author fangrui 2015年1月4日 下午8:48:26
 */
public class PersonalRewardLookUpDataView {
    @Resource
    private RedsendService redsendService;
    private static Logger  logger = LoggerFactory.getLogger(PersonalRewardLookUpDataView.class);

    public JsonPageVO<RedsendJDO> execute(ParameterParser params) {
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        Integer redId = params.getInt("redId");
        PageCondition pageCondition = new PageCondition(page, rows);
        logger.info("Redsend list rows: " + rows + ", page: " + page + ", rsRedId:" + redId);

        RedSearchDO redSearchDO = new RedSearchDO();
        redSearchDO.setRsRedId(redId);
        PageResult<RedsendJDO> pageResult = redsendService.queryListJDO(redSearchDO, pageCondition);

        return ResultMapper.toPageVO(pageResult);
    }
}

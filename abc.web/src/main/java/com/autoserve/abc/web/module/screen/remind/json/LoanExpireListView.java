package com.autoserve.abc.web.module.screen.remind.json;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.LoanSearchDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.remind.RemindService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.module.screen.projectmanage.json.AbstractLoanProjectListView;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.projectmanage.LoanVO;

/**
 * 项目到期提醒
 */
public class LoanExpireListView extends AbstractLoanProjectListView {
	
    @Resource
    private RemindService remindService;
    @Resource
    private SysConfigService sysConfigService;
    

    private static Logger   logger = LoggerFactory.getLogger(LoanExpireListView.class);

    public JsonPageVO<LoanVO> execute (ParameterParser params, @Param("page") int page, @Param("rows") int rows) {
        String searchForm = params.getString("SearchForm");

        PlainResult<SysConfig> result = sysConfigService.querySysConfig(SysConfigEntry.EXPIRE_REMIND);
        
        // 到期提醒天数
        Integer expireDay =  Integer.valueOf(result.getData().getConfValue());
        
        PageCondition pageCondition = new PageCondition(page, rows);
        
        // 搜索参数
        LoanSearchDO searchDO = assembleLoanSearchParamIfNecessary(params);
        
        PageResult<Loan> pageResult = remindService.loanExpireList(searchDO,expireDay, pageCondition);
        List<LoanVO> convertedRows = convertLoanVOFields(pageResult);

        return ResultMapper.toPageVO(pageResult, convertedRows);
    }

}

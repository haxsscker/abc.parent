package com.autoserve.abc.web.module.screen.cashsq.json;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CashSqDO;
import com.autoserve.abc.service.biz.intf.cashsq.CashSqService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;

public class CashSqListView {
	 private static Logger logger = LoggerFactory.getLogger(CashSqListView.class);

	    @Resource
	    private UserService   userService;
	    @Resource
	    private CashSqService   cashSqService;
	    public JsonPageVO<CashSqDO> execute(ParameterParser params) {
	    	logger.info("查询免费提现额度申请");
	        Integer rows = params.getInt("rows");
	        Integer page = params.getInt("page");
	        PageCondition pageCondition = new PageCondition(page, rows);
	        CashSqDO cashSqDO = new CashSqDO();
 
	        PageResult<CashSqDO> pageResult = cashSqService.queryListCashSq(cashSqDO, pageCondition);
	        
	      
	        
	        return ResultMapper.toPageVO(pageResult);
	    }
}

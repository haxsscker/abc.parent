package com.autoserve.abc.web.module.screen.moneyManage.json;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.TransferLoanDO;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.TransferLoanDao;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.message.deposit.service.ContractGenerationService;
import com.autoserve.abc.web.vo.JsonBaseVO;
/**
 * 生成项目合同
 * @author Administrator
 *
 */
public class GenerateProContract {
	private final static Logger logger = LoggerFactory.getLogger(GenerateProContract.class);
	@Resource
	private ContractGenerationService contractGenerationService;
	@Resource
    private LoanDao                 loanDao;
    @Resource
    private TransferLoanDao         transferLoanDao;
	
	public JsonBaseVO execute(ParameterParser params, @Param("loanId") int loanId,
			@Param("bidType") int bidType, @Param("isSendMail") boolean isSendMail,Context context) {
		logger.info("--------------------------后台生成项目合同及存证--------------------------");
		logger.info("loanId=={},bidType=={},isSendMail=={}",loanId,bidType,isSendMail);
		JsonBaseVO jsonBaseVO = new JsonBaseVO();
		BaseResult result = null;
		if(BidType.COMMON_LOAN.getType()==bidType){//普通标
			LoanDO loanDo = loanDao.findByLoanId(loanId);
			result = contractGenerationService.investContractGeneration(loanId, loanDo.getLoanNo(), isSendMail);
		}else if(BidType.TRANSFER_LOAN.getType()==bidType){//转让标
			TransferLoanDO loanDo = transferLoanDao.findById(loanId);
			result = contractGenerationService.transContractGeneration(loanId, loanDo.getTlLoanNo(), isSendMail);
		}
		if(!result.isSuccess()){
			jsonBaseVO.setMessage(result.getMessage());
            jsonBaseVO.setSuccess(false);
            return jsonBaseVO;
		}
		return jsonBaseVO;
	}
}

package com.autoserve.abc.service.biz.impl.cash;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.enums.CardStatus;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.TiedCardService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
@Service
public class TiedCardServiceImpl implements TiedCardService {

	@Resource
	private BankInfoService bankInfoService;
	/**
	 * 绑卡
	 */
	@Override
	public BaseResult tiedCard(BankInfo bankInfo) {
		Integer userId = bankInfo.getBankUserId();
		BaseResult cardresult = new BaseResult();
		PlainResult<BankInfoDO> bankResult = bankInfoService.queryBankInfo(userId.toString(), bankInfo.getBankNo());
		//若卡已存在 但是处于失效状态
		if (bankResult.isSuccess() && bankResult.getData().getCardStatus() == 0) {
			bankInfo = new BankInfo();
			bankInfo.setBankId(bankResult.getData().getBankId());
			bankInfo.setCardStatus(CardStatus.STATE_ENABLE);
             cardresult = bankInfoService.modifyBankInfo(bankInfo);
        }
		//若卡存在且处于绑定状态
		else if(bankResult.isSuccess() && bankResult.getData().getCardStatus() == 1){
        	cardresult.setSuccess(false);
        	cardresult.setMessage("该银行卡已绑定!");
        }
		//卡不存在 先更新这个用户下的卡为失效状态  然后再绑定新卡
		else{
        	ListResult<BankInfoDO>  listResult = bankInfoService.queryListBankInfo(userId);
        	if(!listResult.getData().isEmpty()){
        		BankInfo updateBank = new BankInfo();
        		updateBank.setBankUserId(userId);
        		updateBank.setCardStatus(CardStatus.STATE_DISABLE);
        		bankInfoService.modifyBankInfoByUserId(bankInfo);
        	}
        	bankInfo.setCardStatus(CardStatus.STATE_ENABLE);
        	cardresult = bankInfoService.createBankInfo(bankInfo);
        }
		return cardresult;
	}

	@Override
	public BaseResult removeTiedCard(BankInfo bankInfo) {
		PlainResult<BankInfoDO> bankResult = bankInfoService.queryBankInfo(bankInfo.getBankUserId().toString(), bankInfo.getBankNo());
		bankInfo = new BankInfo();
		bankInfo.setBankId(bankResult.getData().getBankId());
		bankInfo.setCardStatus(CardStatus.STATE_DISABLE);
		BaseResult  cardresult = bankInfoService.modifyBankInfo(bankInfo);
		return cardresult;
	}

}

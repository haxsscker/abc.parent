package com.autoserve.abc.web.module.screen.reportAnalysis.json;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.druid.util.StringUtils;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.module.screen.BaseController;

public class GuaranteeAgenciesFundsDetailsExcel extends BaseController{
	@Resource
	private DealRecordService dealrecordservice;
	@Autowired
	private AccountInfoService accountInfoService;
	
	public void execute(ParameterParser params){
		String accountNo = params.getString("accountNo");
		if(!StringUtils.isEmpty(accountNo)){
			AccountInfoDO  accountInfo = accountInfoService.queryByAccountNo(accountNo);
			accountNo = accountInfo.getAccountNo();
			DealRecordDO dealrecorddo = new DealRecordDO();
		    if(null==accountInfo.getAccountNo() || accountInfo.getAccountNo().length()<=0){
		    	dealrecorddo.setDrPayAccount("");
		    }else{
		    	dealrecorddo.setDrPayAccount(accountInfo.getAccountNo());
		    }
			SZMXRecord(dealrecorddo, accountNo, null, null);
		}
	}
	
	//收支明细
    public void SZMXRecord(DealRecordDO dealrecorddo,String accountNo, String startDate, String endDate){
		PageResult<DealRecordDO> result = dealrecordservice.queryDealByParams(dealrecorddo,
				new PageCondition(0,dealrecordservice.queryDealByParams(dealrecorddo,new PageCondition(), startDate, endDate).getTotalCount()), startDate, endDate);		
		List<String> fieldName=Arrays.asList(new String[]{"交易日期","交易订单号","交易金额", "交易对方", "交易类型", "状态"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for(DealRecordDO dealrecord:result.getData()){
			List<String> temp = new ArrayList<String>();
			temp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dealrecord.getDrOperateDate()));
			temp.add(dealrecord.getDrInnerSeqNo());	
			//交易金额
			switch(dealrecord.getDrType()){
			case 0://投资冻结
				temp.add("-"+dealrecord.getDrMoneyAmount().toString());
				break;
			case 2:
			case 3:
				if(dealrecord.getDrPayAccount().equals(accountNo)){
					temp.add("-"+dealrecord.getDrMoneyAmount().toString());
				}else if(dealrecord.getDrReceiveAccount().equals(accountNo)){
					temp.add(dealrecord.getDrMoneyAmount().toString());
				}
				break;
			case 6:
				temp.add(dealrecord.getDrMoneyAmount().toString());
				break;
			case 7:
				temp.add("-"+dealrecord.getDrMoneyAmount().toString());
				break;
			case 8:
				temp.add(dealrecord.getDrMoneyAmount().toString());
				break;
			case 10://红包返还
				temp.add(dealrecord.getDrMoneyAmount().toString());
				break;
			default:temp.add("-");
			}
			//交易对方
			if((dealrecord.getDrType()==2 && accountNo.equals(dealrecord.getDrReceiveAccount())) ||
					(dealrecord.getDrType()==3 && accountNo.equals(dealrecord.getDrReceiveAccount()))
					|| (dealrecord.getDrType()==10 && accountNo.equals(dealrecord.getDrReceiveAccount()))){
				temp.add(dealrecord.getDrPayAccount());
			}else if(dealrecord.getDrType()==9){ // 自主转账
	         	if(accountNo.equals(dealrecord.getDrPayAccount())){
	         		temp.add(dealrecord.getDrReceiveAccount());
	         	}else{
	         		temp.add(dealrecord.getDrPayAccount());
	         	}
			}else{
				temp.add(dealrecord.getDrReceiveAccount());
			}
			//类型
			dealrecord.createDrTypeStr(accountNo);
			temp.add(dealrecord.getDrTypeStr());
			switch(dealrecord.getDrState()){
			case 0:temp.add("进行中");
			break;
			case 1:temp.add("成功");
			break;
			default:temp.add("失败");
			}
			fieldData.add(temp);
		}
		ExportExcel(fieldName,fieldData,"收支明细.xls");
    }
}

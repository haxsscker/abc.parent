package com.autoserve.abc.web.module.screen.moneyManage;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.UserAccountService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.helper.LoginUserInfo;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;
import com.autoserve.abc.web.vo.JsonPageVO;

public class PlateAccountView {
	private static final Logger logger = LoggerFactory
			.getLogger(PlateAccountView.class);
	    @Resource
	    private UserAccountService userAccountService;
	    @Resource
	    private AccountInfoService accountService;
	    @Resource
	    private DoubleDryService doubleDryService;
	    @Resource
		private InvestQueryService investQueryService;
	    @Resource
	    private DealRecordService          dealRecordService;

	    public JsonPageVO<DealRecordDO> execute(ParameterParser params,Context context) throws Exception {
	    	JsonPageVO<DealRecordDO> resultVO = new JsonPageVO<DealRecordDO>();
			Integer rows = params.getInt("rows");
			Integer page = params.getInt("page");
			PageCondition pageCondition = new PageCondition(page, rows);
			
	    	Double AcctBal = 0d;//账面总余额
	        Double AvlBal = 0d;//可用金额
	        Double FrzBal = 0d;//冻结金额
	        String AcTyp="";
	        LoginUserInfo user = LoginUserInfoHelper.getLoginUserInfo();
	        if (user == null) {
	            //TODO 
	        }
	        UserIdentity ui = new UserIdentity();
	        ui.setUserId(user.getEmpId());
	        ui.setUserType(UserType.PLATFORM);
	        AccountInfoDO aif = accountService.queryByUserIdentity(ui).getData();
	        String accountNo = aif.getAccountNo();
	        Map<String, String> resultMap = this.doubleDryService.queryPlatBalance(accountNo);
	        if (resultMap != null) {
	        	String jsonStr = resultMap.get("respData");
	        	List<Map> mapList = JSON.parseArray(jsonStr, Map.class);
	        	if(null != mapList && !mapList.isEmpty()){
	        		for (int i=0,length=mapList.size();i<length;i++) {
	        			AcTyp=String.valueOf(mapList.get(i).get("ac_typ"));
	        			String avl=FormatHelper.changeF2Y(String.valueOf(mapList.get(i).get("avl_bal")));
	        			String frz=FormatHelper.changeF2Y(String.valueOf(mapList.get(i).get("frz_bal")));
	        			String acc=FormatHelper.changeF2Y(String.valueOf(mapList.get(i).get("act_bal")));
	        			if("800".equals(AcTyp)){    //手续费收入账户
	        				AvlBal=Double.valueOf(avl);
	        				FrzBal=Double.valueOf(frz);
	        				AcctBal=Double.valueOf(acc);
	        				context.put("AvlBal1", AvlBal);
	        			}else if("810".equals(AcTyp)){   //营销账户
	        				AvlBal=Double.valueOf(avl);
	        				FrzBal=Double.valueOf(frz);
	        				AcctBal=Double.valueOf(acc);
	        				context.put("AvlBal2", AvlBal);
	        			}else if("820".equals(AcTyp)){    //预付费账户
	        				AvlBal=Double.valueOf(avl);
	        				FrzBal=Double.valueOf(frz);
	        				AcctBal=Double.valueOf(acc);
	        				context.put("AvlBal3", AvlBal);
	        			}else if("830".equals(AcTyp)){   //现金账户
	        				AvlBal=Double.valueOf(avl);
	        				FrzBal=Double.valueOf(frz);
	        				AcctBal=Double.valueOf(acc);
	        				context.put("AvlBal4", AvlBal);
	        			}else if("840".equals(AcTyp)){    //信用账户
	        				AvlBal=Double.valueOf(avl);
	        				FrzBal=Double.valueOf(frz);
	        				AcctBal=Double.valueOf(acc);
	        				context.put("AvlBal5", AvlBal);
	        			}
	        		}
	        	}
		 	    context.put("user", user);
		    }
	        DealRecordDO dealRecord = new DealRecordDO();
	        String searchForm = params.getString("searchForm");
	        if (StringUtils.isNotBlank(searchForm)) {
		        try {
		            JSONObject searchFormJson = JSON.parseObject(searchForm);
		            JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));
		
		            for (Object item : itemsArray) {
		                JSONObject itemJson = JSON.parseObject(String.valueOf(item));
		                String field = String.valueOf(itemJson.get("Field"));
		                String value = String.valueOf(itemJson.get("Value"));
		
		                // 交易时间
		                if ("startTradeDate".equals(field)) {
		                	dealRecord.setStartTradeDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
		                }// 交易时间
		                else if ("endTradeDate".equals(field)) {
		                	dealRecord.setEndTradeDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
		                }
		            }
		        } catch (Exception e) {
		            logger.error("项目投资人项目明细－搜索查询 查询参数解析出错", e);
		        }
	        }
			
	        PageResult<DealRecordDO> result = dealRecordService
					.queryRecord(dealRecord, pageCondition);
			resultVO.setTotal(result.getTotalCount());
			resultVO.setRows(result.getData());
			return resultVO;
	    }

}

package com.autoserve.abc.service.job.thread;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchDO;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.Redsend;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.RedenvelopeType;
import com.autoserve.abc.service.biz.enums.RsState;
import com.autoserve.abc.service.biz.intf.activity.ActivityService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.message.sms.SendMsgService;

/**
 * 投资奖励分析类
 * @author lenovo
 *
 */
public class InvestPrizeThread extends Thread {
	
	private InvestSearchDO		investSearchDO;
    private InvestQueryService 	investQueryService;
    private ActivityService 	activityService;
    private RedService 			redService;
    private SendMsgService 		sendMsgService;
    private UserService			userService;
    
    private final Integer ACT_ID_1001 = 1001;//大赢家活动
    private final Integer ACT_ID_1002 = 1002;//秒标活动
    private final Integer ACT_ID_1003 = 1003;//
    
    public InvestPrizeThread(InvestSearchDO investSearchDO, InvestQueryService investQueryService,
    		ActivityService activityService, RedService redService, SendMsgService sendMsgService, UserService userService)
    {
    	this.investSearchDO = investSearchDO;
    	this.investQueryService = investQueryService;
    	this.activityService = activityService;
    	this.redService = redService;
    	this.sendMsgService = sendMsgService;
    	this.userService = userService;
    }
    
    @Override
    public void run() {
    	
    	this.investSearchDO.setInvestStates(Arrays.asList(InvestState.PAID.state, InvestState.EARNING.state));
    	
    	//大赢家活动奖励
    	if (activityService.isLoanPriceActive(ACT_ID_1001))
    	{
    		//准备奖励列表
    		List<Integer> invIds = new ArrayList<Integer>();
    		
    		List<Invest> investList = this.investQueryService.queryInvestList(investSearchDO).getData();
    		BigDecimal maxInv = new BigDecimal(0.00);
    		
    		//获取最大投资金额
    		for (Invest invest : investList)
    		{
    			if (invest.getInvestMoney().compareTo(maxInv) > 0)
    			{
    				maxInv = invest.getInvestMoney();
    			}
    		}
    		
    		for (Invest invest : investList)
    		{
    			if (invest.getInvestMoney().compareTo(maxInv) >= 0)
    			{
    				invIds.add(invest.getId());
    			}
    		}
    		
    		//进行投资记录操作
    		for (Integer invId : invIds)
    		{
    			investQueryService.updateInvestPrize(invId, ACT_ID_1001);
    		}
    	}
    	
    	//秒标活动奖励
    	if (activityService.isLoanPriceActive(ACT_ID_1002))
    	{
    		//准备奖励列表
    		Integer invId = 0;
    		
    		List<Invest> investList = this.investQueryService.queryInvestList(investSearchDO).getData();
    		if (null != investList && !investList.isEmpty())
    		{
    			invId = investList.get(investList.size() - 1).getId();
    		}
    		
    		investQueryService.updateInvestPrize(invId, ACT_ID_1002);
    	}
    	
    	//9秒标等活动
    	if (activityService.isLoanPriceActive(ACT_ID_1003))
    	{
    		//准备奖励列表
    		List<Integer> invIds = new ArrayList<Integer>();
    		Date date = new Date();
    		Random rand = new Random();
    		Calendar cal = Calendar.getInstance();
			//下面的就是把当前日期加一个月
			cal.add(Calendar.MONTH, 1);
			Date closeDate = cal.getTime();
    		
    		List<Invest> investList = this.investQueryService.queryInvestList(investSearchDO).getData();
    		for (Invest invest : investList)
    		{
    			try
    			{
    				cal.setTime(invest.getCreatetime());
        			int second = cal.get(Calendar.SECOND);
        			if (6 == second || 16 == second || 26 == second || 36 == second || 46 == second || 56 == second)
        			{
        				invIds.add(invest.getId());
        				
        				//随即发红包6-36元 
        				int randNum = 0;
        	    		int cell = rand.nextInt(100);
        				if (cell >= 0 && cell < 50)
        				{
        					randNum = rand.nextInt(14)+6;// 随即6-19的红包50%
        				}
        				else if (cell >= 50 && cell < 80)
        				{
        					randNum = rand.nextInt(10)+20;// 随即20-29的红包30%
        				}
        				else if (cell >= 80 && cell < 100)
        				{
        					randNum = rand.nextInt(7)+30;// 随即30-36的红包20%
        				}
        				
        				List<Redsend> sendList = new ArrayList<Redsend>();
        				Redsend redsend = new Redsend();
        				redsend.setRsTheme("暑期玩6活动红包");
        				redsend.setRsUserid(invest.getUserId());
        				redsend.setRsUseScope("信用贷,抵押贷,担保贷,综合贷");
        				redsend.setRsState(RsState.WITHOUT_USE);
        				redsend.setRsClosetime(closeDate);
        				redsend.setRsStarttime(date);
        				redsend.setRsSender(135);
        				redsend.setRsType(RedenvelopeType.PERSON_RED);
        				redsend.setRsAmt(Double.valueOf(randNum));
        				redsend.setRsValidAmount(Double.valueOf(randNum));

        				sendList.add(redsend);
        				redService.batchSendRed(sendList);
        				
        				//发送中将短信
        				UserDO userDo = userService.findById(invest.getUserId()).getData();
        				if (null != userDo && null != userDo.getUserPhone())
        				{
        					StringBuffer sb = new StringBuffer();
            				sb.append("尊敬的客户，恭喜您在玩6活动中获得了").append(randNum).append("元红包奖励，可登录账户查询并在投资时使用，感谢您支持和信赖。回T退订");
            				sendMsgService.sendMsg(userDo.getUserPhone(), sb.toString(), "","1");
        				}
        			}
    			}
    			catch (Exception ex)
    			{
    				ex.printStackTrace();
    				continue;
    			}
    		}
    		
    		//进行投资记录操作
    		for (Integer invId : invIds)
    		{
    			investQueryService.updateInvestPrize(invId, ACT_ID_1003);
    		}
    	}
    }
}

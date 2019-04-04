package com.autoserve.abc.web.module.screen.activity.json;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.autoserve.abc.dao.dataobject.ActHolidayDO;
import com.autoserve.abc.dao.dataobject.ActPrizeDO;
import com.autoserve.abc.dao.dataobject.ActUserDO;
import com.autoserve.abc.dao.dataobject.CashInvesterViewDO;
import com.autoserve.abc.service.biz.entity.Redsend;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.RedenvelopeType;
import com.autoserve.abc.service.biz.enums.RsState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.intf.activity.ActivityService;
import com.autoserve.abc.service.biz.intf.cash.CashInvesterService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.JsonPlainVO;

/**
 * @author 参加活动
 */
public class OperActivity {

	@Autowired
	private ActivityService activityService;
	
	@Autowired
    private UserService userservice;
	
	@Resource
    private RedService redService;
	
	@Autowired
    private CashInvesterService cashInvesterService;
	
	@Autowired
	private DeployConfigService deployConfigService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpSession session;

	public JsonPlainVO execute(Context context, Navigator nav,
			ParameterParser params, TurbineRunData rundata) {
		JsonPlainVO result = new JsonPlainVO();
		
		ActPrizeDO res = null;
		Date date = new Date();
		String todayString = DateUtil.formatDate(date);

		String actId = params.getString("actId");
		String userId = params.getString("userId");
		String invMoney = params.getString("invMoney");
		String startTime = params.getString("startTime");
		String endTime = params.getString("endTime");
		
		int actType = activityService.getActType(Integer.parseInt(actId));
		//抽奖
		if (1 == actType)
		{
			User user = (User) session.getAttribute("user");
			if (null == user || null == user.getUserPhone())
			{
				result.setSuccess(false);
				result.setMessage("您尚未登陆");
				result.setRedirectUrl(deployConfigService.getLoginUrl(request));
				return result;
			}
			else
			{
				PlainResult<User> re = userservice.findEntityById(user.getUserId());
				User userResult = re.getData();
				// 判断是否进行实名认证(个人用户)
				if (userResult.getUserType() == UserType.PERSONAL
						&& (userResult.getUserRealnameIsproven() == null || userResult.getUserRealnameIsproven() == 0)) {
					result.setSuccess(false);
					result.setMessage("您还未实名认证");
					result.setRedirectUrl("/account/myAccount/bindAccount");
					return result;
				}
			}
			
			int i = activityService.countUserToday(Integer.parseInt(actId), user.getUserId());
//			int i = activityService.countUserToday(Integer.parseInt(actId), Integer.parseInt(userId));
			ActHolidayDO holiday = activityService.findAllHoliday(Integer.parseInt(actId), todayString);
			if (null == holiday)
			{
				if (i > 0)
				{
					result.setSuccess(false);
					result.setMessage("您今天已经参与过活动");
					return result;
				}
			}
			else
			{
				if (i > holiday.getAhCount() - 1)
				{
					result.setSuccess(false);
					result.setMessage("您今天的活动次数已用完");
					return result;
				}
			}
			
			ListResult<ActPrizeDO> aps = activityService.findAllPrize(Integer.parseInt(actId));
			List<ActPrizeDO> prizes = aps.getData();
			if (null != prizes && !prizes.isEmpty())
			{
				Random r = new Random();
				int random = r.nextInt(1000) + 1;// 1-1000随机数
				
				for (ActPrizeDO p : prizes)
				{
					//奖项已经获奖数
					int count = activityService.countPrizeUser(p.getApId(), Integer.parseInt(actId));
					if (random <= p.getApPercentage() && count < p.getApCount())
					{
						res = p;
						//插入抽奖记录
						ActUserDO actUsr = new ActUserDO();
						actUsr.setActId(p.getActId());
						actUsr.setApId(p.getApId());
						actUsr.setAuCreatetime(date);
						actUsr.setAuUserId(user.getUserId());
						actUsr.setAuPhone(user.getUserPhone());
//						actUsr.setAuUserId(Integer.parseInt(userId));
//						actUsr.setAuPhone("17855115511");
//						
						activityService.createActUser(actUsr);
						
						result.setMessage("恭喜您中奖");
						result.setData(res);
						
						break;
					}
				}
				
				if (null == res)
				{
					result.setSuccess(false);
					result.setMessage("所有奖项已经抽完");
					return result;
				}
			}
		}
		//参与活动
		else if (2 == actType)
		{
			User user = (User) session.getAttribute("user");
			if (null == user || null == user.getUserPhone())
			{
				result.setSuccess(false);
				result.setMessage("您尚未登陆");
				result.setRedirectUrl(deployConfigService.getLoginUrl(request));
				return result;
			}
			else
			{
				PlainResult<User> re = userservice.findEntityById(user.getUserId());
				User userResult = re.getData();
				// 判断是否进行实名认证(个人用户)
				if (userResult.getUserType() == UserType.PERSONAL
						&& (userResult.getUserRealnameIsproven() == null || userResult.getUserRealnameIsproven() == 0)) {
					result.setSuccess(false);
					result.setMessage("您还未实名认证");
					result.setRedirectUrl("/account/myAccount/bindAccount");
					return result;
				}
			}
			
			int i = activityService.countUserPrize(Integer.parseInt(actId), user.getUserId());
//			int i = activityService.countUserPrize(Integer.parseInt(actId), Integer.parseInt(userId));
			if (i > 0)
			{
				result.setSuccess(false);
				result.setMessage("您已经参与活动");
				return result;
			}
			else
			{
				//判断是否有参与资格，按投资金额算
				if (activityService.isInvestUser(user.getUserId(), Integer.parseInt(invMoney), startTime, endTime))
//				if (activityService.isInvestUser(Integer.parseInt(userId), Integer.parseInt(invMoney), startTime, endTime))
				{
					ActUserDO actUsr = new ActUserDO();
					actUsr.setActId(Integer.parseInt(actId));
					actUsr.setApId(1);
					actUsr.setAuCreatetime(date);
					actUsr.setAuUserId(user.getUserId());
					actUsr.setAuPhone(user.getUserPhone());
//					actUsr.setAuUserId(Integer.parseInt(userId));
//					actUsr.setAuPhone("18326681761");
					
					activityService.createActUser(actUsr);
					
					result.setData(res);
					result.setSuccess(true);
					result.setMessage("恭喜您参与成功");
				}
				else
				{
					result.setSuccess(false);
					result.setMessage("您还不符合参与资格");
					return result;
				}
			}
		}
		//周年庆随即金额红包
		else if (3 == actType)
		{
			User user = (User) session.getAttribute("user");
			if (null == user || null == user.getUserPhone())
			{
				result.setSuccess(false);
				result.setMessage("您尚未登陆");
				result.setRedirectUrl(deployConfigService.getLoginUrl(request));
				return result;
			}
			else
			{
				PlainResult<User> re = userservice.findEntityById(user.getUserId());
				User userResult = re.getData();
				// 判断是否进行实名认证(个人用户)
				if (userResult.getUserType() == UserType.PERSONAL
						&& (userResult.getUserRealnameIsproven() == null || userResult.getUserRealnameIsproven() == 0)) {
					result.setSuccess(false);
					result.setMessage("您还未实名认证");
					result.setRedirectUrl("/account/myAccount/bindAccount");
					return result;
				}
			}
			
			int i = activityService.countUserPrize(Integer.parseInt(actId), user.getUserId());
			if (i > 0)
			{
				result.setSuccess(false);
				result.setMessage("您已经参与过活动");
				return result;
			}
			else
			{
				BigDecimal investSum = BigDecimal.valueOf(0.00);
				BigDecimal actSum = BigDecimal.valueOf(10000.00);//1万元可以抽奖一次
				
				//投资统计、待收汇总
		        try {
					PlainResult<CashInvesterViewDO> plainResult = cashInvesterService.queryCashInvester(user.getUserId());
					if (null != plainResult && null != plainResult.getData() && null != plainResult.getData().getInValidInvestMoney())
					{
						investSum = plainResult.getData().getInValidInvestMoney();
					}
					
					//判断是否有参与资格，按投资金额算
					if (investSum.compareTo(actSum) >= 0)
					{
						Calendar cal = Calendar.getInstance();
						//下面的就是把当前日期加一个月
						cal.add(Calendar.MONTH, 1);
						Date closeDate = cal.getTime();
						
						//随即概率,自动发红包
						Random rand = new Random();
						int randNum = 1;
						
						int cell = rand.nextInt(100);
						if (cell >= 0 && cell < 60)
						{
							randNum = rand.nextInt(11)+9;// 随即9-19的红包60%
						}
						else if (cell >= 60 && cell < 85)
						{
							randNum = rand.nextInt(20)+20;// 随即20-39的红包25%
						}
						else if (cell >= 85 && cell < 95)
						{
							randNum = rand.nextInt(20)+40;// 随即40-59的红包10%
						}
						else
						{
							randNum = rand.nextInt(7)+60;// 随即60-66的红包5%
						}
						
						ActUserDO actUsr = new ActUserDO();
						actUsr.setActId(Integer.parseInt(actId));
						actUsr.setApId(1);
						actUsr.setAuCreatetime(date);
						actUsr.setAuUserId(user.getUserId());
						actUsr.setAuPhone(user.getUserPhone());
						actUsr.setAuNote(randNum+"元投资红包");
						activityService.createActUser(actUsr);
						
						List<Redsend> sendList = new ArrayList<Redsend>();
						Redsend redsend = new Redsend();
						redsend.setRsTheme("两周年庆活动红包");
						redsend.setRsUserid(user.getUserId());
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
						
						result.setMessage("恭喜您抽中了"+randNum+"元红包");
					}
					else
					{
						result.setSuccess(false);
						result.setMessage("累计投资10000元才能进行抽奖,快去投资吧");
						result.setRedirectUrl("/invest/investList");
						return result;
					}
				} catch (Exception e) {
					e.printStackTrace();
					result.setSuccess(false);
					result.setMessage("参与活动异常,请联系平台技术人员");
					return result;
				}
			}
		}
		//参加活动
		else if (4 == actType)
		{
			String uName = params.getString("uName");
			String uPhone = params.getString("uPhone");
			String uNote = params.getString("uNote");
			
			if (StringUtil.isEmpty(uName) || StringUtil.isEmpty(uPhone))
			{
				result.setSuccess(false);
				result.setMessage("报名信息缺失");
				return result;
			}
			
			//判断是否报过名
			if (activityService.isSign(uName, uPhone) > 0)
			{
				result.setSuccess(false);
				result.setMessage("您已提交报名信息,感谢支持");
				return result;
			}
			
			//参加活动
			activityService.actSignIn(uName, uPhone, uNote);
			result.setSuccess(true);
			result.setMessage("报名成功,感谢支持");
		}
		//红包雨
		else if (5 == actType)
		{
			//投资统计、待收汇总
	        try {
	        	
	        	User user = (User) session.getAttribute("user");
				if (null == user || null == user.getUserPhone())
				{
					result.setSuccess(false);
					result.setMessage("您尚未登陆");
					result.setRedirectUrl(deployConfigService.getLoginUrl(request));
					return result;
				}
				else
				{
					PlainResult<User> re = userservice.findEntityById(user.getUserId());
					User userResult = re.getData();
					// 判断是否进行实名认证(个人用户)
					if (userResult.getUserType() == UserType.PERSONAL
							&& (userResult.getUserRealnameIsproven() == null || userResult.getUserRealnameIsproven() == 0)) {
						result.setSuccess(false);
						result.setMessage("您还未实名认证");
						result.setRedirectUrl("/account/myAccount/bindAccount");
						return result;
					}
				}
				
				Calendar cal = Calendar.getInstance();
				int hour = cal.get(Calendar.HOUR_OF_DAY);//24小时制
				int minute = cal.get(Calendar.MINUTE);//分钟
				int day = cal.get(Calendar.DAY_OF_MONTH);
				
				if (!(21 == day || 22 == day || 23 == day || 24 == day || 25 == day))
				{
					result.setSuccess(false);
					result.setMessage("红包雨时间为8.21日至8.25日，敬请期待");
					return result;
				}
				else if (!((10 == hour && 0 == minute) || (15 == hour && 0 == minute)))
				{
					result.setSuccess(false);
					result.setMessage("红包雨时间为10:00:00-10:01:00和15:00:00-15:01:00");
					return result;
				}
	        	
	        	//下面的就是把当前日期加一个月
				cal.add(Calendar.MONTH, 1);
				Date closeDate = cal.getTime();
				
				//随即概率,自动发红包
				Random rand = new Random();
				int randNum = 1;
				
				int cell = rand.nextInt(100);
				if (cell >= 0 && cell < 50)
				{
					randNum = 0;// 0的红包50%
				}
				else if (cell >= 50 && cell < 80)
				{
					randNum = rand.nextInt(4)+1;// 随即1-4的红包30%
				}
				else if (cell >= 80 && cell < 95)
				{
					randNum = rand.nextInt(5)+5;// 随即5-9的红包15%
				}
				else if (cell >= 95 && cell < 100)
				{
					randNum = rand.nextInt(10)+10;// 随即10-19的红包5%
				}
				
				ActUserDO actUsr = new ActUserDO();
				actUsr.setActId(Integer.parseInt(actId));
				actUsr.setApId(1);
				actUsr.setAuCreatetime(date);
				actUsr.setAuUserId(user.getUserId());
				actUsr.setAuPhone(user.getUserPhone());
				actUsr.setAuNote(randNum+"元投资红包");
				activityService.createActUser(actUsr);
				
				if (randNum > 0)
				{
					List<Redsend> sendList = new ArrayList<Redsend>();
					Redsend redsend = new Redsend();
					redsend.setRsTheme("暑期红包雨活动");
					redsend.setRsUserid(user.getUserId());
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
					
					result.setMessage("恭喜您抽中了"+randNum+"元红包！");
				}
				else
				{
					result.setMessage("请再接再厉哦！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.setSuccess(false);
				result.setMessage("参与活动异常,请联系平台技术人员");
				return result;
			}
		}
		
		return result;
	}
}

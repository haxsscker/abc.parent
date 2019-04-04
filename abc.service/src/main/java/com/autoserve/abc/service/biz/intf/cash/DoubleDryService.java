package com.autoserve.abc.service.biz.intf.cash;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.service.biz.result.MapResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 双乾接口
 * 
 * @author Y。XY
 */
public interface DoubleDryService {
    /**
     * 余额查询接口
     * @param PlatformId
     * @param PlatformType 1:托管账户 2：自有账户
     * @return
     */
    public Double[] queryBalance(String PlatformId, String PlatformType);

    /**
     * 余额查询接口(明细)
     * @param PlatformId
     * @param PlatformType 1:托管账户 2：自有账户
     * @return
     */
    public Double[] queryBalanceDetail(String PlatformId);
    
    /**
     * 开户接口
     *
     * @author Y。XY 2015年 1月19日
     */
    public PlainResult<Map> openAccent(Map params);
    
    /**
     * 企业开户接口
     *
     * @author zy 2018年4月11日
     */
    public PlainResult<Map> openChargeAccent(Map params);
    
    /**
     * 企业开户接口
     *
     * @author zy 2018年4月11日
     */
	public Map<String, String> queryChargeAccountResult(String accountUserAccount, String mark);
    
    
    /**
     * 更换银行卡接口
     *
     * @author zy 2018年 3月30日
     */
    public PlainResult<Map> bindCard(Map params);

    /**
     * 充值接口
     *
     * @author Y。XY 2015年 1月21日
     */
    public PlainResult<Map> recharge(Map params);

    /**
     * 转账接口
     * 
     * @return returnDataMap 数据封装成Map
     * @param LoanJsonList 转账列表
     * @param TransferAction 转账类型
     * @param Action 操作类型
     * @param TransferType 转账方式
     * @param NeedAudit 通过是否需要审核
     * @author wuqiang.du 2015年 1月22日
     */
    public MapResult<String, String> transfer(String LoanJsonList, String TransferAction, String Action,
                                              String TransferType, String NeedAudit, Map<String, String> params);

    /**
     * 满标划转接口
     * 
     * @param LoanNoList 乾多多流水号列表
     * @param LoanJsonList 手续费，担保费，转让费转账列表
     * @param seqNo 平台订单号
     * @param money 总金额
     * @return BaseResult 三方接口处理状态
     * @author xiangyu.yin 2015年 2月4日
     * @param money
     */
    public PlainResult<Map<String, String>> fullTranfer(String LoanNoList, String LoanJsonList, String seqNo,
                                                        String money);

    /**
     * 审核接口
     * 
     * @param LoanNoList 乾多多流水号列表
     * @param AuditType 审核类型
     * @return returnDataMap 数据封装成Map
     * @author wuqiang.du 2015年 1月22日
     */
    public PlainResult<Map<String, String>> transferaudit(String LoanNoList, String AuditType, String seq, String money);

    /**
     * 得到SecondaryJsonList字符串
     * 
     * @param LoanInMoneymoremore 二次收款人乾多多标识
     * @param Amount 二次分配金额
     * @param TransferName 用途
     * @param Remark 备注
     * @author yinxiangyu 2015年 3月4日
     */
    public String secondaryJsonList(String LoanInMoneymoremore, String Amount, String TransferName, String Remark);

    /**
     * 得到loanJsonList字符串
     * 
     * @param inusrId 入账
     * @param outUsrId 出账
     * @param operateMoney 金额
     * @param batchNo 网贷平台标号
     * @param orderNo 网贷平台订单号
     * @author wuqiang.du 2015年 1月22日
     */
    public String loanJsonList(String inusrId, String outUsrId, String operateMoney, String batchNo, String orderNo,
                               String SecondaryJsonList);

    /**
     * 封装开户的数据
     */
    public AccountInfoDO formatAccount(Map params);

    /**
     * 投资、还款授权
     * 
     * @param moneymoremoreId 乾多多标识
     * @return
     * @author wuqiang.du 2015年1月26日
     */
    public Map<String, String> authorize(Map<String, String> map);

    /**
     * 投资接口
     * 
     * @return PlainResult<Map>
     * @param LoanJsonList 付款人，收款人，金额的JSON数据
     */
    public PlainResult<Map<String, String>> invest(String LoanJsonList);

    /**
     * 还款发送接口
     * 
     * @return Map<String,String> 第三方返回的数据
     * @param LoanJsonList 付款人，收款人，金额的JSON数据
     */
    public Map<String, String> trannfee(String LoanJsonList);

    /**
     * 资金释放接口
     * 
     * @param LoanNoList 乾多多流水号列表
     * @param AuditType 审核类型
     * @return returnDataMap 数据封装成Map
     * @author wuqiang.du 2015年1月26日
     */
    public Map<String, String> toloanrelease(String Amount, String OrderNo, String MoneymoremoreId);

    /**
     * 还款调用转账借款
     * 
     * @param LoanNoList 乾多多流水号列表
     * @param AuditType 审核类型
     * @return returnDataMap 数据封装成Map
     * @author wuqiang.du 2015年1月26日
     */
    public PlainResult<Map<String, String>> payBack(String LoanJsonList);

    /**
     * 封装发送三方接口的http请求
     * 
     * @param Map 接口文档中所有的数据和提交的URL
     */
    public PlainResult<Map> postHttpToDD(String submitUrl, Map params);

    /**
     * 对账接口
     * 
     * @param Map 接口文档中所有的数据
     * @return PlainResult接口返回数据
     */
    public PlainResult<Map> balanceAccount(Map<String, String> params);

    /**
     * 流标接口
     * 
     * @param params
     * @return
     */
    public PlainResult<Map<String, String>> loanFree(String LoanNoList, String seqNo, String money);
    /**
     * 查询可用余额接口
     * @param userId
     * @return
     */
	BigDecimal queryRZAvlBalance(int userId);

	/**
     * 三合一接口
     * 
     * @param Map 接口文档中所有的数据
     * @return PlainResult接口返回数据
     */
    public PlainResult<Map> fastPay(Map<String, String> params);

	List<BankInfoDO> queryCard(String accountNo, String userPhone);

	PlainResult<Map> webInvest(Map params);
	/**
	 * 修改手机号
	 * @param paramsMap
	 * @return
	 */
	public PlainResult<Map> changPhone(Map<String, String> param);
	/**
	 * 修改/找回支付密码
	 * @param param
	 * @return
	 */
	public PlainResult<Map> changPwd(Map<String, String> param);

	/**
	 * 红包实时返还接口
	 * @param map
	 * @return
	 */
	public Map<String, String> returnRed(Map<String, Object> map);
	/**
	 * 用户授权信息查询
	 * @param map
	 * @return
	 */
	public Map<String, String> queryAuthorizeInfo(Map<String, String> map);
	/**
	 * 用户投资（后台）
	 * @param map
	 * @return
	 */
	public PlainResult<Map<String, String>> backInvest(String innerSeqNo,
			List<DealRecordDO> dealRecords);
	/**
	 * 线下充值账号查询（后台方式）
	 * @param map
	 * @return
	 */
	public Map<String, String> queryChargeAccount(Map<String, String> map);

    /**
     * 后台商户余额查询接口
     * @param accountNo
     * @return
     */
	public Map<String, String> queryPlatBalance(String accountNo);
	/**
	 * 用户线下充值记录查询
	 * @param map
	 * @return
	 */
	public Map<String, Object> queryChargeDetail(Map<String, String> map);
	 /**
     *  销户接口（企业）
     * @param accountNo
     * @return
     */
	PlainResult<Map> closeAccount(Map params);

	Map<String, String> queryGuarAccountResult(String accountUserAccount,
			String mark);

	Map<String, String> guarAuthorize(Map<String, String> map);
	/**
	 * 个人用户信息查询
	 * @param userPhone
	 * @return
	 */
	Map<String, String> queryUserInf(String userPhone);

	
}

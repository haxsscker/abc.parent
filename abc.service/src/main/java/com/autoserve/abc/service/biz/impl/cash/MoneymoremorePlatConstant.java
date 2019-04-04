package com.autoserve.abc.service.biz.impl.cash;

public interface MoneymoremorePlatConstant {
	
	/**
	 * 用户注册类型  
	 * 1表示全自动，2表示半自动      全自动会生成随机的登录密码和支付密码发送到用户的手机，安保问题需要登录乾多多后台设置
	 */
	public static final String AUTOMATIC_REGISTER_TYPE = "1";
	public static final String SEMIAUTOMATIC_REGISTER_TYPE = "2";
	
	/**
	 * 账户类型
	 * 空表示个人账户 1表示企业账户
	 */
	public static final String ENTERPRISE_ACCOUNT_TYPE = "1";
	
	/**
	 * 三合一接口
	 * 操作类型   目前只有银行卡绑定
	 * 1.用户认证    2.提现银行卡绑定    3.代扣授权   4.取消代扣授权
	 */
	public static final String UBANK_BIND_ACTION_TYPE = "2";
	
	/**
	 * 平台乾多多账户类型
	 * 1.托管账户	2.自有账户
	 */
	public static final String TRUSTEES_PLATFORM_TYPE = "1";
	public static final String OWN_PLATFORM_TYPE = "2";
	
	/**
	 * 转账类型
	 * 1.投标   2.还款
	 */
	public static final String BID_TRANSFER__TYPE = "1";
	public static final String REPAY_BID_TRANSFER__TYPE = "2";
	
	/**
	 * 转账接口
	 * 操作类型
	 * 1.手动   2.自动
	 */
	public static final String MANNUL_TRANSFER_ACTION_TYPE = "1";
	public static final String AUTOMATIC_TRANSFER_ACTION_TYPE = "2";
	
	/**
	 * 转账接口
	 * 转账方式
	 * 1.桥连   2.直连
	 */
	public static final String BRIDG_TRANSFER_MODE = "1";
	public static final String DIRECT_TRANSFER_MODE = "2";
	
	/**
	 * 转账接口
	 * 通过是否需要审核
	 * 空.需要审核  1.自动通过
	 */
	public static final String AUTOMATIC_THROUTH = "1";
	
	/**
	 * 充值类型
	 * 空.网银充值  1.代扣充值
	 */
	public static final String WITHHOLD_RECHARGE_TYPE = "1";
	
	/**
	 * 银行卡类型  
	 * 0.借记卡	1.信用卡
	 */
	public static final String DEBITCARD_TYPE = "0";
	public static final String CREDITCARD_TYPE = "1";

/**
	 * 审核接口
	 * 1.通过   2.退回
	 */
	public static final String YES_AUDIT = "1";
	public static final String NO_AUDIT = "2";
	
}

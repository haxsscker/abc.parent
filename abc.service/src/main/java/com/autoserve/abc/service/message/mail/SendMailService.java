package com.autoserve.abc.service.message.mail;

import com.autoserve.abc.service.biz.entity.LoanIntentApply;
import com.autoserve.abc.service.biz.result.BaseResult;

/**
 * 发送验证码到邮箱
 * 
 * @param toAddress
 *            收件人
 * @param mailContent
 *            邮件内容
 * @param theme
 *            邮件主题
 * @author wuqiang.du
 */
public interface SendMailService {

	/**
	 * 发送验证码到邮箱
	 * 
	 * @param toAddress
	 *            收件人
	 * @param mailContent
	 *            邮件内容
	 * @param theme
	 *            邮件主题
	 * @param mailContent1
	 *            邮件附件
	 */
	public boolean sendYzm2Mail(String toAddress, String mailContent,
			String theme);

	/**
	 * 批量借款发送附件合同
	 * 
	 * @param loanId
	 */
	public BaseResult sendMailToInvestUser(int loanId, String loanName);

	/**
	 * 批量债权发送附件合同
	 * 
	 * @param loanId
	 */
	public BaseResult sendMailToCreditoUser(int loanId, String loanName);

	
	
	/**
	 * 前台借款人提交借款申请后以邮件形式发送至平台企业邮箱
	 * @param loanIntentApply
	 * @return
	 */
	public BaseResult sendMailToManager(LoanIntentApply loanIntentApply);
	
	/**
	 * 公用的发送邮件接口
	 * @param toAddress  发送的地址
	 * @param content    发送内容
	 * @param subject	  邮件标题
	 * @return
	 */
	public BaseResult sendMail(String toAddress,String content,String subject);


	BaseResult sendMailToOrgLoanUser(String content, String toAddress);
	

}

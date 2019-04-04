package com.autoserve.abc.service.message.mail;

import com.autoserve.abc.service.biz.result.BaseResult;

public interface SimpleMailSenderService {
    /**
     * 发送文本邮件
     * 
     * @param mailInfo邮件参数
     */

    public BaseResult sendTextMail(MailSenderInfo mailInfo);
    
    /**
     * 发送HTML格式邮件
     * 
     * @param mailInfo邮件参数
     */

    public BaseResult sendHTMLMail(MailSenderInfo mailInfo);

    /**
     * 发送HTML格式邮件
     * 
     * @param mailInfo邮件参数
     * @param outFile附件路径
     */

    public BaseResult sendHtmlMail(MailSenderInfo mailInfo, String outFile);
}

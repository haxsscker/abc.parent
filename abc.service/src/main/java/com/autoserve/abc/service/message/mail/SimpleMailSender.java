package com.autoserve.abc.service.message.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.autoserve.abc.service.biz.result.BaseResult;

public class SimpleMailSender implements SimpleMailSenderService {
    /**
     * 以文本格式发送邮件
     * 
     * @param mailInfo 待发送的邮件的信息
     */
    @Override
    public BaseResult sendTextMail(MailSenderInfo mailInfo) {
        BaseResult result = new BaseResult();
        // 判断是否需要身份认证    
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        if (mailInfo.isValidate()) {
            // 如果需要身份认证，则创建一个密码验证器    
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session    
        //   Session sendMailSession = Session.getDefaultInstance(pro,authenticator);    //不要用getDefaultInstance,这样只能使用一种邮箱，再想改成别的邮箱就不行了
        Session sendMailSession = Session.getInstance(pro, authenticator); //用这个，程序里可以切换其他邮箱，前提是这些邮箱要开通smtp协议，这是MailSenderInfo里写死的。
        try {
            // 根据session创建一个邮件消息    
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址    
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // 设置邮件消息的发送者    
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中    
            Address to = new InternetAddress(mailInfo.getToAddress());
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            // 设置邮件消息的主题    
            mailMessage.setSubject(mailInfo.getSubject());
            // 设置邮件消息发送的时间    
            mailMessage.setSentDate(new Date());
            // 设置邮件消息的主要内容    
            String mailContent = mailInfo.getContent();
            mailMessage.setText(mailContent);
            // 发送邮件    
            Transport.send(mailMessage);
            result.setMessage("邮件发送成功");
            result.setSuccess(true);
            return result;
        } catch (MessagingException EX) {
            EX.printStackTrace();
        }
        result.setMessage("邮件发送失败");
        result.setSuccess(false);
        return result;
    }

    /**
     * 以HTML格式发送邮件
     * 
     * @param mailInfo 待发送的邮件信息
     */
    @Override
    public BaseResult sendHtmlMail(MailSenderInfo mailInfo, String outFile) {
        BaseResult result = new BaseResult();
        // 判断是否需要身份认证    
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        //如果需要身份认证，则创建一个密码验证器     
        if (mailInfo.isValidate()) {
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session    
        //   Session sendMailSession = Session.getDefaultInstance(pro,authenticator);    //不要用getDefaultInstance,这样只能使用一种邮箱，再想改成别的邮箱就不行了
        Session sendMailSession = Session.getInstance(pro, authenticator); //用这个，程序里可以切换其他邮箱，前提是这些邮箱要开通smtp协议，这是MailSenderInfo里写死的。

        try {
            // 根据session创建一个邮件消息    
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址    
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // 设置邮件消息的发送者    
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中    
//            InternetAddress[] to = new InternetAddress().parse(mailInfo.getToAddress());
            // Message.RecipientType.TO属性表示接收者的类型为TO   
            mailMessage.setRecipients(Message.RecipientType.TO, mailInfo.getTos());
            // 设置邮件消息的主题    
            mailMessage.setSubject(mailInfo.getSubject());      
            // 设置邮件消息发送的时间    
            mailMessage.setSentDate(new Date());
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象   
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart    
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容    
            html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            //添加附件
            // 创建一新的MimeBodyPart  
            MimeBodyPart mdp = new MimeBodyPart();
            //得到文件数据源
            FileDataSource fds = new FileDataSource(outFile);
            //得到附件本身并至入BodyPart
            mdp.setDataHandler(new DataHandler(fds));
            try {
                String name = MimeUtility.encodeText(fds.getName());
                //得到文件名同样至入BodyPart 
                mdp.setFileName(name);
                mainPart.addBodyPart(mdp);
                // 将MiniMultipart对象设置为邮件内容    
                mailMessage.setContent(mainPart);
                // 发送邮件    
                Transport.send(mailMessage);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            result.setMessage("邮件发送成功");
            result.setSuccess(true);
            return result;
        } catch (MessagingException EX) {
            EX.printStackTrace();
        }
        result.setMessage("邮件发送失败");
        result.setSuccess(false);
        return result;
    }

    @Override
    public BaseResult sendHTMLMail(MailSenderInfo mailInfo) {
        BaseResult result = new BaseResult();
        // 判断是否需要身份认证    
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        //如果需要身份认证，则创建一个密码验证器     
        if (mailInfo.isValidate()) {
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session    
        //   Session sendMailSession = Session.getDefaultInstance(pro,authenticator);    //不要用getDefaultInstance,这样只能使用一种邮箱，再想改成别的邮箱就不行了
        Session sendMailSession = Session.getInstance(pro, authenticator); //用这个，程序里可以切换其他邮箱，前提是这些邮箱要开通smtp协议，这是MailSenderInfo里写死的。

        try {
            // 根据session创建一个邮件消息    
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址    
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // 设置邮件消息的发送者    
            mailMessage.setFrom(from);
            new InternetAddress();
			// 创建邮件的接收者地址，并设置到邮件消息中    
            InternetAddress[] to = InternetAddress.parse(mailInfo.getToAddress());
            // Message.RecipientType.TO属性表示接收者的类型为TO   
            mailMessage.setRecipients(Message.RecipientType.TO, to);
            // 设置邮件消息的主题    
            mailMessage.setSubject(mailInfo.getSubject());
            // 设置邮件消息发送的时间    
            mailMessage.setSentDate(new Date());
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象   
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart    
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容    
            html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            mailMessage.setContent(mainPart);
            // 发送邮件    
            Transport.send(mailMessage);

            result.setMessage("邮件发送成功");
            result.setSuccess(true);
            return result;
        } catch (MessagingException EX) {
            EX.printStackTrace();
        }
        result.setMessage("邮件发送失败");
        result.setSuccess(false);
        return result;
    }

}

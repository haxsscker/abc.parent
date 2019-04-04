package com.autoserve.abc.service.message.sms;

/**
 * 发送手机短信
 */
public interface SendMsgService {

    /**
     * 
     * @param telephone 手机号码
     * @param content   短信内容
     * @param personName   发送者姓名
     * @param messageTypeId   （必填）短信类型（1：广告（普通速度）；2：验证码（最高速度）；3：催费（较高速度）；）
     * @return
     */
    public boolean sendMsg(String telephone, String content, String personName,String messageTypeId);
    
    /**
     * 批量发送提醒短信
     * @param telephone 手机号码
     * @param content	提醒内容
     * @return
     */
    public boolean sendMsg(String[] telephone, String content);
}

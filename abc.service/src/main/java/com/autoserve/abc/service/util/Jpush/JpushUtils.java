package com.autoserve.abc.service.util.Jpush;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import com.autoserve.abc.service.util.SystemGetPropeties;
/**
 * 极光推送工具
 * @author sunlu
 *
 */
public class JpushUtils {
private static Logger LOG = LoggerFactory.getLogger(JpushUtils.class);
	
//	private static final String MASTER_SECRET = "0564b36572dcc98fc051cc38";
	
//	private static final String APP_KEY = "fa3aa7a642c2daf0d72c81bf";
	
	private static final String MASTER_SECRET = SystemGetPropeties.getBossString("jgpush.masterSecret");
	
	private static final String APP_KEY = SystemGetPropeties.getBossString("jgpush.appKey");
	
//	private static final JPushClient jpushClient = new JPushClient(MASTER_SECRET,APP_KEY,null,ClientConfig.getInstance());
	
//	private static final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
	
	private static volatile JPushClient jpushClient;
	/**
	 * 初始化JPushClient
	 * @return
	 */
	public static JPushClient jPushClientInit(){
		if(null == jpushClient){
			synchronized (JPushClient.class) {
				jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
			}
		}
		return jpushClient;
	}
	
	/**
	 * 构建ios.PushPayload(广播群发)
	 * @param alert
	 * @return
	 */
	public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(String alert) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.tag_and("0", "0"))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(alert)
                                .setBadge(0)
                                .setSound("happy")
                                .addExtra("from", "JPush")
                                .build())
                        .build())
                 .setMessage(Message.content(""))
                 .setOptions(Options.newBuilder()
                         .setApnsProduction(true)
                         .build())
                 .build();
    }
	
	/**
	 * 构建android.PushPayload(广播群发)
	 * @param alert
	 * @return
	 */
	public static PushPayload buildPushObject_android_tagAnd_alertWithExtrasAndMessage(String alert) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(alert)
                                .addExtra("from", "JPush")
                                .build())
                        .build())
                 .setMessage(Message.content(""))
                 .setOptions(Options.newBuilder()
                         .setApnsProduction(true)
                         .build())
                 .build();
    }
	
	
	/**
	 * 所有平台，推送目标是别名为 "alias"，通知内容为 content
	 * 
	 * @param alias
	 * @param content
	 * @return
	 */
	public static PushPayload buildPushObject_all_alias_alert(String alias, String content) {
		return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(alias))
				.setNotification(Notification.alert(content)).build();
	}
	
	
	/**
	 * 构建ios.PushPayload(根据别名发送消息)
	 * @param alert
	 * @param alias
	 * @return
	 */
	public static PushPayload buildPushObject_ios_alias_alert(String alias,String alert) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.alert(alert))
                .build();
    }
	
	/**
	 * 根据别名推送
	 * @param msg
	 * @return
	 */
	public static Map <String,String> sendPush_alias(String alias,String msg){
		 Map<String, String> hashMap = new HashMap<String, String>();
		 PushResult result = null;
		 PushPayload payload  = buildPushObject_all_alias_alert(alias,msg);
		 try {
			jpushClient = jPushClientInit();
			result = jpushClient.sendPush(payload);
			LOG.info("Got result - " + result);
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
			hashMap.put("code", "fail");
		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
			hashMap.put("code", "fail");
		}catch (Exception e) {
			hashMap.put("code", "fail");
		}
		 return hashMap;
	 }
	
	
	
	/**
	 * ios、android分别推送
	 * @param msg
	 * @return
	 */
	public static Map <String,String> sendPush_android_ios(String msg){
		 Map<String, String> hashMap = new HashMap<String, String>();
		 PushResult result = null;
		 PushPayload ios = buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(msg);
		 PushPayload android = buildPushObject_android_tagAnd_alertWithExtrasAndMessage(msg);
		 try {
			jpushClient = jPushClientInit();
			result = jpushClient.sendPush(ios);
//			System.out.println("ios Got result - " +result);
			LOG.info("ios Got result - " + result);
		} catch (Exception e) {
			hashMap.put("ios_code", "fail");
			hashMap.put("ios_msg", result.toString());
			e.printStackTrace();
		}
		 try {
			result = jpushClient.sendPush(android);
//			System.out.println("android Got result - " + result);
			LOG.info("android Got result - " + result);
		} catch (Exception e) {
			hashMap.put("android_code", "fail");
			hashMap.put("android_msg", result.toString());
			e.printStackTrace();
		}
		 return hashMap;
	 }
	/**
	 * 所有设备统一推送
	 * @param msg
	 * @return
	 */
	 public static Map <String,String> sendPush(String msg){
		 Map<String, String> hashMap = new HashMap<String, String>();
		 PushResult result = null;
		 PushPayload payload = PushPayload.alertAll(msg);
		 try {
			 jpushClient = jPushClientInit();
			 result = jpushClient.sendPush(payload);
			 LOG.info("Got result - " + result);
		 } catch (Exception e) {
			 hashMap.put("code", "fail");
		 }
		 return hashMap;
	 }
	 public static void main(String[] args) {
		 sendPush_alias("lls0062","别名测试");
		// sendPush("测试");
	}
}

package com.ksource.common.util;

import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class GetuiUtil {

    private static String appId = "NAXIlRVtNM7aPMCEKMg2X";
    private static String appKey = "xaCRJ17Czd6Ln8PrpbfQK9";
    private static String appSecret = "iWNxCv5pq074NLNyP0eEz3";
    private static String masterSecret = "iWNxCv5pq074NLNyP0eEz3";
    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";
    
    private static GetuiUtil instance = null;
    
    public static IGtPush push = null;
    public GetuiUtil() {
        push = new IGtPush(url, appKey, masterSecret);
    }
    public static GetuiUtil getInstance(){
        if(instance == null){
            synchronized (GetuiUtil.class) {
                if(instance == null){
                    instance = new GetuiUtil();
                }
            }
        }
        return instance;
    }
    
    
	public static void main(String[] args) throws Exception {
		GetuiUtil.PushToIosSingle("systemAdmin", "标题", "水水水水1");
	}
	

	// IOS和安卓端推送单条信息
	/**
	 * @param CID
	 * @param message
	 *            IOs消息标题
	 * @param Content
	 *            IOS消息内容
	 * @return
	 * @throws Exception
	 */
	public static IPushResult PushToIosSingle(String userId, String message, String Content) throws Exception {
	    
		push.connect();
		// 透传
		TransmissionTemplate template = TransmissionTemplate(message, Content);

		SingleMessage sm = new SingleMessage();
		sm.setOffline(true);
		sm.setOfflineExpireTime(1 * 1000 * 3600 * 72);
		sm.setData(template);

		Target target = new Target();
		target.setAppId(appId);
		target.setAlias(userId);
		IPushResult ret = push.pushMessageToSingle(sm, target);
        System.out.println(ret.getResponse().toString());
		return ret;

	}
	
	
	
	/**
     * 推送多个用户
     * @param pullUserList
     * @param pushData
     */
    public static void pushMessageByUserIds(List<String> pullUserList, String  dataMsg) {
        // 配置返回每个用户返回用户状态，可选
        System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");
        ListMessage message = new ListMessage();
        message.setData(transmissionTemplate(dataMsg));
        // 设置消息离线，并设置离线时间
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 1000 * 3600);
        // 配置推送目标
        List<Target> targets = new ArrayList<Target>();
        for (int i = 0; i < pullUserList.size(); i++) {
            Target target = new Target();
            target.setAppId(appId);
            target.setAlias(pullUserList.get(i));
            targets.add(target);
        }
        String taskId = push.getContentId(message);
        IPushResult ret = push.pushMessageToList(taskId, targets);
        System.out.println(ret.getResponse().toString());
    }
    
    public static TransmissionTemplate transmissionTemplate(String content) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        template.setTransmissionContent(content);
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
        return template;
    }
	
	
	/**
	 * 版本更新时只给安卓端APP推送信息
	 * 
	 * @param Iosmessage
	 * @param Content
	 * @return
	 * @throws Exception
	 */
	public static IPushResult PushToAndroidApp(String Iosmessage, String Content) throws Exception {

	    IGtPush push = new IGtPush(url, appKey, masterSecret);
		push.connect();
		TransmissionTemplate template = TransmissionTemplateAndroid(Iosmessage,Content);
		AppMessage message = new AppMessage();
		message.setData(template);

		message.setOffline(true);
		message.setOfflineExpireTime(1 * 1000 * 3600 * 72);

		List<String> appIdList = new ArrayList<String>();
		List<String> phoneTypeList = new ArrayList<String>();
//		List<String> tagList = new ArrayList<String>();

		appIdList.add(appId);
		phoneTypeList.add("ANDROID");
//		tagList.add("推送");

		message.setAppIdList(appIdList);
		message.setPhoneTypeList(phoneTypeList);
		// message.setTagList(tagList);
		IPushResult ret = push.pushMessageToApp(message, "");
		return ret;

	}



	public static TransmissionTemplate TransmissionTemplate(String message, String Content) throws Exception {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		template.setTransmissionType(2);

		template.setTransmissionContent(Content);
/*		JSONObject jo = JSONObject.fromObject(Content);
		if(StringUtils.isNotBlank(jo.getString("type"))){
			if(jo.getString("type").equals("notice")){
				JSONObject jonObject1 = JSONObject.fromObject(Content);
				JSONObject jonObject2 = JSONObject.fromObject(Content);
				
				String resultContent  = jo.getString("content");
				
				JSONObject jo1 = JSONObject.fromObject(resultContent);
				String resultScheduleId = jo1.getString("scheduleId");
				String resultUserId  = jo.getString("userId");
				String resultType  = jo.getString("type");
				
				jonObject1.put("scheduleId", resultScheduleId);
				jonObject1.put("content", "");
				
				jonObject2.put("content", jonObject1.toString());
				jonObject2.put("userId", resultUserId);
				jonObject2.put("type", resultType);
				
				Content = jonObject2.toString();
			}
		}
		template.setPushInfo("", 1, message, "", Content, "", "", "");*/
		return template;
	}
	
	public static TransmissionTemplate TransmissionTemplateAndroid(String message, String Content) throws Exception {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		template.setTransmissionType(2);

		template.setTransmissionContent(Content);
		//template.setPushInfo("", 1, message, "", Content, "", "", "");
		return template;
	}

}

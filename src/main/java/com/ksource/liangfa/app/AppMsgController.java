package com.ksource.liangfa.app;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.util.JsonMapper;
import com.ksource.liangfa.domain.InstantMessage;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.service.system.InstantMessageService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 通知公告
 * @author 符家鑫
 * @date 2017
 */

@Controller
@RequestMapping("/app/msg")
public class AppMsgController {
	
	@Autowired
	private InstantMessageService instantMessageService;
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(AppMsgController.class);

	@RequestMapping("query")
	@ResponseBody
	public String query (Integer msgType,Integer readStatus,String page,HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
	    try{
	        User currUser = SystemContext.getCurrentUser(request);
	        InstantMessage instantMessage = new InstantMessage();
	        instantMessage.setReadStatus(readStatus);
	        instantMessage.setReceiveUser(currUser.getUserId());
	        instantMessage.setMsgType(msgType);
	        PaginationHelper<InstantMessage> instantMessageList = instantMessageService.query(instantMessage,page);
            jsonObject.put("success", true);
            jsonObject.put("msg", "查询成功");
	        jsonObject.put("list", instantMessageList.getList());
	        jsonObject.put("totalPageNum", instantMessageList.getTotalPageNum());
	        String result = JSONObject.toJSONString(jsonObject,serializeConfig);
	        return result;
	    }catch(Exception e){
	        logger.error("", e);
	        jsonObject.put("success", false);
	        jsonObject.put("msg", "查询失败");
	        String result = jsonObject.toJSONString();
	        return result;
	    }
	}
	
	
	
	@RequestMapping("messageCount")
	@ResponseBody
	public JSONObject message (HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
	    try{
	        User currUser = SystemContext.getCurrentUser(request);
	        InstantMessage instantMessage = new InstantMessage();
	        instantMessage.setReadStatus(Const.READ_STATUS_NO);
	        instantMessage.setReceiveUser(currUser.getUserId());
	        Map<String, Integer> sm = instantMessageService.selectMessageCount(instantMessage);
            jsonObject.put("success", true);
            jsonObject.put("msg", "查询成功");
            jsonObject.put("data",sm);
	        return jsonObject;
	    }catch(Exception e){
	        logger.error("", e);
	        jsonObject.put("success", false);
	        jsonObject.put("msg", "查询失败");
	        return jsonObject;
	    }
	}
	
    @RequestMapping("delete")
    @ResponseBody
    public JSONObject readMsg (Integer msgId){
        JSONObject jsonObject = new JSONObject();
        try{
            if(msgId == null){
                throw new Exception("msgId为空！");
            }
            //删除消息
            instantMessageService.deleteByMsgId(msgId);
            jsonObject.put("success", true);
            jsonObject.put("msg", "删除成功！");
        }catch(Exception e){
            logger.error("", e);
            jsonObject.put("success", false);
            jsonObject.put("msg", e.getMessage());
        }
        return jsonObject; 
    }
	
    @RequestMapping("detail")
    @ResponseBody
    public String detail (Integer msgId){
        JSONObject jsonObject = new JSONObject();
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
        try{
            if(msgId == null){
                throw new Exception("msgId为空！");
            }
            InstantMessage instantMessage = instantMessageService.selectByPrimaryKey(msgId);
            instantMessage.setContent(StringUtils.Html2Text(instantMessage.getContent()));
            instantMessage.setReadStatus(Const.INSTANT_MESSAGE_READ_YES);
            //更新读取状态
            instantMessageService.updateReadStatus(instantMessage);
            jsonObject.put("success", true);
            jsonObject.put("msg", "查询成功！");
            jsonObject.put("instantMessage", instantMessage);
        }catch(Exception e){
            logger.error("", e);
            jsonObject.put("success", false);
            jsonObject.put("msg", e.getMessage());
        }
        String result = JSONObject.toJSONString(jsonObject,serializeConfig);
        return result; 
    }
    
    
    
    
	
}

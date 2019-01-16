package com.ksource.liangfa.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.util.JsonMapper;
import com.ksource.liangfa.domain.CommunionReceive;
import com.ksource.liangfa.domain.CommunionReply;
import com.ksource.liangfa.domain.CommunionSend;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.instruction.CommunionReceiveService;
import com.ksource.liangfa.service.instruction.CommunionReplyService;
import com.ksource.liangfa.service.instruction.CommunionSendService;
import com.ksource.liangfa.service.system.InstantMessageService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 横向交流
 * @author 符家鑫
 * @date 2017
 */

@Controller
@RequestMapping("/app/communion")
public class AppCommunionController {
	
	@Autowired
	CommunionReceiveService communionReceiveService;
	@Autowired
	CommunionSendService communionSendService;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	CommunionReplyService communionReplyService;
    @Autowired
	private InstantMessageService instantMessageService;
	
	/**
	 * 查询我接收的交流
	 * @param communionReceive
	 * @param page
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("myReceiveList")
	@ResponseBody
	public String myReceiveList(String title,String page,HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject();
		PaginationHelper<CommunionReceive> communionReceiveList;
		CommunionReceive communionReceive=new CommunionReceive();
		try {
			communionReceive.setTitle(title);
			User currUser = SystemContext.getCurrentUser(request);
			Organise currOrg = currUser.getOrganise();		
			communionReceive.setReceiveOrg(currOrg.getOrgCode().toString());
			communionReceiveList = null;
			communionReceiveList=communionReceiveService.find(communionReceive, page,null);
			
            jsonObject.put("success", true);
            jsonObject.put("msg", "查询成功");
            jsonObject.put("list", communionReceiveList.getList());
            jsonObject.put("totalPageNum", communionReceiveList.getTotalPageNum());
			SerializeConfig serializeConfig = new SerializeConfig();
            serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
            String result = JSONObject.toJSONString(jsonObject,serializeConfig);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "待分派线索列表获取发生异常！!");
			return jsonObject.toJSONString();
		}
	}
	
	

	/**
	 * 信息交流详情
	 * @param communionId
	 * @param receiveId
	 * @param readStatus
	 * @param request
	 * @return
	 */
	@RequestMapping("detail")
	@ResponseBody
	public String detail(Integer receiveId,HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		try {
		    CommunionReceive communionReceive = new CommunionReceive();
		    communionReceive.setReceiveId(receiveId);
		    communionReceive.setReadStatus(Const.READ_STATUS_YES);
		    communionReceive.setReadTime(new Date());
		    communionReceiveService.updateByPrimaryKeySelective(communionReceive);
			CommunionSend communionSend=new CommunionSend();
			PublishInfoFileExample example = new PublishInfoFileExample();
			List<PublishInfoFile> files = new ArrayList<PublishInfoFile>();
			communionSend=communionSendService.getByReceiveId(receiveId);
			communionSend.setContent(StringUtils.Html2Text(communionSend.getContent()));
			example.createCriteria().andFileTypeEqualTo(Const.TABLE_COMMUNION_SEND).andInfoIdEqualTo(communionSend.getCommunionId());
			files = mybatisAutoMapperService.selectByExample(PublishInfoFileMapper.class, example, PublishInfoFile.class);
			
			User currUser = SystemContext.getCurrentUser(request);
			//更新消息详情
			instantMessageService.upReadStatus(receiveId.toString(), currUser);
			
			jsonObject.put("success", true);
			jsonObject.put("communionSend", communionSend);
			jsonObject.put("fileList", files);
			SerializeConfig serializeConfig = new SerializeConfig();
            serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
            String result = JSONObject.toJSONString(jsonObject,serializeConfig);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "获取工作汇报详情发生异常！!");
			return jsonObject.toJSONString();
		}
		
	}
	
	/**
	 * 查询回复列表
	 * @param communionId
	 * @param page
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("replayList")
	@ResponseBody
	public String replayList(Integer communionId,String page,HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject();
		
		try {
			CommunionReply param=new CommunionReply();
			param.setCommunionId(communionId);
			List<CommunionReply> communionReplys=new ArrayList<CommunionReply>();
			communionReplys=communionReplyService.getListByCommunionId(param);
			
			jsonObject.put("success", true);
			jsonObject.put("communionReplys",communionReplys);
			SerializeConfig serializeConfig = new SerializeConfig();
            serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
            String result = JSONObject.toJSONString(jsonObject,serializeConfig);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "查询汇报列表时发生异常！!");
			return jsonObject.toJSONString();
		}
	}

	/**
	 * 添加回复
	 * @param communionId
	 * @param content
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("replay")
	@ResponseBody
	public String replay(Integer communionId,String content, HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject();
		try {
			CommunionReply communionReply=new CommunionReply();
			User currUser = SystemContext.getCurrentUser(request);
			Organise organise = mybatisAutoMapperService.selectByPrimaryKey(OrganiseMapper.class, currUser.getOrgId(), Organise.class);
			communionReply.setReplyUser(currUser.getUserName());
			communionReply.setCommunionId(communionId);
			communionReply.setOrgCode(organise.getOrgCode().toString());
			communionReply.setContent(content);
			communionReply.setReplyTime(new Date());
			communionReplyService.add(communionReply);
			
			jsonObject.put("success", true);
			return jsonObject.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "查询汇报列表时发生异常！!");
			return jsonObject.toJSONString();
		}
		
	}
	

}

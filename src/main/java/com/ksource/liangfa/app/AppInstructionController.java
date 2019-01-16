package com.ksource.liangfa.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.util.JsonMapper;
import com.ksource.liangfa.domain.InstructionReceive;
import com.ksource.liangfa.domain.InstructionReply;
import com.ksource.liangfa.domain.InstructionSend;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.InstructionReceiveMapper;
import com.ksource.liangfa.mapper.InstructionReplyMapper;
import com.ksource.liangfa.mapper.InstructionSendMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.instruction.InstructionReceiveService;
import com.ksource.liangfa.service.instruction.InstructionReplyService;
import com.ksource.liangfa.service.instruction.InstructionSendService;
import com.ksource.liangfa.service.system.DistrictService;
import com.ksource.liangfa.service.system.InstantMessageService;
import com.ksource.liangfa.service.system.OrgService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 工作指令
 * @author 符家鑫
 * @date 2017
 */

@Controller
@RequestMapping("/app/instruction")
public class AppInstructionController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AppInstructionController.class);
	
	@Autowired
	InstructionSendService instructionSendService;
	@Autowired
	InstructionReceiveService instructionReceiveService;
	@Autowired
	InstructionSendMapper instructionSendMapper;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	InstructionReplyMapper instructionReplyMapper;
	@Autowired
	InstructionReplyService instructionReplyService;
	@Autowired
	DistrictService districtService;
	@Autowired
	OrgService orgService;
    @Autowired
	private InstantMessageService instantMessageService;
	
	/**
	 * 查询我发送的指令
	 * @param instructionSend
	 * @param page
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("mySend")
	@ResponseBody
	public String search(InstructionSend instructionSend,String page,HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject();
		JsonMapper jsonMapper = JsonMapper.buildNonEmptyMapper();
		try {
			User user = SystemContext.getCurrentUser(request);
			Organise currOrg = user.getOrganise();
			instructionSend.setSendOrg(currOrg.getOrgCode());
			PaginationHelper<InstructionSend> list = instructionSendService.find(instructionSend, page);
            jsonObject.put("success", true);
            jsonObject.put("msg", "查询成功");
            jsonObject.put("list", list.getList());
            jsonObject.put("totalPageNum", list.getTotalPageNum());
			return jsonMapper.toJson(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "获取我发送的工作指令发生异常!");
			return jsonObject.toJSONString();
		}
	}
	
	
	/**
	 * 获取发给我的工作指令
	 * @param instructionReceive
	 * @param info
	 * @param page
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("myInstruction")
	@ResponseBody
	public String myInstruction(String title,String page,HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject();
		InstructionReceive instructionReceive=new InstructionReceive();
		InstructionSend instructionSend=new InstructionSend();
		Map<String,Object> map=new HashMap<String,Object>();
		PaginationHelper<InstructionReceive> instructions = new PaginationHelper<InstructionReceive>();
		try {
			User user = SystemContext.getCurrentUser(request);
			String orgCode = user.getOrganise().getOrgCode().toString();
			String districtCode=user.getOrganise().getDistrictCode();
			Integer districtJB=districtService.selectByPrimaryKey(districtCode).getJb();
			if(districtJB==2){//如果为市级单位，查询我发送的工作指令
				instructionSend.setSendOrg(user.getOrganise().getOrgCode());
				instructionSend.setTitle(title);
				PaginationHelper<InstructionSend> list = instructionSendService.find(instructionSend, page);
				List<InstructionReceive> receiveList=new ArrayList<InstructionReceive>();
				//查询指令回复数量
				if(list.getList().size()>0){
					for(InstructionSend send:list.getList()){
						map.put("instructionId", send.getInstructionId());
						//查询回复数量
						int count=instructionReplyService.getReplyCount(map);
						//把InstructionSend转换为InstructionReceive实体
						InstructionReceive receive=new InstructionReceive();
						receive.setReplyCount(count);
						//查询receiveId
						InstructionReceive param=new InstructionReceive();
						param.setInstructionId(send.getInstructionId());
						List<InstructionReceive> list1=instructionReceiveService.queryAll(param);
						if(list1.size()>0){
							InstructionReceive temp=list1.get(0);
							receive.setReceiveId(temp.getReceiveId());
						}
						receive.setReceiveOrg("");
						receive.setCompleteTime(send.getSendTime());
						receive.setInstructionId(send.getInstructionId());
						receive.setSendOrg(send.getSendOrg());
						receive.setSendOrgName(send.getSendOrgName());
						receive.setSendTime(send.getSendTime());
						receive.setTitle(send.getTitle());
						receive.setContent(send.getContent());
						receive.setStatus(3);
						receiveList.add(receive);
					}
				}
				jsonObject.put("list", receiveList);
				jsonObject.put("totalPageNum", list.getTotalPageNum());
			}else if(districtJB==3){//如果为县级单位，查询我接收的工作指令
				instructionReceive.setReceiveOrg(orgCode);
				instructionReceive.setTitle(title);
				//待签收
				instructionReceive.setStatus(1);
				instructions = instructionReceiveService.find(instructionReceive, page,null);
				//查询指令回复数量
				if(instructions.getList().size()>0){
					for(InstructionReceive receive:instructions.getList()){
						map.put("instructionId", receive.getInstructionId());
						int count=instructionReplyService.getReplyCount(map);
						receive.setReplyCount(count);
					}
				}
				jsonObject.put("list", instructions.getList());
				jsonObject.put("totalPageNum", instructions.getTotalPageNum());
			}
			
            jsonObject.put("success", true);
            jsonObject.put("msg", "查询成功");
			SerializeConfig serializeConfig = new SerializeConfig();
            serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
            String result = JSONObject.toJSONString(jsonObject,serializeConfig);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "获取我发接收的工作指令发生异常!");
			return jsonObject.toJSONString();
		}
	}
	
	
	//工作指令详情
	@RequestMapping("detail")
	@ResponseBody
	public String detail(String receiveId,HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		receiveId=StringUtils.trimToEmpty(receiveId);
		try {
			InstructionReceive instructionReceive = instructionReceiveService.selectByPrimaryKey(Integer.parseInt(receiveId));
			Organise organise = orgService.selectByPrimaryKey(instructionReceive.getSendOrg());
			if(organise != null){
			    instructionReceive.setSendOrgName(organise.getOrgName());  
			}
			PublishInfoFileExample example = new PublishInfoFileExample();
			example.createCriteria().andFileTypeEqualTo(Const.TABLE_INSTRUCTION_SEND).andInfoIdEqualTo(instructionReceive.getInstructionId());
			List<PublishInfoFile> files = mybatisAutoMapperService.selectByExample(PublishInfoFileMapper.class, example, PublishInfoFile.class);
			
			User currUser = SystemContext.getCurrentUser(request);
			//更新消息详情
			instantMessageService.upReadStatus(receiveId, currUser);
			
			jsonObject.put("success", true);
			jsonObject.put("instructionSend", instructionReceive);
			jsonObject.put("fileList", files);
			SerializeConfig serializeConfig = new SerializeConfig();
            serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
            String result = JSONObject.toJSONString(jsonObject,serializeConfig);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "获取工作指令详情时发生异常!");
			return jsonObject.toJSONString();
		}
	}
	
	/**
	 * 查看回复记录
	 * @param instructionId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("replays")
	@ResponseBody
	public String replayList(Integer instructionId,HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject();
		try {
			InstructionReply param=new InstructionReply();
			param.setInstructionId(instructionId);
			List<InstructionReply> replys=new ArrayList<InstructionReply>();
			replys=instructionReplyMapper.getListByInstructionId(param);
			jsonObject.put("success", true);
			jsonObject.put("replys",replys);
			SerializeConfig serializeConfig = new SerializeConfig();
            serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
            String result = JSONObject.toJSONString(jsonObject,serializeConfig);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "获取工作指令回复列表时发生异常!");
			return jsonObject.toJSONString();
		}
	}
	
	
	/**
	 * 保存工作指令回复
	 * @param instructionId
	 * @param content
	 * @param request
	 * @return
	 */
	@RequestMapping("reply")
	@ResponseBody
	public String replay(Integer instructionId,String content,HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		try {
			User user = SystemContext.getCurrentUser(request);
			Organise organise = mybatisAutoMapperService.selectByPrimaryKey(OrganiseMapper.class, user.getOrgId(), Organise.class);
			InstructionReply instructionReply = new InstructionReply();
			instructionReply.setContent(content);
			instructionReply.setReplyTime(new Date());
			instructionReply.setOrgCode(organise.getOrgCode().toString());
			instructionReply.setReplyUser(user.getUserName());
			instructionReply.setInstructionId(instructionId);
			instructionReplyService.insert(instructionReply);
			jsonObject.put("success", true);
			jsonObject.put("msg", "工作指令回复成功!");
			return jsonObject.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "保存工作指令时发生异常!");
			return jsonObject.toJSONString();
		}
	}
	
	
	/**
	 * 签收工作指令
	 * @param receiveId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sign")
	@ResponseBody
	public String sign(Integer receiveId,HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject();
		try {
			InstructionReceive instructionReceive = new InstructionReceive();
			instructionReceive.setReceiveId(receiveId);
			instructionReceive.setStatus(Const.READ_STATUS_YES);
			instructionReceive.setSignTime(new Date());
			mybatisAutoMapperService.updateByPrimaryKeySelective(InstructionReceiveMapper.class, instructionReceive);
			jsonObject.put("success", true);
			jsonObject.put("msg", "工作指令签收成功！");
			return jsonObject.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "保存工作指令时发生异常!");
			return jsonObject.toJSONString();
		}
	}
	
	
	

}

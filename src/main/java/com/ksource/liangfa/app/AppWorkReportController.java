package com.ksource.liangfa.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.WorkReport;
import com.ksource.liangfa.domain.WorkReportReceive;
import com.ksource.liangfa.domain.WorkReportReply;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.mapper.WorkReportReplyMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.instruction.WorkReportReceiveService;
import com.ksource.liangfa.service.instruction.WorkReportReplyService;
import com.ksource.liangfa.service.instruction.WorkReportService;
import com.ksource.liangfa.service.system.DistrictService;
import com.ksource.liangfa.service.system.InstantMessageService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 工作汇报
 * @author 符家鑫
 * @date 2017
 */

@Controller
@RequestMapping("/app/workReport")
public class AppWorkReportController{
	
	@Autowired
	WorkReportService workReportService;
	@Autowired
	WorkReportReceiveService workReportReceiveService;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	WorkReportReplyService workReportReplyService;
	@Autowired
	WorkReportReplyMapper workReportReplyMapper;
	@Autowired
	DistrictService districtService;
	@Autowired
	private InstantMessageService instantMessageService;

	/**
	 * 查询我发送的工作汇报
	 * @param workReport
	 * @param page
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@ResponseBody
	public String list(String title,String page,HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject();
		PaginationHelper<WorkReport> workReportList = new PaginationHelper<WorkReport>();
		WorkReport workReport=new WorkReport();
		try {
			workReport.setTitle(title);
			User currUser = SystemContext.getCurrentUser(request);
			Organise currOrg = currUser.getOrganise();
			workReport.setSendOrg(currOrg.getOrgCode().toString());
			workReportList = workReportService.find(workReport, page);	
            jsonObject.put("success", true);
            jsonObject.put("msg", "查询成功");
            jsonObject.put("list", workReportList.getList());
            jsonObject.put("totalPageNum", workReportList.getTotalPageNum());
			SerializeConfig serializeConfig = new SerializeConfig();
            serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
            String result = JSONObject.toJSONString(jsonObject,serializeConfig);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "查询我发送的工作汇报时发生异常！!");
			return jsonObject.toJSONString();
		}
	}
	
	
	/**
	 * 查询我接收的工作汇报
	 * @param workReportReceive
	 * @param info
	 * @param page
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("myReceiveList")
	@ResponseBody
	public String myReceiveList(String title,String page,HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject();
		WorkReportReceive workReportReceive=new WorkReportReceive();
		PaginationHelper<WorkReport> workReportList = new PaginationHelper<WorkReport>();
		List<WorkReportReceive> receiveList=new ArrayList<WorkReportReceive>();
		WorkReport workReport=new WorkReport();
		try {
			User currUser = SystemContext.getCurrentUser(request);
			Organise currOrg = currUser.getOrganise();		
			String districtCode=currOrg.getDistrictCode();
			Integer districtJB=districtService.selectByPrimaryKey(districtCode).getJb();
			if(districtJB==2){//如果为市级单位，查询我接收的工作汇报
				workReportReceive.setReceiveOrg(currOrg.getOrgCode().toString());
				workReportReceive.setTitle(title);
				PaginationHelper<WorkReportReceive> workReportReceiveList = workReportReceiveService.find(workReportReceive, page);
				jsonObject.put("list", workReportReceiveList.getList());
	            jsonObject.put("totalPageNum", workReportReceiveList.getTotalPageNum());
			}else if(districtJB==3){//如果为县级单位，查询我发送的工作汇报
				workReport.setTitle(title);
				workReport.setSendOrg(currOrg.getOrgCode().toString());
				workReportList = workReportService.find(workReport, page);
				if(workReportList.getList().size()>0){
					for(WorkReport workReport1:workReportList.getList()){
						//把WorkReport转换为WorkReportReceive
						WorkReportReceive receive =new WorkReportReceive();
						//为receiveId赋值
						Map<String,Object> param=new HashMap<String,Object>();
						param.put("reportId", workReport1.getReportId());
						List<WorkReportReceive> list1 =workReportReceiveService.getListByReportId(param);
						if(list1.size()>0){
							WorkReportReceive temp=list1.get(0);
							receive.setReceiveId(temp.getReceiveId());
						}
						receive.setReportId(workReport1.getReportId());
						receive.setSendOrg(workReport1.getSendOrg());
						receive.setSendOrgName(workReport1.getSendOrgName());
						receive.setSendTime(workReport1.getSendTime());
						receive.setTitle(workReport1.getTitle());
						receive.setContent(workReport1.getContent());
						receiveList.add(receive);
					}
				}
				
				jsonObject.put("list", receiveList);
	            jsonObject.put("totalPageNum", workReportList.getTotalPageNum());
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
			jsonObject.put("msg", "查询发送给我的工作汇报时发生异常！!");
			return jsonObject.toJSONString();
		}
	}
	
	/**
	 * 工作汇报下发详情信息
	 * @param reportId
	 * @param flag
	 * @param request
	 * @return
	 */
	@RequestMapping("detail")
	@ResponseBody
	public String detail(Integer reportId,HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		try {
			WorkReport workReport= new WorkReport();
			PublishInfoFileExample example = new PublishInfoFileExample();
			List<PublishInfoFile> files = new ArrayList<PublishInfoFile>();
			workReport= workReportService.selectByPrimaryKey(reportId);
			workReport.setContent(StringUtils.Html2Text(workReport.getContent()));
			example.createCriteria().andFileTypeEqualTo(Const.TABLE_WORK_REPORT).andInfoIdEqualTo(reportId);
			files = mybatisAutoMapperService.selectByExample(PublishInfoFileMapper.class, example, PublishInfoFile.class);
			
			User currUser = SystemContext.getCurrentUser(request);
			//更新消息详情
			instantMessageService.upReadStatus(reportId.toString(), currUser);
			
			jsonObject.put("success", true);
			jsonObject.put("fileList", files);
			jsonObject.put("workReport", workReport);
			SerializeConfig serializeConfig = new SerializeConfig();
            serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
            String result = JSONObject.toJSONString(jsonObject,serializeConfig);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "查询工作汇报详情时发生异常！!");
			return jsonObject.toJSONString();
		}
	}
	
	/**
	 * 新增工作汇报下发信息
	 * @param reportId
	 * @param content
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("replay")
	@ResponseBody
	public String saveReplay(Integer reportId,String content, HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject();
		try {
			WorkReportReply workReportReply=new WorkReportReply();
			User currUser = SystemContext.getCurrentUser(request);
			Organise organise = mybatisAutoMapperService.selectByPrimaryKey(OrganiseMapper.class, currUser.getOrgId(), Organise.class);
			workReportReply.setReplyUser(currUser.getUserName());
			workReportReply.setOrgCode(organise.getOrgCode().toString());
			workReportReply.setReportId(reportId);
			workReportReply.setContent(content);
			workReportReply.setReplyTime(new Date());
			workReportReplyService.add(workReportReply);
			jsonObject.put("success", true);
			jsonObject.put("msg", "添加汇报回复成功！");
			return jsonObject.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "添加汇报回复时发生异常！!");
			return jsonObject.toJSONString();
		}
	}
	
	/**
	 * 查询回复列表
	 * @param reportId
	 * @param page
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("replayList")
	@ResponseBody
	public String replayList(Integer reportId,String page,HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject();
		try {
			WorkReportReply param=new WorkReportReply();
			param.setReportId(reportId);
			List<WorkReportReply> list=new ArrayList<WorkReportReply>();
			list=workReportReplyMapper.getListByReportId(param);
			jsonObject.put("success", true);
			jsonObject.put("workReportReplyList",list);
			SerializeConfig serializeConfig = new SerializeConfig();
            serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
            String result = JSONObject.toJSONString(jsonObject,serializeConfig);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "查询回复列表时发生异常！!");
			return jsonObject.toJSONString();
		}
	}
	
	
}

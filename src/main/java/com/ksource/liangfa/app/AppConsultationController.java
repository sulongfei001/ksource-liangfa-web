package com.ksource.liangfa.app;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.CaseConsultation;
import com.ksource.liangfa.domain.CaseConsultationContent;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.service.casehandle.CaseConsultationService;
import com.ksource.liangfa.service.system.InstantMessageService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 案件咨询
 * @author lijiajia
 * @date 2017
 */

@Controller
@RequestMapping("/app/consultation")
public class AppConsultationController{
	
	@Autowired
	private CaseConsultationService caseConsultationService;
    @Autowired
	private InstantMessageService instantMessageService;

	/**
	 * 案件咨询查询
	 * @param title
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("query")
	@ResponseBody
	public String query (String title,String page,HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
    	User currUser = SystemContext.getCurrentUser(request);
	    Organise organise=currUser.getOrganise();
        CaseConsultation caseConsultation=new CaseConsultation();
        PaginationHelper<CaseConsultation> caseConsultationlist=new PaginationHelper<CaseConsultation>();
    	try {
    		caseConsultation.setTitle(title);
    		caseConsultation.setInputer(currUser.getUserId());
    		caseConsultation.setState(Const.CASE_CONSULTCATION_START);
			//如果用户是行政单位，查询我发起的咨询，其他单位查询我接收到的咨询
			if(organise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG)){
				caseConsultation.setType("3");
				//查询我发起的咨询
				caseConsultationlist=caseConsultationService.search(caseConsultation,page);
			}else{
				//查询我接收的咨询
				caseConsultationlist=caseConsultationService.receiveList(caseConsultation,page);
			}
			jsonObject.put("success",true);
			jsonObject.put("msg","案件咨询信息查询成功！");
    	    jsonObject.put("list",caseConsultationlist.getList());
    	    jsonObject.put("totalPageNum", caseConsultationlist.getTotalPageNum());
    	    SerializeConfig serializeConfig = new SerializeConfig();
            serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
            String result = JSONObject.toJSONString(jsonObject,serializeConfig);
    	    return result;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success",false);
			jsonObject.put("msg","案件咨询信息查询失败！");
			return jsonObject.toJSONString();
		}
	}
	
	
	/**
	 * 案件咨询详情
	 * @param id
	 * @return
	 */
    @RequestMapping("detail")
    @ResponseBody
    public String detail(Integer id,HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
    	CaseConsultation caseConsultation=new CaseConsultation();
    	try {
    	    if(id == null){
    	        throw new Exception("id为空");
    	    }
    		caseConsultation=caseConsultationService.selectByPK(id);
    		caseConsultation.setContent(StringUtils.Html2Text(caseConsultation.getContent()));
    		
			User currUser = SystemContext.getCurrentUser(request);
			//更新消息详情
			instantMessageService.upReadStatus(id.toString(), currUser);
    		
        	jsonObject.put("success",true);
        	jsonObject.put("msg","案件咨询详情查询成功！");
        	jsonObject.put("caseConsultation",caseConsultation);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success",false);
			jsonObject.put("msg","案件咨询详情查询失败："+e.getMessage());
		}
        String result = JSONObject.toJSONString(jsonObject,serializeConfig);
        return result;
    }
    
    /**
	 * 查询案件咨询回复信息
	 * @param id
	 * @return
	 */
    @RequestMapping("replyList")
    @ResponseBody
    public String replyList(Integer id,String page){
    	JSONObject jsonObject = new JSONObject();
    	PaginationHelper<CaseConsultationContent> replylist=new PaginationHelper<CaseConsultationContent>();
    	Map<String,Object> map=new HashMap<String,Object>();
    	try {
    		map.put("caseConsultationId", id);
    		replylist=caseConsultationService.findReplyList(map, page);
            jsonObject.put("success", true);
            jsonObject.put("msg", "查询成功");
            jsonObject.put("list", replylist.getList());
            jsonObject.put("totalPageNum", replylist.getTotalPageNum());
    		SerializeConfig serializeConfig = new SerializeConfig();
            serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
            String result = JSONObject.toJSONString(jsonObject,serializeConfig);
    	    return result;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success",false);
			jsonObject.put("msg","案件咨询回复信息查询失败！");
			return jsonObject.toJSONString();
		}
    }
    
    /**
     * 添加案件咨询回复信息
     * @param consultationId
     * @param content
     * @param request
     * @return
     */
    @RequestMapping("reply")
    @ResponseBody
    public String reply(Integer consultationId,String content,HttpServletRequest request){
    	JSONObject jsonObject = new JSONObject();
    	User currUser = SystemContext.getCurrentUser(request);
    	CaseConsultationContent consultationcontent=new CaseConsultationContent();
    	try {
    		if(consultationId!=null && StringUtils.isNotBlank(content)){
    			consultationcontent.setCaseConsultationId(consultationId);
        		consultationcontent.setContent(content);
        		consultationcontent.setUserId(currUser.getUserId());
        		caseConsultationService.addContext(consultationcontent,null);
        		jsonObject.put("success",true);
    			jsonObject.put("msg","案件咨询回复成功！");
    		}else{
    			jsonObject.put("success",false);
    			jsonObject.put("msg","咨询id或者回复内容为空！");
    		}
    		return jsonObject.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success",false);
			jsonObject.put("msg","案件咨询回复失败！");
			return jsonObject.toJSONString();
		}
    }
}

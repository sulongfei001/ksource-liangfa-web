package com.ksource.liangfa.app;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.util.DictionaryManager;
import com.ksource.common.util.JsonMapper;
import com.ksource.liangfa.domain.ClueAccept;
import com.ksource.liangfa.domain.ClueDispatch;
import com.ksource.liangfa.domain.ClueInfo;
import com.ksource.liangfa.domain.ClueInfoReply;
import com.ksource.liangfa.domain.Dictionary;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.ClueDispatchMapper;
import com.ksource.liangfa.service.casehandle.ClueInfoService;
import com.ksource.liangfa.service.clueInfoReply.ClueInfoReplyService;
import com.ksource.liangfa.service.system.InstantMessageService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 线索管理
 * @author 符家鑫
 * @date 2017
 */
@Controller
@RequestMapping("/app/clue")
public class AppClueController {
	
	@Autowired
	private ClueInfoService clueInfoService;
	@Autowired
	private ClueDispatchMapper clueDispatchMapper;
	@Autowired
	private ClueInfoReplyService clueInfoReplyService;
    @Autowired
	private InstantMessageService instantMessageService;
	
	private static final Logger logger = LoggerFactory.getLogger(AppClueController.class);
	
	/**
	 * 待分派 线索查询列表
	 * @param request
	 * @param clueInfo
	 * @param page
	 * @return
	 */
	@RequestMapping("getClueInfoList")
	@ResponseBody
	public String getClueInfoList(HttpServletRequest request,String clueNo,String page){
		JSONObject jsonObject = new JSONObject();// 封装异常信息或者没有districtCode的请求提醒
		ClueInfo clueInfo=new ClueInfo();
		try {
			clueInfo.setClueNo(clueNo);
			//市级用户可以查询市县的线索，县级用户只能查询县级线索
			User currentUser = SystemContext.getCurrentUser(request);
			Organise org = currentUser.getOrganise();
			String districtCode = org.getDistrictCode();
			//设置线索状态
			clueInfo.setClueState(Const.CLUE_STATE_DAIFENPAI);
			PaginationHelper<ClueInfo> clueInfoList = null ;
			clueInfo.setDistrictCode(StringUtils.getShortRegion(districtCode));
			//行政机关
			if(org.getOrgType().equals(Const.ORG_TYPE_XINGZHENG)){
				clueInfo.setOrgPath(org.getOrgPath());
				clueInfoList = clueInfoService.getClueInfoList(clueInfo,page);
			}else if(org.getOrgType().equals(Const.ORG_TYPE_JIANCHAYUAN)){
				//检察院
				clueInfoList = clueInfoService.getClueInfoList(clueInfo,page);
			}
			jsonObject.put("success", true);
			jsonObject.put("msg", "查询成功");
			jsonObject.put("list", clueInfoList.getList());
			jsonObject.put("totalPageNum", clueInfoList.getTotalPageNum());
			SerializeConfig serializeConfig = new SerializeConfig();
            serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
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
	 * 查询已分派
	 * @param request
	 * @param clueInfo
	 * @param page
	 * @return
	 */
	@RequestMapping("getHaveFenpaiClueList")
	@ResponseBody
	public String getHaveFenpaiClueList(HttpServletRequest request,String clueNo,String page){
		JSONObject jsonObject = new JSONObject();// 封装异常信息或者没有districtCode的请求提醒
		ClueInfo clueInfo=new ClueInfo();
		try {
			clueInfo.setClueNo(clueNo);
			//市级用户可以查询市县的线索，县级用户只能查询县级线索
			User currentUser = SystemContext.getCurrentUser(request);
			clueInfo.setDistributeUserId(currentUser.getUserId());
			PaginationHelper<ClueInfo> clueInfoList = clueInfoService.getClueInfoList(clueInfo,page);
            jsonObject.put("success", true);
            jsonObject.put("msg", "查询成功");
            jsonObject.put("list", clueInfoList.getList());
            jsonObject.put("totalPageNum", clueInfoList.getTotalPageNum());
			SerializeConfig serializeConfig = new SerializeConfig();
            serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
            String result = JSONObject.toJSONString(jsonObject,serializeConfig);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "已分派线索列表获取发生异常！!");
			return jsonObject.toJSONString();
		}
	}
	
	/**
	 * 待处理线索查询列表
	 * @param request
	 * @param clueDispatch
	 * @param page
	 * @return
	 */
	@RequestMapping("notDealClueList")
	@ResponseBody
	public String notDealClueList(String clueNo,HttpServletRequest request,String page){
		JSONObject jsonObject = new JSONObject();// 封装异常信息或者没有districtCode的请求提醒
		ClueDispatch clueDispatch=new ClueDispatch();
		try {
			clueDispatch.setClueNo(clueNo);
			User currentUser = SystemContext.getCurrentUser(request);
			Integer orgId = currentUser.getOrgId();
			clueDispatch.setReceiveOrg(orgId);
			PaginationHelper<ClueDispatch> clueDispatchList = clueInfoService.notDealClueList(clueDispatch,page);
            jsonObject.put("success", true);
            jsonObject.put("msg", "查询成功");
            jsonObject.put("list", clueDispatchList.getList());
            jsonObject.put("totalPageNum", clueDispatchList.getTotalPageNum());
			SerializeConfig serializeConfig = new SerializeConfig();
            serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
            String result = JSONObject.toJSONString(jsonObject,serializeConfig);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "待处理线索列表获取发生异常！!");
			return jsonObject.toJSONString();
		}
	}
	
	/**
	 * 已处理线索查询
	 * @param request
	 * @param clueDispatch
	 * @param page
	 * @return
	 */
	@RequestMapping("getHaveDealtClueList")
	@ResponseBody
	public String getHaveDealtClueList(String clueNo,HttpServletRequest request,String page){
		JSONObject jsonObject = new JSONObject();// 封装异常信息或者没有districtCode的请求提醒
		ClueDispatch clueDispatch=new ClueDispatch();
		try {
			clueDispatch.setClueNo(clueNo);
			User currentUser = SystemContext.getCurrentUser(request);
			Integer orgId = currentUser.getOrgId();
			clueDispatch.setReceiveOrg(orgId);
			clueDispatch.setDispatchCasestate(Const.CLUE_STATE_YIBANLI);
			PaginationHelper<ClueDispatch> clueDispatchList = clueInfoService.haveDealClueList(clueDispatch,page);
            jsonObject.put("success", true);
            jsonObject.put("msg", "查询成功");
            jsonObject.put("list", clueDispatchList.getList());
            jsonObject.put("totalPageNum", clueDispatchList.getTotalPageNum());
			SerializeConfig serializeConfig = new SerializeConfig();
            serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
            String result = JSONObject.toJSONString(jsonObject,serializeConfig);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "已处理线索列表获取发生异常！!");
			return jsonObject.toJSONString();
		}
	}
	
	/**
	 * 查询线索详情
	 * @param request
	 * @param clueId
	 * @param anchor
	 * @return
	 */
	@RequestMapping("getClueDetailView")
	@ResponseBody
	public String getClueDetailView(HttpServletRequest request,Integer clueId){
		JSONObject jsonObject = new JSONObject();// 封装异常信息或者没有districtCode的请求提醒
		try {
			User user = SystemContext.getCurrentUser(request);
			List<Dictionary> clueResourceList = DictionaryManager.getDictList("caseSource");
			//查询线索详情
			ClueInfo clueInfo = clueInfoService.selectByPrimaryKey(clueId);
			clueInfo.setContent(StringUtils.Html2Text(clueInfo.getContent()));
			for (Dictionary dic:clueResourceList) {
				if(dic.getDtCode().equals(String.valueOf(clueInfo.getClueResource()))){
					clueInfo.setClueResourceName(dic.getDtName());
					break;
				}
			}
			//更新线索阅读状态
			ClueDispatch dispatch=new ClueDispatch();
			dispatch.setClueId(clueId);
			dispatch.setReceiveOrg(user.getOrganise().getOrgCode());
			dispatch.setReadTime(new Date());
			dispatch.setReadUser(user.getUserId());
			dispatch.setDispatchReadstate(1);
			clueDispatchMapper.updateDispatchState(dispatch);
			
			User currUser = SystemContext.getCurrentUser(request);
			//更新消息详情
			instantMessageService.upReadStatus(clueId.toString(), currUser);
			
			jsonObject.put("success", true);
			jsonObject.put("clueInfo", clueInfo);
			SerializeConfig serializeConfig = new SerializeConfig();
            serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
            String result = JSONObject.toJSONString(jsonObject,serializeConfig);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "线索详情获取发生异常！!");
			return jsonObject.toJSONString();
		}
	}
	
	/**
	 * 查询线索来源
	 * @return
	 */
	@RequestMapping("clueResourceList")
	@ResponseBody
	public String clueResourceList(){
		JSONObject jsonObject = new JSONObject();
		JsonMapper jsonMapper = JsonMapper.buildNonEmptyMapper();
		try {
			List<Dictionary> clueResourceList = DictionaryManager.getDictList("caseSource");
			jsonObject.put("success", true);
			jsonObject.put("clueResourceList", clueResourceList);
			return jsonMapper.toJson(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "线索来源列表获取发生异常！!");
			return jsonObject.toJSONString();
		}
	}
	
	
	/**
	 * 线索查阅情况
	 * @param request
	 * @param clueId
	 * @param anchor
	 * @return
	 */
	@RequestMapping("getClueReadInfo")
	@ResponseBody
	public String getClueReadInfo(Integer clueId){
		JSONObject jsonObject = new JSONObject();
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			map.put("clueId", clueId);
			List<ClueDispatch> list=clueInfoService.getClueReadInfo(map);
			jsonObject.put("success", true);
			jsonObject.put("list", list);
			SerializeConfig serializeConfig = new SerializeConfig();
            serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
            String result = JSONObject.toJSONString(jsonObject,serializeConfig);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "线索查阅情况获取发生异常！!");
			return jsonObject.toJSONString();
		}
	}

	
	/**
	 * 线索办理情况
	 * @param request
	 * @param clueId
	 * @param anchor
	 * @return
	 */
	@RequestMapping("getClueTransactInfo")
	@ResponseBody
	public String getClueTransactInfo(Integer clueId){
		JSONObject jsonObject = new JSONObject();
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			map.put("clueId", clueId);
			List<ClueInfoReply> clueInfoReplyList = clueInfoReplyService.getClueCaseList(clueId);
	        if(clueInfoReplyList.size()>0){
                for(ClueInfoReply reply:clueInfoReplyList){
                    if(reply !=null && StringUtils.isNotBlank(reply.getCaseNo())){
                        reply.setContent("已转换为行政受理案件，案件编号："+reply.getCaseNo());
                    }
                }
	        }
			jsonObject.put("success", true);
			jsonObject.put("list", clueInfoReplyList);
			SerializeConfig serializeConfig = new SerializeConfig();
            serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
            String result = JSONObject.toJSONString(jsonObject,serializeConfig);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "线索办理情况获取发生异常！");
			return jsonObject.toJSONString();
		}
	}
}

package com.ksource.liangfa.web.casehandle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.util.DictionaryManager;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.ClueCaseAndReply;
import com.ksource.liangfa.domain.ClueDispatch;
import com.ksource.liangfa.domain.ClueInfo;
import com.ksource.liangfa.domain.ClueInfoReply;
import com.ksource.liangfa.domain.Dictionary;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.service.casehandle.ClueInfoService;
import com.ksource.liangfa.service.clueInfoReply.ClueInfoReplyService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping("/casehandle/clueInfo/reply")
public class ClueInfoReplyController {
	
	@Autowired
	private ClueInfoReplyService clueInfoReplyService;
	@Autowired
	private SystemDAO systemDao;
	@Autowired
	private ClueInfoService clueInfoService;
	
	@RequestMapping("toReplyView")
	public String toEeplyView(ModelMap mm,Integer clueId,Integer dispatchId){
		mm.addAttribute("clueId", clueId);
		mm.addAttribute("dispatchId", dispatchId);
		return "casehandle/clueInfoReply/addClueReply";
	}
	
	@InitBinder
    public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }
	
	/**
	 * 添加回复
	 * @param request
	 * @param clueReply
	 * @param multipartRequest
	 * @return
	 */
	@RequestMapping("addReply")
	public ModelAndView addReply(HttpServletRequest request,ClueInfoReply clueReply,MultipartHttpServletRequest multipartRequest){
		ModelAndView mv = new ModelAndView("casehandle/clueInfoReply/addClueReply");
		
		
		User user = SystemContext.getCurrentUser(request);
		String userId = user.getUserId();
		
		clueReply.setCreateTime(new Date());
		clueReply.setCreateUserId(Integer.valueOf(userId));
		clueReply.setReplyId(systemDao.getSeqNextVal(Const.TABLE_CLUE_REPLY));
		
		Boolean addSuccess = null;
		if(clueReply.getClueInfoId()!= null ){
			addSuccess = clueInfoReplyService.add(clueReply,multipartRequest,user.getOrganise().getOrgCode());
		}
		
		mv.addObject("clueId", clueReply.getClueInfoId());
		mv.addObject("info",addSuccess);
		return mv;
	}
	
	/**
	 * 查询回复列表
	 * @param clueInfoId
	 * @return
	 */
	@RequestMapping("replyList")
	public ModelAndView replyList(Integer clueInfoId){
		ModelAndView mv = new ModelAndView("casehandle/clueInfoReply/clueReplyList");
		//查找线索
		ClueInfo clueInfo = clueInfoService.selectByPrimaryKey(clueInfoId);
		
		List<Dictionary> clueResourceList = DictionaryManager.getDictList("caseSource");
		
		//查找线索回复
		List<ClueInfoReply> replys=clueInfoReplyService.getListByClueInfoId(clueInfo);
		
		List<ClueDispatch> readList = clueInfoService.clueReadList(clueInfoId);
		
		List<ClueInfoReply> dealList = clueInfoReplyService.getClueCaseList(clueInfoId);
		
		mv.addObject("readList", readList);
		mv.addObject("dealList", dealList);
		mv.addObject("replys",replys);
		mv.addObject("clueInfo",clueInfo);
		mv.addObject("clueResourceList",clueResourceList);
		return mv;
		
	}
	
	@RequestMapping("detil")
	public ModelAndView detil(Integer replyId){
		ModelAndView mv = new ModelAndView("/casehandle/clueInfoReply/detilClueReply");
		ClueInfoReply clueInfoReply = clueInfoReplyService.getReplyById(replyId);
		mv.addObject("clueInfoReply", clueInfoReply);
		return mv;
	}

}

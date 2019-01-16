package com.ksource.liangfa.web.specialactivity;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ResponseMessage;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.domain.SpecialActivity;
import com.ksource.liangfa.domain.SuperviseCase;
import com.ksource.liangfa.domain.SuperviseCaseExample;
import com.ksource.liangfa.domain.SuperviseCaseExample.Criteria;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.mapper.SuperviseCaseMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.specialactivity.SpecialActivityService;
import com.ksource.liangfa.service.specialactivity.SuperviseCaseService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PromptType;
import com.ksource.syscontext.SystemContext;

/**
 *@author wangzhenya
 *@date 2013-4-16 
 *@time 下午3:06:50
 */
@Controller
@RequestMapping(value = "activity/supervise_case")
public class SuperviseCaseController {

	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	private SuperviseCaseService superviseCaseService;
	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	private SpecialActivityService specialActivityService;
	
	private static final String SUPERVISE_CASE_MAIN = "activity/supervise_case_main";
	private static final String SUPERVISE_CASE_ADD = "activity/supervise_case_add";
	private static final String SUPERVISE_CASE_ADDUI = "redirect:/activity/supervise_case/v_add";
	private static final String SUPERVISE_CASE_UPDATE = "activity/supervise_case_update";
	private static final String SUPERVISE_CASE_UPDATEUI = "redirect:/activity/supervise_case/v_update";
	private static final String SUPERVISE_CASE_BACK = "redirect:/activity/supervise_case/back";
	private static final String SUPERVISE_CASE_DETAIL = "activity/supervise_case_detail";
	
	/** 用于保存查询条件(管理页面) */
	private static final String MANAGE_SEARCH_CONDITION = SuperviseCaseController.class
			.getName() + "conditionObjManage";

	/** 用于保存分页的标志(管理页面) */
	private static final String MANAGE_PAGE_MARK = SuperviseCaseController.class
			.getName() + "pageManage";
	
	/**
	 * 督办案件的管理页面
	 * @param superviseCase
	 * @param page
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "main")
	public ModelAndView main(HttpSession session, SuperviseCase superviseCase, String page) {
		ModelAndView view = new ModelAndView(SUPERVISE_CASE_MAIN);
		
		User user = SystemContext.getCurrentUser(session);
		superviseCase.setOrgId(user.getOrgId());
		
		PaginationHelper<SuperviseCase> superviseCases = superviseCaseService.querySuperviseCase(superviseCase, page);
		List<SpecialActivity> specialActivities = specialActivityService.getSpecialActivityName();
		view.addObject("specialActivities", specialActivities);
		view.addObject("superviseCases", superviseCases);
		view.addObject("superviseCase", superviseCase);
		view.addObject("page", page);
		
		// 第一次进入管理页面或空查询时把查询条件删除。
		if ((superviseCase.getTypeId() == null) && (superviseCase.getCaseId() == null) && (page == null)) {
			removeCondition(session, MANAGE_SEARCH_CONDITION, MANAGE_PAGE_MARK);
		} else {
			session.setAttribute(MANAGE_SEARCH_CONDITION, superviseCase);
			session.setAttribute(MANAGE_PAGE_MARK, page);
			view.addObject("page", page);
		}
		
		return view;
	}
	
	/**
	 * 跳转到督办案件的添加页面
	 * @param map
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "v_add")
	public String addUI(ModelMap map,ResponseMessage res) {
		List<SpecialActivity> specialActivities = specialActivityService.getSpecialActivityName();
		map.addAttribute("specialActivities", specialActivities);
		map.addAttribute("info", ResponseMessage.parseMsg(res));
		return SUPERVISE_CASE_ADD;
	}
	
	/**
	 * 添加督办案件
	 * @param superviseCase
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/o_add")
	public String add(SuperviseCase superviseCase, HttpServletRequest request, 
			MultipartHttpServletRequest attachmentFile){
		User user = SystemContext.getCurrentUser(request);
		superviseCase.setSuperviseId(systemDAO.getSeqNextVal(Const.TABLE_SUPERVISE_CASE));
		superviseCase.setUserId(user.getUserId());
		superviseCase.setOrgId(user.getOrgId());
		superviseCase.setCreateTime(new Date());
		superviseCaseService.add(superviseCase, attachmentFile);
		return ResponseMessage.addPromptTypeForPath(SUPERVISE_CASE_ADDUI, PromptType.add);
	}
	
	/**
	 * 跳转到督办案件修改页面
	 * @param map
	 * @param res
	 * @param superviseId
	 * @return
	 */
	@RequestMapping(value = "/v_update")
	public String updateUI(ModelMap map,ResponseMessage res,Integer superviseId) {
		List<SpecialActivity> specialActivities = specialActivityService.getSpecialActivityName();
		SuperviseCase superviseCase = mybatisAutoMapperService.selectByPrimaryKey(SuperviseCaseMapper.class, superviseId, SuperviseCase.class);
		PublishInfoFileExample example = new PublishInfoFileExample();
		example.createCriteria().andFileTypeEqualTo(Const.TABLE_SUPERVISE_CASE).andInfoIdEqualTo(superviseId);
		List<PublishInfoFile> publishInfoFiles = mybatisAutoMapperService.selectByExample(
				PublishInfoFileMapper.class, example, PublishInfoFile.class);
		map.addAttribute("publishInfoFiles", publishInfoFiles);
		map.addAttribute("specialActivities", specialActivities);
		map.addAttribute("superviseCase", superviseCase);
		map.addAttribute("info", ResponseMessage.parseMsg(res));
		return SUPERVISE_CASE_UPDATE;
	}
	
	/**
	 * 修改督办的案件
	 * @param superviseCase
	 * @return
	 */
	@RequestMapping(value = "/o_update")
	public String update(SuperviseCase superviseCase,MultipartHttpServletRequest attachmentFile){
		superviseCaseService.update(superviseCase, attachmentFile);
		return ResponseMessage.addPromptTypeForPath(SUPERVISE_CASE_UPDATEUI + "?superviseId=" + superviseCase.getSuperviseId(), PromptType.update);
	}
	
	/**
	 * 删除督办的案件
	 * @param superviseId
	 * @return
	 */
	@RequestMapping(value = "/delete") 
	public String delete(Integer superviseId) {
		superviseCaseService.delete(superviseId);
		return SUPERVISE_CASE_BACK;
	}
	
	/**
	 * 验证案件编号不能重复
	 * @param caseId
	 * @param superviseId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/check_name")
	public boolean checkName(String caseId, Integer superviseId) {
		String name = caseId.trim();
		SuperviseCaseExample example = new SuperviseCaseExample();
		Criteria criteria = example.createCriteria().andCaseIdEqualTo(name);
		if(superviseId != null) {
			criteria.andSuperviseIdNotEqualTo(superviseId);
		}
		int result = mybatisAutoMapperService.countByExample(SuperviseCaseMapper.class, example);
		if(result > 0) {
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * 删除督办案件的附件
	 * @param fileId
	 */
	@ResponseBody
	@RequestMapping(value="delFile/{fileId}",method=RequestMethod.GET)
    public void delFile(@PathVariable Integer fileId){
		superviseCaseService.deleteFile(fileId);
	}
	
	/**
	 * 督办案件详情显示
	 * @param map
	 * @param superviseId
	 * @return
	 */
	@RequestMapping("/detail")
	public String detail(ModelMap map, Integer superviseId) {
		superviseCaseService.detail(superviseId, map);
		return SUPERVISE_CASE_DETAIL;
	}
	
	/**
	 * 返回操作
	 * @param session
	 * @param number
	 * @param backType
	 * @return
	 */
	@RequestMapping(value = "back")
	public ModelAndView back(HttpSession session, String number, String backType) {
		// 有查询条件,按查询条件返回
		SuperviseCase superviseCase;
		String page;
		String con = MANAGE_SEARCH_CONDITION;
		String pageMark = MANAGE_PAGE_MARK;
		

		if (session.getAttribute(con) != null) {
			superviseCase = (SuperviseCase) session.getAttribute(con);
		} else {
			superviseCase = new SuperviseCase();
			superviseCase.setSuperviseId(-1);
		}

		if (session.getAttribute(pageMark) != null) {
			page = (String) session.getAttribute(pageMark);
		} else {
			page = "1";
		}

		return this.main(session, superviseCase, page);
	}
	
	private void removeCondition(HttpSession session, String con,
			String pageMark) {
		if (session.getAttribute(con) != null) {
			session.removeAttribute(con);
		}

		if (session.getAttribute(pageMark) != null) {
			session.removeAttribute(pageMark);
		}
	}
}

package com.ksource.liangfa.web.info;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.ZhifaInfo;
import com.ksource.liangfa.domain.ZhifaInfoType;
import com.ksource.liangfa.domain.ZhifaInfoTypeExample;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.liangfa.mapper.ZhifaInfoMapper;
import com.ksource.liangfa.mapper.ZhifaInfoTypeMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.info.InfoService;
import com.ksource.liangfa.service.info.ZhifaTypeService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 执法信息维护
 * 
 * @author junxy
 * 
 */
@Controller
@RequestMapping("/info/zhifa")
public class ZhiFaInfoController {

	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	private InfoService infoService;
	@Autowired
	private ZhifaTypeService zhifaTypeService;
	@Autowired
	private SystemDAO systemDAO;

	// 查询请求
	private static final String INFO_SEARCH = "redirect:/info/zhifa/back";
	// 列表页面
	private static final String INFO_LIST = "/info/zhifa/list";
	// 新增页面
	private static final String INFO_ADD = "/info/zhifa/add";
	// 详细页面
	private static final String INFO_DETAIL = "/info/zhifa/view";
	// 修改页面
	private static final String INFO_UPDATE = "/info/zhifa/update";
	// 执法信息查阅
	private static final String ZHIFAINFO_SEARCH = "/info/zhifa/zhifaInfoSearch";
	/** 用于保存查询条件(查阅页面) */
	private static final String CONSULT_SEARCH_CONDITION = ZhiFaInfoController.class
			.getName() + "conditionObjConsult";

	/** 用于保存分页的标志(查阅页面) */
	private static final String CONSULT_PAGE_MARK = ZhiFaInfoController.class
			.getName() + "pageConsult";

	/** 用于保存查询条件(管理页面) */
	private static final String MANAGE_SEARCH_CONDITION = ZhiFaInfoController.class
			.getName() + "conditionObjManage";

	/** 用于保存分页的标志(管理页面) */
	private static final String MANAGE_PAGE_MARK = ZhiFaInfoController.class
			.getName() + "pageManage";

	/** 执法信息维护主界面 */
	@RequestMapping(value = "/manage")
	public String manage(ZhifaInfo info, String page,
			Map<String, Object> model, HttpSession session) {
		User aUser = SystemContext.getCurrentUser(session);
		if (aUser.getUserType() == Const.USER_TYPE_PLAIN) {
			info.setUserId(aUser.getUserId());
		}
		PaginationHelper<ZhifaInfo> infos = infoService.queryZhiFaInfos(info,
				page);

		List<ZhifaInfoType> zhifaInfoTypes = mybatisAutoMapperService
				.selectByExample(ZhifaInfoTypeMapper.class,
						new ZhifaInfoTypeExample(), ZhifaInfoType.class);
		model.put("zhifaInfoTypes", zhifaInfoTypes);
		model.put("infos", infos);
		// 查询条件
		model.put("info", info);
		if ((info.getTitle() == null) && (info.getTypeId() == null)
				&& page == null) {
			removeCondition(session, MANAGE_SEARCH_CONDITION, MANAGE_PAGE_MARK);
		} else {
			session.setAttribute(MANAGE_SEARCH_CONDITION, info);
			session.setAttribute(MANAGE_PAGE_MARK, page);
			model.put("page", page);
		}
		return INFO_LIST;
	}

	/** 进行执法信息查阅操作 */
	@RequestMapping(value = "/zhifaInfoSearch")
	public String zhifaInfoSearch(ZhifaInfo info, String page,
			Map<String, Object> model, HttpSession session) {
		PaginationHelper<ZhifaInfo> infos = infoService.queryZhiFaInfos(info,
				page);
		List<ZhifaInfoType> zhifaInfoTypes = mybatisAutoMapperService
				.selectByExample(ZhifaInfoTypeMapper.class,
						new ZhifaInfoTypeExample(), ZhifaInfoType.class);
		model.put("zhifaInfoTypes", zhifaInfoTypes);
		model.put("infos", infos);
		// 查询条件
		model.put("info", info);

		model.put("backType", "consult");

		if ((info.getTitle() == null) && (info.getTypeId() == null)
				&& page == null) {
			removeCondition(session, CONSULT_SEARCH_CONDITION,
					CONSULT_PAGE_MARK);
		} else {
			session.setAttribute(CONSULT_SEARCH_CONDITION, info);
			session.setAttribute(CONSULT_PAGE_MARK, page);
			model.put("page", page);
		}
		return ZHIFAINFO_SEARCH;

	}

	/** 打开新增页面 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addUI(ZhifaInfoType zhifaInfoType, Map<String, Object> model) {
		List<ZhifaInfoType> zhifaInfoTypes = zhifaTypeService
				.queryZhifaInfoTypes(zhifaInfoType);
		model.put("zhifaInfoTypes", zhifaInfoTypes);
		return INFO_ADD;
	}

	/** 新增信息 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request, ZhifaInfo info) {
		User user = SystemContext.getCurrentUser(request);
		info.setCreateTime(new Date());
		info.setUserId(user.getUserId());
		info.setOrgId(user.getOrgId());
		info.setContent(StringUtils.Html2Text(info.getContent()));
		info.setInfoId(String.valueOf(systemDAO
				.getSeqNextVal(Const.TABLE_ZHIFA_INFO)));
		// mybatisAutoMapperService.insert(ZhifaInfoMapper.class, info);
		infoService.insertZhifaInfo(info);
		return INFO_SEARCH;
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateUI(ZhifaInfoType zhifaInfoType, String infoId,
			Map<String, Object> model) {
		ZhifaInfo info = mybatisAutoMapperService.selectByPrimaryKey(
				ZhifaInfoMapper.class, infoId, ZhifaInfo.class);
		List<ZhifaInfoType> zhifaInfoTypes = zhifaTypeService
				.queryZhifaInfoTypes(zhifaInfoType);
		model.put("zhifaInfoTypes", zhifaInfoTypes);
		model.put("info", info);
		return INFO_UPDATE;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(ZhifaInfo info) {
		info.setContent(StringUtils.Html2Text(info.getContent()));
		// mybatisAutoMapperService.updateByPrimaryKeySelective(
		// ZhifaInfoMapper.class, info);
		infoService.updateZhifaByPrimaryKeySelective(info);
		return INFO_SEARCH;
	}

	@RequestMapping(value = "/view")
	public String detail(String infoId, Map<String, Object> model,
			String backType) {
		ZhifaInfo info = mybatisAutoMapperService.selectByPrimaryKey(
				ZhifaInfoMapper.class, infoId, ZhifaInfo.class);
		User user = mybatisAutoMapperService.selectByPrimaryKey(
				UserMapper.class, info.getUserId(), User.class);
		Organise org = mybatisAutoMapperService.selectByPrimaryKey(
				OrganiseMapper.class, info.getOrgId(), Organise.class);
		ZhifaInfoType zhifaType = mybatisAutoMapperService.selectByPrimaryKey(
				ZhifaInfoTypeMapper.class, info.getTypeId(),
				ZhifaInfoType.class);

		model.put("user", user);
		model.put("org", org);
		model.put("zhifaType", zhifaType);
		model.put("info", info);

		if (backType == null) {
			backType = "manage";
		}
		model.put("backType", backType);
		return INFO_DETAIL;

	}

	@RequestMapping(value = "/delete")
	public String delete(String infoId) {
		infoService.delZhifa(infoId);
		return INFO_SEARCH;
	}
	
	@RequestMapping(value="/batch_delete")
	public String batchDelete(String[] check){
		if(check != null){
		infoService.batchDeleteZhifa(check);
		}
		return INFO_SEARCH;
	}

	// 返回
	@RequestMapping(value = "back")
	public String back(HttpSession session, Map<String, Object> model,
			String back, String backType) {
		// 有查询条件,按查询条件返回
		ZhifaInfo zhifa;
		String page;
		String con = MANAGE_SEARCH_CONDITION;
		String pageMark = MANAGE_PAGE_MARK;

		if (backType != null && backType.equals("consult")) {
			pageMark = CONSULT_PAGE_MARK;
			con = CONSULT_SEARCH_CONDITION;
		}

		if (session.getAttribute(con) != null) {
			zhifa = (ZhifaInfo) session.getAttribute(con);
		} else {
			zhifa = new ZhifaInfo();
			zhifa.setInfoId("");
		}

		if (session.getAttribute(pageMark) != null) {
			page = (String) session.getAttribute(pageMark);
		} else {
			page = "1";
		}
		if (backType != null && backType.equals("consult")) {
			return this.zhifaInfoSearch(zhifa, page, model, session);
		} else {
			return this.manage(zhifa, page, model, session);
		}
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
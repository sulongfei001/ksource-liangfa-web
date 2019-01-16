package com.ksource.liangfa.web.info;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi2.hssf.record.formula.functions.Int;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.lucene.LayIndexAspect;
import com.ksource.common.lucene.LuceneUtil;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.InfoType;
import com.ksource.liangfa.domain.InfoTypeExample;
import com.ksource.liangfa.domain.LayInfo;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.InfoTypeMapper;
import com.ksource.liangfa.mapper.LayInfoMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.info.InfoService;
import com.ksource.liangfa.service.info.LayTypeService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 法律法规维护
 * 
 * @author junxy
 * 
 */
@Controller
@RequestMapping("/info/lay")
public class LayInfoController {
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	private InfoService infoService;
	@Autowired
	private LayTypeService layTypeService;
	@Autowired
	private LayIndexAspect layIndex;
	@Autowired
	private SystemDAO systemDAO;

	// 查询请求
	private static final String INFO_SEARCH = "redirect:/info/lay/back";

	// 列表页面
	private static final String CREATE_INDEX = "/info/lay/layIndex";

	// 列表页面
	private static final String INFO_LIST = "/info/lay/list";

	// 新增页面
	private static final String INFO_ADD = "/info/lay/add";

	// 详细页面
	private static final String INFO_DETAIL = "/info/lay/view";

	// 修改页面
	private static final String INFO_UPDATE = "/info/lay/update";

	// 法律查阅
	private static final String LAY_SEARCH = "/info/lay/laySearch";
	
	private static final String INFOLAY = "/info/lay/infoLay";
	
	private static final String CONSULTLAY = "/info/lay/consultLay";

	/** 用于保存查询条件(查阅页面) */
	private static final String CONSULT_SEARCH_CONDITION = LayInfoController.class
			.getName() + "conditionObjConsult";

	/** 用于保存分页的标志(查阅页面) */
	private static final String CONSULT_PAGE_MARK = LayInfoController.class
			.getName() + "pageConsult";

	/** 用于保存查询条件(管理页面) */
	private static final String MANAGE_SEARCH_CONDITION = LayInfoController.class
			.getName() + "conditionObjManage";

	/** 用于保存分页的标志(管理页面) */
	private static final String MANAGE_PAGE_MARK = LayInfoController.class
			.getName() + "pageManage";

	/** 用于保存查询条件(起始页面) */
	private static final String BACK_SEARCH_CONDITION = LayInfoController.class
			.getName() + "conditionObjBack";

	/** 用于保存分页的标志(起始页面) */
	private static final String BACK_PAGE_MARK = LayInfoController.class
			.getName() + "pageBack";

	private static final String CONSULT_MARK = "consult";
	private static final String BEGIN_MARK = "back";
	private static final String MANAGE_MARK = "manage";
	
	private static Map<String, Integer> commonLaw = new TreeMap<String, Integer>();
	
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
	/*跳转到新的主界面*/
	@RequestMapping("/infoLay")
	public ModelAndView infoLay(){
		ModelAndView mv = new ModelAndView(INFOLAY);
		return mv;
	}
	
	/*跳转到查阅界面*/
	@RequestMapping("/consultLay")
	public ModelAndView consultLay(){
		ModelAndView mv = new ModelAndView(CONSULTLAY);
		List<InfoType> queryLayTypes = layTypeService.queryLayTypes(null);
		mv.addObject("queryLayTypes",queryLayTypes);
		return mv;
	}

	/** 法律法规主界面 */
	@RequestMapping(value = "/manage")
	public String manage(LayInfo layInfo, String page,Map<String, Object> model, HttpSession session) {
		String  typeId = layInfo.getTypeId() == null || layInfo.getTypeId().equals("0") ? "" : layInfo.getTypeId();
		layInfo.setTypeId(typeId);
		
		User aUser = SystemContext.getCurrentUser(session);
		InfoTypeExample example = new InfoTypeExample();
		example.setOrderByClause("type_id");

		PaginationHelper<LayInfo> infos=infoService.queryInfos(layInfo,page);
		List<InfoType> infoTypes = mybatisAutoMapperService.selectByExample(InfoTypeMapper.class,example,InfoType.class);

		model.put("infos", infos);
		model.put("infoTypes", infoTypes);
		// 查询条件
		if(typeId.equals("")){
			layInfo.setTypeId("0");
		}
		model.put("info", layInfo);

		if ((layInfo.getTitle() == null) && (layInfo.getTypeId() == null)
				&& (page == null)) {
			removeCondition(session, MANAGE_SEARCH_CONDITION, MANAGE_PAGE_MARK,
					MANAGE_MARK);
		} else {
			session.setAttribute(MANAGE_SEARCH_CONDITION, layInfo);
			session.setAttribute(MANAGE_PAGE_MARK, page);
			model.put("page", page);
		}

		return INFO_LIST;
	}

	/** 法律法规索引创建界面 */
	@RequestMapping(value = "/createIndex")
	public String createIndex(Map<String, Object> model, HttpSession session) {
		InfoTypeExample example = new InfoTypeExample();
		example.setOrderByClause("type_id");

		List<InfoType> infoTypes = mybatisAutoMapperService.selectByExample(
				InfoTypeMapper.class,example, InfoType.class);

		model.put("infoTypes", infoTypes);

		return CREATE_INDEX;
	}

	/** 进行法律法规查阅操作 */
	@RequestMapping(value = "/laySearch")
	public String laySearch(LayInfo info, String page,Map<String, Object> model, String back, HttpSession session) {
		info.setContent(info.getTitle());
		info.setTypeId(info.getTypeId() == null?null:(info.getTypeId().equals("0")?null:info.getTypeId()));
		
		String backType = BEGIN_MARK;
		InfoTypeExample example = new InfoTypeExample();
		example.setOrderByClause("type_id");

		if ((back == null) || back.equals("")) {
			backType = CONSULT_MARK;
		}
		model.put("backType", backType);
		PaginationHelper<LayInfo> infos = layIndex.queryIndex(info, page,backType, session);
		
		List<InfoType> infoTypes = mybatisAutoMapperService.selectByExample(
				InfoTypeMapper.class,example, InfoType.class);
		model.put("infos", infos);
		model.put("infoTypes", infoTypes);
		// 查询条件
		model.put("info", info);

		// 传递参数(这些用于区别是起始页面还是查阅界面请求过来的)
		model.put(BEGIN_MARK, back);

		String con = CONSULT_SEARCH_CONDITION;
		String pageMark = CONSULT_PAGE_MARK;

		if ((back != null) && back.equals("true")) {
			con = BACK_SEARCH_CONDITION;
			pageMark = BACK_PAGE_MARK;
		}

		if ((info.getInfoId() == null)&& ((info.getTypeId() == null) & (page == null))) {
			removeCondition(session, con, pageMark, backType);
		} else {
			session.setAttribute(con, info);
			session.setAttribute(pageMark, page);
			model.put("page", page);
		}
		
		//获得常用法律
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String,Integer>>(commonLaw.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String,Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		
		if(list.size()>10){
			list = list.subList(0,10);
		}
		
		List<LayInfo> commonLaws = new ArrayList<LayInfo>();
		for (Map.Entry<String, Integer> enty : list) {
			LayInfo layinfo = infoService.selectByPrimaryKey(enty.getKey());
			commonLaws.add(layinfo);
		}

		model.put("commonLaws", commonLaws);
		return LAY_SEARCH;
	}

	/** 打开新增页面 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addUI(Integer typeId,InfoType infoType, Map<String, Object> model) {
		List<InfoType> infoTypes = layTypeService.queryLayTypes(infoType);
		model.put("infoTypes", infoTypes);
		model.put("selectTypeId",typeId);
		return INFO_ADD;
	}

	/** 新增信息 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request, LayInfo layInfo) {
		if(layInfo.getInfoId() == null || layInfo.getInfoId().equals("")){
			//添加
		User user = SystemContext.getCurrentUser(request);
		layInfo.setUserId(user.getUserId());
		layInfo.setOrgId(user.getOrgId());
//		layInfo.setContent(StringUtils.Html2Text(layInfo.getContent()));
		if(layInfo.getCreateTime() == null){
			layInfo.setCreateTime(new Date());
		}
		layInfo.setIsValid(1);//1有效 0无效
		layInfo.setInfoId(String.valueOf(systemDAO.getSeqNextVal(Const.TABLE_LAY_INFO)));
		// mybatisAutoMapperService.insert(LayInfoMapper.class, layInfo);
		infoService.insertLay(layInfo);
		}else{
			//更新
			infoService.updateLayByPrimaryKeySelective(layInfo);
		}
		List<LayInfo> layList = infoService.queryLayInfos(layInfo);
		LayInfo tempLay = null;
		if (layList != null && !layList.isEmpty()) {
			tempLay = layList.get(0);
			layInfo.setUserName(tempLay.getUserName());
			layInfo.setOrgName(tempLay.getOrgName());
			layInfo.setTypeName(tempLay.getTypeName());
			layInfo.setUserId(tempLay.getUserId());
			layInfo.setOrgId(tempLay.getOrgId());
		}
		return INFO_SEARCH;
	}

	/** 进入修改界面 */
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateUI(InfoType infoType, String infoId,
			Map<String, Object> model) {
		LayInfo info = mybatisAutoMapperService.selectByPrimaryKey(LayInfoMapper.class, infoId, LayInfo.class);
		List<InfoType> infoTypes = layTypeService.queryLayTypes(infoType);
		model.put("info", info);
		model.put("infoTypes", infoTypes);
		return INFO_UPDATE;
	}

	/** 修改操作 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(LayInfo layInfo) {
		layInfo.setContent(StringUtils.Html2Text(layInfo.getContent()));
		infoService.updateLayByPrimaryKeySelective(layInfo);
		List<LayInfo> layList = infoService.queryLayInfos(layInfo);
		LayInfo tempLay = null;
		if (layList != null && !layList.isEmpty()) {
			tempLay = layList.get(0);
			layInfo.setUserName(tempLay.getUserName());
			layInfo.setOrgName(tempLay.getOrgName());
			layInfo.setTypeName(tempLay.getTypeName());
			layInfo.setUserId(tempLay.getUserId());
			layInfo.setOrgId(tempLay.getOrgId());
			layInfo.setCreateTime(tempLay.getCreateTime());
		}
		return INFO_SEARCH;
	}

	/** 法律法则详情 */
	@RequestMapping(value = "/view")
	public String detail(String infoId, Map<String, Object> model, String back,
			String backType, HttpSession session) {
		if (backType == null || backType.equals("")) {
			backType = MANAGE_MARK;
		}
		
//		LayInfo info = layIndex.detail(infoId, backType, session);
		LayInfo info = infoService.selectByPrimaryKey(infoId);
		
		commonLaw.put(info.getInfoId(),commonLaw.containsKey(info.getInfoId())? commonLaw.get(info.getInfoId())+1 : 1);
		
		InfoType infoType = mybatisAutoMapperService.selectByPrimaryKey(
				InfoTypeMapper.class, Integer.parseInt(info.getTypeId()),
				InfoType.class);
		model.put("infoType", infoType);
		model.put("info", info);
		model.put(BEGIN_MARK, back);

		model.put("backType", backType);

		return INFO_DETAIL;
	}

	/** 法律法则删除操作 */
	@RequestMapping(value = "/delete")
	public String delete(String infoId) {
		infoService.delLay(infoId);
		return INFO_SEARCH;
	}
	/**
	 * 法律法则批量删除操作
	 * @param check
	 * @return
	 */
	@RequestMapping(value="/batch_delete")
	public String batchDelete(String[] check){
		if(check != null){
				infoService.batchDeleteLay(check);
		}
		return INFO_SEARCH;
	}
	// 返回
	@RequestMapping(value = BEGIN_MARK)
	public String back(HttpSession session, Map<String, Object> model,
			String back, String backType) {
		// 有查询条件,按查询条件返回
		LayInfo lay;
		String page;
		String con = MANAGE_SEARCH_CONDITION;
		String pageMark = MANAGE_PAGE_MARK;

		if ((backType != null) && backType.equals(BEGIN_MARK)) {
			con = BACK_SEARCH_CONDITION;
			pageMark = BACK_PAGE_MARK;
		} else if ((backType != null) && backType.equals(CONSULT_MARK)) {
			pageMark = CONSULT_PAGE_MARK;
			con = CONSULT_SEARCH_CONDITION;
		}

		if (session.getAttribute(con) != null) {
			lay = (LayInfo) session.getAttribute(con);
		} else {
			lay = new LayInfo();
			lay.setInfoId("");
		}

		if (session.getAttribute(pageMark) != null) {
			page = (String) session.getAttribute(pageMark);
		} else {
			page = "1";
		}

		if (((backType != null) && !backType.equals(MANAGE_MARK))
				|| ((back != null) && back.equals("true"))) {
			return this.laySearch(lay, page, model, back, session);
		} else {
			return this.manage(lay, page, model, session);
		}
	}

	private void removeCondition(HttpSession session, String con,String pageMark, String queryType) {
		if (session.getAttribute(con) != null) {
			session.removeAttribute(con);
		}

		if (session.getAttribute(pageMark) != null) {
			session.removeAttribute(pageMark);
		}
		
		LuceneUtil.removeSessionQuery(session, queryType);
	}
}

package com.ksource.liangfa.web.website.maintain;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.WebArticle;
import com.ksource.liangfa.domain.WebArticleExample;
import com.ksource.liangfa.domain.WebArticleType;
import com.ksource.liangfa.domain.WebArticleTypeExample;
import com.ksource.liangfa.domain.WebPrograma;
import com.ksource.liangfa.domain.WebProgramaExample;
import com.ksource.liangfa.mapper.WebArticleMapper;
import com.ksource.liangfa.mapper.WebArticleTypeMapper;
import com.ksource.liangfa.mapper.WebProgramaMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.website.maintain.WebArticleService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping("/website/maintain/webArticle")
public class WebArticleController {
	public static final String WEBARTICLEADD_VIEW = "website/maintain/webArticleAdd";
	public static final String WEBARTICLE_VIEW = "website/maintain/webArticleMain";
	public static final String MAIN_VIEW = "redirect:/website/maintain/webArticle/back";
	public static final String WEBARTICLEDETAIL_VIEW = "website/maintain/webArticleDetail";
	public static final String WEBARTICLEUPDATE_VIEW = "website/maintain/webArticleUpdate";

	/** 用于保存查询条件(查阅页面) */
	private static final String CONSULT_SEARCH_CONDITION = WebArticleController.class
			.getName() + "conditionObjConsult";

	/** 用于保存分页的标志(查阅页面) */
	private static final String CONSULT_PAGE_MARK = WebArticleController.class
			.getName() + "pageConsult";

	/** 用于保存查询条件(管理页面) */
	private static final String MANAGE_SEARCH_CONDITION = WebArticleController.class
			.getName() + "conditionObjManage";

	/** 用于保存分页的标志(管理页面) */
	private static final String MANAGE_PAGE_MARK = WebArticleController.class
			.getName() + "pageManage";

	/** 用于保存查询条件(起始页面) */
	private static final String BACK_SEARCH_CONDITION = WebArticleController.class
			.getName() + "conditionObjBack";

	/** 用于保存分页的标志(起始页面) */
	private static final String BACK_PAGE_MARK = WebArticleController.class
			.getName() + "pageBack";
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	
	@Autowired
	private WebArticleService webArticleService;

	/** 进入信息添加页面 */
	@RequestMapping(value = "addUI")
	public ModelAndView addUI(Map<String, Object> model) {
		ModelAndView view = new ModelAndView(WEBARTICLEADD_VIEW);
		WebProgramaExample example = new WebProgramaExample();
		List<WebPrograma> programas = mybatisAutoMapperService.selectByExample(WebProgramaMapper.class, example, WebPrograma.class);
		view.addObject("programas", programas);
		return view;
	}
	
	/** 进行信息添加操作 */
	@RequestMapping(value = "add")
	public String add(HttpServletRequest request, WebArticle webArticle,
			MultipartHttpServletRequest attachmentFile) {
		User loginUser = SystemContext.getCurrentUser(request);
		webArticle.setUserId(loginUser.getUserId());
		
		if(webArticle.getSort() == null) {
			webArticle.setSort(1);
		}
		Calendar c = Calendar.getInstance();
		webArticle.setArticleTime(c.getTime());
		webArticleService.add(webArticle, attachmentFile);
		return MAIN_VIEW;
	}

	/** 进入管理界面 */
	@RequestMapping(value = "main")
	public ModelAndView main(HttpSession session, WebArticle article, String page) {
		ModelAndView view = new ModelAndView(WEBARTICLE_VIEW);
		User loginUser = SystemContext.getCurrentUser(session);
		// 普通用户只能查到自己添加的文章
		/*if (loginUser.getUserType() == Const.USER_TYPE_PLAIN) {
			article.setUserId(loginUser.getUserId());
		}*/
		if(!Const.SYSTEM_ADMIN_ID.equals(loginUser.getAccount())) {
			article.setDistrictCode(StringUtils.rightTrim0(loginUser.getOrganise().getDistrictCode()));
		}
		PaginationHelper<WebArticle> articleList = webArticleService.find(article, page);
		view.addObject("articleList", articleList);
		view.addObject("article", article);
		WebProgramaExample example = new WebProgramaExample();
		List<WebPrograma> programas = mybatisAutoMapperService.selectByExample(WebProgramaMapper.class, example, WebPrograma.class);
		view.addObject("programas", programas);
		if(article.getProgramaId()!=null){
			WebArticleTypeExample articleTypeExample = new WebArticleTypeExample();
			articleTypeExample.createCriteria().andProgramaIdEqualTo(article.getProgramaId());
			List<WebArticleType> articleTypes = mybatisAutoMapperService.selectByExample(WebArticleTypeMapper.class, articleTypeExample, WebArticleType.class);
			view.addObject("articleTypes", articleTypes);
		}
		if ((article.getArticleTitle() == null)
				&& (article.getProgramaId() == null) && (page == null)) {
			removeCondition(session, MANAGE_SEARCH_CONDITION, MANAGE_PAGE_MARK);
		} else {
			session.setAttribute(MANAGE_SEARCH_CONDITION, article);
			session.setAttribute(MANAGE_PAGE_MARK, page);
			view.addObject("page", page);
		}
		
		return view;
	}

	
	/** 进行信息删除操作 */
	@RequestMapping(value = "delete")
	public String delete(Integer articleId) {
		webArticleService.del(articleId);
		return MAIN_VIEW;
	}
	
	/**
	 * 对信息进行批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "batch_delete")
	public String batchDelete(Integer[] check) {
		if(check != null) {
			webArticleService.batchDelete(check);
		}
		return MAIN_VIEW;
	}

	/** 进行 管理--详细 界面 */
	@RequestMapping(value = "display")
	public String display(HttpServletRequest request,Map<String, Object> model, Integer articleId) {
		WebArticle article = webArticleService.find(articleId);
		model.put("article", article);
		model.put("articleId",articleId);
		return WEBARTICLEDETAIL_VIEW;
	}

	/** 进入管理--修改页面 */
	@RequestMapping(value = "updateUI")
	public String updateUI(Map<String, Object> model, WebArticleExample example,
			Integer articleId) {
		example.createCriteria().andArticleIdEqualTo(articleId);
		WebArticle article = mybatisAutoMapperService.selectByPrimaryKey(WebArticleMapper.class, articleId, WebArticle.class);
		model.put("article", article);
		WebProgramaExample programaExample = new WebProgramaExample();
		List<WebPrograma> programas = mybatisAutoMapperService.selectByExample(WebProgramaMapper.class, programaExample, WebPrograma.class);
		model.put("programas", programas);
		WebArticleTypeExample articleTypeExample = new WebArticleTypeExample();
		articleTypeExample.createCriteria().andProgramaIdEqualTo(article.getProgramaId());
		List<WebArticleType> articleTypes = mybatisAutoMapperService.selectByExample(WebArticleTypeMapper.class, articleTypeExample, WebArticleType.class);
		model.put("articleTypes", articleTypes);
		return WEBARTICLEUPDATE_VIEW;
	}

	/** 进行信息更新操作 */
	@RequestMapping(value = "update")
	public String update(WebArticle article,MultipartHttpServletRequest attachmentFile) {
		Calendar c = Calendar.getInstance();
		article.setLastUpdateTime(c.getTime());
		List<MultipartFile> files = attachmentFile.getFiles("file");
		for (int i = 0; i < files.size(); i++) {
			MultipartFile mFile = files.get(i);
			if (mFile != null && !mFile.isEmpty()) {
				UpLoadContext upLoad = new UpLoadContext(
						new UploadResource());
				String url = upLoad.uploadFileApp(mFile, null);
				article.setImagePath(url);
			}
		}
		webArticleService.updateByPrimaryKeySelective(article);
		return MAIN_VIEW;
	}

	/** 返回操作 */
	@RequestMapping(value = "back")
	public ModelAndView back(HttpSession session, String number, String backType) {
		/**/
		// 有查询条件,按查询条件返回
		WebArticle webArticle;
		String page;
		String con = MANAGE_SEARCH_CONDITION;
		String pageMark = MANAGE_PAGE_MARK;

		if ((backType != null) && backType.equals("back")) {
			con = BACK_SEARCH_CONDITION;
			pageMark = BACK_PAGE_MARK;
		} else if ((backType != null) && backType.equals("consult")) {
			pageMark = CONSULT_PAGE_MARK;
			con = CONSULT_SEARCH_CONDITION;
		}

		if (session.getAttribute(con) != null) {
			webArticle = (WebArticle) session.getAttribute(con);
		} else {
			webArticle = new WebArticle();
			webArticle.setArticleId(-1);
		}

		if (session.getAttribute(pageMark) != null) {
			page = (String) session.getAttribute(pageMark);
		} else {
			page = "1";
		}

		return this.main(session, webArticle, page);		
		
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

package com.ksource.liangfa.web.website.maintain;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ResponseMessage;
import com.ksource.common.util.JsTreeUtils;
import com.ksource.common.util.ServletResponseUtil;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.WebArticleType;
import com.ksource.liangfa.domain.WebProgramaExample.Criteria;
import com.ksource.liangfa.domain.WebPrograma;
import com.ksource.liangfa.domain.WebProgramaExample;
import com.ksource.liangfa.mapper.WebArticleTypeMapper;
import com.ksource.liangfa.mapper.WebProgramaMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.website.maintain.WebProgramaService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PromptType;

/**
 *@author wangzhenya
 *@2012-10-26 上午11:25:04
 */
@Controller
@RequestMapping(value="website/maintain/webPrograma")
public class WebProgramaController {

	private static final String WEBPROGRAMA_MAIN = "website/maintain/webProgramaMain";
	private static final String WEBPROGRAMA_ADD = "website/maintain/webProgramaAdd";
	private static final String WEBPROGRAMA_ADDUI = "redirect:/website/maintain/webPrograma/addUI";
	private static final String WEBPROGRAMA_UPDATE = "website/maintain/webProgramaUpdate";
	private static final String WEBPROGRAMA_UPDATEUI = "redirect:/website/maintain/webPrograma/updateUI";
	private static final String BACK_MAIN = "redirect:/website/maintain/webPrograma/main";
	
	@Autowired
	private WebProgramaService webProgramaService;
	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	
	/**
	 * 进入栏目管理的主页面
	 * @param webPrograma
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "main")
	public ModelAndView main(WebPrograma webPrograma,String page){
		ModelAndView view = new ModelAndView(WEBPROGRAMA_MAIN);
		PaginationHelper<WebPrograma> webProgramaList = webProgramaService.find(webPrograma, page);
		view.addObject("webProgramaList", webProgramaList);
		view.addObject("page", page);
		return view;
	}
	
	/**
	 * 进入栏目管理的添加页面
	 * @param map
	 * @param res
	 * @return
	 */
	@RequestMapping(value="addUI")
	public String addUI(ModelMap map,ResponseMessage res){
		map.addAttribute("info", ResponseMessage.parseMsg(res));
		return WEBPROGRAMA_ADD;
	}
	
	/**
	 * 对栏目管理进行添加操作
	 * @param webPrograma
	 * @return
	 */
	@RequestMapping(value = "add")
	public String add(WebPrograma webPrograma){
		Integer programaId = systemDAO.getSeqNextVal(Const.TABLE_WEB_PROGRAMA);
		webPrograma.setProgramaId(programaId);
		mybatisAutoMapperService.insert(WebProgramaMapper.class, webPrograma);
		//为栏目添加默认文章类型
		WebArticleType webArticleType = new WebArticleType();
		webArticleType.setTypeId(systemDAO.getSeqNextVal(Const.TABLE_WEB_ARTICLE_TYPE));
		webArticleType.setTypeName("默认文章类型");
		webArticleType.setProgramaId(programaId);
		webArticleType.setIsDefault(Const.WEB_ARTICLE_TYPE_DEFAULT);
		mybatisAutoMapperService.insert(WebArticleTypeMapper.class, webArticleType);
		
		return ResponseMessage.addPromptTypeForPath(WEBPROGRAMA_ADDUI, PromptType.add);
	}
	
	/**
	 * 进入栏目管理的修改页面
	 * @param map
	 * @param res
	 * @param programaId
	 * @return
	 */
	@RequestMapping(value = "updateUI")
	public String updateUI(ModelMap map,ResponseMessage res,Integer programaId){
		WebPrograma webPrograma = mybatisAutoMapperService.selectByPrimaryKey(WebProgramaMapper.class, programaId, WebPrograma.class);
		map.addAttribute("webPrograma", webPrograma);
		map.addAttribute("info", ResponseMessage.parseMsg(res));
		return WEBPROGRAMA_UPDATE;
	}
	
	/**
	 * 对栏目管理进行修改操作
	 * @param webPrograma
	 * @return
	 */
	@RequestMapping(value = "update")
	public String update(WebPrograma webPrograma){
		mybatisAutoMapperService.updateByPrimaryKey(WebProgramaMapper.class, webPrograma);
		return ResponseMessage.addPromptTypeForPath(WEBPROGRAMA_UPDATEUI+"?programaId="+webPrograma.getProgramaId(), PromptType.update);
	}
	
	/**
	 * 对栏目管理进行删除操作
	 * @param programaId
	 * @return
	 */
	@RequestMapping(value = "delete")
	public String delete(Integer programaId){
		mybatisAutoMapperService.deleteByPrimaryKey(WebProgramaMapper.class, programaId);
		return BACK_MAIN;
	}
	
	/**
	 * 检查栏目名称是否重复
	 * @param programaName
	 * @return
	 */
	@RequestMapping(value = "checkName")
	@ResponseBody
	public boolean checkName(String programaName,Integer programaId){
		String name = programaName.trim();
		WebProgramaExample example = new WebProgramaExample();
		Criteria criteria = example.createCriteria().andProgramaNameEqualTo(name);
		if(programaId != null){
			criteria.andProgramaIdNotEqualTo(programaId);
		}
		int result = mybatisAutoMapperService.countByExample(WebProgramaMapper.class, example);
		if(result>0){
			return false;
		}
		return true;
	}
	
	/**
	 * 查询栏目树
	 * @param response
	 */
	@RequestMapping(value = "webProgramaTree")
	public void webProgramaTree(HttpServletResponse response){
		List<WebPrograma> programas = webProgramaService.webProgramaTree();
		String trees = JsTreeUtils.webProgramaTree(programas,false);
		ServletResponseUtil servletResponseUtil = new ServletResponseUtil();
		servletResponseUtil.writeJson(response, trees);
		
	}
}

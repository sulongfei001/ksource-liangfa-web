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
import com.ksource.liangfa.domain.WebArticleTypeExample;
import com.ksource.liangfa.domain.WebArticleTypeExample.Criteria;
import com.ksource.liangfa.domain.WebPrograma;
import com.ksource.liangfa.mapper.WebArticleTypeMapper;
import com.ksource.liangfa.mapper.WebProgramaMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.website.maintain.WebArticleTypeService;
import com.ksource.liangfa.service.website.maintain.WebProgramaService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PromptType;

/**
 *@author wangzhenya
 *@2012-10-29 上午8:48:39
 */
@Controller
@RequestMapping(value = "website/maintain/webArticleType")
public class WebArticleTypeController {

	private static final String WEBARTICLETYPE_MAIN = "website/maintain/webArticleTypeMain";
	private static final String WEBARTICLETYPE_ADD = "website/maintain/webArticleTypeAdd";
	private static final String WEBARTICLETYPE_ADDUI = "redirect:/website/maintain/webArticleType/addUI";
	private static final String WEBARTICLETYPE_UPDATE = "website/maintain/webArticleTypeUpdate";
	private static final String WEBARTICLETYPE_UPDATEUI = "redirect:/website/maintain/webArticleType/updateUI";
	private static final String BACK_MAIN = "redirect:/website/maintain/webArticleType/main";
	
	@Autowired
	private WebArticleTypeService webArticleTypeService;
	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	private WebProgramaService webProgramaService;
	
	/**
	 * 进入文章类型管理的主页面
	 * @param webArticleType
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "main")
	public ModelAndView main(WebArticleType webArticleType,String page){
		ModelAndView view = new ModelAndView(WEBARTICLETYPE_MAIN);
		
		PaginationHelper<WebArticleType> webArticleTypeList = webArticleTypeService.find(webArticleType, page);
		view.addObject("webArticleTypeList", webArticleTypeList);
		view.addObject("page", page);
		
		return view;
	}
	
	/**
	 * 进入文章类型的添加页面
	 * @param map
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "addUI")
	public String addUI(ModelMap map,ResponseMessage res){
		List<WebPrograma> programas = webProgramaService.webProgramaTree();
		map.addAttribute("programas",programas);
		map.addAttribute("info", ResponseMessage.parseMsg(res));
		return WEBARTICLETYPE_ADD;
	}
	
	/**
	 * 对文章类型进行添加操作
	 * @param webArticleType
	 * @return
	 */
	@RequestMapping(value = "add")
	public String add(WebArticleType webArticleType){
		webArticleType.setTypeId(systemDAO.getSeqNextVal(Const.TABLE_WEB_ARTICLE_TYPE));
		webArticleType.setIsDefault(Const.WEB_ARTICLE_TYPE_NOTDEFAULT);
		mybatisAutoMapperService.insert(WebArticleTypeMapper.class, webArticleType);
		return ResponseMessage.addPromptTypeForPath(WEBARTICLETYPE_ADDUI, PromptType.add);
	}
	
	/**
	 * 进入文章类型更新页面
	 * @param typeId
	 * @param map
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "updateUI")
	public String updateUI(Integer typeId,ModelMap map,ResponseMessage res){
		WebArticleType webArticleType = mybatisAutoMapperService.selectByPrimaryKey(
				WebArticleTypeMapper.class, typeId, WebArticleType.class);
		WebPrograma webPrograma = mybatisAutoMapperService.selectByPrimaryKey(
				WebProgramaMapper.class, webArticleType.getProgramaId(), WebPrograma.class);
		map.addAttribute("webPrograma", webPrograma);
		map.addAttribute("webArticleType", webArticleType);
		map.addAttribute("info", ResponseMessage.parseMsg(res));
		return WEBARTICLETYPE_UPDATE;
	}
	
	/**
	 * 更新文章类型
	 * @param webArticleType
	 * @return
	 */
	@RequestMapping(value = "update")
	public String update(WebArticleType webArticleType){
		mybatisAutoMapperService.updateByPrimaryKey(WebArticleTypeMapper.class, webArticleType);
		return ResponseMessage.addPromptTypeForPath(WEBARTICLETYPE_UPDATEUI+"?typeId="+webArticleType.getTypeId(), PromptType.update);
	}
	
	/**
	 * 根据文章类型ID删除文章类型
	 * @param typeId
	 * @return
	 */
	@RequestMapping(value = "delete")
	public String delete(Integer typeId){
		mybatisAutoMapperService.deleteByPrimaryKey(WebArticleTypeMapper.class, typeId);
		return BACK_MAIN;
	}
	
	/**
	 * 检查文章类型名称是否重复
	 * @param typeName
	 * @param typeId
	 * @return
	 */
	@RequestMapping(value = "checkName")
	@ResponseBody
	public boolean checkName(String typeName,Integer programaId,Integer typeId){
		String name = typeName.trim();
		WebArticleTypeExample example = new WebArticleTypeExample();
		Criteria criteria = example.createCriteria().andTypeNameEqualTo(name);
		if(typeId!=null){
			criteria.andProgramaIdEqualTo(programaId).andTypeIdNotEqualTo(typeId);
		}else{
			criteria.andProgramaIdEqualTo(programaId);
		}
		int result = mybatisAutoMapperService.countByExample(WebArticleTypeMapper.class, example);
		if(result>0){
			return false;
		}
		return true;
	}
	
	/**
	 * 查询栏目和文章类型树，栏目为父节点，文章类型为子节点
	 * @param response
	 * @param programaId
	 */
	@RequestMapping(value = "webArticleTypeTree")
	public void webArticleTypeTree(HttpServletResponse response,Integer programaId){
		ServletResponseUtil servletResponseUtil = new ServletResponseUtil();
		String trees ;
		if(programaId==null){
			List<WebPrograma> webProgramas = webProgramaService.webProgramaTree();
			trees = JsTreeUtils.webProgramaTree(webProgramas, true);
		}else{
			WebArticleTypeExample example = new WebArticleTypeExample();
			example.createCriteria().andProgramaIdEqualTo(programaId);
			List<WebArticleType> webArticleTypes = mybatisAutoMapperService.selectByExample(WebArticleTypeMapper.class, example, WebArticleType.class);
			trees = JsTreeUtils.webArticleTypeTree(webArticleTypes);
		}
		servletResponseUtil.writeJson(response, trees);
	}
}

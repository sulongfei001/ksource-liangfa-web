package com.ksource.liangfa.web.specialactivity;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.util.JsTreeUtils;
import com.ksource.common.util.ServletResponseUtil;
import com.ksource.liangfa.domain.DqdjCategory;
import com.ksource.liangfa.domain.DqdjCategoryExample;
import com.ksource.liangfa.domain.DqdjChart;
import com.ksource.liangfa.mapper.DqdjCategoryMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.specialactivity.DqdjCategoryService;

/**
 *@author wangzhenya
 *@2013-1-6 下午5:43:07
 */
@Controller
@RequestMapping(value = "activity/dqdjCategory")
public class DqdjCategoryController {

	@Autowired 
	private DqdjCategoryService dqdjCategoryService;
	
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	
	/**
	 * 打侵打假分类树
	 * @param id
	 * @param response
	 */
	@RequestMapping(value = "getDqdjCategoryTree")
	public void getDqdjCategoryTree(Integer id,HttpServletResponse response) {
		id = id==null ? 0 :id;
		List<DqdjCategory> dqdjCategories = dqdjCategoryService.getDqdjCategoryTree(id);
		String trees = JsTreeUtils.getDqdjCategoryTree(dqdjCategories);
		ServletResponseUtil.writeJson(response, trees);
	}
	
	/**
	 * 打侵打假统计
	 * @param request
	 * @return
	 */
	@RequestMapping(value="dqdjTongji")
	public ModelAndView  dqdjCharts (HttpServletRequest request){	
		ModelAndView view = new ModelAndView("activity/dqdjTongji");
		DqdjCategoryExample example = new DqdjCategoryExample();
		example.createCriteria().andCategoryIdIsNotNull();
		List<DqdjCategory> dqdjCategories = mybatisAutoMapperService.selectByExample(DqdjCategoryMapper.class, example, DqdjCategory.class);
		
		DqdjChart dqdjChart = dqdjCategoryService.querydqdjCharts();
		view.addObject("dqdjCategories", dqdjCategories);
		view.addObject("dqdjChart", dqdjChart);
		return view;
	}
	
	
	/**
	 * 打侵打假分类树
	 * @param id
	 * @param response
	 */
	@RequestMapping(value = "getDqdjTree")
	public void getDqdjTree(Integer id,HttpServletResponse response) {
		id = id==null ? 0 :id;
		List<DqdjCategory> dqdjCategories = dqdjCategoryService.getDqdjCategoryTree(id);
		String trees = JsTreeUtils.getDqdjCategoryTree(dqdjCategories);
		response.setContentType("application/json");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(trees);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}

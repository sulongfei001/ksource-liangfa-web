package com.ksource.liangfa.web.workflow;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.util.JsonMapper;
import com.ksource.liangfa.domain.AccuseInfo;
import com.ksource.liangfa.mapper.AccuseInfoMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.workflow.XianYiRenService;
import com.ksource.syscontext.Const;

/**
 * 移送公安操作<br>
 * 挂载与任务办理页面，在任务办理办理完毕前，处理嫌疑人相关操作
 * @author rengeng
 *
 */
@Controller
@RequestMapping("/workflow/yisonggongan")
public class YiSongGongAnController {
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	AccuseInfoMapper accuseInfoMapper;
	@Autowired
	XianYiRenService xianYiRenService;
	
	 //加载法院判决操作页面
	@RequestMapping
	public ModelAndView load(String caseId){
		ModelAndView view = new ModelAndView("workflow/xianyiren/yisonggongan");
		List<AccuseInfo> accuseInfos= accuseInfoMapper.selectCaseZm(caseId, Const.CASE_ZM_TYPE_yisonggongan);
		view.addObject("accuseInfos", JsonMapper.buildNonEmptyMapper().toJson(accuseInfos));
		return view;
	}
	//添加移送公安罪名
	@RequestMapping("yisonggonganZm")
	@ResponseBody
	public ServiceResponse addZm(String caseId,String yisonggonganZm){
		ServiceResponse response = new ServiceResponse(true, "");
		String[] yisonggonganZmArr = StringUtils.split(yisonggonganZm, ",");
		xianYiRenService.addCaseZm(caseId, yisonggonganZmArr, Const.CASE_ZM_TYPE_yisonggongan);
		return response;
	}
	
}

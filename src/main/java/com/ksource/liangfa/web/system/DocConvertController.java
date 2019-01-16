package com.ksource.liangfa.web.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksource.liangfa.service.DocConvertService;

/**
 * 手动执行文档转换
 *@author 任庚
 *@2012-7-19 下午5:28:56
 */
@Controller
@RequestMapping("/system/docConvert")
public class DocConvertController {

	@Autowired
	private DocConvertService docConvertService;
	
	@RequestMapping
	public String load(Model model) {
		model.addAttribute("docConvertServiceRuning", docConvertService.docConvertServiceRuning);
		return "system/docConvert";
	}
	
	@RequestMapping("exec")
	@ResponseBody
	public boolean exec(){
		docConvertService.docConvert();
		return true;//随便返回什么，前台也不用
	}
	
}

package com.ksource.liangfa.web.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksource.common.util.DictionaryManager;
import com.ksource.liangfa.domain.Dictionary;
import com.ksource.liangfa.service.MybatisAutoMapperService;

/**
 * 此类为行政区划控制器
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2011-6-23
 * time   下午12:03:34
 */
@Controller
@RequestMapping("/system/dictionary")
public class DictionaryController {
	@Autowired
    MybatisAutoMapperService  mybatisAutoMapperService;
	@RequestMapping(value="getCaseState")
	@ResponseBody
	public List<Dictionary> getCaseState(String groupCode){
		if(groupCode.equals("")){
			groupCode ="case";
		}
		groupCode+="State";
		return  DictionaryManager.getDictList(groupCode);
	}

}

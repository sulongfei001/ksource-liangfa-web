package com.ksource.liangfa.web.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksource.common.util.JsTreeUtils;
import com.ksource.common.util.ServletResponseUtil;
import com.ksource.liangfa.domain.JudgeCondition;
import com.ksource.liangfa.domain.JudgeConditionExample;
import com.ksource.liangfa.mapper.JudgeConditionMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.syscontext.Const;

/**
 * 判决情况控制器
 * @author jack
 *
 */

@Controller
@RequestMapping("/system/judgeCondition/")
public class JudgeConditionController {
	
	@Autowired
	private MybatisAutoMapperService autoMapperService;
	
	@RequestMapping("loadJudgeCondition")
	public void loadJudgeCondition(Integer id,HttpServletRequest request,HttpServletResponse response){
		if(id == null){
			id = Const.TOP_CONDITION_ID;
		}
		JudgeConditionExample example = new JudgeConditionExample();
		example.createCriteria().andParentIdEqualTo(id);
		List<JudgeCondition> list = (List<JudgeCondition>) autoMapperService.selectByExample(JudgeConditionMapper.class, example, JudgeCondition.class);
		String trees = JsTreeUtils.getJudgeConditionTree(list);
		ServletResponseUtil.writeJson(response, trees);	
	}
}

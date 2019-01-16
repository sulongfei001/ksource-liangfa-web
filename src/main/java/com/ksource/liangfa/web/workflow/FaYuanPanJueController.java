package com.ksource.liangfa.web.workflow;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.ksource.liangfa.domain.CaseXianyiren;
import com.ksource.liangfa.domain.CaseXianyirenExample;
import com.ksource.liangfa.mapper.AccuseInfoMapper;
import com.ksource.liangfa.mapper.CaseXianyirenMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.workflow.XianYiRenService;
import com.ksource.syscontext.Const;

/**
 * 法院判决操作<br>
 * 挂载与任务办理页面，在任务办理办理完毕前，处理嫌疑人相关操作
 * @author rengeng
 *
 */
@Controller
@RequestMapping("/workflow/fayuanpanjue")
public class FaYuanPanJueController {
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	AccuseInfoMapper accuseInfoMapper;
	@Autowired
	XianYiRenService xianYiRenService;
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
	 //加载法院判决操作页面
	@RequestMapping
	public ModelAndView load(String caseId){
		ModelAndView view = new ModelAndView("workflow/xianyiren/fayuanpanjue");
		//查询所有提起公诉的嫌疑人
		CaseXianyiren xianyiren = new CaseXianyiren();
		xianyiren.setCaseId(caseId);
		xianyiren.setTiqigongsuState(Const.XIANYIREN_TIQIGONGSU_STATE_YES);
		List<CaseXianyiren> fayuanPanjueRenList = xianYiRenService.findAll(xianyiren);
		//2012-7-24任庚添加
		for(CaseXianyiren caseXianyiren:fayuanPanjueRenList){
			caseXianyiren.setAccuseInfoList(accuseInfoMapper.selectXianyirenZm(caseXianyiren.getXianyirenId(), Const.ZM_TYPE_panjue1Zm));//查询一审罪名
			caseXianyiren.setAccuseInfoList2(accuseInfoMapper.selectXianyirenZm(caseXianyiren.getXianyirenId(), Const.ZM_TYPE_panjue2Zm));//查询终审罪名
			caseXianyiren.setAccuseInfoList3(accuseInfoMapper.selectXianyirenZm(caseXianyiren.getXianyirenId(), Const.ZM_TYPE_tiqigongsuZm));//查询起诉罪名
		}
		view.addObject("fayuanPanjueRenList", fayuanPanjueRenList);
		return view;
	}
	
	//填写判决结果
	@RequestMapping("panjue")
	@ResponseBody
	public ServiceResponse  panjue(CaseXianyiren xianyiren){
		ServiceResponse response = new ServiceResponse(true, "");
		xianyiren.setFayuanpanjueState(Const.XIANYIREN_PANJUE_STATE_YES);
		xianYiRenService.updatePanjue(xianyiren, false,null);
		return response;
	}
	//查询嫌疑人byId
	@RequestMapping("getXianyirenById")
	@ResponseBody
	public CaseXianyiren getXianyirenById(Integer xianyirenId){
		CaseXianyiren caseXianyiren = xianYiRenService.selectById(xianyirenId);
		//2012-7-24任庚添加
		caseXianyiren.setAccuseInfoList(accuseInfoMapper.selectXianyirenZm(caseXianyiren.getXianyirenId(), Const.ZM_TYPE_panjue1Zm));//查询罪名
		caseXianyiren.setAccuseInfoList2(accuseInfoMapper.selectXianyirenZm(caseXianyiren.getXianyirenId(), Const.ZM_TYPE_panjue2Zm));//查询罪名
		return caseXianyiren;
	}
}

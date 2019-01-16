package com.ksource.liangfa.web.workflow;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.ksource.common.util.CheckIdentity;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseXianyiren;
import com.ksource.liangfa.domain.CaseXianyirenExample;
import com.ksource.liangfa.mapper.AccuseInfoMapper;
import com.ksource.liangfa.mapper.CaseXianyirenMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.workflow.XianYiRenService;
import com.ksource.syscontext.Const;

/**
 * 提请逮捕操作<br>
 * 挂载与任务办理页面，在任务办理办理完毕前，处理嫌疑人相关操作
 * @author rengeng
 *
 */
@Controller
@RequestMapping("/workflow/tiqingdaibu")
public class TiQIngDaiBuController {

	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	XianYiRenService xianYiRenService;
	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	AccuseInfoMapper accuseInfoMapper;
	
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
	 //加载提请逮捕操作页面
	@RequestMapping
	public ModelAndView load(String caseId){
		ModelAndView view = new ModelAndView("workflow/xianyiren/tiqingdaibu");
		//更新以前添加的提请逮捕名单里逮捕状态的人员为未起诉
		xianYiRenService.updateXianyirenStateByCaseId(caseId,"tiqingdaibu");
		//查询已提请逮捕的
		/*CaseXianyirenExample caseXianyirenExample = new CaseXianyirenExample();
		caseXianyirenExample.createCriteria().andCaseIdEqualTo(caseId).
		andDaibuStateEqualTo(Const.XIANYIREN_DAIBU_STATE_TIQING);
		List<CaseXianyiren> tiqingdaibuRenList = mybatisAutoMapperService.selectByExample(CaseXianyirenMapper.class, caseXianyirenExample, CaseXianyiren.class);
		
		//2012-7-24任庚添加
		for(CaseXianyiren caseXianyiren:tiqingdaibuRenList){
			caseXianyiren.setAccuseInfoList(accuseInfoMapper.selectXianyirenZm(caseXianyiren.getXianyirenId(), Const.ZM_TYPE_tiqingdaibuZm));//查询罪名
		}
		
		view.addObject("tiqingdaibuRenList", tiqingdaibuRenList);*/
		return view;
	}
	
	//添加提请逮捕嫌疑人
	@RequestMapping("addXianyiren")
	@ResponseBody
	public CaseXianyiren addXianyiren(CaseXianyiren xianyiren,HttpServletResponse httpRresponse){
		httpRresponse.setHeader("Cache-Control", "no-cache");   
		httpRresponse.setHeader("Pragma", "no-cache");   
		httpRresponse.setHeader("Expires", "-1");
		
		if(xianyiren.getXianyirenId()!=null && xianyiren.getXianyirenId()!=0){//修改嫌疑人
			xianyiren.setDaibuState(Const.XIANYIREN_DAIBU_STATE_TIQING);
			xianyiren.setTiqingqisuState(Const.XIANYIREN_TIQINGQISU_STATE_NOTYET);
			xianyiren.setTiqigongsuState(Const.XIANYIREN_TIQIGONGSU_STATE_NOTYET);
			xianyiren.setFayuanpanjueState(Const.XIANYIREN_PANJUE_STATE_NOTYET);
			xianYiRenService.updateXianyiren(xianyiren,true,null);
		}else{//添加嫌疑人
			xianyiren.setDaibuState(Const.XIANYIREN_DAIBU_STATE_TIQING);
			xianyiren.setTiqingqisuState(Const.XIANYIREN_TIQINGQISU_STATE_NOTYET);
			xianyiren.setTiqigongsuState(Const.XIANYIREN_TIQIGONGSU_STATE_NOTYET);
			xianyiren.setFayuanpanjueState(Const.XIANYIREN_PANJUE_STATE_NOTYET);
			xianyiren.setXianyirenId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_XIANYIREN)));
			xianYiRenService.addXianyiren(xianyiren);
		}
		
		//2012-7-24任庚添加
		xianyiren.setAccuseInfoList(accuseInfoMapper.selectXianyirenZm(xianyiren.getXianyirenId(), Const.ZM_TYPE_tiqingdaibuZm));//查询罪名
				
		return xianyiren;
	}

	//修改提请逮捕嫌疑人
	@RequestMapping("updateXianyiren")
	@ResponseBody
	public CaseXianyiren updateXianyiren(CaseXianyiren xianyiren,HttpServletResponse httpRresponse){
		httpRresponse.setHeader("Cache-Control", "no-cache");   
		httpRresponse.setHeader("Pragma", "no-cache");   
		httpRresponse.setHeader("Expires", "-1");
		xianYiRenService.updateXianyiren(xianyiren,true,null);
		
		//2012-7-24任庚添加
		xianyiren.setAccuseInfoList(accuseInfoMapper.selectXianyirenZm(xianyiren.getXianyirenId(), Const.ZM_TYPE_tiqingdaibuZm));//查询罪名
		
		return xianyiren;
	}
	 
	//删除提请逮捕嫌疑人byId
	@RequestMapping("deleteXianyiren")
	@ResponseBody
	public ServiceResponse deleteXianyiren(Long xianyirenId,HttpServletResponse httpRresponse){
		httpRresponse.setHeader("Cache-Control", "no-cache");   
		httpRresponse.setHeader("Pragma", "no-cache");   
		httpRresponse.setHeader("Expires", "-1");
		
		//修改嫌疑人状态为“未逮捕”
		CaseXianyiren caseXianyirenForUpdate=new CaseXianyiren();
		caseXianyirenForUpdate.setXianyirenId(xianyirenId);
		caseXianyirenForUpdate.setDaibuState(Const.XIANYIREN_DAIBU_STATE_NOTYET);
		//2012-7-24任庚添加
				xianYiRenService.deleteXianyiren(caseXianyirenForUpdate,Const.ZM_TYPE_tiqingdaibuZm);
		ServiceResponse response = new ServiceResponse();
		return response;
	}
	
	//查询提请逮捕嫌疑人byId
	@RequestMapping("getXianyirenById")
	@ResponseBody
	public CaseXianyiren getXianyirenById(Long xianyirenId,HttpServletResponse httpRresponse){
		httpRresponse.setHeader("Cache-Control", "no-cache");   
		httpRresponse.setHeader("Pragma", "no-cache");   
		httpRresponse.setHeader("Expires", "-1");
		CaseXianyiren caseXianyiren = mybatisAutoMapperService.selectByPrimaryKey(CaseXianyirenMapper.class, xianyirenId, CaseXianyiren.class);
		
		//2012-7-24任庚添加
		caseXianyiren.setAccuseInfoList(accuseInfoMapper.selectXianyirenZm(xianyirenId, Const.ZM_TYPE_tiqingdaibuZm));//查询罪名
				
		return caseXianyiren;
	}
	
	/*//校验是否已提交逮捕人
	@RequestMapping("tiqingdaibuxianyirenCheck")
	@ResponseBody
	public ServiceResponse tiqingdaibuxianyirenCheck(String caseId){
		ServiceResponse response = new ServiceResponse();
		//查询已提请逮捕的数量
		CaseXianyirenExample caseXianyirenExample = new CaseXianyirenExample();
		caseXianyirenExample.createCriteria().andCaseIdEqualTo(caseId).
		andDaibuStateEqualTo(Const.XIANYIREN_DAIBU_STATE_TIQING);
		int count = mybatisAutoMapperService.countByExample(CaseXianyirenMapper.class, caseXianyirenExample);
		if(count==0){
			response.setResult(false);
		}else{response.setResult(true);}
		return response;
	}
	*/
	
	//得到未提请逮捕的嫌疑人
	@RequestMapping("getNotYetTiqingdaibu")
	@ResponseBody
	public List<CaseXianyiren> getNotYetTiqingdaibu(String caseId,HttpServletResponse httpRresponse){
		httpRresponse.setHeader("Cache-Control", "no-cache");   
		httpRresponse.setHeader("Pragma", "no-cache");   
		httpRresponse.setHeader("Expires", "-1");
		
		//查询已提请逮捕的
		CaseXianyirenExample caseXianyirenExample = new CaseXianyirenExample();
		caseXianyirenExample.createCriteria().andCaseIdEqualTo(caseId).
		andDaibuStateEqualTo(Const.XIANYIREN_DAIBU_STATE_NOTYET);
		List<CaseXianyiren> tiqingqisuRenList = mybatisAutoMapperService.selectByExample(CaseXianyirenMapper.class, caseXianyirenExample, CaseXianyiren.class);
		for (CaseXianyiren caseXianyiren : tiqingqisuRenList) {
			String birthday = CheckIdentity.getBirthday(caseXianyiren.getXianyirenId().toString());
			caseXianyiren.setBirthday(CheckIdentity.genDate(birthday));
		}
		return tiqingqisuRenList;
	}
}

package com.ksource.liangfa.web.casehandle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.dForm.DFormUtil;
import com.ksource.common.poi.adapter.CaseTemVO;
import com.ksource.common.poi.adapter.CaseTemplateUtil;
import com.ksource.common.util.FileUtil;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseAttachment;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseImport;
import com.ksource.liangfa.domain.CaseImportExample;
import com.ksource.liangfa.domain.CaseState;
import com.ksource.liangfa.domain.CaseTodo;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.CaseAttachmentMapper;
import com.ksource.liangfa.mapper.CaseImportMapper;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.IllegalSituationMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.casehandle.CaseTemplateService;
import com.ksource.liangfa.service.specialactivity.DqdjCategoryService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * User: zxl
 * Date: 12-11-30
 * Time: 上午9:20
 * 案件导入控制器
 */
@Controller
@RequestMapping("caseTem")
public class CaseTemplateController {
    public static final String CASE_TEM_LIST = "caseTemList";
    private static final String REDI_SEARCH_VIEW = "redirect:/caseTem/main";
    private static final String IMPORT_VIEW = "casehandle/caseTemImport";
    private static final String VALID_VIEW = "casehandle/caseTemValidResult";
    private static final String IMPORT_RESULT_VIEW = "casehandle/caseImportResult";
    @Autowired
    private SystemDAO systemDAO;
    @Autowired
    private CaseService caseService;
    @Autowired
    private MybatisAutoMapperService mybatisAutoMapperService;
    @Autowired
    private CaseTemplateService caseTemplateService;
    @Autowired
    private DqdjCategoryService dqdjCategoryService;
    @Autowired
    private IllegalSituationMapper illegalSituationMapper;
    @Autowired
    private OrganiseMapper organiseMapper;
    @Autowired
    private DistrictMapper districtMapper;
    @RequestMapping(value = "upload")
    public String upload(
            MultipartHttpServletRequest temFile, ModelMap map, HttpSession session) throws Exception {
        //接受模板信息，并解析，校验模板和数据并返回校验结果
        MultipartFile file = null;
        if (temFile != null
                && (file = temFile.getFile("temFile")) != null
                && !file.isEmpty()) {
            ServiceResponse res = CaseTemplateUtil.validateTemplate(file.getInputStream());   //验证模板
            if (!res.getResult()) {
/*                map.put("acceptUnit", acceptUnit);
                map.put("acceptUnitName", acceptUnitName);
                Map<String, String> procMap = DFormUtil.getProcDefIdAndInputerTargetTaskDef(Const.CASE_CHUFA_PROC_KEY);
                String procDefId = procMap.get("procDefId");
                String targetTaskDefId = procMap.get("targetTaskDefId");
                boolean needAssignTarget = DFormUtil.needAssignTarget(procDefId, targetTaskDefId);
                if (needAssignTarget) {
                    map.put("procDefId", procDefId);
                    map.put("targetTaskDefId", targetTaskDefId);
                    map.put("needAssignTarget", needAssignTarget);
                }
*/                map.put("error", res.getMsg());      //模板有误,或是模板中没有数据
                return VALID_VIEW;
            }
            List<CaseBasic> extList = CaseTemplateUtil.validateDate(file.getInputStream());//验证数据
            if (extList.size() != 0) {
               /* map.put("acceptUnit", acceptUnit);
                map.put("acceptUnitName", acceptUnitName);
                Map<String, String> procMap = DFormUtil.getProcDefIdAndInputerTargetTaskDef(Const.CASE_CHUFA_PROC_KEY);
                String procDefId = procMap.get("procDefId");
                String targetTaskDefId = procMap.get("targetTaskDefId");
                boolean needAssignTarget = DFormUtil.needAssignTarget(procDefId, targetTaskDefId);
                if (needAssignTarget) {
                    map.put("procDefId", procDefId);
                    map.put("targetTaskDefId", targetTaskDefId);
                    map.put("needAssignTarget", needAssignTarget);
                }*/
                session.setAttribute("errorList", extList);
                map.put("dataError", extList);  //数据有误
                return VALID_VIEW;
            }
            CaseTemVO vo = CaseTemplateUtil.getCaseBasic(file.getInputStream());
            
            //TODO:调用案件录入时的保存功能
            caseTemplateService.batckInsert(vo.getCaseList());
            map.put("count", vo.getCaseList().size());
            session.removeAttribute("errorList");
            return IMPORT_RESULT_VIEW;
        }
        map.put("error", "模板有误，请重新下载模板!");      //模板有误,或是模板中没有数据
        return VALID_VIEW;
    }

    @RequestMapping(value = "errorList")
    public String errorList(ModelMap map, HttpSession session) throws Exception {
        map.put("dataError", session.getAttribute("errorList")); //查询结果
        return VALID_VIEW;
    }

    @RequestMapping("importUI")
    public String importUI(ModelMap map) {
        Map<String, String> procMap = DFormUtil.getProcDefIdAndInputerTargetTaskDef(Const.CASE_CHUFA_PROC_KEY);
        String procDefId = procMap.get("procDefId");
        String targetTaskDefId = procMap.get("targetTaskDefId");
        boolean needAssignTarget = DFormUtil.needAssignTarget(procDefId, targetTaskDefId);
        if (needAssignTarget) {
            map.put("procDefId", procDefId);
            map.put("targetTaskDefId", targetTaskDefId);
            map.put("needAssignTarget", needAssignTarget);
        }
        return IMPORT_VIEW;
    }

    @RequestMapping(value = "main")
    public String main(CaseImport caseImport, ModelMap map, HttpSession session) throws Exception {
        caseImport.setUploadFlag(Const.STATE_INVALID);
        caseImport.setInputer(SystemContext.getCurrentUser(session).getUserId());
        map.put("case", caseImport);  //查询条件
        map.put("caseList", caseTemplateService.find(caseImport)); //查询结果
        return "casehandle/caseTemAdd";
    }

    @RequestMapping(value = "updateUI")
    public String updateUI(Integer importId,ModelMap map,HttpServletRequest request) {
        CaseImport caseImport = mybatisAutoMapperService.selectByPrimaryKey(CaseImportMapper.class, importId, CaseImport.class);
        //查询案发区域名称
        District district = districtMapper.selectByPrimaryKey(caseImport.getAnfaAddress());
        caseImport.setAnfaAddressName(district.getDistrictName());
        map.put("caseInfo", caseImport);
        map.put("importId",importId);
        map.put("caseImport", caseImport);
        return "casehandle/caseTemUpdate";
    }

    @RequestMapping(value = "add")
    public String add(HttpSession session, HttpServletRequest request, String[] check) {
        User user = SystemContext.getCurrentUser(request);
		String industryType =   user.getOrganise().getIndustryType();
        String userId = user.getUserId();
        Integer orgCode = user.getOrgId();
        Date currentDate = new Date();
        
        List<String> importIdTemp=Arrays.asList(check);
        List<Integer> importIds=new ArrayList<Integer>();
        if(importIdTemp.size()>0){
        	for(String id:importIdTemp){ 
        		if(id.contains("=")){
        			Integer temp=Integer.valueOf(id.substring(0, id.indexOf("=")));
                	importIds.add(temp);	
        		}else{
        			importIds.add(Integer.valueOf(id));
        		}
            }
        }
        
        CaseImportExample example = new CaseImportExample();
        example.createCriteria().andImportIdIn(importIds);
        List<CaseImport> caseImports = mybatisAutoMapperService.selectByExampleWithBLOBs(CaseImportMapper.class, example, CaseImport.class);
        
//        PenaltyCaseExt penaltyCaseExt = new PenaltyCaseExt();
        CaseBasic caseBasic = new CaseBasic();
        CaseState caseState = new CaseState();
        caseBasic.setInputer(user.getUserId());
        if (user.getOrganise()!=null) {//数据库中的录入单位可以为空，systemAdmin登录时，会得不到Organise信息
        	caseBasic.setInputUnit(user.getOrganise().getOrgCode());
		}
        
        caseBasic.setInputUnit(orgCode);
        caseBasic.setInputTime(currentDate);
        caseBasic.setCaseState(Const.CHUFA_PROC_0);//案件处理状态，行政受理
        caseBasic.setLatestPocessTime(currentDate);
        caseBasic.setProcKey(Const.CASE_CHUFA_PROC_KEY);
        caseBasic.setInputType(Const.INPUT_TYPE_EXCEL); //excel导入
        caseBasic.setVersionNo(Const.SYSTEM_VERSION_1);
        caseBasic.setIsAssign(Const.IS_ASSIGN_NO);//是否交办案件
        caseBasic.setIsSuspectedCriminal(Const.SUSPECTED_CRIMINAL_NO);//疑似涉嫌犯罪
        //对案件编号进行去掉空格处理(与案件编号的验证，查询保持一致)
        caseBasic.setCaseNo(StringUtils.trim(caseBasic.getCaseNo()));
        //设置案件状态
        caseState.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_NOTYET);//行政立案状态
        caseState.setChufaState(Const.CHUFA_STATE_NOTYET);
        caseState.setLianState(Const.LIAN_STATE_NOTYET);
        caseState.setDaibuState(Const.DAIBU_STATE_NOTYET);
        caseState.setQisuState(Const.QISU_STATE_NOTYET);
        caseState.setPanjueState(Const.PANJUE_STATE_NOTYET);
        caseState.setYisongState(Const.YISONG_STATE_NO);
        caseState.setJieanState(Const.JIEAN_STATE_NO);
        caseState.setExplainState(Const.EXPLAIN_STATE_NOTYET);
        caseState.setReqExplainState(Const.REQ_EXPLAIN_STATE_NOTYET);
        //设置待办案件
        CaseTodo caseTodo = new CaseTodo();
        caseTodo.setCreateUser(userId);
        caseTodo.setCreateTime(currentDate);
        caseTodo.setCreateOrg(orgCode);
        caseTodo.setAssignUser(userId);//办理人id
        caseTodo.setAssignOrg(orgCode);//办理人机构
        
        for (CaseImport temp : caseImports) {
        	Map<String, Object> map = new HashMap<String, Object>();
            caseBasic.setCaseNo(StringUtils.trim(temp.getCaseNo()));    //对案件编号进行去掉空格处理(与案件编号的验证，查询保持一致)
            caseBasic.setCaseName(temp.getCaseName());
            caseBasic.setCaseDetailName(temp.getCaseDetailName());//违法事实
            caseBasic.setRecordSrc(temp.getRecordSrc());//案件来源
            caseBasic.setAnfaAddress(temp.getAnfaAddress());//案发区域
            caseBasic.setAnfaTime(temp.getAnfaTime());//违法行为发生时间
            caseBasic.setUndertaker(temp.getUndertaker());
            caseBasic.setUndertakerTime(temp.getUndertakerTime());
            caseBasic.setUndertakerSuggest(temp.getUndertakerSuggest());
            
            caseBasic.setCasePartyJson(temp.getCasePartyJson());
            caseBasic.setCaseCompanyJson(temp.getCaseCompanyJson());
            
            map.put("caseImport", temp);
            map.put("caseState", caseState);
            map.put("caseTodo", caseTodo);
            caseService.addByTem(caseBasic,map,industryType,request);
        }
        return REDI_SEARCH_VIEW;
    }

    //修改案件(目前只修改附件)　　
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(CaseImport caseImport) throws Exception {
        caseTemplateService.update(caseImport);
        return REDI_SEARCH_VIEW;
    }

    @ResponseBody
    @RequestMapping(value = "delFile/{id}/{importId}", method = RequestMethod.GET)
    public void delFile(@PathVariable Integer id,@PathVariable Integer importId) {
        CaseAttachment atta = mybatisAutoMapperService.selectByPrimaryKey(CaseAttachmentMapper.class, id, CaseAttachment.class);
        if (atta.getId().equals(id)) {
            FileUtil.deleteFileInDisk(atta.getAttachmentPath());
        }
        mybatisAutoMapperService.deleteByPrimaryKey(CaseAttachmentMapper.class, id);
        //删除附件id
        caseTemplateService.delFile(importId);
    }

    
    //批量删除　　
    @RequestMapping(value = "batchDel")
    public String batchDel(String[] check,CaseImport caseImport, ModelMap map,
			HttpSession session) throws Exception {
    	if(check != null){
    		caseTemplateService.batchDel(check);
    	}
    	map.addAttribute("info", "删除成功!");
		return this.main(caseImport, map, session);
    }
    
    
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}


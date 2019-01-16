package com.ksource.common.poi.adapter;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.poi.Excel;
import com.ksource.common.util.DateUtil;
import com.ksource.common.util.DictionaryManager;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseImport;
import com.ksource.liangfa.domain.Dictionary;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.DistrictExample;
import com.ksource.liangfa.domain.HotlineInfo;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.syscontext.SpringContext;

/**
 * User: zxl
 * Date: 12-11-30
 * Time: 上午9:54
 */
public final class CaseTemplateUtil {
    /**
     * 验证上传文件是否与模板对应
     *
     * @param input
     * @return
     */
    public static ServiceResponse validateTemplate(InputStream input) {
        Excel excel = new Excel(input);
        excel.setWorkingSheet(0);//第一个sheet的index为0
        ServiceResponse res = CaseBasicAdapter.validate(excel);
        return res;
    }

    public static CaseTemVO getCaseBasic(InputStream inputStream) {
        Excel excel = new Excel(inputStream);
        excel.setWorkingSheet(0);////第一个sheet的index为0
        CaseTemVO temVO = new CaseTemVO();
        List<CaseImport> caseBasicList = CaseBasicAdapter.convertCellToObject(excel);
        temVO.setCaseList(caseBasicList);
        return temVO;
    }
    
    public static CaseTemVO getImportHotline(InputStream inputStream) {
    	Excel excel = new Excel(inputStream);
    	excel.setWorkingSheet(0);////第一个sheet的index为0
    	CaseTemVO temVO = new CaseTemVO();
    	List<HotlineInfo> hotlineInfoList = HotlineInfoAdapter.convertCellToHotline(excel);
    	temVO.setHotlineInfoList(hotlineInfoList);
    	return temVO;
    }

    /**
     * 验证数据有效性(字段长度，字段类型[字符，整数，小数，时间]，唯一性，是否在有效范围内)
     * @return
     */
    public static List<CaseBasic> validateDate(InputStream input) {
    	Excel excel = new Excel(input);
        excel.setWorkingSheet(0);////第一个sheet的index为0
        return  CaseBasicAdapter.validateDate(excel);
    }
    
    /**
     * 验证数据有效性(字段长度，字段类型[字符，整数，小数，时间]，唯一性，是否在有效范围内)
     * @return
     */
    public static List<HotlineInfo> validateHotlineDate(InputStream input) {
    	Excel excel = new Excel(input);
    	excel.setWorkingSheet(0);////第一个sheet的index为0
    	return  HotlineInfoAdapter.validateDate(excel);
    }

    /**
     * 转换特别值
     *
     * @param type
     * @param value
     * @return
     */
    public static Object convert(String type, Object value) {
        Object obj = value;
        String groupCode = null;
         if ("anfaTime".equals(type)) {
            if(DateUtil.isDateString(value)){
               obj= DateUtil.convertStringToDate(String.valueOf(value));
            }
        }else if ("anfaAddress".equals(type)) {
        	DistrictExample districtExample = new DistrictExample();
        	districtExample.createCriteria().andDistrictNameEqualTo((String)value);
        	DistrictMapper districtMapper = SpringContext.getApplicationContext().getBean(DistrictMapper.class);
        	List<District> districtList = districtMapper.selectByExample(districtExample);
        	if(districtList.size()>0){
        		obj = districtList.get(0).getDistrictCode();
        	}
        	
        }else if("recordSrc".equals(type)){
        	groupCode = "caseSource";
        
        }else if ("casePartys".equals(type)||"caseCompanys".equals(type)) {
       		obj=StringUtils.trim(String.valueOf(value));
       		obj=StringUtils.replace(String.valueOf(obj), "，", ",");
        }
        if (groupCode != null) {
            for (Dictionary dic : DictionaryManager.getDictList(groupCode)) {
                if (dic.getDtName().equals(value)) {
                    obj = dic.getDtCode();
                }
            }
        }
        return obj;
    }

	public static ServiceResponse validateTemplateInfo(InputStream input) {
		Excel excel = new Excel(input);
        excel.setWorkingSheet(0);//第一个sheet的index为0
        ServiceResponse res = HotlineInfoAdapter.validateHotline(excel);
        return res;
	}

	/**
	 * 市长热线类型、时间数据转换
	 * @param type
	 * @param value
	 * @return
	 */
	public static Object converHotline(String type, Object value) {
		Object obj = value;
        String groupCode = null;
        if ("acceptTime".equals(type)) {
            if(DateUtil.isDateString(value)){
               obj= DateUtil.convertStringToDate(String.valueOf(value));
            }
        }else if ("handleTime".equals(type)) {
            if(DateUtil.isDateString(value)){
               obj= DateUtil.convertStringToDate(String.valueOf(value));
            }
        }else if("hotlineType".equals(type)){
        	 groupCode = "hotlineType";
        }else if("contentType".equals(type)){
        	 groupCode = "contentType";
         }
        if (groupCode != null) {
            for (Dictionary dic : DictionaryManager.getDictList(groupCode)) {
                if (dic.getDtName().equals(value)) {
                    obj = dic.getDtCode();
                }
            }
        }
        return obj;
	}

}

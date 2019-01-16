package com.ksource.common.poi.adapter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.MethodInvoker;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.poi.Excel;
import com.ksource.common.poi.ValidRule;
import com.ksource.liangfa.domain.HotlineInfo;
import com.ksource.liangfa.domain.HotlineInfoExample;
import com.ksource.liangfa.mapper.HotlineInfoMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.syscontext.SpringContext;

public class HotlineInfoAdapter {
	
	private static CellAdapter infoNo;
	private static CellAdapter theme;
	private static CellAdapter content;
	private static CellAdapter contentType;
    private static CellAdapter hotlineType;
    private static CellAdapter name;
    private static CellAdapter phone;
    private static CellAdapter acceptUser;
    private static CellAdapter acceptTime;
    private static CellAdapter dealInfo;
    private static CellAdapter assignCommnet;
    private static CellAdapter handleOrg;
    private static CellAdapter handleTime;
    
    private static List<CellAdapter> caseCell=new ArrayList<CellAdapter>();
    static {
    	
    	ValidRule infoNoRule = new ValidRule(25, ValidRule.DataType.str_type.getKey(), true, true);
    	infoNo= new CellAdapter("infoNo","受理编号",0,0,infoNoRule);
    	
    	ValidRule themeRule = new ValidRule(100, ValidRule.DataType.str_type.getKey(), false, true);
    	theme= new CellAdapter("theme","主题",1,0,themeRule);
    	   	
    	ValidRule contentRule = new ValidRule(500, ValidRule.DataType.str_type.getKey(), false, true);
    	content= new CellAdapter("content","情况概述",2,0,contentRule);
    	
    	ValidRule contentTypeRule = new ValidRule(50, ValidRule.DataType.str_type.getKey(), false, true);
    	contentType= new CellAdapter("contentType","内容类型",3,0,contentTypeRule);
    	
        ValidRule hotlineTypeRule = new ValidRule(50, ValidRule.DataType.str_type.getKey(), false, true);
        hotlineType= new CellAdapter("hotlineType","反映类型",4,0,hotlineTypeRule);
        
        ValidRule nameRule = new ValidRule(50, ValidRule.DataType.str_type.getKey(), false, true);
        name= new CellAdapter("name","咨询人姓名",5,0,nameRule);
        
        ValidRule phoneRule = new ValidRule(15, ValidRule.DataType.str_type.getKey(), false, true);
        phone= new CellAdapter("phone","来电人电话",6,0,phoneRule);
        
        ValidRule acceptUserRule = new ValidRule(25, ValidRule.DataType.str_type.getKey(), false, true);
        acceptUser= new CellAdapter("acceptUser","受理人",7,0,acceptUserRule);
        
        ValidRule acceptTimeRule = new ValidRule(25, ValidRule.DataType.date_type.getKey(), false, true);
        acceptTime= new CellAdapter("acceptTime","受理时间",8,0,acceptTimeRule);
        
        ValidRule dealInfoRule = new ValidRule(250, ValidRule.DataType.str_type.getKey(), false, true);
        dealInfo= new CellAdapter("dealInfo","办理情况",9,0,dealInfoRule);
        
        ValidRule assignCommnetRule = new ValidRule(250, ValidRule.DataType.str_type.getKey(), false, true);
        assignCommnet= new CellAdapter("assignCommnet","交办意见",10,0,assignCommnetRule);
        
        ValidRule handleOrgRule = new ValidRule(25, ValidRule.DataType.str_type.getKey(), false, true);
        handleOrg= new CellAdapter("handleOrg","承办单位",11,0,handleOrgRule);
        
        ValidRule handleTimeRule = new ValidRule(25, ValidRule.DataType.date_type.getKey(), false, true);
        handleTime= new CellAdapter("handleTime","受理时间",12,0,handleTimeRule);
       
        //以下数据需要特别处理
        contentType.needConvert(true);
        hotlineType.needConvert(true);
        acceptTime.needConvert(true);
        handleTime.needConvert(true);
        
        caseCell.add(infoNo);
        caseCell.add(theme);
        caseCell.add(content);
        caseCell.add(contentType);
        caseCell.add(hotlineType);
        caseCell.add(name);
        caseCell.add(phone);
        caseCell.add(acceptUser);
        caseCell.add(acceptTime);
        caseCell.add(dealInfo);
        caseCell.add(assignCommnet);
        caseCell.add(handleOrg);
        caseCell.add(handleTime);
    }
    
    
    //市长热线
    public static List<HotlineInfo> convertCellToHotline(Excel excel) {
    	List<HotlineInfo> casePartyList = new ArrayList<HotlineInfo>();
    	int validLength = getObjectCount(excel);
    	for (int i = 1; i <= validLength; i++) {
    		
    		HotlineInfo hotline = new HotlineInfo();
    		MethodInvoker method = new MethodInvoker();
    		method.setTargetObject(hotline);
    		Iterator<CellAdapter> tem = caseCell.iterator();
    		while (tem.hasNext()) {
    			CellAdapter adapter = tem.next();
    			Object args = excel.cell(adapter.getRow() + i, adapter.getCol()).value();
    			if (adapter.needConvert()) {
    				args = CaseTemplateUtil.converHotline(adapter.getPropertyName(), args);
    			}
    			//字符去空处理
    			if(args instanceof String){
    				args = StringUtils.trim((String)args);
    			}
    			method.setTargetMethod("set" +
    					Character.toUpperCase(adapter.getPropertyName().charAt(0)) +
    					adapter.getPropertyName().substring(1));
    			method.setArguments(new Object[]{args});
    			try {
    				method.prepare();
    				method.invoke();
    			} catch (ClassNotFoundException e) {
    				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    			} catch (NoSuchMethodException e) {
    				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    			} catch (IllegalAccessException e) {
    				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    			} catch (InvocationTargetException e) {
    				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    			}
    			
    		}
    		casePartyList.add(hotline);
    	}
    	return casePartyList;
    }


    /**
     * 第一个案件编号为空的前一个案件被当做最后一个案件
     *
     * @param excel
     * @return
     */
    public static int getObjectCount(Excel excel) { //TODO:待测试
        int caseRow = 0;
        for (int i = 0; ; i++) {
            boolean fullBlank = true;
            for (CellAdapter adapter : caseCell) {
                if (StringUtils.isNotBlank(excel.cell(i, adapter.getCol()).value().toString())) {
                    fullBlank = false;
                    break;
                }
            }
            if (fullBlank) {
                caseRow = i;
                break;
            }
        }
        return caseRow - 1;
    }

    public boolean IdValidate(String str){
    	
    	return true;
    }

	public static ServiceResponse validateHotline(Excel excel) {
		ServiceResponse res = new ServiceResponse(true, "");
        List<CellAdapter> cellAdapters = correctTemPosition(excel);
        Iterator<CellAdapter> tem = null;
        if (cellAdapters != null && cellAdapters.size() != 0) {
            tem = cellAdapters.iterator();
        } else {
            tem = caseCell.iterator();
        }
        //如果没有数据，同样验证失败
        if (getObjectCount(excel) == 0) {
            res.setingError("文件中没有数据，请填写数据后再上传!");
        }
        return res;
	}


	  //TODO:是不是覆盖模板？
    private static List<CellAdapter> correctTemPosition(Excel excel) {
        List<CellAdapter> temps = caseCell;
        return temps;
    }
    
    /**
     * 验证数据有效性
     *
     * @param excel
     * @return
     */
    public static List<HotlineInfo> validateDate(Excel excel) {
        int validLength = getObjectCount(excel);
        /** 用来保存有唯一值验证的字段的值
         **/
        Map<String, List<Object>> paramMap = new HashMap<String, List<Object>>();
        Map<Object, Integer> lineNumMap = new HashMap<Object, Integer>();
        List<HotlineInfo> extList = new ArrayList<HotlineInfo>();
        MybatisAutoMapperService mybatisAutoMapperService = SpringContext.getApplicationContext().getBean(MybatisAutoMapperService.class);
        HotlineInfoExample example = new HotlineInfoExample();

        for (int i = 1; i <= validLength; i++) { //行递增

            Iterator<CellAdapter> tem = caseCell.iterator();
            HotlineInfo hotlineInfo = new HotlineInfo();
            List<String> errors = new ArrayList<String>();
            hotlineInfo.setValidError(errors);
            hotlineInfo.setExcelRowNum(i + 1); //行号比索引大1
            hotlineInfo.setInfoNo(excel.cell(infoNo.getRow() + i, infoNo.getCol()).value().toString());
            while (tem.hasNext()) {    //列循环
                CellAdapter adapter = tem.next();
                ValidRule rule = adapter.getValidRule();
                Object args = excel.cell(adapter.getRow() + i, adapter.getCol()).value();
                if (adapter.needConvert()) {
                    args = CaseTemplateUtil.convert(adapter.getPropertyName(), args);
                }
                boolean sucess = true;
                //验证开始,验证结果被封装成对象集返回出去
                if (!args.equals("")) {   //如果为空不进行数据类型验证
                	
                    if (rule.getType().equals(ValidRule.DataType.str_type.getKey())) {
                        if (!args.getClass().getSimpleName().equals("String")) {
                            errors.add("\"" + adapter.getName() + "\"" + "数据类型不对，应该为字符类型!");
                            continue;
                        }
                        String temp = StringUtils.trim(String.valueOf(args));
                        if (!rule.getLength().equals(-1) && rule.getLength() < temp.length()) {
                            errors.add("\"" + adapter.getName() + "\"" + "长度过长!");
                        }
                    } else if (rule.getType().equals(ValidRule.DataType.int_type.getKey())) {
                        if (!args.getClass().getSimpleName().equals("Integer")) {
                            errors.add("\"" + adapter.getName() + "\"" + "数据类型不对，应该为整数类型!");
                            continue;
                        }
                    } else if (rule.getType().equals(ValidRule.DataType.short_type.getKey())) {
                        if (!args.getClass().getSimpleName().equals("Short")) {
                            errors.add("\"" + adapter.getName() + "\"" + "数据类型不对，应该为short类型!");
                            continue;
                        }
                    } else if (rule.getType().equals(ValidRule.DataType.integer_type.getKey())) {
                        if (!args.getClass().getSimpleName().equals("Integer")) {
                            errors.add("\"" + adapter.getName() + "\"" + "数据类型不对，应该为integer类型!");
                            continue;
                        }
                    }else if (rule.getType().equals(ValidRule.DataType.dou_type.getKey())) {
                        if (!args.getClass().getSimpleName().equals("Double")) {
                            errors.add("\"" + adapter.getName() + "\"" + "数据类型不对，应该为小数类型!");
                            continue;
                        }
                        String[] strDou= String.format("%.2f",args).split("\\.");
                        if(strDou[0].length()>10||strDou[1].length()>2){
                            errors.add("\"" + adapter.getName() + "\"" + "格式不对，整数最多10位，小数最多2位!");
                            continue;
                        }

                    } else if (rule.getType().equals(ValidRule.DataType.date_type.getKey())) {
                        if (!args.getClass().getSimpleName().equals("Date")) {
                            errors.add("\"" + adapter.getName() + "\"" + "数据类型不对，应该为日期类型,格式为：2014/1/1!");
                            continue;
                        }
                    } else {
                        errors.add("\"" + adapter.getName() + "\"" + "类型为" + args.getClass().getSimpleName() + ",不支持的数据类型!");
                        continue;
                    }
                    if (rule.getScope() != null && rule.getScope().size() != 0) {
                        if (!rule.getScope().contains(args)) {
                            errors.add("\"" + adapter.getName() + "\"" + "不在合法范围内!");
                        }
                    }
                    //唯一性校验
                    if (rule.isUnique()) {
                        if (paramMap.get(adapter.getPropertyName()) == null) {
                            paramMap.put(adapter.getPropertyName(), new ArrayList());
                        }

                        if (paramMap.get(adapter.getPropertyName()).contains(args)) {
                        	int lineNum=lineNumMap.get(args);
                            errors.add(adapter.getName() + "\"" + args + "\"" + "已存在于excel中的第"+lineNum+"行,不允许重复!");
                        } else {
                            paramMap.get(adapter.getPropertyName()).add(args);
                            lineNumMap.put(args,hotlineInfo.getExcelRowNum()); //保存行号
                        }
                        if (adapter.getPropertyName().equals("infoNo")) {    //TODO:::对案件编号进行数据验证唯一性，特别处理，可以考虑重构
                            example.clear();
                            example.createCriteria().andInfoNoEqualTo(String.valueOf(args));
                            if (mybatisAutoMapperService.countByExample(HotlineInfoMapper.class, example) > 0) {
                                errors.add(adapter.getName() + "\"" + args + "\"" + "已存在于数据库中,不允许重复!");
                            }

                        }
                    }
                }
                //必填校验
                if (rule.isRequired()) {
                    if (args.equals("")) {
                        errors.add("\""+adapter.getName() + "\"不允许为空!");
                    }
                }
            }/**列循环结束*/
            if (hotlineInfo.getValidError().size() != 0) {
                extList.add(hotlineInfo);
            }
        }
        return extList;
    }

}

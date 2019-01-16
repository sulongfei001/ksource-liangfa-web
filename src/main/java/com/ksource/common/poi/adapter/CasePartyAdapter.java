package com.ksource.common.poi.adapter;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.poi.Excel;
import com.ksource.common.poi.ValidRule;
import com.ksource.common.util.CheckIdentity;
import com.ksource.liangfa.domain.CaseParty;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.MethodInvoker;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: zxl
 * Date: 12-11-29
 * Time: 上午9:13
 * To change this template use File | Settings | File Templates.
 */
public class CasePartyAdapter {
    private static CellAdapter idsNo;
    private static CellAdapter name;
    private static CellAdapter sex;
    private static CellAdapter education;
    private static CellAdapter nation;
    private static CellAdapter birthplace;
    private static CellAdapter profession;
    /*private static CellAdapter vocation;*/
    private static CellAdapter tel;
    private static CellAdapter birthday;

    private static CellAdapter sexName;
	private static CellAdapter educationName;
	private static CellAdapter nationName;
	/*private static CellAdapter vocationName;*/

    private static List<CellAdapter> caseCell=new ArrayList<CellAdapter>();
    private static CellAdapter casePartyInfo;
    private static CellAdapter caseCompanyInfo;

    static {
    	
    	ValidRule casePartyInfoRule = new ValidRule(100, ValidRule.DataType.str_type.getKey(), false, false);
        casePartyInfo= new CellAdapter("当事人信息",0,13,casePartyInfoRule);//用于模板定位
        
        ValidRule caseCompanyInfoRule = new ValidRule(100, ValidRule.DataType.str_type.getKey(), false, false);
        caseCompanyInfo= new CellAdapter("企业信息",0,30, caseCompanyInfoRule);//用于模板定位

        ValidRule idsNoRule = new ValidRule(100, ValidRule.DataType.str_type.getKey(), true, true);
        idsNo= new CellAdapter("idsNo","身份证号",0,14, idsNoRule);
        
        ValidRule nameRule = new ValidRule(100, ValidRule.DataType.str_type.getKey(), false, true);
        name= new CellAdapter("name","姓名",1,14,nameRule);
        
        ValidRule sexRule = new ValidRule(25, ValidRule.DataType.str_type.getKey(), false, false);
        sex= new CellAdapter("sex","性别",3,14,sexRule);
        
        ValidRule educationRule = new ValidRule(25, ValidRule.DataType.str_type.getKey(), false, false);
        education= new CellAdapter("education","文化程度",4,14,educationRule);
        
        ValidRule nationRule = new ValidRule(25, ValidRule.DataType.str_type.getKey(), false, false);
        nation= new CellAdapter("nation","民族",5,14,nationRule);
        
        ValidRule birthplaceRule = new ValidRule(500, ValidRule.DataType.str_type.getKey(), false, false);
        birthplace= new CellAdapter("birthplace","籍贯",6,14,birthplaceRule);
        
        ValidRule professionRule = new ValidRule(500, ValidRule.DataType.str_type.getKey(), false, false);
        profession= new CellAdapter("profession","工作单位和职务",7,14,professionRule);
        /*
        ValidRule vocationRule = new ValidRule(-1, ValidRule.DataType.str_type.getKey(), false, true);
        vocation= new CellAdapter("vocation","职业",8,15,vocationRule);
        */
        ValidRule telRule = new ValidRule(-1, ValidRule.DataType.str_type.getKey(), true, false);
        tel= new CellAdapter("tel","联系电话",8,14,telRule);
        
        ValidRule birthdayRule = new ValidRule(25, ValidRule.DataType.date_type.getKey(), false, false);
        birthday= new CellAdapter("birthday","出生日期",9,14,birthdayRule);
        
        
        //特别字段
        ValidRule sexNameRule = new ValidRule(-1, ValidRule.DataType.str_type.getKey(), false, false);
        sexName= new CellAdapter("sexName","性别",3,14,sexNameRule);
        
        ValidRule educationNameRule = new ValidRule(-1, ValidRule.DataType.str_type.getKey(), false, false);
        educationName= new CellAdapter("educationName","文化程度",4,14,educationNameRule);
        
        ValidRule nationNameRule = new ValidRule(-1, ValidRule.DataType.str_type.getKey(), false, false);
        nationName= new CellAdapter("nationName","民族",5,14,nationNameRule);
        /*
        ValidRule vocationNameRule = new ValidRule(-1, ValidRule.DataType.str_type.getKey(), false, false);
        vocationName= new CellAdapter("vocationName","职业",8,15,vocationNameRule);
        */
        //以下数据需要特别处理
        sex.needConvert(true);
        education.needConvert(true);
        nation.needConvert(true);
        /*vocation.needConvert(true);*/

        caseCell.add(idsNo);
        caseCell.add(name);
        caseCell.add(sex);
        caseCell.add(education);
        caseCell.add(nation);
        caseCell.add(birthplace);
        caseCell.add(profession);
        /*caseCell.add(vocation);*/
        caseCell.add(tel);
        caseCell.add(birthday);
        caseCell.add(sexName);
        caseCell.add(educationName);
        caseCell.add(nationName);
        /*caseCell.add(vocationName);*/
        //caseCell.add(birthdayName);
    }
    public static ServiceResponse validate(Excel excel){
        ServiceResponse res = new ServiceResponse(true,"");
        StringBuffer errorMsg = new StringBuffer();
        List<CellAdapter> cellAdapters=correctTemPosition(excel);
        Iterator<CellAdapter> tem =null;
        if(cellAdapters!=null&&cellAdapters.size()!=0){
             tem = cellAdapters.iterator();
        }else{
             tem = caseCell.iterator();
        }
        while (tem.hasNext()){
            CellAdapter adapter=tem.next();
            if(!adapter.getName().equals(excel.cell(adapter.getRow(),adapter.getCol()).value())){
                errorMsg.append("模板中\""+adapter.getName()+"\""+":\""+excel.cell(adapter.getRow(),adapter.getCol()).value()+"\"\n");
            }
        }
        if(errorMsg.length()!=0){
            res.setingError(errorMsg.toString());
        }
        return res;

    }
    //TODO:是不是覆盖模板？
    private static List<CellAdapter> correctTemPosition(Excel excel) {
       List<CellAdapter> temps=caseCell;
       if(!casePartyInfo.getName().equals(excel.cell(casePartyInfo.getRow(),casePartyInfo.getCol()).value())){//如何模板位置和既定位置不对就矫正模板位置
           temps=new ArrayList<CellAdapter>(caseCell.size());
          // Collections.copy(temps,caseCell); //不修改caseCell变量，因为这个变量是既定模板。
           Object[] values = excel.column(casePartyInfo.getCol()).value();
           for(int i=0;i<values.length;i++){
               if(casePartyInfo.equals(values[i])){
                     for(CellAdapter adapter:temps){
                         adapter.setRow(adapter.getRow()+(i-adapter.getRow()));
                     }
                   break;
               }
           }
       }
        return temps;
    }

    public static List<CaseParty> convertCellToCaseParty(Excel excel) {
        List<CaseParty> casePartyList = new ArrayList<CaseParty>();
        int validLength=getCasePartyCount(excel);
        for(int i=1;i<=validLength;i++){
                Object obj=excel.cell(caseCell.get(0).getRow()+i,caseCell.get(0).getCol()).value();
                if(caseCell.get(0).getPropertyName().equals("idsNo")&&obj.toString().equals("")){
                    continue;
                }
            CaseParty temp= new CaseParty();
            MethodInvoker method = new MethodInvoker();
            method.setTargetObject(temp);
            Iterator<CellAdapter> tem = caseCell.iterator();
            while (tem.hasNext()){
                CellAdapter adapter=tem.next();
                Object args=excel.cell(adapter.getRow()+i,adapter.getCol()).value();
                if(args != "") {
                	if(adapter.needConvert()){
                		args=CaseTemplateUtil.convert(adapter.getPropertyName(),args);
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
            }
            casePartyList.add(temp);
        }
        return casePartyList;
    }

    /**
     * 第一个身份证号为空的前一个当事人被当做最后一个当事人
     * @param excel
     * @return
     */
    public static int getCasePartyCount(Excel excel) {
        /*int lastCasePartyRow=0;
           Object[] values = excel.column(casePartyInfo.getCol()).value();
           for(int i=casePartyInfo.getRow()+1;i<values.length;i++){
               if("".equals(values[i])){
                   lastCasePartyRow=i-1;
                   break;
               }
           }
        if(lastCasePartyRow==0){   //无当事人信息
            return lastCasePartyRow;
        }else{
            return lastCasePartyRow-casePartyInfo.getRow()-1;
        }*/
        int caseCompanyRow=0;
        if(!caseCompanyInfo.equals(excel.cell(caseCompanyInfo.getRow(),caseCompanyInfo.getCol()).value())){//如何模板位置和既定位置不对就矫正模板位置
           Object[] values = excel.column(caseCompanyInfo.getCol()).value();
           for(int i=0;i<values.length;i++){
               if(caseCompanyInfo.getName().equals(values[i])){
                   caseCompanyRow=i;
                   break;
               }
           }
       }
        if(caseCompanyRow==0){
            throw new RuntimeException("模板被修改，请重新下载模板");
        }

        return caseCompanyRow-casePartyInfo.getRow()-2;
    }
    
    /**
     * 验证数据有效性
     *
     * @param excel
     * @return
     */
    public static List<String> validateDate(Excel excel, List<String> errors) {
    	int validLength=getCasePartyCount(excel);
        for(int i=1;i<=validLength;i++){
                Object obj=excel.cell(caseCell.get(0).getRow()+i,caseCell.get(0).getCol()).value();
                if(caseCell.get(0).getPropertyName().equals("idsNo")&&obj.toString().equals("")){
                    continue;
                }
	        Iterator<CellAdapter> tem = caseCell.iterator();
	        while (tem.hasNext()) {    //列循环
	            CellAdapter adapter = tem.next();
	            Object args=excel.cell(adapter.getRow() + i,adapter.getCol()).value();
	            ValidRule rule = adapter.getValidRule();
	            String sheetName = excel.sheet().toHSSFSheet().getSheetName();
	            int row = adapter.getRow() + i + 1;
	            String error = "工作表\"" + sheetName + "\",\"第" + row + "行" + adapter.getName();
	            //验证开始,验证结果被封装成对象集返回出去
	            if (!args.equals("")) {   //如果为空不进行数据类型验证
	            	//只能数据不为空，才能进行转换
	            	if (adapter.needConvert()) {
	            		args = CaseTemplateUtil.convert(adapter.getPropertyName(), args);
	            	}
	                if (rule.getType().equals(ValidRule.DataType.str_type.getKey())) {
	                    String temp = StringUtils.trim(String.valueOf(args));
	                    if (!rule.getLength().equals(-1) && rule.getLength() < temp.length()) {
	                        errors.add(error + "\"" + "长度过长!");
	                    }
	                } else if (rule.getType().equals(ValidRule.DataType.int_type.getKey())) {
	                    if (!args.getClass().getSimpleName().equals("Integer")) {
	                        errors.add(error + "\"" + "数据类型不对，应该为整数类型!");
	                        continue;
	                    }
	                } else if (rule.getType().equals(ValidRule.DataType.short_type.getKey())) {
	                    if (!args.getClass().getSimpleName().equals("Short")) {
	                        errors.add(error + "\"" + "数据类型不对，应该为short类型!");
	                        continue;
	                    }
	                } else if (rule.getType().equals(ValidRule.DataType.dou_type.getKey())) {
	                    if (!args.getClass().getSimpleName().equals("Double")) {
	                        errors.add(error + "\"" + "数据类型不对，应该为小数类型!");
	                        continue;
	                    }
	                    String[] strDou= String.format("%.2f",args).split("\\.");
	                    if(strDou[0].length()>10||strDou[1].length()>2){
	                        errors.add(error + "\"" + "格式不对，整数最多10位，小数最多2位!");
	                        continue;
	                    }
	
	                } else if (rule.getType().equals(ValidRule.DataType.date_type.getKey())) {
	                    if (!args.getClass().getSimpleName().equals("Date")) {
	                        errors.add(error + "\"" + "数据类型不对，应该为日期类型,格式为：年-月-日!");
	                        continue;
	                    }
	                } else {
	                    errors.add(error + "\"" + "类型为" + args.getClass().getSimpleName() + ",不支持的数据类型!");
	                    continue;
	                }
	                if (rule.getScope() != null && rule.getScope().size() != 0) {
	                    if (!rule.getScope().contains(args)) {
	                        errors.add(error + "\"" + "不在合法范围内!");
	                    }
	                }
	                if(rule.isUnique()) {
	                	if(adapter.getPropertyName().equals("idsNo")) {
	                		if(!CheckIdentity.CheckIDCard(String.valueOf(args))) {
	                			errors.add(error + "\"格式不正确!" );
	                		}
	                	} else if(adapter.getPropertyName().equals("tel")) {
	                		String regex = "^((13\\d)|(15\\d)|(18\\d))\\d{8}$";
	                        String regex2 = "^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)(\\d{7,8})(-(\\d{3,}))?$";
	                        Pattern pattern = Pattern.compile(regex);
	                        Pattern pattern2 = Pattern.compile(regex2);
                 		    Matcher matcher = pattern.matcher(String.valueOf(args));
                 		    Matcher matcher2 = pattern2.matcher(String.valueOf(args));
                 		    if(!matcher.matches()) {
                 			   if(!matcher2.matches()) {
                 				  errors.add(error + "\"" + "格式不正确");
                 			   }
                 		    }
	                	}
	                }
	            }
	            if (rule.isRequired()) {
	                if (args.equals("")) {
	                    errors.add(error + "\"不允许为空!");
	                }
	            }
	        }
        }
        return errors;
    }
    
}

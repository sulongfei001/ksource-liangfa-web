package com.ksource.common.poi.adapter;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.poi.Excel;
import com.ksource.common.poi.ValidRule;
import com.ksource.liangfa.domain.CaseCompany;

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
public class CaseCompanyAdapter {
    private static CellAdapter name;
    private static CellAdapter companyType;
    private static CellAdapter registractionNum;
    private static CellAdapter proxy;
    private static CellAdapter registractionCapital;
    private static CellAdapter registractionTime;
    private static CellAdapter tel;
    private static CellAdapter companyTypeName;
    private static CellAdapter address;
    private static List<CellAdapter> caseCell=new ArrayList<CellAdapter>();
    private static CellAdapter caseCompanyInfo;

    static {
    	ValidRule caseCompanyInfoRule = new ValidRule(-1, ValidRule.DataType.str_type.getKey(), false, false);
        caseCompanyInfo= new CellAdapter("企业信息",0,30,caseCompanyInfoRule);//用于模板定位
        
        ValidRule nameRule = new ValidRule(50, ValidRule.DataType.str_type.getKey(), false, true);
        name= new CellAdapter("name","单位名称",0,31,nameRule);
        
        ValidRule companyTypeRule = new ValidRule(-1, ValidRule.DataType.str_type.getKey(), false, true);
        companyType= new CellAdapter("companyType","单位类型",1,31,companyTypeRule);
        
        ValidRule registractionNumRule = new ValidRule(100, ValidRule.DataType.str_type.getKey(), true, true);
        registractionNum= new CellAdapter("registractionNum","登记证号",3,31,registractionNumRule);
        
        ValidRule proxyRule = new ValidRule(50, ValidRule.DataType.str_type.getKey(), false, true);
        proxy= new CellAdapter("proxy","法人代表",4,31,proxyRule);
        
        ValidRule registractionCapitalRule = new ValidRule(20, ValidRule.DataType.dou_type.getKey(), false, false);
        registractionCapital= new CellAdapter("registractionCapital","注册资金",5,31,registractionCapitalRule);
        
        ValidRule registractionTimeRule = new ValidRule(-1, ValidRule.DataType.date_type.getKey(), false, false);
        registractionTime= new CellAdapter("registractionTime","注册时间",6,31,registractionTimeRule);
        
        ValidRule telRule = new ValidRule(-1, ValidRule.DataType.str_type.getKey(), true, false);
        tel= new CellAdapter("tel","联系电话",7,31,telRule);
        
        ValidRule addressRule = new ValidRule(500, ValidRule.DataType.str_type.getKey(), false, false);
        address= new CellAdapter("address","注册地",8,31,addressRule);
        
        ValidRule companyTypeNameRule = new ValidRule(-1, ValidRule.DataType.str_type.getKey(), false, false);
        companyTypeName= new CellAdapter("companyTypeName","单位类型",1,31,companyTypeNameRule);
        //以下数据需要特别处理
        companyType.needConvert(true);

        caseCell.add(name);
        caseCell.add(companyType);
        caseCell.add(registractionNum);
        caseCell.add(proxy);
        caseCell.add(registractionCapital);
        caseCell.add(registractionTime);
        caseCell.add(tel);
        caseCell.add(address);
        caseCell.add(companyTypeName);
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
       if(!caseCompanyInfo.getName().equals(excel.cell(caseCompanyInfo.getRow(),caseCompanyInfo.getCol()).value())){//如何模板位置和既定位置不对就矫正模板位置
           temps=new ArrayList<CellAdapter>(caseCell.size());
           // Collections.copy(temps,caseCell); //不修改caseCell变量，因为这个变量是既定模板。
           Object[] values = excel.column(caseCompanyInfo.getCol()).value();
           for(int i=0;i<values.length;i++){
               if(caseCompanyInfo.equals(values[i])){
                     for(CellAdapter adapter:temps){
                         adapter.setRow(adapter.getRow()+(i-adapter.getRow()));
                     }
                   break;
               }
           }
       }
        return temps;
    }

    public static List<CaseCompany> convertCellToCaseCompany(Excel excel) {
        List<CaseCompany> comCompanyList = new ArrayList<CaseCompany>();
        int validLength= getCaseCompanyCount(excel);
        for(int i=1;i<=validLength;i++){
                Object obj=excel.cell(caseCell.get(0).getRow()+i,caseCell.get(0).getCol()).value();
                if(caseCell.get(0).getPropertyName().equals("name")&&obj.toString().equals("")){
                    continue;
                }
            CaseCompany temp= new CaseCompany();
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
            comCompanyList.add(temp);
        }
        return comCompanyList;
    }

    /**
     * 第一个身份证号为空的前一个当事人被当做最后一个当事人
     * @param excel
     * @return
     */
    public static int getCaseCompanyCount(Excel excel) {
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
        return excel.sheet().getLastRowNum()-caseCompanyInfo.getRow()-2;
    }
    
    /**
     * 验证数据有效性
     *
     * @param excel
     * @return
     */
    public static List<String> validateDate(Excel excel, List<String> errors) {
    	int validLength= getCaseCompanyCount(excel);
        for(int i=1;i<=validLength;i++){
                Object obj=excel.cell(caseCell.get(0).getRow()+i,caseCell.get(0).getCol()).value();
                if(caseCell.get(0).getPropertyName().equals("name")&&obj.toString().equals("")){
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
	                	if(adapter.getPropertyName().equals("tel")) {
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

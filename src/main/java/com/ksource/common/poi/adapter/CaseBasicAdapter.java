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
import com.ksource.common.util.CheckIdentity;
import com.ksource.liangfa.domain.CaseBasicExample;
import com.ksource.liangfa.domain.CaseImport;
import com.ksource.liangfa.domain.CaseImportExample;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.CaseImportMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.syscontext.SpringContext;

/**
 * User: zxl
 * Date: 12-11-29
 * Time: 上午9:13
 */
public class CaseBasicAdapter {
	private static CellAdapter caseNo;
    private static CellAdapter caseName;
    private static CellAdapter recordSrc;
    private static CellAdapter anfaAddress;
    //违法行为发生时间
    private static CellAdapter anfaTime;
    private static CellAdapter undertaker;
    private static CellAdapter undertakerTime;
    private static CellAdapter caseDetailName;
    private static CellAdapter undertakerSuggest;
    private static CellAdapter casePartyJson;
    private static CellAdapter caseCompanyJson;
    private static List<CellAdapter> caseCell=new ArrayList<CellAdapter>();
    static {
    	
    	ValidRule caseNoRule = new ValidRule(40, ValidRule.DataType.str_type.getKey(), true, true);
    	caseNo= new CellAdapter("caseNo","案件编号",0,0,caseNoRule);
    	   	
        ValidRule caseNameRule = new ValidRule(40, ValidRule.DataType.str_type.getKey(), false, true);
        caseName= new CellAdapter("caseName","案件名称",1,0,caseNameRule);
        
        ValidRule caseSrcRule = new ValidRule(1, ValidRule.DataType.str_type.getKey(), false, true);
        recordSrc= new CellAdapter("recordSrc","案件来源",2,0,caseSrcRule);
        
        ValidRule anfaAddressRule = new ValidRule(50, ValidRule.DataType.str_type.getKey(), false, true);
        anfaAddress= new CellAdapter("anfaAddress","案发区域",3,0,anfaAddressRule);
        
        //案发时间
        ValidRule anfaTimeRule = new ValidRule(1, ValidRule.DataType.date_type.getKey(), false, true);
        anfaTime= new CellAdapter("anfaTime","案发时间",4,0,anfaTimeRule);
        
        //违法事实
        ValidRule caseDetailNameRule = new ValidRule(-1, ValidRule.DataType.str_type.getKey(), false, true);
        caseDetailName= new CellAdapter("caseDetailName","违法事实",5,0,caseDetailNameRule);
        
        ValidRule undertakerRule = new ValidRule(15, ValidRule.DataType.str_type.getKey(), false, true);
        undertaker= new CellAdapter("undertaker","承办人",6,0,undertakerRule);
        
        //承办时间
        ValidRule undertakerTimeRule = new ValidRule(1, ValidRule.DataType.date_type.getKey(), false, true);
        undertakerTime= new CellAdapter("undertakerTime","承办时间",7,0,anfaTimeRule);
        
        ValidRule undertakerSuggestRule = new ValidRule(400, ValidRule.DataType.str_type.getKey(), false, true);
        undertakerSuggest= new CellAdapter("undertakerSuggest","承办意见",8,0,undertakerSuggestRule);
        
        ValidRule casePartyJsonRule=new ValidRule(250, ValidRule.DataType.str_type.getKey(), false, false);
        casePartyJson=new CellAdapter("casePartyJson","当事人信息(多个用,分隔)",9,0,casePartyJsonRule);
        
        ValidRule caseCompanyJsonRule=new ValidRule(250,ValidRule.DataType.str_type.getKey(),false,false);
        caseCompanyJson=new CellAdapter("caseCompanyJson","企业信息(多个用,分隔)",10,0,caseCompanyJsonRule);
        

        //以下数据需要特别处理
        anfaAddress.needConvert(true);
        recordSrc.needConvert(true);
        anfaTime.needConvert(true);
        undertakerTime.needConvert(true);
        casePartyJson.needConvert(true);
        caseCompanyJson.needConvert(true);
        
        caseCell.add(caseNo);
        caseCell.add(caseName);
        caseCell.add(recordSrc);
        caseCell.add(anfaAddress);
        //违法行为发生时间
        caseCell.add(anfaTime);
        caseCell.add(caseDetailName);
        caseCell.add(undertaker);
        caseCell.add(undertakerTime);
        caseCell.add(undertakerSuggest);
        caseCell.add(casePartyJson);
        caseCell.add(caseCompanyJson);
    }

    /**
     * 根据模板名字和位置进行验证
     *
     * @param excel
     * @return
     */
    public static ServiceResponse validate(Excel excel) {
        ServiceResponse res = new ServiceResponse(true, "");
        StringBuffer errorMsg = new StringBuffer();
        List<CellAdapter> cellAdapters = correctTemPosition(excel);
        Iterator<CellAdapter> tem = null;
        if (cellAdapters != null && cellAdapters.size() != 0) {
            tem = cellAdapters.iterator();
        } else {
            tem = caseCell.iterator();
        }
        while (tem.hasNext()) {
            CellAdapter adapter = tem.next();
            if (!adapter.getName().equals(excel.cell(adapter.getRow(), adapter.getCol()).value())) {
                errorMsg.append("模板中\"" + adapter.getName() + ":\"" + excel.cell(adapter.getRow(), adapter.getCol()).value() + "\"\n");
            }
        }
        if (errorMsg.length() != 0) {
            res.setingError("模板被修改，请重新下载模板<br>"+errorMsg.toString());
            return res;
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

    public static List<CaseImport> convertCellToObject(Excel excel) {
        List<CaseImport> casePartyList = new ArrayList<CaseImport>();
        int validLength = getObjectCount(excel);
        for (int i = 1; i <= validLength; i++) {
            Object obj = excel.cell(caseCell.get(0).getRow() + i, caseCell.get(0).getCol()).value();
            if (caseCell.get(0).getPropertyName().equals("idsNo") && obj.toString().equals("")) {
                continue;
            }
            CaseImport temp = new CaseImport();
            MethodInvoker method = new MethodInvoker();
            method.setTargetObject(temp);
            Iterator<CellAdapter> tem = caseCell.iterator();
            while (tem.hasNext()) {
                CellAdapter adapter = tem.next();
                Object args = excel.cell(adapter.getRow() + i, adapter.getCol()).value();
                if (adapter.needConvert()) {
                    args = CaseTemplateUtil.convert(adapter.getPropertyName(), args);
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
            casePartyList.add(temp);
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
    /**
     * 验证数据有效性
     *
     * @param excel
     * @return
     */
    public static List<CaseBasic> validateDate(Excel excel) {
        int validLength = getObjectCount(excel);
        /** 用来保存有唯一值验证的字段的值
         **/
        Map<String, List<Object>> paramMap = new HashMap<String, List<Object>>();
        Map<Object, Integer> lineNumMap = new HashMap<Object, Integer>();
        List<CaseBasic> extList = new ArrayList<CaseBasic>();
        MybatisAutoMapperService mybatisAutoMapperService = SpringContext.getApplicationContext().getBean(MybatisAutoMapperService.class);
        CaseBasicExample example = new CaseBasicExample();
        CaseImportExample importExample=new CaseImportExample();

        for (int i = 1; i <= validLength; i++) { //行递增

            Iterator<CellAdapter> tem = caseCell.iterator();
            CaseBasic caseBasic = new CaseBasic();
            List<String> errors = new ArrayList<String>();
            caseBasic.setValidError(errors);
            caseBasic.setExcelRowNum(i + 1); //行号比索引大1
            caseBasic.setCaseNo(excel.cell(caseNo.getRow() + i, caseNo.getCol()).value().toString());
            caseBasic.setCaseName(excel.cell(caseName.getRow() + i, caseName.getCol()).value().toString());
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
                	//验证当事人和涉案企业的名字是否过长
                	//TODO:添加号码验证
                	if (adapter.getPropertyName().equals("casePartyJson")) {
                		String peopleNameString = StringUtils.replace((String)args, "，", ",");
                		peopleNameString = StringUtils.replace(peopleNameString, "：", ":");
                		peopleNameString = peopleNameString.replaceAll("\\s*", "");//去空格
						String[] peopleNameArray= (peopleNameString.split(","));
						for (String peopleName : peopleNameArray) {
							String[] peopleIdArray = peopleName.split(":");
							if(peopleIdArray.length!=2){
								errors.add("\"当事人信息格式错误！\"");
								break;
							}
							else if(peopleIdArray[0].length()>=25){
								errors.add("\"当事人的名字过长！\"");
								break;
							}
							else if(!(CheckIdentity.CheckIDCard(peopleIdArray[1]))){
								errors.add("\"身份证信息格式错误！\"");
							}
						}
					}
                	if (adapter.getPropertyName().equals("caseCompanyJson")) {
                		String companyNameString = StringUtils.replace((String)args, "，", ",");
                		companyNameString = StringUtils.replace(companyNameString, "：", ":");
                		companyNameString = companyNameString.replaceAll("\\s*", "");//去空格
                		String[] companyNameArray=companyNameString.split(",");
                		for (String companyName : companyNameArray) {
                			String[] companyIdArray = companyName.split(":");
                			if(companyIdArray.length!=2){
                				errors.add("\"涉案企业信息格式错误！\"");
                			}
                			else if(companyIdArray[0].length()>=25){
                				errors.add("\"涉案企业的名字过长！\"");
                			}
						}
                	}
                	
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
                            lineNumMap.put(args,caseBasic.getExcelRowNum()); //保存行号
                        }
                        if (adapter.getPropertyName().equals("caseNo")) {    //TODO:::对案件编号进行数据验证唯一性，特别处理，可以考虑重构
                            example.clear();
                            example.createCriteria().andCaseNoEqualTo(String.valueOf(args));
                            importExample.clear();
                            importExample.createCriteria().andCaseNoEqualTo(String.valueOf(args));
                            if (mybatisAutoMapperService.countByExample(CaseBasicMapper.class, example) > 0
                               ||mybatisAutoMapperService.countByExample(CaseImportMapper.class,importExample)>0
                            ) {
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
            if (caseBasic.getValidError().size() != 0) {
                extList.add(caseBasic);
            }
        }
        return extList;
    }
}

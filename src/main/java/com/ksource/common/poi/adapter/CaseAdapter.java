package com.ksource.common.poi.adapter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.util.MethodInvoker;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.poi.Excel;
import com.ksource.liangfa.domain.CaseBasic;

/**
 * Created by IntelliJ IDEA.
 * User: zxl
 * Date: 12-11-28
 * Time: 下午3:56
 * To change this template use File | Settings | File Templates.
 */
public class CaseAdapter {
    private static CellAdapter caseNo;
    private static CellAdapter caseName;
    private static CellAdapter amountInvolved;
    private static CellAdapter recordSrc;
    private static CellAdapter undertaker;
    private static CellAdapter lianTime;
    private static CellAdapter approver;
    private static CellAdapter chachuTime;
    private static CellAdapter caseDetailName;
    private static List<CellAdapter> caseCell=new ArrayList<CellAdapter>();
    static {
        caseNo= new CellAdapter("caseNo","案件编号",1,3,3,3);
        caseName= new CellAdapter("caseName","案件名称",5,3,7,3);
        amountInvolved= new CellAdapter("amountInvolved","涉案金额",1,4,3,4);
        recordSrc= new CellAdapter("recordSrc","案件来源",5,4,7,4);
        undertaker= new CellAdapter("undertaker","承办人",1,5,3,5);
        lianTime= new CellAdapter("lianTime","立案时间",5,5,7,5);
        approver= new CellAdapter("approver","批准人",1,6,3,6);
        chachuTime= new CellAdapter("chachuTime","查处时间",5,6,7,6);
        caseDetailName= new CellAdapter("caseDetailName","案情简介",1,7,1,8);

        //以下数据需要特别处理
        recordSrc.needConvert(true);

        caseCell.add(caseNo);
        caseCell.add(caseName);
        caseCell.add(amountInvolved);
        caseCell.add(recordSrc);
        caseCell.add(undertaker);
        caseCell.add(lianTime);
        caseCell.add(approver);
        caseCell.add(chachuTime);
        caseCell.add(caseDetailName);
    }
    public static ServiceResponse validate(Excel excel){
        ServiceResponse res = new ServiceResponse(true,"");
        StringBuffer errorMsg = new StringBuffer();
        Iterator<CellAdapter> tem = caseCell.iterator();
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

    public static CaseBasic convertCellToCase(Excel excel) {
        CaseBasic temp = new CaseBasic();
        MethodInvoker method = new MethodInvoker();
        method.setTargetObject(temp);
        Iterator<CellAdapter> tem = caseCell.iterator();
        while (tem.hasNext()){
            CellAdapter adapter=tem.next();
            Object obj=excel.cell(adapter.getValRow(),adapter.getValCol()).value();
            if(adapter.needConvert()){
                   obj=CaseTemplateUtil.convert(adapter.getPropertyName(),obj);
            }
            method.setTargetMethod("set" +
                            Character.toUpperCase(adapter.getPropertyName().charAt(0)) +
                            adapter.getPropertyName().substring(1));
             method.setArguments(new Object[]{obj});
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
        return temp;
    }
}

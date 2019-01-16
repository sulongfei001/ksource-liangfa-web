package com.ksource.common.poi.adapter;

import com.ksource.common.poi.ValidRule;
import org.apache.poi.ss.usermodel.Cell;

/**
 * Created by IntelliJ IDEA.
 * User: zxl
 * Date: 12-11-28
 * Time: 下午3:56
 * 此类为字段与表格内容转换器，把excel里表格里的信息转换成对象字段信息
 */
public class CellAdapter {
    public CellAdapter(){

    }
    public CellAdapter(String propertyName,String name,int col,int row,int valCol,int valRow){
        this.propertyName=propertyName;
        this.name=name;
        this.col=col;
        this.row=row;
        this.valCol=valCol;
        this.valRow=valRow;
    }
    public CellAdapter(String name,int col,int row,ValidRule rule){
        this.name=name;
        this.col=col;
        this.row=row;
        this.validRule=rule;
    }
    public CellAdapter(String propertyName,String name,int col,int row,ValidRule rule){
        this.propertyName=propertyName;
        this.name=name;
        this.col=col;
        this.row=row;
        this.validRule=rule;
    }
    /** 表格对应的对象属性名*/
    private String propertyName;
     /** 模板字段名称*/
    private String name;
    /** 模板字段名称所在列*/
    private int col;
    /** 模板字段名称所在行*/
    private int row;
    /** 模板字段名称对应的值所在列*/
    private int valCol;
    /** 模板字段名称对应的值所在行*/
    private int valRow;
    /** 是否需要转换 用来处理特别的值 */
    private boolean needConvert=false;

    public ValidRule getValidRule() {
        return validRule;
    }

    public void setValidRule(ValidRule validRule) {
        this.validRule = validRule;
    }

    /** 验证规则**/
    private ValidRule validRule;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getValCol() {
        return valCol;
    }

    public void setValCol(int valCol) {
        this.valCol = valCol;
    }

    public int getValRow() {
        return valRow;
    }

    public void setValRow(int valRow) {
        this.valRow = valRow;
    }
    public void needConvert(boolean needConvert){
          this.needConvert=needConvert;
    }
    public boolean needConvert(){
          return needConvert;
    }
}

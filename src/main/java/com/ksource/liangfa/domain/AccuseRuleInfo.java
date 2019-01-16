package com.ksource.liangfa.domain;

/**
 * 量刑标准规则实体类
 * @author wsq
 *
 */
public class AccuseRuleInfo {
    
    //案件名称关键字
    private String caseName;
    /**案件名称的研判系数*/
    private Double nameThreshold;
    //案件简介关键字
    private String caseDetail;
    /**案件详情研判系数*/
    private Double detailThreshold;
    /**
     *涉案金额   大于
     */
    private Double  moneyNumberBefore ;
    /**
     * 涉案金额   小于
     */
    private Double  moneyNumberAfter ;
    
    /**
     *涉案金额   大于
     */
    private Double  goodsCountNumberBefore ;
    /**
     * 涉案金额   小于
     */
    private Double  goodsCountNumberAfter ;
    public String getCaseName() {
        return caseName;
    }
    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }
    public String getCaseDetail() {
        return caseDetail;
    }
    public void setCaseDetail(String caseDetail) {
        this.caseDetail = caseDetail;
    }
    public Double getMoneyNumberBefore() {
        return moneyNumberBefore;
    }
    public void setMoneyNumberBefore(Double moneyNumberBefore) {
        this.moneyNumberBefore = moneyNumberBefore;
    }
    public Double getMoneyNumberAfter() {
        return moneyNumberAfter;
    }
    public void setMoneyNumberAfter(Double moneyNumberAfter) {
        this.moneyNumberAfter = moneyNumberAfter;
    }
    public Double getGoodsCountNumberBefore() {
        return goodsCountNumberBefore;
    }
    public void setGoodsCountNumberBefore(Double goodsCountNumberBefore) {
        this.goodsCountNumberBefore = goodsCountNumberBefore;
    }
    public Double getGoodsCountNumberAfter() {
        return goodsCountNumberAfter;
    }
    public void setGoodsCountNumberAfter(Double goodsCountNumberAfter) {
        this.goodsCountNumberAfter = goodsCountNumberAfter;
    }
    public Double getNameThreshold() {
        return nameThreshold;
    }
    public void setNameThreshold(Double nameThreshold) {
        this.nameThreshold = nameThreshold;
    }
    public Double getDetailThreshold() {
        return detailThreshold;
    }
    public void setDetailThreshold(Double detailThreshold) {
        this.detailThreshold = detailThreshold;
    }
    
}

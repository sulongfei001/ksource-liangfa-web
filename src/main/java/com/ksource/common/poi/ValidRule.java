package com.ksource.common.poi;

import java.util.List;

/**
 * 用于表示单元格的校验规则
 * User: zxl
 * Date: 13-4-27
 * Time: 上午10:37
 */
public class ValidRule {
    private Integer length;
    private String type = DataType.str_type.value;
    private boolean isUnique = false;
    private List scope;
    private boolean required=false;

    public ValidRule(Integer length, String type, boolean isUnique,boolean required) {
        this.length = length;
        this.type = type;
        this.isUnique = isUnique;
        this.required=required;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public void setUnique(boolean unique) {
        isUnique = unique;
    }

    public List getScope() {
        return scope;
    }

    public void setScope(List scope) {
        this.scope = scope;
    }

    //[字符，整数，小数，时间]
    public enum DataType {
       short_type("SHORT","short类型"),int_type("INT", "整数类型"), str_type("STRING", "字符类型"), dou_type("DOUBLE", "小数类型"), date_type("DATE", "时间类型"),
       integer_type("INTEGER","integer类型");
        private String key;
        private String value;

        private DataType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}

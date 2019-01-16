package com.ksource.liangfa.domain;

import java.util.ArrayList;
import java.util.List;

public class UkeyUserExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    public UkeyUserExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andSerialNumberIsNull() {
            addCriterion("SERIAL_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andSerialNumberIsNotNull() {
            addCriterion("SERIAL_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andSerialNumberEqualTo(String value) {
            addCriterion("SERIAL_NUMBER =", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberNotEqualTo(String value) {
            addCriterion("SERIAL_NUMBER <>", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberGreaterThan(String value) {
            addCriterion("SERIAL_NUMBER >", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberGreaterThanOrEqualTo(String value) {
            addCriterion("SERIAL_NUMBER >=", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberLessThan(String value) {
            addCriterion("SERIAL_NUMBER <", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberLessThanOrEqualTo(String value) {
            addCriterion("SERIAL_NUMBER <=", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberLike(String value) {
            addCriterion("SERIAL_NUMBER like", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberNotLike(String value) {
            addCriterion("SERIAL_NUMBER not like", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberIn(List<String> values) {
            addCriterion("SERIAL_NUMBER in", values, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberNotIn(List<String> values) {
            addCriterion("SERIAL_NUMBER not in", values, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberBetween(String value1, String value2) {
            addCriterion("SERIAL_NUMBER between", value1, value2, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberNotBetween(String value1, String value2) {
            addCriterion("SERIAL_NUMBER not between", value1, value2, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("USER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("USER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("USER_NAME =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("USER_NAME <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("USER_NAME >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("USER_NAME >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("USER_NAME <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("USER_NAME <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("USER_NAME like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("USER_NAME not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("USER_NAME in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("USER_NAME not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("USER_NAME between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("USER_NAME not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andAdminPassIsNull() {
            addCriterion("ADMIN_PASS is null");
            return (Criteria) this;
        }

        public Criteria andAdminPassIsNotNull() {
            addCriterion("ADMIN_PASS is not null");
            return (Criteria) this;
        }

        public Criteria andAdminPassEqualTo(String value) {
            addCriterion("ADMIN_PASS =", value, "adminPass");
            return (Criteria) this;
        }

        public Criteria andAdminPassNotEqualTo(String value) {
            addCriterion("ADMIN_PASS <>", value, "adminPass");
            return (Criteria) this;
        }

        public Criteria andAdminPassGreaterThan(String value) {
            addCriterion("ADMIN_PASS >", value, "adminPass");
            return (Criteria) this;
        }

        public Criteria andAdminPassGreaterThanOrEqualTo(String value) {
            addCriterion("ADMIN_PASS >=", value, "adminPass");
            return (Criteria) this;
        }

        public Criteria andAdminPassLessThan(String value) {
            addCriterion("ADMIN_PASS <", value, "adminPass");
            return (Criteria) this;
        }

        public Criteria andAdminPassLessThanOrEqualTo(String value) {
            addCriterion("ADMIN_PASS <=", value, "adminPass");
            return (Criteria) this;
        }

        public Criteria andAdminPassLike(String value) {
            addCriterion("ADMIN_PASS like", value, "adminPass");
            return (Criteria) this;
        }

        public Criteria andAdminPassNotLike(String value) {
            addCriterion("ADMIN_PASS not like", value, "adminPass");
            return (Criteria) this;
        }

        public Criteria andAdminPassIn(List<String> values) {
            addCriterion("ADMIN_PASS in", values, "adminPass");
            return (Criteria) this;
        }

        public Criteria andAdminPassNotIn(List<String> values) {
            addCriterion("ADMIN_PASS not in", values, "adminPass");
            return (Criteria) this;
        }

        public Criteria andAdminPassBetween(String value1, String value2) {
            addCriterion("ADMIN_PASS between", value1, value2, "adminPass");
            return (Criteria) this;
        }

        public Criteria andAdminPassNotBetween(String value1, String value2) {
            addCriterion("ADMIN_PASS not between", value1, value2, "adminPass");
            return (Criteria) this;
        }

        public Criteria andSalfValueIsNull() {
            addCriterion("SALF_VALUE is null");
            return (Criteria) this;
        }

        public Criteria andSalfValueIsNotNull() {
            addCriterion("SALF_VALUE is not null");
            return (Criteria) this;
        }

        public Criteria andSalfValueEqualTo(String value) {
            addCriterion("SALF_VALUE =", value, "salfValue");
            return (Criteria) this;
        }

        public Criteria andSalfValueNotEqualTo(String value) {
            addCriterion("SALF_VALUE <>", value, "salfValue");
            return (Criteria) this;
        }

        public Criteria andSalfValueGreaterThan(String value) {
            addCriterion("SALF_VALUE >", value, "salfValue");
            return (Criteria) this;
        }

        public Criteria andSalfValueGreaterThanOrEqualTo(String value) {
            addCriterion("SALF_VALUE >=", value, "salfValue");
            return (Criteria) this;
        }

        public Criteria andSalfValueLessThan(String value) {
            addCriterion("SALF_VALUE <", value, "salfValue");
            return (Criteria) this;
        }

        public Criteria andSalfValueLessThanOrEqualTo(String value) {
            addCriterion("SALF_VALUE <=", value, "salfValue");
            return (Criteria) this;
        }

        public Criteria andSalfValueLike(String value) {
            addCriterion("SALF_VALUE like", value, "salfValue");
            return (Criteria) this;
        }

        public Criteria andSalfValueNotLike(String value) {
            addCriterion("SALF_VALUE not like", value, "salfValue");
            return (Criteria) this;
        }

        public Criteria andSalfValueIn(List<String> values) {
            addCriterion("SALF_VALUE in", values, "salfValue");
            return (Criteria) this;
        }

        public Criteria andSalfValueNotIn(List<String> values) {
            addCriterion("SALF_VALUE not in", values, "salfValue");
            return (Criteria) this;
        }

        public Criteria andSalfValueBetween(String value1, String value2) {
            addCriterion("SALF_VALUE between", value1, value2, "salfValue");
            return (Criteria) this;
        }

        public Criteria andSalfValueNotBetween(String value1, String value2) {
            addCriterion("SALF_VALUE not between", value1, value2, "salfValue");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table UKEY_USER
     *
     * @mbggenerated do_not_delete_during_merge Thu Jun 07 14:27:55 CST 2012
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table UKEY_USER
     *
     * @mbggenerated Thu Jun 07 14:27:55 CST 2012
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
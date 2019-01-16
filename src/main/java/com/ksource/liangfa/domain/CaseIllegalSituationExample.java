package com.ksource.liangfa.domain;

import java.util.ArrayList;
import java.util.List;

public class CaseIllegalSituationExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ILLEGAL_SITUATION
     *
     * @mbggenerated Wed Mar 02 15:53:04 CST 2016
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ILLEGAL_SITUATION
     *
     * @mbggenerated Wed Mar 02 15:53:04 CST 2016
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ILLEGAL_SITUATION
     *
     * @mbggenerated Wed Mar 02 15:53:04 CST 2016
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ILLEGAL_SITUATION
     *
     * @mbggenerated Wed Mar 02 15:53:04 CST 2016
     */
    public CaseIllegalSituationExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ILLEGAL_SITUATION
     *
     * @mbggenerated Wed Mar 02 15:53:04 CST 2016
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ILLEGAL_SITUATION
     *
     * @mbggenerated Wed Mar 02 15:53:04 CST 2016
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ILLEGAL_SITUATION
     *
     * @mbggenerated Wed Mar 02 15:53:04 CST 2016
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ILLEGAL_SITUATION
     *
     * @mbggenerated Wed Mar 02 15:53:04 CST 2016
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ILLEGAL_SITUATION
     *
     * @mbggenerated Wed Mar 02 15:53:04 CST 2016
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ILLEGAL_SITUATION
     *
     * @mbggenerated Wed Mar 02 15:53:04 CST 2016
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ILLEGAL_SITUATION
     *
     * @mbggenerated Wed Mar 02 15:53:04 CST 2016
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ILLEGAL_SITUATION
     *
     * @mbggenerated Wed Mar 02 15:53:04 CST 2016
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
     * This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ILLEGAL_SITUATION
     *
     * @mbggenerated Wed Mar 02 15:53:04 CST 2016
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ILLEGAL_SITUATION
     *
     * @mbggenerated Wed Mar 02 15:53:04 CST 2016
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ILLEGAL_SITUATION
     *
     * @mbggenerated Wed Mar 02 15:53:04 CST 2016
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

        public Criteria andCaseIdIsNull() {
            addCriterion("CASE_ID is null");
            return (Criteria) this;
        }

        public Criteria andCaseIdIsNotNull() {
            addCriterion("CASE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCaseIdEqualTo(String value) {
            addCriterion("CASE_ID =", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdNotEqualTo(String value) {
            addCriterion("CASE_ID <>", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdGreaterThan(String value) {
            addCriterion("CASE_ID >", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdGreaterThanOrEqualTo(String value) {
            addCriterion("CASE_ID >=", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdLessThan(String value) {
            addCriterion("CASE_ID <", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdLessThanOrEqualTo(String value) {
            addCriterion("CASE_ID <=", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdLike(String value) {
            addCriterion("CASE_ID like", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdNotLike(String value) {
            addCriterion("CASE_ID not like", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdIn(List<String> values) {
            addCriterion("CASE_ID in", values, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdNotIn(List<String> values) {
            addCriterion("CASE_ID not in", values, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdBetween(String value1, String value2) {
            addCriterion("CASE_ID between", value1, value2, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdNotBetween(String value1, String value2) {
            addCriterion("CASE_ID not between", value1, value2, "caseId");
            return (Criteria) this;
        }

        public Criteria andSituationIdIsNull() {
            addCriterion("SITUATION_ID is null");
            return (Criteria) this;
        }

        public Criteria andSituationIdIsNotNull() {
            addCriterion("SITUATION_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSituationIdEqualTo(String value) {
            addCriterion("SITUATION_ID =", value, "situationId");
            return (Criteria) this;
        }

        public Criteria andSituationIdNotEqualTo(String value) {
            addCriterion("SITUATION_ID <>", value, "situationId");
            return (Criteria) this;
        }

        public Criteria andSituationIdGreaterThan(String value) {
            addCriterion("SITUATION_ID >", value, "situationId");
            return (Criteria) this;
        }

        public Criteria andSituationIdGreaterThanOrEqualTo(String value) {
            addCriterion("SITUATION_ID >=", value, "situationId");
            return (Criteria) this;
        }

        public Criteria andSituationIdLessThan(String value) {
            addCriterion("SITUATION_ID <", value, "situationId");
            return (Criteria) this;
        }

        public Criteria andSituationIdLessThanOrEqualTo(String value) {
            addCriterion("SITUATION_ID <=", value, "situationId");
            return (Criteria) this;
        }

        public Criteria andSituationIdLike(String value) {
            addCriterion("SITUATION_ID like", value, "situationId");
            return (Criteria) this;
        }

        public Criteria andSituationIdNotLike(String value) {
            addCriterion("SITUATION_ID not like", value, "situationId");
            return (Criteria) this;
        }

        public Criteria andSituationIdIn(List<String> values) {
            addCriterion("SITUATION_ID in", values, "situationId");
            return (Criteria) this;
        }

        public Criteria andSituationIdNotIn(List<String> values) {
            addCriterion("SITUATION_ID not in", values, "situationId");
            return (Criteria) this;
        }

        public Criteria andSituationIdBetween(String value1, String value2) {
            addCriterion("SITUATION_ID between", value1, value2, "situationId");
            return (Criteria) this;
        }

        public Criteria andSituationIdNotBetween(String value1, String value2) {
            addCriterion("SITUATION_ID not between", value1, value2, "situationId");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ILLEGAL_SITUATION
     *
     * @mbggenerated do_not_delete_during_merge Wed Mar 02 15:53:04 CST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ILLEGAL_SITUATION
     *
     * @mbggenerated Wed Mar 02 15:53:04 CST 2016
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
package com.ksource.liangfa.domain;

import java.util.ArrayList;
import java.util.List;

public class DiscretionStandardExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table LIANGFA_XINXIANG20170222.DISCRETION_STANDARD
     *
     * @mbggenerated Mon Mar 27 13:54:08 CST 2017
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table LIANGFA_XINXIANG20170222.DISCRETION_STANDARD
     *
     * @mbggenerated Mon Mar 27 13:54:08 CST 2017
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table LIANGFA_XINXIANG20170222.DISCRETION_STANDARD
     *
     * @mbggenerated Mon Mar 27 13:54:08 CST 2017
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.DISCRETION_STANDARD
     *
     * @mbggenerated Mon Mar 27 13:54:08 CST 2017
     */
    public DiscretionStandardExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.DISCRETION_STANDARD
     *
     * @mbggenerated Mon Mar 27 13:54:08 CST 2017
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.DISCRETION_STANDARD
     *
     * @mbggenerated Mon Mar 27 13:54:08 CST 2017
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.DISCRETION_STANDARD
     *
     * @mbggenerated Mon Mar 27 13:54:08 CST 2017
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.DISCRETION_STANDARD
     *
     * @mbggenerated Mon Mar 27 13:54:08 CST 2017
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.DISCRETION_STANDARD
     *
     * @mbggenerated Mon Mar 27 13:54:08 CST 2017
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.DISCRETION_STANDARD
     *
     * @mbggenerated Mon Mar 27 13:54:08 CST 2017
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.DISCRETION_STANDARD
     *
     * @mbggenerated Mon Mar 27 13:54:08 CST 2017
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.DISCRETION_STANDARD
     *
     * @mbggenerated Mon Mar 27 13:54:08 CST 2017
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
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.DISCRETION_STANDARD
     *
     * @mbggenerated Mon Mar 27 13:54:08 CST 2017
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.DISCRETION_STANDARD
     *
     * @mbggenerated Mon Mar 27 13:54:08 CST 2017
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table LIANGFA_XINXIANG20170222.DISCRETION_STANDARD
     *
     * @mbggenerated Mon Mar 27 13:54:08 CST 2017
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

        public Criteria andStandardIdIsNull() {
            addCriterion("STANDARD_ID is null");
            return (Criteria) this;
        }

        public Criteria andStandardIdIsNotNull() {
            addCriterion("STANDARD_ID is not null");
            return (Criteria) this;
        }

        public Criteria andStandardIdEqualTo(Integer value) {
            addCriterion("STANDARD_ID =", value, "standardId");
            return (Criteria) this;
        }

        public Criteria andStandardIdNotEqualTo(Integer value) {
            addCriterion("STANDARD_ID <>", value, "standardId");
            return (Criteria) this;
        }

        public Criteria andStandardIdGreaterThan(Integer value) {
            addCriterion("STANDARD_ID >", value, "standardId");
            return (Criteria) this;
        }

        public Criteria andStandardIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("STANDARD_ID >=", value, "standardId");
            return (Criteria) this;
        }

        public Criteria andStandardIdLessThan(Integer value) {
            addCriterion("STANDARD_ID <", value, "standardId");
            return (Criteria) this;
        }

        public Criteria andStandardIdLessThanOrEqualTo(Integer value) {
            addCriterion("STANDARD_ID <=", value, "standardId");
            return (Criteria) this;
        }

        public Criteria andStandardIdIn(List<Integer> values) {
            addCriterion("STANDARD_ID in", values, "standardId");
            return (Criteria) this;
        }

        public Criteria andStandardIdNotIn(List<Integer> values) {
            addCriterion("STANDARD_ID not in", values, "standardId");
            return (Criteria) this;
        }

        public Criteria andStandardIdBetween(Integer value1, Integer value2) {
            addCriterion("STANDARD_ID between", value1, value2, "standardId");
            return (Criteria) this;
        }

        public Criteria andStandardIdNotBetween(Integer value1, Integer value2) {
            addCriterion("STANDARD_ID not between", value1, value2, "standardId");
            return (Criteria) this;
        }

        public Criteria andBasisIdIsNull() {
            addCriterion("BASIS_ID is null");
            return (Criteria) this;
        }

        public Criteria andBasisIdIsNotNull() {
            addCriterion("BASIS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBasisIdEqualTo(Integer value) {
            addCriterion("BASIS_ID =", value, "basisId");
            return (Criteria) this;
        }

        public Criteria andBasisIdNotEqualTo(Integer value) {
            addCriterion("BASIS_ID <>", value, "basisId");
            return (Criteria) this;
        }

        public Criteria andBasisIdGreaterThan(Integer value) {
            addCriterion("BASIS_ID >", value, "basisId");
            return (Criteria) this;
        }

        public Criteria andBasisIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("BASIS_ID >=", value, "basisId");
            return (Criteria) this;
        }

        public Criteria andBasisIdLessThan(Integer value) {
            addCriterion("BASIS_ID <", value, "basisId");
            return (Criteria) this;
        }

        public Criteria andBasisIdLessThanOrEqualTo(Integer value) {
            addCriterion("BASIS_ID <=", value, "basisId");
            return (Criteria) this;
        }

        public Criteria andBasisIdIn(List<Integer> values) {
            addCriterion("BASIS_ID in", values, "basisId");
            return (Criteria) this;
        }

        public Criteria andBasisIdNotIn(List<Integer> values) {
            addCriterion("BASIS_ID not in", values, "basisId");
            return (Criteria) this;
        }

        public Criteria andBasisIdBetween(Integer value1, Integer value2) {
            addCriterion("BASIS_ID between", value1, value2, "basisId");
            return (Criteria) this;
        }

        public Criteria andBasisIdNotBetween(Integer value1, Integer value2) {
            addCriterion("BASIS_ID not between", value1, value2, "basisId");
            return (Criteria) this;
        }

        public Criteria andTermIdIsNull() {
            addCriterion("TERM_ID is null");
            return (Criteria) this;
        }

        public Criteria andTermIdIsNotNull() {
            addCriterion("TERM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTermIdEqualTo(Integer value) {
            addCriterion("TERM_ID =", value, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdNotEqualTo(Integer value) {
            addCriterion("TERM_ID <>", value, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdGreaterThan(Integer value) {
            addCriterion("TERM_ID >", value, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("TERM_ID >=", value, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdLessThan(Integer value) {
            addCriterion("TERM_ID <", value, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdLessThanOrEqualTo(Integer value) {
            addCriterion("TERM_ID <=", value, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdIn(List<Integer> values) {
            addCriterion("TERM_ID in", values, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdNotIn(List<Integer> values) {
            addCriterion("TERM_ID not in", values, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdBetween(Integer value1, Integer value2) {
            addCriterion("TERM_ID between", value1, value2, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdNotBetween(Integer value1, Integer value2) {
            addCriterion("TERM_ID not between", value1, value2, "termId");
            return (Criteria) this;
        }

        public Criteria andDiscretionLevelIsNull() {
            addCriterion("DISCRETION_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andDiscretionLevelIsNotNull() {
            addCriterion("DISCRETION_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andDiscretionLevelEqualTo(Integer value) {
            addCriterion("DISCRETION_LEVEL =", value, "discretionLevel");
            return (Criteria) this;
        }

        public Criteria andDiscretionLevelNotEqualTo(Integer value) {
            addCriterion("DISCRETION_LEVEL <>", value, "discretionLevel");
            return (Criteria) this;
        }

        public Criteria andDiscretionLevelGreaterThan(Integer value) {
            addCriterion("DISCRETION_LEVEL >", value, "discretionLevel");
            return (Criteria) this;
        }

        public Criteria andDiscretionLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("DISCRETION_LEVEL >=", value, "discretionLevel");
            return (Criteria) this;
        }

        public Criteria andDiscretionLevelLessThan(Integer value) {
            addCriterion("DISCRETION_LEVEL <", value, "discretionLevel");
            return (Criteria) this;
        }

        public Criteria andDiscretionLevelLessThanOrEqualTo(Integer value) {
            addCriterion("DISCRETION_LEVEL <=", value, "discretionLevel");
            return (Criteria) this;
        }

        public Criteria andDiscretionLevelIn(List<Integer> values) {
            addCriterion("DISCRETION_LEVEL in", values, "discretionLevel");
            return (Criteria) this;
        }

        public Criteria andDiscretionLevelNotIn(List<Integer> values) {
            addCriterion("DISCRETION_LEVEL not in", values, "discretionLevel");
            return (Criteria) this;
        }

        public Criteria andDiscretionLevelBetween(Integer value1, Integer value2) {
            addCriterion("DISCRETION_LEVEL between", value1, value2, "discretionLevel");
            return (Criteria) this;
        }

        public Criteria andDiscretionLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("DISCRETION_LEVEL not between", value1, value2, "discretionLevel");
            return (Criteria) this;
        }

        public Criteria andIllegalSituationIsNull() {
            addCriterion("ILLEGAL_SITUATION is null");
            return (Criteria) this;
        }

        public Criteria andIllegalSituationIsNotNull() {
            addCriterion("ILLEGAL_SITUATION is not null");
            return (Criteria) this;
        }

        public Criteria andIllegalSituationEqualTo(String value) {
            addCriterion("ILLEGAL_SITUATION =", value, "illegalSituation");
            return (Criteria) this;
        }

        public Criteria andIllegalSituationNotEqualTo(String value) {
            addCriterion("ILLEGAL_SITUATION <>", value, "illegalSituation");
            return (Criteria) this;
        }

        public Criteria andIllegalSituationGreaterThan(String value) {
            addCriterion("ILLEGAL_SITUATION >", value, "illegalSituation");
            return (Criteria) this;
        }

        public Criteria andIllegalSituationGreaterThanOrEqualTo(String value) {
            addCriterion("ILLEGAL_SITUATION >=", value, "illegalSituation");
            return (Criteria) this;
        }

        public Criteria andIllegalSituationLessThan(String value) {
            addCriterion("ILLEGAL_SITUATION <", value, "illegalSituation");
            return (Criteria) this;
        }

        public Criteria andIllegalSituationLessThanOrEqualTo(String value) {
            addCriterion("ILLEGAL_SITUATION <=", value, "illegalSituation");
            return (Criteria) this;
        }

        public Criteria andIllegalSituationLike(String value) {
            addCriterion("ILLEGAL_SITUATION like", value, "illegalSituation");
            return (Criteria) this;
        }

        public Criteria andIllegalSituationNotLike(String value) {
            addCriterion("ILLEGAL_SITUATION not like", value, "illegalSituation");
            return (Criteria) this;
        }

        public Criteria andIllegalSituationIn(List<String> values) {
            addCriterion("ILLEGAL_SITUATION in", values, "illegalSituation");
            return (Criteria) this;
        }

        public Criteria andIllegalSituationNotIn(List<String> values) {
            addCriterion("ILLEGAL_SITUATION not in", values, "illegalSituation");
            return (Criteria) this;
        }

        public Criteria andIllegalSituationBetween(String value1, String value2) {
            addCriterion("ILLEGAL_SITUATION between", value1, value2, "illegalSituation");
            return (Criteria) this;
        }

        public Criteria andIllegalSituationNotBetween(String value1, String value2) {
            addCriterion("ILLEGAL_SITUATION not between", value1, value2, "illegalSituation");
            return (Criteria) this;
        }

        public Criteria andStandardIsNull() {
            addCriterion("STANDARD is null");
            return (Criteria) this;
        }

        public Criteria andStandardIsNotNull() {
            addCriterion("STANDARD is not null");
            return (Criteria) this;
        }

        public Criteria andStandardEqualTo(String value) {
            addCriterion("STANDARD =", value, "standard");
            return (Criteria) this;
        }

        public Criteria andStandardNotEqualTo(String value) {
            addCriterion("STANDARD <>", value, "standard");
            return (Criteria) this;
        }

        public Criteria andStandardGreaterThan(String value) {
            addCriterion("STANDARD >", value, "standard");
            return (Criteria) this;
        }

        public Criteria andStandardGreaterThanOrEqualTo(String value) {
            addCriterion("STANDARD >=", value, "standard");
            return (Criteria) this;
        }

        public Criteria andStandardLessThan(String value) {
            addCriterion("STANDARD <", value, "standard");
            return (Criteria) this;
        }

        public Criteria andStandardLessThanOrEqualTo(String value) {
            addCriterion("STANDARD <=", value, "standard");
            return (Criteria) this;
        }

        public Criteria andStandardLike(String value) {
            addCriterion("STANDARD like", value, "standard");
            return (Criteria) this;
        }

        public Criteria andStandardNotLike(String value) {
            addCriterion("STANDARD not like", value, "standard");
            return (Criteria) this;
        }

        public Criteria andStandardIn(List<String> values) {
            addCriterion("STANDARD in", values, "standard");
            return (Criteria) this;
        }

        public Criteria andStandardNotIn(List<String> values) {
            addCriterion("STANDARD not in", values, "standard");
            return (Criteria) this;
        }

        public Criteria andStandardBetween(String value1, String value2) {
            addCriterion("STANDARD between", value1, value2, "standard");
            return (Criteria) this;
        }

        public Criteria andStandardNotBetween(String value1, String value2) {
            addCriterion("STANDARD not between", value1, value2, "standard");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table LIANGFA_XINXIANG20170222.DISCRETION_STANDARD
     *
     * @mbggenerated do_not_delete_during_merge Mon Mar 27 13:54:08 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table LIANGFA_XINXIANG20170222.DISCRETION_STANDARD
     *
     * @mbggenerated Mon Mar 27 13:54:08 CST 2017
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
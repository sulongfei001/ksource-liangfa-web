package com.ksource.liangfa.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CaseInformationExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table LIANGFA_HUBEI20160504.CASE_INFORMATION
     *
     * @mbggenerated Tue Jul 05 08:36:54 CST 2016
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table LIANGFA_HUBEI20160504.CASE_INFORMATION
     *
     * @mbggenerated Tue Jul 05 08:36:54 CST 2016
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table LIANGFA_HUBEI20160504.CASE_INFORMATION
     *
     * @mbggenerated Tue Jul 05 08:36:54 CST 2016
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160504.CASE_INFORMATION
     *
     * @mbggenerated Tue Jul 05 08:36:54 CST 2016
     */
    public CaseInformationExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160504.CASE_INFORMATION
     *
     * @mbggenerated Tue Jul 05 08:36:54 CST 2016
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160504.CASE_INFORMATION
     *
     * @mbggenerated Tue Jul 05 08:36:54 CST 2016
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160504.CASE_INFORMATION
     *
     * @mbggenerated Tue Jul 05 08:36:54 CST 2016
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160504.CASE_INFORMATION
     *
     * @mbggenerated Tue Jul 05 08:36:54 CST 2016
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160504.CASE_INFORMATION
     *
     * @mbggenerated Tue Jul 05 08:36:54 CST 2016
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160504.CASE_INFORMATION
     *
     * @mbggenerated Tue Jul 05 08:36:54 CST 2016
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160504.CASE_INFORMATION
     *
     * @mbggenerated Tue Jul 05 08:36:54 CST 2016
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160504.CASE_INFORMATION
     *
     * @mbggenerated Tue Jul 05 08:36:54 CST 2016
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
     * This method corresponds to the database table LIANGFA_HUBEI20160504.CASE_INFORMATION
     *
     * @mbggenerated Tue Jul 05 08:36:54 CST 2016
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160504.CASE_INFORMATION
     *
     * @mbggenerated Tue Jul 05 08:36:54 CST 2016
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table LIANGFA_HUBEI20160504.CASE_INFORMATION
     *
     * @mbggenerated Tue Jul 05 08:36:54 CST 2016
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

        public Criteria andInfoIdIsNull() {
            addCriterion("INFO_ID is null");
            return (Criteria) this;
        }

        public Criteria andInfoIdIsNotNull() {
            addCriterion("INFO_ID is not null");
            return (Criteria) this;
        }

        public Criteria andInfoIdEqualTo(Long value) {
            addCriterion("INFO_ID =", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdNotEqualTo(Long value) {
            addCriterion("INFO_ID <>", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdGreaterThan(Long value) {
            addCriterion("INFO_ID >", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdGreaterThanOrEqualTo(Long value) {
            addCriterion("INFO_ID >=", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdLessThan(Long value) {
            addCriterion("INFO_ID <", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdLessThanOrEqualTo(Long value) {
            addCriterion("INFO_ID <=", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdIn(List<Long> values) {
            addCriterion("INFO_ID in", values, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdNotIn(List<Long> values) {
            addCriterion("INFO_ID not in", values, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdBetween(Long value1, Long value2) {
            addCriterion("INFO_ID between", value1, value2, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdNotBetween(Long value1, Long value2) {
            addCriterion("INFO_ID not between", value1, value2, "infoId");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("TITLE is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("TITLE is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("TITLE =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("TITLE <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("TITLE >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("TITLE >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("TITLE <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("TITLE <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("TITLE like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("TITLE not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("TITLE in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("TITLE not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("TITLE between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("TITLE not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andCaseNoIsNull() {
            addCriterion("CASE_NO is null");
            return (Criteria) this;
        }

        public Criteria andCaseNoIsNotNull() {
            addCriterion("CASE_NO is not null");
            return (Criteria) this;
        }

        public Criteria andCaseNoEqualTo(String value) {
            addCriterion("CASE_NO =", value, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNoNotEqualTo(String value) {
            addCriterion("CASE_NO <>", value, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNoGreaterThan(String value) {
            addCriterion("CASE_NO >", value, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNoGreaterThanOrEqualTo(String value) {
            addCriterion("CASE_NO >=", value, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNoLessThan(String value) {
            addCriterion("CASE_NO <", value, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNoLessThanOrEqualTo(String value) {
            addCriterion("CASE_NO <=", value, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNoLike(String value) {
            addCriterion("CASE_NO like", value, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNoNotLike(String value) {
            addCriterion("CASE_NO not like", value, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNoIn(List<String> values) {
            addCriterion("CASE_NO in", values, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNoNotIn(List<String> values) {
            addCriterion("CASE_NO not in", values, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNoBetween(String value1, String value2) {
            addCriterion("CASE_NO between", value1, value2, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNoNotBetween(String value1, String value2) {
            addCriterion("CASE_NO not between", value1, value2, "caseNo");
            return (Criteria) this;
        }

        public Criteria andCaseNameIsNull() {
            addCriterion("CASE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCaseNameIsNotNull() {
            addCriterion("CASE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCaseNameEqualTo(String value) {
            addCriterion("CASE_NAME =", value, "caseName");
            return (Criteria) this;
        }

        public Criteria andCaseNameNotEqualTo(String value) {
            addCriterion("CASE_NAME <>", value, "caseName");
            return (Criteria) this;
        }

        public Criteria andCaseNameGreaterThan(String value) {
            addCriterion("CASE_NAME >", value, "caseName");
            return (Criteria) this;
        }

        public Criteria andCaseNameGreaterThanOrEqualTo(String value) {
            addCriterion("CASE_NAME >=", value, "caseName");
            return (Criteria) this;
        }

        public Criteria andCaseNameLessThan(String value) {
            addCriterion("CASE_NAME <", value, "caseName");
            return (Criteria) this;
        }

        public Criteria andCaseNameLessThanOrEqualTo(String value) {
            addCriterion("CASE_NAME <=", value, "caseName");
            return (Criteria) this;
        }

        public Criteria andCaseNameLike(String value) {
            addCriterion("CASE_NAME like", value, "caseName");
            return (Criteria) this;
        }

        public Criteria andCaseNameNotLike(String value) {
            addCriterion("CASE_NAME not like", value, "caseName");
            return (Criteria) this;
        }

        public Criteria andCaseNameIn(List<String> values) {
            addCriterion("CASE_NAME in", values, "caseName");
            return (Criteria) this;
        }

        public Criteria andCaseNameNotIn(List<String> values) {
            addCriterion("CASE_NAME not in", values, "caseName");
            return (Criteria) this;
        }

        public Criteria andCaseNameBetween(String value1, String value2) {
            addCriterion("CASE_NAME between", value1, value2, "caseName");
            return (Criteria) this;
        }

        public Criteria andCaseNameNotBetween(String value1, String value2) {
            addCriterion("CASE_NAME not between", value1, value2, "caseName");
            return (Criteria) this;
        }

        public Criteria andOpinionDateIsNull() {
            addCriterion("OPINION_DATE is null");
            return (Criteria) this;
        }

        public Criteria andOpinionDateIsNotNull() {
            addCriterion("OPINION_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andOpinionDateEqualTo(Date value) {
            addCriterion("OPINION_DATE =", value, "opinionDate");
            return (Criteria) this;
        }

        public Criteria andOpinionDateNotEqualTo(Date value) {
            addCriterion("OPINION_DATE <>", value, "opinionDate");
            return (Criteria) this;
        }

        public Criteria andOpinionDateGreaterThan(Date value) {
            addCriterion("OPINION_DATE >", value, "opinionDate");
            return (Criteria) this;
        }

        public Criteria andOpinionDateGreaterThanOrEqualTo(Date value) {
            addCriterion("OPINION_DATE >=", value, "opinionDate");
            return (Criteria) this;
        }

        public Criteria andOpinionDateLessThan(Date value) {
            addCriterion("OPINION_DATE <", value, "opinionDate");
            return (Criteria) this;
        }

        public Criteria andOpinionDateLessThanOrEqualTo(Date value) {
            addCriterion("OPINION_DATE <=", value, "opinionDate");
            return (Criteria) this;
        }

        public Criteria andOpinionDateIn(List<Date> values) {
            addCriterion("OPINION_DATE in", values, "opinionDate");
            return (Criteria) this;
        }

        public Criteria andOpinionDateNotIn(List<Date> values) {
            addCriterion("OPINION_DATE not in", values, "opinionDate");
            return (Criteria) this;
        }

        public Criteria andOpinionDateBetween(Date value1, Date value2) {
            addCriterion("OPINION_DATE between", value1, value2, "opinionDate");
            return (Criteria) this;
        }

        public Criteria andOpinionDateNotBetween(Date value1, Date value2) {
            addCriterion("OPINION_DATE not between", value1, value2, "opinionDate");
            return (Criteria) this;
        }

        public Criteria andKeywordIsNull() {
            addCriterion("KEYWORD is null");
            return (Criteria) this;
        }

        public Criteria andKeywordIsNotNull() {
            addCriterion("KEYWORD is not null");
            return (Criteria) this;
        }

        public Criteria andKeywordEqualTo(String value) {
            addCriterion("KEYWORD =", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordNotEqualTo(String value) {
            addCriterion("KEYWORD <>", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordGreaterThan(String value) {
            addCriterion("KEYWORD >", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordGreaterThanOrEqualTo(String value) {
            addCriterion("KEYWORD >=", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordLessThan(String value) {
            addCriterion("KEYWORD <", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordLessThanOrEqualTo(String value) {
            addCriterion("KEYWORD <=", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordLike(String value) {
            addCriterion("KEYWORD like", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordNotLike(String value) {
            addCriterion("KEYWORD not like", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordIn(List<String> values) {
            addCriterion("KEYWORD in", values, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordNotIn(List<String> values) {
            addCriterion("KEYWORD not in", values, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordBetween(String value1, String value2) {
            addCriterion("KEYWORD between", value1, value2, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordNotBetween(String value1, String value2) {
            addCriterion("KEYWORD not between", value1, value2, "keyword");
            return (Criteria) this;
        }

        public Criteria andInputerIsNull() {
            addCriterion("INPUTER is null");
            return (Criteria) this;
        }

        public Criteria andInputerIsNotNull() {
            addCriterion("INPUTER is not null");
            return (Criteria) this;
        }

        public Criteria andInputerEqualTo(Long value) {
            addCriterion("INPUTER =", value, "inputer");
            return (Criteria) this;
        }

        public Criteria andInputerNotEqualTo(Long value) {
            addCriterion("INPUTER <>", value, "inputer");
            return (Criteria) this;
        }

        public Criteria andInputerGreaterThan(Long value) {
            addCriterion("INPUTER >", value, "inputer");
            return (Criteria) this;
        }

        public Criteria andInputerGreaterThanOrEqualTo(Long value) {
            addCriterion("INPUTER >=", value, "inputer");
            return (Criteria) this;
        }

        public Criteria andInputerLessThan(Long value) {
            addCriterion("INPUTER <", value, "inputer");
            return (Criteria) this;
        }

        public Criteria andInputerLessThanOrEqualTo(Long value) {
            addCriterion("INPUTER <=", value, "inputer");
            return (Criteria) this;
        }

        public Criteria andInputerIn(List<Long> values) {
            addCriterion("INPUTER in", values, "inputer");
            return (Criteria) this;
        }

        public Criteria andInputerNotIn(List<Long> values) {
            addCriterion("INPUTER not in", values, "inputer");
            return (Criteria) this;
        }

        public Criteria andInputerBetween(Long value1, Long value2) {
            addCriterion("INPUTER between", value1, value2, "inputer");
            return (Criteria) this;
        }

        public Criteria andInputerNotBetween(Long value1, Long value2) {
            addCriterion("INPUTER not between", value1, value2, "inputer");
            return (Criteria) this;
        }

        public Criteria andInputTimeIsNull() {
            addCriterion("INPUT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andInputTimeIsNotNull() {
            addCriterion("INPUT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andInputTimeEqualTo(Date value) {
            addCriterion("INPUT_TIME =", value, "inputTime");
            return (Criteria) this;
        }

        public Criteria andInputTimeNotEqualTo(Date value) {
            addCriterion("INPUT_TIME <>", value, "inputTime");
            return (Criteria) this;
        }

        public Criteria andInputTimeGreaterThan(Date value) {
            addCriterion("INPUT_TIME >", value, "inputTime");
            return (Criteria) this;
        }

        public Criteria andInputTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("INPUT_TIME >=", value, "inputTime");
            return (Criteria) this;
        }

        public Criteria andInputTimeLessThan(Date value) {
            addCriterion("INPUT_TIME <", value, "inputTime");
            return (Criteria) this;
        }

        public Criteria andInputTimeLessThanOrEqualTo(Date value) {
            addCriterion("INPUT_TIME <=", value, "inputTime");
            return (Criteria) this;
        }

        public Criteria andInputTimeIn(List<Date> values) {
            addCriterion("INPUT_TIME in", values, "inputTime");
            return (Criteria) this;
        }

        public Criteria andInputTimeNotIn(List<Date> values) {
            addCriterion("INPUT_TIME not in", values, "inputTime");
            return (Criteria) this;
        }

        public Criteria andInputTimeBetween(Date value1, Date value2) {
            addCriterion("INPUT_TIME between", value1, value2, "inputTime");
            return (Criteria) this;
        }

        public Criteria andInputTimeNotBetween(Date value1, Date value2) {
            addCriterion("INPUT_TIME not between", value1, value2, "inputTime");
            return (Criteria) this;
        }

        public Criteria andAttachmentIsNull() {
            addCriterion("ATTACHMENT is null");
            return (Criteria) this;
        }

        public Criteria andAttachmentIsNotNull() {
            addCriterion("ATTACHMENT is not null");
            return (Criteria) this;
        }

        public Criteria andAttachmentEqualTo(Long value) {
            addCriterion("ATTACHMENT =", value, "attachment");
            return (Criteria) this;
        }

        public Criteria andAttachmentNotEqualTo(Long value) {
            addCriterion("ATTACHMENT <>", value, "attachment");
            return (Criteria) this;
        }

        public Criteria andAttachmentGreaterThan(Long value) {
            addCriterion("ATTACHMENT >", value, "attachment");
            return (Criteria) this;
        }

        public Criteria andAttachmentGreaterThanOrEqualTo(Long value) {
            addCriterion("ATTACHMENT >=", value, "attachment");
            return (Criteria) this;
        }

        public Criteria andAttachmentLessThan(Long value) {
            addCriterion("ATTACHMENT <", value, "attachment");
            return (Criteria) this;
        }

        public Criteria andAttachmentLessThanOrEqualTo(Long value) {
            addCriterion("ATTACHMENT <=", value, "attachment");
            return (Criteria) this;
        }

        public Criteria andAttachmentIn(List<Long> values) {
            addCriterion("ATTACHMENT in", values, "attachment");
            return (Criteria) this;
        }

        public Criteria andAttachmentNotIn(List<Long> values) {
            addCriterion("ATTACHMENT not in", values, "attachment");
            return (Criteria) this;
        }

        public Criteria andAttachmentBetween(Long value1, Long value2) {
            addCriterion("ATTACHMENT between", value1, value2, "attachment");
            return (Criteria) this;
        }

        public Criteria andAttachmentNotBetween(Long value1, Long value2) {
            addCriterion("ATTACHMENT not between", value1, value2, "attachment");
            return (Criteria) this;
        }

        public Criteria andSourceIsNull() {
            addCriterion("SOURCE is null");
            return (Criteria) this;
        }

        public Criteria andSourceIsNotNull() {
            addCriterion("SOURCE is not null");
            return (Criteria) this;
        }

        public Criteria andSourceEqualTo(String value) {
            addCriterion("SOURCE =", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotEqualTo(String value) {
            addCriterion("SOURCE <>", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThan(String value) {
            addCriterion("SOURCE >", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThanOrEqualTo(String value) {
            addCriterion("SOURCE >=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThan(String value) {
            addCriterion("SOURCE <", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThanOrEqualTo(String value) {
            addCriterion("SOURCE <=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLike(String value) {
            addCriterion("SOURCE like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotLike(String value) {
            addCriterion("SOURCE not like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceIn(List<String> values) {
            addCriterion("SOURCE in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotIn(List<String> values) {
            addCriterion("SOURCE not in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceBetween(String value1, String value2) {
            addCriterion("SOURCE between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotBetween(String value1, String value2) {
            addCriterion("SOURCE not between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andHappenedTimeIsNull() {
            addCriterion("HAPPENED_TIME is null");
            return (Criteria) this;
        }

        public Criteria andHappenedTimeIsNotNull() {
            addCriterion("HAPPENED_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andHappenedTimeEqualTo(Date value) {
            addCriterion("HAPPENED_TIME =", value, "happenedTime");
            return (Criteria) this;
        }

        public Criteria andHappenedTimeNotEqualTo(Date value) {
            addCriterion("HAPPENED_TIME <>", value, "happenedTime");
            return (Criteria) this;
        }

        public Criteria andHappenedTimeGreaterThan(Date value) {
            addCriterion("HAPPENED_TIME >", value, "happenedTime");
            return (Criteria) this;
        }

        public Criteria andHappenedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("HAPPENED_TIME >=", value, "happenedTime");
            return (Criteria) this;
        }

        public Criteria andHappenedTimeLessThan(Date value) {
            addCriterion("HAPPENED_TIME <", value, "happenedTime");
            return (Criteria) this;
        }

        public Criteria andHappenedTimeLessThanOrEqualTo(Date value) {
            addCriterion("HAPPENED_TIME <=", value, "happenedTime");
            return (Criteria) this;
        }

        public Criteria andHappenedTimeIn(List<Date> values) {
            addCriterion("HAPPENED_TIME in", values, "happenedTime");
            return (Criteria) this;
        }

        public Criteria andHappenedTimeNotIn(List<Date> values) {
            addCriterion("HAPPENED_TIME not in", values, "happenedTime");
            return (Criteria) this;
        }

        public Criteria andHappenedTimeBetween(Date value1, Date value2) {
            addCriterion("HAPPENED_TIME between", value1, value2, "happenedTime");
            return (Criteria) this;
        }

        public Criteria andHappenedTimeNotBetween(Date value1, Date value2) {
            addCriterion("HAPPENED_TIME not between", value1, value2, "happenedTime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table LIANGFA_HUBEI20160504.CASE_INFORMATION
     *
     * @mbggenerated do_not_delete_during_merge Tue Jul 05 08:36:54 CST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table LIANGFA_HUBEI20160504.CASE_INFORMATION
     *
     * @mbggenerated Tue Jul 05 08:36:54 CST 2016
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
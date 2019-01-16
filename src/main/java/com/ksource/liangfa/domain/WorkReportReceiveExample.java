package com.ksource.liangfa.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class WorkReportReceiveExample {
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_XINXIANG20170424.WORK_REPORT_RECEIVE
     * @mbggenerated  Thu Jul 27 15:56:08 CST 2017
     */
    protected String orderByClause;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_XINXIANG20170424.WORK_REPORT_RECEIVE
     * @mbggenerated  Thu Jul 27 15:56:08 CST 2017
     */
    protected boolean distinct;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_XINXIANG20170424.WORK_REPORT_RECEIVE
     * @mbggenerated  Thu Jul 27 15:56:08 CST 2017
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170424.WORK_REPORT_RECEIVE
     * @mbggenerated  Thu Jul 27 15:56:08 CST 2017
     */
    public WorkReportReceiveExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170424.WORK_REPORT_RECEIVE
     * @mbggenerated  Thu Jul 27 15:56:08 CST 2017
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170424.WORK_REPORT_RECEIVE
     * @mbggenerated  Thu Jul 27 15:56:08 CST 2017
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170424.WORK_REPORT_RECEIVE
     * @mbggenerated  Thu Jul 27 15:56:08 CST 2017
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170424.WORK_REPORT_RECEIVE
     * @mbggenerated  Thu Jul 27 15:56:08 CST 2017
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170424.WORK_REPORT_RECEIVE
     * @mbggenerated  Thu Jul 27 15:56:08 CST 2017
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170424.WORK_REPORT_RECEIVE
     * @mbggenerated  Thu Jul 27 15:56:08 CST 2017
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170424.WORK_REPORT_RECEIVE
     * @mbggenerated  Thu Jul 27 15:56:08 CST 2017
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170424.WORK_REPORT_RECEIVE
     * @mbggenerated  Thu Jul 27 15:56:08 CST 2017
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170424.WORK_REPORT_RECEIVE
     * @mbggenerated  Thu Jul 27 15:56:08 CST 2017
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170424.WORK_REPORT_RECEIVE
     * @mbggenerated  Thu Jul 27 15:56:08 CST 2017
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator. This class corresponds to the database table LIANGFA_XINXIANG20170424.WORK_REPORT_RECEIVE
     * @mbggenerated  Thu Jul 27 15:56:08 CST 2017
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

        protected void addCriterion(String condition, Object value,
                String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property
                        + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1,
                Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property
                        + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value,
                String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property
                        + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()),
                    property);
        }

        protected void addCriterionForJDBCDate(String condition,
                List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property
                        + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1,
                Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property
                        + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()),
                    new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andReceiveIdIsNull() {
            addCriterion("RECEIVE_ID is null");
            return (Criteria) this;
        }

        public Criteria andReceiveIdIsNotNull() {
            addCriterion("RECEIVE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveIdEqualTo(Integer value) {
            addCriterion("RECEIVE_ID =", value, "receiveId");
            return (Criteria) this;
        }

        public Criteria andReceiveIdNotEqualTo(Integer value) {
            addCriterion("RECEIVE_ID <>", value, "receiveId");
            return (Criteria) this;
        }

        public Criteria andReceiveIdGreaterThan(Integer value) {
            addCriterion("RECEIVE_ID >", value, "receiveId");
            return (Criteria) this;
        }

        public Criteria andReceiveIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("RECEIVE_ID >=", value, "receiveId");
            return (Criteria) this;
        }

        public Criteria andReceiveIdLessThan(Integer value) {
            addCriterion("RECEIVE_ID <", value, "receiveId");
            return (Criteria) this;
        }

        public Criteria andReceiveIdLessThanOrEqualTo(Integer value) {
            addCriterion("RECEIVE_ID <=", value, "receiveId");
            return (Criteria) this;
        }

        public Criteria andReceiveIdIn(List<Integer> values) {
            addCriterion("RECEIVE_ID in", values, "receiveId");
            return (Criteria) this;
        }

        public Criteria andReceiveIdNotIn(List<Integer> values) {
            addCriterion("RECEIVE_ID not in", values, "receiveId");
            return (Criteria) this;
        }

        public Criteria andReceiveIdBetween(Integer value1, Integer value2) {
            addCriterion("RECEIVE_ID between", value1, value2, "receiveId");
            return (Criteria) this;
        }

        public Criteria andReceiveIdNotBetween(Integer value1, Integer value2) {
            addCriterion("RECEIVE_ID not between", value1, value2, "receiveId");
            return (Criteria) this;
        }

        public Criteria andReportIdIsNull() {
            addCriterion("REPORT_ID is null");
            return (Criteria) this;
        }

        public Criteria andReportIdIsNotNull() {
            addCriterion("REPORT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andReportIdEqualTo(Integer value) {
            addCriterion("REPORT_ID =", value, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdNotEqualTo(Integer value) {
            addCriterion("REPORT_ID <>", value, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdGreaterThan(Integer value) {
            addCriterion("REPORT_ID >", value, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("REPORT_ID >=", value, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdLessThan(Integer value) {
            addCriterion("REPORT_ID <", value, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdLessThanOrEqualTo(Integer value) {
            addCriterion("REPORT_ID <=", value, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdIn(List<Integer> values) {
            addCriterion("REPORT_ID in", values, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdNotIn(List<Integer> values) {
            addCriterion("REPORT_ID not in", values, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdBetween(Integer value1, Integer value2) {
            addCriterion("REPORT_ID between", value1, value2, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdNotBetween(Integer value1, Integer value2) {
            addCriterion("REPORT_ID not between", value1, value2, "reportId");
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

        public Criteria andSendOrgIsNull() {
            addCriterion("SEND_ORG is null");
            return (Criteria) this;
        }

        public Criteria andSendOrgIsNotNull() {
            addCriterion("SEND_ORG is not null");
            return (Criteria) this;
        }

        public Criteria andSendOrgEqualTo(String value) {
            addCriterion("SEND_ORG =", value, "sendOrg");
            return (Criteria) this;
        }

        public Criteria andSendOrgNotEqualTo(String value) {
            addCriterion("SEND_ORG <>", value, "sendOrg");
            return (Criteria) this;
        }

        public Criteria andSendOrgGreaterThan(String value) {
            addCriterion("SEND_ORG >", value, "sendOrg");
            return (Criteria) this;
        }

        public Criteria andSendOrgGreaterThanOrEqualTo(String value) {
            addCriterion("SEND_ORG >=", value, "sendOrg");
            return (Criteria) this;
        }

        public Criteria andSendOrgLessThan(String value) {
            addCriterion("SEND_ORG <", value, "sendOrg");
            return (Criteria) this;
        }

        public Criteria andSendOrgLessThanOrEqualTo(String value) {
            addCriterion("SEND_ORG <=", value, "sendOrg");
            return (Criteria) this;
        }

        public Criteria andSendOrgLike(String value) {
            addCriterion("SEND_ORG like", value, "sendOrg");
            return (Criteria) this;
        }

        public Criteria andSendOrgNotLike(String value) {
            addCriterion("SEND_ORG not like", value, "sendOrg");
            return (Criteria) this;
        }

        public Criteria andSendOrgIn(List<String> values) {
            addCriterion("SEND_ORG in", values, "sendOrg");
            return (Criteria) this;
        }

        public Criteria andSendOrgNotIn(List<String> values) {
            addCriterion("SEND_ORG not in", values, "sendOrg");
            return (Criteria) this;
        }

        public Criteria andSendOrgBetween(String value1, String value2) {
            addCriterion("SEND_ORG between", value1, value2, "sendOrg");
            return (Criteria) this;
        }

        public Criteria andSendOrgNotBetween(String value1, String value2) {
            addCriterion("SEND_ORG not between", value1, value2, "sendOrg");
            return (Criteria) this;
        }

        public Criteria andSendOrgNameIsNull() {
            addCriterion("SEND_ORG_NAME is null");
            return (Criteria) this;
        }

        public Criteria andSendOrgNameIsNotNull() {
            addCriterion("SEND_ORG_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andSendOrgNameEqualTo(String value) {
            addCriterion("SEND_ORG_NAME =", value, "sendOrgName");
            return (Criteria) this;
        }

        public Criteria andSendOrgNameNotEqualTo(String value) {
            addCriterion("SEND_ORG_NAME <>", value, "sendOrgName");
            return (Criteria) this;
        }

        public Criteria andSendOrgNameGreaterThan(String value) {
            addCriterion("SEND_ORG_NAME >", value, "sendOrgName");
            return (Criteria) this;
        }

        public Criteria andSendOrgNameGreaterThanOrEqualTo(String value) {
            addCriterion("SEND_ORG_NAME >=", value, "sendOrgName");
            return (Criteria) this;
        }

        public Criteria andSendOrgNameLessThan(String value) {
            addCriterion("SEND_ORG_NAME <", value, "sendOrgName");
            return (Criteria) this;
        }

        public Criteria andSendOrgNameLessThanOrEqualTo(String value) {
            addCriterion("SEND_ORG_NAME <=", value, "sendOrgName");
            return (Criteria) this;
        }

        public Criteria andSendOrgNameLike(String value) {
            addCriterion("SEND_ORG_NAME like", value, "sendOrgName");
            return (Criteria) this;
        }

        public Criteria andSendOrgNameNotLike(String value) {
            addCriterion("SEND_ORG_NAME not like", value, "sendOrgName");
            return (Criteria) this;
        }

        public Criteria andSendOrgNameIn(List<String> values) {
            addCriterion("SEND_ORG_NAME in", values, "sendOrgName");
            return (Criteria) this;
        }

        public Criteria andSendOrgNameNotIn(List<String> values) {
            addCriterion("SEND_ORG_NAME not in", values, "sendOrgName");
            return (Criteria) this;
        }

        public Criteria andSendOrgNameBetween(String value1, String value2) {
            addCriterion("SEND_ORG_NAME between", value1, value2, "sendOrgName");
            return (Criteria) this;
        }

        public Criteria andSendOrgNameNotBetween(String value1, String value2) {
            addCriterion("SEND_ORG_NAME not between", value1, value2,
                    "sendOrgName");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNull() {
            addCriterion("SEND_TIME is null");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNotNull() {
            addCriterion("SEND_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andSendTimeEqualTo(Date value) {
            addCriterionForJDBCDate("SEND_TIME =", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("SEND_TIME <>", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("SEND_TIME >", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("SEND_TIME >=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThan(Date value) {
            addCriterionForJDBCDate("SEND_TIME <", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("SEND_TIME <=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeIn(List<Date> values) {
            addCriterionForJDBCDate("SEND_TIME in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("SEND_TIME not in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("SEND_TIME between", value1, value2,
                    "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("SEND_TIME not between", value1, value2,
                    "sendTime");
            return (Criteria) this;
        }

        public Criteria andReceiveOrgIsNull() {
            addCriterion("RECEIVE_ORG is null");
            return (Criteria) this;
        }

        public Criteria andReceiveOrgIsNotNull() {
            addCriterion("RECEIVE_ORG is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveOrgEqualTo(String value) {
            addCriterion("RECEIVE_ORG =", value, "receiveOrg");
            return (Criteria) this;
        }

        public Criteria andReceiveOrgNotEqualTo(String value) {
            addCriterion("RECEIVE_ORG <>", value, "receiveOrg");
            return (Criteria) this;
        }

        public Criteria andReceiveOrgGreaterThan(String value) {
            addCriterion("RECEIVE_ORG >", value, "receiveOrg");
            return (Criteria) this;
        }

        public Criteria andReceiveOrgGreaterThanOrEqualTo(String value) {
            addCriterion("RECEIVE_ORG >=", value, "receiveOrg");
            return (Criteria) this;
        }

        public Criteria andReceiveOrgLessThan(String value) {
            addCriterion("RECEIVE_ORG <", value, "receiveOrg");
            return (Criteria) this;
        }

        public Criteria andReceiveOrgLessThanOrEqualTo(String value) {
            addCriterion("RECEIVE_ORG <=", value, "receiveOrg");
            return (Criteria) this;
        }

        public Criteria andReceiveOrgLike(String value) {
            addCriterion("RECEIVE_ORG like", value, "receiveOrg");
            return (Criteria) this;
        }

        public Criteria andReceiveOrgNotLike(String value) {
            addCriterion("RECEIVE_ORG not like", value, "receiveOrg");
            return (Criteria) this;
        }

        public Criteria andReceiveOrgIn(List<String> values) {
            addCriterion("RECEIVE_ORG in", values, "receiveOrg");
            return (Criteria) this;
        }

        public Criteria andReceiveOrgNotIn(List<String> values) {
            addCriterion("RECEIVE_ORG not in", values, "receiveOrg");
            return (Criteria) this;
        }

        public Criteria andReceiveOrgBetween(String value1, String value2) {
            addCriterion("RECEIVE_ORG between", value1, value2, "receiveOrg");
            return (Criteria) this;
        }

        public Criteria andReceiveOrgNotBetween(String value1, String value2) {
            addCriterion("RECEIVE_ORG not between", value1, value2,
                    "receiveOrg");
            return (Criteria) this;
        }

        public Criteria andReadStatusIsNull() {
            addCriterion("READ_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andReadStatusIsNotNull() {
            addCriterion("READ_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andReadStatusEqualTo(Integer value) {
            addCriterion("READ_STATUS =", value, "readStatus");
            return (Criteria) this;
        }

        public Criteria andReadStatusNotEqualTo(Integer value) {
            addCriterion("READ_STATUS <>", value, "readStatus");
            return (Criteria) this;
        }

        public Criteria andReadStatusGreaterThan(Integer value) {
            addCriterion("READ_STATUS >", value, "readStatus");
            return (Criteria) this;
        }

        public Criteria andReadStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("READ_STATUS >=", value, "readStatus");
            return (Criteria) this;
        }

        public Criteria andReadStatusLessThan(Integer value) {
            addCriterion("READ_STATUS <", value, "readStatus");
            return (Criteria) this;
        }

        public Criteria andReadStatusLessThanOrEqualTo(Integer value) {
            addCriterion("READ_STATUS <=", value, "readStatus");
            return (Criteria) this;
        }

        public Criteria andReadStatusIn(List<Integer> values) {
            addCriterion("READ_STATUS in", values, "readStatus");
            return (Criteria) this;
        }

        public Criteria andReadStatusNotIn(List<Integer> values) {
            addCriterion("READ_STATUS not in", values, "readStatus");
            return (Criteria) this;
        }

        public Criteria andReadStatusBetween(Integer value1, Integer value2) {
            addCriterion("READ_STATUS between", value1, value2, "readStatus");
            return (Criteria) this;
        }

        public Criteria andReadStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("READ_STATUS not between", value1, value2,
                    "readStatus");
            return (Criteria) this;
        }

        public Criteria andReadTimeIsNull() {
            addCriterion("READ_TIME is null");
            return (Criteria) this;
        }

        public Criteria andReadTimeIsNotNull() {
            addCriterion("READ_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andReadTimeEqualTo(Date value) {
            addCriterion("READ_TIME =", value, "readTime");
            return (Criteria) this;
        }

        public Criteria andReadTimeNotEqualTo(Date value) {
            addCriterion("READ_TIME <>", value, "readTime");
            return (Criteria) this;
        }

        public Criteria andReadTimeGreaterThan(Date value) {
            addCriterion("READ_TIME >", value, "readTime");
            return (Criteria) this;
        }

        public Criteria andReadTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("READ_TIME >=", value, "readTime");
            return (Criteria) this;
        }

        public Criteria andReadTimeLessThan(Date value) {
            addCriterion("READ_TIME <", value, "readTime");
            return (Criteria) this;
        }

        public Criteria andReadTimeLessThanOrEqualTo(Date value) {
            addCriterion("READ_TIME <=", value, "readTime");
            return (Criteria) this;
        }

        public Criteria andReadTimeIn(List<Date> values) {
            addCriterion("READ_TIME in", values, "readTime");
            return (Criteria) this;
        }

        public Criteria andReadTimeNotIn(List<Date> values) {
            addCriterion("READ_TIME not in", values, "readTime");
            return (Criteria) this;
        }

        public Criteria andReadTimeBetween(Date value1, Date value2) {
            addCriterion("READ_TIME between", value1, value2, "readTime");
            return (Criteria) this;
        }

        public Criteria andReadTimeNotBetween(Date value1, Date value2) {
            addCriterion("READ_TIME not between", value1, value2, "readTime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator. This class corresponds to the database table LIANGFA_XINXIANG20170424.WORK_REPORT_RECEIVE
     * @mbggenerated  Thu Jul 27 15:56:08 CST 2017
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

        protected Criterion(String condition, Object value, Object secondValue,
                String typeHandler) {
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

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table LIANGFA_HUBEI20160322.WORK_REPORT_RECEIVE
     *
     * @mbggenerated do_not_delete_during_merge Tue Mar 29 11:18:22 CST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
package com.ksource.liangfa.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CaseTodoExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    public CaseTodoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
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
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
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

        public Criteria andTodoIdIsNull() {
            addCriterion("TODO_ID is null");
            return (Criteria) this;
        }

        public Criteria andTodoIdIsNotNull() {
            addCriterion("TODO_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTodoIdEqualTo(Integer value) {
            addCriterion("TODO_ID =", value, "todoId");
            return (Criteria) this;
        }

        public Criteria andTodoIdNotEqualTo(Integer value) {
            addCriterion("TODO_ID <>", value, "todoId");
            return (Criteria) this;
        }

        public Criteria andTodoIdGreaterThan(Integer value) {
            addCriterion("TODO_ID >", value, "todoId");
            return (Criteria) this;
        }

        public Criteria andTodoIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("TODO_ID >=", value, "todoId");
            return (Criteria) this;
        }

        public Criteria andTodoIdLessThan(Integer value) {
            addCriterion("TODO_ID <", value, "todoId");
            return (Criteria) this;
        }

        public Criteria andTodoIdLessThanOrEqualTo(Integer value) {
            addCriterion("TODO_ID <=", value, "todoId");
            return (Criteria) this;
        }

        public Criteria andTodoIdIn(List<Integer> values) {
            addCriterion("TODO_ID in", values, "todoId");
            return (Criteria) this;
        }

        public Criteria andTodoIdNotIn(List<Integer> values) {
            addCriterion("TODO_ID not in", values, "todoId");
            return (Criteria) this;
        }

        public Criteria andTodoIdBetween(Integer value1, Integer value2) {
            addCriterion("TODO_ID between", value1, value2, "todoId");
            return (Criteria) this;
        }

        public Criteria andTodoIdNotBetween(Integer value1, Integer value2) {
            addCriterion("TODO_ID not between", value1, value2, "todoId");
            return (Criteria) this;
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

        public Criteria andCreateUserIsNull() {
            addCriterion("CREATE_USER is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("CREATE_USER is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(String value) {
            addCriterion("CREATE_USER =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(String value) {
            addCriterion("CREATE_USER <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(String value) {
            addCriterion("CREATE_USER >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_USER >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(String value) {
            addCriterion("CREATE_USER <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(String value) {
            addCriterion("CREATE_USER <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLike(String value) {
            addCriterion("CREATE_USER like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotLike(String value) {
            addCriterion("CREATE_USER not like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<String> values) {
            addCriterion("CREATE_USER in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<String> values) {
            addCriterion("CREATE_USER not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(String value1, String value2) {
            addCriterion("CREATE_USER between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(String value1, String value2) {
            addCriterion("CREATE_USER not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("CREATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CREATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("CREATE_TIME =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("CREATE_TIME <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("CREATE_TIME >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("CREATE_TIME <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("CREATE_TIME in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("CREATE_TIME not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateOrgIsNull() {
            addCriterion("CREATE_ORG is null");
            return (Criteria) this;
        }

        public Criteria andCreateOrgIsNotNull() {
            addCriterion("CREATE_ORG is not null");
            return (Criteria) this;
        }

        public Criteria andCreateOrgEqualTo(Integer value) {
            addCriterion("CREATE_ORG =", value, "createOrg");
            return (Criteria) this;
        }

        public Criteria andCreateOrgNotEqualTo(Integer value) {
            addCriterion("CREATE_ORG <>", value, "createOrg");
            return (Criteria) this;
        }

        public Criteria andCreateOrgGreaterThan(Integer value) {
            addCriterion("CREATE_ORG >", value, "createOrg");
            return (Criteria) this;
        }

        public Criteria andCreateOrgGreaterThanOrEqualTo(Integer value) {
            addCriterion("CREATE_ORG >=", value, "createOrg");
            return (Criteria) this;
        }

        public Criteria andCreateOrgLessThan(Integer value) {
            addCriterion("CREATE_ORG <", value, "createOrg");
            return (Criteria) this;
        }

        public Criteria andCreateOrgLessThanOrEqualTo(Integer value) {
            addCriterion("CREATE_ORG <=", value, "createOrg");
            return (Criteria) this;
        }

        public Criteria andCreateOrgIn(List<Integer> values) {
            addCriterion("CREATE_ORG in", values, "createOrg");
            return (Criteria) this;
        }

        public Criteria andCreateOrgNotIn(List<Integer> values) {
            addCriterion("CREATE_ORG not in", values, "createOrg");
            return (Criteria) this;
        }

        public Criteria andCreateOrgBetween(Integer value1, Integer value2) {
            addCriterion("CREATE_ORG between", value1, value2, "createOrg");
            return (Criteria) this;
        }

        public Criteria andCreateOrgNotBetween(Integer value1, Integer value2) {
            addCriterion("CREATE_ORG not between", value1, value2, "createOrg");
            return (Criteria) this;
        }

        public Criteria andProcInstIdIsNull() {
            addCriterion("PROC_INST_ID is null");
            return (Criteria) this;
        }

        public Criteria andProcInstIdIsNotNull() {
            addCriterion("PROC_INST_ID is not null");
            return (Criteria) this;
        }

        public Criteria andProcInstIdEqualTo(String value) {
            addCriterion("PROC_INST_ID =", value, "procInstId");
            return (Criteria) this;
        }

        public Criteria andProcInstIdNotEqualTo(String value) {
            addCriterion("PROC_INST_ID <>", value, "procInstId");
            return (Criteria) this;
        }

        public Criteria andProcInstIdGreaterThan(String value) {
            addCriterion("PROC_INST_ID >", value, "procInstId");
            return (Criteria) this;
        }

        public Criteria andProcInstIdGreaterThanOrEqualTo(String value) {
            addCriterion("PROC_INST_ID >=", value, "procInstId");
            return (Criteria) this;
        }

        public Criteria andProcInstIdLessThan(String value) {
            addCriterion("PROC_INST_ID <", value, "procInstId");
            return (Criteria) this;
        }

        public Criteria andProcInstIdLessThanOrEqualTo(String value) {
            addCriterion("PROC_INST_ID <=", value, "procInstId");
            return (Criteria) this;
        }

        public Criteria andProcInstIdLike(String value) {
            addCriterion("PROC_INST_ID like", value, "procInstId");
            return (Criteria) this;
        }

        public Criteria andProcInstIdNotLike(String value) {
            addCriterion("PROC_INST_ID not like", value, "procInstId");
            return (Criteria) this;
        }

        public Criteria andProcInstIdIn(List<String> values) {
            addCriterion("PROC_INST_ID in", values, "procInstId");
            return (Criteria) this;
        }

        public Criteria andProcInstIdNotIn(List<String> values) {
            addCriterion("PROC_INST_ID not in", values, "procInstId");
            return (Criteria) this;
        }

        public Criteria andProcInstIdBetween(String value1, String value2) {
            addCriterion("PROC_INST_ID between", value1, value2, "procInstId");
            return (Criteria) this;
        }

        public Criteria andProcInstIdNotBetween(String value1, String value2) {
            addCriterion("PROC_INST_ID not between", value1, value2, "procInstId");
            return (Criteria) this;
        }

        public Criteria andProcDefIdIsNull() {
            addCriterion("PROC_DEF_ID is null");
            return (Criteria) this;
        }

        public Criteria andProcDefIdIsNotNull() {
            addCriterion("PROC_DEF_ID is not null");
            return (Criteria) this;
        }

        public Criteria andProcDefIdEqualTo(String value) {
            addCriterion("PROC_DEF_ID =", value, "procDefId");
            return (Criteria) this;
        }

        public Criteria andProcDefIdNotEqualTo(String value) {
            addCriterion("PROC_DEF_ID <>", value, "procDefId");
            return (Criteria) this;
        }

        public Criteria andProcDefIdGreaterThan(String value) {
            addCriterion("PROC_DEF_ID >", value, "procDefId");
            return (Criteria) this;
        }

        public Criteria andProcDefIdGreaterThanOrEqualTo(String value) {
            addCriterion("PROC_DEF_ID >=", value, "procDefId");
            return (Criteria) this;
        }

        public Criteria andProcDefIdLessThan(String value) {
            addCriterion("PROC_DEF_ID <", value, "procDefId");
            return (Criteria) this;
        }

        public Criteria andProcDefIdLessThanOrEqualTo(String value) {
            addCriterion("PROC_DEF_ID <=", value, "procDefId");
            return (Criteria) this;
        }

        public Criteria andProcDefIdLike(String value) {
            addCriterion("PROC_DEF_ID like", value, "procDefId");
            return (Criteria) this;
        }

        public Criteria andProcDefIdNotLike(String value) {
            addCriterion("PROC_DEF_ID not like", value, "procDefId");
            return (Criteria) this;
        }

        public Criteria andProcDefIdIn(List<String> values) {
            addCriterion("PROC_DEF_ID in", values, "procDefId");
            return (Criteria) this;
        }

        public Criteria andProcDefIdNotIn(List<String> values) {
            addCriterion("PROC_DEF_ID not in", values, "procDefId");
            return (Criteria) this;
        }

        public Criteria andProcDefIdBetween(String value1, String value2) {
            addCriterion("PROC_DEF_ID between", value1, value2, "procDefId");
            return (Criteria) this;
        }

        public Criteria andProcDefIdNotBetween(String value1, String value2) {
            addCriterion("PROC_DEF_ID not between", value1, value2, "procDefId");
            return (Criteria) this;
        }

        public Criteria andAssignUserIsNull() {
            addCriterion("ASSIGN_USER is null");
            return (Criteria) this;
        }

        public Criteria andAssignUserIsNotNull() {
            addCriterion("ASSIGN_USER is not null");
            return (Criteria) this;
        }

        public Criteria andAssignUserEqualTo(String value) {
            addCriterion("ASSIGN_USER =", value, "assignUser");
            return (Criteria) this;
        }

        public Criteria andAssignUserNotEqualTo(String value) {
            addCriterion("ASSIGN_USER <>", value, "assignUser");
            return (Criteria) this;
        }

        public Criteria andAssignUserGreaterThan(String value) {
            addCriterion("ASSIGN_USER >", value, "assignUser");
            return (Criteria) this;
        }

        public Criteria andAssignUserGreaterThanOrEqualTo(String value) {
            addCriterion("ASSIGN_USER >=", value, "assignUser");
            return (Criteria) this;
        }

        public Criteria andAssignUserLessThan(String value) {
            addCriterion("ASSIGN_USER <", value, "assignUser");
            return (Criteria) this;
        }

        public Criteria andAssignUserLessThanOrEqualTo(String value) {
            addCriterion("ASSIGN_USER <=", value, "assignUser");
            return (Criteria) this;
        }

        public Criteria andAssignUserLike(String value) {
            addCriterion("ASSIGN_USER like", value, "assignUser");
            return (Criteria) this;
        }

        public Criteria andAssignUserNotLike(String value) {
            addCriterion("ASSIGN_USER not like", value, "assignUser");
            return (Criteria) this;
        }

        public Criteria andAssignUserIn(List<String> values) {
            addCriterion("ASSIGN_USER in", values, "assignUser");
            return (Criteria) this;
        }

        public Criteria andAssignUserNotIn(List<String> values) {
            addCriterion("ASSIGN_USER not in", values, "assignUser");
            return (Criteria) this;
        }

        public Criteria andAssignUserBetween(String value1, String value2) {
            addCriterion("ASSIGN_USER between", value1, value2, "assignUser");
            return (Criteria) this;
        }

        public Criteria andAssignUserNotBetween(String value1, String value2) {
            addCriterion("ASSIGN_USER not between", value1, value2, "assignUser");
            return (Criteria) this;
        }

        public Criteria andAssignOrgIsNull() {
            addCriterion("ASSIGN_ORG is null");
            return (Criteria) this;
        }

        public Criteria andAssignOrgIsNotNull() {
            addCriterion("ASSIGN_ORG is not null");
            return (Criteria) this;
        }

        public Criteria andAssignOrgEqualTo(Integer value) {
            addCriterion("ASSIGN_ORG =", value, "assignOrg");
            return (Criteria) this;
        }

        public Criteria andAssignOrgNotEqualTo(Integer value) {
            addCriterion("ASSIGN_ORG <>", value, "assignOrg");
            return (Criteria) this;
        }

        public Criteria andAssignOrgGreaterThan(Integer value) {
            addCriterion("ASSIGN_ORG >", value, "assignOrg");
            return (Criteria) this;
        }

        public Criteria andAssignOrgGreaterThanOrEqualTo(Integer value) {
            addCriterion("ASSIGN_ORG >=", value, "assignOrg");
            return (Criteria) this;
        }

        public Criteria andAssignOrgLessThan(Integer value) {
            addCriterion("ASSIGN_ORG <", value, "assignOrg");
            return (Criteria) this;
        }

        public Criteria andAssignOrgLessThanOrEqualTo(Integer value) {
            addCriterion("ASSIGN_ORG <=", value, "assignOrg");
            return (Criteria) this;
        }

        public Criteria andAssignOrgIn(List<Integer> values) {
            addCriterion("ASSIGN_ORG in", values, "assignOrg");
            return (Criteria) this;
        }

        public Criteria andAssignOrgNotIn(List<Integer> values) {
            addCriterion("ASSIGN_ORG not in", values, "assignOrg");
            return (Criteria) this;
        }

        public Criteria andAssignOrgBetween(Integer value1, Integer value2) {
            addCriterion("ASSIGN_ORG between", value1, value2, "assignOrg");
            return (Criteria) this;
        }

        public Criteria andAssignOrgNotBetween(Integer value1, Integer value2) {
            addCriterion("ASSIGN_ORG not between", value1, value2, "assignOrg");
            return (Criteria) this;
        }

        public Criteria andTaskActionIdIsNull() {
            addCriterion("TASK_ACTION_ID is null");
            return (Criteria) this;
        }

        public Criteria andTaskActionIdIsNotNull() {
            addCriterion("TASK_ACTION_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTaskActionIdEqualTo(Integer value) {
            addCriterion("TASK_ACTION_ID =", value, "taskActionId");
            return (Criteria) this;
        }

        public Criteria andTaskActionIdNotEqualTo(Integer value) {
            addCriterion("TASK_ACTION_ID <>", value, "taskActionId");
            return (Criteria) this;
        }

        public Criteria andTaskActionIdGreaterThan(Integer value) {
            addCriterion("TASK_ACTION_ID >", value, "taskActionId");
            return (Criteria) this;
        }

        public Criteria andTaskActionIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("TASK_ACTION_ID >=", value, "taskActionId");
            return (Criteria) this;
        }

        public Criteria andTaskActionIdLessThan(Integer value) {
            addCriterion("TASK_ACTION_ID <", value, "taskActionId");
            return (Criteria) this;
        }

        public Criteria andTaskActionIdLessThanOrEqualTo(Integer value) {
            addCriterion("TASK_ACTION_ID <=", value, "taskActionId");
            return (Criteria) this;
        }

        public Criteria andTaskActionIdIn(List<Integer> values) {
            addCriterion("TASK_ACTION_ID in", values, "taskActionId");
            return (Criteria) this;
        }

        public Criteria andTaskActionIdNotIn(List<Integer> values) {
            addCriterion("TASK_ACTION_ID not in", values, "taskActionId");
            return (Criteria) this;
        }

        public Criteria andTaskActionIdBetween(Integer value1, Integer value2) {
            addCriterion("TASK_ACTION_ID between", value1, value2, "taskActionId");
            return (Criteria) this;
        }

        public Criteria andTaskActionIdNotBetween(Integer value1, Integer value2) {
            addCriterion("TASK_ACTION_ID not between", value1, value2, "taskActionId");
            return (Criteria) this;
        }

        public Criteria andTaskActionNameIsNull() {
            addCriterion("TASK_ACTION_NAME is null");
            return (Criteria) this;
        }

        public Criteria andTaskActionNameIsNotNull() {
            addCriterion("TASK_ACTION_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andTaskActionNameEqualTo(String value) {
            addCriterion("TASK_ACTION_NAME =", value, "taskActionName");
            return (Criteria) this;
        }

        public Criteria andTaskActionNameNotEqualTo(String value) {
            addCriterion("TASK_ACTION_NAME <>", value, "taskActionName");
            return (Criteria) this;
        }

        public Criteria andTaskActionNameGreaterThan(String value) {
            addCriterion("TASK_ACTION_NAME >", value, "taskActionName");
            return (Criteria) this;
        }

        public Criteria andTaskActionNameGreaterThanOrEqualTo(String value) {
            addCriterion("TASK_ACTION_NAME >=", value, "taskActionName");
            return (Criteria) this;
        }

        public Criteria andTaskActionNameLessThan(String value) {
            addCriterion("TASK_ACTION_NAME <", value, "taskActionName");
            return (Criteria) this;
        }

        public Criteria andTaskActionNameLessThanOrEqualTo(String value) {
            addCriterion("TASK_ACTION_NAME <=", value, "taskActionName");
            return (Criteria) this;
        }

        public Criteria andTaskActionNameLike(String value) {
            addCriterion("TASK_ACTION_NAME like", value, "taskActionName");
            return (Criteria) this;
        }

        public Criteria andTaskActionNameNotLike(String value) {
            addCriterion("TASK_ACTION_NAME not like", value, "taskActionName");
            return (Criteria) this;
        }

        public Criteria andTaskActionNameIn(List<String> values) {
            addCriterion("TASK_ACTION_NAME in", values, "taskActionName");
            return (Criteria) this;
        }

        public Criteria andTaskActionNameNotIn(List<String> values) {
            addCriterion("TASK_ACTION_NAME not in", values, "taskActionName");
            return (Criteria) this;
        }

        public Criteria andTaskActionNameBetween(String value1, String value2) {
            addCriterion("TASK_ACTION_NAME between", value1, value2, "taskActionName");
            return (Criteria) this;
        }

        public Criteria andTaskActionNameNotBetween(String value1, String value2) {
            addCriterion("TASK_ACTION_NAME not between", value1, value2, "taskActionName");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated do_not_delete_during_merge Wed Mar 15 14:50:39 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table LIANGFA_XINXIANG20170222.CASE_TODO
     *
     * @mbggenerated Wed Mar 15 14:50:39 CST 2017
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
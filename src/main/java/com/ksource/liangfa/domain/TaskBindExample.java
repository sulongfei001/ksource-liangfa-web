package com.ksource.liangfa.domain;

import java.util.ArrayList;
import java.util.List;

public class TaskBindExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	public TaskBindExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
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

		public Criteria andTaskDefIdIsNull() {
			addCriterion("TASK_DEF_ID is null");
			return (Criteria) this;
		}

		public Criteria andTaskDefIdIsNotNull() {
			addCriterion("TASK_DEF_ID is not null");
			return (Criteria) this;
		}

		public Criteria andTaskDefIdEqualTo(String value) {
			addCriterion("TASK_DEF_ID =", value, "taskDefId");
			return (Criteria) this;
		}

		public Criteria andTaskDefIdNotEqualTo(String value) {
			addCriterion("TASK_DEF_ID <>", value, "taskDefId");
			return (Criteria) this;
		}

		public Criteria andTaskDefIdGreaterThan(String value) {
			addCriterion("TASK_DEF_ID >", value, "taskDefId");
			return (Criteria) this;
		}

		public Criteria andTaskDefIdGreaterThanOrEqualTo(String value) {
			addCriterion("TASK_DEF_ID >=", value, "taskDefId");
			return (Criteria) this;
		}

		public Criteria andTaskDefIdLessThan(String value) {
			addCriterion("TASK_DEF_ID <", value, "taskDefId");
			return (Criteria) this;
		}

		public Criteria andTaskDefIdLessThanOrEqualTo(String value) {
			addCriterion("TASK_DEF_ID <=", value, "taskDefId");
			return (Criteria) this;
		}

		public Criteria andTaskDefIdLike(String value) {
			addCriterion("TASK_DEF_ID like", value, "taskDefId");
			return (Criteria) this;
		}

		public Criteria andTaskDefIdNotLike(String value) {
			addCriterion("TASK_DEF_ID not like", value, "taskDefId");
			return (Criteria) this;
		}

		public Criteria andTaskDefIdIn(List<String> values) {
			addCriterion("TASK_DEF_ID in", values, "taskDefId");
			return (Criteria) this;
		}

		public Criteria andTaskDefIdNotIn(List<String> values) {
			addCriterion("TASK_DEF_ID not in", values, "taskDefId");
			return (Criteria) this;
		}

		public Criteria andTaskDefIdBetween(String value1, String value2) {
			addCriterion("TASK_DEF_ID between", value1, value2, "taskDefId");
			return (Criteria) this;
		}

		public Criteria andTaskDefIdNotBetween(String value1, String value2) {
			addCriterion("TASK_DEF_ID not between", value1, value2, "taskDefId");
			return (Criteria) this;
		}

		public Criteria andAssignTargetIsNull() {
			addCriterion("ASSIGN_TARGET is null");
			return (Criteria) this;
		}

		public Criteria andAssignTargetIsNotNull() {
			addCriterion("ASSIGN_TARGET is not null");
			return (Criteria) this;
		}

		public Criteria andAssignTargetEqualTo(String value) {
			addCriterion("ASSIGN_TARGET =", value, "assignTarget");
			return (Criteria) this;
		}

		public Criteria andAssignTargetNotEqualTo(String value) {
			addCriterion("ASSIGN_TARGET <>", value, "assignTarget");
			return (Criteria) this;
		}

		public Criteria andAssignTargetGreaterThan(String value) {
			addCriterion("ASSIGN_TARGET >", value, "assignTarget");
			return (Criteria) this;
		}

		public Criteria andAssignTargetGreaterThanOrEqualTo(String value) {
			addCriterion("ASSIGN_TARGET >=", value, "assignTarget");
			return (Criteria) this;
		}

		public Criteria andAssignTargetLessThan(String value) {
			addCriterion("ASSIGN_TARGET <", value, "assignTarget");
			return (Criteria) this;
		}

		public Criteria andAssignTargetLessThanOrEqualTo(String value) {
			addCriterion("ASSIGN_TARGET <=", value, "assignTarget");
			return (Criteria) this;
		}

		public Criteria andAssignTargetLike(String value) {
			addCriterion("ASSIGN_TARGET like", value, "assignTarget");
			return (Criteria) this;
		}

		public Criteria andAssignTargetNotLike(String value) {
			addCriterion("ASSIGN_TARGET not like", value, "assignTarget");
			return (Criteria) this;
		}

		public Criteria andAssignTargetIn(List<String> values) {
			addCriterion("ASSIGN_TARGET in", values, "assignTarget");
			return (Criteria) this;
		}

		public Criteria andAssignTargetNotIn(List<String> values) {
			addCriterion("ASSIGN_TARGET not in", values, "assignTarget");
			return (Criteria) this;
		}

		public Criteria andAssignTargetBetween(String value1, String value2) {
			addCriterion("ASSIGN_TARGET between", value1, value2,
					"assignTarget");
			return (Criteria) this;
		}

		public Criteria andAssignTargetNotBetween(String value1, String value2) {
			addCriterion("ASSIGN_TARGET not between", value1, value2,
					"assignTarget");
			return (Criteria) this;
		}

		public Criteria andTaskTypeIsNull() {
			addCriterion("TASK_TYPE is null");
			return (Criteria) this;
		}

		public Criteria andTaskTypeIsNotNull() {
			addCriterion("TASK_TYPE is not null");
			return (Criteria) this;
		}

		public Criteria andTaskTypeEqualTo(Integer value) {
			addCriterion("TASK_TYPE =", value, "taskType");
			return (Criteria) this;
		}

		public Criteria andTaskTypeNotEqualTo(Integer value) {
			addCriterion("TASK_TYPE <>", value, "taskType");
			return (Criteria) this;
		}

		public Criteria andTaskTypeGreaterThan(Integer value) {
			addCriterion("TASK_TYPE >", value, "taskType");
			return (Criteria) this;
		}

		public Criteria andTaskTypeGreaterThanOrEqualTo(Integer value) {
			addCriterion("TASK_TYPE >=", value, "taskType");
			return (Criteria) this;
		}

		public Criteria andTaskTypeLessThan(Integer value) {
			addCriterion("TASK_TYPE <", value, "taskType");
			return (Criteria) this;
		}

		public Criteria andTaskTypeLessThanOrEqualTo(Integer value) {
			addCriterion("TASK_TYPE <=", value, "taskType");
			return (Criteria) this;
		}

		public Criteria andTaskTypeIn(List<Integer> values) {
			addCriterion("TASK_TYPE in", values, "taskType");
			return (Criteria) this;
		}

		public Criteria andTaskTypeNotIn(List<Integer> values) {
			addCriterion("TASK_TYPE not in", values, "taskType");
			return (Criteria) this;
		}

		public Criteria andTaskTypeBetween(Integer value1, Integer value2) {
			addCriterion("TASK_TYPE between", value1, value2, "taskType");
			return (Criteria) this;
		}

		public Criteria andTaskTypeNotBetween(Integer value1, Integer value2) {
			addCriterion("TASK_TYPE not between", value1, value2, "taskType");
			return (Criteria) this;
		}

		public Criteria andCaseIndIsNull() {
			addCriterion("CASE_IND is null");
			return (Criteria) this;
		}

		public Criteria andCaseIndIsNotNull() {
			addCriterion("CASE_IND is not null");
			return (Criteria) this;
		}

		public Criteria andCaseIndEqualTo(Integer value) {
			addCriterion("CASE_IND =", value, "caseInd");
			return (Criteria) this;
		}

		public Criteria andCaseIndNotEqualTo(Integer value) {
			addCriterion("CASE_IND <>", value, "caseInd");
			return (Criteria) this;
		}

		public Criteria andCaseIndGreaterThan(Integer value) {
			addCriterion("CASE_IND >", value, "caseInd");
			return (Criteria) this;
		}

		public Criteria andCaseIndGreaterThanOrEqualTo(Integer value) {
			addCriterion("CASE_IND >=", value, "caseInd");
			return (Criteria) this;
		}

		public Criteria andCaseIndLessThan(Integer value) {
			addCriterion("CASE_IND <", value, "caseInd");
			return (Criteria) this;
		}

		public Criteria andCaseIndLessThanOrEqualTo(Integer value) {
			addCriterion("CASE_IND <=", value, "caseInd");
			return (Criteria) this;
		}

		public Criteria andCaseIndIn(List<Integer> values) {
			addCriterion("CASE_IND in", values, "caseInd");
			return (Criteria) this;
		}

		public Criteria andCaseIndNotIn(List<Integer> values) {
			addCriterion("CASE_IND not in", values, "caseInd");
			return (Criteria) this;
		}

		public Criteria andCaseIndBetween(Integer value1, Integer value2) {
			addCriterion("CASE_IND between", value1, value2, "caseInd");
			return (Criteria) this;
		}

		public Criteria andCaseIndNotBetween(Integer value1, Integer value2) {
			addCriterion("CASE_IND not between", value1, value2, "caseInd");
			return (Criteria) this;
		}

		public Criteria andCaseIndValIsNull() {
			addCriterion("CASE_IND_VAL is null");
			return (Criteria) this;
		}

		public Criteria andCaseIndValIsNotNull() {
			addCriterion("CASE_IND_VAL is not null");
			return (Criteria) this;
		}

		public Criteria andCaseIndValEqualTo(Integer value) {
			addCriterion("CASE_IND_VAL =", value, "caseIndVal");
			return (Criteria) this;
		}

		public Criteria andCaseIndValNotEqualTo(Integer value) {
			addCriterion("CASE_IND_VAL <>", value, "caseIndVal");
			return (Criteria) this;
		}

		public Criteria andCaseIndValGreaterThan(Integer value) {
			addCriterion("CASE_IND_VAL >", value, "caseIndVal");
			return (Criteria) this;
		}

		public Criteria andCaseIndValGreaterThanOrEqualTo(Integer value) {
			addCriterion("CASE_IND_VAL >=", value, "caseIndVal");
			return (Criteria) this;
		}

		public Criteria andCaseIndValLessThan(Integer value) {
			addCriterion("CASE_IND_VAL <", value, "caseIndVal");
			return (Criteria) this;
		}

		public Criteria andCaseIndValLessThanOrEqualTo(Integer value) {
			addCriterion("CASE_IND_VAL <=", value, "caseIndVal");
			return (Criteria) this;
		}

		public Criteria andCaseIndValIn(List<Integer> values) {
			addCriterion("CASE_IND_VAL in", values, "caseIndVal");
			return (Criteria) this;
		}

		public Criteria andCaseIndValNotIn(List<Integer> values) {
			addCriterion("CASE_IND_VAL not in", values, "caseIndVal");
			return (Criteria) this;
		}

		public Criteria andCaseIndValBetween(Integer value1, Integer value2) {
			addCriterion("CASE_IND_VAL between", value1, value2, "caseIndVal");
			return (Criteria) this;
		}

		public Criteria andCaseIndValNotBetween(Integer value1, Integer value2) {
			addCriterion("CASE_IND_VAL not between", value1, value2,
					"caseIndVal");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TASK_BIND
	 * @mbggenerated  Tue Jul 26 14:55:26 CST 2011
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
     * This class corresponds to the database table TASK_BIND
     *
     * @mbggenerated do_not_delete_during_merge Fri May 20 17:02:08 CST 2011
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
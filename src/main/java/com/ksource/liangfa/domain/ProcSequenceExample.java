package com.ksource.liangfa.domain;

import java.util.ArrayList;
import java.util.List;

public class ProcSequenceExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_XINXIANG20170421.PROC_SEQUENCE
	 * @mbggenerated  Sat Apr 22 16:00:37 CST 2017
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_XINXIANG20170421.PROC_SEQUENCE
	 * @mbggenerated  Sat Apr 22 16:00:37 CST 2017
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_XINXIANG20170421.PROC_SEQUENCE
	 * @mbggenerated  Sat Apr 22 16:00:37 CST 2017
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170421.PROC_SEQUENCE
	 * @mbggenerated  Sat Apr 22 16:00:37 CST 2017
	 */
	public ProcSequenceExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170421.PROC_SEQUENCE
	 * @mbggenerated  Sat Apr 22 16:00:37 CST 2017
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170421.PROC_SEQUENCE
	 * @mbggenerated  Sat Apr 22 16:00:37 CST 2017
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170421.PROC_SEQUENCE
	 * @mbggenerated  Sat Apr 22 16:00:37 CST 2017
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170421.PROC_SEQUENCE
	 * @mbggenerated  Sat Apr 22 16:00:37 CST 2017
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170421.PROC_SEQUENCE
	 * @mbggenerated  Sat Apr 22 16:00:37 CST 2017
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170421.PROC_SEQUENCE
	 * @mbggenerated  Sat Apr 22 16:00:37 CST 2017
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170421.PROC_SEQUENCE
	 * @mbggenerated  Sat Apr 22 16:00:37 CST 2017
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170421.PROC_SEQUENCE
	 * @mbggenerated  Sat Apr 22 16:00:37 CST 2017
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170421.PROC_SEQUENCE
	 * @mbggenerated  Sat Apr 22 16:00:37 CST 2017
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170421.PROC_SEQUENCE
	 * @mbggenerated  Sat Apr 22 16:00:37 CST 2017
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table LIANGFA_XINXIANG20170421.PROC_SEQUENCE
	 * @mbggenerated  Sat Apr 22 16:00:37 CST 2017
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

		public Criteria andFlowIdIsNull() {
			addCriterion("FLOW_ID is null");
			return (Criteria) this;
		}

		public Criteria andFlowIdIsNotNull() {
			addCriterion("FLOW_ID is not null");
			return (Criteria) this;
		}

		public Criteria andFlowIdEqualTo(String value) {
			addCriterion("FLOW_ID =", value, "flowId");
			return (Criteria) this;
		}

		public Criteria andFlowIdNotEqualTo(String value) {
			addCriterion("FLOW_ID <>", value, "flowId");
			return (Criteria) this;
		}

		public Criteria andFlowIdGreaterThan(String value) {
			addCriterion("FLOW_ID >", value, "flowId");
			return (Criteria) this;
		}

		public Criteria andFlowIdGreaterThanOrEqualTo(String value) {
			addCriterion("FLOW_ID >=", value, "flowId");
			return (Criteria) this;
		}

		public Criteria andFlowIdLessThan(String value) {
			addCriterion("FLOW_ID <", value, "flowId");
			return (Criteria) this;
		}

		public Criteria andFlowIdLessThanOrEqualTo(String value) {
			addCriterion("FLOW_ID <=", value, "flowId");
			return (Criteria) this;
		}

		public Criteria andFlowIdLike(String value) {
			addCriterion("FLOW_ID like", value, "flowId");
			return (Criteria) this;
		}

		public Criteria andFlowIdNotLike(String value) {
			addCriterion("FLOW_ID not like", value, "flowId");
			return (Criteria) this;
		}

		public Criteria andFlowIdIn(List<String> values) {
			addCriterion("FLOW_ID in", values, "flowId");
			return (Criteria) this;
		}

		public Criteria andFlowIdNotIn(List<String> values) {
			addCriterion("FLOW_ID not in", values, "flowId");
			return (Criteria) this;
		}

		public Criteria andFlowIdBetween(String value1, String value2) {
			addCriterion("FLOW_ID between", value1, value2, "flowId");
			return (Criteria) this;
		}

		public Criteria andFlowIdNotBetween(String value1, String value2) {
			addCriterion("FLOW_ID not between", value1, value2, "flowId");
			return (Criteria) this;
		}

		public Criteria andProcKeyIsNull() {
			addCriterion("PROC_KEY is null");
			return (Criteria) this;
		}

		public Criteria andProcKeyIsNotNull() {
			addCriterion("PROC_KEY is not null");
			return (Criteria) this;
		}

		public Criteria andProcKeyEqualTo(String value) {
			addCriterion("PROC_KEY =", value, "procKey");
			return (Criteria) this;
		}

		public Criteria andProcKeyNotEqualTo(String value) {
			addCriterion("PROC_KEY <>", value, "procKey");
			return (Criteria) this;
		}

		public Criteria andProcKeyGreaterThan(String value) {
			addCriterion("PROC_KEY >", value, "procKey");
			return (Criteria) this;
		}

		public Criteria andProcKeyGreaterThanOrEqualTo(String value) {
			addCriterion("PROC_KEY >=", value, "procKey");
			return (Criteria) this;
		}

		public Criteria andProcKeyLessThan(String value) {
			addCriterion("PROC_KEY <", value, "procKey");
			return (Criteria) this;
		}

		public Criteria andProcKeyLessThanOrEqualTo(String value) {
			addCriterion("PROC_KEY <=", value, "procKey");
			return (Criteria) this;
		}

		public Criteria andProcKeyLike(String value) {
			addCriterion("PROC_KEY like", value, "procKey");
			return (Criteria) this;
		}

		public Criteria andProcKeyNotLike(String value) {
			addCriterion("PROC_KEY not like", value, "procKey");
			return (Criteria) this;
		}

		public Criteria andProcKeyIn(List<String> values) {
			addCriterion("PROC_KEY in", values, "procKey");
			return (Criteria) this;
		}

		public Criteria andProcKeyNotIn(List<String> values) {
			addCriterion("PROC_KEY not in", values, "procKey");
			return (Criteria) this;
		}

		public Criteria andProcKeyBetween(String value1, String value2) {
			addCriterion("PROC_KEY between", value1, value2, "procKey");
			return (Criteria) this;
		}

		public Criteria andProcKeyNotBetween(String value1, String value2) {
			addCriterion("PROC_KEY not between", value1, value2, "procKey");
			return (Criteria) this;
		}

		public Criteria andFlowNameIsNull() {
			addCriterion("FLOW_NAME is null");
			return (Criteria) this;
		}

		public Criteria andFlowNameIsNotNull() {
			addCriterion("FLOW_NAME is not null");
			return (Criteria) this;
		}

		public Criteria andFlowNameEqualTo(String value) {
			addCriterion("FLOW_NAME =", value, "flowName");
			return (Criteria) this;
		}

		public Criteria andFlowNameNotEqualTo(String value) {
			addCriterion("FLOW_NAME <>", value, "flowName");
			return (Criteria) this;
		}

		public Criteria andFlowNameGreaterThan(String value) {
			addCriterion("FLOW_NAME >", value, "flowName");
			return (Criteria) this;
		}

		public Criteria andFlowNameGreaterThanOrEqualTo(String value) {
			addCriterion("FLOW_NAME >=", value, "flowName");
			return (Criteria) this;
		}

		public Criteria andFlowNameLessThan(String value) {
			addCriterion("FLOW_NAME <", value, "flowName");
			return (Criteria) this;
		}

		public Criteria andFlowNameLessThanOrEqualTo(String value) {
			addCriterion("FLOW_NAME <=", value, "flowName");
			return (Criteria) this;
		}

		public Criteria andFlowNameLike(String value) {
			addCriterion("FLOW_NAME like", value, "flowName");
			return (Criteria) this;
		}

		public Criteria andFlowNameNotLike(String value) {
			addCriterion("FLOW_NAME not like", value, "flowName");
			return (Criteria) this;
		}

		public Criteria andFlowNameIn(List<String> values) {
			addCriterion("FLOW_NAME in", values, "flowName");
			return (Criteria) this;
		}

		public Criteria andFlowNameNotIn(List<String> values) {
			addCriterion("FLOW_NAME not in", values, "flowName");
			return (Criteria) this;
		}

		public Criteria andFlowNameBetween(String value1, String value2) {
			addCriterion("FLOW_NAME between", value1, value2, "flowName");
			return (Criteria) this;
		}

		public Criteria andFlowNameNotBetween(String value1, String value2) {
			addCriterion("FLOW_NAME not between", value1, value2, "flowName");
			return (Criteria) this;
		}

		public Criteria andSourceRefIsNull() {
			addCriterion("SOURCE_REF is null");
			return (Criteria) this;
		}

		public Criteria andSourceRefIsNotNull() {
			addCriterion("SOURCE_REF is not null");
			return (Criteria) this;
		}

		public Criteria andSourceRefEqualTo(String value) {
			addCriterion("SOURCE_REF =", value, "sourceRef");
			return (Criteria) this;
		}

		public Criteria andSourceRefNotEqualTo(String value) {
			addCriterion("SOURCE_REF <>", value, "sourceRef");
			return (Criteria) this;
		}

		public Criteria andSourceRefGreaterThan(String value) {
			addCriterion("SOURCE_REF >", value, "sourceRef");
			return (Criteria) this;
		}

		public Criteria andSourceRefGreaterThanOrEqualTo(String value) {
			addCriterion("SOURCE_REF >=", value, "sourceRef");
			return (Criteria) this;
		}

		public Criteria andSourceRefLessThan(String value) {
			addCriterion("SOURCE_REF <", value, "sourceRef");
			return (Criteria) this;
		}

		public Criteria andSourceRefLessThanOrEqualTo(String value) {
			addCriterion("SOURCE_REF <=", value, "sourceRef");
			return (Criteria) this;
		}

		public Criteria andSourceRefLike(String value) {
			addCriterion("SOURCE_REF like", value, "sourceRef");
			return (Criteria) this;
		}

		public Criteria andSourceRefNotLike(String value) {
			addCriterion("SOURCE_REF not like", value, "sourceRef");
			return (Criteria) this;
		}

		public Criteria andSourceRefIn(List<String> values) {
			addCriterion("SOURCE_REF in", values, "sourceRef");
			return (Criteria) this;
		}

		public Criteria andSourceRefNotIn(List<String> values) {
			addCriterion("SOURCE_REF not in", values, "sourceRef");
			return (Criteria) this;
		}

		public Criteria andSourceRefBetween(String value1, String value2) {
			addCriterion("SOURCE_REF between", value1, value2, "sourceRef");
			return (Criteria) this;
		}

		public Criteria andSourceRefNotBetween(String value1, String value2) {
			addCriterion("SOURCE_REF not between", value1, value2, "sourceRef");
			return (Criteria) this;
		}

		public Criteria andTargetRefIsNull() {
			addCriterion("TARGET_REF is null");
			return (Criteria) this;
		}

		public Criteria andTargetRefIsNotNull() {
			addCriterion("TARGET_REF is not null");
			return (Criteria) this;
		}

		public Criteria andTargetRefEqualTo(String value) {
			addCriterion("TARGET_REF =", value, "targetRef");
			return (Criteria) this;
		}

		public Criteria andTargetRefNotEqualTo(String value) {
			addCriterion("TARGET_REF <>", value, "targetRef");
			return (Criteria) this;
		}

		public Criteria andTargetRefGreaterThan(String value) {
			addCriterion("TARGET_REF >", value, "targetRef");
			return (Criteria) this;
		}

		public Criteria andTargetRefGreaterThanOrEqualTo(String value) {
			addCriterion("TARGET_REF >=", value, "targetRef");
			return (Criteria) this;
		}

		public Criteria andTargetRefLessThan(String value) {
			addCriterion("TARGET_REF <", value, "targetRef");
			return (Criteria) this;
		}

		public Criteria andTargetRefLessThanOrEqualTo(String value) {
			addCriterion("TARGET_REF <=", value, "targetRef");
			return (Criteria) this;
		}

		public Criteria andTargetRefLike(String value) {
			addCriterion("TARGET_REF like", value, "targetRef");
			return (Criteria) this;
		}

		public Criteria andTargetRefNotLike(String value) {
			addCriterion("TARGET_REF not like", value, "targetRef");
			return (Criteria) this;
		}

		public Criteria andTargetRefIn(List<String> values) {
			addCriterion("TARGET_REF in", values, "targetRef");
			return (Criteria) this;
		}

		public Criteria andTargetRefNotIn(List<String> values) {
			addCriterion("TARGET_REF not in", values, "targetRef");
			return (Criteria) this;
		}

		public Criteria andTargetRefBetween(String value1, String value2) {
			addCriterion("TARGET_REF between", value1, value2, "targetRef");
			return (Criteria) this;
		}

		public Criteria andTargetRefNotBetween(String value1, String value2) {
			addCriterion("TARGET_REF not between", value1, value2, "targetRef");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table LIANGFA_XINXIANG20170421.PROC_SEQUENCE
	 * @mbggenerated  Sat Apr 22 16:00:37 CST 2017
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
     * This class corresponds to the database table LIANGFA_XINXIANG20170421.PROC_SEQUENCE
     *
     * @mbggenerated do_not_delete_during_merge Sat Apr 22 15:04:50 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
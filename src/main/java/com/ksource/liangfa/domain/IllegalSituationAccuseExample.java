package com.ksource.liangfa.domain;

import java.util.ArrayList;
import java.util.List;

public class IllegalSituationAccuseExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
	 */
	public IllegalSituationAccuseExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
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

		public Criteria andIllegalSituationIdIsNull() {
			addCriterion("ILLEGAL_SITUATION_ID is null");
			return (Criteria) this;
		}

		public Criteria andIllegalSituationIdIsNotNull() {
			addCriterion("ILLEGAL_SITUATION_ID is not null");
			return (Criteria) this;
		}

		public Criteria andIllegalSituationIdEqualTo(String value) {
			addCriterion("ILLEGAL_SITUATION_ID =", value, "illegalSituationId");
			return (Criteria) this;
		}

		public Criteria andIllegalSituationIdNotEqualTo(String value) {
			addCriterion("ILLEGAL_SITUATION_ID <>", value, "illegalSituationId");
			return (Criteria) this;
		}

		public Criteria andIllegalSituationIdGreaterThan(String value) {
			addCriterion("ILLEGAL_SITUATION_ID >", value, "illegalSituationId");
			return (Criteria) this;
		}

		public Criteria andIllegalSituationIdGreaterThanOrEqualTo(String value) {
			addCriterion("ILLEGAL_SITUATION_ID >=", value, "illegalSituationId");
			return (Criteria) this;
		}

		public Criteria andIllegalSituationIdLessThan(String value) {
			addCriterion("ILLEGAL_SITUATION_ID <", value, "illegalSituationId");
			return (Criteria) this;
		}

		public Criteria andIllegalSituationIdLessThanOrEqualTo(String value) {
			addCriterion("ILLEGAL_SITUATION_ID <=", value, "illegalSituationId");
			return (Criteria) this;
		}

		public Criteria andIllegalSituationIdLike(String value) {
			addCriterion("ILLEGAL_SITUATION_ID like", value,
					"illegalSituationId");
			return (Criteria) this;
		}

		public Criteria andIllegalSituationIdNotLike(String value) {
			addCriterion("ILLEGAL_SITUATION_ID not like", value,
					"illegalSituationId");
			return (Criteria) this;
		}

		public Criteria andIllegalSituationIdIn(List<String> values) {
			addCriterion("ILLEGAL_SITUATION_ID in", values,
					"illegalSituationId");
			return (Criteria) this;
		}

		public Criteria andIllegalSituationIdNotIn(List<String> values) {
			addCriterion("ILLEGAL_SITUATION_ID not in", values,
					"illegalSituationId");
			return (Criteria) this;
		}

		public Criteria andIllegalSituationIdBetween(String value1,
				String value2) {
			addCriterion("ILLEGAL_SITUATION_ID between", value1, value2,
					"illegalSituationId");
			return (Criteria) this;
		}

		public Criteria andIllegalSituationIdNotBetween(String value1,
				String value2) {
			addCriterion("ILLEGAL_SITUATION_ID not between", value1, value2,
					"illegalSituationId");
			return (Criteria) this;
		}

		public Criteria andAccuseIdIsNull() {
			addCriterion("ACCUSE_ID is null");
			return (Criteria) this;
		}

		public Criteria andAccuseIdIsNotNull() {
			addCriterion("ACCUSE_ID is not null");
			return (Criteria) this;
		}

		public Criteria andAccuseIdEqualTo(Integer value) {
			addCriterion("ACCUSE_ID =", value, "accuseId");
			return (Criteria) this;
		}

		public Criteria andAccuseIdNotEqualTo(Integer value) {
			addCriterion("ACCUSE_ID <>", value, "accuseId");
			return (Criteria) this;
		}

		public Criteria andAccuseIdGreaterThan(Integer value) {
			addCriterion("ACCUSE_ID >", value, "accuseId");
			return (Criteria) this;
		}

		public Criteria andAccuseIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("ACCUSE_ID >=", value, "accuseId");
			return (Criteria) this;
		}

		public Criteria andAccuseIdLessThan(Integer value) {
			addCriterion("ACCUSE_ID <", value, "accuseId");
			return (Criteria) this;
		}

		public Criteria andAccuseIdLessThanOrEqualTo(Integer value) {
			addCriterion("ACCUSE_ID <=", value, "accuseId");
			return (Criteria) this;
		}

		public Criteria andAccuseIdIn(List<Integer> values) {
			addCriterion("ACCUSE_ID in", values, "accuseId");
			return (Criteria) this;
		}

		public Criteria andAccuseIdNotIn(List<Integer> values) {
			addCriterion("ACCUSE_ID not in", values, "accuseId");
			return (Criteria) this;
		}

		public Criteria andAccuseIdBetween(Integer value1, Integer value2) {
			addCriterion("ACCUSE_ID between", value1, value2, "accuseId");
			return (Criteria) this;
		}

		public Criteria andAccuseIdNotBetween(Integer value1, Integer value2) {
			addCriterion("ACCUSE_ID not between", value1, value2, "accuseId");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
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
     * This class corresponds to the database table LIANGFA_JINGZHOU20160722.ILLEGAL_SITUATION_ACCUSE
     *
     * @mbggenerated do_not_delete_during_merge Thu Jul 28 17:15:15 CST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
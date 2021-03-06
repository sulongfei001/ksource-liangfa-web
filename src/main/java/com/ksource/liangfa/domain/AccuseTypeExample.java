package com.ksource.liangfa.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccuseTypeExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	public AccuseTypeExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
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

		public Criteria andAccuseNameIsNull() {
			addCriterion("ACCUSE_NAME is null");
			return (Criteria) this;
		}

		public Criteria andAccuseNameIsNotNull() {
			addCriterion("ACCUSE_NAME is not null");
			return (Criteria) this;
		}

		public Criteria andAccuseNameEqualTo(String value) {
			addCriterion("ACCUSE_NAME =", value, "accuseName");
			return (Criteria) this;
		}

		public Criteria andAccuseNameNotEqualTo(String value) {
			addCriterion("ACCUSE_NAME <>", value, "accuseName");
			return (Criteria) this;
		}

		public Criteria andAccuseNameGreaterThan(String value) {
			addCriterion("ACCUSE_NAME >", value, "accuseName");
			return (Criteria) this;
		}

		public Criteria andAccuseNameGreaterThanOrEqualTo(String value) {
			addCriterion("ACCUSE_NAME >=", value, "accuseName");
			return (Criteria) this;
		}

		public Criteria andAccuseNameLessThan(String value) {
			addCriterion("ACCUSE_NAME <", value, "accuseName");
			return (Criteria) this;
		}

		public Criteria andAccuseNameLessThanOrEqualTo(String value) {
			addCriterion("ACCUSE_NAME <=", value, "accuseName");
			return (Criteria) this;
		}

		public Criteria andAccuseNameLike(String value) {
			addCriterion("ACCUSE_NAME like", value, "accuseName");
			return (Criteria) this;
		}

		public Criteria andAccuseNameNotLike(String value) {
			addCriterion("ACCUSE_NAME not like", value, "accuseName");
			return (Criteria) this;
		}

		public Criteria andAccuseNameIn(List<String> values) {
			addCriterion("ACCUSE_NAME in", values, "accuseName");
			return (Criteria) this;
		}

		public Criteria andAccuseNameNotIn(List<String> values) {
			addCriterion("ACCUSE_NAME not in", values, "accuseName");
			return (Criteria) this;
		}

		public Criteria andAccuseNameBetween(String value1, String value2) {
			addCriterion("ACCUSE_NAME between", value1, value2, "accuseName");
			return (Criteria) this;
		}

		public Criteria andAccuseNameNotBetween(String value1, String value2) {
			addCriterion("ACCUSE_NAME not between", value1, value2,
					"accuseName");
			return (Criteria) this;
		}

		public Criteria andAccuseLevelIsNull() {
			addCriterion("ACCUSE_LEVEL is null");
			return (Criteria) this;
		}

		public Criteria andAccuseLevelIsNotNull() {
			addCriterion("ACCUSE_LEVEL is not null");
			return (Criteria) this;
		}

		public Criteria andAccuseLevelEqualTo(Integer value) {
			addCriterion("ACCUSE_LEVEL =", value, "accuseLevel");
			return (Criteria) this;
		}

		public Criteria andAccuseLevelNotEqualTo(Integer value) {
			addCriterion("ACCUSE_LEVEL <>", value, "accuseLevel");
			return (Criteria) this;
		}

		public Criteria andAccuseLevelGreaterThan(Integer value) {
			addCriterion("ACCUSE_LEVEL >", value, "accuseLevel");
			return (Criteria) this;
		}

		public Criteria andAccuseLevelGreaterThanOrEqualTo(Integer value) {
			addCriterion("ACCUSE_LEVEL >=", value, "accuseLevel");
			return (Criteria) this;
		}

		public Criteria andAccuseLevelLessThan(Integer value) {
			addCriterion("ACCUSE_LEVEL <", value, "accuseLevel");
			return (Criteria) this;
		}

		public Criteria andAccuseLevelLessThanOrEqualTo(Integer value) {
			addCriterion("ACCUSE_LEVEL <=", value, "accuseLevel");
			return (Criteria) this;
		}

		public Criteria andAccuseLevelIn(List<Integer> values) {
			addCriterion("ACCUSE_LEVEL in", values, "accuseLevel");
			return (Criteria) this;
		}

		public Criteria andAccuseLevelNotIn(List<Integer> values) {
			addCriterion("ACCUSE_LEVEL not in", values, "accuseLevel");
			return (Criteria) this;
		}

		public Criteria andAccuseLevelBetween(Integer value1, Integer value2) {
			addCriterion("ACCUSE_LEVEL between", value1, value2, "accuseLevel");
			return (Criteria) this;
		}

		public Criteria andAccuseLevelNotBetween(Integer value1, Integer value2) {
			addCriterion("ACCUSE_LEVEL not between", value1, value2,
					"accuseLevel");
			return (Criteria) this;
		}

		public Criteria andParentIdIsNull() {
			addCriterion("PARENT_ID is null");
			return (Criteria) this;
		}

		public Criteria andParentIdIsNotNull() {
			addCriterion("PARENT_ID is not null");
			return (Criteria) this;
		}

		public Criteria andParentIdEqualTo(Integer value) {
			addCriterion("PARENT_ID =", value, "parentId");
			return (Criteria) this;
		}

		public Criteria andParentIdNotEqualTo(Integer value) {
			addCriterion("PARENT_ID <>", value, "parentId");
			return (Criteria) this;
		}

		public Criteria andParentIdGreaterThan(Integer value) {
			addCriterion("PARENT_ID >", value, "parentId");
			return (Criteria) this;
		}

		public Criteria andParentIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("PARENT_ID >=", value, "parentId");
			return (Criteria) this;
		}

		public Criteria andParentIdLessThan(Integer value) {
			addCriterion("PARENT_ID <", value, "parentId");
			return (Criteria) this;
		}

		public Criteria andParentIdLessThanOrEqualTo(Integer value) {
			addCriterion("PARENT_ID <=", value, "parentId");
			return (Criteria) this;
		}

		public Criteria andParentIdIn(List<Integer> values) {
			addCriterion("PARENT_ID in", values, "parentId");
			return (Criteria) this;
		}

		public Criteria andParentIdNotIn(List<Integer> values) {
			addCriterion("PARENT_ID not in", values, "parentId");
			return (Criteria) this;
		}

		public Criteria andParentIdBetween(Integer value1, Integer value2) {
			addCriterion("PARENT_ID between", value1, value2, "parentId");
			return (Criteria) this;
		}

		public Criteria andParentIdNotBetween(Integer value1, Integer value2) {
			addCriterion("PARENT_ID not between", value1, value2, "parentId");
			return (Criteria) this;
		}

		public Criteria andAccuseOrderIsNull() {
			addCriterion("ACCUSE_ORDER is null");
			return (Criteria) this;
		}

		public Criteria andAccuseOrderIsNotNull() {
			addCriterion("ACCUSE_ORDER is not null");
			return (Criteria) this;
		}

		public Criteria andAccuseOrderEqualTo(Integer value) {
			addCriterion("ACCUSE_ORDER =", value, "accuseOrder");
			return (Criteria) this;
		}

		public Criteria andAccuseOrderNotEqualTo(Integer value) {
			addCriterion("ACCUSE_ORDER <>", value, "accuseOrder");
			return (Criteria) this;
		}

		public Criteria andAccuseOrderGreaterThan(Integer value) {
			addCriterion("ACCUSE_ORDER >", value, "accuseOrder");
			return (Criteria) this;
		}

		public Criteria andAccuseOrderGreaterThanOrEqualTo(Integer value) {
			addCriterion("ACCUSE_ORDER >=", value, "accuseOrder");
			return (Criteria) this;
		}

		public Criteria andAccuseOrderLessThan(Integer value) {
			addCriterion("ACCUSE_ORDER <", value, "accuseOrder");
			return (Criteria) this;
		}

		public Criteria andAccuseOrderLessThanOrEqualTo(Integer value) {
			addCriterion("ACCUSE_ORDER <=", value, "accuseOrder");
			return (Criteria) this;
		}

		public Criteria andAccuseOrderIn(List<Integer> values) {
			addCriterion("ACCUSE_ORDER in", values, "accuseOrder");
			return (Criteria) this;
		}

		public Criteria andAccuseOrderNotIn(List<Integer> values) {
			addCriterion("ACCUSE_ORDER not in", values, "accuseOrder");
			return (Criteria) this;
		}

		public Criteria andAccuseOrderBetween(Integer value1, Integer value2) {
			addCriterion("ACCUSE_ORDER between", value1, value2, "accuseOrder");
			return (Criteria) this;
		}

		public Criteria andAccuseOrderNotBetween(Integer value1, Integer value2) {
			addCriterion("ACCUSE_ORDER not between", value1, value2,
					"accuseOrder");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
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
     * This class corresponds to the database table ACCUSE_TYPE
     *
     * @mbggenerated do_not_delete_during_merge Thu Jul 19 17:20:05 CST 2012
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
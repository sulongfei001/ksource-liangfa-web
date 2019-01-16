package com.ksource.liangfa.domain;

import java.util.ArrayList;
import java.util.List;

public class FormDefExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	public FormDefExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
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

		public Criteria andFormDefIdIsNull() {
			addCriterion("FORM_DEF_ID is null");
			return (Criteria) this;
		}

		public Criteria andFormDefIdIsNotNull() {
			addCriterion("FORM_DEF_ID is not null");
			return (Criteria) this;
		}

		public Criteria andFormDefIdEqualTo(Integer value) {
			addCriterion("FORM_DEF_ID =", value, "formDefId");
			return (Criteria) this;
		}

		public Criteria andFormDefIdNotEqualTo(Integer value) {
			addCriterion("FORM_DEF_ID <>", value, "formDefId");
			return (Criteria) this;
		}

		public Criteria andFormDefIdGreaterThan(Integer value) {
			addCriterion("FORM_DEF_ID >", value, "formDefId");
			return (Criteria) this;
		}

		public Criteria andFormDefIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("FORM_DEF_ID >=", value, "formDefId");
			return (Criteria) this;
		}

		public Criteria andFormDefIdLessThan(Integer value) {
			addCriterion("FORM_DEF_ID <", value, "formDefId");
			return (Criteria) this;
		}

		public Criteria andFormDefIdLessThanOrEqualTo(Integer value) {
			addCriterion("FORM_DEF_ID <=", value, "formDefId");
			return (Criteria) this;
		}

		public Criteria andFormDefIdIn(List<Integer> values) {
			addCriterion("FORM_DEF_ID in", values, "formDefId");
			return (Criteria) this;
		}

		public Criteria andFormDefIdNotIn(List<Integer> values) {
			addCriterion("FORM_DEF_ID not in", values, "formDefId");
			return (Criteria) this;
		}

		public Criteria andFormDefIdBetween(Integer value1, Integer value2) {
			addCriterion("FORM_DEF_ID between", value1, value2, "formDefId");
			return (Criteria) this;
		}

		public Criteria andFormDefIdNotBetween(Integer value1, Integer value2) {
			addCriterion("FORM_DEF_ID not between", value1, value2, "formDefId");
			return (Criteria) this;
		}

		public Criteria andFormDefNameIsNull() {
			addCriterion("FORM_DEF_NAME is null");
			return (Criteria) this;
		}

		public Criteria andFormDefNameIsNotNull() {
			addCriterion("FORM_DEF_NAME is not null");
			return (Criteria) this;
		}

		public Criteria andFormDefNameEqualTo(String value) {
			addCriterion("FORM_DEF_NAME =", value, "formDefName");
			return (Criteria) this;
		}

		public Criteria andFormDefNameNotEqualTo(String value) {
			addCriterion("FORM_DEF_NAME <>", value, "formDefName");
			return (Criteria) this;
		}

		public Criteria andFormDefNameGreaterThan(String value) {
			addCriterion("FORM_DEF_NAME >", value, "formDefName");
			return (Criteria) this;
		}

		public Criteria andFormDefNameGreaterThanOrEqualTo(String value) {
			addCriterion("FORM_DEF_NAME >=", value, "formDefName");
			return (Criteria) this;
		}

		public Criteria andFormDefNameLessThan(String value) {
			addCriterion("FORM_DEF_NAME <", value, "formDefName");
			return (Criteria) this;
		}

		public Criteria andFormDefNameLessThanOrEqualTo(String value) {
			addCriterion("FORM_DEF_NAME <=", value, "formDefName");
			return (Criteria) this;
		}

		public Criteria andFormDefNameLike(String value) {
			addCriterion("FORM_DEF_NAME like", value, "formDefName");
			return (Criteria) this;
		}

		public Criteria andFormDefNameNotLike(String value) {
			addCriterion("FORM_DEF_NAME not like", value, "formDefName");
			return (Criteria) this;
		}

		public Criteria andFormDefNameIn(List<String> values) {
			addCriterion("FORM_DEF_NAME in", values, "formDefName");
			return (Criteria) this;
		}

		public Criteria andFormDefNameNotIn(List<String> values) {
			addCriterion("FORM_DEF_NAME not in", values, "formDefName");
			return (Criteria) this;
		}

		public Criteria andFormDefNameBetween(String value1, String value2) {
			addCriterion("FORM_DEF_NAME between", value1, value2, "formDefName");
			return (Criteria) this;
		}

		public Criteria andFormDefNameNotBetween(String value1, String value2) {
			addCriterion("FORM_DEF_NAME not between", value1, value2,
					"formDefName");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
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
     * This class corresponds to the database table FORM_DEF
     *
     * @mbggenerated do_not_delete_during_merge Fri May 06 19:54:43 CST 2011
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
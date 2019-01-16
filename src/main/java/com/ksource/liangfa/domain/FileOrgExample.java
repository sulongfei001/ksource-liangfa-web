package com.ksource.liangfa.domain;

import java.util.ArrayList;
import java.util.List;

public class FileOrgExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	public FileOrgExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
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

		public Criteria andFileIdIsNull() {
			addCriterion("FILE_ID is null");
			return (Criteria) this;
		}

		public Criteria andFileIdIsNotNull() {
			addCriterion("FILE_ID is not null");
			return (Criteria) this;
		}

		public Criteria andFileIdEqualTo(Integer value) {
			addCriterion("FILE_ID =", value, "fileId");
			return (Criteria) this;
		}

		public Criteria andFileIdNotEqualTo(Integer value) {
			addCriterion("FILE_ID <>", value, "fileId");
			return (Criteria) this;
		}

		public Criteria andFileIdGreaterThan(Integer value) {
			addCriterion("FILE_ID >", value, "fileId");
			return (Criteria) this;
		}

		public Criteria andFileIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("FILE_ID >=", value, "fileId");
			return (Criteria) this;
		}

		public Criteria andFileIdLessThan(Integer value) {
			addCriterion("FILE_ID <", value, "fileId");
			return (Criteria) this;
		}

		public Criteria andFileIdLessThanOrEqualTo(Integer value) {
			addCriterion("FILE_ID <=", value, "fileId");
			return (Criteria) this;
		}

		public Criteria andFileIdIn(List<Integer> values) {
			addCriterion("FILE_ID in", values, "fileId");
			return (Criteria) this;
		}

		public Criteria andFileIdNotIn(List<Integer> values) {
			addCriterion("FILE_ID not in", values, "fileId");
			return (Criteria) this;
		}

		public Criteria andFileIdBetween(Integer value1, Integer value2) {
			addCriterion("FILE_ID between", value1, value2, "fileId");
			return (Criteria) this;
		}

		public Criteria andFileIdNotBetween(Integer value1, Integer value2) {
			addCriterion("FILE_ID not between", value1, value2, "fileId");
			return (Criteria) this;
		}

		public Criteria andOrgIdIsNull() {
			addCriterion("ORG_ID is null");
			return (Criteria) this;
		}

		public Criteria andOrgIdIsNotNull() {
			addCriterion("ORG_ID is not null");
			return (Criteria) this;
		}

		public Criteria andOrgIdEqualTo(Integer value) {
			addCriterion("ORG_ID =", value, "orgId");
			return (Criteria) this;
		}

		public Criteria andOrgIdNotEqualTo(Integer value) {
			addCriterion("ORG_ID <>", value, "orgId");
			return (Criteria) this;
		}

		public Criteria andOrgIdGreaterThan(Integer value) {
			addCriterion("ORG_ID >", value, "orgId");
			return (Criteria) this;
		}

		public Criteria andOrgIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("ORG_ID >=", value, "orgId");
			return (Criteria) this;
		}

		public Criteria andOrgIdLessThan(Integer value) {
			addCriterion("ORG_ID <", value, "orgId");
			return (Criteria) this;
		}

		public Criteria andOrgIdLessThanOrEqualTo(Integer value) {
			addCriterion("ORG_ID <=", value, "orgId");
			return (Criteria) this;
		}

		public Criteria andOrgIdIn(List<Integer> values) {
			addCriterion("ORG_ID in", values, "orgId");
			return (Criteria) this;
		}

		public Criteria andOrgIdNotIn(List<Integer> values) {
			addCriterion("ORG_ID not in", values, "orgId");
			return (Criteria) this;
		}

		public Criteria andOrgIdBetween(Integer value1, Integer value2) {
			addCriterion("ORG_ID between", value1, value2, "orgId");
			return (Criteria) this;
		}

		public Criteria andOrgIdNotBetween(Integer value1, Integer value2) {
			addCriterion("ORG_ID not between", value1, value2, "orgId");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
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
     * This class corresponds to the database table FILE_ORG
     *
     * @mbggenerated do_not_delete_during_merge Sat Oct 08 17:46:06 CST 2011
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
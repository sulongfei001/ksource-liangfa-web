package com.ksource.liangfa.domain;

import java.util.ArrayList;
import java.util.List;

public class PostExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table POST_
	 * @mbggenerated  Fri Dec 30 11:59:03 CST 2011
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table POST_
	 * @mbggenerated  Fri Dec 30 11:59:03 CST 2011
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table POST_
	 * @mbggenerated  Fri Dec 30 11:59:03 CST 2011
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table POST_
	 * @mbggenerated  Fri Dec 30 11:59:03 CST 2011
	 */
	public PostExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table POST_
	 * @mbggenerated  Fri Dec 30 11:59:03 CST 2011
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table POST_
	 * @mbggenerated  Fri Dec 30 11:59:03 CST 2011
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table POST_
	 * @mbggenerated  Fri Dec 30 11:59:03 CST 2011
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table POST_
	 * @mbggenerated  Fri Dec 30 11:59:03 CST 2011
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table POST_
	 * @mbggenerated  Fri Dec 30 11:59:03 CST 2011
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table POST_
	 * @mbggenerated  Fri Dec 30 11:59:03 CST 2011
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table POST_
	 * @mbggenerated  Fri Dec 30 11:59:03 CST 2011
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table POST_
	 * @mbggenerated  Fri Dec 30 11:59:03 CST 2011
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table POST_
	 * @mbggenerated  Fri Dec 30 11:59:03 CST 2011
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table POST_
	 * @mbggenerated  Fri Dec 30 11:59:03 CST 2011
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table POST_
	 * @mbggenerated  Fri Dec 30 11:59:03 CST 2011
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

		public Criteria andPostIdIsNull() {
			addCriterion("POST_ID is null");
			return (Criteria) this;
		}

		public Criteria andPostIdIsNotNull() {
			addCriterion("POST_ID is not null");
			return (Criteria) this;
		}

		public Criteria andPostIdEqualTo(Integer value) {
			addCriterion("POST_ID =", value, "postId");
			return (Criteria) this;
		}

		public Criteria andPostIdNotEqualTo(Integer value) {
			addCriterion("POST_ID <>", value, "postId");
			return (Criteria) this;
		}

		public Criteria andPostIdGreaterThan(Integer value) {
			addCriterion("POST_ID >", value, "postId");
			return (Criteria) this;
		}

		public Criteria andPostIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("POST_ID >=", value, "postId");
			return (Criteria) this;
		}

		public Criteria andPostIdLessThan(Integer value) {
			addCriterion("POST_ID <", value, "postId");
			return (Criteria) this;
		}

		public Criteria andPostIdLessThanOrEqualTo(Integer value) {
			addCriterion("POST_ID <=", value, "postId");
			return (Criteria) this;
		}

		public Criteria andPostIdIn(List<Integer> values) {
			addCriterion("POST_ID in", values, "postId");
			return (Criteria) this;
		}

		public Criteria andPostIdNotIn(List<Integer> values) {
			addCriterion("POST_ID not in", values, "postId");
			return (Criteria) this;
		}

		public Criteria andPostIdBetween(Integer value1, Integer value2) {
			addCriterion("POST_ID between", value1, value2, "postId");
			return (Criteria) this;
		}

		public Criteria andPostIdNotBetween(Integer value1, Integer value2) {
			addCriterion("POST_ID not between", value1, value2, "postId");
			return (Criteria) this;
		}

		public Criteria andPostNameIsNull() {
			addCriterion("POST_NAME is null");
			return (Criteria) this;
		}

		public Criteria andPostNameIsNotNull() {
			addCriterion("POST_NAME is not null");
			return (Criteria) this;
		}

		public Criteria andPostNameEqualTo(String value) {
			addCriterion("POST_NAME =", value, "postName");
			return (Criteria) this;
		}

		public Criteria andPostNameNotEqualTo(String value) {
			addCriterion("POST_NAME <>", value, "postName");
			return (Criteria) this;
		}

		public Criteria andPostNameGreaterThan(String value) {
			addCriterion("POST_NAME >", value, "postName");
			return (Criteria) this;
		}

		public Criteria andPostNameGreaterThanOrEqualTo(String value) {
			addCriterion("POST_NAME >=", value, "postName");
			return (Criteria) this;
		}

		public Criteria andPostNameLessThan(String value) {
			addCriterion("POST_NAME <", value, "postName");
			return (Criteria) this;
		}

		public Criteria andPostNameLessThanOrEqualTo(String value) {
			addCriterion("POST_NAME <=", value, "postName");
			return (Criteria) this;
		}

		public Criteria andPostNameLike(String value) {
			addCriterion("POST_NAME like", value, "postName");
			return (Criteria) this;
		}

		public Criteria andPostNameNotLike(String value) {
			addCriterion("POST_NAME not like", value, "postName");
			return (Criteria) this;
		}

		public Criteria andPostNameIn(List<String> values) {
			addCriterion("POST_NAME in", values, "postName");
			return (Criteria) this;
		}

		public Criteria andPostNameNotIn(List<String> values) {
			addCriterion("POST_NAME not in", values, "postName");
			return (Criteria) this;
		}

		public Criteria andPostNameBetween(String value1, String value2) {
			addCriterion("POST_NAME between", value1, value2, "postName");
			return (Criteria) this;
		}

		public Criteria andPostNameNotBetween(String value1, String value2) {
			addCriterion("POST_NAME not between", value1, value2, "postName");
			return (Criteria) this;
		}

		public Criteria andDescriptionIsNull() {
			addCriterion("DESCRIPTION is null");
			return (Criteria) this;
		}

		public Criteria andDescriptionIsNotNull() {
			addCriterion("DESCRIPTION is not null");
			return (Criteria) this;
		}

		public Criteria andDescriptionEqualTo(String value) {
			addCriterion("DESCRIPTION =", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionNotEqualTo(String value) {
			addCriterion("DESCRIPTION <>", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionGreaterThan(String value) {
			addCriterion("DESCRIPTION >", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
			addCriterion("DESCRIPTION >=", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionLessThan(String value) {
			addCriterion("DESCRIPTION <", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionLessThanOrEqualTo(String value) {
			addCriterion("DESCRIPTION <=", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionLike(String value) {
			addCriterion("DESCRIPTION like", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionNotLike(String value) {
			addCriterion("DESCRIPTION not like", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionIn(List<String> values) {
			addCriterion("DESCRIPTION in", values, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionNotIn(List<String> values) {
			addCriterion("DESCRIPTION not in", values, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionBetween(String value1, String value2) {
			addCriterion("DESCRIPTION between", value1, value2, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionNotBetween(String value1, String value2) {
			addCriterion("DESCRIPTION not between", value1, value2,
					"description");
			return (Criteria) this;
		}

		public Criteria andPostTypeIsNull() {
			addCriterion("POST_TYPE is null");
			return (Criteria) this;
		}

		public Criteria andPostTypeIsNotNull() {
			addCriterion("POST_TYPE is not null");
			return (Criteria) this;
		}

		public Criteria andPostTypeEqualTo(String value) {
			addCriterion("POST_TYPE =", value, "postType");
			return (Criteria) this;
		}

		public Criteria andPostTypeNotEqualTo(String value) {
			addCriterion("POST_TYPE <>", value, "postType");
			return (Criteria) this;
		}

		public Criteria andPostTypeGreaterThan(String value) {
			addCriterion("POST_TYPE >", value, "postType");
			return (Criteria) this;
		}

		public Criteria andPostTypeGreaterThanOrEqualTo(String value) {
			addCriterion("POST_TYPE >=", value, "postType");
			return (Criteria) this;
		}

		public Criteria andPostTypeLessThan(String value) {
			addCriterion("POST_TYPE <", value, "postType");
			return (Criteria) this;
		}

		public Criteria andPostTypeLessThanOrEqualTo(String value) {
			addCriterion("POST_TYPE <=", value, "postType");
			return (Criteria) this;
		}

		public Criteria andPostTypeLike(String value) {
			addCriterion("POST_TYPE like", value, "postType");
			return (Criteria) this;
		}

		public Criteria andPostTypeNotLike(String value) {
			addCriterion("POST_TYPE not like", value, "postType");
			return (Criteria) this;
		}

		public Criteria andPostTypeIn(List<String> values) {
			addCriterion("POST_TYPE in", values, "postType");
			return (Criteria) this;
		}

		public Criteria andPostTypeNotIn(List<String> values) {
			addCriterion("POST_TYPE not in", values, "postType");
			return (Criteria) this;
		}

		public Criteria andPostTypeBetween(String value1, String value2) {
			addCriterion("POST_TYPE between", value1, value2, "postType");
			return (Criteria) this;
		}

		public Criteria andPostTypeNotBetween(String value1, String value2) {
			addCriterion("POST_TYPE not between", value1, value2, "postType");
			return (Criteria) this;
		}

		public Criteria andDeptIdIsNull() {
			addCriterion("DEPT_ID is null");
			return (Criteria) this;
		}

		public Criteria andDeptIdIsNotNull() {
			addCriterion("DEPT_ID is not null");
			return (Criteria) this;
		}

		public Criteria andDeptIdEqualTo(Integer value) {
			addCriterion("DEPT_ID =", value, "deptId");
			return (Criteria) this;
		}

		public Criteria andDeptIdNotEqualTo(Integer value) {
			addCriterion("DEPT_ID <>", value, "deptId");
			return (Criteria) this;
		}

		public Criteria andDeptIdGreaterThan(Integer value) {
			addCriterion("DEPT_ID >", value, "deptId");
			return (Criteria) this;
		}

		public Criteria andDeptIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("DEPT_ID >=", value, "deptId");
			return (Criteria) this;
		}

		public Criteria andDeptIdLessThan(Integer value) {
			addCriterion("DEPT_ID <", value, "deptId");
			return (Criteria) this;
		}

		public Criteria andDeptIdLessThanOrEqualTo(Integer value) {
			addCriterion("DEPT_ID <=", value, "deptId");
			return (Criteria) this;
		}

		public Criteria andDeptIdIn(List<Integer> values) {
			addCriterion("DEPT_ID in", values, "deptId");
			return (Criteria) this;
		}

		public Criteria andDeptIdNotIn(List<Integer> values) {
			addCriterion("DEPT_ID not in", values, "deptId");
			return (Criteria) this;
		}

		public Criteria andDeptIdBetween(Integer value1, Integer value2) {
			addCriterion("DEPT_ID between", value1, value2, "deptId");
			return (Criteria) this;
		}

		public Criteria andDeptIdNotBetween(Integer value1, Integer value2) {
			addCriterion("DEPT_ID not between", value1, value2, "deptId");
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
	 * This class was generated by MyBatis Generator. This class corresponds to the database table POST_
	 * @mbggenerated  Fri Dec 30 11:59:03 CST 2011
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
     * This class corresponds to the database table POST_
     *
     * @mbggenerated do_not_delete_during_merge Mon May 09 12:06:21 CST 2011
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
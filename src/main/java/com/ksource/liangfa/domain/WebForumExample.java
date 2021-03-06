package com.ksource.liangfa.domain;

import java.util.ArrayList;
import java.util.List;

public class WebForumExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table WEB_FORUM
	 * @mbggenerated  Fri Nov 02 09:21:40 CST 2012
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table WEB_FORUM
	 * @mbggenerated  Fri Nov 02 09:21:40 CST 2012
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table WEB_FORUM
	 * @mbggenerated  Fri Nov 02 09:21:40 CST 2012
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table WEB_FORUM
	 * @mbggenerated  Fri Nov 02 09:21:40 CST 2012
	 */
	public WebForumExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table WEB_FORUM
	 * @mbggenerated  Fri Nov 02 09:21:40 CST 2012
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table WEB_FORUM
	 * @mbggenerated  Fri Nov 02 09:21:40 CST 2012
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table WEB_FORUM
	 * @mbggenerated  Fri Nov 02 09:21:40 CST 2012
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table WEB_FORUM
	 * @mbggenerated  Fri Nov 02 09:21:40 CST 2012
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table WEB_FORUM
	 * @mbggenerated  Fri Nov 02 09:21:40 CST 2012
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table WEB_FORUM
	 * @mbggenerated  Fri Nov 02 09:21:40 CST 2012
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table WEB_FORUM
	 * @mbggenerated  Fri Nov 02 09:21:40 CST 2012
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table WEB_FORUM
	 * @mbggenerated  Fri Nov 02 09:21:40 CST 2012
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table WEB_FORUM
	 * @mbggenerated  Fri Nov 02 09:21:40 CST 2012
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table WEB_FORUM
	 * @mbggenerated  Fri Nov 02 09:21:40 CST 2012
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table WEB_FORUM
	 * @mbggenerated  Fri Nov 02 09:21:40 CST 2012
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

		public Criteria andForumIdIsNull() {
			addCriterion("FORUM_ID is null");
			return (Criteria) this;
		}

		public Criteria andForumIdIsNotNull() {
			addCriterion("FORUM_ID is not null");
			return (Criteria) this;
		}

		public Criteria andForumIdEqualTo(Integer value) {
			addCriterion("FORUM_ID =", value, "forumId");
			return (Criteria) this;
		}

		public Criteria andForumIdNotEqualTo(Integer value) {
			addCriterion("FORUM_ID <>", value, "forumId");
			return (Criteria) this;
		}

		public Criteria andForumIdGreaterThan(Integer value) {
			addCriterion("FORUM_ID >", value, "forumId");
			return (Criteria) this;
		}

		public Criteria andForumIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("FORUM_ID >=", value, "forumId");
			return (Criteria) this;
		}

		public Criteria andForumIdLessThan(Integer value) {
			addCriterion("FORUM_ID <", value, "forumId");
			return (Criteria) this;
		}

		public Criteria andForumIdLessThanOrEqualTo(Integer value) {
			addCriterion("FORUM_ID <=", value, "forumId");
			return (Criteria) this;
		}

		public Criteria andForumIdIn(List<Integer> values) {
			addCriterion("FORUM_ID in", values, "forumId");
			return (Criteria) this;
		}

		public Criteria andForumIdNotIn(List<Integer> values) {
			addCriterion("FORUM_ID not in", values, "forumId");
			return (Criteria) this;
		}

		public Criteria andForumIdBetween(Integer value1, Integer value2) {
			addCriterion("FORUM_ID between", value1, value2, "forumId");
			return (Criteria) this;
		}

		public Criteria andForumIdNotBetween(Integer value1, Integer value2) {
			addCriterion("FORUM_ID not between", value1, value2, "forumId");
			return (Criteria) this;
		}

		public Criteria andNavigationSortIsNull() {
			addCriterion("NAVIGATION_SORT is null");
			return (Criteria) this;
		}

		public Criteria andNavigationSortIsNotNull() {
			addCriterion("NAVIGATION_SORT is not null");
			return (Criteria) this;
		}

		public Criteria andNavigationSortEqualTo(Integer value) {
			addCriterion("NAVIGATION_SORT =", value, "navigationSort");
			return (Criteria) this;
		}

		public Criteria andNavigationSortNotEqualTo(Integer value) {
			addCriterion("NAVIGATION_SORT <>", value, "navigationSort");
			return (Criteria) this;
		}

		public Criteria andNavigationSortGreaterThan(Integer value) {
			addCriterion("NAVIGATION_SORT >", value, "navigationSort");
			return (Criteria) this;
		}

		public Criteria andNavigationSortGreaterThanOrEqualTo(Integer value) {
			addCriterion("NAVIGATION_SORT >=", value, "navigationSort");
			return (Criteria) this;
		}

		public Criteria andNavigationSortLessThan(Integer value) {
			addCriterion("NAVIGATION_SORT <", value, "navigationSort");
			return (Criteria) this;
		}

		public Criteria andNavigationSortLessThanOrEqualTo(Integer value) {
			addCriterion("NAVIGATION_SORT <=", value, "navigationSort");
			return (Criteria) this;
		}

		public Criteria andNavigationSortIn(List<Integer> values) {
			addCriterion("NAVIGATION_SORT in", values, "navigationSort");
			return (Criteria) this;
		}

		public Criteria andNavigationSortNotIn(List<Integer> values) {
			addCriterion("NAVIGATION_SORT not in", values, "navigationSort");
			return (Criteria) this;
		}

		public Criteria andNavigationSortBetween(Integer value1, Integer value2) {
			addCriterion("NAVIGATION_SORT between", value1, value2,
					"navigationSort");
			return (Criteria) this;
		}

		public Criteria andNavigationSortNotBetween(Integer value1,
				Integer value2) {
			addCriterion("NAVIGATION_SORT not between", value1, value2,
					"navigationSort");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table WEB_FORUM
	 * @mbggenerated  Fri Nov 02 09:21:40 CST 2012
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
     * This class corresponds to the database table WEB_FORUM
     *
     * @mbggenerated do_not_delete_during_merge Thu Nov 01 16:20:11 CST 2012
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
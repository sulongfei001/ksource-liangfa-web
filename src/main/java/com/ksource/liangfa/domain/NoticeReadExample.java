package com.ksource.liangfa.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoticeReadExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	public NoticeReadExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
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

		public Criteria andIdIsNull() {
			addCriterion("ID is null");
			return (Criteria) this;
		}

		public Criteria andIdIsNotNull() {
			addCriterion("ID is not null");
			return (Criteria) this;
		}

		public Criteria andIdEqualTo(Integer value) {
			addCriterion("ID =", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotEqualTo(Integer value) {
			addCriterion("ID <>", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThan(Integer value) {
			addCriterion("ID >", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("ID >=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThan(Integer value) {
			addCriterion("ID <", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThanOrEqualTo(Integer value) {
			addCriterion("ID <=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdIn(List<Integer> values) {
			addCriterion("ID in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotIn(List<Integer> values) {
			addCriterion("ID not in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdBetween(Integer value1, Integer value2) {
			addCriterion("ID between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotBetween(Integer value1, Integer value2) {
			addCriterion("ID not between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andNoticeIdIsNull() {
			addCriterion("NOTICE_ID is null");
			return (Criteria) this;
		}

		public Criteria andNoticeIdIsNotNull() {
			addCriterion("NOTICE_ID is not null");
			return (Criteria) this;
		}

		public Criteria andNoticeIdEqualTo(Integer value) {
			addCriterion("NOTICE_ID =", value, "noticeId");
			return (Criteria) this;
		}

		public Criteria andNoticeIdNotEqualTo(Integer value) {
			addCriterion("NOTICE_ID <>", value, "noticeId");
			return (Criteria) this;
		}

		public Criteria andNoticeIdGreaterThan(Integer value) {
			addCriterion("NOTICE_ID >", value, "noticeId");
			return (Criteria) this;
		}

		public Criteria andNoticeIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("NOTICE_ID >=", value, "noticeId");
			return (Criteria) this;
		}

		public Criteria andNoticeIdLessThan(Integer value) {
			addCriterion("NOTICE_ID <", value, "noticeId");
			return (Criteria) this;
		}

		public Criteria andNoticeIdLessThanOrEqualTo(Integer value) {
			addCriterion("NOTICE_ID <=", value, "noticeId");
			return (Criteria) this;
		}

		public Criteria andNoticeIdIn(List<Integer> values) {
			addCriterion("NOTICE_ID in", values, "noticeId");
			return (Criteria) this;
		}

		public Criteria andNoticeIdNotIn(List<Integer> values) {
			addCriterion("NOTICE_ID not in", values, "noticeId");
			return (Criteria) this;
		}

		public Criteria andNoticeIdBetween(Integer value1, Integer value2) {
			addCriterion("NOTICE_ID between", value1, value2, "noticeId");
			return (Criteria) this;
		}

		public Criteria andNoticeIdNotBetween(Integer value1, Integer value2) {
			addCriterion("NOTICE_ID not between", value1, value2, "noticeId");
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

		public Criteria andUserIdIsNull() {
			addCriterion("USER_ID is null");
			return (Criteria) this;
		}

		public Criteria andUserIdIsNotNull() {
			addCriterion("USER_ID is not null");
			return (Criteria) this;
		}

		public Criteria andUserIdEqualTo(String value) {
			addCriterion("USER_ID =", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotEqualTo(String value) {
			addCriterion("USER_ID <>", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdGreaterThan(String value) {
			addCriterion("USER_ID >", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdGreaterThanOrEqualTo(String value) {
			addCriterion("USER_ID >=", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdLessThan(String value) {
			addCriterion("USER_ID <", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdLessThanOrEqualTo(String value) {
			addCriterion("USER_ID <=", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdLike(String value) {
			addCriterion("USER_ID like", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotLike(String value) {
			addCriterion("USER_ID not like", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdIn(List<String> values) {
			addCriterion("USER_ID in", values, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotIn(List<String> values) {
			addCriterion("USER_ID not in", values, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdBetween(String value1, String value2) {
			addCriterion("USER_ID between", value1, value2, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotBetween(String value1, String value2) {
			addCriterion("USER_ID not between", value1, value2, "userId");
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
	 * This class was generated by MyBatis Generator. This class corresponds to the database table NOTICE_READ
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
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
     * This class corresponds to the database table NOTICE_READ
     *
     * @mbggenerated do_not_delete_during_merge Thu Oct 18 15:44:22 CST 2012
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
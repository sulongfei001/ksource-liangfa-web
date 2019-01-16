package com.ksource.liangfa.domain;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class AccuseInfoExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	public AccuseInfoExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
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

		public Criteria andNameIsNull() {
			addCriterion("NAME is null");
			return (Criteria) this;
		}

		public Criteria andNameIsNotNull() {
			addCriterion("NAME is not null");
			return (Criteria) this;
		}

		public Criteria andNameEqualTo(String value) {
			addCriterion("NAME =", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameNotEqualTo(String value) {
			addCriterion("NAME <>", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameGreaterThan(String value) {
			addCriterion("NAME >", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameGreaterThanOrEqualTo(String value) {
			addCriterion("NAME >=", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameLessThan(String value) {
			addCriterion("NAME <", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameLessThanOrEqualTo(String value) {
			addCriterion("NAME <=", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameLike(String value) {
			addCriterion("NAME like", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameNotLike(String value) {
			addCriterion("NAME not like", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameIn(List<String> values) {
			addCriterion("NAME in", values, "name");
			return (Criteria) this;
		}

		public Criteria andNameNotIn(List<String> values) {
			addCriterion("NAME not in", values, "name");
			return (Criteria) this;
		}

		public Criteria andNameBetween(String value1, String value2) {
			addCriterion("NAME between", value1, value2, "name");
			return (Criteria) this;
		}

		public Criteria andNameNotBetween(String value1, String value2) {
			addCriterion("NAME not between", value1, value2, "name");
			return (Criteria) this;
		}

		public Criteria andClauseIsNull() {
			addCriterion("CLAUSE is null");
			return (Criteria) this;
		}

		public Criteria andClauseIsNotNull() {
			addCriterion("CLAUSE is not null");
			return (Criteria) this;
		}

		public Criteria andClauseEqualTo(String value) {
			addCriterion("CLAUSE =", value, "clause");
			return (Criteria) this;
		}

		public Criteria andClauseNotEqualTo(String value) {
			addCriterion("CLAUSE <>", value, "clause");
			return (Criteria) this;
		}

		public Criteria andClauseGreaterThan(String value) {
			addCriterion("CLAUSE >", value, "clause");
			return (Criteria) this;
		}

		public Criteria andClauseGreaterThanOrEqualTo(String value) {
			addCriterion("CLAUSE >=", value, "clause");
			return (Criteria) this;
		}

		public Criteria andClauseLessThan(String value) {
			addCriterion("CLAUSE <", value, "clause");
			return (Criteria) this;
		}

		public Criteria andClauseLessThanOrEqualTo(String value) {
			addCriterion("CLAUSE <=", value, "clause");
			return (Criteria) this;
		}

		public Criteria andClauseLike(String value) {
			addCriterion("CLAUSE like", value, "clause");
			return (Criteria) this;
		}

		public Criteria andClauseNotLike(String value) {
			addCriterion("CLAUSE not like", value, "clause");
			return (Criteria) this;
		}

		public Criteria andClauseIn(List<String> values) {
			addCriterion("CLAUSE in", values, "clause");
			return (Criteria) this;
		}

		public Criteria andClauseNotIn(List<String> values) {
			addCriterion("CLAUSE not in", values, "clause");
			return (Criteria) this;
		}

		public Criteria andClauseBetween(String value1, String value2) {
			addCriterion("CLAUSE between", value1, value2, "clause");
			return (Criteria) this;
		}

		public Criteria andClauseNotBetween(String value1, String value2) {
			addCriterion("CLAUSE not between", value1, value2, "clause");
			return (Criteria) this;
		}

		public Criteria andInfoOrderIsNull() {
			addCriterion("INFO_ORDER is null");
			return (Criteria) this;
		}

		public Criteria andInfoOrderIsNotNull() {
			addCriterion("INFO_ORDER is not null");
			return (Criteria) this;
		}

		public Criteria andInfoOrderEqualTo(Integer value) {
			addCriterion("INFO_ORDER =", value, "infoOrder");
			return (Criteria) this;
		}

		public Criteria andInfoOrderNotEqualTo(Integer value) {
			addCriterion("INFO_ORDER <>", value, "infoOrder");
			return (Criteria) this;
		}

		public Criteria andInfoOrderGreaterThan(Integer value) {
			addCriterion("INFO_ORDER >", value, "infoOrder");
			return (Criteria) this;
		}

		public Criteria andInfoOrderGreaterThanOrEqualTo(Integer value) {
			addCriterion("INFO_ORDER >=", value, "infoOrder");
			return (Criteria) this;
		}

		public Criteria andInfoOrderLessThan(Integer value) {
			addCriterion("INFO_ORDER <", value, "infoOrder");
			return (Criteria) this;
		}

		public Criteria andInfoOrderLessThanOrEqualTo(Integer value) {
			addCriterion("INFO_ORDER <=", value, "infoOrder");
			return (Criteria) this;
		}

		public Criteria andInfoOrderIn(List<Integer> values) {
			addCriterion("INFO_ORDER in", values, "infoOrder");
			return (Criteria) this;
		}

		public Criteria andInfoOrderNotIn(List<Integer> values) {
			addCriterion("INFO_ORDER not in", values, "infoOrder");
			return (Criteria) this;
		}

		public Criteria andInfoOrderBetween(Integer value1, Integer value2) {
			addCriterion("INFO_ORDER between", value1, value2, "infoOrder");
			return (Criteria) this;
		}

		public Criteria andInfoOrderNotBetween(Integer value1, Integer value2) {
			addCriterion("INFO_ORDER not between", value1, value2, "infoOrder");
			return (Criteria) this;
		}

		public Criteria andAccuseId1IsNull() {
			addCriterion("ACCUSE_ID1 is null");
			return (Criteria) this;
		}

		public Criteria andAccuseId1IsNotNull() {
			addCriterion("ACCUSE_ID1 is not null");
			return (Criteria) this;
		}

		public Criteria andAccuseId1EqualTo(Integer value) {
			addCriterion("ACCUSE_ID1 =", value, "accuseId1");
			return (Criteria) this;
		}

		public Criteria andAccuseId1NotEqualTo(Integer value) {
			addCriterion("ACCUSE_ID1 <>", value, "accuseId1");
			return (Criteria) this;
		}

		public Criteria andAccuseId1GreaterThan(Integer value) {
			addCriterion("ACCUSE_ID1 >", value, "accuseId1");
			return (Criteria) this;
		}

		public Criteria andAccuseId1GreaterThanOrEqualTo(Integer value) {
			addCriterion("ACCUSE_ID1 >=", value, "accuseId1");
			return (Criteria) this;
		}

		public Criteria andAccuseId1LessThan(Integer value) {
			addCriterion("ACCUSE_ID1 <", value, "accuseId1");
			return (Criteria) this;
		}

		public Criteria andAccuseId1LessThanOrEqualTo(Integer value) {
			addCriterion("ACCUSE_ID1 <=", value, "accuseId1");
			return (Criteria) this;
		}

		public Criteria andAccuseId1In(List<Integer> values) {
			addCriterion("ACCUSE_ID1 in", values, "accuseId1");
			return (Criteria) this;
		}

		public Criteria andAccuseId1NotIn(List<Integer> values) {
			addCriterion("ACCUSE_ID1 not in", values, "accuseId1");
			return (Criteria) this;
		}

		public Criteria andAccuseId1Between(Integer value1, Integer value2) {
			addCriterion("ACCUSE_ID1 between", value1, value2, "accuseId1");
			return (Criteria) this;
		}

		public Criteria andAccuseId1NotBetween(Integer value1, Integer value2) {
			addCriterion("ACCUSE_ID1 not between", value1, value2, "accuseId1");
			return (Criteria) this;
		}

		public Criteria andAccuseId2IsNull() {
			addCriterion("ACCUSE_ID2 is null");
			return (Criteria) this;
		}

		public Criteria andAccuseId2IsNotNull() {
			addCriterion("ACCUSE_ID2 is not null");
			return (Criteria) this;
		}

		public Criteria andAccuseId2EqualTo(Integer value) {
			addCriterion("ACCUSE_ID2 =", value, "accuseId2");
			return (Criteria) this;
		}

		public Criteria andAccuseId2NotEqualTo(Integer value) {
			addCriterion("ACCUSE_ID2 <>", value, "accuseId2");
			return (Criteria) this;
		}

		public Criteria andAccuseId2GreaterThan(Integer value) {
			addCriterion("ACCUSE_ID2 >", value, "accuseId2");
			return (Criteria) this;
		}

		public Criteria andAccuseId2GreaterThanOrEqualTo(Integer value) {
			addCriterion("ACCUSE_ID2 >=", value, "accuseId2");
			return (Criteria) this;
		}

		public Criteria andAccuseId2LessThan(Integer value) {
			addCriterion("ACCUSE_ID2 <", value, "accuseId2");
			return (Criteria) this;
		}

		public Criteria andAccuseId2LessThanOrEqualTo(Integer value) {
			addCriterion("ACCUSE_ID2 <=", value, "accuseId2");
			return (Criteria) this;
		}

		public Criteria andAccuseId2In(List<Integer> values) {
			addCriterion("ACCUSE_ID2 in", values, "accuseId2");
			return (Criteria) this;
		}

		public Criteria andAccuseId2NotIn(List<Integer> values) {
			addCriterion("ACCUSE_ID2 not in", values, "accuseId2");
			return (Criteria) this;
		}

		public Criteria andAccuseId2Between(Integer value1, Integer value2) {
			addCriterion("ACCUSE_ID2 between", value1, value2, "accuseId2");
			return (Criteria) this;
		}

		public Criteria andAccuseId2NotBetween(Integer value1, Integer value2) {
			addCriterion("ACCUSE_ID2 not between", value1, value2, "accuseId2");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
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
     * This class corresponds to the database table ACCUSE_INFO
     *
     * @mbggenerated do_not_delete_during_merge Thu Jul 19 17:20:05 CST 2012
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
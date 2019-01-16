package com.ksource.liangfa.domain;

import java.util.ArrayList;
import java.util.List;

public class CaseStateExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	public CaseStateExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
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

		public Criteria andCaseIdIsNull() {
			addCriterion("CASE_ID is null");
			return (Criteria) this;
		}

		public Criteria andCaseIdIsNotNull() {
			addCriterion("CASE_ID is not null");
			return (Criteria) this;
		}

		public Criteria andCaseIdEqualTo(String value) {
			addCriterion("CASE_ID =", value, "caseId");
			return (Criteria) this;
		}

		public Criteria andCaseIdNotEqualTo(String value) {
			addCriterion("CASE_ID <>", value, "caseId");
			return (Criteria) this;
		}

		public Criteria andCaseIdGreaterThan(String value) {
			addCriterion("CASE_ID >", value, "caseId");
			return (Criteria) this;
		}

		public Criteria andCaseIdGreaterThanOrEqualTo(String value) {
			addCriterion("CASE_ID >=", value, "caseId");
			return (Criteria) this;
		}

		public Criteria andCaseIdLessThan(String value) {
			addCriterion("CASE_ID <", value, "caseId");
			return (Criteria) this;
		}

		public Criteria andCaseIdLessThanOrEqualTo(String value) {
			addCriterion("CASE_ID <=", value, "caseId");
			return (Criteria) this;
		}

		public Criteria andCaseIdLike(String value) {
			addCriterion("CASE_ID like", value, "caseId");
			return (Criteria) this;
		}

		public Criteria andCaseIdNotLike(String value) {
			addCriterion("CASE_ID not like", value, "caseId");
			return (Criteria) this;
		}

		public Criteria andCaseIdIn(List<String> values) {
			addCriterion("CASE_ID in", values, "caseId");
			return (Criteria) this;
		}

		public Criteria andCaseIdNotIn(List<String> values) {
			addCriterion("CASE_ID not in", values, "caseId");
			return (Criteria) this;
		}

		public Criteria andCaseIdBetween(String value1, String value2) {
			addCriterion("CASE_ID between", value1, value2, "caseId");
			return (Criteria) this;
		}

		public Criteria andCaseIdNotBetween(String value1, String value2) {
			addCriterion("CASE_ID not between", value1, value2, "caseId");
			return (Criteria) this;
		}

		public Criteria andXingzhenglianStateIsNull() {
			addCriterion("XINGZHENGLIAN_STATE is null");
			return (Criteria) this;
		}

		public Criteria andXingzhenglianStateIsNotNull() {
			addCriterion("XINGZHENGLIAN_STATE is not null");
			return (Criteria) this;
		}

		public Criteria andXingzhenglianStateEqualTo(Integer value) {
			addCriterion("XINGZHENGLIAN_STATE =", value, "xingzhenglianState");
			return (Criteria) this;
		}

		public Criteria andXingzhenglianStateNotEqualTo(Integer value) {
			addCriterion("XINGZHENGLIAN_STATE <>", value, "xingzhenglianState");
			return (Criteria) this;
		}

		public Criteria andXingzhenglianStateGreaterThan(Integer value) {
			addCriterion("XINGZHENGLIAN_STATE >", value, "xingzhenglianState");
			return (Criteria) this;
		}

		public Criteria andXingzhenglianStateGreaterThanOrEqualTo(Integer value) {
			addCriterion("XINGZHENGLIAN_STATE >=", value, "xingzhenglianState");
			return (Criteria) this;
		}

		public Criteria andXingzhenglianStateLessThan(Integer value) {
			addCriterion("XINGZHENGLIAN_STATE <", value, "xingzhenglianState");
			return (Criteria) this;
		}

		public Criteria andXingzhenglianStateLessThanOrEqualTo(Integer value) {
			addCriterion("XINGZHENGLIAN_STATE <=", value, "xingzhenglianState");
			return (Criteria) this;
		}

		public Criteria andXingzhenglianStateIn(List<Integer> values) {
			addCriterion("XINGZHENGLIAN_STATE in", values, "xingzhenglianState");
			return (Criteria) this;
		}

		public Criteria andXingzhenglianStateNotIn(List<Integer> values) {
			addCriterion("XINGZHENGLIAN_STATE not in", values,
					"xingzhenglianState");
			return (Criteria) this;
		}

		public Criteria andXingzhenglianStateBetween(Integer value1,
				Integer value2) {
			addCriterion("XINGZHENGLIAN_STATE between", value1, value2,
					"xingzhenglianState");
			return (Criteria) this;
		}

		public Criteria andXingzhenglianStateNotBetween(Integer value1,
				Integer value2) {
			addCriterion("XINGZHENGLIAN_STATE not between", value1, value2,
					"xingzhenglianState");
			return (Criteria) this;
		}

		public Criteria andChufaStateIsNull() {
			addCriterion("CHUFA_STATE is null");
			return (Criteria) this;
		}

		public Criteria andChufaStateIsNotNull() {
			addCriterion("CHUFA_STATE is not null");
			return (Criteria) this;
		}

		public Criteria andChufaStateEqualTo(Integer value) {
			addCriterion("CHUFA_STATE =", value, "chufaState");
			return (Criteria) this;
		}

		public Criteria andChufaStateNotEqualTo(Integer value) {
			addCriterion("CHUFA_STATE <>", value, "chufaState");
			return (Criteria) this;
		}

		public Criteria andChufaStateGreaterThan(Integer value) {
			addCriterion("CHUFA_STATE >", value, "chufaState");
			return (Criteria) this;
		}

		public Criteria andChufaStateGreaterThanOrEqualTo(Integer value) {
			addCriterion("CHUFA_STATE >=", value, "chufaState");
			return (Criteria) this;
		}

		public Criteria andChufaStateLessThan(Integer value) {
			addCriterion("CHUFA_STATE <", value, "chufaState");
			return (Criteria) this;
		}

		public Criteria andChufaStateLessThanOrEqualTo(Integer value) {
			addCriterion("CHUFA_STATE <=", value, "chufaState");
			return (Criteria) this;
		}

		public Criteria andChufaStateIn(List<Integer> values) {
			addCriterion("CHUFA_STATE in", values, "chufaState");
			return (Criteria) this;
		}

		public Criteria andChufaStateNotIn(List<Integer> values) {
			addCriterion("CHUFA_STATE not in", values, "chufaState");
			return (Criteria) this;
		}

		public Criteria andChufaStateBetween(Integer value1, Integer value2) {
			addCriterion("CHUFA_STATE between", value1, value2, "chufaState");
			return (Criteria) this;
		}

		public Criteria andChufaStateNotBetween(Integer value1, Integer value2) {
			addCriterion("CHUFA_STATE not between", value1, value2,
					"chufaState");
			return (Criteria) this;
		}

		public Criteria andYisongStateIsNull() {
			addCriterion("YISONG_STATE is null");
			return (Criteria) this;
		}

		public Criteria andYisongStateIsNotNull() {
			addCriterion("YISONG_STATE is not null");
			return (Criteria) this;
		}

		public Criteria andYisongStateEqualTo(Integer value) {
			addCriterion("YISONG_STATE =", value, "yisongState");
			return (Criteria) this;
		}

		public Criteria andYisongStateNotEqualTo(Integer value) {
			addCriterion("YISONG_STATE <>", value, "yisongState");
			return (Criteria) this;
		}

		public Criteria andYisongStateGreaterThan(Integer value) {
			addCriterion("YISONG_STATE >", value, "yisongState");
			return (Criteria) this;
		}

		public Criteria andYisongStateGreaterThanOrEqualTo(Integer value) {
			addCriterion("YISONG_STATE >=", value, "yisongState");
			return (Criteria) this;
		}

		public Criteria andYisongStateLessThan(Integer value) {
			addCriterion("YISONG_STATE <", value, "yisongState");
			return (Criteria) this;
		}

		public Criteria andYisongStateLessThanOrEqualTo(Integer value) {
			addCriterion("YISONG_STATE <=", value, "yisongState");
			return (Criteria) this;
		}

		public Criteria andYisongStateIn(List<Integer> values) {
			addCriterion("YISONG_STATE in", values, "yisongState");
			return (Criteria) this;
		}

		public Criteria andYisongStateNotIn(List<Integer> values) {
			addCriterion("YISONG_STATE not in", values, "yisongState");
			return (Criteria) this;
		}

		public Criteria andYisongStateBetween(Integer value1, Integer value2) {
			addCriterion("YISONG_STATE between", value1, value2, "yisongState");
			return (Criteria) this;
		}

		public Criteria andYisongStateNotBetween(Integer value1, Integer value2) {
			addCriterion("YISONG_STATE not between", value1, value2,
					"yisongState");
			return (Criteria) this;
		}

		public Criteria andLianStateIsNull() {
			addCriterion("LIAN_STATE is null");
			return (Criteria) this;
		}

		public Criteria andLianStateIsNotNull() {
			addCriterion("LIAN_STATE is not null");
			return (Criteria) this;
		}

		public Criteria andLianStateEqualTo(Integer value) {
			addCriterion("LIAN_STATE =", value, "lianState");
			return (Criteria) this;
		}

		public Criteria andLianStateNotEqualTo(Integer value) {
			addCriterion("LIAN_STATE <>", value, "lianState");
			return (Criteria) this;
		}

		public Criteria andLianStateGreaterThan(Integer value) {
			addCriterion("LIAN_STATE >", value, "lianState");
			return (Criteria) this;
		}

		public Criteria andLianStateGreaterThanOrEqualTo(Integer value) {
			addCriterion("LIAN_STATE >=", value, "lianState");
			return (Criteria) this;
		}

		public Criteria andLianStateLessThan(Integer value) {
			addCriterion("LIAN_STATE <", value, "lianState");
			return (Criteria) this;
		}

		public Criteria andLianStateLessThanOrEqualTo(Integer value) {
			addCriterion("LIAN_STATE <=", value, "lianState");
			return (Criteria) this;
		}

		public Criteria andLianStateIn(List<Integer> values) {
			addCriterion("LIAN_STATE in", values, "lianState");
			return (Criteria) this;
		}

		public Criteria andLianStateNotIn(List<Integer> values) {
			addCriterion("LIAN_STATE not in", values, "lianState");
			return (Criteria) this;
		}

		public Criteria andLianStateBetween(Integer value1, Integer value2) {
			addCriterion("LIAN_STATE between", value1, value2, "lianState");
			return (Criteria) this;
		}

		public Criteria andLianStateNotBetween(Integer value1, Integer value2) {
			addCriterion("LIAN_STATE not between", value1, value2, "lianState");
			return (Criteria) this;
		}

		public Criteria andDaibuStateIsNull() {
			addCriterion("DAIBU_STATE is null");
			return (Criteria) this;
		}

		public Criteria andDaibuStateIsNotNull() {
			addCriterion("DAIBU_STATE is not null");
			return (Criteria) this;
		}

		public Criteria andDaibuStateEqualTo(Integer value) {
			addCriterion("DAIBU_STATE =", value, "daibuState");
			return (Criteria) this;
		}

		public Criteria andDaibuStateNotEqualTo(Integer value) {
			addCriterion("DAIBU_STATE <>", value, "daibuState");
			return (Criteria) this;
		}

		public Criteria andDaibuStateGreaterThan(Integer value) {
			addCriterion("DAIBU_STATE >", value, "daibuState");
			return (Criteria) this;
		}

		public Criteria andDaibuStateGreaterThanOrEqualTo(Integer value) {
			addCriterion("DAIBU_STATE >=", value, "daibuState");
			return (Criteria) this;
		}

		public Criteria andDaibuStateLessThan(Integer value) {
			addCriterion("DAIBU_STATE <", value, "daibuState");
			return (Criteria) this;
		}

		public Criteria andDaibuStateLessThanOrEqualTo(Integer value) {
			addCriterion("DAIBU_STATE <=", value, "daibuState");
			return (Criteria) this;
		}

		public Criteria andDaibuStateIn(List<Integer> values) {
			addCriterion("DAIBU_STATE in", values, "daibuState");
			return (Criteria) this;
		}

		public Criteria andDaibuStateNotIn(List<Integer> values) {
			addCriterion("DAIBU_STATE not in", values, "daibuState");
			return (Criteria) this;
		}

		public Criteria andDaibuStateBetween(Integer value1, Integer value2) {
			addCriterion("DAIBU_STATE between", value1, value2, "daibuState");
			return (Criteria) this;
		}

		public Criteria andDaibuStateNotBetween(Integer value1, Integer value2) {
			addCriterion("DAIBU_STATE not between", value1, value2,
					"daibuState");
			return (Criteria) this;
		}

		public Criteria andQisuStateIsNull() {
			addCriterion("QISU_STATE is null");
			return (Criteria) this;
		}

		public Criteria andQisuStateIsNotNull() {
			addCriterion("QISU_STATE is not null");
			return (Criteria) this;
		}

		public Criteria andQisuStateEqualTo(Integer value) {
			addCriterion("QISU_STATE =", value, "qisuState");
			return (Criteria) this;
		}

		public Criteria andQisuStateNotEqualTo(Integer value) {
			addCriterion("QISU_STATE <>", value, "qisuState");
			return (Criteria) this;
		}

		public Criteria andQisuStateGreaterThan(Integer value) {
			addCriterion("QISU_STATE >", value, "qisuState");
			return (Criteria) this;
		}

		public Criteria andQisuStateGreaterThanOrEqualTo(Integer value) {
			addCriterion("QISU_STATE >=", value, "qisuState");
			return (Criteria) this;
		}

		public Criteria andQisuStateLessThan(Integer value) {
			addCriterion("QISU_STATE <", value, "qisuState");
			return (Criteria) this;
		}

		public Criteria andQisuStateLessThanOrEqualTo(Integer value) {
			addCriterion("QISU_STATE <=", value, "qisuState");
			return (Criteria) this;
		}

		public Criteria andQisuStateIn(List<Integer> values) {
			addCriterion("QISU_STATE in", values, "qisuState");
			return (Criteria) this;
		}

		public Criteria andQisuStateNotIn(List<Integer> values) {
			addCriterion("QISU_STATE not in", values, "qisuState");
			return (Criteria) this;
		}

		public Criteria andQisuStateBetween(Integer value1, Integer value2) {
			addCriterion("QISU_STATE between", value1, value2, "qisuState");
			return (Criteria) this;
		}

		public Criteria andQisuStateNotBetween(Integer value1, Integer value2) {
			addCriterion("QISU_STATE not between", value1, value2, "qisuState");
			return (Criteria) this;
		}

		public Criteria andPanjueStateIsNull() {
			addCriterion("PANJUE_STATE is null");
			return (Criteria) this;
		}

		public Criteria andPanjueStateIsNotNull() {
			addCriterion("PANJUE_STATE is not null");
			return (Criteria) this;
		}

		public Criteria andPanjueStateEqualTo(Integer value) {
			addCriterion("PANJUE_STATE =", value, "panjueState");
			return (Criteria) this;
		}

		public Criteria andPanjueStateNotEqualTo(Integer value) {
			addCriterion("PANJUE_STATE <>", value, "panjueState");
			return (Criteria) this;
		}

		public Criteria andPanjueStateGreaterThan(Integer value) {
			addCriterion("PANJUE_STATE >", value, "panjueState");
			return (Criteria) this;
		}

		public Criteria andPanjueStateGreaterThanOrEqualTo(Integer value) {
			addCriterion("PANJUE_STATE >=", value, "panjueState");
			return (Criteria) this;
		}

		public Criteria andPanjueStateLessThan(Integer value) {
			addCriterion("PANJUE_STATE <", value, "panjueState");
			return (Criteria) this;
		}

		public Criteria andPanjueStateLessThanOrEqualTo(Integer value) {
			addCriterion("PANJUE_STATE <=", value, "panjueState");
			return (Criteria) this;
		}

		public Criteria andPanjueStateIn(List<Integer> values) {
			addCriterion("PANJUE_STATE in", values, "panjueState");
			return (Criteria) this;
		}

		public Criteria andPanjueStateNotIn(List<Integer> values) {
			addCriterion("PANJUE_STATE not in", values, "panjueState");
			return (Criteria) this;
		}

		public Criteria andPanjueStateBetween(Integer value1, Integer value2) {
			addCriterion("PANJUE_STATE between", value1, value2, "panjueState");
			return (Criteria) this;
		}

		public Criteria andPanjueStateNotBetween(Integer value1, Integer value2) {
			addCriterion("PANJUE_STATE not between", value1, value2,
					"panjueState");
			return (Criteria) this;
		}

		public Criteria andJieanStateIsNull() {
			addCriterion("JIEAN_STATE is null");
			return (Criteria) this;
		}

		public Criteria andJieanStateIsNotNull() {
			addCriterion("JIEAN_STATE is not null");
			return (Criteria) this;
		}

		public Criteria andJieanStateEqualTo(Integer value) {
			addCriterion("JIEAN_STATE =", value, "jieanState");
			return (Criteria) this;
		}

		public Criteria andJieanStateNotEqualTo(Integer value) {
			addCriterion("JIEAN_STATE <>", value, "jieanState");
			return (Criteria) this;
		}

		public Criteria andJieanStateGreaterThan(Integer value) {
			addCriterion("JIEAN_STATE >", value, "jieanState");
			return (Criteria) this;
		}

		public Criteria andJieanStateGreaterThanOrEqualTo(Integer value) {
			addCriterion("JIEAN_STATE >=", value, "jieanState");
			return (Criteria) this;
		}

		public Criteria andJieanStateLessThan(Integer value) {
			addCriterion("JIEAN_STATE <", value, "jieanState");
			return (Criteria) this;
		}

		public Criteria andJieanStateLessThanOrEqualTo(Integer value) {
			addCriterion("JIEAN_STATE <=", value, "jieanState");
			return (Criteria) this;
		}

		public Criteria andJieanStateIn(List<Integer> values) {
			addCriterion("JIEAN_STATE in", values, "jieanState");
			return (Criteria) this;
		}

		public Criteria andJieanStateNotIn(List<Integer> values) {
			addCriterion("JIEAN_STATE not in", values, "jieanState");
			return (Criteria) this;
		}

		public Criteria andJieanStateBetween(Integer value1, Integer value2) {
			addCriterion("JIEAN_STATE between", value1, value2, "jieanState");
			return (Criteria) this;
		}

		public Criteria andJieanStateNotBetween(Integer value1, Integer value2) {
			addCriterion("JIEAN_STATE not between", value1, value2,
					"jieanState");
			return (Criteria) this;
		}

		public Criteria andReqExplainStateIsNull() {
			addCriterion("REQ_EXPLAIN_STATE is null");
			return (Criteria) this;
		}

		public Criteria andReqExplainStateIsNotNull() {
			addCriterion("REQ_EXPLAIN_STATE is not null");
			return (Criteria) this;
		}

		public Criteria andReqExplainStateEqualTo(Integer value) {
			addCriterion("REQ_EXPLAIN_STATE =", value, "reqExplainState");
			return (Criteria) this;
		}

		public Criteria andReqExplainStateNotEqualTo(Integer value) {
			addCriterion("REQ_EXPLAIN_STATE <>", value, "reqExplainState");
			return (Criteria) this;
		}

		public Criteria andReqExplainStateGreaterThan(Integer value) {
			addCriterion("REQ_EXPLAIN_STATE >", value, "reqExplainState");
			return (Criteria) this;
		}

		public Criteria andReqExplainStateGreaterThanOrEqualTo(Integer value) {
			addCriterion("REQ_EXPLAIN_STATE >=", value, "reqExplainState");
			return (Criteria) this;
		}

		public Criteria andReqExplainStateLessThan(Integer value) {
			addCriterion("REQ_EXPLAIN_STATE <", value, "reqExplainState");
			return (Criteria) this;
		}

		public Criteria andReqExplainStateLessThanOrEqualTo(Integer value) {
			addCriterion("REQ_EXPLAIN_STATE <=", value, "reqExplainState");
			return (Criteria) this;
		}

		public Criteria andReqExplainStateIn(List<Integer> values) {
			addCriterion("REQ_EXPLAIN_STATE in", values, "reqExplainState");
			return (Criteria) this;
		}

		public Criteria andReqExplainStateNotIn(List<Integer> values) {
			addCriterion("REQ_EXPLAIN_STATE not in", values, "reqExplainState");
			return (Criteria) this;
		}

		public Criteria andReqExplainStateBetween(Integer value1, Integer value2) {
			addCriterion("REQ_EXPLAIN_STATE between", value1, value2,
					"reqExplainState");
			return (Criteria) this;
		}

		public Criteria andReqExplainStateNotBetween(Integer value1,
				Integer value2) {
			addCriterion("REQ_EXPLAIN_STATE not between", value1, value2,
					"reqExplainState");
			return (Criteria) this;
		}

		public Criteria andExplainStateIsNull() {
			addCriterion("EXPLAIN_STATE is null");
			return (Criteria) this;
		}

		public Criteria andExplainStateIsNotNull() {
			addCriterion("EXPLAIN_STATE is not null");
			return (Criteria) this;
		}

		public Criteria andExplainStateEqualTo(Integer value) {
			addCriterion("EXPLAIN_STATE =", value, "explainState");
			return (Criteria) this;
		}

		public Criteria andExplainStateNotEqualTo(Integer value) {
			addCriterion("EXPLAIN_STATE <>", value, "explainState");
			return (Criteria) this;
		}

		public Criteria andExplainStateGreaterThan(Integer value) {
			addCriterion("EXPLAIN_STATE >", value, "explainState");
			return (Criteria) this;
		}

		public Criteria andExplainStateGreaterThanOrEqualTo(Integer value) {
			addCriterion("EXPLAIN_STATE >=", value, "explainState");
			return (Criteria) this;
		}

		public Criteria andExplainStateLessThan(Integer value) {
			addCriterion("EXPLAIN_STATE <", value, "explainState");
			return (Criteria) this;
		}

		public Criteria andExplainStateLessThanOrEqualTo(Integer value) {
			addCriterion("EXPLAIN_STATE <=", value, "explainState");
			return (Criteria) this;
		}

		public Criteria andExplainStateIn(List<Integer> values) {
			addCriterion("EXPLAIN_STATE in", values, "explainState");
			return (Criteria) this;
		}

		public Criteria andExplainStateNotIn(List<Integer> values) {
			addCriterion("EXPLAIN_STATE not in", values, "explainState");
			return (Criteria) this;
		}

		public Criteria andExplainStateBetween(Integer value1, Integer value2) {
			addCriterion("EXPLAIN_STATE between", value1, value2,
					"explainState");
			return (Criteria) this;
		}

		public Criteria andExplainStateNotBetween(Integer value1, Integer value2) {
			addCriterion("EXPLAIN_STATE not between", value1, value2,
					"explainState");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
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
     * This class corresponds to the database table CASE_STATE
     *
     * @mbggenerated do_not_delete_during_merge Wed Feb 27 08:51:06 CST 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
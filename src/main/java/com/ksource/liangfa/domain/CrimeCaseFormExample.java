package com.ksource.liangfa.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrimeCaseFormExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_XINXIANG20170424.CRIME_CASE_FORM
	 * @mbggenerated  Tue May 09 14:58:02 CST 2017
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_XINXIANG20170424.CRIME_CASE_FORM
	 * @mbggenerated  Tue May 09 14:58:02 CST 2017
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_XINXIANG20170424.CRIME_CASE_FORM
	 * @mbggenerated  Tue May 09 14:58:02 CST 2017
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170424.CRIME_CASE_FORM
	 * @mbggenerated  Tue May 09 14:58:02 CST 2017
	 */
	public CrimeCaseFormExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170424.CRIME_CASE_FORM
	 * @mbggenerated  Tue May 09 14:58:02 CST 2017
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170424.CRIME_CASE_FORM
	 * @mbggenerated  Tue May 09 14:58:02 CST 2017
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170424.CRIME_CASE_FORM
	 * @mbggenerated  Tue May 09 14:58:02 CST 2017
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170424.CRIME_CASE_FORM
	 * @mbggenerated  Tue May 09 14:58:02 CST 2017
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170424.CRIME_CASE_FORM
	 * @mbggenerated  Tue May 09 14:58:02 CST 2017
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170424.CRIME_CASE_FORM
	 * @mbggenerated  Tue May 09 14:58:02 CST 2017
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170424.CRIME_CASE_FORM
	 * @mbggenerated  Tue May 09 14:58:02 CST 2017
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170424.CRIME_CASE_FORM
	 * @mbggenerated  Tue May 09 14:58:02 CST 2017
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170424.CRIME_CASE_FORM
	 * @mbggenerated  Tue May 09 14:58:02 CST 2017
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170424.CRIME_CASE_FORM
	 * @mbggenerated  Tue May 09 14:58:02 CST 2017
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table LIANGFA_XINXIANG20170424.CRIME_CASE_FORM
	 * @mbggenerated  Tue May 09 14:58:02 CST 2017
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

		public Criteria andAcceptOrgIsNull() {
			addCriterion("ACCEPT_ORG is null");
			return (Criteria) this;
		}

		public Criteria andAcceptOrgIsNotNull() {
			addCriterion("ACCEPT_ORG is not null");
			return (Criteria) this;
		}

		public Criteria andAcceptOrgEqualTo(Integer value) {
			addCriterion("ACCEPT_ORG =", value, "acceptOrg");
			return (Criteria) this;
		}

		public Criteria andAcceptOrgNotEqualTo(Integer value) {
			addCriterion("ACCEPT_ORG <>", value, "acceptOrg");
			return (Criteria) this;
		}

		public Criteria andAcceptOrgGreaterThan(Integer value) {
			addCriterion("ACCEPT_ORG >", value, "acceptOrg");
			return (Criteria) this;
		}

		public Criteria andAcceptOrgGreaterThanOrEqualTo(Integer value) {
			addCriterion("ACCEPT_ORG >=", value, "acceptOrg");
			return (Criteria) this;
		}

		public Criteria andAcceptOrgLessThan(Integer value) {
			addCriterion("ACCEPT_ORG <", value, "acceptOrg");
			return (Criteria) this;
		}

		public Criteria andAcceptOrgLessThanOrEqualTo(Integer value) {
			addCriterion("ACCEPT_ORG <=", value, "acceptOrg");
			return (Criteria) this;
		}

		public Criteria andAcceptOrgIn(List<Integer> values) {
			addCriterion("ACCEPT_ORG in", values, "acceptOrg");
			return (Criteria) this;
		}

		public Criteria andAcceptOrgNotIn(List<Integer> values) {
			addCriterion("ACCEPT_ORG not in", values, "acceptOrg");
			return (Criteria) this;
		}

		public Criteria andAcceptOrgBetween(Integer value1, Integer value2) {
			addCriterion("ACCEPT_ORG between", value1, value2, "acceptOrg");
			return (Criteria) this;
		}

		public Criteria andAcceptOrgNotBetween(Integer value1, Integer value2) {
			addCriterion("ACCEPT_ORG not between", value1, value2, "acceptOrg");
			return (Criteria) this;
		}

		public Criteria andYisongNoIsNull() {
			addCriterion("YISONG_NO is null");
			return (Criteria) this;
		}

		public Criteria andYisongNoIsNotNull() {
			addCriterion("YISONG_NO is not null");
			return (Criteria) this;
		}

		public Criteria andYisongNoEqualTo(String value) {
			addCriterion("YISONG_NO =", value, "yisongNo");
			return (Criteria) this;
		}

		public Criteria andYisongNoNotEqualTo(String value) {
			addCriterion("YISONG_NO <>", value, "yisongNo");
			return (Criteria) this;
		}

		public Criteria andYisongNoGreaterThan(String value) {
			addCriterion("YISONG_NO >", value, "yisongNo");
			return (Criteria) this;
		}

		public Criteria andYisongNoGreaterThanOrEqualTo(String value) {
			addCriterion("YISONG_NO >=", value, "yisongNo");
			return (Criteria) this;
		}

		public Criteria andYisongNoLessThan(String value) {
			addCriterion("YISONG_NO <", value, "yisongNo");
			return (Criteria) this;
		}

		public Criteria andYisongNoLessThanOrEqualTo(String value) {
			addCriterion("YISONG_NO <=", value, "yisongNo");
			return (Criteria) this;
		}

		public Criteria andYisongNoLike(String value) {
			addCriterion("YISONG_NO like", value, "yisongNo");
			return (Criteria) this;
		}

		public Criteria andYisongNoNotLike(String value) {
			addCriterion("YISONG_NO not like", value, "yisongNo");
			return (Criteria) this;
		}

		public Criteria andYisongNoIn(List<String> values) {
			addCriterion("YISONG_NO in", values, "yisongNo");
			return (Criteria) this;
		}

		public Criteria andYisongNoNotIn(List<String> values) {
			addCriterion("YISONG_NO not in", values, "yisongNo");
			return (Criteria) this;
		}

		public Criteria andYisongNoBetween(String value1, String value2) {
			addCriterion("YISONG_NO between", value1, value2, "yisongNo");
			return (Criteria) this;
		}

		public Criteria andYisongNoNotBetween(String value1, String value2) {
			addCriterion("YISONG_NO not between", value1, value2, "yisongNo");
			return (Criteria) this;
		}

		public Criteria andYisongTimeIsNull() {
			addCriterion("YISONG_TIME is null");
			return (Criteria) this;
		}

		public Criteria andYisongTimeIsNotNull() {
			addCriterion("YISONG_TIME is not null");
			return (Criteria) this;
		}

		public Criteria andYisongTimeEqualTo(Date value) {
			addCriterion("YISONG_TIME =", value, "yisongTime");
			return (Criteria) this;
		}

		public Criteria andYisongTimeNotEqualTo(Date value) {
			addCriterion("YISONG_TIME <>", value, "yisongTime");
			return (Criteria) this;
		}

		public Criteria andYisongTimeGreaterThan(Date value) {
			addCriterion("YISONG_TIME >", value, "yisongTime");
			return (Criteria) this;
		}

		public Criteria andYisongTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("YISONG_TIME >=", value, "yisongTime");
			return (Criteria) this;
		}

		public Criteria andYisongTimeLessThan(Date value) {
			addCriterion("YISONG_TIME <", value, "yisongTime");
			return (Criteria) this;
		}

		public Criteria andYisongTimeLessThanOrEqualTo(Date value) {
			addCriterion("YISONG_TIME <=", value, "yisongTime");
			return (Criteria) this;
		}

		public Criteria andYisongTimeIn(List<Date> values) {
			addCriterion("YISONG_TIME in", values, "yisongTime");
			return (Criteria) this;
		}

		public Criteria andYisongTimeNotIn(List<Date> values) {
			addCriterion("YISONG_TIME not in", values, "yisongTime");
			return (Criteria) this;
		}

		public Criteria andYisongTimeBetween(Date value1, Date value2) {
			addCriterion("YISONG_TIME between", value1, value2, "yisongTime");
			return (Criteria) this;
		}

		public Criteria andYisongTimeNotBetween(Date value1, Date value2) {
			addCriterion("YISONG_TIME not between", value1, value2,
					"yisongTime");
			return (Criteria) this;
		}

		public Criteria andYisongFileIsNull() {
			addCriterion("YISONG_FILE is null");
			return (Criteria) this;
		}

		public Criteria andYisongFileIsNotNull() {
			addCriterion("YISONG_FILE is not null");
			return (Criteria) this;
		}

		public Criteria andYisongFileEqualTo(Integer value) {
			addCriterion("YISONG_FILE =", value, "yisongFile");
			return (Criteria) this;
		}

		public Criteria andYisongFileNotEqualTo(Integer value) {
			addCriterion("YISONG_FILE <>", value, "yisongFile");
			return (Criteria) this;
		}

		public Criteria andYisongFileGreaterThan(Integer value) {
			addCriterion("YISONG_FILE >", value, "yisongFile");
			return (Criteria) this;
		}

		public Criteria andYisongFileGreaterThanOrEqualTo(Integer value) {
			addCriterion("YISONG_FILE >=", value, "yisongFile");
			return (Criteria) this;
		}

		public Criteria andYisongFileLessThan(Integer value) {
			addCriterion("YISONG_FILE <", value, "yisongFile");
			return (Criteria) this;
		}

		public Criteria andYisongFileLessThanOrEqualTo(Integer value) {
			addCriterion("YISONG_FILE <=", value, "yisongFile");
			return (Criteria) this;
		}

		public Criteria andYisongFileIn(List<Integer> values) {
			addCriterion("YISONG_FILE in", values, "yisongFile");
			return (Criteria) this;
		}

		public Criteria andYisongFileNotIn(List<Integer> values) {
			addCriterion("YISONG_FILE not in", values, "yisongFile");
			return (Criteria) this;
		}

		public Criteria andYisongFileBetween(Integer value1, Integer value2) {
			addCriterion("YISONG_FILE between", value1, value2, "yisongFile");
			return (Criteria) this;
		}

		public Criteria andYisongFileNotBetween(Integer value1, Integer value2) {
			addCriterion("YISONG_FILE not between", value1, value2,
					"yisongFile");
			return (Criteria) this;
		}

		public Criteria andSurveyFileIsNull() {
			addCriterion("SURVEY_FILE is null");
			return (Criteria) this;
		}

		public Criteria andSurveyFileIsNotNull() {
			addCriterion("SURVEY_FILE is not null");
			return (Criteria) this;
		}

		public Criteria andSurveyFileEqualTo(Integer value) {
			addCriterion("SURVEY_FILE =", value, "surveyFile");
			return (Criteria) this;
		}

		public Criteria andSurveyFileNotEqualTo(Integer value) {
			addCriterion("SURVEY_FILE <>", value, "surveyFile");
			return (Criteria) this;
		}

		public Criteria andSurveyFileGreaterThan(Integer value) {
			addCriterion("SURVEY_FILE >", value, "surveyFile");
			return (Criteria) this;
		}

		public Criteria andSurveyFileGreaterThanOrEqualTo(Integer value) {
			addCriterion("SURVEY_FILE >=", value, "surveyFile");
			return (Criteria) this;
		}

		public Criteria andSurveyFileLessThan(Integer value) {
			addCriterion("SURVEY_FILE <", value, "surveyFile");
			return (Criteria) this;
		}

		public Criteria andSurveyFileLessThanOrEqualTo(Integer value) {
			addCriterion("SURVEY_FILE <=", value, "surveyFile");
			return (Criteria) this;
		}

		public Criteria andSurveyFileIn(List<Integer> values) {
			addCriterion("SURVEY_FILE in", values, "surveyFile");
			return (Criteria) this;
		}

		public Criteria andSurveyFileNotIn(List<Integer> values) {
			addCriterion("SURVEY_FILE not in", values, "surveyFile");
			return (Criteria) this;
		}

		public Criteria andSurveyFileBetween(Integer value1, Integer value2) {
			addCriterion("SURVEY_FILE between", value1, value2, "surveyFile");
			return (Criteria) this;
		}

		public Criteria andSurveyFileNotBetween(Integer value1, Integer value2) {
			addCriterion("SURVEY_FILE not between", value1, value2,
					"surveyFile");
			return (Criteria) this;
		}

		public Criteria andGoodsListFileIsNull() {
			addCriterion("GOODS_LIST_FILE is null");
			return (Criteria) this;
		}

		public Criteria andGoodsListFileIsNotNull() {
			addCriterion("GOODS_LIST_FILE is not null");
			return (Criteria) this;
		}

		public Criteria andGoodsListFileEqualTo(Integer value) {
			addCriterion("GOODS_LIST_FILE =", value, "goodsListFile");
			return (Criteria) this;
		}

		public Criteria andGoodsListFileNotEqualTo(Integer value) {
			addCriterion("GOODS_LIST_FILE <>", value, "goodsListFile");
			return (Criteria) this;
		}

		public Criteria andGoodsListFileGreaterThan(Integer value) {
			addCriterion("GOODS_LIST_FILE >", value, "goodsListFile");
			return (Criteria) this;
		}

		public Criteria andGoodsListFileGreaterThanOrEqualTo(Integer value) {
			addCriterion("GOODS_LIST_FILE >=", value, "goodsListFile");
			return (Criteria) this;
		}

		public Criteria andGoodsListFileLessThan(Integer value) {
			addCriterion("GOODS_LIST_FILE <", value, "goodsListFile");
			return (Criteria) this;
		}

		public Criteria andGoodsListFileLessThanOrEqualTo(Integer value) {
			addCriterion("GOODS_LIST_FILE <=", value, "goodsListFile");
			return (Criteria) this;
		}

		public Criteria andGoodsListFileIn(List<Integer> values) {
			addCriterion("GOODS_LIST_FILE in", values, "goodsListFile");
			return (Criteria) this;
		}

		public Criteria andGoodsListFileNotIn(List<Integer> values) {
			addCriterion("GOODS_LIST_FILE not in", values, "goodsListFile");
			return (Criteria) this;
		}

		public Criteria andGoodsListFileBetween(Integer value1, Integer value2) {
			addCriterion("GOODS_LIST_FILE between", value1, value2,
					"goodsListFile");
			return (Criteria) this;
		}

		public Criteria andGoodsListFileNotBetween(Integer value1,
				Integer value2) {
			addCriterion("GOODS_LIST_FILE not between", value1, value2,
					"goodsListFile");
			return (Criteria) this;
		}

		public Criteria andIdentifyFileIsNull() {
			addCriterion("IDENTIFY_FILE is null");
			return (Criteria) this;
		}

		public Criteria andIdentifyFileIsNotNull() {
			addCriterion("IDENTIFY_FILE is not null");
			return (Criteria) this;
		}

		public Criteria andIdentifyFileEqualTo(Integer value) {
			addCriterion("IDENTIFY_FILE =", value, "identifyFile");
			return (Criteria) this;
		}

		public Criteria andIdentifyFileNotEqualTo(Integer value) {
			addCriterion("IDENTIFY_FILE <>", value, "identifyFile");
			return (Criteria) this;
		}

		public Criteria andIdentifyFileGreaterThan(Integer value) {
			addCriterion("IDENTIFY_FILE >", value, "identifyFile");
			return (Criteria) this;
		}

		public Criteria andIdentifyFileGreaterThanOrEqualTo(Integer value) {
			addCriterion("IDENTIFY_FILE >=", value, "identifyFile");
			return (Criteria) this;
		}

		public Criteria andIdentifyFileLessThan(Integer value) {
			addCriterion("IDENTIFY_FILE <", value, "identifyFile");
			return (Criteria) this;
		}

		public Criteria andIdentifyFileLessThanOrEqualTo(Integer value) {
			addCriterion("IDENTIFY_FILE <=", value, "identifyFile");
			return (Criteria) this;
		}

		public Criteria andIdentifyFileIn(List<Integer> values) {
			addCriterion("IDENTIFY_FILE in", values, "identifyFile");
			return (Criteria) this;
		}

		public Criteria andIdentifyFileNotIn(List<Integer> values) {
			addCriterion("IDENTIFY_FILE not in", values, "identifyFile");
			return (Criteria) this;
		}

		public Criteria andIdentifyFileBetween(Integer value1, Integer value2) {
			addCriterion("IDENTIFY_FILE between", value1, value2,
					"identifyFile");
			return (Criteria) this;
		}

		public Criteria andIdentifyFileNotBetween(Integer value1, Integer value2) {
			addCriterion("IDENTIFY_FILE not between", value1, value2,
					"identifyFile");
			return (Criteria) this;
		}

		public Criteria andOtherFileIsNull() {
			addCriterion("OTHER_FILE is null");
			return (Criteria) this;
		}

		public Criteria andOtherFileIsNotNull() {
			addCriterion("OTHER_FILE is not null");
			return (Criteria) this;
		}

		public Criteria andOtherFileEqualTo(Integer value) {
			addCriterion("OTHER_FILE =", value, "otherFile");
			return (Criteria) this;
		}

		public Criteria andOtherFileNotEqualTo(Integer value) {
			addCriterion("OTHER_FILE <>", value, "otherFile");
			return (Criteria) this;
		}

		public Criteria andOtherFileGreaterThan(Integer value) {
			addCriterion("OTHER_FILE >", value, "otherFile");
			return (Criteria) this;
		}

		public Criteria andOtherFileGreaterThanOrEqualTo(Integer value) {
			addCriterion("OTHER_FILE >=", value, "otherFile");
			return (Criteria) this;
		}

		public Criteria andOtherFileLessThan(Integer value) {
			addCriterion("OTHER_FILE <", value, "otherFile");
			return (Criteria) this;
		}

		public Criteria andOtherFileLessThanOrEqualTo(Integer value) {
			addCriterion("OTHER_FILE <=", value, "otherFile");
			return (Criteria) this;
		}

		public Criteria andOtherFileIn(List<Integer> values) {
			addCriterion("OTHER_FILE in", values, "otherFile");
			return (Criteria) this;
		}

		public Criteria andOtherFileNotIn(List<Integer> values) {
			addCriterion("OTHER_FILE not in", values, "otherFile");
			return (Criteria) this;
		}

		public Criteria andOtherFileBetween(Integer value1, Integer value2) {
			addCriterion("OTHER_FILE between", value1, value2, "otherFile");
			return (Criteria) this;
		}

		public Criteria andOtherFileNotBetween(Integer value1, Integer value2) {
			addCriterion("OTHER_FILE not between", value1, value2, "otherFile");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table LIANGFA_XINXIANG20170424.CRIME_CASE_FORM
	 * @mbggenerated  Tue May 09 14:58:02 CST 2017
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
     * This class corresponds to the database table LIANGFA_XINXIANG20170222.CRIME_CASE_FORM
     *
     * @mbggenerated do_not_delete_during_merge Wed Mar 22 11:07:08 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
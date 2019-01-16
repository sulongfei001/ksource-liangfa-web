package com.ksource.liangfa.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CaseConsultationExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table CASE_CONSULTATION
	 * @mbggenerated  Tue Dec 27 11:46:25 CST 2011
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table CASE_CONSULTATION
	 * @mbggenerated  Tue Dec 27 11:46:25 CST 2011
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table CASE_CONSULTATION
	 * @mbggenerated  Tue Dec 27 11:46:25 CST 2011
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_CONSULTATION
	 * @mbggenerated  Tue Dec 27 11:46:25 CST 2011
	 */
	public CaseConsultationExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_CONSULTATION
	 * @mbggenerated  Tue Dec 27 11:46:25 CST 2011
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_CONSULTATION
	 * @mbggenerated  Tue Dec 27 11:46:25 CST 2011
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_CONSULTATION
	 * @mbggenerated  Tue Dec 27 11:46:25 CST 2011
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_CONSULTATION
	 * @mbggenerated  Tue Dec 27 11:46:25 CST 2011
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_CONSULTATION
	 * @mbggenerated  Tue Dec 27 11:46:25 CST 2011
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_CONSULTATION
	 * @mbggenerated  Tue Dec 27 11:46:25 CST 2011
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_CONSULTATION
	 * @mbggenerated  Tue Dec 27 11:46:25 CST 2011
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_CONSULTATION
	 * @mbggenerated  Tue Dec 27 11:46:25 CST 2011
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_CONSULTATION
	 * @mbggenerated  Tue Dec 27 11:46:25 CST 2011
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_CONSULTATION
	 * @mbggenerated  Tue Dec 27 11:46:25 CST 2011
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table CASE_CONSULTATION
	 * @mbggenerated  Tue Dec 27 11:46:25 CST 2011
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

		public Criteria andInputerIsNull() {
			addCriterion("INPUTER is null");
			return (Criteria) this;
		}

		public Criteria andInputerIsNotNull() {
			addCriterion("INPUTER is not null");
			return (Criteria) this;
		}

		public Criteria andInputerEqualTo(String value) {
			addCriterion("INPUTER =", value, "inputer");
			return (Criteria) this;
		}

		public Criteria andInputerNotEqualTo(String value) {
			addCriterion("INPUTER <>", value, "inputer");
			return (Criteria) this;
		}

		public Criteria andInputerGreaterThan(String value) {
			addCriterion("INPUTER >", value, "inputer");
			return (Criteria) this;
		}

		public Criteria andInputerGreaterThanOrEqualTo(String value) {
			addCriterion("INPUTER >=", value, "inputer");
			return (Criteria) this;
		}

		public Criteria andInputerLessThan(String value) {
			addCriterion("INPUTER <", value, "inputer");
			return (Criteria) this;
		}

		public Criteria andInputerLessThanOrEqualTo(String value) {
			addCriterion("INPUTER <=", value, "inputer");
			return (Criteria) this;
		}

		public Criteria andInputerLike(String value) {
			addCriterion("INPUTER like", value, "inputer");
			return (Criteria) this;
		}

		public Criteria andInputerNotLike(String value) {
			addCriterion("INPUTER not like", value, "inputer");
			return (Criteria) this;
		}

		public Criteria andInputerIn(List<String> values) {
			addCriterion("INPUTER in", values, "inputer");
			return (Criteria) this;
		}

		public Criteria andInputerNotIn(List<String> values) {
			addCriterion("INPUTER not in", values, "inputer");
			return (Criteria) this;
		}

		public Criteria andInputerBetween(String value1, String value2) {
			addCriterion("INPUTER between", value1, value2, "inputer");
			return (Criteria) this;
		}

		public Criteria andInputerNotBetween(String value1, String value2) {
			addCriterion("INPUTER not between", value1, value2, "inputer");
			return (Criteria) this;
		}

		public Criteria andInputOrgIdIsNull() {
			addCriterion("INPUT_ORG_ID is null");
			return (Criteria) this;
		}

		public Criteria andInputOrgIdIsNotNull() {
			addCriterion("INPUT_ORG_ID is not null");
			return (Criteria) this;
		}

		public Criteria andInputOrgIdEqualTo(Integer value) {
			addCriterion("INPUT_ORG_ID =", value, "inputOrgId");
			return (Criteria) this;
		}

		public Criteria andInputOrgIdNotEqualTo(Integer value) {
			addCriterion("INPUT_ORG_ID <>", value, "inputOrgId");
			return (Criteria) this;
		}

		public Criteria andInputOrgIdGreaterThan(Integer value) {
			addCriterion("INPUT_ORG_ID >", value, "inputOrgId");
			return (Criteria) this;
		}

		public Criteria andInputOrgIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("INPUT_ORG_ID >=", value, "inputOrgId");
			return (Criteria) this;
		}

		public Criteria andInputOrgIdLessThan(Integer value) {
			addCriterion("INPUT_ORG_ID <", value, "inputOrgId");
			return (Criteria) this;
		}

		public Criteria andInputOrgIdLessThanOrEqualTo(Integer value) {
			addCriterion("INPUT_ORG_ID <=", value, "inputOrgId");
			return (Criteria) this;
		}

		public Criteria andInputOrgIdIn(List<Integer> values) {
			addCriterion("INPUT_ORG_ID in", values, "inputOrgId");
			return (Criteria) this;
		}

		public Criteria andInputOrgIdNotIn(List<Integer> values) {
			addCriterion("INPUT_ORG_ID not in", values, "inputOrgId");
			return (Criteria) this;
		}

		public Criteria andInputOrgIdBetween(Integer value1, Integer value2) {
			addCriterion("INPUT_ORG_ID between", value1, value2, "inputOrgId");
			return (Criteria) this;
		}

		public Criteria andInputOrgIdNotBetween(Integer value1, Integer value2) {
			addCriterion("INPUT_ORG_ID not between", value1, value2,
					"inputOrgId");
			return (Criteria) this;
		}

		public Criteria andInputTimeIsNull() {
			addCriterion("INPUT_TIME is null");
			return (Criteria) this;
		}

		public Criteria andInputTimeIsNotNull() {
			addCriterion("INPUT_TIME is not null");
			return (Criteria) this;
		}

		public Criteria andInputTimeEqualTo(Date value) {
			addCriterion("INPUT_TIME =", value, "inputTime");
			return (Criteria) this;
		}

		public Criteria andInputTimeNotEqualTo(Date value) {
			addCriterion("INPUT_TIME <>", value, "inputTime");
			return (Criteria) this;
		}

		public Criteria andInputTimeGreaterThan(Date value) {
			addCriterion("INPUT_TIME >", value, "inputTime");
			return (Criteria) this;
		}

		public Criteria andInputTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("INPUT_TIME >=", value, "inputTime");
			return (Criteria) this;
		}

		public Criteria andInputTimeLessThan(Date value) {
			addCriterion("INPUT_TIME <", value, "inputTime");
			return (Criteria) this;
		}

		public Criteria andInputTimeLessThanOrEqualTo(Date value) {
			addCriterion("INPUT_TIME <=", value, "inputTime");
			return (Criteria) this;
		}

		public Criteria andInputTimeIn(List<Date> values) {
			addCriterion("INPUT_TIME in", values, "inputTime");
			return (Criteria) this;
		}

		public Criteria andInputTimeNotIn(List<Date> values) {
			addCriterion("INPUT_TIME not in", values, "inputTime");
			return (Criteria) this;
		}

		public Criteria andInputTimeBetween(Date value1, Date value2) {
			addCriterion("INPUT_TIME between", value1, value2, "inputTime");
			return (Criteria) this;
		}

		public Criteria andInputTimeNotBetween(Date value1, Date value2) {
			addCriterion("INPUT_TIME not between", value1, value2, "inputTime");
			return (Criteria) this;
		}

		public Criteria andStateIsNull() {
			addCriterion("STATE is null");
			return (Criteria) this;
		}

		public Criteria andStateIsNotNull() {
			addCriterion("STATE is not null");
			return (Criteria) this;
		}

		public Criteria andStateEqualTo(Integer value) {
			addCriterion("STATE =", value, "state");
			return (Criteria) this;
		}

		public Criteria andStateNotEqualTo(Integer value) {
			addCriterion("STATE <>", value, "state");
			return (Criteria) this;
		}

		public Criteria andStateGreaterThan(Integer value) {
			addCriterion("STATE >", value, "state");
			return (Criteria) this;
		}

		public Criteria andStateGreaterThanOrEqualTo(Integer value) {
			addCriterion("STATE >=", value, "state");
			return (Criteria) this;
		}

		public Criteria andStateLessThan(Integer value) {
			addCriterion("STATE <", value, "state");
			return (Criteria) this;
		}

		public Criteria andStateLessThanOrEqualTo(Integer value) {
			addCriterion("STATE <=", value, "state");
			return (Criteria) this;
		}

		public Criteria andStateIn(List<Integer> values) {
			addCriterion("STATE in", values, "state");
			return (Criteria) this;
		}

		public Criteria andStateNotIn(List<Integer> values) {
			addCriterion("STATE not in", values, "state");
			return (Criteria) this;
		}

		public Criteria andStateBetween(Integer value1, Integer value2) {
			addCriterion("STATE between", value1, value2, "state");
			return (Criteria) this;
		}

		public Criteria andStateNotBetween(Integer value1, Integer value2) {
			addCriterion("STATE not between", value1, value2, "state");
			return (Criteria) this;
		}

		public Criteria andTitleIsNull() {
			addCriterion("TITLE is null");
			return (Criteria) this;
		}

		public Criteria andTitleIsNotNull() {
			addCriterion("TITLE is not null");
			return (Criteria) this;
		}

		public Criteria andTitleEqualTo(String value) {
			addCriterion("TITLE =", value, "title");
			return (Criteria) this;
		}

		public Criteria andTitleNotEqualTo(String value) {
			addCriterion("TITLE <>", value, "title");
			return (Criteria) this;
		}

		public Criteria andTitleGreaterThan(String value) {
			addCriterion("TITLE >", value, "title");
			return (Criteria) this;
		}

		public Criteria andTitleGreaterThanOrEqualTo(String value) {
			addCriterion("TITLE >=", value, "title");
			return (Criteria) this;
		}

		public Criteria andTitleLessThan(String value) {
			addCriterion("TITLE <", value, "title");
			return (Criteria) this;
		}

		public Criteria andTitleLessThanOrEqualTo(String value) {
			addCriterion("TITLE <=", value, "title");
			return (Criteria) this;
		}

		public Criteria andTitleLike(String value) {
			addCriterion("TITLE like", value, "title");
			return (Criteria) this;
		}

		public Criteria andTitleNotLike(String value) {
			addCriterion("TITLE not like", value, "title");
			return (Criteria) this;
		}

		public Criteria andTitleIn(List<String> values) {
			addCriterion("TITLE in", values, "title");
			return (Criteria) this;
		}

		public Criteria andTitleNotIn(List<String> values) {
			addCriterion("TITLE not in", values, "title");
			return (Criteria) this;
		}

		public Criteria andTitleBetween(String value1, String value2) {
			addCriterion("TITLE between", value1, value2, "title");
			return (Criteria) this;
		}

		public Criteria andTitleNotBetween(String value1, String value2) {
			addCriterion("TITLE not between", value1, value2, "title");
			return (Criteria) this;
		}

		public Criteria andAttachmentPathIsNull() {
			addCriterion("ATTACHMENT_PATH is null");
			return (Criteria) this;
		}

		public Criteria andAttachmentPathIsNotNull() {
			addCriterion("ATTACHMENT_PATH is not null");
			return (Criteria) this;
		}

		public Criteria andAttachmentPathEqualTo(String value) {
			addCriterion("ATTACHMENT_PATH =", value, "attachmentPath");
			return (Criteria) this;
		}

		public Criteria andAttachmentPathNotEqualTo(String value) {
			addCriterion("ATTACHMENT_PATH <>", value, "attachmentPath");
			return (Criteria) this;
		}

		public Criteria andAttachmentPathGreaterThan(String value) {
			addCriterion("ATTACHMENT_PATH >", value, "attachmentPath");
			return (Criteria) this;
		}

		public Criteria andAttachmentPathGreaterThanOrEqualTo(String value) {
			addCriterion("ATTACHMENT_PATH >=", value, "attachmentPath");
			return (Criteria) this;
		}

		public Criteria andAttachmentPathLessThan(String value) {
			addCriterion("ATTACHMENT_PATH <", value, "attachmentPath");
			return (Criteria) this;
		}

		public Criteria andAttachmentPathLessThanOrEqualTo(String value) {
			addCriterion("ATTACHMENT_PATH <=", value, "attachmentPath");
			return (Criteria) this;
		}

		public Criteria andAttachmentPathLike(String value) {
			addCriterion("ATTACHMENT_PATH like", value, "attachmentPath");
			return (Criteria) this;
		}

		public Criteria andAttachmentPathNotLike(String value) {
			addCriterion("ATTACHMENT_PATH not like", value, "attachmentPath");
			return (Criteria) this;
		}

		public Criteria andAttachmentPathIn(List<String> values) {
			addCriterion("ATTACHMENT_PATH in", values, "attachmentPath");
			return (Criteria) this;
		}

		public Criteria andAttachmentPathNotIn(List<String> values) {
			addCriterion("ATTACHMENT_PATH not in", values, "attachmentPath");
			return (Criteria) this;
		}

		public Criteria andAttachmentPathBetween(String value1, String value2) {
			addCriterion("ATTACHMENT_PATH between", value1, value2,
					"attachmentPath");
			return (Criteria) this;
		}

		public Criteria andAttachmentPathNotBetween(String value1, String value2) {
			addCriterion("ATTACHMENT_PATH not between", value1, value2,
					"attachmentPath");
			return (Criteria) this;
		}

		public Criteria andAttachmentNameIsNull() {
			addCriterion("ATTACHMENT_NAME is null");
			return (Criteria) this;
		}

		public Criteria andAttachmentNameIsNotNull() {
			addCriterion("ATTACHMENT_NAME is not null");
			return (Criteria) this;
		}

		public Criteria andAttachmentNameEqualTo(String value) {
			addCriterion("ATTACHMENT_NAME =", value, "attachmentName");
			return (Criteria) this;
		}

		public Criteria andAttachmentNameNotEqualTo(String value) {
			addCriterion("ATTACHMENT_NAME <>", value, "attachmentName");
			return (Criteria) this;
		}

		public Criteria andAttachmentNameGreaterThan(String value) {
			addCriterion("ATTACHMENT_NAME >", value, "attachmentName");
			return (Criteria) this;
		}

		public Criteria andAttachmentNameGreaterThanOrEqualTo(String value) {
			addCriterion("ATTACHMENT_NAME >=", value, "attachmentName");
			return (Criteria) this;
		}

		public Criteria andAttachmentNameLessThan(String value) {
			addCriterion("ATTACHMENT_NAME <", value, "attachmentName");
			return (Criteria) this;
		}

		public Criteria andAttachmentNameLessThanOrEqualTo(String value) {
			addCriterion("ATTACHMENT_NAME <=", value, "attachmentName");
			return (Criteria) this;
		}

		public Criteria andAttachmentNameLike(String value) {
			addCriterion("ATTACHMENT_NAME like", value, "attachmentName");
			return (Criteria) this;
		}

		public Criteria andAttachmentNameNotLike(String value) {
			addCriterion("ATTACHMENT_NAME not like", value, "attachmentName");
			return (Criteria) this;
		}

		public Criteria andAttachmentNameIn(List<String> values) {
			addCriterion("ATTACHMENT_NAME in", values, "attachmentName");
			return (Criteria) this;
		}

		public Criteria andAttachmentNameNotIn(List<String> values) {
			addCriterion("ATTACHMENT_NAME not in", values, "attachmentName");
			return (Criteria) this;
		}

		public Criteria andAttachmentNameBetween(String value1, String value2) {
			addCriterion("ATTACHMENT_NAME between", value1, value2,
					"attachmentName");
			return (Criteria) this;
		}

		public Criteria andAttachmentNameNotBetween(String value1, String value2) {
			addCriterion("ATTACHMENT_NAME not between", value1, value2,
					"attachmentName");
			return (Criteria) this;
		}

		public Criteria andSetTopIsNull() {
			addCriterion("SET_TOP is null");
			return (Criteria) this;
		}

		public Criteria andSetTopIsNotNull() {
			addCriterion("SET_TOP is not null");
			return (Criteria) this;
		}

		public Criteria andSetTopEqualTo(Integer value) {
			addCriterion("SET_TOP =", value, "setTop");
			return (Criteria) this;
		}

		public Criteria andSetTopNotEqualTo(Integer value) {
			addCriterion("SET_TOP <>", value, "setTop");
			return (Criteria) this;
		}

		public Criteria andSetTopGreaterThan(Integer value) {
			addCriterion("SET_TOP >", value, "setTop");
			return (Criteria) this;
		}

		public Criteria andSetTopGreaterThanOrEqualTo(Integer value) {
			addCriterion("SET_TOP >=", value, "setTop");
			return (Criteria) this;
		}

		public Criteria andSetTopLessThan(Integer value) {
			addCriterion("SET_TOP <", value, "setTop");
			return (Criteria) this;
		}

		public Criteria andSetTopLessThanOrEqualTo(Integer value) {
			addCriterion("SET_TOP <=", value, "setTop");
			return (Criteria) this;
		}

		public Criteria andSetTopIn(List<Integer> values) {
			addCriterion("SET_TOP in", values, "setTop");
			return (Criteria) this;
		}

		public Criteria andSetTopNotIn(List<Integer> values) {
			addCriterion("SET_TOP not in", values, "setTop");
			return (Criteria) this;
		}

		public Criteria andSetTopBetween(Integer value1, Integer value2) {
			addCriterion("SET_TOP between", value1, value2, "setTop");
			return (Criteria) this;
		}

		public Criteria andSetTopNotBetween(Integer value1, Integer value2) {
			addCriterion("SET_TOP not between", value1, value2, "setTop");
			return (Criteria) this;
		}

		public Criteria andCaseNoIsNull() {
			addCriterion("CASE_NO is null");
			return (Criteria) this;
		}

		public Criteria andCaseNoIsNotNull() {
			addCriterion("CASE_NO is not null");
			return (Criteria) this;
		}

		public Criteria andCaseNoEqualTo(String value) {
			addCriterion("CASE_NO =", value, "caseNo");
			return (Criteria) this;
		}

		public Criteria andCaseNoNotEqualTo(String value) {
			addCriterion("CASE_NO <>", value, "caseNo");
			return (Criteria) this;
		}

		public Criteria andCaseNoGreaterThan(String value) {
			addCriterion("CASE_NO >", value, "caseNo");
			return (Criteria) this;
		}

		public Criteria andCaseNoGreaterThanOrEqualTo(String value) {
			addCriterion("CASE_NO >=", value, "caseNo");
			return (Criteria) this;
		}

		public Criteria andCaseNoLessThan(String value) {
			addCriterion("CASE_NO <", value, "caseNo");
			return (Criteria) this;
		}

		public Criteria andCaseNoLessThanOrEqualTo(String value) {
			addCriterion("CASE_NO <=", value, "caseNo");
			return (Criteria) this;
		}

		public Criteria andCaseNoLike(String value) {
			addCriterion("CASE_NO like", value, "caseNo");
			return (Criteria) this;
		}

		public Criteria andCaseNoNotLike(String value) {
			addCriterion("CASE_NO not like", value, "caseNo");
			return (Criteria) this;
		}

		public Criteria andCaseNoIn(List<String> values) {
			addCriterion("CASE_NO in", values, "caseNo");
			return (Criteria) this;
		}

		public Criteria andCaseNoNotIn(List<String> values) {
			addCriterion("CASE_NO not in", values, "caseNo");
			return (Criteria) this;
		}

		public Criteria andCaseNoBetween(String value1, String value2) {
			addCriterion("CASE_NO between", value1, value2, "caseNo");
			return (Criteria) this;
		}

		public Criteria andCaseNoNotBetween(String value1, String value2) {
			addCriterion("CASE_NO not between", value1, value2, "caseNo");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table CASE_CONSULTATION
	 * @mbggenerated  Tue Dec 27 11:46:25 CST 2011
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
     * This class corresponds to the database table CASE_CONSULTATION
     *
     * @mbggenerated do_not_delete_during_merge Mon Aug 29 11:28:44 CST 2011
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
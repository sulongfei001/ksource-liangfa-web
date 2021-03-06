package com.ksource.liangfa.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForumThemeExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table FORUM_THEME
	 * @mbggenerated  Fri Dec 30 16:38:20 CST 2011
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table FORUM_THEME
	 * @mbggenerated  Fri Dec 30 16:38:20 CST 2011
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table FORUM_THEME
	 * @mbggenerated  Fri Dec 30 16:38:20 CST 2011
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORUM_THEME
	 * @mbggenerated  Fri Dec 30 16:38:20 CST 2011
	 */
	public ForumThemeExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORUM_THEME
	 * @mbggenerated  Fri Dec 30 16:38:20 CST 2011
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORUM_THEME
	 * @mbggenerated  Fri Dec 30 16:38:20 CST 2011
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORUM_THEME
	 * @mbggenerated  Fri Dec 30 16:38:20 CST 2011
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORUM_THEME
	 * @mbggenerated  Fri Dec 30 16:38:20 CST 2011
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORUM_THEME
	 * @mbggenerated  Fri Dec 30 16:38:20 CST 2011
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORUM_THEME
	 * @mbggenerated  Fri Dec 30 16:38:20 CST 2011
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORUM_THEME
	 * @mbggenerated  Fri Dec 30 16:38:20 CST 2011
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORUM_THEME
	 * @mbggenerated  Fri Dec 30 16:38:20 CST 2011
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORUM_THEME
	 * @mbggenerated  Fri Dec 30 16:38:20 CST 2011
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORUM_THEME
	 * @mbggenerated  Fri Dec 30 16:38:20 CST 2011
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table FORUM_THEME
	 * @mbggenerated  Fri Dec 30 16:38:20 CST 2011
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

		public Criteria andSectionIdIsNull() {
			addCriterion("SECTION_ID is null");
			return (Criteria) this;
		}

		public Criteria andSectionIdIsNotNull() {
			addCriterion("SECTION_ID is not null");
			return (Criteria) this;
		}

		public Criteria andSectionIdEqualTo(Integer value) {
			addCriterion("SECTION_ID =", value, "sectionId");
			return (Criteria) this;
		}

		public Criteria andSectionIdNotEqualTo(Integer value) {
			addCriterion("SECTION_ID <>", value, "sectionId");
			return (Criteria) this;
		}

		public Criteria andSectionIdGreaterThan(Integer value) {
			addCriterion("SECTION_ID >", value, "sectionId");
			return (Criteria) this;
		}

		public Criteria andSectionIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("SECTION_ID >=", value, "sectionId");
			return (Criteria) this;
		}

		public Criteria andSectionIdLessThan(Integer value) {
			addCriterion("SECTION_ID <", value, "sectionId");
			return (Criteria) this;
		}

		public Criteria andSectionIdLessThanOrEqualTo(Integer value) {
			addCriterion("SECTION_ID <=", value, "sectionId");
			return (Criteria) this;
		}

		public Criteria andSectionIdIn(List<Integer> values) {
			addCriterion("SECTION_ID in", values, "sectionId");
			return (Criteria) this;
		}

		public Criteria andSectionIdNotIn(List<Integer> values) {
			addCriterion("SECTION_ID not in", values, "sectionId");
			return (Criteria) this;
		}

		public Criteria andSectionIdBetween(Integer value1, Integer value2) {
			addCriterion("SECTION_ID between", value1, value2, "sectionId");
			return (Criteria) this;
		}

		public Criteria andSectionIdNotBetween(Integer value1, Integer value2) {
			addCriterion("SECTION_ID not between", value1, value2, "sectionId");
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

		public Criteria andCreateTimeIsNull() {
			addCriterion("CREATE_TIME is null");
			return (Criteria) this;
		}

		public Criteria andCreateTimeIsNotNull() {
			addCriterion("CREATE_TIME is not null");
			return (Criteria) this;
		}

		public Criteria andCreateTimeEqualTo(Date value) {
			addCriterion("CREATE_TIME =", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeNotEqualTo(Date value) {
			addCriterion("CREATE_TIME <>", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeGreaterThan(Date value) {
			addCriterion("CREATE_TIME >", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("CREATE_TIME >=", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeLessThan(Date value) {
			addCriterion("CREATE_TIME <", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
			addCriterion("CREATE_TIME <=", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeIn(List<Date> values) {
			addCriterion("CREATE_TIME in", values, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeNotIn(List<Date> values) {
			addCriterion("CREATE_TIME not in", values, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeBetween(Date value1, Date value2) {
			addCriterion("CREATE_TIME between", value1, value2, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
			addCriterion("CREATE_TIME not between", value1, value2,
					"createTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeIsNull() {
			addCriterion("UPDATE_TIME is null");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeIsNotNull() {
			addCriterion("UPDATE_TIME is not null");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeEqualTo(Date value) {
			addCriterion("UPDATE_TIME =", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeNotEqualTo(Date value) {
			addCriterion("UPDATE_TIME <>", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeGreaterThan(Date value) {
			addCriterion("UPDATE_TIME >", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("UPDATE_TIME >=", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeLessThan(Date value) {
			addCriterion("UPDATE_TIME <", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
			addCriterion("UPDATE_TIME <=", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeIn(List<Date> values) {
			addCriterion("UPDATE_TIME in", values, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeNotIn(List<Date> values) {
			addCriterion("UPDATE_TIME not in", values, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeBetween(Date value1, Date value2) {
			addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
			addCriterion("UPDATE_TIME not between", value1, value2,
					"updateTime");
			return (Criteria) this;
		}

		public Criteria andReplyCountIsNull() {
			addCriterion("REPLY_COUNT is null");
			return (Criteria) this;
		}

		public Criteria andReplyCountIsNotNull() {
			addCriterion("REPLY_COUNT is not null");
			return (Criteria) this;
		}

		public Criteria andReplyCountEqualTo(Integer value) {
			addCriterion("REPLY_COUNT =", value, "replyCount");
			return (Criteria) this;
		}

		public Criteria andReplyCountNotEqualTo(Integer value) {
			addCriterion("REPLY_COUNT <>", value, "replyCount");
			return (Criteria) this;
		}

		public Criteria andReplyCountGreaterThan(Integer value) {
			addCriterion("REPLY_COUNT >", value, "replyCount");
			return (Criteria) this;
		}

		public Criteria andReplyCountGreaterThanOrEqualTo(Integer value) {
			addCriterion("REPLY_COUNT >=", value, "replyCount");
			return (Criteria) this;
		}

		public Criteria andReplyCountLessThan(Integer value) {
			addCriterion("REPLY_COUNT <", value, "replyCount");
			return (Criteria) this;
		}

		public Criteria andReplyCountLessThanOrEqualTo(Integer value) {
			addCriterion("REPLY_COUNT <=", value, "replyCount");
			return (Criteria) this;
		}

		public Criteria andReplyCountIn(List<Integer> values) {
			addCriterion("REPLY_COUNT in", values, "replyCount");
			return (Criteria) this;
		}

		public Criteria andReplyCountNotIn(List<Integer> values) {
			addCriterion("REPLY_COUNT not in", values, "replyCount");
			return (Criteria) this;
		}

		public Criteria andReplyCountBetween(Integer value1, Integer value2) {
			addCriterion("REPLY_COUNT between", value1, value2, "replyCount");
			return (Criteria) this;
		}

		public Criteria andReplyCountNotBetween(Integer value1, Integer value2) {
			addCriterion("REPLY_COUNT not between", value1, value2,
					"replyCount");
			return (Criteria) this;
		}

		public Criteria andReadCountIsNull() {
			addCriterion("READ_COUNT is null");
			return (Criteria) this;
		}

		public Criteria andReadCountIsNotNull() {
			addCriterion("READ_COUNT is not null");
			return (Criteria) this;
		}

		public Criteria andReadCountEqualTo(Integer value) {
			addCriterion("READ_COUNT =", value, "readCount");
			return (Criteria) this;
		}

		public Criteria andReadCountNotEqualTo(Integer value) {
			addCriterion("READ_COUNT <>", value, "readCount");
			return (Criteria) this;
		}

		public Criteria andReadCountGreaterThan(Integer value) {
			addCriterion("READ_COUNT >", value, "readCount");
			return (Criteria) this;
		}

		public Criteria andReadCountGreaterThanOrEqualTo(Integer value) {
			addCriterion("READ_COUNT >=", value, "readCount");
			return (Criteria) this;
		}

		public Criteria andReadCountLessThan(Integer value) {
			addCriterion("READ_COUNT <", value, "readCount");
			return (Criteria) this;
		}

		public Criteria andReadCountLessThanOrEqualTo(Integer value) {
			addCriterion("READ_COUNT <=", value, "readCount");
			return (Criteria) this;
		}

		public Criteria andReadCountIn(List<Integer> values) {
			addCriterion("READ_COUNT in", values, "readCount");
			return (Criteria) this;
		}

		public Criteria andReadCountNotIn(List<Integer> values) {
			addCriterion("READ_COUNT not in", values, "readCount");
			return (Criteria) this;
		}

		public Criteria andReadCountBetween(Integer value1, Integer value2) {
			addCriterion("READ_COUNT between", value1, value2, "readCount");
			return (Criteria) this;
		}

		public Criteria andReadCountNotBetween(Integer value1, Integer value2) {
			addCriterion("READ_COUNT not between", value1, value2, "readCount");
			return (Criteria) this;
		}

		public Criteria andLatestReplyIdIsNull() {
			addCriterion("LATEST_REPLY_ID is null");
			return (Criteria) this;
		}

		public Criteria andLatestReplyIdIsNotNull() {
			addCriterion("LATEST_REPLY_ID is not null");
			return (Criteria) this;
		}

		public Criteria andLatestReplyIdEqualTo(Integer value) {
			addCriterion("LATEST_REPLY_ID =", value, "latestReplyId");
			return (Criteria) this;
		}

		public Criteria andLatestReplyIdNotEqualTo(Integer value) {
			addCriterion("LATEST_REPLY_ID <>", value, "latestReplyId");
			return (Criteria) this;
		}

		public Criteria andLatestReplyIdGreaterThan(Integer value) {
			addCriterion("LATEST_REPLY_ID >", value, "latestReplyId");
			return (Criteria) this;
		}

		public Criteria andLatestReplyIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("LATEST_REPLY_ID >=", value, "latestReplyId");
			return (Criteria) this;
		}

		public Criteria andLatestReplyIdLessThan(Integer value) {
			addCriterion("LATEST_REPLY_ID <", value, "latestReplyId");
			return (Criteria) this;
		}

		public Criteria andLatestReplyIdLessThanOrEqualTo(Integer value) {
			addCriterion("LATEST_REPLY_ID <=", value, "latestReplyId");
			return (Criteria) this;
		}

		public Criteria andLatestReplyIdIn(List<Integer> values) {
			addCriterion("LATEST_REPLY_ID in", values, "latestReplyId");
			return (Criteria) this;
		}

		public Criteria andLatestReplyIdNotIn(List<Integer> values) {
			addCriterion("LATEST_REPLY_ID not in", values, "latestReplyId");
			return (Criteria) this;
		}

		public Criteria andLatestReplyIdBetween(Integer value1, Integer value2) {
			addCriterion("LATEST_REPLY_ID between", value1, value2,
					"latestReplyId");
			return (Criteria) this;
		}

		public Criteria andLatestReplyIdNotBetween(Integer value1,
				Integer value2) {
			addCriterion("LATEST_REPLY_ID not between", value1, value2,
					"latestReplyId");
			return (Criteria) this;
		}

		public Criteria andLatestReplyNameIsNull() {
			addCriterion("LATEST_REPLY_NAME is null");
			return (Criteria) this;
		}

		public Criteria andLatestReplyNameIsNotNull() {
			addCriterion("LATEST_REPLY_NAME is not null");
			return (Criteria) this;
		}

		public Criteria andLatestReplyNameEqualTo(String value) {
			addCriterion("LATEST_REPLY_NAME =", value, "latestReplyName");
			return (Criteria) this;
		}

		public Criteria andLatestReplyNameNotEqualTo(String value) {
			addCriterion("LATEST_REPLY_NAME <>", value, "latestReplyName");
			return (Criteria) this;
		}

		public Criteria andLatestReplyNameGreaterThan(String value) {
			addCriterion("LATEST_REPLY_NAME >", value, "latestReplyName");
			return (Criteria) this;
		}

		public Criteria andLatestReplyNameGreaterThanOrEqualTo(String value) {
			addCriterion("LATEST_REPLY_NAME >=", value, "latestReplyName");
			return (Criteria) this;
		}

		public Criteria andLatestReplyNameLessThan(String value) {
			addCriterion("LATEST_REPLY_NAME <", value, "latestReplyName");
			return (Criteria) this;
		}

		public Criteria andLatestReplyNameLessThanOrEqualTo(String value) {
			addCriterion("LATEST_REPLY_NAME <=", value, "latestReplyName");
			return (Criteria) this;
		}

		public Criteria andLatestReplyNameLike(String value) {
			addCriterion("LATEST_REPLY_NAME like", value, "latestReplyName");
			return (Criteria) this;
		}

		public Criteria andLatestReplyNameNotLike(String value) {
			addCriterion("LATEST_REPLY_NAME not like", value, "latestReplyName");
			return (Criteria) this;
		}

		public Criteria andLatestReplyNameIn(List<String> values) {
			addCriterion("LATEST_REPLY_NAME in", values, "latestReplyName");
			return (Criteria) this;
		}

		public Criteria andLatestReplyNameNotIn(List<String> values) {
			addCriterion("LATEST_REPLY_NAME not in", values, "latestReplyName");
			return (Criteria) this;
		}

		public Criteria andLatestReplyNameBetween(String value1, String value2) {
			addCriterion("LATEST_REPLY_NAME between", value1, value2,
					"latestReplyName");
			return (Criteria) this;
		}

		public Criteria andLatestReplyNameNotBetween(String value1,
				String value2) {
			addCriterion("LATEST_REPLY_NAME not between", value1, value2,
					"latestReplyName");
			return (Criteria) this;
		}

		public Criteria andLatestReplyTimeIsNull() {
			addCriterion("LATEST_REPLY_TIME is null");
			return (Criteria) this;
		}

		public Criteria andLatestReplyTimeIsNotNull() {
			addCriterion("LATEST_REPLY_TIME is not null");
			return (Criteria) this;
		}

		public Criteria andLatestReplyTimeEqualTo(Date value) {
			addCriterion("LATEST_REPLY_TIME =", value, "latestReplyTime");
			return (Criteria) this;
		}

		public Criteria andLatestReplyTimeNotEqualTo(Date value) {
			addCriterion("LATEST_REPLY_TIME <>", value, "latestReplyTime");
			return (Criteria) this;
		}

		public Criteria andLatestReplyTimeGreaterThan(Date value) {
			addCriterion("LATEST_REPLY_TIME >", value, "latestReplyTime");
			return (Criteria) this;
		}

		public Criteria andLatestReplyTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("LATEST_REPLY_TIME >=", value, "latestReplyTime");
			return (Criteria) this;
		}

		public Criteria andLatestReplyTimeLessThan(Date value) {
			addCriterion("LATEST_REPLY_TIME <", value, "latestReplyTime");
			return (Criteria) this;
		}

		public Criteria andLatestReplyTimeLessThanOrEqualTo(Date value) {
			addCriterion("LATEST_REPLY_TIME <=", value, "latestReplyTime");
			return (Criteria) this;
		}

		public Criteria andLatestReplyTimeIn(List<Date> values) {
			addCriterion("LATEST_REPLY_TIME in", values, "latestReplyTime");
			return (Criteria) this;
		}

		public Criteria andLatestReplyTimeNotIn(List<Date> values) {
			addCriterion("LATEST_REPLY_TIME not in", values, "latestReplyTime");
			return (Criteria) this;
		}

		public Criteria andLatestReplyTimeBetween(Date value1, Date value2) {
			addCriterion("LATEST_REPLY_TIME between", value1, value2,
					"latestReplyTime");
			return (Criteria) this;
		}

		public Criteria andLatestReplyTimeNotBetween(Date value1, Date value2) {
			addCriterion("LATEST_REPLY_TIME not between", value1, value2,
					"latestReplyTime");
			return (Criteria) this;
		}

		public Criteria andLabelIsNull() {
			addCriterion("LABEL is null");
			return (Criteria) this;
		}

		public Criteria andLabelIsNotNull() {
			addCriterion("LABEL is not null");
			return (Criteria) this;
		}

		public Criteria andLabelEqualTo(String value) {
			addCriterion("LABEL =", value, "label");
			return (Criteria) this;
		}

		public Criteria andLabelNotEqualTo(String value) {
			addCriterion("LABEL <>", value, "label");
			return (Criteria) this;
		}

		public Criteria andLabelGreaterThan(String value) {
			addCriterion("LABEL >", value, "label");
			return (Criteria) this;
		}

		public Criteria andLabelGreaterThanOrEqualTo(String value) {
			addCriterion("LABEL >=", value, "label");
			return (Criteria) this;
		}

		public Criteria andLabelLessThan(String value) {
			addCriterion("LABEL <", value, "label");
			return (Criteria) this;
		}

		public Criteria andLabelLessThanOrEqualTo(String value) {
			addCriterion("LABEL <=", value, "label");
			return (Criteria) this;
		}

		public Criteria andLabelLike(String value) {
			addCriterion("LABEL like", value, "label");
			return (Criteria) this;
		}

		public Criteria andLabelNotLike(String value) {
			addCriterion("LABEL not like", value, "label");
			return (Criteria) this;
		}

		public Criteria andLabelIn(List<String> values) {
			addCriterion("LABEL in", values, "label");
			return (Criteria) this;
		}

		public Criteria andLabelNotIn(List<String> values) {
			addCriterion("LABEL not in", values, "label");
			return (Criteria) this;
		}

		public Criteria andLabelBetween(String value1, String value2) {
			addCriterion("LABEL between", value1, value2, "label");
			return (Criteria) this;
		}

		public Criteria andLabelNotBetween(String value1, String value2) {
			addCriterion("LABEL not between", value1, value2, "label");
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
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table FORUM_THEME
	 * @mbggenerated  Fri Dec 30 16:38:20 CST 2011
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
     * This class corresponds to the database table FORUM_THEME
     *
     * @mbggenerated do_not_delete_during_merge Wed Dec 28 15:41:20 CST 2011
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
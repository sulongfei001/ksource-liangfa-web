package com.ksource.liangfa.domain;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class CaseAttachmentExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ATTACHMENT
	 * @mbggenerated  Thu Sep 25 11:42:46 CST 2014
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ATTACHMENT
	 * @mbggenerated  Thu Sep 25 11:42:46 CST 2014
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ATTACHMENT
	 * @mbggenerated  Thu Sep 25 11:42:46 CST 2014
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ATTACHMENT
	 * @mbggenerated  Thu Sep 25 11:42:46 CST 2014
	 */
	public CaseAttachmentExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ATTACHMENT
	 * @mbggenerated  Thu Sep 25 11:42:46 CST 2014
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ATTACHMENT
	 * @mbggenerated  Thu Sep 25 11:42:46 CST 2014
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ATTACHMENT
	 * @mbggenerated  Thu Sep 25 11:42:46 CST 2014
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ATTACHMENT
	 * @mbggenerated  Thu Sep 25 11:42:46 CST 2014
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ATTACHMENT
	 * @mbggenerated  Thu Sep 25 11:42:46 CST 2014
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ATTACHMENT
	 * @mbggenerated  Thu Sep 25 11:42:46 CST 2014
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ATTACHMENT
	 * @mbggenerated  Thu Sep 25 11:42:46 CST 2014
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ATTACHMENT
	 * @mbggenerated  Thu Sep 25 11:42:46 CST 2014
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ATTACHMENT
	 * @mbggenerated  Thu Sep 25 11:42:46 CST 2014
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ATTACHMENT
	 * @mbggenerated  Thu Sep 25 11:42:46 CST 2014
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ATTACHMENT
	 * @mbggenerated  Thu Sep 25 11:42:46 CST 2014
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

		public Criteria andIdEqualTo(Long value) {
			addCriterion("ID =", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotEqualTo(Long value) {
			addCriterion("ID <>", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThan(Long value) {
			addCriterion("ID >", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThanOrEqualTo(Long value) {
			addCriterion("ID >=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThan(Long value) {
			addCriterion("ID <", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThanOrEqualTo(Long value) {
			addCriterion("ID <=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdIn(List<Long> values) {
			addCriterion("ID in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotIn(List<Long> values) {
			addCriterion("ID not in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdBetween(Long value1, Long value2) {
			addCriterion("ID between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotBetween(Long value1, Long value2) {
			addCriterion("ID not between", value1, value2, "id");
			return (Criteria) this;
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

		public Criteria andProcKeyIsNull() {
			addCriterion("PROC_KEY is null");
			return (Criteria) this;
		}

		public Criteria andProcKeyIsNotNull() {
			addCriterion("PROC_KEY is not null");
			return (Criteria) this;
		}

		public Criteria andProcKeyEqualTo(String value) {
			addCriterion("PROC_KEY =", value, "procKey");
			return (Criteria) this;
		}

		public Criteria andProcKeyNotEqualTo(String value) {
			addCriterion("PROC_KEY <>", value, "procKey");
			return (Criteria) this;
		}

		public Criteria andProcKeyGreaterThan(String value) {
			addCriterion("PROC_KEY >", value, "procKey");
			return (Criteria) this;
		}

		public Criteria andProcKeyGreaterThanOrEqualTo(String value) {
			addCriterion("PROC_KEY >=", value, "procKey");
			return (Criteria) this;
		}

		public Criteria andProcKeyLessThan(String value) {
			addCriterion("PROC_KEY <", value, "procKey");
			return (Criteria) this;
		}

		public Criteria andProcKeyLessThanOrEqualTo(String value) {
			addCriterion("PROC_KEY <=", value, "procKey");
			return (Criteria) this;
		}

		public Criteria andProcKeyLike(String value) {
			addCriterion("PROC_KEY like", value, "procKey");
			return (Criteria) this;
		}

		public Criteria andProcKeyNotLike(String value) {
			addCriterion("PROC_KEY not like", value, "procKey");
			return (Criteria) this;
		}

		public Criteria andProcKeyIn(List<String> values) {
			addCriterion("PROC_KEY in", values, "procKey");
			return (Criteria) this;
		}

		public Criteria andProcKeyNotIn(List<String> values) {
			addCriterion("PROC_KEY not in", values, "procKey");
			return (Criteria) this;
		}

		public Criteria andProcKeyBetween(String value1, String value2) {
			addCriterion("PROC_KEY between", value1, value2, "procKey");
			return (Criteria) this;
		}

		public Criteria andProcKeyNotBetween(String value1, String value2) {
			addCriterion("PROC_KEY not between", value1, value2, "procKey");
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

		public Criteria andSwfPathIsNull() {
			addCriterion("SWF_PATH is null");
			return (Criteria) this;
		}

		public Criteria andSwfPathIsNotNull() {
			addCriterion("SWF_PATH is not null");
			return (Criteria) this;
		}

		public Criteria andSwfPathEqualTo(String value) {
			addCriterion("SWF_PATH =", value, "swfPath");
			return (Criteria) this;
		}

		public Criteria andSwfPathNotEqualTo(String value) {
			addCriterion("SWF_PATH <>", value, "swfPath");
			return (Criteria) this;
		}

		public Criteria andSwfPathGreaterThan(String value) {
			addCriterion("SWF_PATH >", value, "swfPath");
			return (Criteria) this;
		}

		public Criteria andSwfPathGreaterThanOrEqualTo(String value) {
			addCriterion("SWF_PATH >=", value, "swfPath");
			return (Criteria) this;
		}

		public Criteria andSwfPathLessThan(String value) {
			addCriterion("SWF_PATH <", value, "swfPath");
			return (Criteria) this;
		}

		public Criteria andSwfPathLessThanOrEqualTo(String value) {
			addCriterion("SWF_PATH <=", value, "swfPath");
			return (Criteria) this;
		}

		public Criteria andSwfPathLike(String value) {
			addCriterion("SWF_PATH like", value, "swfPath");
			return (Criteria) this;
		}

		public Criteria andSwfPathNotLike(String value) {
			addCriterion("SWF_PATH not like", value, "swfPath");
			return (Criteria) this;
		}

		public Criteria andSwfPathIn(List<String> values) {
			addCriterion("SWF_PATH in", values, "swfPath");
			return (Criteria) this;
		}

		public Criteria andSwfPathNotIn(List<String> values) {
			addCriterion("SWF_PATH not in", values, "swfPath");
			return (Criteria) this;
		}

		public Criteria andSwfPathBetween(String value1, String value2) {
			addCriterion("SWF_PATH between", value1, value2, "swfPath");
			return (Criteria) this;
		}

		public Criteria andSwfPathNotBetween(String value1, String value2) {
			addCriterion("SWF_PATH not between", value1, value2, "swfPath");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_ATTACHMENT
	 * @mbggenerated  Thu Sep 25 11:42:46 CST 2014
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
     * This class corresponds to the database table CASE_ATTACHMENT
     *
     * @mbggenerated do_not_delete_during_merge Mon Jun 25 15:45:13 CST 2012
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
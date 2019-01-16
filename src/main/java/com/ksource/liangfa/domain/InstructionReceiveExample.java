package com.ksource.liangfa.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.math.BigDecimal;

public class InstructionReceiveExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_RECEIVE
	 * @mbggenerated  Sat Mar 26 11:05:06 CST 2016
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_RECEIVE
	 * @mbggenerated  Sat Mar 26 11:05:06 CST 2016
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_RECEIVE
	 * @mbggenerated  Sat Mar 26 11:05:06 CST 2016
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_RECEIVE
	 * @mbggenerated  Sat Mar 26 11:05:06 CST 2016
	 */
	public InstructionReceiveExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_RECEIVE
	 * @mbggenerated  Sat Mar 26 11:05:06 CST 2016
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_RECEIVE
	 * @mbggenerated  Sat Mar 26 11:05:06 CST 2016
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_RECEIVE
	 * @mbggenerated  Sat Mar 26 11:05:06 CST 2016
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_RECEIVE
	 * @mbggenerated  Sat Mar 26 11:05:06 CST 2016
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_RECEIVE
	 * @mbggenerated  Sat Mar 26 11:05:06 CST 2016
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_RECEIVE
	 * @mbggenerated  Sat Mar 26 11:05:06 CST 2016
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_RECEIVE
	 * @mbggenerated  Sat Mar 26 11:05:06 CST 2016
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_RECEIVE
	 * @mbggenerated  Sat Mar 26 11:05:06 CST 2016
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_RECEIVE
	 * @mbggenerated  Sat Mar 26 11:05:06 CST 2016
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_RECEIVE
	 * @mbggenerated  Sat Mar 26 11:05:06 CST 2016
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_RECEIVE
	 * @mbggenerated  Sat Mar 26 11:05:06 CST 2016
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

		protected void addCriterionForJDBCDate(String condition, Date value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			addCriterion(condition, new java.sql.Date(value.getTime()),
					property);
		}

		protected void addCriterionForJDBCDate(String condition,
				List<Date> values, String property) {
			if (values == null || values.size() == 0) {
				throw new RuntimeException("Value list for " + property
						+ " cannot be null or empty");
			}
			List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
			Iterator<Date> iter = values.iterator();
			while (iter.hasNext()) {
				dateList.add(new java.sql.Date(iter.next().getTime()));
			}
			addCriterion(condition, dateList, property);
		}

		protected void addCriterionForJDBCDate(String condition, Date value1,
				Date value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			addCriterion(condition, new java.sql.Date(value1.getTime()),
					new java.sql.Date(value2.getTime()), property);
		}

		public Criteria andReceiveIdIsNull() {
			addCriterion("RECEIVE_ID is null");
			return (Criteria) this;
		}

		public Criteria andReceiveIdIsNotNull() {
			addCriterion("RECEIVE_ID is not null");
			return (Criteria) this;
		}

		public Criteria andReceiveIdEqualTo(Integer value) {
			addCriterion("RECEIVE_ID =", value, "receiveId");
			return (Criteria) this;
		}

		public Criteria andReceiveIdNotEqualTo(Integer value) {
			addCriterion("RECEIVE_ID <>", value, "receiveId");
			return (Criteria) this;
		}

		public Criteria andReceiveIdGreaterThan(Integer value) {
			addCriterion("RECEIVE_ID >", value, "receiveId");
			return (Criteria) this;
		}

		public Criteria andReceiveIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("RECEIVE_ID >=", value, "receiveId");
			return (Criteria) this;
		}

		public Criteria andReceiveIdLessThan(Integer value) {
			addCriterion("RECEIVE_ID <", value, "receiveId");
			return (Criteria) this;
		}

		public Criteria andReceiveIdLessThanOrEqualTo(Integer value) {
			addCriterion("RECEIVE_ID <=", value, "receiveId");
			return (Criteria) this;
		}

		public Criteria andReceiveIdIn(List<Integer> values) {
			addCriterion("RECEIVE_ID in", values, "receiveId");
			return (Criteria) this;
		}

		public Criteria andReceiveIdNotIn(List<Integer> values) {
			addCriterion("RECEIVE_ID not in", values, "receiveId");
			return (Criteria) this;
		}

		public Criteria andReceiveIdBetween(Integer value1, Integer value2) {
			addCriterion("RECEIVE_ID between", value1, value2, "receiveId");
			return (Criteria) this;
		}

		public Criteria andReceiveIdNotBetween(Integer value1, Integer value2) {
			addCriterion("RECEIVE_ID not between", value1, value2, "receiveId");
			return (Criteria) this;
		}

		public Criteria andInstructionIdIsNull() {
			addCriterion("INSTRUCTION_ID is null");
			return (Criteria) this;
		}

		public Criteria andInstructionIdIsNotNull() {
			addCriterion("INSTRUCTION_ID is not null");
			return (Criteria) this;
		}

		public Criteria andInstructionIdEqualTo(Integer value) {
			addCriterion("INSTRUCTION_ID =", value, "instructionId");
			return (Criteria) this;
		}

		public Criteria andInstructionIdNotEqualTo(Integer value) {
			addCriterion("INSTRUCTION_ID <>", value, "instructionId");
			return (Criteria) this;
		}

		public Criteria andInstructionIdGreaterThan(Integer value) {
			addCriterion("INSTRUCTION_ID >", value, "instructionId");
			return (Criteria) this;
		}

		public Criteria andInstructionIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("INSTRUCTION_ID >=", value, "instructionId");
			return (Criteria) this;
		}

		public Criteria andInstructionIdLessThan(Integer value) {
			addCriterion("INSTRUCTION_ID <", value, "instructionId");
			return (Criteria) this;
		}

		public Criteria andInstructionIdLessThanOrEqualTo(Integer value) {
			addCriterion("INSTRUCTION_ID <=", value, "instructionId");
			return (Criteria) this;
		}

		public Criteria andInstructionIdIn(List<Integer> values) {
			addCriterion("INSTRUCTION_ID in", values, "instructionId");
			return (Criteria) this;
		}

		public Criteria andInstructionIdNotIn(List<Integer> values) {
			addCriterion("INSTRUCTION_ID not in", values, "instructionId");
			return (Criteria) this;
		}

		public Criteria andInstructionIdBetween(Integer value1, Integer value2) {
			addCriterion("INSTRUCTION_ID between", value1, value2,
					"instructionId");
			return (Criteria) this;
		}

		public Criteria andInstructionIdNotBetween(Integer value1,
				Integer value2) {
			addCriterion("INSTRUCTION_ID not between", value1, value2,
					"instructionId");
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

		public Criteria andSendOrgIsNull() {
			addCriterion("SEND_ORG is null");
			return (Criteria) this;
		}

		public Criteria andSendOrgIsNotNull() {
			addCriterion("SEND_ORG is not null");
			return (Criteria) this;
		}

		public Criteria andSendOrgEqualTo(Integer value) {
			addCriterion("SEND_ORG =", value, "sendOrg");
			return (Criteria) this;
		}

		public Criteria andSendOrgNotEqualTo(Integer value) {
			addCriterion("SEND_ORG <>", value, "sendOrg");
			return (Criteria) this;
		}

		public Criteria andSendOrgGreaterThan(Integer value) {
			addCriterion("SEND_ORG >", value, "sendOrg");
			return (Criteria) this;
		}

		public Criteria andSendOrgGreaterThanOrEqualTo(Integer value) {
			addCriterion("SEND_ORG >=", value, "sendOrg");
			return (Criteria) this;
		}

		public Criteria andSendOrgLessThan(Integer value) {
			addCriterion("SEND_ORG <", value, "sendOrg");
			return (Criteria) this;
		}

		public Criteria andSendOrgLessThanOrEqualTo(Integer value) {
			addCriterion("SEND_ORG <=", value, "sendOrg");
			return (Criteria) this;
		}

		public Criteria andSendOrgIn(List<Integer> values) {
			addCriterion("SEND_ORG in", values, "sendOrg");
			return (Criteria) this;
		}

		public Criteria andSendOrgNotIn(List<Integer> values) {
			addCriterion("SEND_ORG not in", values, "sendOrg");
			return (Criteria) this;
		}

		public Criteria andSendOrgBetween(Integer value1, Integer value2) {
			addCriterion("SEND_ORG between", value1, value2, "sendOrg");
			return (Criteria) this;
		}

		public Criteria andSendOrgNotBetween(Integer value1, Integer value2) {
			addCriterion("SEND_ORG not between", value1, value2, "sendOrg");
			return (Criteria) this;
		}

		public Criteria andSendTimeIsNull() {
			addCriterion("SEND_TIME is null");
			return (Criteria) this;
		}

		public Criteria andSendTimeIsNotNull() {
			addCriterion("SEND_TIME is not null");
			return (Criteria) this;
		}

		public Criteria andSendTimeEqualTo(Date value) {
			addCriterionForJDBCDate("SEND_TIME =", value, "sendTime");
			return (Criteria) this;
		}

		public Criteria andSendTimeNotEqualTo(Date value) {
			addCriterionForJDBCDate("SEND_TIME <>", value, "sendTime");
			return (Criteria) this;
		}

		public Criteria andSendTimeGreaterThan(Date value) {
			addCriterionForJDBCDate("SEND_TIME >", value, "sendTime");
			return (Criteria) this;
		}

		public Criteria andSendTimeGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("SEND_TIME >=", value, "sendTime");
			return (Criteria) this;
		}

		public Criteria andSendTimeLessThan(Date value) {
			addCriterionForJDBCDate("SEND_TIME <", value, "sendTime");
			return (Criteria) this;
		}

		public Criteria andSendTimeLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("SEND_TIME <=", value, "sendTime");
			return (Criteria) this;
		}

		public Criteria andSendTimeIn(List<Date> values) {
			addCriterionForJDBCDate("SEND_TIME in", values, "sendTime");
			return (Criteria) this;
		}

		public Criteria andSendTimeNotIn(List<Date> values) {
			addCriterionForJDBCDate("SEND_TIME not in", values, "sendTime");
			return (Criteria) this;
		}

		public Criteria andSendTimeBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("SEND_TIME between", value1, value2,
					"sendTime");
			return (Criteria) this;
		}

		public Criteria andSendTimeNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("SEND_TIME not between", value1, value2,
					"sendTime");
			return (Criteria) this;
		}

		public Criteria andReceiveOrgIsNull() {
			addCriterion("RECEIVE_ORG is null");
			return (Criteria) this;
		}

		public Criteria andReceiveOrgIsNotNull() {
			addCriterion("RECEIVE_ORG is not null");
			return (Criteria) this;
		}

		public Criteria andReceiveOrgEqualTo(String value) {
			addCriterion("RECEIVE_ORG =", value, "receiveOrg");
			return (Criteria) this;
		}

		public Criteria andReceiveOrgNotEqualTo(String value) {
			addCriterion("RECEIVE_ORG <>", value, "receiveOrg");
			return (Criteria) this;
		}

		public Criteria andReceiveOrgGreaterThan(String value) {
			addCriterion("RECEIVE_ORG >", value, "receiveOrg");
			return (Criteria) this;
		}

		public Criteria andReceiveOrgGreaterThanOrEqualTo(String value) {
			addCriterion("RECEIVE_ORG >=", value, "receiveOrg");
			return (Criteria) this;
		}

		public Criteria andReceiveOrgLessThan(String value) {
			addCriterion("RECEIVE_ORG <", value, "receiveOrg");
			return (Criteria) this;
		}

		public Criteria andReceiveOrgLessThanOrEqualTo(String value) {
			addCriterion("RECEIVE_ORG <=", value, "receiveOrg");
			return (Criteria) this;
		}

		public Criteria andReceiveOrgLike(String value) {
			addCriterion("RECEIVE_ORG like", value, "receiveOrg");
			return (Criteria) this;
		}

		public Criteria andReceiveOrgNotLike(String value) {
			addCriterion("RECEIVE_ORG not like", value, "receiveOrg");
			return (Criteria) this;
		}

		public Criteria andReceiveOrgIn(List<String> values) {
			addCriterion("RECEIVE_ORG in", values, "receiveOrg");
			return (Criteria) this;
		}

		public Criteria andReceiveOrgNotIn(List<String> values) {
			addCriterion("RECEIVE_ORG not in", values, "receiveOrg");
			return (Criteria) this;
		}

		public Criteria andReceiveOrgBetween(String value1, String value2) {
			addCriterion("RECEIVE_ORG between", value1, value2, "receiveOrg");
			return (Criteria) this;
		}

		public Criteria andReceiveOrgNotBetween(String value1, String value2) {
			addCriterion("RECEIVE_ORG not between", value1, value2,
					"receiveOrg");
			return (Criteria) this;
		}

		public Criteria andCompleteTimeIsNull() {
			addCriterion("COMPLETE_TIME is null");
			return (Criteria) this;
		}

		public Criteria andCompleteTimeIsNotNull() {
			addCriterion("COMPLETE_TIME is not null");
			return (Criteria) this;
		}

		public Criteria andCompleteTimeEqualTo(Date value) {
			addCriterionForJDBCDate("COMPLETE_TIME =", value, "completeTime");
			return (Criteria) this;
		}

		public Criteria andCompleteTimeNotEqualTo(Date value) {
			addCriterionForJDBCDate("COMPLETE_TIME <>", value, "completeTime");
			return (Criteria) this;
		}

		public Criteria andCompleteTimeGreaterThan(Date value) {
			addCriterionForJDBCDate("COMPLETE_TIME >", value, "completeTime");
			return (Criteria) this;
		}

		public Criteria andCompleteTimeGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("COMPLETE_TIME >=", value, "completeTime");
			return (Criteria) this;
		}

		public Criteria andCompleteTimeLessThan(Date value) {
			addCriterionForJDBCDate("COMPLETE_TIME <", value, "completeTime");
			return (Criteria) this;
		}

		public Criteria andCompleteTimeLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("COMPLETE_TIME <=", value, "completeTime");
			return (Criteria) this;
		}

		public Criteria andCompleteTimeIn(List<Date> values) {
			addCriterionForJDBCDate("COMPLETE_TIME in", values, "completeTime");
			return (Criteria) this;
		}

		public Criteria andCompleteTimeNotIn(List<Date> values) {
			addCriterionForJDBCDate("COMPLETE_TIME not in", values,
					"completeTime");
			return (Criteria) this;
		}

		public Criteria andCompleteTimeBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("COMPLETE_TIME between", value1, value2,
					"completeTime");
			return (Criteria) this;
		}

		public Criteria andCompleteTimeNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("COMPLETE_TIME not between", value1,
					value2, "completeTime");
			return (Criteria) this;
		}

		public Criteria andStatusIsNull() {
			addCriterion("STATUS is null");
			return (Criteria) this;
		}

		public Criteria andStatusIsNotNull() {
			addCriterion("STATUS is not null");
			return (Criteria) this;
		}

		public Criteria andStatusEqualTo(Integer value) {
			addCriterion("STATUS =", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusNotEqualTo(Integer value) {
			addCriterion("STATUS <>", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusGreaterThan(Integer value) {
			addCriterion("STATUS >", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
			addCriterion("STATUS >=", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusLessThan(Integer value) {
			addCriterion("STATUS <", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusLessThanOrEqualTo(Integer value) {
			addCriterion("STATUS <=", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusIn(List<Integer> values) {
			addCriterion("STATUS in", values, "status");
			return (Criteria) this;
		}

		public Criteria andStatusNotIn(List<Integer> values) {
			addCriterion("STATUS not in", values, "status");
			return (Criteria) this;
		}

		public Criteria andStatusBetween(Integer value1, Integer value2) {
			addCriterion("STATUS between", value1, value2, "status");
			return (Criteria) this;
		}

		public Criteria andStatusNotBetween(Integer value1, Integer value2) {
			addCriterion("STATUS not between", value1, value2, "status");
			return (Criteria) this;
		}

		public Criteria andSignTimeIsNull() {
			addCriterion("SIGN_TIME is null");
			return (Criteria) this;
		}

		public Criteria andSignTimeIsNotNull() {
			addCriterion("SIGN_TIME is not null");
			return (Criteria) this;
		}

		public Criteria andSignTimeEqualTo(Date value) {
			addCriterionForJDBCDate("SIGN_TIME =", value, "signTime");
			return (Criteria) this;
		}

		public Criteria andSignTimeNotEqualTo(Date value) {
			addCriterionForJDBCDate("SIGN_TIME <>", value, "signTime");
			return (Criteria) this;
		}

		public Criteria andSignTimeGreaterThan(Date value) {
			addCriterionForJDBCDate("SIGN_TIME >", value, "signTime");
			return (Criteria) this;
		}

		public Criteria andSignTimeGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("SIGN_TIME >=", value, "signTime");
			return (Criteria) this;
		}

		public Criteria andSignTimeLessThan(Date value) {
			addCriterionForJDBCDate("SIGN_TIME <", value, "signTime");
			return (Criteria) this;
		}

		public Criteria andSignTimeLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("SIGN_TIME <=", value, "signTime");
			return (Criteria) this;
		}

		public Criteria andSignTimeIn(List<Date> values) {
			addCriterionForJDBCDate("SIGN_TIME in", values, "signTime");
			return (Criteria) this;
		}

		public Criteria andSignTimeNotIn(List<Date> values) {
			addCriterionForJDBCDate("SIGN_TIME not in", values, "signTime");
			return (Criteria) this;
		}

		public Criteria andSignTimeBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("SIGN_TIME between", value1, value2,
					"signTime");
			return (Criteria) this;
		}

		public Criteria andSignTimeNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("SIGN_TIME not between", value1, value2,
					"signTime");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_RECEIVE
	 * @mbggenerated  Sat Mar 26 11:05:06 CST 2016
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
     * This class corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_RECEIVE
     *
     * @mbggenerated do_not_delete_during_merge Tue Mar 22 16:13:14 CST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
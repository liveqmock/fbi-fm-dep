package skyline.repository.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ActivemqMsgsExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTIVEMQ_MSGS
     *
     * @mbggenerated Fri Oct 28 13:00:28 CST 2011
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTIVEMQ_MSGS
     *
     * @mbggenerated Fri Oct 28 13:00:28 CST 2011
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTIVEMQ_MSGS
     *
     * @mbggenerated Fri Oct 28 13:00:28 CST 2011
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTIVEMQ_MSGS
     *
     * @mbggenerated Fri Oct 28 13:00:28 CST 2011
     */
    public ActivemqMsgsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTIVEMQ_MSGS
     *
     * @mbggenerated Fri Oct 28 13:00:28 CST 2011
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTIVEMQ_MSGS
     *
     * @mbggenerated Fri Oct 28 13:00:28 CST 2011
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTIVEMQ_MSGS
     *
     * @mbggenerated Fri Oct 28 13:00:28 CST 2011
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTIVEMQ_MSGS
     *
     * @mbggenerated Fri Oct 28 13:00:28 CST 2011
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTIVEMQ_MSGS
     *
     * @mbggenerated Fri Oct 28 13:00:28 CST 2011
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTIVEMQ_MSGS
     *
     * @mbggenerated Fri Oct 28 13:00:28 CST 2011
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTIVEMQ_MSGS
     *
     * @mbggenerated Fri Oct 28 13:00:28 CST 2011
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTIVEMQ_MSGS
     *
     * @mbggenerated Fri Oct 28 13:00:28 CST 2011
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTIVEMQ_MSGS
     *
     * @mbggenerated Fri Oct 28 13:00:28 CST 2011
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTIVEMQ_MSGS
     *
     * @mbggenerated Fri Oct 28 13:00:28 CST 2011
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ACTIVEMQ_MSGS
     *
     * @mbggenerated Fri Oct 28 13:00:28 CST 2011
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

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
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

        public Criteria andIdEqualTo(BigDecimal value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(BigDecimal value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(BigDecimal value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(BigDecimal value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<BigDecimal> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<BigDecimal> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andContainerIsNull() {
            addCriterion("CONTAINER is null");
            return (Criteria) this;
        }

        public Criteria andContainerIsNotNull() {
            addCriterion("CONTAINER is not null");
            return (Criteria) this;
        }

        public Criteria andContainerEqualTo(String value) {
            addCriterion("CONTAINER =", value, "container");
            return (Criteria) this;
        }

        public Criteria andContainerNotEqualTo(String value) {
            addCriterion("CONTAINER <>", value, "container");
            return (Criteria) this;
        }

        public Criteria andContainerGreaterThan(String value) {
            addCriterion("CONTAINER >", value, "container");
            return (Criteria) this;
        }

        public Criteria andContainerGreaterThanOrEqualTo(String value) {
            addCriterion("CONTAINER >=", value, "container");
            return (Criteria) this;
        }

        public Criteria andContainerLessThan(String value) {
            addCriterion("CONTAINER <", value, "container");
            return (Criteria) this;
        }

        public Criteria andContainerLessThanOrEqualTo(String value) {
            addCriterion("CONTAINER <=", value, "container");
            return (Criteria) this;
        }

        public Criteria andContainerLike(String value) {
            addCriterion("CONTAINER like", value, "container");
            return (Criteria) this;
        }

        public Criteria andContainerNotLike(String value) {
            addCriterion("CONTAINER not like", value, "container");
            return (Criteria) this;
        }

        public Criteria andContainerIn(List<String> values) {
            addCriterion("CONTAINER in", values, "container");
            return (Criteria) this;
        }

        public Criteria andContainerNotIn(List<String> values) {
            addCriterion("CONTAINER not in", values, "container");
            return (Criteria) this;
        }

        public Criteria andContainerBetween(String value1, String value2) {
            addCriterion("CONTAINER between", value1, value2, "container");
            return (Criteria) this;
        }

        public Criteria andContainerNotBetween(String value1, String value2) {
            addCriterion("CONTAINER not between", value1, value2, "container");
            return (Criteria) this;
        }

        public Criteria andMsgidProdIsNull() {
            addCriterion("MSGID_PROD is null");
            return (Criteria) this;
        }

        public Criteria andMsgidProdIsNotNull() {
            addCriterion("MSGID_PROD is not null");
            return (Criteria) this;
        }

        public Criteria andMsgidProdEqualTo(String value) {
            addCriterion("MSGID_PROD =", value, "msgidProd");
            return (Criteria) this;
        }

        public Criteria andMsgidProdNotEqualTo(String value) {
            addCriterion("MSGID_PROD <>", value, "msgidProd");
            return (Criteria) this;
        }

        public Criteria andMsgidProdGreaterThan(String value) {
            addCriterion("MSGID_PROD >", value, "msgidProd");
            return (Criteria) this;
        }

        public Criteria andMsgidProdGreaterThanOrEqualTo(String value) {
            addCriterion("MSGID_PROD >=", value, "msgidProd");
            return (Criteria) this;
        }

        public Criteria andMsgidProdLessThan(String value) {
            addCriterion("MSGID_PROD <", value, "msgidProd");
            return (Criteria) this;
        }

        public Criteria andMsgidProdLessThanOrEqualTo(String value) {
            addCriterion("MSGID_PROD <=", value, "msgidProd");
            return (Criteria) this;
        }

        public Criteria andMsgidProdLike(String value) {
            addCriterion("MSGID_PROD like", value, "msgidProd");
            return (Criteria) this;
        }

        public Criteria andMsgidProdNotLike(String value) {
            addCriterion("MSGID_PROD not like", value, "msgidProd");
            return (Criteria) this;
        }

        public Criteria andMsgidProdIn(List<String> values) {
            addCriterion("MSGID_PROD in", values, "msgidProd");
            return (Criteria) this;
        }

        public Criteria andMsgidProdNotIn(List<String> values) {
            addCriterion("MSGID_PROD not in", values, "msgidProd");
            return (Criteria) this;
        }

        public Criteria andMsgidProdBetween(String value1, String value2) {
            addCriterion("MSGID_PROD between", value1, value2, "msgidProd");
            return (Criteria) this;
        }

        public Criteria andMsgidProdNotBetween(String value1, String value2) {
            addCriterion("MSGID_PROD not between", value1, value2, "msgidProd");
            return (Criteria) this;
        }

        public Criteria andMsgidSeqIsNull() {
            addCriterion("MSGID_SEQ is null");
            return (Criteria) this;
        }

        public Criteria andMsgidSeqIsNotNull() {
            addCriterion("MSGID_SEQ is not null");
            return (Criteria) this;
        }

        public Criteria andMsgidSeqEqualTo(BigDecimal value) {
            addCriterion("MSGID_SEQ =", value, "msgidSeq");
            return (Criteria) this;
        }

        public Criteria andMsgidSeqNotEqualTo(BigDecimal value) {
            addCriterion("MSGID_SEQ <>", value, "msgidSeq");
            return (Criteria) this;
        }

        public Criteria andMsgidSeqGreaterThan(BigDecimal value) {
            addCriterion("MSGID_SEQ >", value, "msgidSeq");
            return (Criteria) this;
        }

        public Criteria andMsgidSeqGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MSGID_SEQ >=", value, "msgidSeq");
            return (Criteria) this;
        }

        public Criteria andMsgidSeqLessThan(BigDecimal value) {
            addCriterion("MSGID_SEQ <", value, "msgidSeq");
            return (Criteria) this;
        }

        public Criteria andMsgidSeqLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MSGID_SEQ <=", value, "msgidSeq");
            return (Criteria) this;
        }

        public Criteria andMsgidSeqIn(List<BigDecimal> values) {
            addCriterion("MSGID_SEQ in", values, "msgidSeq");
            return (Criteria) this;
        }

        public Criteria andMsgidSeqNotIn(List<BigDecimal> values) {
            addCriterion("MSGID_SEQ not in", values, "msgidSeq");
            return (Criteria) this;
        }

        public Criteria andMsgidSeqBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MSGID_SEQ between", value1, value2, "msgidSeq");
            return (Criteria) this;
        }

        public Criteria andMsgidSeqNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MSGID_SEQ not between", value1, value2, "msgidSeq");
            return (Criteria) this;
        }

        public Criteria andExpirationIsNull() {
            addCriterion("EXPIRATION is null");
            return (Criteria) this;
        }

        public Criteria andExpirationIsNotNull() {
            addCriterion("EXPIRATION is not null");
            return (Criteria) this;
        }

        public Criteria andExpirationEqualTo(BigDecimal value) {
            addCriterion("EXPIRATION =", value, "expiration");
            return (Criteria) this;
        }

        public Criteria andExpirationNotEqualTo(BigDecimal value) {
            addCriterion("EXPIRATION <>", value, "expiration");
            return (Criteria) this;
        }

        public Criteria andExpirationGreaterThan(BigDecimal value) {
            addCriterion("EXPIRATION >", value, "expiration");
            return (Criteria) this;
        }

        public Criteria andExpirationGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("EXPIRATION >=", value, "expiration");
            return (Criteria) this;
        }

        public Criteria andExpirationLessThan(BigDecimal value) {
            addCriterion("EXPIRATION <", value, "expiration");
            return (Criteria) this;
        }

        public Criteria andExpirationLessThanOrEqualTo(BigDecimal value) {
            addCriterion("EXPIRATION <=", value, "expiration");
            return (Criteria) this;
        }

        public Criteria andExpirationIn(List<BigDecimal> values) {
            addCriterion("EXPIRATION in", values, "expiration");
            return (Criteria) this;
        }

        public Criteria andExpirationNotIn(List<BigDecimal> values) {
            addCriterion("EXPIRATION not in", values, "expiration");
            return (Criteria) this;
        }

        public Criteria andExpirationBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EXPIRATION between", value1, value2, "expiration");
            return (Criteria) this;
        }

        public Criteria andExpirationNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EXPIRATION not between", value1, value2, "expiration");
            return (Criteria) this;
        }

        public Criteria andPriorityIsNull() {
            addCriterion("PRIORITY is null");
            return (Criteria) this;
        }

        public Criteria andPriorityIsNotNull() {
            addCriterion("PRIORITY is not null");
            return (Criteria) this;
        }

        public Criteria andPriorityEqualTo(BigDecimal value) {
            addCriterion("PRIORITY =", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotEqualTo(BigDecimal value) {
            addCriterion("PRIORITY <>", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityGreaterThan(BigDecimal value) {
            addCriterion("PRIORITY >", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRIORITY >=", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityLessThan(BigDecimal value) {
            addCriterion("PRIORITY <", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRIORITY <=", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityIn(List<BigDecimal> values) {
            addCriterion("PRIORITY in", values, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotIn(List<BigDecimal> values) {
            addCriterion("PRIORITY not in", values, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRIORITY between", value1, value2, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRIORITY not between", value1, value2, "priority");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ACTIVEMQ_MSGS
     *
     * @mbggenerated do_not_delete_during_merge Fri Oct 28 13:00:28 CST 2011
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ACTIVEMQ_MSGS
     *
     * @mbggenerated Fri Oct 28 13:00:28 CST 2011
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

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
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
}
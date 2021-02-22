package com.iscas.biz.domain.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrgExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrgExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
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

        public Criteria andOrgIdIsNull() {
            addCriterion("org_id is null");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNotNull() {
            addCriterion("org_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrgIdEqualTo(Integer value) {
            addCriterion("org_id =", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotEqualTo(Integer value) {
            addCriterion("org_id <>", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThan(Integer value) {
            addCriterion("org_id >", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("org_id >=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThan(Integer value) {
            addCriterion("org_id <", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThanOrEqualTo(Integer value) {
            addCriterion("org_id <=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdIn(List<Integer> values) {
            addCriterion("org_id in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotIn(List<Integer> values) {
            addCriterion("org_id not in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdBetween(Integer value1, Integer value2) {
            addCriterion("org_id between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotBetween(Integer value1, Integer value2) {
            addCriterion("org_id not between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgNameIsNull() {
            addCriterion("org_name is null");
            return (Criteria) this;
        }

        public Criteria andOrgNameIsNotNull() {
            addCriterion("org_name is not null");
            return (Criteria) this;
        }

        public Criteria andOrgNameEqualTo(String value) {
            addCriterion("org_name =", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotEqualTo(String value) {
            addCriterion("org_name <>", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameGreaterThan(String value) {
            addCriterion("org_name >", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameGreaterThanOrEqualTo(String value) {
            addCriterion("org_name >=", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameLessThan(String value) {
            addCriterion("org_name <", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameLessThanOrEqualTo(String value) {
            addCriterion("org_name <=", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameLike(String value) {
            addCriterion("org_name like", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotLike(String value) {
            addCriterion("org_name not like", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameIn(List<String> values) {
            addCriterion("org_name in", values, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotIn(List<String> values) {
            addCriterion("org_name not in", values, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameBetween(String value1, String value2) {
            addCriterion("org_name between", value1, value2, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotBetween(String value1, String value2) {
            addCriterion("org_name not between", value1, value2, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgPidIsNull() {
            addCriterion("org_pid is null");
            return (Criteria) this;
        }

        public Criteria andOrgPidIsNotNull() {
            addCriterion("org_pid is not null");
            return (Criteria) this;
        }

        public Criteria andOrgPidEqualTo(Integer value) {
            addCriterion("org_pid =", value, "orgPid");
            return (Criteria) this;
        }

        public Criteria andOrgPidNotEqualTo(Integer value) {
            addCriterion("org_pid <>", value, "orgPid");
            return (Criteria) this;
        }

        public Criteria andOrgPidGreaterThan(Integer value) {
            addCriterion("org_pid >", value, "orgPid");
            return (Criteria) this;
        }

        public Criteria andOrgPidGreaterThanOrEqualTo(Integer value) {
            addCriterion("org_pid >=", value, "orgPid");
            return (Criteria) this;
        }

        public Criteria andOrgPidLessThan(Integer value) {
            addCriterion("org_pid <", value, "orgPid");
            return (Criteria) this;
        }

        public Criteria andOrgPidLessThanOrEqualTo(Integer value) {
            addCriterion("org_pid <=", value, "orgPid");
            return (Criteria) this;
        }

        public Criteria andOrgPidIn(List<Integer> values) {
            addCriterion("org_pid in", values, "orgPid");
            return (Criteria) this;
        }

        public Criteria andOrgPidNotIn(List<Integer> values) {
            addCriterion("org_pid not in", values, "orgPid");
            return (Criteria) this;
        }

        public Criteria andOrgPidBetween(Integer value1, Integer value2) {
            addCriterion("org_pid between", value1, value2, "orgPid");
            return (Criteria) this;
        }

        public Criteria andOrgPidNotBetween(Integer value1, Integer value2) {
            addCriterion("org_pid not between", value1, value2, "orgPid");
            return (Criteria) this;
        }

        public Criteria andOrgCreateTimeIsNull() {
            addCriterion("org_create_time is null");
            return (Criteria) this;
        }

        public Criteria andOrgCreateTimeIsNotNull() {
            addCriterion("org_create_time is not null");
            return (Criteria) this;
        }

        public Criteria andOrgCreateTimeEqualTo(Date value) {
            addCriterion("org_create_time =", value, "orgCreateTime");
            return (Criteria) this;
        }

        public Criteria andOrgCreateTimeNotEqualTo(Date value) {
            addCriterion("org_create_time <>", value, "orgCreateTime");
            return (Criteria) this;
        }

        public Criteria andOrgCreateTimeGreaterThan(Date value) {
            addCriterion("org_create_time >", value, "orgCreateTime");
            return (Criteria) this;
        }

        public Criteria andOrgCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("org_create_time >=", value, "orgCreateTime");
            return (Criteria) this;
        }

        public Criteria andOrgCreateTimeLessThan(Date value) {
            addCriterion("org_create_time <", value, "orgCreateTime");
            return (Criteria) this;
        }

        public Criteria andOrgCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("org_create_time <=", value, "orgCreateTime");
            return (Criteria) this;
        }

        public Criteria andOrgCreateTimeIn(List<Date> values) {
            addCriterion("org_create_time in", values, "orgCreateTime");
            return (Criteria) this;
        }

        public Criteria andOrgCreateTimeNotIn(List<Date> values) {
            addCriterion("org_create_time not in", values, "orgCreateTime");
            return (Criteria) this;
        }

        public Criteria andOrgCreateTimeBetween(Date value1, Date value2) {
            addCriterion("org_create_time between", value1, value2, "orgCreateTime");
            return (Criteria) this;
        }

        public Criteria andOrgCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("org_create_time not between", value1, value2, "orgCreateTime");
            return (Criteria) this;
        }

        public Criteria andOrgUpdateTimeIsNull() {
            addCriterion("org_update_time is null");
            return (Criteria) this;
        }

        public Criteria andOrgUpdateTimeIsNotNull() {
            addCriterion("org_update_time is not null");
            return (Criteria) this;
        }

        public Criteria andOrgUpdateTimeEqualTo(Date value) {
            addCriterion("org_update_time =", value, "orgUpdateTime");
            return (Criteria) this;
        }

        public Criteria andOrgUpdateTimeNotEqualTo(Date value) {
            addCriterion("org_update_time <>", value, "orgUpdateTime");
            return (Criteria) this;
        }

        public Criteria andOrgUpdateTimeGreaterThan(Date value) {
            addCriterion("org_update_time >", value, "orgUpdateTime");
            return (Criteria) this;
        }

        public Criteria andOrgUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("org_update_time >=", value, "orgUpdateTime");
            return (Criteria) this;
        }

        public Criteria andOrgUpdateTimeLessThan(Date value) {
            addCriterion("org_update_time <", value, "orgUpdateTime");
            return (Criteria) this;
        }

        public Criteria andOrgUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("org_update_time <=", value, "orgUpdateTime");
            return (Criteria) this;
        }

        public Criteria andOrgUpdateTimeIn(List<Date> values) {
            addCriterion("org_update_time in", values, "orgUpdateTime");
            return (Criteria) this;
        }

        public Criteria andOrgUpdateTimeNotIn(List<Date> values) {
            addCriterion("org_update_time not in", values, "orgUpdateTime");
            return (Criteria) this;
        }

        public Criteria andOrgUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("org_update_time between", value1, value2, "orgUpdateTime");
            return (Criteria) this;
        }

        public Criteria andOrgUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("org_update_time not between", value1, value2, "orgUpdateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

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
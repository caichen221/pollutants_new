package com.iscas.biz.domain.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MenuExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MenuExample() {
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

        public Criteria andMenuIdIsNull() {
            addCriterion("menu_id is null");
            return (Criteria) this;
        }

        public Criteria andMenuIdIsNotNull() {
            addCriterion("menu_id is not null");
            return (Criteria) this;
        }

        public Criteria andMenuIdEqualTo(Integer value) {
            addCriterion("menu_id =", value, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdNotEqualTo(Integer value) {
            addCriterion("menu_id <>", value, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdGreaterThan(Integer value) {
            addCriterion("menu_id >", value, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("menu_id >=", value, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdLessThan(Integer value) {
            addCriterion("menu_id <", value, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdLessThanOrEqualTo(Integer value) {
            addCriterion("menu_id <=", value, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdIn(List<Integer> values) {
            addCriterion("menu_id in", values, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdNotIn(List<Integer> values) {
            addCriterion("menu_id not in", values, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdBetween(Integer value1, Integer value2) {
            addCriterion("menu_id between", value1, value2, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdNotBetween(Integer value1, Integer value2) {
            addCriterion("menu_id not between", value1, value2, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuPidIsNull() {
            addCriterion("menu_pid is null");
            return (Criteria) this;
        }

        public Criteria andMenuPidIsNotNull() {
            addCriterion("menu_pid is not null");
            return (Criteria) this;
        }

        public Criteria andMenuPidEqualTo(Integer value) {
            addCriterion("menu_pid =", value, "menuPid");
            return (Criteria) this;
        }

        public Criteria andMenuPidNotEqualTo(Integer value) {
            addCriterion("menu_pid <>", value, "menuPid");
            return (Criteria) this;
        }

        public Criteria andMenuPidGreaterThan(Integer value) {
            addCriterion("menu_pid >", value, "menuPid");
            return (Criteria) this;
        }

        public Criteria andMenuPidGreaterThanOrEqualTo(Integer value) {
            addCriterion("menu_pid >=", value, "menuPid");
            return (Criteria) this;
        }

        public Criteria andMenuPidLessThan(Integer value) {
            addCriterion("menu_pid <", value, "menuPid");
            return (Criteria) this;
        }

        public Criteria andMenuPidLessThanOrEqualTo(Integer value) {
            addCriterion("menu_pid <=", value, "menuPid");
            return (Criteria) this;
        }

        public Criteria andMenuPidIn(List<Integer> values) {
            addCriterion("menu_pid in", values, "menuPid");
            return (Criteria) this;
        }

        public Criteria andMenuPidNotIn(List<Integer> values) {
            addCriterion("menu_pid not in", values, "menuPid");
            return (Criteria) this;
        }

        public Criteria andMenuPidBetween(Integer value1, Integer value2) {
            addCriterion("menu_pid between", value1, value2, "menuPid");
            return (Criteria) this;
        }

        public Criteria andMenuPidNotBetween(Integer value1, Integer value2) {
            addCriterion("menu_pid not between", value1, value2, "menuPid");
            return (Criteria) this;
        }

        public Criteria andMenuPageIsNull() {
            addCriterion("menu_page is null");
            return (Criteria) this;
        }

        public Criteria andMenuPageIsNotNull() {
            addCriterion("menu_page is not null");
            return (Criteria) this;
        }

        public Criteria andMenuPageEqualTo(String value) {
            addCriterion("menu_page =", value, "menuPage");
            return (Criteria) this;
        }

        public Criteria andMenuPageNotEqualTo(String value) {
            addCriterion("menu_page <>", value, "menuPage");
            return (Criteria) this;
        }

        public Criteria andMenuPageGreaterThan(String value) {
            addCriterion("menu_page >", value, "menuPage");
            return (Criteria) this;
        }

        public Criteria andMenuPageGreaterThanOrEqualTo(String value) {
            addCriterion("menu_page >=", value, "menuPage");
            return (Criteria) this;
        }

        public Criteria andMenuPageLessThan(String value) {
            addCriterion("menu_page <", value, "menuPage");
            return (Criteria) this;
        }

        public Criteria andMenuPageLessThanOrEqualTo(String value) {
            addCriterion("menu_page <=", value, "menuPage");
            return (Criteria) this;
        }

        public Criteria andMenuPageLike(String value) {
            addCriterion("menu_page like", value, "menuPage");
            return (Criteria) this;
        }

        public Criteria andMenuPageNotLike(String value) {
            addCriterion("menu_page not like", value, "menuPage");
            return (Criteria) this;
        }

        public Criteria andMenuPageIn(List<String> values) {
            addCriterion("menu_page in", values, "menuPage");
            return (Criteria) this;
        }

        public Criteria andMenuPageNotIn(List<String> values) {
            addCriterion("menu_page not in", values, "menuPage");
            return (Criteria) this;
        }

        public Criteria andMenuPageBetween(String value1, String value2) {
            addCriterion("menu_page between", value1, value2, "menuPage");
            return (Criteria) this;
        }

        public Criteria andMenuPageNotBetween(String value1, String value2) {
            addCriterion("menu_page not between", value1, value2, "menuPage");
            return (Criteria) this;
        }

        public Criteria andMenuCreateTimeIsNull() {
            addCriterion("menu_create_time is null");
            return (Criteria) this;
        }

        public Criteria andMenuCreateTimeIsNotNull() {
            addCriterion("menu_create_time is not null");
            return (Criteria) this;
        }

        public Criteria andMenuCreateTimeEqualTo(Date value) {
            addCriterion("menu_create_time =", value, "menuCreateTime");
            return (Criteria) this;
        }

        public Criteria andMenuCreateTimeNotEqualTo(Date value) {
            addCriterion("menu_create_time <>", value, "menuCreateTime");
            return (Criteria) this;
        }

        public Criteria andMenuCreateTimeGreaterThan(Date value) {
            addCriterion("menu_create_time >", value, "menuCreateTime");
            return (Criteria) this;
        }

        public Criteria andMenuCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("menu_create_time >=", value, "menuCreateTime");
            return (Criteria) this;
        }

        public Criteria andMenuCreateTimeLessThan(Date value) {
            addCriterion("menu_create_time <", value, "menuCreateTime");
            return (Criteria) this;
        }

        public Criteria andMenuCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("menu_create_time <=", value, "menuCreateTime");
            return (Criteria) this;
        }

        public Criteria andMenuCreateTimeIn(List<Date> values) {
            addCriterion("menu_create_time in", values, "menuCreateTime");
            return (Criteria) this;
        }

        public Criteria andMenuCreateTimeNotIn(List<Date> values) {
            addCriterion("menu_create_time not in", values, "menuCreateTime");
            return (Criteria) this;
        }

        public Criteria andMenuCreateTimeBetween(Date value1, Date value2) {
            addCriterion("menu_create_time between", value1, value2, "menuCreateTime");
            return (Criteria) this;
        }

        public Criteria andMenuCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("menu_create_time not between", value1, value2, "menuCreateTime");
            return (Criteria) this;
        }

        public Criteria andMenuUpdateTimeIsNull() {
            addCriterion("menu_update_time is null");
            return (Criteria) this;
        }

        public Criteria andMenuUpdateTimeIsNotNull() {
            addCriterion("menu_update_time is not null");
            return (Criteria) this;
        }

        public Criteria andMenuUpdateTimeEqualTo(Date value) {
            addCriterion("menu_update_time =", value, "menuUpdateTime");
            return (Criteria) this;
        }

        public Criteria andMenuUpdateTimeNotEqualTo(Date value) {
            addCriterion("menu_update_time <>", value, "menuUpdateTime");
            return (Criteria) this;
        }

        public Criteria andMenuUpdateTimeGreaterThan(Date value) {
            addCriterion("menu_update_time >", value, "menuUpdateTime");
            return (Criteria) this;
        }

        public Criteria andMenuUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("menu_update_time >=", value, "menuUpdateTime");
            return (Criteria) this;
        }

        public Criteria andMenuUpdateTimeLessThan(Date value) {
            addCriterion("menu_update_time <", value, "menuUpdateTime");
            return (Criteria) this;
        }

        public Criteria andMenuUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("menu_update_time <=", value, "menuUpdateTime");
            return (Criteria) this;
        }

        public Criteria andMenuUpdateTimeIn(List<Date> values) {
            addCriterion("menu_update_time in", values, "menuUpdateTime");
            return (Criteria) this;
        }

        public Criteria andMenuUpdateTimeNotIn(List<Date> values) {
            addCriterion("menu_update_time not in", values, "menuUpdateTime");
            return (Criteria) this;
        }

        public Criteria andMenuUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("menu_update_time between", value1, value2, "menuUpdateTime");
            return (Criteria) this;
        }

        public Criteria andMenuUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("menu_update_time not between", value1, value2, "menuUpdateTime");
            return (Criteria) this;
        }

        public Criteria andMenuNameIsNull() {
            addCriterion("menu_name is null");
            return (Criteria) this;
        }

        public Criteria andMenuNameIsNotNull() {
            addCriterion("menu_name is not null");
            return (Criteria) this;
        }

        public Criteria andMenuNameEqualTo(String value) {
            addCriterion("menu_name =", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotEqualTo(String value) {
            addCriterion("menu_name <>", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameGreaterThan(String value) {
            addCriterion("menu_name >", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameGreaterThanOrEqualTo(String value) {
            addCriterion("menu_name >=", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameLessThan(String value) {
            addCriterion("menu_name <", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameLessThanOrEqualTo(String value) {
            addCriterion("menu_name <=", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameLike(String value) {
            addCriterion("menu_name like", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotLike(String value) {
            addCriterion("menu_name not like", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameIn(List<String> values) {
            addCriterion("menu_name in", values, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotIn(List<String> values) {
            addCriterion("menu_name not in", values, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameBetween(String value1, String value2) {
            addCriterion("menu_name between", value1, value2, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotBetween(String value1, String value2) {
            addCriterion("menu_name not between", value1, value2, "menuName");
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
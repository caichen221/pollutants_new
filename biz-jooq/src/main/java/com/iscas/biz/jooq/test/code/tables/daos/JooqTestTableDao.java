/*
 * This file is generated by jOOQ.
 */
package com.iscas.biz.jooq.test.code.tables.daos;


import com.iscas.biz.jooq.test.code.tables.records.JooqTestTableRecord;

import java.util.List;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JooqTestTableDao extends DAOImpl<JooqTestTableRecord, com.iscas.biz.jooq.test.code.tables.pojos.JooqTestTable, Integer> {

    /**
     * Create a new JooqTestTableDao without any configuration
     */
    public JooqTestTableDao() {
        super(com.iscas.biz.jooq.test.code.tables.JooqTestTable.JOOQ_TEST_TABLE, com.iscas.biz.jooq.test.code.tables.pojos.JooqTestTable.class);
    }

    /**
     * Create a new JooqTestTableDao with an attached configuration
     */
    public JooqTestTableDao(Configuration configuration) {
        super(com.iscas.biz.jooq.test.code.tables.JooqTestTable.JOOQ_TEST_TABLE, com.iscas.biz.jooq.test.code.tables.pojos.JooqTestTable.class, configuration);
    }

    @Override
    public Integer getId(com.iscas.biz.jooq.test.code.tables.pojos.JooqTestTable object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.iscas.biz.jooq.test.code.tables.pojos.JooqTestTable> fetchRangeOfId(Integer lowerInclusive, Integer upperInclusive) {
        return fetchRange(com.iscas.biz.jooq.test.code.tables.JooqTestTable.JOOQ_TEST_TABLE.ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.iscas.biz.jooq.test.code.tables.pojos.JooqTestTable> fetchById(Integer... values) {
        return fetch(com.iscas.biz.jooq.test.code.tables.JooqTestTable.JOOQ_TEST_TABLE.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.iscas.biz.jooq.test.code.tables.pojos.JooqTestTable fetchOneById(Integer value) {
        return fetchOne(com.iscas.biz.jooq.test.code.tables.JooqTestTable.JOOQ_TEST_TABLE.ID, value);
    }

    /**
     * Fetch records that have <code>name BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.iscas.biz.jooq.test.code.tables.pojos.JooqTestTable> fetchRangeOfName(String lowerInclusive, String upperInclusive) {
        return fetchRange(com.iscas.biz.jooq.test.code.tables.JooqTestTable.JOOQ_TEST_TABLE.NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.iscas.biz.jooq.test.code.tables.pojos.JooqTestTable> fetchByName(String... values) {
        return fetch(com.iscas.biz.jooq.test.code.tables.JooqTestTable.JOOQ_TEST_TABLE.NAME, values);
    }
}

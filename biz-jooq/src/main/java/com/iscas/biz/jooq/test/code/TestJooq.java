/*
 * This file is generated by jOOQ.
 */
package com.iscas.biz.jooq.test.code;


import com.iscas.biz.jooq.test.code.tables.JooqTestTable;
import com.iscas.biz.jooq.test.code.tables.Sample;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TestJooq extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>test_jooq</code>
     */
    public static final TestJooq TEST_JOOQ = new TestJooq();

    /**
     * The table <code>test_jooq.jooq_test_table</code>.
     */
    public final JooqTestTable JOOQ_TEST_TABLE = JooqTestTable.JOOQ_TEST_TABLE;

    /**
     * The table <code>test_jooq.sample</code>.
     */
    public final Sample SAMPLE = Sample.SAMPLE;

    /**
     * No further instances allowed
     */
    private TestJooq() {
        super("test_jooq", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            JooqTestTable.JOOQ_TEST_TABLE,
            Sample.SAMPLE);
    }
}

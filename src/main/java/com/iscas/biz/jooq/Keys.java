/*
 * This file is generated by jOOQ.
 */
package com.iscas.biz.jooq;


import com.iscas.biz.jooq.tables.JooqTestTable;
import com.iscas.biz.jooq.tables.records.JooqTestTableRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in 
 * test_jooq.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<JooqTestTableRecord> KEY_JOOQ_TEST_TABLE_PRIMARY = Internal.createUniqueKey(JooqTestTable.JOOQ_TEST_TABLE, DSL.name("KEY_jooq_test_table_PRIMARY"), new TableField[] { JooqTestTable.JOOQ_TEST_TABLE.ID }, true);
    public static final UniqueKey<JooqTestTableRecord> SYNTHETIC_PK_JOOQ_TEST_TABLE = Internal.createUniqueKey(JooqTestTable.JOOQ_TEST_TABLE, DSL.name("SYNTHETIC_PK_jooq_test_table"), new TableField[] { JooqTestTable.JOOQ_TEST_TABLE.ID }, true);
}

package com.iscas.biz.jooq.test.service;

import com.iscas.biz.jooq.test.code.Tables;
import com.iscas.biz.jooq.test.code.tables.pojos.JooqTestTable;
import com.iscas.biz.jooq.test.code.tables.records.JooqTestTableRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/4/23 21:25
 * @since jdk1.8
 */
@Service
@Transactional
public class TestService {
    @Autowired
    private DSLContext dslContext;

    public void testInsert() {
        //第一种方式
        int result = dslContext.insertInto(Tables.JOOQ_TEST_TABLE)
                .columns(Tables.JOOQ_TEST_TABLE.NAME)
                .values("张三").execute();
        //第二种方式
        JooqTestTableRecord jooqTestTableRecord = new JooqTestTableRecord();
        jooqTestTableRecord.setName("李四");
        int execute = dslContext.insertInto(Tables.JOOQ_TEST_TABLE)
                .set(jooqTestTableRecord).execute();
    }

    public void testUpdate() {
        //第一种方式
        JooqTestTableRecord jooqTestTableRecord = new JooqTestTableRecord();
        jooqTestTableRecord.setName("李四1");
        int result1 = dslContext.update(Tables.JOOQ_TEST_TABLE)
                .set(jooqTestTableRecord)
                .where(Tables.JOOQ_TEST_TABLE.ID.eq(1))
                .execute();
        //第二种方式
        Map map = new HashMap();
        map.put("name", "张三1");
        int result2 = dslContext.update(Tables.JOOQ_TEST_TABLE)
                .set(map)
                .where(Tables.JOOQ_TEST_TABLE.ID.eq(2))
                .execute();
    }

    public void testQuery() {
        //查询所有
        List<JooqTestTable> jooqTestTables = dslContext.select().from(Tables.JOOQ_TEST_TABLE)
                .fetch().into(JooqTestTable.class);
        jooqTestTables.forEach(System.out::println);
    }
}

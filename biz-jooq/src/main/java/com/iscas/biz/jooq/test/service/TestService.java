package com.iscas.biz.jooq.test.service;

import com.iscas.biz.jooq.test.code.Tables;
import com.iscas.biz.jooq.test.code.tables.pojos.JooqTestTable;
import com.iscas.biz.jooq.test.code.tables.pojos.Sample;
import com.iscas.biz.jooq.test.code.tables.records.JooqTestTableRecord;
import com.iscas.biz.jooq.test.code.tables.records.SampleRecord;
import com.iscas.biz.jooq.test.model.SampleAndTest;
import com.iscas.biz.jooq.test.model.SampleAvgSum;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
        int result1 = dslContext.insertInto(Tables.JOOQ_TEST_TABLE)
                .columns(Tables.JOOQ_TEST_TABLE.NAME)
                .values("张三").execute();
        //第二种方式
        JooqTestTableRecord jooqTestTableRecord = new JooqTestTableRecord();
        jooqTestTableRecord.setName("李四");
        int result2 = dslContext.insertInto(Tables.JOOQ_TEST_TABLE)
                .set(jooqTestTableRecord).execute();


        SampleRecord sampleRecord = new SampleRecord();
        sampleRecord.setC1("test");
        sampleRecord.setC2(123L);
        sampleRecord.setC3((byte) 1);
        sampleRecord.setC4(true);
        int result3 = dslContext.insertInto(Tables.SAMPLE)
                .set(sampleRecord).execute();


    }

    public void testUpdate() {
        //第一种方式
        JooqTestTableRecord jooqTestTableRecord = new JooqTestTableRecord();
        jooqTestTableRecord.setName("李四1");
        int result1 = dslContext.update(Tables.JOOQ_TEST_TABLE)
                .set(jooqTestTableRecord)
                .where(Tables.JOOQ_TEST_TABLE.ID.eq(1))
                .execute();
        SampleRecord sampleRecord = new SampleRecord();
        sampleRecord.setC1("test");
        sampleRecord.setC2(123L);
        sampleRecord.setC3((byte) 5);
        sampleRecord.setC4(true);
        int result3 = dslContext.update(Tables.SAMPLE)
                .set(sampleRecord)
                .where(Tables.SAMPLE.ID.eq(1))
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
        System.out.println("======查询jooq_test_table所有的结果=======");
        jooqTestTables.forEach(System.out::println);

        List<Sample> samples = dslContext.select().from(Tables.SAMPLE)
                .fetch().into(Sample.class);
        System.out.println("======查询sample所有的结果=======");
        samples.forEach(System.out::println);

        //查询均值、和等
        SampleAvgSum sampleAvgSum = dslContext
                .select(DSL.avg(Tables.SAMPLE.C2).as("avg"), DSL.max(Tables.SAMPLE.C3).as("max"))
                .from(Tables.SAMPLE)
                .fetchOne().into(SampleAvgSum.class);
        System.out.println("======查询sample均值、和等的结果=======");
        System.out.println(sampleAvgSum);

        //查询指定字段
        List<Sample> samples2 = dslContext.select(Tables.SAMPLE.C1, Tables.SAMPLE.C2)
                .from(Tables.SAMPLE)
                .where(Tables.SAMPLE.ID.le(10))
                .fetch().into(Sample.class);
        System.out.println("======查询sample指定字段的结果=======");
        samples2.forEach(System.out::println);

        //查询后排序
        List<Sample> samples3 = dslContext.select()
                .from(Tables.SAMPLE)
                .orderBy(Tables.SAMPLE.C2.desc())
                .fetch().into(Sample.class);
        System.out.println("========查询后排序==============");
        samples3.forEach(System.out::println);

        //查询后分组
        List<Sample> samples4 = dslContext.select()
                .from(Tables.SAMPLE)
                .groupBy(Tables.SAMPLE.C2)
                .having(Tables.SAMPLE.C2.le(200L))
                .fetch()
                .into(Sample.class);
        System.out.println("========查询后分组==============");
        samples4.forEach(System.out::println);

        //分页
        List<Sample> samples5 = dslContext.select()
                .from(Tables.SAMPLE)
                .limit(0, 5)
                .fetch().into(Sample.class);
        System.out.println("========查询后分页==============");
        samples5.forEach(System.out::println);

        //关联查询
        List<SampleAndTest> sampleAndTests = dslContext.select(Tables.SAMPLE.ID, Tables.SAMPLE.C2, Tables.JOOQ_TEST_TABLE.NAME)
                .from(Tables.SAMPLE).leftJoin(Tables.JOOQ_TEST_TABLE)
                .on(Tables.SAMPLE.ID.eq(Tables.JOOQ_TEST_TABLE.ID))
                .fetch().into(SampleAndTest.class);
        System.out.println("==========关联查询=========");
        sampleAndTests.forEach(System.out::println);

        //查询总数
        Integer count = dslContext.selectCount()
                .from(Tables.SAMPLE)
                .fetchOne().into(Integer.class);
        System.out.println("==========查询数目=========");
        System.out.println(count);

        //Condition
        Condition condition = DSL.trueCondition();
        condition = condition.and(Tables.SAMPLE.ID.ge(5))
                .and(Tables.SAMPLE.ID.le(15));
        String render = dslContext.render(condition);
        System.out.println(render);
        List<Sample> samples6 = dslContext.select()
                .from(Tables.SAMPLE)
                .where(condition)
                .fetch().into(Sample.class);
        System.out.println("=======condition查询============");
        samples6.forEach(System.out::println);
        System.out.println(dslContext.renderNamedParams(dslContext.select()
                .from(Tables.SAMPLE)
                .where(condition)));
    }

    public void testDelete() {
        dslContext.delete(Tables.SAMPLE)
                .where(Tables.SAMPLE.ID.greaterOrEqual(12)).execute();

    }
}

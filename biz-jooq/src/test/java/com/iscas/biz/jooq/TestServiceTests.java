package com.iscas.biz.jooq;

import com.iscas.biz.jooq.test.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/4/23 21:46
 * @since jdk1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JooqBizApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class TestServiceTests {
    @Autowired
    private TestService testService;

    /**
     * 测试插入相关操作
     * */
    @Test
    public void testInsert() {
        testService.testInsert();
    }

    /**
     *
     * 测试修改相关操作
     * */
    @Test
    public void testUpdate() {
        testService.testUpdate();
    }

    /**
     *
     * 测试查询
     * */
    @Test
    public void testQuery() {
        testService.testQuery();
    }

}

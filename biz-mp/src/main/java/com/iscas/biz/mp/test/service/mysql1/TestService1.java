package com.iscas.biz.mp.test.service.mysql1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.biz.mp.mapper.DynamicMapper;
import com.iscas.biz.mp.test.mapper.TestEntityMapper;
import com.iscas.biz.mp.test.model.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/10 15:07
 * @since jdk1.8
 */
@Service
@ConditionalOnMybatis
public class TestService1 {
    @Autowired
    private DynamicMapper dynamicMapper;
    @Autowired
    private TestEntityMapper testEntityMapper;

    public List<Map> getIds() {
        return dynamicMapper.dynamicSelect("select * from test");
    }

    public List<TestEntity> get() {
        List<TestEntity> testEntities = testEntityMapper.selectList(null);
        return testEntities;
    }

    public List<TestEntity> get2() {
        IPage<TestEntity> page = new Page<>();
        page.setCurrent(1);
        page.setSize(2);
        IPage<TestEntity> testEntityIPage = testEntityMapper.selectPage(page, null);
        List<TestEntity> records = testEntityIPage.getRecords();
        return records;
    }
}

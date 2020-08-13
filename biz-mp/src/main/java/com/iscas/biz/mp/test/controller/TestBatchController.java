package com.iscas.biz.mp.test.controller;

import com.iscas.biz.mp.mapper.DynamicMapper;
import com.iscas.biz.mp.util.MybatisGeneralUtils;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/6/15 10:11
 * @since jdk1.8
 */
@RestController
@RequestMapping("/t/batch")
public class TestBatchController {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private DynamicMapper dynamicMapper;

    @GetMapping("/t1")
    public void t1() throws SQLException {
        ArrayList<String> stringArrayList = new ArrayList<String>();
        stringArrayList.add("insert into test values('3')");
        MybatisGeneralUtils.executeBatch(sqlSessionFactory,
                stringArrayList, 6, true);
    }

    @GetMapping("/t2")
    public Map t2() throws SQLException {
        return dynamicMapper.dynamicSelectOne("select id as id from test");
    }

    @GetMapping("/t3")
    public void t3() throws SQLException {
        ArrayList<String> stringArrayList = new ArrayList<String>();
        stringArrayList.add("insert into test values('6')");
        stringArrayList.add("insert into test values('7')");
        dynamicMapper.dynamicBatch(stringArrayList);
    }
}

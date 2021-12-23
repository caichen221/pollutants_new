package com.iscas.biz.mp.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.biz.mp.enhancer.mapper.DynamicMapper;
import com.iscas.biz.mp.test.mapper.TestMapper;
import com.iscas.biz.mp.test.model.Test;
import com.iscas.biz.mp.test.service.impl.TestService;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/3/26 14:09
 * @since jdk1.8
 */
@RestController
@RequestMapping("/test/mybatis/plus")
@ConditionalOnMybatis
public class TestController extends BaseController {

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private TestService testService;

    @Autowired
    private DynamicMapper dynamicMapper;

    @GetMapping("/page")
    public ResponseEntity testPage() {
        ResponseEntity response = getResponse();
        IPage<Test> page = new Page<Test>();
        page.setSize(2);
        page.setCurrent(1);
        IPage<Test> testPage = testMapper.selectPage(page, null);
        response.setValue(testPage.getRecords());
        return response;
    }

    @GetMapping("/update")
    public ResponseEntity testUpdate() {
        List select = dynamicMapper.select("select * from test");
        System.out.println(select);
        List<Test> tests = testMapper.selectList(null);
        tests.forEach(test -> test.setAge(null));
        testService.saveOrUpdateBatch(tests);
        return getResponse();
    }

    @GetMapping("/truncate")
    public ResponseEntity testTruncate() {
        testMapper.truncate();
        return getResponse();
    }

    @GetMapping("/fetchByStream")
    public ResponseEntity fetchByStream() {
        List<Test> result = new ArrayList<>();
        testMapper.fetchByStream(new QueryWrapper<Test>().eq("name", "222"), resultContext -> {
            result.add(resultContext.getResultObject());
        });
        ResponseEntity responseEntity = getResponse().setValue(result);
        return responseEntity;
    }

}

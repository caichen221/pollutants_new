package com.iscas.biz.mp.test.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iscas.biz.mp.test.mapper.TestEntityMapper;
import com.iscas.biz.mp.test.model.TestEntity;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/4/15 18:51
 * @since jdk1.8
 */
@RestController
@RequestMapping("/testPage")
public class TestPageController extends BaseController {
    @Autowired
    private TestEntityMapper testEntityMapper;
    @GetMapping("/test")
    public ResponseEntity test() {
        ResponseEntity response = getResponse();
        IPage<TestEntity> page = new Page<>();
        page.setCurrent(1);
        page.setSize(2);
        IPage<TestEntity> testEntityIPage = testEntityMapper.selectPage(page, null);
        List<TestEntity> records = testEntityIPage.getRecords();
        response.setValue(records);
        return response;
    }
}

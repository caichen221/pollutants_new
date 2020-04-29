package com.iscas.biz.mp.test.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iscas.biz.mp.test.mapper.TestMapper;
import com.iscas.biz.mp.test.model.Test;
import com.iscas.templet.common.BaseController;
import com.iscas.templet.common.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/3/26 14:09
 * @since jdk1.8
 */
@RestController
@RequestMapping("/testPage")
public class TestController extends BaseController {
    @Autowired
    private TestMapper testMapper;
    @GetMapping()
    public ResponseEntity testPage() {
        ResponseEntity response = getResponse();
        IPage<Test> page = new Page<Test>();
        page.setSize(2);
        page.setCurrent(1);
        IPage<Test> testPage = testMapper.selectPage(page, null);
        response.setValue(testPage.getRecords());
        return response;
    }
}

package com.iscas.biz.mp.test.controller;

import com.iscas.biz.mp.mapper.DynamicMapper;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class TestSearchLargeData {

    @Autowired
    private DynamicMapper dynamicMapper;

    @GetMapping("/getLargeData")
    public void getLargeData() {
        String sql = "select * from m8j";
        dynamicMapper.dynamicSelectLargeData(sql, new ResultHandler<Map>() {
            @Override
            public void handleResult(ResultContext<? extends Map> resultContext) {
                Map resultObject = resultContext.getResultObject();
                System.out.println(resultObject);
            }
        });
    }
}

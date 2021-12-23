package com.iscas.biz.mp.test.service.mysql2;

import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.biz.mp.enhancer.mapper.DynamicMapper;
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
public class TestService2 {
    @Autowired
    private DynamicMapper dynamicMapper;

    public List<Map> getIds() {
        return dynamicMapper.select("select * from alg");
    }
}

package com.iscas.biz.test.service.db1;

import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.biz.mp.mapper.DynamicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/20 10:21
 * @since jdk1.8
 */
@Service
@ConditionalOnMybatis
public class Db1TestService {
    @Autowired
    private DynamicMapper dynamicMapper;

    public void test() {
        dynamicMapper.insert("insert into test_mp_ar(name) values('111')");
    }

    public void test2() {
        dynamicMapper.insert("insert into ws_data(type) values('BUSINESS')");
    }
}

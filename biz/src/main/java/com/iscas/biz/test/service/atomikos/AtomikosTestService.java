package com.iscas.biz.test.service.atomikos;

import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.biz.test.service.db1.Db1TestService;
import com.iscas.biz.test.service.db2.Db2TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/20 10:38
 * @since jdk1.8
 */
@Service
@ConditionalOnMybatis
public class AtomikosTestService {
    @Autowired
    private Db1TestService db1TestService;
    @Autowired
    private Db2TestService db2TestService;

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public void test() {
        db1TestService.test();
        int a = 4/0;
        db2TestService.test();
        throw new RuntimeException("出错啦");
    }

}

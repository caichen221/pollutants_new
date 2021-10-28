package com.iscas.common.etcd.tools.utils;

import com.iscas.common.etcd.tools.exception.EtcdClientException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/10/28 9:23
 * @since jdk1.8
 */
class EtcdUtilsTests {

    @BeforeEach
    public void before() throws EtcdClientException {
        EtcdUtils.getClient();
    }

    @Test
    public void getClient() throws EtcdClientException {
        System.out.println(EtcdUtils.getClient());
    }

    @Test
    public void exists() {
        Boolean exists = EtcdUtils.exists("/registry/events");
        Assertions.assertFalse(exists);
    }

    @Test
    public void put() {
        Boolean put = EtcdUtils.put("/test/test/test", "test-test");
        Assertions.assertTrue(put);
        String single = EtcdUtils.getSingle("/test/test/test");
        System.out.println(single);
    }

    @Test
    public void get() {
        String data = EtcdUtils.getSingle("xxx");
        System.out.println(data);
    }

    /**
     * 测试分布式锁
     * */
    @Test
    public void testLock() {
        Long aLong = EtcdUtils.acquireLock("1234", 100000);
        System.out.println(aLong);
        EtcdUtils.releaseLock("1234", aLong);
        Long aLong1 = EtcdUtils.acquireLock("1234", 100000);
        System.out.println(aLong1);
    }

}
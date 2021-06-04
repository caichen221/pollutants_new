package com.iscas.common.minio.service;

import com.iscas.common.minio.exception.MinioServiceException;
import com.iscas.common.minio.tools.MinioClientUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/4 17:20
 * @since jdk1.8
 */
public class MinioServiceTest {
    private MinioService minioService;

    @BeforeEach
    public void before() {
        minioService = new MinioService();
        minioService.init("http://172.16.10.160:30900", "admin", "iscas123");
    }

    /**
     * 判断存储桶是否存在
     * */
    @Test
    public void testExsitsBucket() throws MinioServiceException {
        boolean exists = minioService.bucketExists("aaa");
        Assertions.assertFalse(exists);
    }

    /**
     * 创建存储桶
     * */
    @Test
    public void makeBucket() throws MinioServiceException {
        boolean exists = minioService.bucketExists("test-abc");
        if (exists) {
            try {
                minioService.makeBucket("test-abc");
            } catch (Exception e) {
                Assertions.assertTrue(e instanceof MinioServiceException);
            }
        } else {
            minioService.makeBucket("test-abc");
        }
    }

}
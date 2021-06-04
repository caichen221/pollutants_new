package com.iscas.common.minio.tools;

import io.minio.MinioClient;

/**
 * minio-client工具类
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/4 9:51
 * @since jdk1.8
 */
public class MinioClientUtils {
    private MinioClientUtils() {}

    public static MinioClient getMinioClient(String endpoint, int port, String accessKey, String secretKey) {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}

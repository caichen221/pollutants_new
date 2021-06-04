package com.iscas.common.minio.service;

import com.iscas.common.minio.exception.MinioServiceException;
import com.iscas.common.minio.tools.MinioClientUtils;
import io.minio.*;
import io.minio.errors.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/4 17:03
 * @since jdk1.8
 */
public class MinioService {
    private MinioClient minioClient = null;

    public void init(String endpoint, String accessKey, String secretKey) {
        minioClient = MinioClientUtils.getClient(endpoint, accessKey, secretKey);
    }

    /**
     * 判断某个存储桶在不在
     * @version 1.0
     * @since jdk1.8
     * @date 2021/6/4
     * @param bucketName 存储桶名称
     * @throws MinioServiceException
     * @return boolean
     */
    public boolean bucketExists(String bucketName) throws MinioServiceException {
        check();
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (ErrorResponseException e) {
            throw new MinioServiceException("执行请求失败", e);
        } catch (InsufficientDataException e) {
            throw new MinioServiceException("非法的存储桶名称", e);
        } catch (InternalException e) {
            throw new MinioServiceException("minio服务内部异常", e);
        } catch (InvalidKeyException e) {
            throw new MinioServiceException("无效密钥", e);
        } catch (InvalidResponseException e) {
            throw new MinioServiceException("响应异常", e);
        } catch (IOException e) {
             throw new MinioServiceException("连接异常", e);
        } catch (NoSuchAlgorithmException e) {
            throw new MinioServiceException("加密算法异常", e);
        } catch (ServerException e) {
            throw new MinioServiceException("minio服务内部异常", e);
        } catch (XmlParserException e) {
            throw new MinioServiceException("解析返回的XML异常", e);
        }
    }


    /**
     * 创建存储桶
     * @version 1.0
     * @since jdk1.8
     * @date 2021/6/4
     * @param bucketName 存储桶名称
     * @throws MinioServiceException
     * @return void
     */
    public void makeBucket(String bucketName) throws MinioServiceException {
        check();
        boolean exists = bucketExists(bucketName);
        if (exists) {
            throw new MinioServiceException(String.format("存储桶:%s已存在", bucketName));
        }
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } catch (ErrorResponseException e) {
            throw new MinioServiceException("执行请求失败", e);
        } catch (InsufficientDataException e) {
            throw new MinioServiceException("非法的存储桶名称", e);
        } catch (InternalException e) {
            throw new MinioServiceException("minio服务内部异常", e);
        } catch (InvalidKeyException e) {
            throw new MinioServiceException("无效密钥", e);
        } catch (InvalidResponseException e) {
            throw new MinioServiceException("响应异常", e);
        } catch (IOException e) {
            throw new MinioServiceException("连接异常", e);
        } catch (NoSuchAlgorithmException e) {
            throw new MinioServiceException("加密算法异常", e);
        } catch (ServerException e) {
            throw new MinioServiceException("minio服务内部异常", e);
        } catch (XmlParserException e) {
            throw new MinioServiceException("解析返回的XML异常", e);
        }
    }


    private void check() {
        if (minioClient == null) {
            throw new RuntimeException("minioClient未初始化，需调用init");
        }
    }

}

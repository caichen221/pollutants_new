package com.iscas.base.biz.service;

/**
 *
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/12/7 22:47
 * @since jdk1.8
 */
public interface IAuthCacheService {
    void remove(String key);
    void set(String key, Object value);
    Object get(String key);
}

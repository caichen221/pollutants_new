package com.iscas.base.biz.service.common;

import com.iscas.base.biz.service.IAuthCacheService;
import com.iscas.base.biz.util.CaffCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/12/7 22:50
 * @since jdk1.8
 */
@Service
public class AuthCacheService implements IAuthCacheService {
    @Autowired
    private CacheManager cacheManager;

    @Override
    public void remove(String key) {
        if (!isRedisCacheManager()) {
            CaffCacheUtils.remove(key);
        } else {
            RedisCacheManager redisCacheManager = castToRedisCacheManager();

        }

    }

    @Override
    public void set(String key, Object value) {
        if (!isRedisCacheManager()) {
            CaffCacheUtils.set(key, value);
        } else {
            RedisCacheManager redisCacheManager = castToRedisCacheManager();

        }
    }

    @Override
    public Object get(String key) {
        if (!isRedisCacheManager()) {
            return CaffCacheUtils.get(key);
        } else {
            RedisCacheManager redisCacheManager = castToRedisCacheManager();
            return null;
        }
    }

    private boolean isRedisCacheManager() {
        return cacheManager instanceof RedisCacheManager;
    }

    private RedisCacheManager castToRedisCacheManager() {
        return (RedisCacheManager) cacheManager;
    }
}

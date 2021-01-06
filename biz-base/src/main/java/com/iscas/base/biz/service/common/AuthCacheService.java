package com.iscas.base.biz.service.common;

import com.iscas.base.biz.service.IAuthCacheService;
import com.iscas.base.biz.util.CaffCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
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
    private final CacheManager cacheManager;

    public AuthCacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void remove(String key) {
        if (!isRedisCacheManager()) {
            CaffCacheUtils.remove(key);
        } else {
            RedisCacheManager redisCacheManager = castToRedisCacheManager();
            Cache authCache = redisCacheManager.getCache("auth");
            if (authCache == null) return;
            authCache.evict(key);
        }
    }

    @Override
    public void set(String key, Object value) {
        if (!isRedisCacheManager()) {
            CaffCacheUtils.set(key, value);
        } else {
            RedisCacheManager redisCacheManager = castToRedisCacheManager();
            Cache authCache = redisCacheManager.getCache("auth");
            if (authCache == null) {
                throw new RuntimeException("找不到缓存：auth");
            }
            authCache.put(key, value);
        }
    }

    @Override
    public Object get(String key) {
        if (!isRedisCacheManager()) {
            return CaffCacheUtils.get(key);
        } else {
            RedisCacheManager redisCacheManager = castToRedisCacheManager();
            Cache authCache = redisCacheManager.getCache("auth");
            if (authCache == null) {
                return null;
            }
            Cache.ValueWrapper valueWrapper = authCache.get(key);
            if (valueWrapper == null) {
                return null;
            }
            return valueWrapper.get();
        }
    }

    private boolean isRedisCacheManager() {
        return cacheManager instanceof RedisCacheManager;
    }

    private RedisCacheManager castToRedisCacheManager() {
        return (RedisCacheManager) cacheManager;
    }
}

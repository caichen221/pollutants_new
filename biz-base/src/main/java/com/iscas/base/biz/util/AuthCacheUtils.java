package com.iscas.base.biz.util;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.date.DateUnit;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.text.MessageFormat;
import java.util.Optional;

/**
 * 登录以及权限校验缓存工具类
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2018/11/9 9:25
 * @since jdk1.8
 */
public class AuthCacheUtils {
    private static final Cache<String, Object> FIFO_CACHE = CacheUtil.newFIFOCache(1000000);


    public static void put(String key, Object value, String cacheKey, int ttl) {
        CacheManager cacheManager = SpringUtils.getApplicationContext().getBean(CacheManager.class);
        if (cacheManager instanceof RedisCacheManager) {
            org.springframework.cache.Cache loginCache = cacheManager.getCache(cacheKey);
            if (loginCache == null) {
                throw new RuntimeException(MessageFormat.format("找不到：{0}", cacheKey));
            }
            loginCache.put(key, value);
        } else {
            FIFO_CACHE.put(cacheKey + ":" + key, value, DateUnit.SECOND.getMillis() * ttl);
        }
    }

    public static Object get(String key, String cacheKey) {
        CacheManager cacheManager = SpringUtils.getApplicationContext().getBean(CacheManager.class);
        if (cacheManager instanceof RedisCacheManager) {

            RedisCacheManager redisCacheManager = (RedisCacheManager) cacheManager;
            return Optional.ofNullable(redisCacheManager.getCache(cacheKey))
                    .map(authCache -> authCache.get(key))
                    .map(org.springframework.cache.Cache.ValueWrapper::get)
                    .orElse(null);
        } else {
            return FIFO_CACHE.get(cacheKey + ":" + key, false);
        }
    }

    public static void remove(String key, String cacheKey) {
        CacheManager cacheManager = SpringUtils.getApplicationContext().getBean(CacheManager.class);
        if (cacheManager instanceof RedisCacheManager) {
            org.springframework.cache.Cache loginCache = cacheManager.getCache(cacheKey);
            if (loginCache == null) {
                return;
            }
            loginCache.evict(key);
        } else {
            FIFO_CACHE.remove(cacheKey + ":" + key);
        }

    }


}


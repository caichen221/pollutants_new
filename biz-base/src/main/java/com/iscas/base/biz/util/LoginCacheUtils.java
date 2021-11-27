package com.iscas.base.biz.util;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.date.DateUnit;
import org.springframework.cache.CacheManager;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.util.UUID;

/**
 * 登录缓存工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/11/9 9:25
 * @since jdk1.8
 */
public class LoginCacheUtils {
    private static Cache<String,String> fifoCache = CacheUtil.newFIFOCache(1000);

    public static String createCodeAndPut(String secretKey) {
        UUID uuid = UUID.randomUUID();
        put(uuid.toString(), secretKey);
        return uuid.toString();
    }

    public static void put(String key, String value) {
        Environment environment = SpringUtils.getApplicationContext().getBean(Environment.class);
        String loginRandomCacheTime = environment.getProperty("login.random.data.cache.time-to-live");
        int loginRandomCacheSenconds = Integer.parseInt(loginRandomCacheTime);
        CacheManager cacheManager = SpringUtils.getApplicationContext().getBean(CacheManager.class);
        if (cacheManager instanceof RedisCacheManager) {
            org.springframework.cache.Cache loginCache = cacheManager.getCache("loginCache");
            if (loginCache == null) throw new RuntimeException("找不到loginCache");
            loginCache.put(key, value);
        } else {
            fifoCache.put(key, value, DateUnit.SECOND.getMillis() * loginRandomCacheSenconds);
        }
    }

    public static String get(String uuid) {
        CacheManager cacheManager = SpringUtils.getApplicationContext().getBean(CacheManager.class);
        if (cacheManager instanceof RedisCacheManager) {
            org.springframework.cache.Cache loginCache = cacheManager.getCache("loginCache");
            if (loginCache == null) return null;
            org.springframework.cache.Cache.ValueWrapper valueWrapper = loginCache.get(uuid);
            if (valueWrapper == null) return null;
            return (String) valueWrapper.get();
        } else {
            return fifoCache.get(uuid, false);
        }
    }

    public static void remove(String key) {
        CacheManager cacheManager = SpringUtils.getApplicationContext().getBean(CacheManager.class);
        if (cacheManager instanceof RedisCacheManager) {
            org.springframework.cache.Cache loginCache = cacheManager.getCache("loginCache");
            if (loginCache == null) return;
            loginCache.evict(key);
        } else {
            fifoCache.remove(key);
        }

    }


}


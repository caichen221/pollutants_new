package com.iscas.base.biz.util;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.date.DateUnit;
import com.iscas.base.biz.service.common.SpringService;
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

    public static String put(String secretKey) {
        UUID uuid = UUID.randomUUID();
        Environment environment = SpringService.getApplicationContext().getBean(Environment.class);
        String loginRandomCacheTime = environment.getProperty("login.random.data.cache.time-to-live");
        int loginRandomCacheSenconds = Integer.parseInt(loginRandomCacheTime);
        CacheManager cacheManager = SpringService.getApplicationContext().getBean(CacheManager.class);
        if (cacheManager instanceof RedisCacheManager) {
            org.springframework.cache.Cache loginCache = cacheManager.getCache("loginCache");
            if (loginCache == null) throw new RuntimeException("找不到loginCache");
            loginCache.put(uuid.toString(), secretKey);
        } else {
            fifoCache.put(uuid.toString(), secretKey, DateUnit.SECOND.getMillis() * loginRandomCacheSenconds);
        }
        return uuid.toString();
    }
    public static String get(String uuid) {
        CacheManager cacheManager = SpringService.getApplicationContext().getBean(CacheManager.class);
        if (cacheManager instanceof RedisCacheManager) {
            org.springframework.cache.Cache loginCache = cacheManager.getCache("loginCache");
            if (loginCache == null) return null;
            org.springframework.cache.Cache.ValueWrapper valueWrapper = loginCache.get(uuid);
            if (valueWrapper == null) return null;
            return (String) valueWrapper.get();
        } else {
            return fifoCache.get(uuid);
        }
    }

    public static void remove(String uuid) {
        CacheManager cacheManager = SpringService.getApplicationContext().getBean(CacheManager.class);
        if (cacheManager instanceof RedisCacheManager) {
            org.springframework.cache.Cache loginCache = cacheManager.getCache("loginCache");
            if (loginCache == null) return;
            loginCache.evict(uuid);
        } else {
            fifoCache.remove(uuid);
        }

    }


}


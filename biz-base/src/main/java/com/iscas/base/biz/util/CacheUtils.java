package com.iscas.base.biz.util;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.Optional;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/3/1 10:41
 * @since jdk1.8
 */
public class CacheUtils {

    public static CacheManager getCacheManager() {
        return SpringUtils.getBean(CacheManager.class);
    }

    /**
     * 清理缓存
     */
    public static void evictCache(String cacheName) {
        Cache cache = getCacheManager().getCache(cacheName);
        Optional.ofNullable(cache).ifPresent(Cache::invalidate);
    }

    /**
     * 根据cacheKeys清理缓存
     */
    public static void evictCache(String cacheName, Collection cacheKeys) {
        Cache cache = getCacheManager().getCache(cacheName);
        Optional.ofNullable(cache).ifPresent(a -> Optional.ofNullable(cacheKeys).ifPresent(keys -> keys.stream().forEach(cache::evict)));
    }

    /**
     * 缓存存在时，加入缓存
     */
    public static void putCache(String cacheName, Object key, Object value) {
        Cache cache = getCacheManager().getCache(cacheName);
        Optional.ofNullable(cache).ifPresent(c -> c.put(key, value));
    }

}

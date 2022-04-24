package com.iscas.base.biz.util;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 咖啡因缓存工具类
 *
 * @author zhuquanwen
 * @date 2018/3/21 15:09
 *
 **/
@SuppressWarnings("unused")
public class CaffCacheUtils {
    private CaffCacheUtils() { }
    private static final Logger log = LoggerFactory.getLogger(CaffCacheUtils.class);
    /**初始化*/
    private static final int INITIAL_CAPCITY = 100;
    /**最大容量*/
    private static final int MAXIMUM_SIZE = 10000;
    /**目前没查到资料怎样使缓存用不失效，设置尽量一个大的值10年*/
    private static final int EXPIRE_AFTER_WRITE = 3650 * 24 * 60;
    private static volatile LoadingCache<String,Object> localCache = null;
    private static void initLocalCache() {
        if (localCache == null) {
            synchronized (CaffCacheUtils.class) {
                localCache = Caffeine.newBuilder()
                        .initialCapacity(INITIAL_CAPCITY)
                        .maximumSize(MAXIMUM_SIZE)
                        .expireAfterWrite(EXPIRE_AFTER_WRITE, TimeUnit.MINUTES)
                        .build(s -> null);
            }
        }
    }
    public static void set(String key, Object value) {
        initLocalCache();
        localCache.put(key, value);
    }

    public synchronized static void safeSet(String key, Object value) {
        set(key, value);
    }

    public static void cleanup() {
        initLocalCache();
        localCache.invalidateAll();
    }

    @SuppressWarnings("AlibabaUndefineMagicConstant")
    public static Object get(String key) {
        initLocalCache();
        Object value;
        try {
            value = localCache.get(key);
            if ("null".equals(value)) {
                return null;
            }
            return value;
        } catch (Exception e) {
            log.error("localCache get error", e);
        }
        return null;
    }
    public synchronized static Object safeGet(String key) {
        return get(key);
    }
    public static void remove(String key) {
        initLocalCache();
        if (key != null) {
            localCache.invalidate(key);
        }
    }

    public synchronized static void safeRemove(String key) {
        remove(key);
    }

}

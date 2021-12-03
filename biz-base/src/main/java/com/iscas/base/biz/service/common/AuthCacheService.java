package com.iscas.base.biz.service.common;

import com.iscas.base.biz.service.IAuthCacheService;
import com.iscas.base.biz.util.CaffCacheUtils;
import com.iscas.base.biz.util.CommonRedisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/12/7 22:50
 * @since jdk1.8
 */
@Service
public class AuthCacheService implements IAuthCacheService {
    private static final String CACHE_AUTH = "auth";
    private final CacheManager cacheManager;

    @Autowired(required = false)
    private RedisTemplate redisTemplate;
    @Autowired(required = false)
    private CommonRedisHelper commonRedisHelper;

    @Value("${user.max.sessions}")
    private int userMaxSessions;

    private Map<String, List<String>> jdkList = new WeakHashMap<>();

    public AuthCacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void remove(String key) {
        if (!isRedisCacheManager()) {
            CaffCacheUtils.remove(key);
        } else {
            RedisCacheManager redisCacheManager = castToRedisCacheManager();
            Optional.ofNullable(redisCacheManager.getCache(CACHE_AUTH))
                    .ifPresent(authCache -> authCache.evict(key));
        }
    }

    @Override
    public void set(String key, Object value) {
        if (!isRedisCacheManager()) {
            CaffCacheUtils.set(key, value);
        } else {
            RedisCacheManager redisCacheManager = castToRedisCacheManager();
            Cache authCache = redisCacheManager.getCache(CACHE_AUTH);
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
            return Optional.ofNullable(redisCacheManager.getCache(CACHE_AUTH))
                    .map(authCache -> authCache.get(key))
                    .map(Cache.ValueWrapper::get)
                    .orElse(null);
        }
    }

    @Override
    public void rpush(String key, String value) {
        if (!isRedisCacheManager()) {
            synchronized (key.intern()) {
                List<String> values = jdkList.computeIfAbsent(key, k -> new ArrayList<>());
                if (llen(key) >= userMaxSessions) {
                    //移除较早登录的会话
                    Optional.ofNullable(lpop(key)).ifPresent(this::remove);
                }
                //加入新会话
                values.add(value);
            }
        } else {
            try {
                for (int i = 0; i < 300; i++) {
                    //使用分布式锁，防止同时移除，使得刚登录的用户也退出
                    boolean lock = commonRedisHelper.lock(key);
                    if (lock) {
                        if (llen(key) >= userMaxSessions) {
                            //移除较早登录的会话
                            Optional.ofNullable(lpop(key)).ifPresent(this::remove);
                        }
                        //加入新会话
                        redisTemplate.opsForList().rightPush(key, value);
                        break;
                    } else {
                        try {
                            TimeUnit.MILLISECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } finally {
                commonRedisHelper.delete(key);
            }
        }
    }

    @Override
    public String lpop(String key) {
        if (!isRedisCacheManager()) {
            List<String> values = jdkList.computeIfAbsent(key, k -> new ArrayList<>());
            if (values.size() > 0) {
                //移除
                return values.remove(0);
            }
        } else {
            return (String) redisTemplate.opsForList().leftPop(key);
        }
        return null;
    }

    @Override
    public int llen(String key) {
        if (!isRedisCacheManager()) {
            return Optional.ofNullable(jdkList.get(key)).map(List::size).orElse(0);
        } else {
            return Optional.ofNullable(redisTemplate.opsForList().size(key))
                    .map(Long::intValue).orElse(0);
        }
    }

    @Override
    public boolean listContains(String key, String value) {
        if (!isRedisCacheManager()) {
            return Optional.ofNullable(jdkList.get(key)).map(list -> list.contains(value)).orElse(false);
        } else {
            try {
                Long aLong = redisTemplate.opsForList().indexOf(key, value);
                return aLong >= 0;
            } catch (Exception e) {
                return false;
            }
        }
    }

    private boolean isRedisCacheManager() {
        return cacheManager instanceof RedisCacheManager;
    }

    private RedisCacheManager castToRedisCacheManager() {
        return (RedisCacheManager) cacheManager;
    }

}

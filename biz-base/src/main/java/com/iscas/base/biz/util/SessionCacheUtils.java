package com.iscas.base.biz.util;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.date.DateUnit;
import com.google.gson.Gson;

import java.util.Map;

/**
 * session-cache工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/11/9 16:56
 * @since jdk1.8
 */
public class SessionCacheUtils {
    private static Gson gson = new Gson();
    //超时时间秒
    private static long timeout = 36000;
    private static Cache<String,Object> fifoCache = CacheUtil.newFIFOCache(2000);

    public static void put(String sessionId, Object obj) {
        //超时时间十个小时
        fifoCache.put(sessionId, obj, DateUnit.SECOND.getMillis() * timeout);
    }
    public static Map<String, Object> get(String sessionId) {
        Map<String, Object> map = (Map<String, Object>) fifoCache.get(sessionId);
        return map;
    }

    public static void remove(String sessionId) {
        fifoCache.remove(sessionId);
    }
}

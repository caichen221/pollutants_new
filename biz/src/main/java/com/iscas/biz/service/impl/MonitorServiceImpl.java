package com.iscas.biz.service.impl;

import cn.hutool.json.JSONObject;
import com.iscas.base.biz.util.CaffCacheUtils;
import com.iscas.base.biz.util.DateTimeUtils;
import com.iscas.biz.model.monitor.jvm.JvmExtraMonitor;
import com.iscas.biz.model.monitor.jvm.JvmMonitor;
import com.iscas.biz.model.monitor.sys.SysExtraMonitor;
import com.iscas.biz.model.monitor.sys.SysMonitor;
import com.iscas.biz.service.MonitorService;
import com.iscas.biz.util.CloneUtils;
import com.iscas.biz.util.RegexUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/3/2 14:30
 * @since jdk1.8
 */
@Service
public class MonitorServiceImpl implements MonitorService {

    private static final int cacheSize = 1000;

    @Override
    public void saveData(Map<Class, Object> data, Map<Class, Object> extraData) {

        //动态数据的缓存
        Optional.ofNullable(data).filter(d -> d.size() > 0).ifPresent(d ->
                data.forEach((clazz, object) -> {
                    String cacheName = clazz.getSimpleName();
                    ConcurrentHashMap<String, LinkedList<String>> dataCache = getCache(cacheName, ConcurrentHashMap::new);
                    Field[] declaredFields = clazz.getDeclaredFields();
                    synchronized (cacheName) {
                        Arrays.stream(declaredFields).forEach(declaredField -> {
                            declaredField.setAccessible(true);
                            try {
                                String fieldName = declaredField.getName();
                                Object fieldValue = declaredField.get(object);
                                String cacheValue = "0";
                                if (fieldValue != null) {//普通字段
                                    if (!(fieldValue instanceof Date)) {
                                        cacheValue = RegexUtils.getStartNumber(fieldValue.toString());
                                    } else {//时间类型
                                        cacheValue = DateTimeUtils.getDateStr((Date) fieldValue);
                                    }
                                }
                                LinkedList<String> valueCache = dataCache.computeIfAbsent(fieldName, a -> new LinkedList<>());
                                int size = valueCache.size();
                                if (size >= cacheSize) {
                                    int delete = size - cacheSize;
                                    while (delete-- >= 0) {
                                        valueCache.removeFirst();
                                    }
                                }
                                valueCache.add(cacheValue);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                })
        );

        //额外数据的缓存
        Optional.ofNullable(extraData).filter(d -> d.size() > 0).ifPresent(d ->
                d.forEach((clazz, object) -> {
                    String cacheName = clazz.getSimpleName();
                    ConcurrentHashMap<String, Object> extraDataCache = getCache(cacheName, ConcurrentHashMap::new);
                    extraDataCache.put(cacheName, object);
                })
        );
    }

    @Override
    public Object getData() {
        JSONObject result = new JSONObject();

        //系统监控数据
        getMonitorData(result, SysMonitor.class, SysExtraMonitor.class);

        //JVM监控数据
        getMonitorData(result, JvmMonitor.class, JvmExtraMonitor.class);

        return result;
    }

    /**
     * 从缓存获取数据
     */
    private Map getMonitorData(JSONObject result, Class monitorClass, Class extraMonitorClass) {
        Object monitorCache = CaffCacheUtils.get(monitorClass.getSimpleName());
        ConcurrentHashMap<String, LinkedList<String>> monitorData = (ConcurrentHashMap<String, LinkedList<String>>) monitorCache;
        Object extraMonitorCache = CaffCacheUtils.get(extraMonitorClass.getSimpleName());
        ConcurrentHashMap<String, LinkedList<String>> extraMonitorData = (ConcurrentHashMap<String, LinkedList<String>>) extraMonitorCache;

        Map monitorResult = new HashMap();
        Optional.ofNullable(monitorData).ifPresent(data -> {
            synchronized (monitorData) {
                ConcurrentHashMap<String, LinkedList<String>> cloneData = CloneUtils.clone(monitorData);
                monitorResult.put("data", cloneData);
            }
        });
        Optional.ofNullable(extraMonitorData).ifPresent(extraData -> {
            monitorResult.put("props", extraData);
        });
        result.put(monitorClass.getSimpleName(), monitorResult);
        return monitorResult;

    }

    /**
     * 获取或初始化缓存
     */
    private <T> T getCache(String cacheKey, Supplier<T> valueSupplier) {
        Object value = CaffCacheUtils.get(cacheKey);
        if (value == null) {
            synchronized (cacheKey) {
                value = CaffCacheUtils.get(cacheKey);
                if (value == null) {
                    value = valueSupplier.get();
                    CaffCacheUtils.set(cacheKey, value);
                }
            }
        }
        return (T) value;
    }

}

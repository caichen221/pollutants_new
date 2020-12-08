package com.iscas.common.harbor.tools.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * JSON转为对象的工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/12/8 21:23
 * @since jdk1.8
 */
public class Json2ObjUtils {
    private Json2ObjUtils() {}

    public static <T> T json2Obj(Class<T> tClass, Map jsonMap, BiFunction<String, Object, Object> biFunction, String... keys) {
        T t = null;
        try {
            t = tClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(String.format("无法实例化对象:[%s]", tClass.getName()), e);
        }
        for (int i = 0; i < keys.length; i = i + 2) {
            Object mapValue = jsonMap.get(keys[i]);
            String objKey = keys[i + 1];
            try {
                mapValue = biFunction.apply(objKey, mapValue);

                Field field = tClass.getDeclaredField(objKey);
                field.setAccessible(true);
                field.set(t, mapValue);
            } catch (Exception e) {
                throw new RuntimeException(String.format("向对象:[%s]注入属性:[%s],值:[%s]出错", tClass.getName(),
                        objKey, mapValue + ""), e);
            }
        }
        return t;
    }
    public static <T> List<T> json2List(Class<T> tClass, List<Map> jsonMaps, BiFunction<String, Object , Object> biFunction, String... keys) {
        List<T> ts = new ArrayList<>();
        for (Map jsonMap : jsonMaps) {
            ts.add(json2Obj(tClass, jsonMap, biFunction, keys));
        }
        return ts;
    }
 }

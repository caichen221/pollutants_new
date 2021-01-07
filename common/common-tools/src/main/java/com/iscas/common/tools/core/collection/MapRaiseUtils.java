package com.iscas.common.tools.core.collection;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Map扩展工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/3/26 15:55
 * @since jdk1.8
 */
public class MapRaiseUtils {
    private MapRaiseUtils() {}

    /**
     * 删除map中多个对象
     * @version 1.0
     * @since jdk1.8
     * @date 2021/1/6
     * @param map
     * @param keys 待删除的key
     * @throws
     * @return void
     */
    public static void remove(Map map, Object... keys) {
        Assert.notNull(map, "map不能为空");
        Assert.notNull(keys, "要删除的key不能为空");
        for (int i = 0; i < keys.length; i++) {
            map.remove(keys[i]);
        }
    }


    /**
     * 判断Map是不是为空，如果Map里全都是null也判断
     * @version 1.0
     * @since jdk1.8
     * @date 2021/1/6
     * @param map
     * @throws
     * @return boolean
     */
    public static boolean isEmpty(Map map) {
        boolean flag = map == null || map.size() <= 0;
        if (!flag) {
            Set<Map.Entry> entrySet = map.entrySet();
            boolean notnullFlag = false;
            for (Map.Entry entry: entrySet) {
                if (entry.getKey() != null) {
                    notnullFlag = true;
                }
            }
            flag = !notnullFlag;
        }
        return flag;
    }

    /**
     * 判断Map是不是不为空，如果Map里全都是null也判断
     * @version 1.0
     * @since jdk1.8
     * @date 2021/1/6
     * @param map
     * @throws
     * @return boolean
     */
    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }

    /**
     * 移除Map中的null值
     * @version 1.0
     * @since jdk1.8
     * @date 2021/1/6
     * @param map
     * @throws
     * @return void
     */
    public static void removeNullValue(Map<Object, Object> map) {
        Assert.notNull(map, "map不能为空");
        List<Object> keys = new ArrayList<>();
        for (Map.Entry<Object, Object> entry: map.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                keys.add(key);
            }
        }
        if (CollectionUtils.isNotEmpty(keys)) {
            Object[] keyArray = keys.toArray(new Object[0]);
            remove(map, keyArray);
        }
    }

    /**
     * 将Map的Key转为驼峰的
     * @version 1.0
     * @since jdk1.8
     * @date 2021/1/6
     * @param map
     * @throws
     * @return java.util.Map<K,V>
     */
    public static <K, V> Map<K, V> convertToHump(Map<K, V> map) {
        return MapUtil.toCamelCaseMap(map);
    }
}

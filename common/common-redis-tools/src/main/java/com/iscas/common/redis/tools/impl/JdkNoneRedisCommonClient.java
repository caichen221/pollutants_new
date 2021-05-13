package com.iscas.common.redis.tools.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.iscas.common.redis.tools.JedisConnection;
import com.iscas.common.redis.tools.helper.MyObjectHelper;
import com.iscas.common.redis.tools.helper.MyStringHelper;
import com.iscas.common.redis.tools.impl.jdk.JdkNoneRedisConnection;
import com.iscas.common.redis.tools.impl.jdk.ZsortDto;
import redis.clients.jedis.ListPosition;
import redis.clients.jedis.PipelineBase;
import redis.clients.jedis.Tuple;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 不使用Redis，使用Jdk仿造Redis的方法，
 * 接口与Redis统一，适应不需要Redis的环境
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/05/08 12:53
 * @since jdk1.8
 */
public class JdkNoneRedisCommonClient {
    protected JedisConnection jedisConnection;
    protected JdkNoneRedisConnection jdkNoneRedisConnection;

    /**
     * 获取byte[]类型Key
     * @param object
     * @return
     */
    public static byte[] getBytesKey(Object object) throws IOException {
        if(object instanceof String){
            return MyStringHelper.getBytes((String)object);
        }else{
            return MyObjectHelper.serialize(object);
        }
    }

    /**
     * Object转换byte[]类型
     * @param object
     * @return
     */
    public static byte[] toBytes(Object object) throws IOException {
        return MyObjectHelper.serialize(object);
    }

    /**
     * byte[]型转换Object
     * @param bytes
     * @return
     */
    public static Object toObject(byte[] bytes) throws IOException, ClassNotFoundException {
        return MyObjectHelper.unserialize(bytes);
    }

    /**
     * 获取分布式锁 返回Null表示获取锁失败
     * @version 1.0
     * @since jdk1.8
     * @date 2021/05/08
     * @param lockName 锁名称
     * @param lockTimeoutInMS 锁超时时间
     * @throws
     * @return java.lang.String 锁标识
     */
    public String acquireLock(String lockName, long lockTimeoutInMS) {
        String identifier = UUID.randomUUID().toString();
        String lockKey = "lock:" + lockName;

        //TimeCache中已经使用了Lock，这里使用锁为了保证读取和存入缓存是一个原子操作
        synchronized (lockKey.intern()) {
            String lock = jdkNoneRedisConnection.ACQUIRE_LOCK_CACHE.get(lockKey);
            if (lock != null) {
                //锁存在
                return null;
            } else {
                //锁不存在
                jdkNoneRedisConnection.ACQUIRE_LOCK_CACHE.put(lockKey, identifier, lockTimeoutInMS);
            }
        }
        return identifier;
    }

    /**
     * 释放锁
     * @version 1.0
     * @since jdk1.8
     * @date 2018/11/6
     * @param lockName 锁key
     * @param identifier 锁标识
     * @throws
     * @return boolean
     */
    public boolean releaseLock(String lockName, String identifier) {
        String lockKey = "lock:" + lockName;
        boolean result;
        if (identifier == null) {
            result = true;
        }
        //TimeCache中已经使用了Lock，这里使用锁为了保证读取和删除缓存是一个原子操作
        synchronized (lockKey.intern()) {
            String identifier2 = jdkNoneRedisConnection.ACQUIRE_LOCK_CACHE.get(lockKey);
            if (null != identifier2 && !Objects.equals(identifier, identifier2)) {
                //只有加锁的人才能释放锁，如果获取的值跟传入的
                //identifier不一致，说明这不是自己加的锁，拒绝释放
                result = false;
            } else {
                jdkNoneRedisConnection.ACQUIRE_LOCK_CACHE.remove(lockKey);
                result = true;
            }
        }
        return result;
    }

    /**
     *  IP 限流
     * @version 1.0
     * @since jdk1.8
     * @date 2018/11/6
     * @param ip ip
     * @param timeout 规定时间 （秒）
     * @param limit 限制次数
     * @throws
     * @return 是否可以访问
     */
    public boolean accessLimit(String ip, int timeout, int limit) {
        throw new UnsupportedOperationException("jdk模式不支持IP限流");
    }

    public Object jedisCommandsBytesLuaEvalSha(Object jedisCommands, String lua, List key, List val) {
        throw new UnsupportedOperationException("jdk模式不支持pipeline");
    }

    public PipelineBase getPipeline(Object jedisResource) {
        throw new UnsupportedOperationException("jdk模式不支持pipeline");
    }

    public void pipelineBatch(Consumer<PipelineBase> consumer) {
        throw new UnsupportedOperationException("jdk模式不支持pipeline");
    }

    protected long doDel(String key) {
        synchronized (key.intern()) {
            if (jdkNoneRedisConnection.OBJECT_CACHE.containsKey(key)) {
                jdkNoneRedisConnection.OBJECT_CACHE.remove(key);
                return 1;
            } else {
                return 0;
            }
        }
    }

    protected boolean doExists(String key) {
        return jdkNoneRedisConnection.OBJECT_CACHE.containsKey(key);
    }

    protected void doDeleteByPattern(String pattern) {
        //转换为正则表达式
        pattern = pattern.replace("*", ".*");
        pattern = pattern.replace("?", ".");
        Map cacheMap = (Map) ReflectUtil.getFieldValue(jdkNoneRedisConnection.OBJECT_CACHE, "cacheMap");
        Set<String> set = cacheMap.keySet();
        if (CollectionUtil.isNotEmpty(set)) {
            Pattern pa = Pattern.compile(pattern);
            //删除正则表达式匹配到得key
            set.stream().filter(key -> pa.matcher(key).matches())
                    .forEach(jdkNoneRedisConnection.OBJECT_CACHE::remove);
        }
    }

    protected void doExpire(String key, long milliseconds) {
        synchronized (key.intern()) {
            Object value = jdkNoneRedisConnection.OBJECT_CACHE.get(key);
            if (value != null) {
                jdkNoneRedisConnection.OBJECT_CACHE.put(key, value, milliseconds);
            }
        }
    }

    protected boolean doSet(String key, Object value, long seconds) {
        synchronized (key.intern()) {
            if (seconds == 0) {
                jdkNoneRedisConnection.OBJECT_CACHE.put(key, value);
            } else {
                jdkNoneRedisConnection.OBJECT_CACHE.put(key, value, seconds * 1000);
            }
            return true;
        }
    }

    protected <T> T doGet(Class<T> tClass, String key) {
        return (T) jdkNoneRedisConnection.OBJECT_CACHE.get(key);
    }

    protected long doSetnx(String key, Object value) {
        synchronized (key.intern()) {
            if (doExists(key)) {
                return 0L;
            }
            if (doSet(key, value, 0)) {
                return 1L;
            }
            return 0L;
        }
    }

    protected <T> T doGetSet(Class<T> tClass, String key, T value) {
        synchronized (key.intern()) {
            T s = doGet(tClass, key);
            doSet(key, value, 0);
            return s;
        }
    }

    protected <T> List<T> doMget(Class<T> tClass, String... keys) {
        return Arrays.stream(keys).map(key -> doGet(tClass, key))
                .collect(Collectors.toList());
    }

    protected boolean doMset(Object... keysvalues) {
        if (keysvalues.length % 2 != 0) {
            throw new RuntimeException("key和value必须匹配");
        }
        Map<String, Object> keyValueMap = new LinkedHashMap<>();
        for (int i = 0; i < keysvalues.length; i = i + 2) {
            keyValueMap.put((String) keysvalues[i], keysvalues[i + 1]);
        }
        for (Map.Entry<String, Object> entry : keyValueMap.entrySet()) {
            doSet(entry.getKey(), entry.getValue(), 0);
        }
        return true;
    }

    protected long doRpush(String key, Object... value) {
        synchronized (key.intern()) {
            LinkedList linkedList = doGet(LinkedList.class, key);
            if (linkedList == null) {
                linkedList = new LinkedList();
                doSet(key, linkedList, 0);
            }
            Arrays.stream(value).forEach(linkedList::addLast);
            return linkedList.size();
        }
    }

    protected long doLpush(String key, Object... value) {
        synchronized (key.intern()) {
            LinkedList linkedList = getLinkedList(key);
            Arrays.stream(value).forEach(linkedList::addFirst);
            return linkedList.size();
        }
    }

    protected long doLlen(String key) {
        LinkedList linkedList = doGet(LinkedList.class, key);
        return linkedList == null ? 0 : linkedList.size();
    }

    private LinkedList getLinkedList(String key) {
        synchronized (key.intern()) {
            LinkedList linkedList = doGet(LinkedList.class, key);
            if (linkedList == null) {
                linkedList = new LinkedList();
                doSet(key, linkedList, 0);
            }
            return linkedList;
        }
    }

    protected boolean doLset(String key, int index, Object value) {
        synchronized (key.intern()) {
            LinkedList linkedList = getLinkedList(key);
            if (index > linkedList.size() - 1) {
                throw new RuntimeException(String.format("链表没有下标:%d", index));
            }
            linkedList.set(index, value);
            return true;
        }
    }

    protected long doLinsert(String key, ListPosition where, Object pivot, Object value) {
        synchronized (key.intern()) {
            LinkedList linkedList = doGet(LinkedList.class, key);
            if (linkedList == null) {
                throw new RuntimeException(String.format("key:%s对应的链表不存在", key));
            }
            for (int i = 0; i < linkedList.size(); i++) {
                Object o = linkedList.get(i);
                if (Objects.equals(o, pivot)) {
                    if (where == ListPosition.AFTER) {
                        linkedList.add(i + 1, value);
                    } else {
                        linkedList.add(i, value);
                    }
                }
            }
            return linkedList.size();
        }
    }

    protected  <T> T doLindex(Class<T> tClass, String key, long index) {
        synchronized (key.intern()) {
            LinkedList linkedList = doGet(LinkedList.class, key);
            if (linkedList == null) {
                return null;
            }
            return (T) linkedList.get((int) index);
        }
    }

    protected  <T> T doLpop(Class<T> tClass, String key) {
        synchronized (key.intern()) {
            LinkedList linkedList = doGet(LinkedList.class, key);
            if (linkedList == null) {
                return null;
            }
            return (T) linkedList.pollFirst();
        }
    }

    protected <T> T doRpop(Class<T> tClass, String key) {
        synchronized (key.intern()) {
            LinkedList linkedList = doGet(LinkedList.class, key);
            if (linkedList == null) {
                return null;
            }
            return (T) linkedList.pollLast();
        }
    }

    protected <T> List<T> doLrange(Class<T> tClass, String key, long start, long end) {
        synchronized (key.intern()) {
            LinkedList linkedList = doGet(LinkedList.class, key);
            if (linkedList == null) {
                return null;
            }
            if (end == -1) {
                end = linkedList.size() - 1;
            }
            return linkedList.subList((int) start, (int) end + 1);
        }
    }

    protected long doLrem(String key, int count, Object value) {
        synchronized (key.intern()) {
            long sum = 0L;
            LinkedList linkedList = doGet(LinkedList.class, key);
            if (linkedList == null) {
                return 0L;
            }
            if (count > 0) {
                Iterator iterator = linkedList.iterator();
                while (iterator.hasNext() && sum < count) {
                    Object o = iterator.next();
                    if (Objects.equals(o, value)) {
                        iterator.remove();
                        sum ++;
                    }
                }
            } else {
                for (int i = linkedList.size() - 1; i >= 0 && sum < -count; i--) {
                    Object o = linkedList.get(i);
                    if (Objects.equals(o, value)) {
                        linkedList.remove(i);
                        sum ++;
                    }
                }
            }
            return sum;
        }
    }

    protected boolean doLtrim(String key, long start, long end) {
        synchronized (key.intern()) {
            LinkedList linkedList = doGet(LinkedList.class, key);
            if (linkedList == null) {
                return false;
            }
            for (int i = linkedList.size() - 1; i >= 0; i--) {
                if (i < start || i > end) {
                    linkedList.remove(i);
                }
            }
            return true;
        }
    }

    private Map getMap(String key) {
        synchronized (key.intern()) {
            Map map = doGet(Map.class, key);
            if (map == null) {
                map = new HashMap();
                doSet(key, map, 0);
            }
            return map;
        }
    }

    protected boolean doHmset(String key, Map map, int cacheSeconds) {
        synchronized (key.intern()) {
            Map storeMap = getMap(key);
            storeMap.putAll(map);
            if (cacheSeconds != 0) {
                doExpire(key, cacheSeconds * 1000);
            }
            return true;
        }
    }


    protected  <K extends Object, V extends Object> Map<K, V> doHgetAll(Class<K> keyClass, Class<V> valClass, String key) {
        Map<K, V> map = doGet(Map.class, key);
        return map;
    }

    protected long doHdel(String key, Object... fields) {
        synchronized (key.intern()) {
            long count = 0;
            Map map = doGet(Map.class, key);
            if (map == null) {
                return 0L;
            }
            for (Object field : fields) {
                Object remove = map.remove(field);
                if (remove != null) {
                    count++;
                }
            }
            return count;
        }
    }

    protected boolean doHexists(String key, Object field) {
        synchronized (key.intern()) {
            Map map = doGet(Map.class, key);
            if (map == null) {
                return false;
            }
            return map.containsKey(field);
        }
    }

    protected <T> T doHget(Class<T> tClass, String key, String field) {
        synchronized (key.intern()) {
            Map map = doGet(Map.class, key);
            if (map == null) {
                return null;
            }
            return (T) map.get(field);
        }
    }

    protected long doHset(String key, Object field, Object value) {
        synchronized (key.intern()) {
            Map map = getMap(key);
            map.put(field, value);
            return 1L;
        }
    }

    protected long doHsetnx(String key, Object field, Object value) {
        synchronized (key.intern()) {
            Map map = doGet(Map.class, key);
            if (map.containsKey(field)) {
                return 0L;
            }
            map.put(field, value);
            return 1L;
        }
    }

    protected <T> List<T> doHvals(Class<T> tClass, String key) {
        synchronized (key.intern()) {
            Map map = doGet(Map.class, key);
            if (map == null) {
                return null;
            }
            return new ArrayList<T>(map.values());
        }
    }

    protected long doHincrby(String key, String field, long value) {
        Map map = doGet(Map.class, key);
        if (map == null) {
            throw new RuntimeException(String.format("key:%s对应的hash不存在", key));
        }
        synchronized (key.intern()) {
            Object o = map.get(field);
            if (o == null) {
                throw new RuntimeException(String.format("field:%s对应的值不存在", key));
            }
            try {
                long data = Long.parseLong(o.toString());
                data += value;
                if (o instanceof String) {
                    doHset(key, field, String.valueOf(data));
                } else if (o instanceof Integer || o.getClass() == int.class) {
                    doHset(key, field, (int) data);
                } else if (o instanceof Short || o.getClass() == short.class) {
                    doHset(key, field, (int) data);
                } else if (o instanceof Byte || o.getClass() == byte.class) {
                    doHset(key, field, (int) data);
                } else if (o instanceof Long || o.getClass() == long.class) {
                    doHset(key, field, data);
                } else {
                    throw new RuntimeException(String.format("不支持的数据类型:%s", o.getClass()));
                }
                return data;
            } catch (Exception e) {
                throw new RuntimeException(String.format("值:%s不能转化为整数", o.toString()));
            }
        }
    }

    public Double doHincrby(String key, String field, double value) {
        Map map = doGet(Map.class, key);
        if (map == null) {
            throw new RuntimeException(String.format("key:%s对应的hash不存在", key));
        }
        synchronized (key.intern()) {
            Object o = map.get(field);
            if (o == null) {
                throw new RuntimeException(String.format("field:%s对应的值不存在", key));
            }
            try {
                double data = Double.parseDouble(o.toString());
                data += value;
                if (o instanceof String) {
                    doHset(key, field, String.valueOf(data));
                } else if (o instanceof Float || o.getClass() == float.class) {
                    doHset(key, field, (int) data);
                } else if (o instanceof Double || o.getClass() == double.class) {
                    doHset(key, field, (int) data);
                } else {
                    throw new RuntimeException(String.format("不支持的数据类型:%s", o.getClass()));
                }
                return data;
            } catch (Exception e) {
                throw new RuntimeException(String.format("值:%s不能转化为浮点数", o.toString()));
            }
        }
    }

    protected <T> Set<T> doHkeys(Class<T> tClass, String key) {
        synchronized (key.intern()) {
            Map map = doGet(Map.class, key);
            if (map == null) {
                return null;
            }
            return map.keySet();
        }
    }

    protected long doHlen(String key) {
        synchronized (key.intern()) {
            Map map = doGet(Map.class, key);
            if (map == null) {
                return 0L;
            }
            return map.size();
        }
    }

    protected <T> List<T> doHmget(Class<T> tClass, String key, Object... fields) {
        synchronized (key.intern()) {
            Map map = doGet(Map.class, key);
            if (map == null) {
                return null;
            }
            return Arrays.stream(fields).map(field -> (T) map.get(field))
                    .collect(Collectors.toList());
        }
    }

    private Set getSet(String key) {
        synchronized (key.intern()) {
            Set set = doGet(Set.class, key);
            if (set == null) {
                set = new HashSet();
                doSet(key, set, 0);
            }
            return set;
        }
    }

    protected long doSadd(String key, Set value, int cacheSeconds) {
        synchronized (key.intern()) {
            Set set = getSet(key);
            set.addAll(value);
            if (cacheSeconds != 0) {
                doExpire(key, cacheSeconds * 1000);
            }
            return value.size();
        }
    }

    protected long doSadd(String key, Object... value) {
        synchronized (key.intern()) {
            Set set = getSet(key);
            Arrays.stream(value).forEach(set::add);
            return value.length;
        }
    }

    protected long doScard(String key) {
        synchronized (key.intern()) {
            Set set = doGet(Set.class, key);
            if (set == null) {
                return 0;
            }
            return set.size();
        }
    }

    protected <T> Set<T> doSdiff(Class<T> tClass, String... keys) {
        Set set0 = doGet(Set.class, keys[0]);
        if (set0 == null) {
            return new HashSet<>();
        }
        Set diffSet = ObjectUtil.cloneByStream(set0);
        for (int i = 1; i < keys.length; i++) {
            Set set = doGet(Set.class, keys[i]);
            if (set != null) {
                for (Object o : set) {
                    if (diffSet.contains(o)) {
                        diffSet.remove(o);
                    }
                }
            }
        }
        return diffSet;
    }

    protected long doSdiffStore(String newkey, String... keys) {
        Set<Object> diffSet = doSdiff(Object.class, keys);
        if (diffSet != null && diffSet.size() > 0) {
            doSadd(newkey, diffSet, 0);
            return diffSet.size();
        } else {
            return 0L;
        }
    }

    protected <T> Set<T> doSinter(Class<T> tClass, String... keys) {
        Set set0 = doGet(Set.class, keys[0]);
        if (set0 == null) {
            return new HashSet<>();
        }
        Set interSet = ObjectUtil.cloneByStream(set0);
        Set tmpInterSet = new HashSet();
        for (int i = 1; i < keys.length; i++) {
            Set set = doGet(Set.class, keys[i]);
            if (set != null) {
                for (Object o : set) {
                    if (interSet.contains(o)) {
                         tmpInterSet.add(o);
                    }
                }
            } else {
                return new HashSet<>();
            }
            interSet.clear();
            interSet = tmpInterSet;
        }
        return interSet;
    }

    protected long doSinterStore(String newKey, String... keys) {
        Set<Object> diffSet = doSinter(Object.class, keys);
        if (diffSet != null && diffSet.size() > 0) {
            doSadd(newKey, diffSet, 0);
            return diffSet.size();
        } else {
            return 0L;
        }
    }

    protected boolean doSismember(String key, Object member) {
        synchronized (key.intern()) {
            Set set = doGet(Set.class, key);
            if (set == null) {
                return false;
            }
            return set.contains(member);
        }
    }

    protected <T> Set<T> doSmembers(Class<T> tClass, String key) {
        synchronized (key.intern()) {
            Set set = doGet(Set.class, key);
            if (set == null) {
                return null;
            }
            return set;
        }
    }

    protected long doSmove(String srckey, String dstkey, Object member) {
        synchronized (srckey.intern()) {
            synchronized (dstkey.intern()) {
                Set set = doGet(Set.class, srckey);
                if (!set.contains(member)) {
                    return 0;
                }
                set.remove(member);
                Set set1 = getSet(dstkey);
                set1.add(member);
                return 1;
            }
        }
    }

    protected <T> T doSpop(Class<T> tClass, String key) {
        synchronized (key.intern()) {
            Set set = doGet(Set.class, key);
            Iterator iterator = set.iterator();
            if (iterator.hasNext()) {
                T o = (T) iterator.next();
                iterator.remove();
                return o;
            } else {
                return null;
            }
        }
    }
    protected <T> Set<T> doSpop(Class<T> tClass, String key, long count) {
        synchronized (key.intern()) {
            Set<T> result = new HashSet<>();
            Set set = doGet(Set.class, key);
            Iterator iterator = set.iterator();
            int sum = 0;
            while (iterator.hasNext() && sum < count) {
                T o = (T) iterator.next();
                iterator.remove();
                sum++;
                result.add(o);
            }
            return result;
        }
    }

    protected long doSrem(String key, Object... member) {
        synchronized (key.intern()) {
            long result = 0;
            Set set = doGet(Set.class, key);
            for (Object o : member) {
                boolean remove = set.remove(o);
                if (remove) {
                    result = 1;
                }
            }
            return result;
        }
    }

    protected  <T> Set<T> doSunion(Class<T> tClass, String... keys) {
        Set result = new HashSet();
        Arrays.stream(keys).map(key -> doGet(Set.class, key))
                .forEach(result::addAll);
        return result;
    }

    private TreeSet<ZsortDto> getZset(String key) {
        TreeSet<ZsortDto> treeSet = doGet(TreeSet.class, key);
        if (treeSet == null) {
            treeSet = new TreeSet<>();
            doSet(key, treeSet, 0);
        }
        return treeSet;
    }
    protected long doZadd(String key, double score, Object member) {
        synchronized (key.intern()) {
            TreeSet<ZsortDto> zset = getZset(key);
            ZsortDto zsortDto = new ZsortDto(score, member);
            zset.remove(zsortDto);
            zset.add(zsortDto);
            return 1L;
        }
    }

    protected long doZadd(String key, Map<? extends Object, Double> valueScoreMap, int cacheSeconds) {
        synchronized (key.intern()) {
            long l = doZadd(key, valueScoreMap);
            if (cacheSeconds != 0) {
                doExpire(key, cacheSeconds * 1000);
            }
            return l;
        }
    }

    protected long doZadd(String key, Map<? extends Object, Double> valueScoreMap) {
        synchronized (key.intern()) {
            TreeSet<ZsortDto> zset = getZset(key);
            for (Map.Entry<?, Double> entry : valueScoreMap.entrySet()) {
                ZsortDto zsortDto = new ZsortDto(entry.getValue(), entry.getKey());
                zset.remove(zsortDto);
                zset.add(zsortDto);
            }
            return valueScoreMap.size();
        }
    }

    protected long doZcard(String key) {
        synchronized (key.intern()) {
            TreeSet<ZsortDto> zset = doGet(TreeSet.class, key);
            return zset == null ? 0 : zset.size();
        }
    }

    protected long doZcount(String key, double min, double max) {
        synchronized (key.intern()) {
            TreeSet<ZsortDto> zset = doGet(TreeSet.class, key);
            if (zset != null) {
                long count = 0L;
                for (ZsortDto zsortDto : zset) {
                    if (zsortDto.getScore() >= min && zsortDto.getScore() <= max) {
                        count++;
                    }
                }
                return count;
            }
            return 0L;
        }
    }

    protected double doZincrby(String key, double score, Object member) {
        synchronized (key.intern()) {
            TreeSet<ZsortDto> zset = getZset(key);
            for (ZsortDto zsortDto : zset) {
                double score1 = zsortDto.getScore();
                Object member1 = zsortDto.getMember();
                if (Objects.equals(member, member1)) {
                    double newScore = score + score1;
                    zsortDto.setScore(newScore);
                    return newScore;
                }
            }
            ZsortDto zsortDto = new ZsortDto(score, member);
            zset.add(zsortDto);
            return score;
        }
    }

    protected <T> Set<T> doZrange(Class<T> tClass, String key, long start, long end) {
        synchronized (key.intern()) {
            TreeSet<ZsortDto> zset = doGet(TreeSet.class, key);
            if (zset == null) {
                return new HashSet<>();
            }
            int i = 0;
            if (end == -1) {
                end = zset.size() - 1;
            }
            Set<T> result = new TreeSet<>();
            for (ZsortDto zsortDto : zset) {
                if (i >= start && i <= end) {
                    result.add((T) zsortDto.getMember());
                }
                i++;
            }
            return result;
        }
    }

    protected <T> Map<T, Double> doZrangeWithScoresToMap(Class<T> tClass, String key, long start, long end) {
        Map<T, Double> resultMap = new LinkedHashMap<>();
        synchronized (key.intern()) {
            TreeSet<ZsortDto> zset = doGet(TreeSet.class, key);
            if (zset == null) {
                return resultMap;
            }
            if (end == -1) {
                end = zset.size() - 1;
            }
            int i = 0;
            for (ZsortDto zsortDto : zset) {
                if (i >= start && i <= end) {
                    resultMap.put((T) zsortDto.getMember(), zsortDto.getScore());
                }
                i++;
            }
            return resultMap;
        }
    }

    protected <T> Set<T> doZrangeByScore(Class<T> tClass, String key, double min, double max) {
        synchronized (key.intern()) {
            TreeSet<ZsortDto> zset = doGet(TreeSet.class, key);
            if (zset == null) {
                return new LinkedHashSet<>();
            }
            Set<T> resultSet = new LinkedHashSet<>();
            for (ZsortDto zsortDto : zset) {
                if (zsortDto.getScore() >= min && zsortDto.getScore() <= max) {
                    resultSet.add((T) zsortDto.getMember());
                }
            }
            return resultSet;
        }
    }

    protected <T> Set<T> doZrangeByScore(Class<T> tClass, String key, double min, double max, int offset, int count) {
        synchronized (key.intern()) {
            TreeSet<ZsortDto> zset = doGet(TreeSet.class, key);
            if (zset == null) {
                return new LinkedHashSet<>();
            }
            Set<T> resultSet = new LinkedHashSet<>();
            int index = 0;
            int sum = 0;
            for (ZsortDto zsortDto : zset) {
                if (zsortDto.getScore() >= min && zsortDto.getScore() <= max && index++ >= offset) {
                    resultSet.add((T) zsortDto.getMember());
                    if (++sum >= count) {
                        break;
                    }
                }
            }
            return resultSet;
        }
    }


}

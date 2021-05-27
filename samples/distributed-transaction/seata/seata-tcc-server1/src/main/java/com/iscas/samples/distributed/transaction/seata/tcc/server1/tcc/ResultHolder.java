package com.iscas.samples.distributed.transaction.seata.tcc.server1.tcc;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/27 11:04
 * @since jdk1.8
 */
public class ResultHolder {
    private static Map<Class<?>, Map<String, String>> map = new ConcurrentHashMap<>();

    private static Map<Class<?>, Map<String, Date>> nullRollbackedMap = new ConcurrentHashMap<>();

    static {
        try {
            ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
            ses.scheduleAtFixedRate(() -> {
                Collection<Map<String, Date>> values = nullRollbackedMap.values();
                if (values != null) {
                    for (Map<String, Date> value : values) {
                        Iterator<Map.Entry<String, Date>> iterator = value.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, Date> entry = iterator.next();
                            if (System.currentTimeMillis() - entry.getValue().getTime() > 300000) {
                                iterator.remove();
                            }
                        }
                    }
                }
            }, 5, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setResult(Class<?> actionClass, String xid, String v) {
        Map<String, String> results = map.get(actionClass);

        if (results == null) {
            synchronized (map) {
                if (results == null) {
                    results = new ConcurrentHashMap<>();
                    map.put(actionClass, results);
                }
            }
        }

        results.put(xid, v);
    }

    public static String getResult(Class<?> actionClass, String xid) {
        Map<String, String> results;
        results = map.get(actionClass);
        if (results != null) {
            return results.get(xid);
        }

        return null;
    }

    public static void removeResult(Class<?> actionClass, String xid) {
        Map<String, String> results = map.get(actionClass);
        if (results != null) {
            results.remove(xid);
        }
    }


    public static void setNullRollbackedResult(Class<?> actionClass, String xid, Date date) {
        Map<String, Date> results = nullRollbackedMap.get(actionClass);

        if (results == null) {
            synchronized (nullRollbackedMap) {
                if (results == null) {
                    results = new ConcurrentHashMap<>();
                    nullRollbackedMap.put(actionClass, results);
                }
            }
        }

        results.put(xid, date);
    }

    public static Date getNullRollbackedResult(Class<?> actionClass, String xid) {
        Map<String, Date> results;
        results = nullRollbackedMap.get(actionClass);
        if (results != null) {
            return results.get(xid);
        }

        return null;
    }

    public static void removeNullRollbackedResult(Class<?> actionClass, String xid) {
        Map<String, Date> results = nullRollbackedMap.get(actionClass);
        if (results != null) {
            results.remove(xid);
        }
    }
}

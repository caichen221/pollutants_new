package com.iscas.biz.mp.config.db;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/10 13:46
 * @since jdk1.8
 */
public class DbContextHolder {
    private static final ThreadLocal CONTEXT_HOLDER = new ThreadLocal<>();
    /**
     * 设置数据源
     * @param dbType
     */
    public static void setDbType(String dbType) {
        CONTEXT_HOLDER.set(dbType);
    }

    /**
     * 取得当前数据源
     * @return
     */
    public static String getDbType() {
        return (String) CONTEXT_HOLDER.get();
    }

    /**
     * 清除上下文数据
     */
    public static void clearDbType() {
        CONTEXT_HOLDER.remove();
    }
}


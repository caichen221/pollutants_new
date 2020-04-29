package com.iscas.biz.mp.util;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * mybatis关闭自动提交的工具类
 * 可以自定义sessionfactory,
 * 为了连接非配置文件内的数据库，
 * 适用于数据归并或抽取时
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/12/10 0010 下午 15:48
 * @since jdk11
 */
public class MybatisUnCommitUtils {

    private static Map<String, String> createSqlMap(String sql) {
        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sql);
        return sqlMap;
    }

    /**
     * 获取SqlSession
     * */
    public static SqlSession getSqlSession(SqlSessionFactory sessionFactory) {
        SqlSession sqlSession = sessionFactory.openSession();
        return sqlSession;
    }


    public static List<Map> executeSearch(SqlSession session, String sql) {
        Map<String, String> sqlMap = createSqlMap(sql);
        List<Map> result = null;
        String method = "com.iscas.biz.mp.mapper.DynamicMapper.dynamicSelect";
        result = session.selectList(method, sqlMap);
        return result;

    }

    public static void executeInsert(SqlSession session, String sql) {
        Map<String, String> sqlMap = createSqlMap(sql);
        String method = "com.iscas.biz.mp.mapper.DynamicMapper.dynamicInsert";
        session.insert(method, sqlMap);
    }


    public static void executeUpdate(SqlSession session, String sql) {
        Map<String, String> sqlMap = createSqlMap(sql);
        String method = "com.iscas.biz.mp.mapper.DynamicMapper.dynamicUpdate";
        session.update(method, sqlMap);
    }


    public static void executeDelete(SqlSession session, String sql) {
        Map<String, String> sqlMap = createSqlMap(sql);
        String method = "com.iscas.biz.mp.mapper.DynamicMapper.dynamicDelete";
        session.update(method, sqlMap);
    }

}

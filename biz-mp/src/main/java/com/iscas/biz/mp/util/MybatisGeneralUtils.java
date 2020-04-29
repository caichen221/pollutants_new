package com.iscas.biz.mp.util;

import com.iscas.biz.mp.mapper.DynamicMapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * mybatis工具类
 * 可以自定义sessionfactory,
 * 为了连接非配置文件内的数据库，
 * 适用于数据归并或抽取时
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/12/10 0010 下午 15:48
 * @since jdk11
 */
public class MybatisGeneralUtils {

    public static SqlSessionFactory getSessionFactory(String envId, DataSource dataSource) {

        Configuration configuration = new Configuration();
        configuration.addMapper(DynamicMapper.class);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment(envId, transactionFactory, dataSource);
        configuration.setEnvironment(environment);
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return sessionFactory;
    }

    private static Map<String, String> createSqlMap(String sql) {
        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sql);
        return sqlMap;
    }


    public static List<Map> executeSearch(SqlSessionFactory sessionFactory, String sql) {
        SqlSession sqlSession = null;
        try {
            sqlSession = sessionFactory.openSession();
            Map<String, String> sqlMap = createSqlMap(sql);
            List<Map> result = null;
            String method = "com.iscas.biz.mp.mapper.DynamicMapper.dynamicSelect";
            result = sqlSession.selectList(method, sqlMap);
            sqlSession.commit();
            return result;
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    public static void executeInsert(SqlSessionFactory sessionFactory, String sql) {
        SqlSession sqlSession = null;
        try {
            sqlSession = sessionFactory.openSession();
            Map<String, String> sqlMap = createSqlMap(sql);
            String method = "com.iscas.biz.mp.mapper.DynamicMapper.dynamicInsert";
            sqlSession.insert(method, sqlMap);
            sqlSession.commit();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    public static void executeUpdate(SqlSessionFactory sessionFactory, String sql) {
        SqlSession sqlSession = null;
        try {
            sqlSession = sessionFactory.openSession();
            Map<String, String> sqlMap = createSqlMap(sql);
            String method = "com.iscas.biz.mp.mapper.DynamicMapper.dynamicUpdate";
            sqlSession.update(method, sqlMap);
            sqlSession.commit();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    public static void executeDelete(SqlSessionFactory sessionFactory, String sql) {
        SqlSession sqlSession = null;
        try {
            sqlSession = sessionFactory.openSession();
            Map<String, String> sqlMap = createSqlMap(sql);
            String method = "com.iscas.biz.mp.mapper.DynamicMapper.dynamicDelete";
            sqlSession.update(method, sqlMap);
            sqlSession.commit();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

}

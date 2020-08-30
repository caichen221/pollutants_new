package com.iscas.biz.mp.util;

import com.iscas.biz.mp.mapper.DynamicMapper;
import lombok.SneakyThrows;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    private static volatile Map<String, SqlSessionFactory> sqlSessionFactoryMap = new ConcurrentHashMap<>();

    public static SqlSessionFactory getSessionFactory(String envId, DataSource dataSource) {
        if (!sqlSessionFactoryMap.containsKey(envId)) {
            synchronized (envId.intern()) {
                if (!sqlSessionFactoryMap.containsKey(envId)) {
                    Configuration configuration = new Configuration();
                    configuration.addMapper(DynamicMapper.class);
                    TransactionFactory transactionFactory = new JdbcTransactionFactory();
                    Environment environment = new Environment(envId, transactionFactory, dataSource);
                    configuration.setEnvironment(environment);
                    SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(configuration);
                    sqlSessionFactoryMap.put(envId, sessionFactory);
                    return sessionFactory;
                }
            }
        }
        return sqlSessionFactoryMap.get(envId);

    }

    private static Map<String, String> createSqlMap(String sql) {
        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sql);
        return sqlMap;
    }

    public static Map executeSearchOne(SqlSessionFactory sessionFactory, String sql) {
        SqlSession sqlSession = null;
        try {
            sqlSession = sessionFactory.openSession();
            Map<String, String> sqlMap = createSqlMap(sql);
            Map result = null;
            String method = "com.iscas.biz.mp.mapper.DynamicMapper.dynamicSelect";
            result = sqlSession.selectOne(method, sqlMap);
            sqlSession.commit();
            return result;
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
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


    /**
     * 批量提交
     * forceExecute 表示是否强制提交，sqls的size小于batchSize
     * */
    public static void executeBatch(SqlSessionFactory sessionFactory, List<String> sqls, int batchSize, boolean forceExecute) throws SQLException {
        SqlSession sqlSession = null;

        if (sqls == null) {
            return;
        }
        if (sqls.size() < batchSize && !forceExecute) {
            return;
        }

        sqlSession = sessionFactory.openSession();
        Connection conn = sqlSession.getConnection();
        conn.setAutoCommit(false);
        try {
            Statement statement = conn.createStatement();
            for (String sql : sqls) {
                statement.addBatch(sql);
            }
            statement.executeBatch();
            conn.commit();
            sqls.clear();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw e;
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 批量提交
     * forceExecute 表示是否强制提交，sqls的size小于batchSize
     * 使用ExecutorType.Batch,效率应该没有executeBatch快
     * */
    public static void executeBatch2(SqlSessionFactory sessionFactory, List<String> sqls, int batchSize, boolean forceExecute) throws SQLException {
        SqlSession sqlSession = null;

        if (sqls == null) {
            return;
        }
        if (sqls.size() < batchSize && !forceExecute) {
            return;
        }

        sqlSession = sessionFactory.openSession(ExecutorType.BATCH, false);
        try {
            for (String sql : sqls) {
                String method = "com.iscas.biz.mp.mapper.DynamicMapper.dynamicUpdate";
                Map<String, String> sqlMap = createSqlMap(sql);
                sqlSession.update(method, sqlMap);
            }
            sqlSession.commit();
            sqls.clear();
        } catch (Exception e) {
            try {
                sqlSession.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw e;
        } finally {
            try {
                sqlSession.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}

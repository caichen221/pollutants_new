package com.iscas.datasong.connector.jdbc;

import com.iscas.datasong.client.DataSongHttpClient;
import com.iscas.datasong.connector.Constants;
import com.iscas.datasong.connector.conf.HostInfo;
import com.iscas.datasong.lib.common.DataSongException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/29 18:32
 * @since jdk1.8
 */
public class ConnectionImpl implements Connection, Constants {
    private HostInfo origHostInfo;
    private String dsUrl;
    private String dbName;
    private DataSongHttpClient dsHttpClient;

    public HostInfo getOrigHostInfo() {
        return origHostInfo;
    }

    public String getDsUrl() {
        return dsUrl;
    }

    public String getDbName() {
        return dbName;
    }

    public DataSongHttpClient getDsHttpClient() {
        return dsHttpClient;
    }

    /**
     * 默认结果集类型
     * */
    private static final int DEFAULT_RESULT_SET_TYPE = ResultSet.TYPE_FORWARD_ONLY;

    private static final int DEFAULT_RESULT_SET_CONCURRENCY = ResultSet.CONCUR_READ_ONLY;

    public ConnectionImpl(HostInfo hostInfo) throws SQLException {
        this.origHostInfo = hostInfo;
        //生成datasong的URL
        createDsUrl(hostInfo);
        //生成datasong cleint
        try {
            createDsHttpClient(hostInfo);
        } catch (DataSongException e) {
            throw new SQLException("获取datasong连接失败", e);
        }
    }



    public static ConnectionImpl getInstance(HostInfo hostInfo) throws SQLException {
        return new ConnectionImpl(hostInfo);
    }

    @Override
    public Statement createStatement() throws SQLException {
        return createStatement(DEFAULT_RESULT_SET_TYPE, DEFAULT_RESULT_SET_CONCURRENCY);
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        StatementImpl statement = new StatementImpl(this);
        statement.setResultSetType(resultSetType);
        statement.setResultSetConcurrency(resultSetConcurrency);
        return statement;
    }


    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return prepareStatement(sql, DEFAULT_RESULT_SET_TYPE, DEFAULT_RESULT_SET_CONCURRENCY);
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        return null;
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return null;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {

    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return false;
    }

    @Override
    public void commit() throws SQLException {

    }

    @Override
    public void rollback() throws SQLException {

    }

    @Override
    public void close() throws SQLException {

    }

    @Override
    public boolean isClosed() throws SQLException {
        return false;
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return null;
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {

    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return false;
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {

    }

    @Override
    public String getCatalog() throws SQLException {
        return null;
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {

    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return 0;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {

    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        PreparedStatementImpl statement = new PreparedStatementImpl(this, sql);
        statement.setResultSetType(resultSetType);
        statement.setResultSetConcurrency(resultSetConcurrency);
        return statement;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return null;
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

    }

    @Override
    public void setHoldability(int holdability) throws SQLException {

    }

    @Override
    public int getHoldability() throws SQLException {
        return 0;
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return null;
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        return null;
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {

    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {

    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return prepareStatement(sql);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return prepareStatement(sql);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return prepareStatement(sql);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return prepareStatement(sql);
    }

    @Override
    public Clob createClob() throws SQLException {
        return null;
    }

    @Override
    public Blob createBlob() throws SQLException {
        return null;
    }

    @Override
    public NClob createNClob() throws SQLException {
        return null;
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return null;
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return false;
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {

    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {

    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return null;
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return null;
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return null;
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return null;
    }

    @Override
    public void setSchema(String schema) throws SQLException {

    }

    @Override
    public String getSchema() throws SQLException {
        return null;
    }

    @Override
    public void abort(Executor executor) throws SQLException {

    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return 0;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    private void createDsUrl(HostInfo hostInfo) {
        dbName = hostInfo.getDatabase();
        dsUrl = String.format("%s%s%s%s/%s", DS_URL_PREFIX_HTTP, hostInfo.getHostPortPair(), DS_URL_DATASONG,
                DS_URL_DATASERVICE, dbName);
    }

    private void createDsHttpClient(HostInfo hostInfo) throws DataSongException {
        try {
            Constructor<DataSongHttpClient> constructor = DataSongHttpClient.class.getDeclaredConstructor(String.class, String.class);
            constructor.setAccessible(true);
            dsHttpClient = constructor.newInstance(String.format("http://%s:%s/datasong/", hostInfo.getHost(), hostInfo.getPort()), dbName);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}

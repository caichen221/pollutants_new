package com.iscas.datasong.connector.jdbc;

import com.iscas.datasong.connector.exception.DatasongClientException;
import com.iscas.datasong.connector.jdbc.statement.ExecuteDelete;
import com.iscas.datasong.connector.jdbc.statement.ExecuteInsert;
import com.iscas.datasong.connector.jdbc.statement.ExecuteQuery;
import com.iscas.datasong.connector.jdbc.statement.ExecuteUpdate;
import com.iscas.datasong.lib.common.DataSongException;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2022/1/7 16:35
 * @since jdk1.8
 */
public class StatementImpl implements DsStatement {

    private int resultSetType;
    private int resultSetConcurrency;

    private ConnectionImpl connection;

    private int fetchSize = 1000;

    private ResultSet rs;

    private int updateCount = 0;

    public void setResultSetType(int resultSetType) {
        this.resultSetType = resultSetType;
    }

    public void setResultSetConcurrency(int resultSetConcurrency) {
        this.resultSetConcurrency = resultSetConcurrency;
    }

    public StatementImpl(ConnectionImpl connection) {
        this.connection = connection;
    }


    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        ResultSetImpl rs = initRs();
        rs.setFetchSize(getFetchSize());
        try {
            net.sf.jsqlparser.statement.Statement statement = CCJSqlParserUtil.parse(sql);
            if (!(statement instanceof Select)) {
                throw new SQLException("Statement.executeQuery()不能执行不生成结果集的语句: " + sql);
            }
            ExecuteQuery.execute(rs, statement);
        } catch (JSQLParserException e) {
            throw new SQLException("SQL解析出错", e);
        } catch (DataSongException e) {
            throw new SQLException("查询datasong出错", e);
        } catch (DatasongClientException e) {
            throw new SQLException("datasong-connect-java处理异常:" + e.getMessage(), e);
        }
        return rs;
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        try {
            net.sf.jsqlparser.statement.Statement statement = CCJSqlParserUtil.parse(sql);
            if (statement instanceof Select) {
                // 执行查询语句
                executeQuery(sql);
                return 0;
            } else if (statement instanceof Insert) {
                return ExecuteInsert.execute((Insert) statement, connection);
            } else if (statement instanceof Update) {
                return ExecuteUpdate.execute((Update) statement, connection, fetchSize);
            } else if (statement instanceof Delete) {
                return ExecuteDelete.execute((Delete) statement, connection, fetchSize);
            }
        } catch (JSQLParserException e) {
            throw new SQLException("SQL解析出错", e);
        }  catch (DataSongException e) {
            throw new SQLException("操作datasong出错", e);
        } catch (DatasongClientException e) {
            throw new SQLException("datasong-connect-java处理异常:" + e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public void close() {
        // nothing to do
    }

    @Override
    public int getMaxFieldSize() {
        return 0;
    }

    @Override
    public void setMaxFieldSize(int max) {
    }

    @Override
    public int getMaxRows() {
        return 0;
    }

    @Override
    public void setMaxRows(int max) {
    }

    @Override
    public void setEscapeProcessing(boolean enable) {
    }

    @Override
    public int getQueryTimeout() {
        return 0;
    }

    @Override
    public void setQueryTimeout(int seconds) {
    }

    @Override
    public void cancel() {
    }

    @Override
    public SQLWarning getWarnings() {
        return null;
    }

    @Override
    public void clearWarnings() {
    }

    @Override
    public void setCursorName(String name) {
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        try {
            net.sf.jsqlparser.statement.Statement statement = CCJSqlParserUtil.parse(sql);
            if (statement instanceof Select) {
                rs = executeQuery(sql);
                return true;
            } else {
                executeUpdate(sql);
                return false;
            }
        } catch (JSQLParserException e) {
            throw new SQLException("SQL解析出错", e);
        }
    }

    @Override
    public ResultSet getResultSet() {
        return rs;
    }

    @Override
    public int getUpdateCount() {
        return updateCount;
    }

    @Override
    public boolean getMoreResults() {
        return false;
    }

    @Override
    public void setFetchDirection(int direction) {
    }

    @Override
    public int getFetchDirection() {
        return 0;
    }

    @Override
    public void setFetchSize(int rows) {
        this.fetchSize = rows;
    }

    @Override
    public int getFetchSize() {
        return fetchSize;
    }

    @Override
    public int getResultSetConcurrency() {
        return ResultSet.CONCUR_READ_ONLY;
    }

    @Override
    public int getResultSetType() {
        return ResultSet.FETCH_FORWARD;
    }

    @Override
    public void addBatch(String sql) {

    }

    @Override
    public void clearBatch() {

    }

    @Override
    public int[] executeBatch() {
        return new int[0];
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public boolean getMoreResults(int current) {
        return false;
    }

    @Override
    public ResultSet getGeneratedKeys() {
        return null;
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) {
        return 0;
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return false;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return 0;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return false;
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {

    }

    @Override
    public boolean isPoolable() throws SQLException {
        return false;
    }

    @Override
    public void closeOnCompletion() throws SQLException {

    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    private ResultSetImpl initRs() {
        return new ResultSetImpl(this);
    }
}

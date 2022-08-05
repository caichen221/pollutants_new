package com.iscas.datasong.connector.jdbc;

import com.iscas.datasong.connector.exception.DatasongClientException;
import com.iscas.datasong.connector.jdbc.statement.ExecuteDelete;
import com.iscas.datasong.connector.jdbc.statement.ExecuteInsert;
import com.iscas.datasong.connector.jdbc.statement.ExecuteQuery;
import com.iscas.datasong.connector.jdbc.statement.ExecuteUpdate;
import com.iscas.datasong.connector.util.CollectionUtils;
import com.iscas.datasong.lib.common.DataSongException;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private int autoGeneratedKeys = Statement.RETURN_GENERATED_KEYS;

    /**
    * 缓存datasong插入的ID
    * */
    private List<String> dsIds;

    private List<String> batchSqls = new ArrayList<>();

    private boolean closed = false;

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
        checkClosed();
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
        checkClosed();
        try {
            net.sf.jsqlparser.statement.Statement statement = CCJSqlParserUtil.parse(sql);
            if (statement instanceof Select) {
                // 执行查询语句
                executeQuery(sql);
                return 0;
            } else if (statement instanceof Insert) {
                List<String> dsIds = ExecuteInsert.execute((Insert) statement, connection);
                this.dsIds = dsIds;
                return dsIds != null ? dsIds.size() : 0;
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
        closed = true;
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
        checkClosed();
        try {
            net.sf.jsqlparser.statement.Statement statement = CCJSqlParserUtil.parse(sql);
            if (statement instanceof Select) {
                rs = executeQuery(sql);
                return true;
            } else {
                updateCount = executeUpdate(sql);
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
        batchSqls.add(sql);
    }

    @Override
    public void clearBatch() {
        batchSqls.clear();
    }

    @Override
    public int[] executeBatch() throws SQLException {
        checkClosed();
        int[] res = new int[batchSqls.size()];
        for (int i = 0; i < batchSqls.size(); i++) {
            res[i] = executeUpdate(batchSqls.get(i));
        }
        return res;
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
    public ResultSet getGeneratedKeys() throws SQLException {
        checkClosed();
        if (RETURN_GENERATED_KEYS == autoGeneratedKeys) {
            ResultSetImpl rs = new ResultSetImpl(this);
            if (CollectionUtils.isNotEmpty(dsIds)) {
                List<Map<String, Object>> data = dsIds.stream().map(id -> {
                    Map<String, Object> header = new HashMap<>();
                    header.put("_id", id);
                    return header;
                }).collect(Collectors.toList());
                rs.setCacheData(data);
            }
            rs.setHeaderMapping(new HashMap<>(){{put(0, "_id");}});
            return rs;
        }
        return null;
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        checkClosed();
        this.autoGeneratedKeys = autoGeneratedKeys;
        return executeUpdate(sql);
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        // todo 暂不处理columnIndexes
        return executeUpdate(sql);
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        // todo 暂不处理columnNames
        return executeUpdate(sql);
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        checkClosed();
        this.autoGeneratedKeys = autoGeneratedKeys;
        return execute(sql);
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        // todo 暂不处理columnIndexes
        return execute(sql);
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        // todo 暂不处理columnNames
        return execute(sql);
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return 0;
    }

    @Override
    public boolean isClosed() {
        return closed;
    }

    @Override
    public void setPoolable(boolean poolable) {

    }

    @Override
    public boolean isPoolable() {
        return false;
    }

    @Override
    public void closeOnCompletion() {
        close();
    }

    @Override
    public boolean isCloseOnCompletion() {
        return isClosed();
    }

    @Override
    public <T> T unwrap(Class<T> iface) {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) {
        return false;
    }

    private ResultSetImpl initRs() {
        return new ResultSetImpl(this);
    }

    private void checkClosed() throws SQLException {
        if (closed) {
            throw new SQLException("statement已关闭");
        }
    }
}

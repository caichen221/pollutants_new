package com.iscas.biz.mp.util;

import com.iscas.biz.mp.model.mysql.MysqlColumn;
import com.iscas.biz.mp.model.mysql.MysqlTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/30 9:48
 * @since jdk1.8
 */
public class MysqlUtils {
    private MysqlUtils() {}

    public static List<MysqlColumn> getColumn(Connection conn, String dbName, String tableName) throws SQLException {
        try {
            List<MysqlColumn> cols = new ArrayList<>();
            String sql = "select * from information_schema.COLUMNS where TABLE_SCHEMA = ? AND TABLE_NAME = ?";
            ResultSet rs = JdbcUtils.query(conn, sql, Arrays.asList(dbName, tableName));
            while (rs.next()) {
                cols.add(convertOneTableCols(rs));
            }
            return cols;
        } finally {
            try {
                JdbcUtils.closeConn(conn);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    private static MysqlColumn convertOneTableCols(ResultSet rs) throws SQLException {
        MysqlColumn column = new MysqlColumn();
        column.setTableName(rs.getString("TABLE_NAME"));
        column.setColName(rs.getString("COLUMN_NAME"));
        column.setDefaultVal(rs.getObject("COLUMN_DEFAULT"));
        column.setNullable(Objects.equals("YES", rs.getString("IS_NULLABLE")) ? true : false);
        column.setDataType(rs.getString("DATA_TYPE"));
        column.setOrdinalPosition(rs.getInt("ORDINAL_POSITION"));
        column.setComment(rs.getString("COLUMN_COMMENT"));
        column.setTableSchema(rs.getString("TABLE_SCHEMA"));
        column.setCharMaxLen(rs.getObject("CHARACTER_MAXIMUM_LENGTH") == null ? null :
                rs.getLong("CHARACTER_MAXIMUM_LENGTH"));
        column.setCharOctetLen(rs.getObject("CHARACTER_OCTET_LENGTH") == null ? null :
                rs.getLong("CHARACTER_OCTET_LENGTH"));
        column.setCharset(rs.getString("CHARACTER_SET_NAME"));
        column.setCollation(rs.getString("COLLATION_NAME"));
        column.setColumnType(rs.getString("COLUMN_TYPE"));
        column.setNumPrecision(rs.getObject("NUMERIC_PRECISION") == null ? null :
                rs.getLong("NUMERIC_PRECISION"));
        column.setNumScale(rs.getObject("NUMERIC_SCALE") == null ? null :
                rs.getLong("NUMERIC_SCALE"));
        column.setDatetimePrecision(rs.getObject("DATETIME_PRECISION") == null ? null :
                rs.getInt("DATETIME_PRECISION"));
        return column;
    }

    public static MysqlTable getTable(Connection conn, String dbName, String tableName) throws SQLException {
        try {
            String sql = "select * from information_schema.TABLES where TABLE_SCHEMA = ? AND TABLE_NAME = ?";
            ResultSet rs = JdbcUtils.query(conn, sql, Arrays.asList(dbName, tableName));
            if (rs.next()) {
                return convertOneTable(rs);
            }
            return null;
        } finally {
            try {
                JdbcUtils.closeConn(conn);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public static List<MysqlTable> getTables(Connection conn, String dbName) throws SQLException {
        try {
            List<MysqlTable> tables = new ArrayList<>();
            String sql = "select * from information_schema.TABLES where TABLE_SCHEMA = ?";
            ResultSet rs = JdbcUtils.query(conn, sql, Arrays.asList(dbName));
            while (rs.next()) {
                tables.add(convertOneTable(rs));
            }
            return tables;
        } finally {
            try {
                JdbcUtils.closeConn(conn);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private static MysqlTable convertOneTable(ResultSet rs) throws SQLException {
        MysqlTable table = new MysqlTable();
        table.setName(rs.getString("TABLE_NAME"));
        table.setComment(rs.getString("TABLE_COMMENT"));
        table.setSchema(rs.getString("TABLE_SCHEMA"));
        table.setType(rs.getString("TABLE_TYPE"));
        table.setEngine(rs.getString("ENGINE"));
        table.setVersion(rs.getObject("VERSION") == null ? null  : rs.getObject("VERSION").toString());
        table.setRowFormat(rs.getObject("ROW_FORMAT") == null ? null  : rs.getObject("ROW_FORMAT").toString());
        table.setRows(rs.getObject("TABLE_ROWS") == null ? 0L  : rs.getLong("TABLE_ROWS"));
        table.setCreateTime(rs.getObject("CREATE_TIME") == null ? null : rs.getDate("CREATE_TIME"));
        table.setUpdateTime(rs.getObject("UPDATE_TIME") == null ? null : rs.getDate("UPDATE_TIME"));
        table.setComment(rs.getString("TABLE_COLLATION"));
        return table;
    }


}

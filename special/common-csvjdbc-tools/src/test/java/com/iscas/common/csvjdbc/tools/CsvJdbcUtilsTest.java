package com.iscas.common.csvjdbc.tools;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2023/4/28 17:25
 */
class CsvJdbcUtilsTest {
    @Test
    public void test() throws SQLException, ClassNotFoundException {
        Properties properties = new Properties();
        properties.put("separator", ",");
        properties.put("fileExtension", ".csv");
        Connection conn = CsvJdbcUtils.getConn("d:/tmp", properties);
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("select * from a");
        while (rs.next()) {
            System.out.println(rs.getObject(1) + "," + rs.getObject(2));
        }
        conn.close();
    }
}
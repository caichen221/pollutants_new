package com.iscas.opendistro;

import java.sql.*;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/4/22 18:09
 * @since jdk1.8
 */
public class Test {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:elasticsearch://localhost:9200";

        Connection con = DriverManager.getConnection(url);
        Statement st = con.createStatement();
        ResultSet resultSet = st.executeQuery("select * from people.man");
        System.out.println(resultSet);
        System.out.println(st);
    }
}

package com.iscas.samples.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/7/8 11:19
 * @since jdk1.8
 */
public class H2Test {
    public static void main(String[] args) throws Exception{

        Connection connection = null;
        Class.forName("org.h2.Driver");
        //内存计算
        connection = DriverManager.getConnection("jdbc:h2:mem:");
        //支持持久存储
        //connection = DriverManager.getConnection("jdbc:h2:file:./bb.bb");
        //connection = DriverManager.getConnection("jdbc:h2:./bb.bb");
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);  // set timeout to 30 sec.
        statement.executeUpdate("drop table if exists person ");
        //statement.executeUpdate("drop table person ");
        statement.executeUpdate("create table person (id integer, name varchar(50))");
        statement.executeUpdate("insert into person values(1, '我是第一个学生')");
        statement.executeUpdate("insert into1 person values(2, '中国人')");
        statement.executeUpdate("insert into person values(45, '外国人')");
        statement.executeUpdate("insert into person values(4, '中国人')");
        ResultSet rs = statement.executeQuery("select * from person order by id ");
        while(rs.next()){
            System.out.println("id: "+rs.getInt("id")+"   name: " + rs.getString("name"));
        }
        rs.close();
        statement.close();
        connection.close();
    }
}

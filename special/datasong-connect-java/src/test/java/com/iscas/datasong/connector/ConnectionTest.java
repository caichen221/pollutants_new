package com.iscas.datasong.connector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/29 21:13
 * @since jdk1.8
 */
public class ConnectionTest {
    Connection connection = null;
    @BeforeEach
    public void testConnection() {
        try {
            Class.forName("com.iscas.datasong.connector.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:datasong://192.168.100.21:15680/dmotest", null, null);
            System.out.println(connection);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 全查
     * */
    @Test
    public void test() throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println(statement);
        ResultSet rs = statement.executeQuery("select * from testtable");
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
    }

    /**
     * limit
     * */
    @Test
    public void testStatement2() throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println(statement);
        ResultSet rs = statement.executeQuery("select * from testtable limit 0, 1");
        System.out.println(rs);
    }

    /**
     * 别名
     * */
    @Test
    public void testStatement3() throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println(statement);
        ResultSet rs = statement.executeQuery("select name AS lala, t.*, id, max(id) AS x, avg(name) from testtable t limit 0, 1");
        System.out.println(rs);
    }

    /**
     * 查询所i有
     * */
    @Test
    public void testSelectAll() throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println(statement);
        ResultSet rs = statement.executeQuery("select * from testtable t");
        while (rs.next()) {
            String col1 = rs.getString(1);
            String col2 = rs.getString(2);
            int col3 = rs.getInt(3);
            String col4 = rs.getString(4);
            System.out.println(col1);
            System.out.println(col2);
            System.out.println(col3);
            System.out.println(col4);
        }
    }

    /**
     * 查询所i有
     * */
    @Test
    public void testSelectAllLimit() throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println(statement);
        ResultSet rs = statement.executeQuery("select * from testtable t limit 1");
        while (rs.next()) {
            String col1 = rs.getString(1);
            String col2 = rs.getString(2);
            int col3 = rs.getInt(3);
            String col4 = rs.getString(4);
            System.out.println(col1);
            System.out.println(col2);
            System.out.println(col3);
            System.out.println(col4);
        }
    }

    /**
     * 测试查询并使用函数
     * */
    @Test
    public void testSelectUseFunction() throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println(statement);
        ResultSet rs = statement.executeQuery("select LOWER(NAME) from testtable t limit 1");
        while (rs.next()) {
            String col1 = rs.getString("LOWER(NAME)");
            System.out.println(col1);
        }
    }

    /**
     * 测试执行executeQuery中使用update语句,查看报错
     * */
    @Test
    public void testUpdateError() throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println(statement);
        try {
            ResultSet rs = statement.executeQuery("update testtable t set name = '111'");
            while (rs.next()) {
                int col1 = rs.getInt(1);
                System.out.println(col1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试执行executeUpdate中使用insert
     * */
    @Test
    public void testInsert() throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println(statement);
        int count = statement.executeUpdate("insert into testtable(id, name) values (77, 'testName')");
        System.out.println(count);
    }

    /**
     * 测试执行executeUpdate中使用insert
     * */
    @Test
    public void testInsert2() throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println(statement);
        int count = statement.executeUpdate("insert into testtable values ('wegwe',88, 'testName')");
        System.out.println(count);
    }

    /**
     * 测试执行executeUpdate中使用insert
     * */
    @Test
    public void testInsert3() throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println(statement);
        int count = statement.executeUpdate("insert into testtable(id, name) values (89, 'testName'),(90, 'testName')");
        System.out.println(count);
    }

    /**
     * 测试执行executeUpdate中使用update
     * */
    @Test
    public void testUpdate1() throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println(statement);
        int count = statement.executeUpdate("update testtable set name = 'testName_2' WHERE id = 90");
        System.out.println(count);
    }

    /**
     * 测试执行executeUpdate中使用delete
     * */
    @Test
    public void testDelete1() throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println(statement);
        int count = statement.executeUpdate("delete from testtable  WHERE id = 89");
        System.out.println(count);
    }

    /**
     * 测试执行查询，group by
     * */
    @Test
    public void testGroupBy() throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println(statement);
        ResultSet rs = statement.executeQuery("select count(*), sum(id), name, id from testtable  group by name, id");
        while (rs.next()) {
            int c = rs.getInt("count(*)");
            int s = rs.getInt("sum(id)");
            String name = rs.getString("name");
            int id = rs.getInt("id");
            System.out.println("id=" + id + ";name=" + name + ";count=" + c + ";sum=" + s);
        }
    }

    /**
     * 测试插入数据，获取返回的ID
     * */
    @Test
    public void testGetGenerateKey() throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println(statement);
        statement.executeUpdate("insert into testtable (id, name) values (92, '王二')", Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
    }

    /**
     * 测试prepareStatement
     * */
    @Test
    public void testPrepareStatement() throws SQLException {
        PreparedStatement prepareStatement = connection.prepareStatement("select * from testtable");
        ResultSet resultSet = prepareStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("name"));
        }
    }

    /**
     * 测试prepareStatement
     * */
    @Test
    public void testPrepareStatement2() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from testtable where name = ?");
        ps.setString(1, "testName");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("name"));
            System.out.println(resultSet.getInt("id"));
        }
    }

    /**
     * 测试count(*)
     * */
    @Test
    public void testCount() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select count(*), sum(id) from testtable WHERE name like '%test%'");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1));
            System.out.println(resultSet.getInt(2));

        }
    }

    /**
     * 测试Mid
     * */
    @Test
    public void testMid() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select mid('axaaaa', 1, 3) from testtable WHERE name like '%test%'");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }

    /**
     * 测试position
     * */
    @Test
    public void testPosition() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select position('a' in 'abc') from testtable WHERE name like '%test%'");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1));
        }
    }

    /**
     * 测试repeat
     * */
    @Test
    public void testRepeat() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select repeat('a', 5) from testtable WHERE name like '%test%'");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }

    /**
     * 测试replace
     * */
    @Test
    public void testReplace() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select replace('a25@wse', '@', '%%%') from testtable WHERE name like '%test%'");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }

    /**
     * 测试reverse
     * */
    @Test
    public void testReverse() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select reverse('a25@wse') from testtable WHERE name like '%test%'");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }

    /**
     * 测试right
     * */
    @Test
    public void testRight() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select right('a25@wse', 2) from testtable WHERE name like '%test%'");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }

    /**
     * 测试RPAD
     * */
    @Test
    public void testRpad() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select Rpad('a25@wse', 12, 'zzz') from testtable WHERE name like '%test%'");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }

    /**
     * 测试RTRIM
     * */
    @Test
    public void testRtrim() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select RTRIM('  a25@wse  ') from testtable WHERE name like '%test%'");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }

    /**
     * 测试SPACE
     * */
    @Test
    public void testSpace() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select SPACE(4) from testtable WHERE name like '%test%'");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }

    /**
     * 测试STRCMP
     * */
    @Test
    public void testStrcmp() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select STRCMP(4, 10) from testtable WHERE name like '%test%'");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1));
        }
    }

    /**
     * 测试SUBSTR
     * */
    @Test
    public void testSubStr() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select substr('中国你好呀，你好呀', 2, 2) from testtable WHERE name like '%test%'");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }

    /**
     * 测试SUBSTRING
     * */
    @Test
    public void testSubString() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select substring('中国你好呀，你好呀', 2, 2) from testtable WHERE name like '%test%'");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }

    /**
     * 测试SUBSTRING_INDEX
     * */
    @Test
    public void testSubStringIndex() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select substring_index('中国*你好*呀，你好*呀', '*', 0) from testtable WHERE name like '%test%'");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }

    /**
     * 测试trim
     * */
    @Test
    public void testTrim() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select trim(' wegweg ') from testtable WHERE name like '%test%'");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }

    /**
     * 测试ucase
     * */
    @Test
    public void testUcase() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select Ucase(' wegweg ') from testtable WHERE name like '%test%'");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }

    /**
     * 测试UPPER
     * */
    @Test
    public void testUpper() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select Upper(' wegweg ') from testtable WHERE name like '%test%'");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }

}

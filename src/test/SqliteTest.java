/**
 * Project:      AsyncTask
 * FileName:     SqliteTest.java
 * @Description: TODO
 * @author:      ligh4
 * @version      V1.0 
 * Createdate:   2015年3月11日 下午3:08:47
 * Copyright:    Copyright(C) 2014-2015
 * Company       Lenovo LTD.
 * All rights Reserved, Designed By Lenovo CIC.
 */
package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 类 SqliteTest 的实现描述：TODO 类实现描述
 * 
 * @author ligh4 2015年3月11日下午3:08:47
 */
public class SqliteTest {

    /**
     * @author ligh4 2015年3月11日下午3:08:47
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30); // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists person");
            statement.executeUpdate("create table person (id integer, name string)");
            statement.executeUpdate("insert into person values(1, 'leo')");
            statement.executeUpdate("insert into person values(2, 'yui')");
            ResultSet rs = statement.executeQuery("select * from person");
            while (rs.next()) {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));
            }
        } catch (SQLException e) {
            // if the error message is "out of memory", 
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
    }

}

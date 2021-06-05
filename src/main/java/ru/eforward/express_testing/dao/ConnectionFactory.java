package ru.eforward.express_testing.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionFactory {
    static Connection connection = null;
    static{
        try {
            Context context = new InitialContext();
            DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/myefdb");
            System.out.println("DataSource ds =" + ds);
            connection = ds.getConnection();
            System.out.println("connection = " + connection);
        } catch (NamingException e) {
            System.out.println("class ConnectionFactory : NamingException e");
            e.printStackTrace();
        } catch (SQLException throwables) {
            System.out.println("class ConnectionFactory : SQLException throwables");
            throwables.printStackTrace();
        }
    }
    public static Connection getConnection(){
        return connection;
    }

}
package ru.eforward.express_testing.dbConnection;

import ru.eforward.express_testing.utils.LogHelper;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class PoolConnector {
    static Connection connection = null;
    static{
        try {
            Context context = new InitialContext();
            DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/myefdb");
            LogHelper.writeMessage("DataSource ds =" + ds);
            connection = ds.getConnection();
            LogHelper.writeMessage("connection = " + connection);
        } catch (NamingException e) {
            LogHelper.writeMessage("class ConnectionFactory : NamingException e");
            e.printStackTrace();
        } catch (SQLException throwables) {
            LogHelper.writeMessage("class ConnectionFactory : SQLException throwables");
            throwables.printStackTrace();
        }
    }
    public static Connection getConnection(){
        return connection;
    }

}
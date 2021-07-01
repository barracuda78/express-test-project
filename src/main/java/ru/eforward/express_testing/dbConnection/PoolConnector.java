package ru.eforward.express_testing.dbConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.eforward.express_testing.utils.LogHelper;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class PoolConnector {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoolConnector.class);
    static Connection connection;

    static{
        connection = createConnection();
    }

    public static Connection getConnection(){
        try {
            if(connection == null || connection.isClosed()){
                connection = createConnection();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connection){
        try {
            if (connection != null && !connection.isClosed()){
                connection.close();
            }
        } catch (SQLException throwables) {
            LOGGER.error("SQLException while closing connection");
            throwables.printStackTrace();
        }
    }

    private static Connection createConnection(){
        Connection connection = null;
        try {
            Context context = new InitialContext();
            DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/myefdb");
            LogHelper.writeMessage("DataSource ds =" + ds);
            connection = ds.getConnection();
            LogHelper.writeMessage("connection = " + connection);
        } catch (NamingException e) {
            LOGGER.error("class ConnectionFactory : NamingException: " + e.getMessage());
            LogHelper.writeMessage("class ConnectionFactory : NamingException: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException throwables) {
            LOGGER.error("class ConnectionFactory : SQLException: " + throwables.getMessage());
            LogHelper.writeMessage("class ConnectionFactory : SQLException: " + throwables.getMessage());
            throwables.printStackTrace();
        }
        return connection;
    }
}
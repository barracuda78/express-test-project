package ru.eforward.express_testing.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String DATASOURCE_NAME = "newDB";
    private static DataSource ds = null;

    static {
        Context initContext = null;
        try {
            initContext = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        Context envContext = null;
        try {
            envContext = (Context)initContext.lookup("java:/comp/env");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        try {
            ds = (DataSource) envContext.lookup(DATASOURCE_NAME);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = ds.getConnection();
        return conn;
    }
}

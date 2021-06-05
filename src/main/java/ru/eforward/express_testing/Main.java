package ru.eforward.express_testing;

import ru.eforward.express_testing.dao.ConnectionFactory;
import ru.eforward.express_testing.dao.Connector;
import ru.eforward.express_testing.model.Student;
import ru.eforward.express_testing.model.User;
import ru.eforward.express_testing.model.UserBuilder;

import java.sql.*;

import static ru.eforward.express_testing.model.User.ROLE.STUDENT;

public class Main {
    public static String URL;
    private static String USER;
    private static String PASSWORD;
    private static String QUERY;

    private static Connection connection;
    private static PreparedStatement pst;

    public static void main(String[] args) throws SQLException {
        System.out.println("loadDriver = " + Connector.loadDriver());
        connection = Connector.getConnection();

//        QUERY = "ALTER TABLE TESTTABLE ADD email13 VARCHAR(30);";
//        Statement statement = connection.createStatement();
//        boolean executed = statement.execute(QUERY);
//        System.out.println("executed = " + executed);
//        statement.close();
//        connection.close();

        connection.close();

    }
}

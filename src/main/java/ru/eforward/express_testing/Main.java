package ru.eforward.express_testing;

import ru.eforward.express_testing.dbConnection.DriverManagerConnector;
import ru.eforward.express_testing.utils.LogHelper;

import java.sql.*;

public class Main {
    public static String URL;
    private static String USER;
    private static String PASSWORD;
    private static String QUERY;

    private static Connection connection;
    private static PreparedStatement pst;

    //todo: 1. специальный формат тестов - как говорил ЗЩайцев.
    //todo: 2. все замечания с первой предзащиты переписать в эл вид.
    //todo: 3. сделать хэширование с той самоой библиотекой.

    public static void main(String[] args) throws SQLException {
        LogHelper.writeMessage("loadDriver = " + DriverManagerConnector.loadDriver());
        connection = DriverManagerConnector.getConnection();

//        QUERY = "ALTER TABLE TESTTABLE ADD email13 VARCHAR(30);";
//        Statement statement = connection.createStatement();
//        boolean executed = statement.execute(QUERY);
//        System.out.println("executed = " + executed);
//        statement.close();
//        connection.close();

        connection.close();

    }
}

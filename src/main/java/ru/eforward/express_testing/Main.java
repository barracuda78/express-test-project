package ru.eforward.express_testing;



import org.mindrot.jbcrypt.BCrypt;
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

    public static void main(String[] args) {
        String password = "111";
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println("plain password = " + password);
        System.out.println("hashed password = " + hashed);

        boolean equals = BCrypt.checkpw(password, hashed);
        System.out.println("equals = " + equals);
    }
}

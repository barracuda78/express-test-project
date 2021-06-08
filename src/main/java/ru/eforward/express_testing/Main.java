package ru.eforward.express_testing;



import org.mindrot.jbcrypt.BCrypt;
import ru.eforward.express_testing.dao.TestDAOFilesystemImpl;
import ru.eforward.express_testing.daoInterfaces.TestDAO;
import ru.eforward.express_testing.dbConnection.DriverManagerConnector;
import ru.eforward.express_testing.utils.LogHelper;
import ru.eforward.express_testing.utils.TestFilesReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.*;

public class Main {
    public static String URL;
    private static String USER;
    private static String PASSWORD;
    private static String QUERY;

    private static Connection connection;
    private static PreparedStatement pst;

    public static void main(String[] args) throws IOException {
        TestFilesReader testFilesReader = new TestFilesReader();
        String s = testFilesReader.readTestFile(Paths.get("D:\\coding\\projects\\EF\\express_test_project\\src\\main\\resources\\tests\\eng\\level01\\lesson01.txt"));

        String[] array =stringToTokens(s);
        System.out.println("========array[0]=========");
        System.out.println(array[0]);
        System.out.println("========array[1]=========");
        System.out.println(array[1]);
        System.out.println("========array[2]=========");
        System.out.println(array[2]);
        System.out.println("========array[3]=========");
        System.out.println(array[3]);

    }

    public static String[] stringToTokens(String s){
        return s.split("\n\n");
    }

}

package ru.eforward.express_testing;



import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.LoggerFactory;
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
import org.slf4j.Logger;

public class Main {
    public static final Logger LOGGER = (Logger) LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) throws IOException {
//        TestFilesReader testFilesReader = new TestFilesReader();
//        String s = testFilesReader.readTestFile(Paths.get("D:\\coding\\projects\\EF\\express_test_project\\src\\main\\resources\\tests\\eng\\level01\\lesson01.txt"));
//
//        String[] array =stringToTokens(s);
//        System.out.println("========array[0]=========");
//        System.out.println(array[0]);
//        System.out.println("========array[1]=========");
//        System.out.println(array[1]);
//        System.out.println("========array[2]=========");
//        System.out.println(array[2]);
//        System.out.println("========array[3]=========");
//        System.out.println(array[3]);

        LOGGER.info("Начало работы программы!!!");

        try {
            LOGGER.warn("Внимание! Программа пытается разделить одно число на другое");
            System.out.println(12/0);
        } catch (ArithmeticException x) {

            LOGGER.error("Ошибка! Произошло деление на ноль!");
        }
    }

    public static String[] stringToTokens(String s){
        return s.split("\n\n");
    }

}

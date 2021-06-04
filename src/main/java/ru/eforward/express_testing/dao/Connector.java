package ru.eforward.express_testing.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
Утилитарный класс для загрузки драйвера, получения параметров соединения из файла .properties и создания соединения.
Используется в конструкторе классов DAO;
*/
public class Connector {
    private static Connection connection;
    private static final String URL = "jdbc:hsqldb:hsql://localhost:9001/efdb";
    private static final String USER = "root";
    private static final String PASSWORD = "root";


    //метод для получения параметров подключения:
    protected static Properties getProperties(){
        Properties properties = new Properties();
        try(InputStream inputStream = ClassLoader.getSystemResourceAsStream("D:\\coding\\projects\\EF\\express_test_project\\src\\main\\resources\\database.properties")){
            properties.load(inputStream);
            System.out.println("Файл properties прочитан");
        }catch(IOException ex){
            System.out.println("Ошибка чтения файла properties");
        }

        System.out.println("Параметры подключения:");
        return properties;
    }

    //метод для получения соединения:
    public static Connection getConnection(){
        try {
            if(connection != null && !connection.isClosed()){
                return connection;
            }
        } catch (SQLException ex) {
            System.out.println("Ошибка проверки активности соединения");
        }

        //загрузим драйвер. Если загрузился драйвер - будем возвращщать соединение:
        if(loadDriver()){ //loadDriver()
//            Properties properties = getProperties();
//            String connectionString = properties.getProperty("url") + ";user=" +
//                    properties.getProperty("user") + ";password=" +
//                    properties.getProperty("password");

            String connectionString = URL + ";user=" + USER + ";password=" + PASSWORD;

            System.out.println("Строка подключения: " + connectionString);

            try {
                connection = DriverManager.getConnection(connectionString);
                System.out.println("Соединение создано");
            } catch (SQLException ex) {
                System.out.println("Ошибка получения соединения");
                ex.printStackTrace();
            }
        }
        return connection;
    }

    //метод для загрузки драйвера:
    public static boolean loadDriver(){     //--------------->make it private
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            //Class.forName("java.util.List");
            //               org.hsqldb.jdbc.JDBCDriver
            //Class.forName("org.hsqldb.jdbc.JDBCDriver");
            //Class.forName("org.apache.derby.jdbc.ClientDriver");
            System.out.println("Драйвер  загружен");
            return true;
        } catch (ClassNotFoundException ex) {
            System.out.println("Ошибка загрузки драйвера");
            return false;
        }
    }

    //метод для закрытия соединения:
    private void closeConnection(){
        try {
            if(connection != null && !connection.isClosed()){
                connection.close();
                System.out.println("6. метод closeConnection(): Соединение закрыто");
            }
        } catch (SQLException ex) {
            //Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("6: метод closeConnection(): Ошибка закрытия соединения. " + ex.getMessage());
        }
    }
}
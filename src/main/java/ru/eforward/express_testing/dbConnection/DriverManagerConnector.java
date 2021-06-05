package ru.eforward.express_testing.dbConnection;

import ru.eforward.express_testing.utils.LogHelper;

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
public class DriverManagerConnector{
    private static Connection connection;
//    private static final String URL = "jdbc:hsqldb:hsql://localhost:9001/efdb";
//    private static final String USER = "root";
//    private static final String PASSWORD = "root";


    //метод для получения параметров подключения:
    protected static Properties getProperties(){
        Properties properties = new Properties();
        try(InputStream inputStream = ClassLoader.getSystemResourceAsStream("database.properties")){
            properties.load(inputStream);
            LogHelper.writeMessage("Файл properties прочитан");
        }catch(IOException ex){
            LogHelper.writeMessage("Ошибка чтения файла properties");
            ex.printStackTrace();
        }

        LogHelper.writeMessage("Параметры подключения:");
        return properties;
    }

    //метод для получения соединения:
    public static Connection getConnection(){
        try {
            if(connection != null && !connection.isClosed()){
                return connection;
            }
        } catch (SQLException ex) {
            LogHelper.writeMessage("Ошибка проверки активности соединения");
            ex.printStackTrace();
        }

        //загрузим драйвер. Если загрузился драйвер - будем возвращщать соединение:
        if(loadDriver()){
            Properties properties = getProperties();
            String connectionString =
                    properties.getProperty("url") + ";user=" +
                    properties.getProperty("user") + ";password=" +
                    properties.getProperty("password");

            //String connectionString = URL + ";user=" + USER + ";password=" + PASSWORD;

            LogHelper.writeMessage("Строка подключения: " + connectionString);

            try {
                connection = DriverManager.getConnection(connectionString);
                LogHelper.writeMessage("Соединение создано");
                LogHelper.writeMessage("connection = " + connection);
            } catch (SQLException ex) {
                LogHelper.writeMessage("Ошибка получения соединения");
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
            LogHelper.writeMessage("Драйвер  загружен");
            return true;
        } catch (ClassNotFoundException ex) {
            LogHelper.writeMessage("Ошибка загрузки драйвера");
            return false;
        }
    }

    //метод для закрытия соединения:
    private void closeConnection(){
        try {
            if(connection != null && !connection.isClosed()){
                connection.close();
                LogHelper.writeMessage("6. метод closeConnection(): Соединение закрыто");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            LogHelper.writeMessage("6: метод closeConnection(): Ошибка закрытия соединения. " + ex.getMessage());
        }
    }
}
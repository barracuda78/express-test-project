package ru.eforward.express_testing.dbConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.eforward.express_testing.dao.BranchDAOImpl;
import ru.eforward.express_testing.utils.LogHelper;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Is used only ic ConnectionPool is not used.
 * Util class for loading driver, getting Connection.
*/
public class DriverManagerConnector{
    private static Connection connection;
    private static final Logger LOGGER = LoggerFactory.getLogger(DriverManagerConnector.class);

    /**
     * Reads Connection parameters from .properties file
     */
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

    /**
     * Creates jdbc Connection to DataBase
     */
    public static Connection getConnection(){
        try {
            if(connection != null && !connection.isClosed()){
                return connection;
            }
        } catch (SQLException ex) {
            LogHelper.writeMessage("Ошибка проверки активности соединения");
            ex.printStackTrace();
            LOGGER.error("jdbc connection problem");
        }

        if(loadDriver()){
            Properties properties = getProperties();
            String connectionString =
                    properties.getProperty("url") + ";user=" +
                    properties.getProperty("user") + ";password=" +
                    properties.getProperty("password");

            LogHelper.writeMessage("Строка подключения: " + connectionString);
            try {
                connection = DriverManager.getConnection(connectionString);
                LogHelper.writeMessage("Соединение создано");
                LogHelper.writeMessage("connection = " + connection);
            } catch (SQLException ex) {
                LogHelper.writeMessage("Ошибка получения соединения");
                ex.printStackTrace();
                LOGGER.error("Can not get jdbc connection");
            }
        }
        return connection;
    }

    /**
     * Loads jdbc Driver:
     */
    public static boolean loadDriver(){
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            LogHelper.writeMessage("Драйвер  загружен");
            return true;
        } catch (ClassNotFoundException ex) {
            LogHelper.writeMessage("Ошибка загрузки драйвера");
            LOGGER.error("Can not load jdbc driver");
            return false;
        }
    }

    /**
     * Closes connection:
     */
    private void closeConnection(){
        try {
            if(connection != null && !connection.isClosed()){
                connection.close();
                LogHelper.writeMessage("6. метод closeConnection(): Соединение закрыто");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            LogHelper.writeMessage("6: метод closeConnection(): Ошибка закрытия соединения. " + ex.getMessage());
            LOGGER.error("jdbc connection closing error");
        }
    }
}
package ru.eforward.express_testing.dao;

import ru.eforward.express_testing.daoInterfaces.UserDAO;
import ru.eforward.express_testing.dbConnection.PoolConnector;
import ru.eforward.express_testing.model.User;
import ru.eforward.express_testing.utils.LogHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;

    @Override
    public boolean addUser(User user) {
        LogHelper.writeMessage("---class UserDAOImpl : We start creating new User");
        if(user == null){
            LogHelper.writeMessage("---class UserDAOImpl : User == null");
            return false;
        }

        if(connection == null){
            connection = PoolConnector.getConnection();
        }

        int updateCount = -1;
        if(connection != null){
            try {
                //check if such user with such id already presents in DB. If so - just return from method;
                if(userPresents(user.getId())){
                    LogHelper.writeMessage("class UserDAOImpl, method addUser() : user already exists in DB. id = " + user.getId());
                    return false;
                }

                preparedStatement = connection.prepareStatement("INSERT INTO USERS (LASTNAME, FIRSTNAME, MIDDLENAME, EMAIL, LOGIN, PASSWORD, ROLE_ID, BRANCH_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                //preparedStatement.setInt(1, user.getId()); --id will be set by DB;
                preparedStatement.setString(1, user.getLastName());
                preparedStatement.setString(2, user.getFirstName());
                preparedStatement.setString(3, user.getMiddleName());
                preparedStatement.setString(4, user.getEmail());
                preparedStatement.setString(5, user.getEmail());   //->this is for login being the same as email!!!
                preparedStatement.setString(6, user.getPassword());         //-------------------->нужно сперва захэшировать пароль
                preparedStatement.setInt(7, user.getRole().getId());
                preparedStatement.setInt(8, user.getBranches().get(0).getId());   //---------------------> at this stage I assume only one branch in the List. For further development

                LogHelper.writeMessage("---class UserDAOImpl : preparedStatement prepared.");

                if(!preparedStatement.execute()){
                    updateCount = preparedStatement.getUpdateCount();
                    LogHelper.writeMessage("class SchoolDAOImpl, method addUser() : added records to Users table" + updateCount);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            LogHelper.writeMessage("class UserDAOImpl, method addUser() : connection is null");
        }
        return updateCount > 0;
    }

    @Override
    public User getUserByLoginPassword(final String login, final String password){
        if(login == null || password == null){
            return null;
        }

        User result = null;
        if(connection == null){
            connection = PoolConnector.getConnection();
        }
        if(connection != null){
            try {
                preparedStatement = connection.prepareStatement("SELECT * FROM USERS WHERE LOGIN = ?");
                preparedStatement.setString(1, login);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet != null){
                    if(resultSet.next()){
                        ///ДОПИШИ ТУТ ВЫТАСКИВАНИЕ  ХЭША ПАРОЛЯ  ПО ЮЗЕРУ И СРАВНЕНИЕ ХЭША И ПАРОЛЯ ИЗ ПАРАМЕТРА МЕТОДА.
                        ///boolean equals = BCrypt.checkpw(password, hashed);
                        //return true;
                    }
                }else{
                    LogHelper.writeMessage("class UserDAOImpl, method getUserByLoginPassword() : resultSet is null");
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            LogHelper.writeMessage("class UserDAOImpl, method getUserByLoginPassword() : connection is null");
        }


        return result;
    }

    public boolean userPresents(int userId){
        if(userId < 0 ){
            return false;
        }
        if(connection == null){
            connection = PoolConnector.getConnection();
        }

        if(connection != null){
            try {
                preparedStatement = connection.prepareStatement("SELECT * FROM USERS WHERE ID = ?");
                preparedStatement.setInt(1, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet != null){
                    if(resultSet.next()){
                        return true;
                    }
                }else{
                    LogHelper.writeMessage("class UserDAOImpl, method userPresents() : resultSet is null");
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            LogHelper.writeMessage("class UserDAOImpl, method userPresents() : connection is null");
        }
        return false;
    }
}

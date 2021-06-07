package ru.eforward.express_testing.dao;

import org.mindrot.jbcrypt.BCrypt;
import ru.eforward.express_testing.daoInterfaces.UserDAO;
import ru.eforward.express_testing.dbConnection.PoolConnector;
import ru.eforward.express_testing.model.User;
import ru.eforward.express_testing.model.UserBuilder;
import ru.eforward.express_testing.utils.LogHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

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
                //check if such user already presents in DB. If so - just return from method;
                if(userIsPresent(user.getLogin(), user.getPassword())){
                    LogHelper.writeMessage("class UserDAOImpl, method addUser() : user already exists in DB. id = " + user.getId());
                    return false;
                }

                preparedStatement = connection.prepareStatement("INSERT INTO USERS (LASTNAME, FIRSTNAME, MIDDLENAME, EMAIL, LOGIN, PASSWORD, ROLE_ID, SCHOOL_ID, BRANCH_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                //preparedStatement.setInt(1, user.getId()); --id will be set by DB;
                preparedStatement.setString(1, user.getLastName());
                preparedStatement.setString(2, user.getFirstName());
                preparedStatement.setString(3, user.getMiddleName());
                preparedStatement.setString(4, user.getEmail());
                preparedStatement.setString(5, user.getEmail());   //->this is for login being the same as email!!!
                preparedStatement.setString(6, user.getPassword());
                preparedStatement.setInt(7, user.getRole().getId());
                preparedStatement.setInt(8, user.getSchool());
                preparedStatement.setInt(9, user.getBranches().get(0));   //---------------------> at this stage I assume only one branch in the List. For further development

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

        if(connection == null){
            connection = PoolConnector.getConnection();
        }
        if(connection != null){
            try {
                preparedStatement = connection.prepareStatement("SELECT * FROM USERS WHERE LOGIN = ?");
                preparedStatement.setString(1, login);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(!resultSet.wasNull()){
                    //I'm using 'while' because can have several users with same login.
                    while(resultSet.next()){
                        String hashedPassword = resultSet.getString("PASSWORD");
                        boolean equals = false;
                        if(hashedPassword != null){
                            equals = BCrypt.checkpw(password, hashedPassword);
                        }

                        if(equals){
                            //LASTNAME, FIRSTNAME, MIDDLENAME, EMAIL, LOGIN, PASSWORD, ROLE_ID, BRANCH_ID
                            //take user with this password and return this user;
                            String lastName = resultSet.getString("LASTNAME");
                            String firstName = resultSet.getString("FIRSTNAME");
                            String middleName = resultSet.getString("MIDDLENAME");
                            String email = resultSet.getString("EMAIL");
                            //login = // already defined as method parameter
                            //password = // already defined as method parameter
                            int role_id = resultSet.getInt("ROLE_ID");
                            int school_id = resultSet.getInt("SCHOOL_ID");
                            Integer branch_id = resultSet.getInt("BRANCH_ID");

                            return new UserBuilder(User.ROLE.getRoleById(role_id))
                                    .addLastName(lastName)
                                    .addFirstName(firstName)
                                    .addMiddleName(middleName)
                                    .addEmail(email)
                                    .addLogin(login)
                                    .addPassword(password)
                                    .addSchool(school_id)
                                    .addBranches(new ArrayList<Integer>(Arrays.asList(branch_id))) //list of one element - just a stub for further development
                                    .buildUser();
                        }
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
        return null;
    }

    @Override
    public User.ROLE getRoleByLoginPassword(String login, String password) {
        User.ROLE result = User.ROLE.UNKNOWN;

        if(login == null || password == null){
            return null;
        }

        if(connection == null){
            connection = PoolConnector.getConnection();
        }
        if(connection != null){
            try {
                preparedStatement = connection.prepareStatement("SELECT LOGIN, PASSWORD, ROLE_ID FROM USERS WHERE LOGIN = ?");
                preparedStatement.setString(1, login);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(!resultSet.wasNull()){
                    //I'm using 'while' because can have several users with same login.
                    while(resultSet.next()){
                        String hashedPassword = resultSet.getString("PASSWORD");
                        boolean equals = false;
                        if(hashedPassword != null){
                            equals = BCrypt.checkpw(password, hashedPassword);
                        }

                        if(equals){
                            //LASTNAME, FIRSTNAME, MIDDLENAME, EMAIL, LOGIN, PASSWORD, ROLE_ID, BRANCH_ID
                            int role_id = resultSet.getInt("ROLE_ID");
                            result = User.ROLE.getRoleById(role_id);
                        }
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

    @Override
    public boolean userIsPresent(int userId){
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

    @Override
    public boolean userIsPresent(String login, String password){
        if(login == null || password == null ){
            return false;
        }
        if(connection == null){
            connection = PoolConnector.getConnection();
        }

        if(connection != null){
            try {
                preparedStatement = connection.prepareStatement("SELECT LOGIN, PASSWORD FROM USERS WHERE LOGIN = ?");
                preparedStatement.setString(1, login);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(!resultSet.wasNull()){
                    while(resultSet.next()){
                        String hashedPassword = resultSet.getString("PASSWORD");
                        boolean equals = false;
                        if(hashedPassword != null){
                            equals = BCrypt.checkpw(password, hashedPassword);
                        }

                        if(equals){
                            return true;
                        }
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
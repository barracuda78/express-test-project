package ru.eforward.express_testing.dao;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.eforward.express_testing.daoInterfaces.GroupDAO;
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
import java.util.List;
import java.util.Objects;

public class UserDAOImpl implements UserDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);
    private Connection connection;
    private PreparedStatement preparedStatement;

    @Override
    public boolean addUser(User user, int parent_id) {
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

                preparedStatement = connection.prepareStatement("INSERT INTO USERS (LASTNAME, FIRSTNAME, MIDDLENAME, EMAIL, LOGIN, PASSWORD, ROLE_ID, SCHOOL_ID, BRANCH_ID, TEACHER_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                //preparedStatement.setInt(1, user.getId()); --id will be set by DB;
                preparedStatement.setString(1, user.getLastName());
                preparedStatement.setString(2, user.getFirstName());
                preparedStatement.setString(3, user.getMiddleName());
                preparedStatement.setString(4, user.getEmail());
                preparedStatement.setString(5, user.getEmail());   //->this is for login being the same as email!!!
                preparedStatement.setString(6, user.getPassword());
                preparedStatement.setInt(7, user.getRole().getId());
                preparedStatement.setInt(8, user.getSchool());
                preparedStatement.setInt(9, user.getBranch());   //---> at this stage I assume only one branch in the List. For further development
                //if teacher or admin will be added - use id of user, who is adding, as TEACHER_ID for this new user;
                if(user.getRole() == User.ROLE.TEACHER || user.getRole() == User.ROLE.ADMIN){
                    preparedStatement.setInt(10, parent_id); //
                }
                else{
                    //todo:if student will be added - use teacher_id from html-from (need to add it) or use NULL
                    preparedStatement.setInt(10, parent_id);
                }

                LogHelper.writeMessage("---class UserDAOImpl : preparedStatement prepared.");

                if(!preparedStatement.execute()){
                    updateCount = preparedStatement.getUpdateCount();
                    LogHelper.writeMessage("class SchoolDAOImpl, method addUser() : added records to Users table" + updateCount);
                    LOGGER.warn("new user record was inserted into table USERS with lastName {}", user.getLastName());
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
                    //while can be replaced by if as LOGIN is UNIQUE.
                    while(resultSet.next()){
                        String hashedPassword = resultSet.getString("PASSWORD");
                        boolean equals = false;
                        if(hashedPassword != null){
                            equals = BCrypt.checkpw(password, hashedPassword);
                        }

                        if(equals){
                            //LASTNAME, FIRSTNAME, MIDDLENAME, EMAIL, LOGIN, PASSWORD, ROLE_ID, BRANCH_ID
                            //take user with this password and return this user;
                            int id = resultSet.getInt("ID");
                            LogHelper.writeMessage("class UserDAOImpl. getUserByLoginPassword. id = " + id);
                            String lastName = resultSet.getString("LASTNAME");
                            String firstName = resultSet.getString("FIRSTNAME");
                            String middleName = resultSet.getString("MIDDLENAME");
                            String email = resultSet.getString("EMAIL");
                            //login = // already defined as method parameter
                            //password = // already defined as method parameter
                            int role_id = resultSet.getInt("ROLE_ID");
                            int school_id = resultSet.getInt("SCHOOL_ID");
                            int branch_id = resultSet.getInt("BRANCH_ID");
                            User.ROLE role = User.ROLE.getRoleById(role_id);
                            UserBuilder userBuilder = new UserBuilder(role)
                                    .addId(id)
                                    .addLastName(lastName)
                                    .addFirstName(firstName)
                                    .addMiddleName(middleName)
                                    .addEmail(email)
                                    .addLogin(login)
                                    .addPassword(password)
                                    .addSchool(school_id)
                                    .addBranch(branch_id);

                            //if ROLE = TEACER - use method AddGroupsToTeacher.
                            //Groups should be taken from BD using GroupsDAOImpl class - getGroupsByTeacherId().
                            if(role == User.ROLE.TEACHER){
                                //int id = resultSet.getInt("ID");
                                GroupDAO groupDAO = new GroupDAOImpl();
                                List<Integer> groups = groupDAO.getGroupIdsByTeacherId(id);
                                userBuilder.addGroupsToTeacher(groups);
                            }

                            //add group_id to user:
                            //    private int levelId;
                            //    private int groupId;
                            //    private List<Integer> testResults; //list of ids of testResults
                            if(role == User.ROLE.STUDENT){
                                int groupId = resultSet.getInt("GROUP_ID");
                                userBuilder.addGroupIdToStudent(groupId);
                            }

                            return userBuilder.buildUser();
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
    public List<User> getUsersByRole(User.ROLE role, int school_id) {
        if(Objects.isNull(role) || school_id <= 0){
            return null;
        }
        if(connection == null){
            connection = PoolConnector.getConnection();
        }
        List<User> users = new ArrayList<>();
        if(connection != null) {
            try {
                //получить всех учителей (id, lastName, FirstName) по id школы
                preparedStatement = connection.prepareStatement("SELECT ID, LASTNAME, FIRSTNAME FROM USERS WHERE SCHOOL_ID = ? AND ROLE_ID = ?");
                preparedStatement.setInt(1, school_id);
                preparedStatement.setInt(2, role.getId());
                ResultSet resultSet = preparedStatement.executeQuery();
                if(!resultSet.wasNull()){
                    //I'm using 'while' because can have several users with same login.
                    while(resultSet.next()){
                        int id = resultSet.getInt("ID");
                        String lastName = resultSet.getString("LASTNAME");
                        String firstName = resultSet.getString("FIRSTNAME");
                        User user = new UserBuilder(role)
                                .addId(id)
                                .addLastName(lastName)
                                .addFirstName(firstName)
                                .buildUser();
                        users.add(user);
                    }
                }else{
                    LogHelper.writeMessage("class UserDAOImpl, method getUsersByRole() : resultSet is null");
                    LOGGER.error("resultSet is null");
                }
            } catch (SQLException throwables) {
                LogHelper.writeMessage("class UserDAOImpl, method getUsersByRole() : SQLException");
                throwables.printStackTrace();
                LOGGER.error("SQLException while SELECT query - getUsersByRole");
            }
        }else{
            LogHelper.writeMessage("class UserDAOImpl, method getUsersByRole() : connection is null");
            LOGGER.error("connection is null");
        }
        return users;
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
                    preparedStatement.close();
                    PoolConnector.closeConnection(connection);
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
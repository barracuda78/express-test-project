package ru.eforward.express_testing.dao;

import ru.eforward.express_testing.daoInterfaces.UserDAO;
import ru.eforward.express_testing.dbConnection.PoolConnector;
import ru.eforward.express_testing.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAOFakeDataBaseImpl implements UserDAO {

    private final List<User> store = new ArrayList<>();
    //private Connection connection = PoolConnector.getConnection();

    public User getById(int id) {

        User result = null;
        //result.setId(-1);

        for (User user : store) {
            if (user.getId() == id) {
                result = user;
            }
        }

        return result;
    }

    public User getUserByLoginPassword(final String login, final String password) {

        User result = null;
        //result.setId(-1);

        for (User user : store) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                result = user;
            }
        }

        return result;
    }

    //just a stub - do not use it
    @Override
    public boolean userPresents(int userId) {
        return false;
    }

    @Override
    public boolean addUser(final User user) {

        for (User u : store) {
            if (u.getLogin().equals(user.getLogin()) && u.getPassword().equals(user.getPassword())) {
                return false;
            }
        }

//        try {
//            String SQLSTRING = "INSERT INTO FLOWERS (ID, FLOWERNAME) VALUES (4, 'flower4')";
//            Statement statement = connection.createStatement();
//            boolean gotResultSet = statement.execute(SQLSTRING);
//            if(!gotResultSet){
//                int updateCount = statement.getUpdateCount();
//                System.out.println("---------updateCount = " + updateCount + " -------------");
//            }
//
//            statement.close();
//            //connection.close();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

        return store.add(user);
    }

    public User.ROLE getRoleByLoginPassword(final String login, final String password) {
        User.ROLE result = User.ROLE.UNKNOWN;

        for (User user : store) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                result = user.getRole();
            }
        }

        return result;
    }

    public boolean userIsPresent(final String login, final String password) {

        boolean result = false;

        for (User user : store) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                result = true;
                break;
            }
        }

        return result;
    }
}

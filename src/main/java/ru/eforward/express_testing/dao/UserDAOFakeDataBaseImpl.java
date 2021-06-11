package ru.eforward.express_testing.dao;
import ru.eforward.express_testing.daoInterfaces.UserDAO;
import ru.eforward.express_testing.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAOFakeDataBaseImpl implements UserDAO {

    private final List<User> store = new ArrayList<>(); //this is a fake database

    public User getById(int id) {

        User result = null;

        for (User user : store) {
            if (user.getId() == id) {
                result = user;
            }
        }

        return result;
    }

    @Override
    public User getUserByLoginPassword(final String login, final String password) {

        User result = null;

        for (User user : store) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                result = user;
            }
        }

        return result;
    }

    @Override
    public boolean userIsPresent(int userId) {
        return false;
    }

    @Override
    public boolean addUser(final User user) {

        for (User u : store) {
            if (u.getLogin().equals(user.getLogin()) && u.getPassword().equals(user.getPassword())) {
                return false;
            }
        }

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

    @Override
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

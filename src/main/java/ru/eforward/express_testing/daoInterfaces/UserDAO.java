package ru.eforward.express_testing.daoInterfaces;

import ru.eforward.express_testing.model.Teacher;
import ru.eforward.express_testing.model.User;

import java.util.List;

public interface UserDAO {
    boolean userIsPresent(final int userId);
    boolean userIsPresent(String login, String password);
    boolean addUser(final User user, final int parent);
    User getUserByLoginPassword(final String login, final String password);
    User.ROLE getRoleByLoginPassword(String login, String password);

    List<User> getUsersByRole(User.ROLE role, int school_id);
}

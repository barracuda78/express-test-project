package ru.eforward.express_testing.daoInterfaces;

import ru.eforward.express_testing.model.User;

public interface UserDAO {
    boolean userPresents(final int userId);
    boolean addUser(final User user);
    User getUserByLoginPassword(final String login, final String password);
}

package ru.eforward.express_testing.daoInterfaces;

import ru.eforward.express_testing.model.User;

public interface UserDAO {
    boolean addUser(final User user);
}

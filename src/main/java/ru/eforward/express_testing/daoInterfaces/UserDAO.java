package ru.eforward.express_testing.daoInterfaces;

import ru.eforward.express_testing.model.User;

public interface UserDAO {
    boolean addUserToDAO(final User user);
}

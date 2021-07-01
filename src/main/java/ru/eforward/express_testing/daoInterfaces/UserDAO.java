package ru.eforward.express_testing.daoInterfaces;

import ru.eforward.express_testing.model.Teacher;
import ru.eforward.express_testing.model.User;

import java.util.List;

public interface UserDAO {
    /**
     * Finds out if Storage contains such user with a given id.
     * @param userId - id of a user.
     * @return  true if such user exists in DB, otherwise false.
     */
    boolean userIsPresent(final int userId);

    /**
     * Finds out if Storage contains such user with a given login and password.
     * @param login - login of a user.
     * @param password - password id of a user.
     * @return  true if such user exists in Storage, otherwise false.
     */
    boolean userIsPresent(String login, String password);

    /**
     * Adds a user to Storage.
     * @param user - user to be added to Storage.
     * @param parent - id of a user, who adds user to storage, or a teacherId - if attached for student.
     * @return  true if user was added, otherwise false.
     */
    boolean addUser(final User user, final int parent);

    /**
     * Gets from Storage User with given login and password.
     * @param login - login of a user,
     * @param password - password of a user,
     * @return  User with given login and password.
     */
    User getUserByLoginPassword(final String login, final String password);

    /**
     * Gets Role of User from Storage with given login and password.
     * @param login - login of a user,
     * @param password - password of a user,
     * @return  Role of User from Storage with given login and password.
     */
    User.ROLE getRoleByLoginPassword(String login, String password);

    /**
     * Gets Role of User from Storage with given login and password.
     * @param role - login of a user,
     * @param school_id - password of a user,
     * @return  Role of User from Storage with given login and password.
     */
    List<User> getUsersByRole(User.ROLE role, int school_id);
}

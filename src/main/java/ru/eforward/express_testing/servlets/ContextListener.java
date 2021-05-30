package ru.eforward.express_testing.servlets;


import ru.eforward.express_testing.dao.UserDAO;
import ru.eforward.express_testing.model.User;
import ru.eforward.express_testing.model.UserBuilder;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.atomic.AtomicReference;

import static ru.eforward.express_testing.model.User.ROLE.ADMIN;
import static ru.eforward.express_testing.model.User.ROLE.USER;

/**
 * ContextListener put user dao to servlet context.
 */
@WebListener
public class ContextListener implements ServletContextListener {
    /**
     * Fake database connector.
     */
    private AtomicReference<UserDAO> dao;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        dao = new AtomicReference<>(new UserDAO());
        UserBuilder ub = new UserBuilder();
        User user01 = ub
                .addId(1)
                .addLastName("Ruzaev")
                .addFirstName("Andrey")
                .addMiddleName("Vladimirovich")
                .addLogin("barracuda")
                .addPassword("111")
                .addRole(ADMIN)
                .buildUser();

        dao.get().add(user01);
        dao.get().add(new User(2, "Egor", "1", USER));

        final ServletContext servletContext = servletContextEvent.getServletContext();

        servletContext.setAttribute("dao", dao);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        dao = null;
    }
}
package ru.eforward.express_testing.servlets.filter;

import ru.eforward.express_testing.dao.UserDAOFakeDataBaseImpl;
import ru.eforward.express_testing.dao.UserDAOImpl;
import ru.eforward.express_testing.daoInterfaces.UserDAO;
import ru.eforward.express_testing.model.Admin;
import ru.eforward.express_testing.model.Student;
import ru.eforward.express_testing.model.Teacher;
import ru.eforward.express_testing.model.User;
import ru.eforward.express_testing.utils.LogHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.nonNull;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        final String login = req.getParameter("login");
        final String password = req.getParameter("password");

        @SuppressWarnings("unchecked")
        //final AtomicReference<UserDAOFakeDataBaseImpl> dao = (AtomicReference<UserDAOFakeDataBaseImpl>) req.getServletContext().getAttribute("dao");
        UserDAO userDAO = new UserDAOImpl();

        final HttpSession session = req.getSession();

        //Logged user. - если пользователь уже залогинен:
        if (nonNull(session) &&  nonNull(session.getAttribute("login")) && nonNull(session.getAttribute("password"))) {

            final User.ROLE role = (User.ROLE) session.getAttribute("role");

            if(role == User.ROLE.STUDENT){
                //Student user = (Student) dao.get().getUserByLoginPassword((String) session.getAttribute("login"), (String) session.getAttribute("password"));
                Student user = (Student)userDAO.getUserByLoginPassword(
                        (String) session.getAttribute("login"),
                        (String) session.getAttribute("password"));
                session.setAttribute("user", user);
            }else if(role == User.ROLE.ADMIN){
                //Admin user = (Admin)dao.get().getUserByLoginPassword((String) session.getAttribute("login"), (String) session.getAttribute("password"));
                Admin user = (Admin)userDAO.getUserByLoginPassword((String) session.getAttribute("login"), (String) session.getAttribute("password"));
                session.setAttribute("user", user);
            }else if(role == User.ROLE.TEACHER){
                //Teacher user = (Teacher) dao.get().getUserByLoginPassword((String) session.getAttribute("login"), (String) session.getAttribute("password"));
                Teacher user = (Teacher)userDAO.getUserByLoginPassword((String) session.getAttribute("login"), (String) session.getAttribute("password"));
                session.setAttribute("user", user);
            }

            moveToMenu(req, res, role);

        //если пользователь еще не залогинен, но существует в БД такие логин-пароль:
        //} else if (dao.get().userIsPresent(login, password)) {
        } else if (userDAO.userIsPresent(login, password)) {
            //final User.ROLE role = dao.get().getRoleByLoginPassword(login, password);
            final User.ROLE role = userDAO.getRoleByLoginPassword(login, password);

            req.getSession().setAttribute("password", password);
            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute("role", role);
            //User user = dao.get().getUserByLoginPassword((String) session.getAttribute("login"), (String) session.getAttribute("password"));
            User user = userDAO.getUserByLoginPassword((String) session.getAttribute("login"), (String) session.getAttribute("password"));

            session.setAttribute("user", user);

            moveToMenu(req, res, role);

        //если не залогинен и такой пары логин-пароль в БД не существует:
        } else {
            moveToMenu(req, res, User.ROLE.UNKNOWN);
        }
    }

    /**
     * Перемещает пользователя на страницу jsp с соответствующим контентом в зависимости от роли пользователя.
     */
    private void moveToMenu(final HttpServletRequest req, final HttpServletResponse res, final User.ROLE role) throws ServletException, IOException {

        if (role.equals(User.ROLE.ADMIN)) {
            req.getRequestDispatcher("WEB-INF/view/admin_menu.jsp").forward(req, res);

        } else if (role.equals(User.ROLE.TEACHER)) {
            LogHelper.writeMessage("---class AuthFilter method moveToMenu() : user is going to be moved to teacher_menu.jsp---");
            req.getRequestDispatcher("/WEB-INF/view/teacher_menu.jsp").forward(req, res);

        } else if (role.equals(User.ROLE.STUDENT)) {
            req.getRequestDispatcher("/WEB-INF/view/student_menu.jsp").forward(req, res);
        }else {
            //неизвестный пользователь опять перенаправляется на страницу логина:
            req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, res);
        }
    }

    @Override
    public void destroy() {
    }
}

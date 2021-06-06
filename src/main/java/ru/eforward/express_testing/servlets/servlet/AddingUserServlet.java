package ru.eforward.express_testing.servlets.servlet;
import org.mindrot.jbcrypt.BCrypt;

import ru.eforward.express_testing.dao.UserDAOImpl;
import ru.eforward.express_testing.daoInterfaces.UserDAO;
import ru.eforward.express_testing.model.*;
import ru.eforward.express_testing.model.school.Branch;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AddingUserServlet", urlPatterns = {"/AddingUserServlet"})
public class AddingUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String lastName = request.getParameter("lastName");
        String firstName = request.getParameter("firstName");
        String middleName = request.getParameter("middleName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role_id = request.getParameter("roles");
        String branch_id = request.getParameter("branches");
        String userCreated = "notCreated";
        String allFieldsFilled = "notAll";

        if(lastName != null && firstName != null && email != null && password != null && role_id != null && branch_id != null){
            UserDAO userDAO = new UserDAOImpl();
            //User user = "1".equals(role_id) ? new Admin() : ("2".equals(role_id) ? new Teacher() : new Student());
            User.ROLE role = "1".equals(role_id) ? User.ROLE.ADMIN : ("2".equals(role_id) ? User.ROLE.TEACHER : User.ROLE.STUDENT);
            UserBuilder userBuilder = new UserBuilder(role);
            List<Branch> branches = new ArrayList<>();
            Branch branch = new Branch();
            branch.setId(Integer.parseInt(branch_id));
            branch.setName(Integer.parseInt(branch_id) == 1 ? "EF - Горьковская" : "EF - Парк Победы ");
            branches.add(branch);
            //------------------------------------------------------------------здесь лезть в базу и искать филиал по id!!!
            User user = userBuilder
                    .addLastName(lastName)
                    .addFirstName(firstName)
                    .addMiddleName(middleName)
                    .addEmail(email)
                    .addPassword(BCrypt.hashpw(password, BCrypt.gensalt()))
                    .addBranches(branches)
                    .buildUser();

            userDAO.addUser(user);
            userCreated = "created";
            allFieldsFilled = "all";
        }else{
            //send to create_user.jsp that all fields should be filled.
            request.setAttribute("userCreated", userCreated);
            request.setAttribute("allFieldsFilled", allFieldsFilled);
            request.getRequestDispatcher("createUser").forward(request, response);
        }

        request.setAttribute("userCreated", userCreated);
        request.setAttribute("allFieldsFilled", allFieldsFilled);
        request.getRequestDispatcher("adminMenu").forward(request, response);

        try(PrintWriter out = response.getWriter()){
            out.println("<p>Мы в сервлете AddingUserServlet</p>");
            out.println("<p>lastName = " + lastName + "</p>");
            out.println("<p>firstName = " + firstName + "</p>");
            out.println("<p>middleName = " + middleName + "</p>");
            out.println("<p>email = " + email + "</p>");
            out.println("<p>password = " + password + "</p>");
            out.println("<p>role_id = " + role_id + "</p>");
            out.println("<p>branch_id = " + branch_id + "</p>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

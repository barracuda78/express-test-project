package ru.eforward.express_testing.servlets.servlet;

import ru.eforward.express_testing.dao.SchoolDAOImpl;
import ru.eforward.express_testing.dao.TestDAOImpl;
import ru.eforward.express_testing.daoInterfaces.SchoolDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@WebServlet(name = "UpdatingServlet", urlPatterns = {"/UpdatingServlet"})
public class UpdatingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String schoolName = request.getParameter("schoolName");
        String button = request.getParameter("createSchool");
        String schoolAdded = "schoolNotAdded";

        if(schoolName != null && button != null){
            SchoolDAO schoolDAO = new SchoolDAOImpl();
            if(schoolDAO.addSchoolByName(schoolName)){
                schoolAdded = "schoolAdded";
            }
        }

        request.setAttribute("schoolAdded", schoolAdded);
        request.getRequestDispatcher("adminMenu").forward(request, response);
    }
}

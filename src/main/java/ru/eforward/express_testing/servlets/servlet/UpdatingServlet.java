package ru.eforward.express_testing.servlets.servlet;

import ru.eforward.express_testing.dao.BranchDAOImpl;
import ru.eforward.express_testing.dao.SchoolDAOImpl;
import ru.eforward.express_testing.daoInterfaces.BranchDAO;
import ru.eforward.express_testing.daoInterfaces.SchoolDAO;
import ru.eforward.express_testing.model.User;
import ru.eforward.express_testing.utils.LogHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "UpdatingServlet", urlPatterns = {"/UpdatingServlet"})
public class UpdatingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String branchName = request.getParameter("branchName");
        User user = (User)request.getSession().getAttribute("user");
        boolean added = false;
        if(Objects.nonNull(branchName) && Objects.nonNull(user)){
            BranchDAO branchDAO = new BranchDAOImpl();
            added = branchDAO.addBranchByName(branchName, user.getSchool());
            LogHelper.writeMessage("UpdatingServlet = if");
        }else{
            LogHelper.writeMessage("UpdatingServlet = else");
        }
        String branchAdded = added ? "branchAdded": "notAdded";
        request.setAttribute("branchAdded", branchAdded);
        request.getRequestDispatcher("adminMenu").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String schoolName = request.getParameter("schoolName");
        String button = request.getParameter("createSchool");
        String schoolAdded = "schoolNotAdded";

        if(schoolName != null && button != null){
            SchoolDAO schoolDAO = new SchoolDAOImpl();
            //first I have to get current school id from session^
            int currentSchoolId = ((User)request.getSession().getAttribute("user")).getSchool();
            if(schoolDAO.changeSchoolName(schoolName, currentSchoolId)){
                schoolAdded = "schoolAdded";
            }
        }

        request.setAttribute("schoolAdded", schoolAdded);
        request.getRequestDispatcher("adminMenu").forward(request, response);
    }
}

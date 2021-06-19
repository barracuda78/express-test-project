package ru.eforward.express_testing.servlets.servlet;

import ru.eforward.express_testing.dao.TestResultDAOImpl;
import ru.eforward.express_testing.daoInterfaces.TestResultDAO;
import ru.eforward.express_testing.testingProcess.TestResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StatisticServlet", urlPatterns = {"/StatisticServlet"})
public class StatisticServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    //- первый вид отчета представляет собой запись,
    // состоящую из ФИО студента и общего балла за прохождение заданного теста данным студентом.

    //- второй вид отчета представляет собой запись,
    // состоящую из названия группы и среднего балла за прохождение студентами данной группы данного тестирования.
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        //<td>id группы: <input type="text" name="groupId" size="12"/></td>
        String groupIdString = request.getParameter("groupId");
        if(groupIdString != null){
            try {
                int groupId = Integer.parseInt(groupIdString);
                TestResultDAO testResultDAO = new TestResultDAOImpl();
                List<TestResult> testResults = testResultDAO.getTestResultsByGroupId(groupId);
                request.setAttribute("testResults", testResults);
            }catch (NumberFormatException nfe){
                request.setAttribute("badId", "badId");
            }
        }
        request.getRequestDispatcher("testResults").forward(request, response);
    }
}

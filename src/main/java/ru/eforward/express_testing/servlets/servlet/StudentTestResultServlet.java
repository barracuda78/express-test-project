package ru.eforward.express_testing.servlets.servlet;

import ru.eforward.express_testing.dao.TestResultDAOImpl;
import ru.eforward.express_testing.daoInterfaces.TestResultDAO;
import ru.eforward.express_testing.model.Student;
import ru.eforward.express_testing.testingProcess.TestResult;
import ru.eforward.express_testing.utils.LogHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StudentTestResultServlet" , urlPatterns = {"/StudentTestResultServlet"})
public class StudentTestResultServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession httpSession = request.getSession();
        Student student = (Student)httpSession.getAttribute("user");
        int studentId = student.getId();

        TestResultDAO testResultDAO = new TestResultDAOImpl();
        List<TestResult> testResults = testResultDAO.getTestResultByStudentId(studentId);

        LogHelper.writeMessage("StudentTestResultServlet");
        LogHelper.writeMessage("testResults = " + testResults);
        request.setAttribute("testResults", testResults);

        request.getRequestDispatcher("/WEB-INF/view/testResults.jsp").forward(request, response);
    }
}

package ru.eforward.express_testing.servlets.servlet;

import ru.eforward.express_testing.dao.TestResultDAOImpl;
import ru.eforward.express_testing.daoInterfaces.TestResultDAO;
import ru.eforward.express_testing.model.Student;
import ru.eforward.express_testing.model.Teacher;
import ru.eforward.express_testing.testingProcess.StatisticType;
import ru.eforward.express_testing.testingProcess.TestResult;
import ru.eforward.express_testing.testingProcess.statsRenderers.StatsRenderer;
import ru.eforward.express_testing.utils.LogHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
            request.getRequestDispatcher("testResults").forward(request, response);
        }

        //Stats requested by Admin:
        String showAdminStats = request.getParameter("showAdminStats");
        if(showAdminStats != null){
            StatsRenderer statsRenderer = StatisticType.getStatsRenderer(StatisticType.ADMIN_GROUP_AVERAGE);
            Object stub = new Object();
            String stats = statsRenderer.render(stub);
            request.setAttribute("stats", stats);
            request.getRequestDispatcher("adminMenu").forward(request, response);
        }

        //Stats requested by TEACHER:
        String showTeacherAllGroupStats = request.getParameter("showTeacherAllGroupStats");
        if(showTeacherAllGroupStats != null){
            StatsRenderer statsRenderer = StatisticType.getStatsRenderer(StatisticType.TEACHER_GROUP);
            HttpSession session = request.getSession();
            Teacher teacher = (Teacher) session.getAttribute("user");
            int teacherId = teacher.getId();
            String stats = statsRenderer.render(teacherId);
            request.setAttribute("stats", stats);
            request.getRequestDispatcher("teacherMenu").forward(request, response);
        }

        //Stats requested by STUDENT:
        String showStudentStats = request.getParameter("showStudentStats");
        if(showStudentStats != null){
            StatsRenderer statsRenderer = StatisticType.getStatsRenderer(StatisticType.STUDENT_SINGLE);
            HttpSession session = request.getSession();
            Student student = (Student) session.getAttribute("user");
            int studentId = student.getId();
            String stats = statsRenderer.render(studentId);
            request.setAttribute("stats", stats);
            request.getRequestDispatcher("studentMenu").forward(request, response);
        }
    }
}

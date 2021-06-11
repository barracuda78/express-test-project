package ru.eforward.express_testing.servlets.servlet;

import ru.eforward.express_testing.testingProcess.TestingUnit;
import ru.eforward.express_testing.utils.LogHelper;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@WebServlet(name = "TestingServlet", urlPatterns = {"/TestingServlet"})
public class TestingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        //                <td>id группы<input type="text" name="groupId" size="12"/></td>
        //                <td>id урока<input type="text" name="lessonId" size="12"/></td>
        String lessonIdString = request.getParameter("lessonId");
        String groupIdString = request.getParameter("groupId");
        int lessonId = -1;
        int groupId = -1;
        try{
            lessonId = Integer.parseInt(lessonIdString);
            groupId = Integer.parseInt(groupIdString);
        }catch(NumberFormatException e){
            LogHelper.writeMessage("---class TestingServlet: doGet() - NumberFormatException" + e.getMessage());
            request.setAttribute("wrongId", "wrongId");
        }
        String runTestButton = request.getParameter("runTestButton");
        List<TestingUnit> testingUnits = null;
        TestingUnit testingUnit = null;
        LogHelper.writeMessage("---class TestingServlet: doGet() - before if statement");
        if(runTestButton != null && groupId >= 0 && lessonId >= 0){
            LogHelper.writeMessage("---class TestingServlet: doGet() - in if statement");
            testingUnit = new TestingUnit(lessonId, groupId);

            ServletContext servletContext = request.getServletContext();
            @SuppressWarnings("unchecked")
            AtomicReference<List<TestingUnit>> testingUnitsListAtomicReference = (AtomicReference<List<TestingUnit>>)servletContext.getAttribute("testingUnitsListAtomicReference");
            if(testingUnitsListAtomicReference == null){
                //create new List. add unit to it. Wrap it with AtomicReference.
                testingUnits = new ArrayList<>();
                testingUnits.add(testingUnit);
                testingUnitsListAtomicReference = new AtomicReference<>(testingUnits);
            }else{
                //get List from AtomicReference. Add unit to List.
                testingUnitsListAtomicReference.get().add(testingUnit);
            }
            servletContext.setAttribute("testingUnitsListAtomicReference", testingUnitsListAtomicReference);
            request.setAttribute("testingStarted", "ok");
            LogHelper.writeMessage("---class TestingServlet: doGet() testingStarted - OK---");
            LogHelper.writeMessage("---class TestingServlet: doGet()Текст теста = " + testingUnit.getNextTest());
        } else {
            LogHelper.writeMessage("---class TestingServlet: doGet() - in else statement");
            request.setAttribute("testingStarted", "bad");
        }
        //go back to teachers main menu with corresponding attributes:
        request.getRequestDispatcher("teacherMenu").forward(request, response);

//this code works with fake database:
//        @SuppressWarnings("unchecked")
//        final AtomicReference<TestDAOFakeDatabaseImpl> testDao = (AtomicReference<TestDAOFakeDatabaseImpl>) request.getServletContext().getAttribute("test");
//        String text1 = request.getParameter("testId");
//        int testId = -1;
//        try {
//            testId = Integer.parseInt(text1);
//        } catch (NumberFormatException e) {
//            request.setAttribute("wrongTestId", "wrongId");
//        }
//        String runTestButton = request.getParameter("runTestButton");
//        Test test = null;
//
//        if (text1 != null && runTestButton != null && testId >= 0) {
//            test = testDao.get().getTestById(testId);
//            test.setActive(true);
//            request.setAttribute("testingStarted", "ok");
//        } else {
//            request.setAttribute("testingStarted", "bad");
//        }
//        request.getRequestDispatcher("teacherMenu").forward(request, response); // ПРОВЕРИТЬ АДРЕС! ВОЗМОЖНО ВНЕСТИ В WEB.XML!

    }
}

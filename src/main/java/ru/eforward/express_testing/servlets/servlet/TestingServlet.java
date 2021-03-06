package ru.eforward.express_testing.servlets.servlet;

import ru.eforward.express_testing.testingProcess.TestingUnit;

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

/**
 *This servlet is used for user with Teacher role for starting new Testing.
 */

@WebServlet(name = "TestingServlet", urlPatterns = {"/TestingServlet"})
public class TestingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String lessonIdString = request.getParameter("lessonId");
        String groupIdString = request.getParameter("groupId");
        String durationString = request.getParameter("duration");
        durationString = durationString.replaceAll("[,:;'\"-]", ".");

        int lessonId = -1;
        int groupId = -1;
        double duration = -1.0d;
        try{
            lessonId = Integer.parseInt(lessonIdString);
            groupId = Integer.parseInt(groupIdString);
            duration = Double.parseDouble(durationString);
        }catch(NumberFormatException e){
            request.setAttribute("wrongId", "wrongId");
        }
        String runTestButton = request.getParameter("runTestButton");
        List<TestingUnit> testingUnits = null;
        TestingUnit testingUnit = null;
        if(runTestButton != null && groupId >= 0 && lessonId >= 0){
            testingUnit = new TestingUnit(lessonId, groupId, duration);

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

        } else {
            request.setAttribute("testingStarted", "bad");
        }
        //go back to teachers main menu with corresponding attributes:
        request.getRequestDispatcher("teacherMenu").forward(request, response);

    }
}

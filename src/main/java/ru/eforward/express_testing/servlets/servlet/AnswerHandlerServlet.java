package ru.eforward.express_testing.servlets.servlet;

import ru.eforward.express_testing.model.Student;
import ru.eforward.express_testing.testingProcess.TestEvaluate;
import ru.eforward.express_testing.testingProcess.TestResult;
import ru.eforward.express_testing.testingProcess.TestingUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

//todo: add this servlet to web.xml or check if annotation with urlPattern works!
@WebServlet(name = "AnswerHandlerServlet", urlPatterns = {"/AnswerHandlerServlet"})
public class AnswerHandlerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        //CONCEPT:
        //this Servlet should know how many (n) questions there is in this test (in this TestingUnit).
        //student will 'n'-times forwarded to testing.jsp ant will get here, AnswerHandlerServlet again.
        //When the n count will be equal to number of questions in this TestingUnit,
        //TestValidating class will help to assert how good passed the student this TestingUnit, and
        //Student will be forwarded to students_menu.jsp with a message "Testing completed! Your score = xx.x of xx."
        //So this Servlet should get from attributes the TestingUnit entity;
        //need to get studentsId somehow here to pass it to database
        //write this answer to DatBase (maybe new table TestResults)

        //session.setAttribute("studentsTestingUnit", testingUnit);
        HttpSession httpSession = request.getSession();
        TestingUnit testingUnit = (TestingUnit)httpSession.getAttribute("studentsTestingUnit");
        Student student = (Student)httpSession.getAttribute("user");
        TestEvaluate testEvaluate = new TestEvaluate();
        //sb.append("<input type=\"hidden\" name=\"type\" value=\"MULTICHOICE\">");
        String questionType = request.getParameter("type");
    //for MultiChoice question type:
        String choice = request.getParameter("choice1"); //this is the answer student gave
        if(Objects.nonNull(questionType)
                && ("MULTICHOICE".equals(questionType))
                && Objects.nonNull(choice)
                && Objects.nonNull(testingUnit)
                && Objects.nonNull(student)){
            //create new TestResult entity and preset it:
            TestResult testResult = new TestResult();
            testResult.setStudentsId(student.getId());
            testResult.setSchoolId(student.getSchool());
            testResult.setLessonId(testingUnit.getLessonId());

            //todo: decide what parameters should be put to this method to evaluate every question. TestingUnit? Question? Should i get question from form 'hidden'?

            int score = testEvaluate.evaluateMultiChoice(choice);


            testResult.getMap().put("question!!!!!!!!", choice);

            httpSession.setAttribute("testResult", testResult);

            try(PrintWriter out = response.getWriter()){
                out.println("<h1>" + choice + "</h1>");
                out.println("<h1>" + score + "</h1>");
                out.println("<h1>" + questionType + "</h1>");
            }
        }
    }
}

package ru.eforward.express_testing.servlets.servlet;

import ru.eforward.express_testing.dao.TestResultDAOImpl;
import ru.eforward.express_testing.daoInterfaces.TestResultDAO;
import ru.eforward.express_testing.model.Student;
import ru.eforward.express_testing.testingProcess.*;
import ru.eforward.express_testing.testingProcess.evaluatingHandlers.ComplianceEvaluator;
import ru.eforward.express_testing.testingProcess.questionHandlers.ComplianceHandler;
import ru.eforward.express_testing.utils.LogHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

        HttpSession httpSession = request.getSession();
        TestingUnit testingUnit = (TestingUnit)httpSession.getAttribute("studentsTestingUnit");
        Student student = (Student)httpSession.getAttribute("user");
        TestResult testResult =(TestResult) httpSession.getAttribute("testResult");

        Stopper stopper = (Stopper)httpSession.getAttribute("stopper");
        boolean timeIsOver = false;
        if(Objects.nonNull(stopper)){
            timeIsOver = stopper.shouldBeStopped();
            if(timeIsOver){
                TestingUnit studentsTestingUnit = (TestingUnit)httpSession.getAttribute("studentsTestingUnit");
                if(Objects.nonNull(studentsTestingUnit)){
                    request.setAttribute("score", new Integer(testResult.getTotalScore()));
                    httpSession.setAttribute("timeIsOver", "timeIsOver");
                    request.getRequestDispatcher("/WEB-INF/view/student_menu.jsp").forward(request, response);
                }
            }
        }


        //MULTICHOICE AND SHORTANSWER:
        String type = request.getParameter("type");
        LogHelper.writeMessage("AnswerHandlerServlet: type = " + type);
        //sb.append("<input type=\"hidden\" name=\"question\" value=\" " + q + "\">"); //passing the original text of question
        String question = request.getParameter("question");
        String choice = request.getParameter("choice"); //this is the answer student gave
        if(Objects.nonNull(type)
                //&& ("MULTICHOICE".equals(type)) //not only for MultiChoice. Short_Answer also.
                && Objects.nonNull(choice)
                && Objects.nonNull(testingUnit)
                && Objects.nonNull(student)
                && Objects.nonNull(question)){
            LogHelper.writeMessage("student = " + student);

            //if no testResult in session - create new TestResult entity and preset it:
            if(Objects.isNull(testResult)){
                testResult = new TestResult();
                testResult.setStudentId(student.getId());
                LogHelper.writeMessage("class AnswerHandlerServlet student.getId() = " + student.getId());
                testResult.setSchoolId(student.getSchool());
                testResult.setLessonId(testingUnit.getLessonId());
            }
            QuestionType questionType = QuestionType.valueOf(type);

            //evaluate question, add question score to the totalScore:
            Evaluating evaluating = new TestEvaluate();

            int score = 0;
            try{
                score = evaluating.getScore(questionType, question, choice);
            }catch(NumberFormatException nfe){
                LogHelper.writeMessage("AnswerHandlerServlet : NumberFormatException");
                httpSession.setAttribute("nfe", "nfe");
                testingUnit.decrementCursor();
                request.getRequestDispatcher("testing").forward(request, response);
            }catch(IllegalArgumentException iae){
                LogHelper.writeMessage("AnswerHandlerServlet : IllegalArgumentException");
                httpSession.setAttribute("iae", "iae");
                request.getRequestDispatcher("testing").forward(request, response);
            }

            int totalScore = testResult.getTotalScore();
            testResult.setTotalScore(totalScore + score);

            testResult.getMap().put(question, choice + "=$$$=" + score); //just appending score after rare "=$$$=" combination for further statistics

            httpSession.setAttribute("testResult", testResult);

            int cursor = testingUnit.getCursor();
            int numberOfQuestions = testingUnit.getQuestions().size();

            if(numberOfQuestions == cursor ){
                request.setAttribute("finished", "finished");
                request.setAttribute("score", new Integer(testResult.getTotalScore()));
                TestResultDAO testResultDAO = new TestResultDAOImpl();
                testResultDAO.addTestResult(testResult);
                request.getRequestDispatcher("/WEB-INF/view/student_menu.jsp").forward(request, response);
            }else{
                request.setAttribute("score", new Integer(testResult.getTotalScore()));
                request.getRequestDispatcher("testing").forward(request, response);
            }
        }

        //COMPLIANCE:
        LogHelper.writeMessage("testingUnit = " + testingUnit);
        LogHelper.writeMessage("student = " + student);
        LogHelper.writeMessage("question = " + question);

        if(Objects.nonNull(type)
                && ("COMPLIANCE".equals(type))
                && Objects.nonNull(testingUnit)
                && Objects.nonNull(student)
                && Objects.nonNull(question)){

            ComplianceHandler ch = new ComplianceHandler();

            List<String> listOfAnswers = ch.createListOfAnswers(question); //correctAnswers.
            LogHelper.writeMessage("AnswerHandlerServlet: listOfAnswers = " + listOfAnswers);
            String manyAnswers = ch.createStringFromList(listOfAnswers);   //correctAnswers.
            LogHelper.writeMessage("AnswerHandlerServlet: manyAnswers = " + manyAnswers);

            List<String> listOfChoices = new ArrayList<>(); //given by student answers
            //read the parameters using numbers (as much as answers is in the list - do not know how many there are at that stage)
            for(int i = 1; i <= listOfAnswers.size(); i++){
                String studentsAnswer = (request.getParameter(String.valueOf(i))).trim();
                LogHelper.writeMessage("AnswerHandlerServlet: String.valueOf(i) = " + String.valueOf(i));
                LogHelper.writeMessage("AnswerHandlerServlet: studentsAnswer request.getParameter(String.valueOf(i)) = " + studentsAnswer);
                listOfChoices.add(studentsAnswer);
            }
            String manyChoices = ch.createStringFromList(listOfChoices);//given by student answers

            //May several times invoke evaluate() method (as mch as list.size is) and collect results here,
            //but better use own specified method:
            TestEvaluate testEvaluate = new TestEvaluate();
            LogHelper.writeMessage("AnswerHandlerServlet: manyAnswers = \n" + manyAnswers);
            LogHelper.writeMessage("AnswerHandlerServlet: manyChoices = \n" + manyChoices);
            int score = testEvaluate.getScore(QuestionType.valueOf(type), manyAnswers , manyChoices);


            //todo: below code duplicates upper if statement. try to insert original code to upper if.
            //if no testResult in session - create new TestResult entity and preset it:
            testResult =(TestResult) httpSession.getAttribute("testResult");
            if(Objects.isNull(testResult)){
                testResult = new TestResult();
                testResult.setStudentId(student.getId());
                LogHelper.writeMessage("class AnswerHandlerServlet student.getId() = " + student.getId());
                testResult.setSchoolId(student.getSchool());
                testResult.setLessonId(testingUnit.getLessonId());
            }
            int totalScore = testResult.getTotalScore();
            testResult.setTotalScore(totalScore + score);

            testResult.getMap().put(question, choice + "=$$$=" + score); //just appending score after rare "=$$$=" combination for further statistics

            httpSession.setAttribute("testResult", testResult);

            int cursor = testingUnit.getCursor();
            int numberOfQuestions = testingUnit.getQuestions().size();

            if(numberOfQuestions == cursor ){
                request.setAttribute("finished", "finished");
                //request.setAttribute("score", Integer.valueOf(testResult.getTotalScore()));
                request.setAttribute("score", new Integer(testResult.getTotalScore()));
                TestResultDAO testResultDAO = new TestResultDAOImpl();
                testResultDAO.addTestResult(testResult);
                request.getRequestDispatcher("/WEB-INF/view/student_menu.jsp").forward(request, response);
            }else{
                request.getRequestDispatcher("testing").forward(request, response);
            }
        }

        try(PrintWriter out = response.getWriter()){
            out.println("<h1>" + "AnswerHandlerServlet: мы не попали в if..." + "</h1>");
        }
    }
}
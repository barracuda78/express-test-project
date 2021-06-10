package ru.eforward.express_testing.servlets.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "AnswerHandlerServlet")
public class AnswerHandlerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String answer = request.getParameter("answer");
        String answeredButton = request.getParameter("answeredButton");

        if(Objects.nonNull(answer) && Objects.nonNull(answeredButton)){
            //give this answer to TestValidating.
            //write this answer to DatBase (maybe new table TestResults)
            //need to get studentsId somehow here to pass it to database

        }
    }
}

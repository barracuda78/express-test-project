package ru.eforward.express_testing.servlets.servlet;

import ru.eforward.express_testing.dao.TestDAO;
import ru.eforward.express_testing.model.school.Test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicReference;

@WebServlet(name = "TestingServlet", urlPatterns = {"/TestingServlet"})
public class TestingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        @SuppressWarnings("unchecked")
        final AtomicReference<TestDAO> testDao = (AtomicReference<TestDAO>) request.getServletContext().getAttribute("test");


        try(PrintWriter out = response.getWriter()){
            String text1 = request.getParameter("testId");
            int testId = -1;
            try {
                testId = Integer.parseInt(text1);
            }catch(NumberFormatException e){
                request.setAttribute("wrongTestId", "wrongId");
            }
            String button1 = request.getParameter("button1");
            Test test = null;

            if(text1 != null && button1 != null && testId >= 0){
                test = testDao.get().getTestById(testId);
                test.setActive(true);
                request.setAttribute("testingStarted", "ok");
            }else{
                request.setAttribute("testingStarted", "bad");
            }

            request.getRequestDispatcher("teacherMenu").forward(request, response); // ПРОВЕРИТЬ АДРЕС! ВОЗМОЖНО ВНЕСТИ В WEB.XML!
        }
    }
}

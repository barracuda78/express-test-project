package ru.eforward.express_testing.servlets.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AddingUserServlet", urlPatterns = {"/AddingUserServlet"})
public class AddingUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

//                    <td>Фамилия: <input type="text" name="lastName" size="12"/></td>
//                    <td>Имя: <input type="text" name="firstName" size="12"/></td>
//                    <td>Отчество: <input type="text" name="middleName" size="12"/></td>
//                    <td>email: <input type="text" name="email" size="12"/></td>
//                    <td>password: <input type="text" name="email" size="12"/></td>
//                    <td><select name="roles">
//                        <option value="" style="display:none">Выберите роль</option>
//                        <option value="ADMIN">Администратор</option>
//                        <option value="TEACHER">Преподаватель</option>
//                        <option value="STUDENT">Студент</option>
//                    </select></td>
//                    <td><select name="branches">
//                        <option value="" style="display:none">Выберите филиал</option>
//                        <option value="1">EF - Горьковская</option>
//                        <option value="2">EF - Парк Победы</option>
//                    </select></td>
        String lastName = request.getParameter("lastName");
        String firstName = request.getParameter("firstName");
        String middleName = request.getParameter("middleName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role_id = request.getParameter("roles");
        String branch_id = request.getParameter("branches");



        try(PrintWriter out = response.getWriter()){
            out.println("<p>Мы в сервлете AddingUserServlet</p>");
            out.println("<p>lastName = " + lastName + "</p>");
            out.println("<p>firstName = " + firstName + "</p>");
            out.println("<p>middleName = " + middleName + "</p>");
            out.println("<p>email = " + email + "</p>");
            out.println("<p>password = " + password + "</p>");
            out.println("<p>role_id = " + role_id + "</p>");
            out.println("<p>branch_id = " + branch_id + "</p>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

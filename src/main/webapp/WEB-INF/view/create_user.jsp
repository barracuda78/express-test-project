<%--
This page is used by ADMIN to create new users
--%>
<%@ page import="ru.eforward.express_testing.model.Teacher" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ru.eforward.express_testing.daoInterfaces.UserDAO" %>
<%@ page import="ru.eforward.express_testing.dao.UserDAOImpl" %>
<%@ page import="ru.eforward.express_testing.model.User" %>
<%@ page import="ru.eforward.express_testing.model.Admin" %>
<%@ page import="ru.eforward.express_testing.model.school.Branch" %>
<%@ page import="ru.eforward.express_testing.daoInterfaces.BranchDAO" %>
<%@ page import="ru.eforward.express_testing.dao.BranchDAOImpl" %>
<%@ page import="ru.eforward.express_testing.utils.LogHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Новый пользователь</title>
</head>
<body>
    <%
        Admin admin = (Admin)session.getAttribute("user");

        LogHelper.writeMessage("create_user.jsp: admin = " + admin);


        UserDAO userDAO = new UserDAOImpl();
        List<User> users = userDAO.getUsersByRole(User.ROLE.TEACHER, admin.getSchool());

        BranchDAO branchDAO = new BranchDAOImpl();
        List<Branch> branches = branchDAO.getBranchesById(admin.getId());
    %>

    <div id="box">
        <form name="add" action="AddingUserServlet" method="POST">
            <table>         <%--таблица--%>
                <tr>        <%--table raw--%>
                    <p>Укажите данные пользователя:</p>
                    <td>Фамилия: <input type="text" name="lastName" size="12"/></td>
                    <td>Имя: <input type="text" name="firstName" size="12"/></td>
                    <td>Отчество: <input type="text" name="middleName" size="12"/></td>
                    <td>email: <input type="text" name="email" size="12"/></td>
                    <td>password: <input type="text" name="password" size="12"/></td>
                    <td><select name="roles">
                        <option value="" style="display:none">Выберите роль</option>
                        <option value="1">Администратор</option>
                        <option value="2">Преподаватель</option>
                        <option value="3">Студент</option>
                    </select></td>
                    <td><select name="branches">
                        <option value="" style="display:none">Выберите филиал</option>
                        <option value="0">Филиал не выбран</option>
                        <%
                            for(Branch b: branches){
                        %>
                        <%="<option value=\"" + b.getId() + "\">" + b.getName() + "</option>"%>
                        <%
                            }
                        %>
                        <%--option value="1">EF - Горьковская</option--%>
                    </select></td>
                    <td><select name="teacher">
                        <option value="" style="display:none">Выберите куратора</option>
                        <option value="0">Куратор не выбран</option>
                        <%
                        for(User u : users){
                            %>
                                <%="<option value=\"" + u.getId() + "\">" + u.getFirstName() + " " + u.getLastName() + "</option>"%>
                            <%
                        }
                        %>
                        <%--option value="1">Учитель Вася</option--%>
                    </select></td>
                </tr>
                <tr>
                    <td><input type="submit" name="createUser" value="Добавить пользователя"/></td>
                </tr>
            </table>
        </form>
    </div>

    <%
        String allFieldsFilled = (String)request.getAttribute("allFieldsFilled");
        if("notAll".equals(allFieldsFilled)){
            %>
                <%="<p>Пользователь не добавлен. Заполните все поля.</p>"%>
            <%
        }
        String added = (String)request.getAttribute("added");
        if(!"added".equals(added)){
            %>
                <%="<p>Пользователь не добавлен. Проверьте уникальность логина/e-mail</p>"%>
            <%
        }
    %>

<a href="<c:url value='/logout' />">Logout</a>
</body>
</html>
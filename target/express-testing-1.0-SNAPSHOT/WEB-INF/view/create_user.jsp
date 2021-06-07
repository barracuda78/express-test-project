<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Новый пользователь</title>
</head>
<body>
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
                        <option value="1">EF - Горьковская</option>
                        <option value="2">EF - Парк Победы</option>
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
    %>

<a href="<c:url value='/logout' />">Logout</a>
</body>
</html>
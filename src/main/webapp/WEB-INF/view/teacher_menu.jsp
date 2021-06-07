<%@ page import="ru.eforward.express_testing.model.Teacher" %>
<%@ page import="ru.eforward.express_testing.model.school.Group" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.eforward.express_testing.utils.LogHelper" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Преподаватель</title>
</head>
<body>

<h1>Преподаватель:</h1>

<%LogHelper.writeMessage("---teacher_menu.jsp - > user has been moved in here. param.user = " + session.getAttribute("user"));%>

<c:set var="user" scope="page" value="${param.user}"/>
<p><c:out value="${user.lastName}" default="error: last name not found..."/></p>
<p><c:out value="${user.firstName}" default="error: first name not found..."/></p>
<p><c:out value="${user.middleName}" default="error: middle name not found..."/></p>


<p>Список групп:</p>

<ul>
<%
    Teacher teacher = (Teacher)session.getAttribute("user");
    List<Group> groups = teacher.getGroups();
    LogHelper.writeMessage("---teacher_menu.jsp : groups = " + groups);
    if(groups != null){
        for(Group g : groups){
            %>
            <li>
            <%=g%>
            </li>
            <%
        }
    }

    String wrongId = (String)request.getAttribute("wrongTestId");
    String testingStarted = (String)request.getAttribute("testingStarted");
    if("wrongId".equals(wrongId)){
        %>
            <%="<p>Невозможно преобразовать id теста к числу. Укажите номер теста в правильном формате.</p>"%>
        <%
    }

    if("ok".equals(testingStarted)){
        %>
            <%="<p>Тестирование началось!</p>"%>
        <%
    }
%>
</ul>

<div id="box">
    <form name="add" action="TestingServlet" method="GET">
        <table>         <%--таблица--%>
            <tr>        <%--table raw--%>
                <p>Укажите id теста:</p>
                <td><input type="text" name="testId" size="12"/></td>
            </tr>
            <tr>
                <td><input type="submit" name="button1" value="Запустить тест"/></td>
                <td><input type="submit" name="buttin2" value="Остановить тест"/></td>
            </tr>

        </table>
    </form>
</div>

<br/>

<a href="<c:url value="/logout"/>">Logout</a>
</body>
</html>

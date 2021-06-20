<%@ page import="ru.eforward.express_testing.model.User" %>
<%@ page import="ru.eforward.express_testing.model.Student" %>
<%@ page import="java.nio.file.Paths" %>
<%@ page import="java.util.Objects" %>
<%@ page import="ru.eforward.express_testing.utils.LogHelper" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Студент</title>
</head>
<body>

<h1>Студент</h1>

<c:set var="user" scope="page" value="${param.user}"/>
<c:set var="branch" scope="page" value="${user.branch}"/>
<c:set var="testResults" scope="page" value="${user.testResults}"/>



<p><b><c:out value="${user.school}" default="error: school not found..."/></b></p>
<p><b>Филиал:</b></p>
        <li><c:out value="${branch}"/></li>
<p><c:out value="${user.lastName}" default="error: last name not found..."/></p>
<p><c:out value="${user.firstName}" default="error: first name not found..."/></p>
<p><c:out value="${user.middleName}" default="error: middle name not found..."/></p>

<!--request.setAttribute("finished", "finished");-->
<!--request.setAttribute("score", 500); -->

<%
    String timeIsOver = (String)session.getAttribute("timeIsOver");
    String finished = (String)request.getAttribute("finished");
    Integer score = (Integer)request.getAttribute("score");

    LogHelper.writeMessage("students_menu.jsp: timeIsOver = " + timeIsOver);
    LogHelper.writeMessage("students_menu.jsp: finished = " + finished);
    LogHelper.writeMessage("students_menu.jsp: score = " + score);

    if(Objects.nonNull(finished)){
        LogHelper.writeMessage("students_menu.jsp: if Objects.nonNull(finished) ...");
        %>
            <%="Спасибо. Тестирование окончено. Вы набрали: " + score%>
        <%
    }

    if(Objects.nonNull(timeIsOver)){
        LogHelper.writeMessage("students_menu.jsp: if Objects.nonNull(timeIsOver) ...");
        %>
            <%="Спасибо. Время тестирования закончилось. Вы набрали: " + score%>
        <%
    }
%>

<hr/>
<p1>Проверить, доступен ли <a href="testing">тест</a></p1>
<hr/>

<p1>Посмотреть <a href="StudentTestResultServlet">результаты тестов СТАРЫЕ</a></p1>

<div id="box">
    <form name="add" action="StatisticServlet" method="GET">
        <table>         <%--таблица--%>
            <tr>        <%--table raw--%>
                <p>Мои результаты тестов:</p>
            </tr>
            <tr>
                <td><input type="submit" name="showStudentStats" value="Статистика"/></td>
            </tr>

        </table>
    </form>
</div>

<%
    String stats = (String)request.getAttribute("stats");
    if(stats != null){
        %>
            <%=stats%>
        <%
    }
%>

<hr/>
<a href="<c:url value='/logout' />">Выйти</a>
</body>
</html>

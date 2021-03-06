<%--
This page is used by TEACHERS as a main page
--%>
<%@ page import="ru.eforward.express_testing.model.Teacher" %>
<%@ page import="ru.eforward.express_testing.model.school.Group" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.eforward.express_testing.utils.LogHelper" %>
<%@ page import="ru.eforward.express_testing.model.school.Lesson" %>
<%@ page import="ru.eforward.express_testing.daoInterfaces.LessonDAO" %>
<%@ page import="ru.eforward.express_testing.dao.LessonDAOImpl" %>
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


<p>Список уроков:</p>

<ul>
<%
    LessonDAO lessonDAO = new LessonDAOImpl();
    List<Lesson> lessons = lessonDAO.getAllLessons();
    if(lessons != null){
        for(Lesson l : lessons){
            %>
            <li>
                <a href="TestingServlet"><%=l.getLessonName() + ", id = " + l.getId()%></a>
            </li>
            <%
            }
        }
    %>

    <p>Список групп:</p>

    <%
    Teacher teacher = (Teacher)session.getAttribute("user");
    List<Integer> groups = teacher.getGroups();
    if(groups != null){
        for(Integer g : groups){
            %>
            <li>
            <a href="TestingServlet"><%=g%></a>
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
                <p>Укажите параметры тестирования:</p>
                <td>продолжительность теста в минутах: <input type="text" name="duration" size="12"/></td>
                <td>id группы: <input type="text" name="groupId" size="12"/></td>
                <td>id урока: <input type="text" name="lessonId" size="12"/></td>
            </tr>
            <tr>
                <td><input type="submit" name="runTestButton" value="Запустить тест"/></td>
            </tr>

        </table>
    </form>
</div>


<div id="box">
    <form name="add" action="StatisticServlet" method="GET">
        <table>         <%--таблица--%>
            <tr>        <%--table raw--%>
                <p>Для просмотра статистики укажите группу:</p>
                <td>id группы: <input type="text" name="groupId" size="12"/></td>
            </tr>
            <tr>
                <td><input type="submit" name="showStats" value="посмотреть статистику группы"/></td>
            </tr>

        </table>
    </form>
</div>


<div id="box">
    <form name="add" action="StatisticServlet" method="GET">
        <table>         <%--таблица--%>
            <tr>        <%--table raw--%>
                <p>Статистика по моим группам:</p>
            </tr>
            <tr>
                <td><input type="submit" name="showTeacherAllGroupStats" value="статистика по всем моим группам"/></td>
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


<br/>

<a href="<c:url value="/logout"/>">Выйти</a>
</body>
</html>

<%@ page import="ru.eforward.express_testing.model.User" %>
<%@ page import="ru.eforward.express_testing.model.Student" %>
<%@ page import="java.nio.file.Paths" %>
<%@ page import="java.util.Objects" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Студент</title>
</head>
<body>

<h1>Студент</h1>

<c:set var="user" scope="page" value="${param.user}"/>
<c:set var="branches" scope="page" value="${user.branches}"/>
<c:set var="testResults" scope="page" value="${user.testResults}"/>



<p><b><c:out value="${user.school}" default="error: school not found..."/></b></p>
<p><b>Филиалы:</b></p>
<ul>
    <c:forEach var="branch" items="${branches}">
        <li><c:out value="${branch}"/></li>
    </c:forEach>
</ul>

<p><c:out value="${user.lastName}" default="error: last name not found..."/></p>
<p><c:out value="${user.firstName}" default="error: first name not found..."/></p>
<p><c:out value="${user.middleName}" default="error: middle name not found..."/></p>

<!--request.setAttribute("finished", "finished");-->
<!--request.setAttribute("score", 500); -->

<%
    String finished = (String)request.getAttribute("finished");
    Integer score = (Integer)request.getAttribute("score");
    if(Objects.nonNull(finished)){
        %>
            <%="Спасибо. Тестирование окончено. Вы набрали: " + score%>
        <%
    }
%>

<hr/>
<p1>Проверить, доступен ли <a href="testing">тест</a></p1>
<hr/>

<hr/>
<p1>Посмотреть <a href="testResults">результаты тестов</a></p1>
<hr/>

<hr/>
<a href="<c:url value='/logout' />">Выйти</a>
</body>
</html>

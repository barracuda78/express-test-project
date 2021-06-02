<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Результаты тестов</title>
</head>
<body>
    <h1>Результаты тестирования:</h1>
    <c:set var="user" scope="page" value="${param.user}"/>
    <c:set var="branches" scope="page" value="${user.branches}"/>
    <c:set var="testResults" scope="page" value="${user.testResults}"/>

    <p><b>Результаты тестов:</b></p>
    <ul>
        <c:forEach var="testResult" items="${testResults}">
            <li><c:out value="${testResult}"/></li>
        </c:forEach>
    </ul>

</body>
</html>

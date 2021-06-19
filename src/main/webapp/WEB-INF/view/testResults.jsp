<%@ page import="ru.eforward.express_testing.testingProcess.TestResult" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Результаты тестов</title>
</head>
<body>
    <h1>Результаты тестирования:</h1>
    <c:set var="user" scope="page" value="${param.user}"/>
    <c:set var="branch" scope="page" value="${user.branch}"/>
    <%--c:set var="testResults" scope="page" value="${user.testResults}"/--%>


    <p><b>Результаты тестов:</b></p>
    <ul>
        <%--c:forEach var="testResult" items="${testResults}">
            <li><c:out value="${testResult}"/></li>
        </c:forEach--%>
    </ul>

    <%
        //request.setAttribute("testResults", testResults);
        //request.setAttribute("badId", "badId");
        String badId = (String)request.getAttribute("badId");
        if("badId".equals(badId)){
            %>
                <%="Неправильный ввод. Укажите число корректно."%>
            <%
        }

        //request.setAttribute("testResults", testResults);
        @SuppressWarnings("unchecked")
        List<TestResult> testResults = (List<TestResult>)request.getAttribute("testResults");
        if(Objects.nonNull(testResults)){
             for(TestResult tr : testResults){
                 %>
                    <%="Тест " + tr.getId() + ", оценка: " + tr.getTotalScore() + ", студент: " + tr.getStudentId()%>
                 <%
             }
        }
    %>

</body>
</html>

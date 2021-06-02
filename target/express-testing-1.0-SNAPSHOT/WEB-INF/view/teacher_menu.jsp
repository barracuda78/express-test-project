<%@ page import="ru.eforward.express_testing.model.Teacher" %>
<%@ page import="ru.eforward.express_testing.model.school.Group" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Преподаватель</title>

</head>
<body>

<h1>Преподаватель:</h1>
<c:set var="user" scope="page" value="${param.user}"/>
<p><c:out value="${user.lastName}" default="error: last name not found..."/></p>
<p><c:out value="${user.firstName}" default="error: first name not found..."/></p>
<p><c:out value="${user.middleName}" default="error: middle name not found..."/></p>


<p>Список групп:</p>

<%
    Teacher teacher = (Teacher)session.getAttribute("user");
    List<Group> groups = teacher.getGroups();
    System.out.println(teacher.getRole());
    for(Group g : groups){
        %>
        <%=g%>
        <%
    }
%>

<br/>

<a href="<c:url value="/logout"/>">Logout</a>
</body>
</html>

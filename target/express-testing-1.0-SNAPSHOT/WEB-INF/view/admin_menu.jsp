<%@ page import="ru.eforward.express_testing.model.User" %>
<%@ page import="java.util.Objects" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Администратор</title>
</head>
<body>

<h1>Администратор</h1>
<%User user = (User)session.getAttribute("user");%>

<c:set var="user" scope="page" value="${param.user}"/>

<p><c:out value="${user.lastName}" default="error: last name not found..."/></p>
<p><c:out value="${user.firstName}" default="error: first name not found..."/></p>
<p><c:out value="${user.middleName}" default="error: middle name not found..."/></p>

<hr/>
    <p1><a href="createSchool"> Изменить</a> имя школы</p1>
    <p1><a href="createBranch"> Добавить</a> филиал</p1>
    <p1><a href="createUser"> Добавить</a> пользователя</p1>
<hr/>

<%
//    String branchAdded = added ? "branchAdded": "notAdded";
//    request.setAttribute("branchAdded", branchAdded);
    String branchAdded = (String)request.getAttribute("branchAdded");
    String choice = (String)request.getAttribute("schoolAdded");
    String userCreated = (String)request.getAttribute("userCreated");
    if("schoolAdded".equals(choice)){
        %>
            <%="<p>Имя школы успешно изменено!</p>"%>
        <%
    }
    //            userCreated = "created";
    //        allFieldsFilled = "all";
    if("created".equals(userCreated)){
        %>
            <%="<p>Новый пользователь успешно добавлен!</p>"%>
        <%
    }
    if("branchAdded".equals(branchAdded)){
        %>
            <%="<p>Новый филиал успешно добавлен!</p>"%>
        <%
    }
    if(Objects.nonNull(branchAdded) && !"branchAdded".equals(branchAdded)){
        %>
            <%="<p>Возникла ошибка! Филиал не был добавлен!</p>"%>
        <%
    }

%>

<a href="<c:url value='/logout' />">Выйти</a>
</body>
</html>

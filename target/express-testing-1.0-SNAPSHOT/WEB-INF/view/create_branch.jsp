<%--
This page is used by ADMIN to create new entities of a school
--%>
<%@ page import="ru.eforward.express_testing.utils.LogHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    LogHelper.writeMessage("create_branch.jsp");
%>
<html>
<head>
    <title>Добавить филиал</title>
</head>
<body>
<div id="box">
    <form name="add" action="UpdatingServlet" method="POST">
        <table>         <%--таблица--%>
            <tr>        <%--table raw--%>
                <p>Укажите название филиала:</p>
                <td><input type="text" name="branchName" size="12"/></td>
            </tr>
            <tr>
                <td><input type="submit" name="createBranchButton" value="Добавить филиал"/></td>
            </tr>
        </table>
    </form>
</div>

<a href="<c:url value='/logout' />">Logout</a>
</body>
</html>

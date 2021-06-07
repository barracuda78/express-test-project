<%--
  Created by IntelliJ IDEA.
  User: ENVY
  Date: 05.06.2021
  Time: 19:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Изменить имя школы</title>
</head>
<body>
    <div id="box">
        <form name="add" action="UpdatingServlet" method="GET">
            <table>         <%--таблица--%>
                <tr>        <%--table raw--%>
                    <p>Укажите название школы:</p>
                    <td><input type="text" name="schoolName" size="12"/></td>
                </tr>
                <tr>
                    <td><input type="submit" name="createSchool" value="Изменить название школы"/></td>
                </tr>
            </table>
        </form>
    </div>

<a href="<c:url value='/logout' />">Logout</a>
</body>
</html>

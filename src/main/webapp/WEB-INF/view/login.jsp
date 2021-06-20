<%--
This page is used by all users to log in to the service
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link href="../../css/style02.css" rel="stylesheet" type="text/css" >
</head>
<body>

    <div class="form">

        <h1>Вход в систему экспресс-тестирования</h1><br>
        <form method="post" action="">

            <input type="text" required placeholder="login" name="login"><br>
            <input type="password" required placeholder="password" name="password"><br><br>
            <input class="button" type="submit" value="Войти">

        </form>
    </div>
</body>
</html>

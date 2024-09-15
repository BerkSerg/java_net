<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Регистрация пользователя</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <section class="form">
        <c:if test="${not empty error}">
            <p class="text-error">${error}</p>
            <c:remove var="error" scope="session" />
        </c:if>
        <form class="reg_user" action="regUser" method="post">
            <label for="login">Логин(email)</label><input type="email" id="login" name="login">
            <label for="name">Псевдоним</label><input type="text" id="name" name="name">
            <label for="password">Пароль</label><input type="password" id="password" name="password">
            <div>
                <button type="submit">Регистрация</button>
                <button type="button" onclick="window.location='index.jsp'">Отмена</button>
            </div>
        </form>
    </section>

</body>
</html>

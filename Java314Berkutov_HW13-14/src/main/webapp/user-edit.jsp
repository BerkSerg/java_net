<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User edit</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section class="form">
    <h2>Редактирование пользователя</h2>
    <c:if test="${not empty error}">
        <p class="text-error">${error}</p>
        <c:remove var="error" scope="session" />
    </c:if>
    <form class="reg_user" action="updateUser" method="post">
        <label for="login">Логин(email)</label><input type="email" id="login" name="login">
        <label for="name">Псевдоним</label><input type="text" id="name" name="name">
        <label for="password">Пароль</label><input type="password" id="password" name="password">
        <div>
            <button type="submit">Обновить</button>
            <button type="button" onclick="window.location='index.jsp'">Отмена</button>
        </div>
    </form>
</section>

</body>
</html>

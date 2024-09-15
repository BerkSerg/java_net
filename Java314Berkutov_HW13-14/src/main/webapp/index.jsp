<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>User service</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <div>
        <h1>User service</h1>
    </div>

    <c:if test="${not empty success}">
        <p class="center text-success fs-3">${success}</p>
        <c:remove var="success" scope="session" />
    </c:if>

    <div class="items">
        <div><a href="user-register.jsp">1. Регистрация пользователя</a></div>
        <div><a href="list-users.jsp">2. Просмотр пользователей</a></div>
    </div>
    </section>
</body>
</html>
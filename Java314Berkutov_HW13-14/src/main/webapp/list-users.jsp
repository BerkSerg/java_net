<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="user.dao.UserDao" %>
<%@ page import="conn.DBConnecion" %>
<%@ page import="user.entity.User" %>
<%@ page import="java.util.ArrayList" %>
<html>
<head>
    <title>Users list</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h2>Список пользователей</h2>
<c:if test="${not empty error}">
    <p class="text-error">${error}</p>
    <c:remove var="error" scope="session" />
</c:if>
<table class="user_list">
    <thead>
    <tr>Логин</tr>
    <tr>Имя</tr>
    <tr>Email</tr>
    </thead>
    <%
        UserDao userDao = new UserDao(DBConnecion.getConn());
        ArrayList<User> users = userDao.getAllUsers();
        for (User user : users) {
            out.println("<tr>");
            out.println("<td>" + user.getLogin() + "</td><td>" + user.getName() + "</td><td>" + user.getPassword() + "</td>");
            out.println("<td><a href='user-edit.jsp?id="+user.getId()+"'>Редактировать</a></td><td><a href='userDel?id="+user.getId()+"'>Удалить</a></td>");
            out.println("</tr>");
        };
    %>
</table>
</body>
</html>

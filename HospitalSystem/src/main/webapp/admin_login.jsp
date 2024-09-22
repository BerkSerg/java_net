<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin login page</title>
    <jsp:include page="component/allcss.jsp"/>
</head>
<body>
    <jsp:include page="component/navbar.jsp" />

    <div class="height">
        <section class="form">
            <h2>Авторизация администратора</h2>
            <c:if test="${not empty sucMsg}">
                <p class="center text-success fs-3">${sucMsg}</p>
                <c:remove var="sucMsg" scope="session" />
            </c:if>
            <c:if test="${not empty errMsg}">
                <p class="center text-success fs-3">${errMsg}</p>
                <c:remove var="errMsg" scope="session" />
            </c:if>
            <form action="adminLogin" method="post">
                <div>
                    <label for="email">Email</label>
                    <input type="email" name="email" id="email" class="form-control" required>
                </div>
                <div>
                    <label for="pass">Пароль</label>
                    <input type="password" name="pass" id="pass" class="form-control" required>
                </div>
                <button class="btn button">Авторизация</button>
            </form>
        </section>
    </div>

    <jsp:include page="component/footer.jsp" />
</body>
</html>

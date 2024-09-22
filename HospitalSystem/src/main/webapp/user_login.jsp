<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>USer login page</title>
    <jsp:include page="component/allcss.jsp"/>
</head>
<body>
<jsp:include page="component/navbar.jsp" />

<div class="height">
    <section class="form">
        <h2>Авторизация пациента</h2>
        <form action="userLogin" method="post">
            <div>
                <label for="email">Email</label>
                <input type="email" name="email" id="email" class="form-control" required>
            </div>
            <div>
                <label for="pass">Email</label>
                <input type="password" name="pass" id="pass" class="form-control" required>
            </div>
            <button class="btn button">Авторизация</button>
        </form>
        <p class="center">У вас нет аккаунта? <a href="signup.jsp" class="text-decoration-none">Создать аккаунт</a></p>
    </section>
</div>


<jsp:include page="component/footer.jsp" />
</body>
</html>

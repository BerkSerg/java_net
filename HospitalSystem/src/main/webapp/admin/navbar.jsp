<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="main">
    <div class="wrap">
        <ul class="menu">
            <li class="logo"><a href="index.jsp">Главная страница</a></li>
            <li><a href="doctor.jsp">Врач</a></li>
            <li><a href="patient">Пациент</a></li>
            <li>
                <div class="dropdown">
                    <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown"
                            aria-expanded="false">
                        Admin
                    </button>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="../adminLogout.jsp">Выход</a></li>
                    </ul>
                </div>
            </li>
        </ul>
    </div>
</div>



package user.servlet;

import conn.DBConnecion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import user.dao.UserDao;
import user.entity.User;

import java.io.IOException;

@WebServlet("/regUser")
public class RegUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            String login = req.getParameter("login");
            String name = req.getParameter("name");
            String pass = req.getParameter("password");

            UserDao userDao = new UserDao(DBConnecion.getConn());
            boolean result = userDao.registerUser(new User(login, name, pass));

            if (result) {
                session.setAttribute("success", "Пользователь успешно зарегистрирован");
                resp.sendRedirect("index.jsp");
            } else {
                session.setAttribute("error", "Ошибка регистрации пользователя");
                resp.sendRedirect("user-register.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Ошибка регистрации пользователя");
            resp.sendRedirect("user-register.jsp");
        }

    }
}

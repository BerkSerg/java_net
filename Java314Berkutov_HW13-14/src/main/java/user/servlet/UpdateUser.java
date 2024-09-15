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

@WebServlet("/updateUser")
public class UpdateUser extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String login = req.getParameter("login");
            String name = req.getParameter("name");
            String pass = req.getParameter("password");

            UserDao userDao = new UserDao(DBConnecion.getConn());
            boolean result = userDao.registerUser(new User(login, name, pass));
            HttpSession session = req.getSession();
            if (result) {
                session.setAttribute("success", "Пользователь успешно обновлен");
                resp.sendRedirect("index.jsp");
            } else {
                session.setAttribute("error", "Ошибка обновления пользователя");
                resp.sendRedirect("user-edit.jsp");
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

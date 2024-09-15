package user.servlet;

import conn.DBConnecion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import user.dao.UserDao;

import java.io.IOException;

@WebServlet("/userDel")
public class DeleteUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            UserDao userDao = new UserDao(DBConnecion.getConn());
            boolean result = userDao.deleteUserById(id);
            HttpSession session = req.getSession();
            if (result) {
                session.setAttribute("success", "Пользователь успешно удален");
                resp.sendRedirect("index.jsp");
            } else {
                session.setAttribute("error", "Ошибка удаления пользователя");
                resp.sendRedirect("list-users.jsp");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

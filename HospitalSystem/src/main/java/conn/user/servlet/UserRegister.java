package conn.user.servlet;

import conn.dao.UserDao;
import conn.db.DBConnect;
import conn.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/user-register")
public class UserRegister extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String full_name = req.getParameter("fio");
            String email = req.getParameter("email");
            String password = req.getParameter("pass");

            User newUser = new User(full_name, email, password);
            UserDao userDao = new UserDao(DBConnect.getConn());
            
            HttpSession session = req.getSession();
            boolean success = userDao.registerUser(newUser);
            if (success) {
                session.setAttribute("sucMsg", "Пользователь зарегистрирован успешно");
                resp.sendRedirect("signup.jsp");
            } else {
                session.setAttribute("errMsg", "Ошибка регистрации");
                resp.sendRedirect("signup.jsp");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

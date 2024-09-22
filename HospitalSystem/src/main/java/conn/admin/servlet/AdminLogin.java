package conn.admin.servlet;

import conn.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/adminLogin")
public class AdminLogin extends HttpServlet {
    private String adminEmail = "bersa@bk.ru";
    private String adminPass = "1234";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String userEmail = req.getParameter("email");
            String userPass = req.getParameter("pass");

            HttpSession session = req.getSession();
            if(userEmail.equals(adminEmail) && userPass.equals(adminPass)){
                session.setAttribute("adminObj", new User());
                resp.sendRedirect("admin/index.jsp");
            }else{
                session.setAttribute("errMsg", "Неверный email или пароль");
                resp.sendRedirect("admin_login.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

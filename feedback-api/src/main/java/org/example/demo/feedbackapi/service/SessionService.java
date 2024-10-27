package org.example.demo.feedbackapi.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.demo.feedbackapi.model.ERole;
import org.example.demo.feedbackapi.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final HttpSession session;

    public void setUser(User user) {
        session.setAttribute("currentUser", user);
    }

    public String getCurrentUserName() {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "";
        }
        return currentUser.getUsername();
    }

    public ERole getCurrentUserRole() {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return ERole.USER;
        }
        return currentUser.getERole();
    }

    public boolean isAdmin() {
        return (getCurrentUserRole() == ERole.ADMIN);
    }
}

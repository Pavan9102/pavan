package com.example.ems.web;

import com.example.ems.dao.CustomerDao;
import com.example.ems.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String loginId = req.getParameter("loginId");
            String password = req.getParameter("password");
            if (loginId == null || password == null || loginId.isBlank() || password.isBlank()) {
                resp.sendRedirect("login.jsp?error=Missing+credentials");
                return;
            }
            Customer user = new CustomerDao().findByLoginAndPassword(loginId, password);
            if (user == null) {
                resp.sendRedirect("login.jsp?error=1");
                return;
            }
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);
            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                resp.sendRedirect("admin/customers");
            } else {
                resp.sendRedirect("app/dashboard");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}


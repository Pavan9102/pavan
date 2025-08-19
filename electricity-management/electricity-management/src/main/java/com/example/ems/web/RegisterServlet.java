package com.example.ems.web;

import com.example.ems.dao.CustomerDao;
import com.example.ems.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            // Basic server-side validation
            String loginId = req.getParameter("loginId");
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String address = req.getParameter("address");
            String phone = req.getParameter("phone");
            String password = req.getParameter("password");
            if (loginId == null || name == null || email == null || address == null || phone == null || password == null
                || loginId.isBlank() || name.isBlank() || email.isBlank() || address.isBlank() || phone.isBlank() || password.isBlank()) {
                resp.sendRedirect("register.jsp?error=All+fields+are+mandatory");
                return;
            }
            Customer c = new Customer();
            c.setLoginId(loginId.trim());
            c.setName(name.trim());
            c.setEmail(email.trim());
            c.setAddress(address.trim());
            c.setPhone(phone.trim());
            c.setPassword(password);
            c.setRole("CUSTOMER");
            new CustomerDao().insert(c);
            resp.sendRedirect("login.jsp?msg=registered");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}


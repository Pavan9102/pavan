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
            Customer c = new Customer();
            c.setLoginId(req.getParameter("loginId"));
            c.setName(req.getParameter("name"));
            c.setEmail(req.getParameter("email"));
            c.setAddress(req.getParameter("address"));
            c.setPhone(req.getParameter("phone"));
            c.setPassword(req.getParameter("password"));
            c.setRole("CUSTOMER");
            new CustomerDao().insert(c);
            resp.sendRedirect("login.jsp?msg=registered");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}


package com.ebms.servlet;

import com.ebms.dao.CustomerDao;
import com.ebms.model.Customer;
import com.ebms.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AuthServlet", urlPatterns = {"/login", "/logout", "/register"})
public class AuthServlet extends HttpServlet {
    private final CustomerDao customerDao = new CustomerDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/logout".equals(path)) {
            HttpSession session = req.getSession(false);
            if (session != null) session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        } else if ("/register".equals(path)) {
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/login":
                handleLogin(req, resp);
                break;
            case "/register":
                handleRegister(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String loginId = req.getParameter("loginId");
        String password = req.getParameter("password");
        String adminUser = "admin";
        String adminPass = "admin123"; // demo only

        try {
            if (adminUser.equals(loginId) && adminPass.equals(password)) {
                HttpSession session = req.getSession(true);
                session.setAttribute("role", "ADMIN");
                session.setAttribute("user", adminUser);
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
                return;
            }

            Customer user = customerDao.findByLoginId(loginId);
            if (user != null && user.getPasswordHash().equals(PasswordUtil.hash(password))) {
                HttpSession session = req.getSession(true);
                session.setAttribute("role", "CUSTOMER");
                session.setAttribute("userId", user.getCustomerId());
                session.setAttribute("loginId", user.getLoginId());
                session.setAttribute("name", user.getName());
                resp.sendRedirect(req.getContextPath() + "/dashboard");
            } else {
                req.setAttribute("error", "Invalid credentials");
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String loginId = req.getParameter("loginId");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        if (isEmpty(loginId) || isEmpty(name) || isEmpty(email) || isEmpty(address) || isEmpty(phone) || isEmpty(password)) {
            req.setAttribute("error", "All fields are mandatory");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        Customer customer = new Customer();
        customer.setLoginId(loginId);
        customer.setName(name);
        customer.setEmail(email);
        customer.setAddress(address);
        customer.setPhone(phone);
        customer.setPasswordHash(PasswordUtil.hash(password));
        try {
            customerDao.create(customer);
            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (SQLException e) {
            req.setAttribute("error", "Registration failed: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        }
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}


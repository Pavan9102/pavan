package com.ebms.servlet;

import com.ebms.dao.CustomerDao;
import com.ebms.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AdminServlet", urlPatterns = {"/admin/customers", "/admin/delete-customer"})
public class AdminServlet extends HttpServlet {
    private final CustomerDao customerDao = new CustomerDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) { resp.sendRedirect(req.getContextPath() + "/login"); return; }
        String path = req.getServletPath();
        try {
            if ("/admin/customers".equals(path)) {
                List<Customer> customers = customerDao.findAll();
                req.setAttribute("customers", customers);
                req.getRequestDispatcher("/WEB-INF/views/customer_list.jsp").forward(req, resp);
            } else {
                resp.sendError(404);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) { resp.sendRedirect(req.getContextPath() + "/login"); return; }
        String path = req.getServletPath();
        try {
            if ("/admin/delete-customer".equals(path)) {
                int id = Integer.parseInt(req.getParameter("customerId"));
                customerDao.deleteIfNoDue(id);
                resp.sendRedirect(req.getContextPath() + "/admin/customers");
            } else {
                resp.sendError(404);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}


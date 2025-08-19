package com.example.ems.web;

import com.example.ems.dao.CustomerDao;
import com.example.ems.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CustomerListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Customer> list = new CustomerDao().findAll();
            req.setAttribute("customers", list);
            req.getRequestDispatcher("/WEB-INF/views/customers.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String deleteId = req.getParameter("deleteId");
        if (deleteId != null) {
            try {
                boolean ok = new CustomerDao().deleteIfNoDue(Integer.parseInt(deleteId));
                resp.sendRedirect("/admin/customers?deleted=" + ok);
                return;
            } catch (Exception e) { throw new ServletException(e); }
        }
        resp.sendRedirect("/admin/customers");
    }
}


package com.example.ems.web;

import com.example.ems.dao.BillDao;
import com.example.ems.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BillListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Customer user = (Customer) req.getSession().getAttribute("user");
        String search = req.getParameter("search");
        try {
            req.setAttribute("bills", new BillDao().listByCustomer(user.getCustomerId(), search));
            req.getRequestDispatcher("/WEB-INF/views/bills.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}


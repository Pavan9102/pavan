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
        int page = 1;
        int size = 10;
        try { page = Integer.parseInt(req.getParameter("page")); } catch (Exception ignored) {}
        if (page < 1) page = 1;
        try {
            // Simple in-memory pagination for brevity
            java.util.List<com.example.ems.model.Bill> all = new BillDao().listByCustomer(user.getCustomerId(), search);
            int from = Math.min((page - 1) * size, all.size());
            int to = Math.min(from + size, all.size());
            req.setAttribute("bills", all.subList(from, to));
            req.setAttribute("page", page);
            req.setAttribute("hasMore", to < all.size());
            req.getRequestDispatcher("/WEB-INF/views/bills.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}


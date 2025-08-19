package com.example.ems.web;

import com.example.ems.dao.BillDao;
import com.example.ems.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Customer user = (Customer) req.getSession().getAttribute("user");
        BillDao billDao = new BillDao();
        try {
            double total = billDao.totalConsumption(user.getCustomerId());
            double lastPay = billDao.lastPaymentAmount(user.getCustomerId());
            double due = billDao.dueAmount(user.getCustomerId());
            req.setAttribute("total", total);
            req.setAttribute("lastPay", lastPay);
            req.setAttribute("due", due);
            req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}


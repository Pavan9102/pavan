package com.example.ems.web;

import com.example.ems.dao.BillDao;
import com.example.ems.model.Bill;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddBillServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/addbill.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Bill b = new Bill();
            b.setCustomerId(Integer.parseInt(req.getParameter("customerId")));
            b.setMonth(req.getParameter("month"));
            b.setYear(Integer.parseInt(req.getParameter("year")));
            b.setMeterReading(Double.parseDouble(req.getParameter("meterReading")));
            b.setBillAmount(Double.parseDouble(req.getParameter("billAmount")));
            b.setStatus("Pending");
            new BillDao().insert(b);
            resp.sendRedirect("/admin/add-bill?added=1");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}


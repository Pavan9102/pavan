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
            String cid = req.getParameter("customerId");
            String month = req.getParameter("month");
            String year = req.getParameter("year");
            String reading = req.getParameter("meterReading");
            String amount = req.getParameter("billAmount");
            if (cid==null||month==null||year==null||reading==null||amount==null||cid.isBlank()||month.isBlank()||year.isBlank()||reading.isBlank()||amount.isBlank()) {
                resp.sendRedirect("/admin/add-bill?error=All+fields+are+mandatory");
                return;
            }
            Bill b = new Bill();
            b.setCustomerId(Integer.parseInt(cid));
            b.setMonth(month);
            b.setYear(Integer.parseInt(year));
            b.setMeterReading(Double.parseDouble(reading));
            b.setBillAmount(Double.parseDouble(amount));
            b.setPaidAmount(0);
            b.setStatus("Pending");
            new BillDao().insert(b);
            resp.sendRedirect("/admin/add-bill?added=1");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}


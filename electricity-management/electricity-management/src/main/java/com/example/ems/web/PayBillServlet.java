package com.example.ems.web;

import com.example.ems.dao.BillDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PayBillServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/pay.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] billIds = req.getParameterValues("billId");
        String[] amounts = req.getParameterValues("amount");
        if (billIds != null && amounts != null && billIds.length == amounts.length) {
            BillDao dao = new BillDao();
            try {
                for (int i = 0; i < billIds.length; i++) {
                    double amt = 0;
                    try { amt = Double.parseDouble(amounts[i]); } catch (NumberFormatException ignored) { }
                    if (amt > 0) dao.applyPayment(Integer.parseInt(billIds[i]), amt);
                }
            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
        resp.sendRedirect("/app/bills?paid=1");
    }
}


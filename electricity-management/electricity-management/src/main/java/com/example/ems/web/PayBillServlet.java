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
        if (billIds != null) {
            BillDao dao = new BillDao();
            try {
                for (String id : billIds) {
                    dao.markPaid(Integer.parseInt(id));
                }
            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
        resp.sendRedirect("/app/bills?paid=1");
    }
}


package com.ebms.servlet;

import com.ebms.dao.BillDao;
import com.ebms.dao.PaymentDao;
import com.ebms.model.Bill;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "BillServlet", urlPatterns = {"/bills", "/paybill", "/admin/addbill"})
public class BillServlet extends HttpServlet {
    private final BillDao billDao = new BillDao();
    private final PaymentDao paymentDao = new PaymentDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("role") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        String path = req.getServletPath();
        String role = (String) session.getAttribute("role");
        try {
            if ("/bills".equals(path)) {
                int customerId = (Integer) session.getAttribute("userId");
                String q = req.getParameter("q");
                List<Bill> bills = billDao.listByCustomer(customerId, q);
                req.setAttribute("bills", bills);
                req.getRequestDispatcher("/WEB-INF/views/bill_list.jsp").forward(req, resp);
            } else if ("/paybill".equals(path)) {
                int billId = Integer.parseInt(req.getParameter("billId"));
                Bill bill = billDao.getById(billId);
                req.setAttribute("bill", bill);
                req.getRequestDispatcher("/WEB-INF/views/pay_bill.jsp").forward(req, resp);
            } else if ("/admin/addbill".equals(path)) {
                if (!"ADMIN".equals(role)) { resp.sendError(403); return; }
                req.getRequestDispatcher("/WEB-INF/views/add_bill.jsp").forward(req, resp);
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
        if (session == null || session.getAttribute("role") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        String path = req.getServletPath();
        String role = (String) session.getAttribute("role");
        try {
            if ("/paybill".equals(path)) {
                int billId = Integer.parseInt(req.getParameter("billId"));
                double amount = Double.parseDouble(req.getParameter("amount"));
                Bill bill = billDao.getById(billId);
                if (bill == null) { resp.sendError(404); return; }
                paymentDao.create(billId, amount);
                double totalPaid = billDao.getTotalPaidForBill(billId);
                if (totalPaid >= bill.getBillAmount() - 0.001) {
                    billDao.updateStatus(billId, "PAID");
                } else if (totalPaid > 0) {
                    billDao.updateStatus(billId, "PARTIAL");
                }
                resp.sendRedirect(req.getContextPath() + "/bills");
            } else if ("/admin/addbill".equals(path)) {
                if (!"ADMIN".equals(role)) { resp.sendError(403); return; }
                Bill bill = new Bill();
                bill.setCustomerId(Integer.parseInt(req.getParameter("customerId")));
                bill.setMeterReading(Double.parseDouble(req.getParameter("meterReading")));
                bill.setBillAmount(Double.parseDouble(req.getParameter("billAmount")));
                bill.setMonthStr(req.getParameter("month"));
                bill.setYearInt(Integer.parseInt(req.getParameter("year")));
                bill.setStatus("PENDING");
                billDao.create(bill);
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            } else {
                resp.sendError(404);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}


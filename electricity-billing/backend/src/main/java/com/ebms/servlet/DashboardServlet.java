package com.ebms.servlet;

import com.ebms.dao.BillDao;
import com.ebms.dao.CustomerDao;
import com.ebms.model.Bill;
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

@WebServlet(name = "DashboardServlet", urlPatterns = {"/dashboard", "/admin/dashboard"})
public class DashboardServlet extends HttpServlet {
    private final BillDao billDao = new BillDao();
    private final CustomerDao customerDao = new CustomerDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("role") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        String role = (String) session.getAttribute("role");
        try {
            if ("ADMIN".equals(role)) {
                req.getRequestDispatcher("/WEB-INF/views/admin_dashboard.jsp").forward(req, resp);
            } else {
                int customerId = (Integer) session.getAttribute("userId");
                List<Bill> bills = billDao.listByCustomer(customerId, null);
                double totalConsumption = bills.stream().mapToDouble(Bill::getMeterReading).sum();
                double lastPayment = bills.stream()
                        .filter(b -> "PAID".equalsIgnoreCase(b.getStatus()))
                        .mapToDouble(Bill::getBillAmount)
                        .findFirst().orElse(0);
                double due = bills.stream()
                        .filter(b -> !"PAID".equalsIgnoreCase(b.getStatus()))
                        .mapToDouble(Bill::getBillAmount)
                        .sum();
                req.setAttribute("totalConsumption", totalConsumption);
                req.setAttribute("lastPayment", lastPayment);
                req.setAttribute("dueAmount", due);
                req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}


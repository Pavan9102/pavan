<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.ems.model.Bill" %>
<%@ page import="com.example.ems.dao.BillDao" %>
<%@ page import="com.example.ems.model.Customer" %>
<%
  Customer user = (Customer) session.getAttribute("user");
  BillDao dao = new BillDao();
  List<Bill> bills = dao.listUnpaidByCustomer(user.getCustomerId());
%>
<html>
  <head>
    <title>Pay Bill</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
  </head>
  <body class="bg-light">
    <div class="container py-4">
      <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>Pay Bill</h3>
        <a href="/app/dashboard" class="btn btn-link">Back</a>
      </div>
      <form method="post" action="/app/pay">
        <table class="table table-bordered">
          <thead>
            <tr><th></th><th>Bill Id</th><th>Meter Reading</th><th>Month-Year</th><th>Bill Amount</th></tr>
          </thead>
          <tbody>
            <%
              double total = 0.0;
              for (Bill b : bills) { total += b.getBillAmount();
            %>
            <tr>
              <td><input type="checkbox" name="billId" value="<%= b.getBillId() %>" /></td>
              <td><%= b.getBillId() %></td>
              <td><%= b.getMeterReading() %></td>
              <td><%= b.getMonth() %> - <%= b.getYear() %></td>
              <td>₹<%= b.getBillAmount() %></td>
            </tr>
            <% } %>
          </tbody>
          <tfoot>
            <tr><th colspan="4" class="text-end">Payment Total</th><th>₹<%= total %></th></tr>
          </tfoot>
        </table>
        <button class="btn btn-primary">Pay Bill</button>
      </form>
    </div>
  </body>
  </html>

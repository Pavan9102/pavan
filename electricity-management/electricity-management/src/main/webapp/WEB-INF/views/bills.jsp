<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.ems.model.Bill" %>
<html>
  <head>
    <title>View Bills</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
  </head>
  <body class="bg-light">
    <div class="container py-4">
      <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>View Bills</h3>
        <a href="/app/dashboard" class="btn btn-link">Back</a>
      </div>
      <form class="row g-2 mb-3" method="get">
        <div class="col-auto">
          <input class="form-control" name="search" placeholder="Search by month" value="<%= request.getParameter("search") == null ? "" : request.getParameter("search") %>" />
        </div>
        <div class="col-auto">
          <button class="btn btn-primary">Search</button>
        </div>
      </form>
      <table class="table table-striped table-bordered">
        <thead>
          <tr><th>Bill Id</th><th>Meter Reading</th><th>Month-Year</th><th>Bill Amount</th><th>Status</th></tr>
        </thead>
        <tbody>
          <%
            List<Bill> bills = (List<Bill>) request.getAttribute("bills");
            for (Bill b : bills) {
          %>
          <tr>
            <td><%= b.getBillId() %></td>
            <td><%= b.getMeterReading() %></td>
            <td><%= b.getMonth() %> - <%= b.getYear() %></td>
            <td>₹<%= b.getBillAmount() %></td>
            <td><%= b.getStatus() %></td>
          </tr>
          <% } %>
        </tbody>
      </table>
      <a href="/app/pay" class="btn btn-success">Pay Bills</a>
    </div>
  </body>
  </html>

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
      <div class="d-flex justify-content-between align-items-center">
        <a href="/app/pay" class="btn btn-success">Pay Bills</a>
        <div>
          <%
            Integer page = (Integer) request.getAttribute("page");
            Boolean hasMore = (Boolean) request.getAttribute("hasMore");
            String q = request.getParameter("search");
            String query = q == null ? "" : ("&search=" + java.net.URLEncoder.encode(q, "UTF-8"));
          %>
          <a class="btn btn-outline-secondary <%= page <= 1 ? "disabled" : "" %>" href="/app/bills?page=<%= page-1 %><%= query %>">Prev</a>
          <span class="mx-2">Page <%= page %></span>
          <a class="btn btn-outline-secondary <%= hasMore ? "" : "disabled" %>" href="/app/bills?page=<%= page+1 %><%= query %>">Next</a>
        </div>
      </div>
    </div>
  </body>
  </html>

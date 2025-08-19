<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
  </head>
  <body class="bg-light">
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
      <div class="container">
        <a class="navbar-brand" href="/app/dashboard">EMS</a>
        <div class="navbar-nav">
          <a class="nav-link" href="/app/bills">View Bills</a>
          <a class="nav-link" href="/app/pay">Pay Bills</a>
        </div>
        <div class="navbar-nav ms-auto">
          <a class="nav-link" href="/logout">Logout</a>
        </div>
      </div>
    </nav>
    <div class="container py-5">
      <div class="row g-4">
        <div class="col-md-4">
          <div class="card text-center"><div class="card-body"><h5>Total Consumption</h5><h3><%= request.getAttribute("total") %> kwh</h3></div></div>
        </div>
        <div class="col-md-4">
          <div class="card text-center"><div class="card-body"><h5>Last Payment</h5><h3>₹<%= request.getAttribute("lastPay") %></h3></div></div>
        </div>
        <div class="col-md-4">
          <div class="card text-center"><div class="card-body"><h5>Due Amount</h5><h3>₹<%= request.getAttribute("due") %></h3></div></div>
        </div>
      </div>
    </div>
  </body>
  </html>

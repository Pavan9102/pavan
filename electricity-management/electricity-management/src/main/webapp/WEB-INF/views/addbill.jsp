<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Add Bill</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
  </head>
  <body class="bg-light">
    <div class="container py-4">
      <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>Bill Entry for Customer</h3>
        <a href="/admin/customers" class="btn btn-link">Back to Customers</a>
      </div>
      <div class="card shadow-sm">
        <div class="card-body">
          <form method="post" action="/admin/add-bill">
            <div class="row g-3">
              <div class="col-md-4">
                <label class="form-label">Customer Id</label>
                <input class="form-control" name="customerId" required />
              </div>
              <div class="col-md-4">
                <label class="form-label">Month</label>
                <input class="form-control" name="month" placeholder="Jan" required />
              </div>
              <div class="col-md-4">
                <label class="form-label">Year</label>
                <input class="form-control" name="year" type="number" required />
              </div>
              <div class="col-md-6">
                <label class="form-label">Meter Reading</label>
                <input class="form-control" name="meterReading" type="number" step="0.01" required />
              </div>
              <div class="col-md-6">
                <label class="form-label">Bill Amount</label>
                <input class="form-control" name="billAmount" type="number" step="0.01" required />
              </div>
            </div>
            <div class="mt-3">
              <button class="btn btn-primary">Add Bill</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </body>
  </html>

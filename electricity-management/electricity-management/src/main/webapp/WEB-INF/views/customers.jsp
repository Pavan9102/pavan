<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.ems.model.Customer" %>
<html>
  <head>
    <title>Customers</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
  </head>
  <body class="bg-light">
    <div class="container py-4">
      <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>Customer List</h3>
        <div>
          <a class="btn btn-success" href="/admin/add-bill">Add Bill</a>
          <a class="btn btn-link" href="/logout">Logout</a>
        </div>
      </div>
      <table class="table table-striped table-bordered">
        <thead><tr><th>Id</th><th>Login Id</th><th>Name</th><th>Email</th><th>Phone</th><th>Role</th><th></th></tr></thead>
        <tbody>
          <%
            List<Customer> customers = (List<Customer>) request.getAttribute("customers");
            for (Customer c : customers) {
          %>
            <tr>
              <td><%= c.getCustomerId() %></td>
              <td><%= c.getLoginId() %></td>
              <td><%= c.getName() %></td>
              <td><%= c.getEmail() %></td>
              <td><%= c.getPhone() %></td>
              <td><%= c.getRole() %></td>
              <td>
                <form method="post" action="/admin/customers" onsubmit="return confirm('Delete if no due?')">
                  <input type="hidden" name="deleteId" value="<%= c.getCustomerId() %>" />
                  <button class="btn btn-sm btn-danger">Delete</button>
                </form>
              </td>
            </tr>
          <% } %>
        </tbody>
      </table>
    </div>
  </body>
  </html>

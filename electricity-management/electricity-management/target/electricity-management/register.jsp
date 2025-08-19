<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Register Customer</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
  </head>
  <body class="bg-light">
    <div class="container py-5">
      <div class="row justify-content-center">
        <div class="col-md-6">
          <div class="card shadow-sm">
            <div class="card-header bg-primary text-white">Register Customer</div>
            <div class="card-body">
              <form method="post" action="register">
                <div class="mb-3">
                  <label class="form-label">User Id</label>
                  <input name="loginId" class="form-control" required />
                </div>
                <div class="mb-3">
                  <label class="form-label">Name</label>
                  <input name="name" class="form-control" required />
                </div>
                <div class="mb-3">
                  <label class="form-label">Email</label>
                  <input type="email" name="email" class="form-control" required />
                </div>
                <div class="mb-3">
                  <label class="form-label">Address</label>
                  <input name="address" class="form-control" required />
                </div>
                <div class="mb-3">
                  <label class="form-label">Phone</label>
                  <input name="phone" class="form-control" required />
                </div>
                <div class="mb-3">
                  <label class="form-label">Password</label>
                  <input type="password" name="password" class="form-control" required />
                </div>
                <button type="submit" class="btn btn-primary">Register</button>
                <a href="login.jsp" class="btn btn-link">Already have an account?</a>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
  </html>

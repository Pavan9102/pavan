<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Login</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
  </head>
  <body class="bg-light">
    <div class="container py-5">
      <div class="row justify-content-center">
        <div class="col-md-6">
          <div class="card shadow-sm">
            <div class="card-header bg-primary text-white">User Login</div>
            <div class="card-body">
              <form method="post" action="login">
                <div class="mb-3">
                  <label class="form-label">User Id</label>
                  <input name="loginId" class="form-control" required />
                </div>
                <div class="mb-3">
                  <label class="form-label">Password</label>
                  <input type="password" name="password" class="form-control" required />
                </div>
                <button type="submit" class="btn btn-primary">Login</button>
                <a href="register.jsp" class="btn btn-link">Register</a>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
  </html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register - EBMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css" />
</head>
<body>
<div class="container auth">
    <div class="card">
        <h2>Create your account</h2>
        <c:if test="${not empty error}">
            <div class="alert">${error}</div>
        </c:if>
        <form method="post" action="${pageContext.request.contextPath}/register">
            <label>User ID</label>
            <input type="text" name="loginId" required />
            <label>Name</label>
            <input type="text" name="name" required />
            <label>Email</label>
            <input type="email" name="email" required />
            <label>Address</label>
            <input type="text" name="address" required />
            <label>Phone</label>
            <input type="text" name="phone" required />
            <label>Password</label>
            <input type="password" name="password" required />
            <button type="submit" class="btn">Register</button>
            <a class="link" href="${pageContext.request.contextPath}/login">Back to login</a>
        </form>
    </div>
</div>
</body>
</html>


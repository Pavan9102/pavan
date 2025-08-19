<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - EBMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css" />
</head>
<body>
<div class="container auth">
    <h1>Electricity Bill Management</h1>
    <div class="card">
        <h2>Login</h2>
        <c:if test="${not empty error}">
            <div class="alert">${error}</div>
        </c:if>
        <form method="post" action="${pageContext.request.contextPath}/login">
            <label>User ID</label>
            <input type="text" name="loginId" required />
            <label>Password</label>
            <input type="password" name="password" required />
            <button type="submit" class="btn">Login</button>
        </form>
        <p class="muted">Admin demo: admin / admin123</p>
        <p>New user? <a href="${pageContext.request.contextPath}/register">Register</a></p>
    </div>
</div>
</body>
</html>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard - EBMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/partials/nav.jsp" />
<div class="container">
    <h2>Admin Dashboard</h2>
    <div class="grid">
        <a class="card link-card" href="${pageContext.request.contextPath}/admin/customers">
            <h3>List Customers</h3>
            <p>Manage all registered customers</p>
        </a>
        <a class="card link-card" href="${pageContext.request.contextPath}/admin/addbill">
            <h3>Add Bill</h3>
            <p>Register bill for a customer</p>
        </a>
    </div>
</div>
</body>
</html>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - EBMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/partials/nav.jsp" />
<div class="container">
    <h2>Welcome, ${sessionScope.name}</h2>
    <div class="grid">
        <div class="stat">
            <div class="stat-label">Total Consumption</div>
            <div class="stat-value">${totalConsumption} kWh</div>
        </div>
        <div class="stat">
            <div class="stat-label">Last Payment</div>
            <div class="stat-value">₹ ${lastPayment}</div>
        </div>
        <div class="stat">
            <div class="stat-label">Total Due</div>
            <div class="stat-value due">₹ ${dueAmount}</div>
        </div>
    </div>
    <div class="actions">
        <a class="btn" href="${pageContext.request.contextPath}/bills">View Bills</a>
    </div>
 </div>
</body>
</html>


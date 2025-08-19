<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Bill - EBMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/partials/nav.jsp" />
<div class="container">
    <h2>Add Bill</h2>
    <div class="card">
        <form method="post" action="${pageContext.request.contextPath}/admin/addbill">
            <label>Customer ID</label>
            <input type="number" name="customerId" required />
            <label>Meter Reading (kWh)</label>
            <input type="number" name="meterReading" step="0.01" required />
            <label>Bill Amount (₹)</label>
            <input type="number" name="billAmount" step="0.01" required />
            <label>Month</label>
            <input type="text" name="month" placeholder="e.g. Jan" required />
            <label>Year</label>
            <input type="number" name="year" required />
            <button type="submit" class="btn">Add</button>
        </form>
    </div>
</div>
</body>
</html>


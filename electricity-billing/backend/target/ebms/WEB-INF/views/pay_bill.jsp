<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pay Bill - EBMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/partials/nav.jsp" />
<div class="container">
    <h2>Pay Bill</h2>
    <div class="card">
        <p><strong>Bill ID:</strong> ${bill.billId}</p>
        <p><strong>Month:</strong> ${bill.monthStr} ${bill.yearInt}</p>
        <p><strong>Amount Due:</strong> ₹ ${bill.billAmount}</p>
        <form method="post" action="${pageContext.request.contextPath}/paybill">
            <input type="hidden" name="billId" value="${bill.billId}" />
            <label>Amount to pay</label>
            <input type="number" name="amount" min="1" max="${bill.billAmount}" step="0.01" required />
            <button type="submit" class="btn">Proceed Payment</button>
        </form>
    </div>
 </div>
</body>
</html>


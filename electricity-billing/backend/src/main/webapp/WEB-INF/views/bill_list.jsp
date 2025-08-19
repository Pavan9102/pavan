<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Bills - EBMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/partials/nav.jsp" />
<div class="container">
    <h2>My Bills</h2>
    <form method="get" class="search">
        <input type="text" name="q" placeholder="Search by month or year" value="${param.q}" />
        <button class="btn" type="submit">Search</button>
    </form>
    <table class="table">
        <thead>
            <tr>
                <th>Bill ID</th>
                <th>Month</th>
                <th>Year</th>
                <th>Meter Reading (kWh)</th>
                <th>Amount (₹)</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="b" items="${bills}">
                <tr>
                    <td>${b.billId}</td>
                    <td>${b.monthStr}</td>
                    <td>${b.yearInt}</td>
                    <td>${b.meterReading}</td>
                    <td>${b.billAmount}</td>
                    <td>
                        <span class="badge ${b.status}">${b.status}</span>
                    </td>
                    <td>
                        <c:if test="${b.status != 'PAID'}">
                            <a class="btn" href="${pageContext.request.contextPath}/paybill?billId=${b.billId}">Pay</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>


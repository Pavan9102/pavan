<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Customers - EBMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css" />
</head>
<body>
<jsp:include page="/WEB-INF/views/partials/nav.jsp" />
<div class="container">
    <h2>Customers</h2>
    <table class="table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Login ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Address</th>
                <th>Phone</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="cst" items="${customers}">
            <tr>
                <td>${cst.customerId}</td>
                <td>${cst.loginId}</td>
                <td>${cst.name}</td>
                <td>${cst.email}</td>
                <td>${cst.address}</td>
                <td>${cst.phone}</td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/admin/delete-customer" onsubmit="return confirm('Delete customer?');">
                        <input type="hidden" name="customerId" value="${cst.customerId}" />
                        <button class="btn danger" type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>


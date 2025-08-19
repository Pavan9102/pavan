<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar">
    <div class="nav-brand">EBMS</div>
    <div class="nav-links">
        <c:choose>
            <c:when test="${sessionScope.role == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
                <a href="${pageContext.request.contextPath}/admin/customers">Customers</a>
                <a href="${pageContext.request.contextPath}/admin/addbill">Add Bill</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
                <a href="${pageContext.request.contextPath}/bills">My Bills</a>
            </c:otherwise>
        </c:choose>
        <a href="${pageContext.request.contextPath}/logout" class="danger">Logout</a>
    </div>
</nav>


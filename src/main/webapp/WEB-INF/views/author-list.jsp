<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Authors" />
<jsp:include page="layout-header.jspf" />

<div class="card">
    <div class="toolbar">
        <h2>Authors</h2>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/authors/new">+ New Author</a>
    </div>

    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>

    <table>
        <thead>
            <tr>
                <th>#</th>
                <th>Name</th>
                <th>Email</th>
                <th>Nationality</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="a" items="${authors}">
                <tr>
                    <td>${a.id}</td>
                    <td>${a.name}</td>
                    <td>${a.email}</td>
                    <td>${a.nationality}</td>
                    <td>
                        <a class="btn btn-link" href="${pageContext.request.contextPath}/authors/${a.id}/edit">Edit</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty authors}">
                <tr><td colspan="5" style="text-align:center;color:#888;">No authors found.</td></tr>
            </c:if>
        </tbody>
    </table>
</div>

<jsp:include page="layout-footer.jspf" />

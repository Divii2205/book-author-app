<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Books" />
<jsp:include page="layout-header.jspf" />

<div class="card">
    <div class="toolbar">
        <h2>Books (Inner Join with Authors)</h2>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/books/new">+ New Book</a>
    </div>

    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>

    <table>
        <thead>
            <tr>
                <th>#</th>
                <th>Title</th>
                <th>ISBN</th>
                <th>Genre</th>
                <th>Price</th>
                <th>Author</th>
                <th>Email</th>
                <th>Nationality</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="b" items="${books}">
                <tr>
                    <td>${b.bookId}</td>
                    <td>${b.title}</td>
                    <td>${b.isbn}</td>
                    <td>${b.genre}</td>
                    <td><fmt:formatNumber value="${b.price}" type="currency" currencySymbol="$" /></td>
                    <td>${b.authorName}</td>
                    <td>${b.authorEmail}</td>
                    <td>${b.authorNationality}</td>
                    <td>
                        <a class="btn btn-link" href="${pageContext.request.contextPath}/books/${b.bookId}/edit">Edit</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty books}">
                <tr><td colspan="9" style="text-align:center;color:#888;">No books found.</td></tr>
            </c:if>
        </tbody>
    </table>
</div>

<jsp:include page="layout-footer.jspf" />

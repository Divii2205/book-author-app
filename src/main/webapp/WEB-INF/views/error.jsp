<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="pageTitle" value="Error" />
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="layout-header.jspf" />

<div class="card">
    <h2>Something went wrong</h2>
    <div class="alert alert-error">${error}</div>
    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/books">Back to Books</a>
</div>

<jsp:include page="layout-footer.jspf" />

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" value="${author.id == null ? 'New Author' : 'Edit Author'}" />
<jsp:include page="layout-header.jspf" />

<div class="card">
    <h2>${pageTitle}</h2>

    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>

    <c:set var="action" value="${author.id == null ? '/authors' : '/authors/'.concat(author.id)}" />

    <form:form modelAttribute="author" action="${pageContext.request.contextPath}${action}" method="post">
        <div class="field">
            <label for="name">Name</label>
            <form:input path="name" id="name" />
            <form:errors path="name" cssClass="error-text" element="div" />
        </div>
        <div class="field">
            <label for="email">Email</label>
            <form:input path="email" id="email" type="email" />
            <form:errors path="email" cssClass="error-text" element="div" />
        </div>
        <div class="field">
            <label for="nationality">Nationality</label>
            <form:input path="nationality" id="nationality" />
        </div>
        <button type="submit" class="btn btn-primary">${author.id == null ? 'Create' : 'Update'}</button>
        <a href="${pageContext.request.contextPath}/authors" class="btn btn-secondary">Cancel</a>
    </form:form>
</div>

<jsp:include page="layout-footer.jspf" />

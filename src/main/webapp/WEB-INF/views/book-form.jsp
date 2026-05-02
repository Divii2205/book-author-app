<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" value="${book.id == null ? 'New Book' : 'Edit Book'}" />
<jsp:include page="layout-header.jspf" />

<div class="card">
    <h2>${pageTitle}</h2>

    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>

    <c:set var="action" value="${book.id == null ? '/books' : '/books/'.concat(book.id)}" />

    <form:form modelAttribute="book" action="${pageContext.request.contextPath}${action}" method="post">
        <div class="field">
            <label for="title">Title</label>
            <form:input path="title" id="title" />
            <form:errors path="title" cssClass="error-text" element="div" />
        </div>
        <div class="field">
            <label for="isbn">ISBN</label>
            <form:input path="isbn" id="isbn" />
            <form:errors path="isbn" cssClass="error-text" element="div" />
        </div>
        <div class="field">
            <label for="genre">Genre</label>
            <form:input path="genre" id="genre" />
        </div>
        <div class="field">
            <label for="price">Price</label>
            <form:input path="price" id="price" type="number" step="0.01" />
        </div>
        <div class="field">
            <label for="authorId">Author</label>
            <select name="authorId" id="authorId" required>
                <option value="">-- Select an author --</option>
                <c:forEach var="a" items="${authors}">
                    <option value="${a.id}"
                        <c:if test="${book.author != null && book.author.id == a.id}">selected</c:if>
                    >${a.name}</option>
                </c:forEach>
            </select>
            <form:errors path="author" cssClass="error-text" element="div" />
        </div>
        <button type="submit" class="btn btn-primary">${book.id == null ? 'Create' : 'Update'}</button>
        <a href="${pageContext.request.contextPath}/books" class="btn btn-secondary">Cancel</a>
    </form:form>
</div>

<jsp:include page="layout-footer.jspf" />

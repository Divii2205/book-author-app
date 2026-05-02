<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Author</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="navbar">
    <h2>Library Management</h2>
    <div>
        <a href="${pageContext.request.contextPath}/">Home</a>
    </div>
</div>

<div class="container">
    <h2>Add New Author</h2>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <form:form action="${pageContext.request.contextPath}/add-author" method="post" modelAttribute="author">
        <div class="form-group">
            <label for="name">Name</label>
            <form:input path="name" id="name" />
            <form:errors path="name" cssClass="error" />
        </div>
        <div class="form-group">
            <label for="nationality">Nationality</label>
            <form:input path="nationality" id="nationality" />
            <form:errors path="nationality" cssClass="error" />
        </div>
        <button type="submit" class="btn">Add Author</button>
        <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Cancel</a>
    </form:form>
</div>

</body>
</html>

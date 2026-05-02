<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Book</title>
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
    <h2>Add New Book</h2>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <form:form action="${pageContext.request.contextPath}/add-book" method="post" modelAttribute="book">
        <div class="form-group">
            <label for="title">Title</label>
            <form:input path="title" id="title" />
            <form:errors path="title" cssClass="error" />
        </div>
        <div class="form-group">
            <label for="genre">Genre</label>
            <form:input path="genre" id="genre" />
            <form:errors path="genre" cssClass="error" />
        </div>
        <div class="form-group">
            <label for="publicationYear">Publication Year</label>
            <form:input type="number" path="publicationYear" id="publicationYear" />
            <form:errors path="publicationYear" cssClass="error" />
        </div>
        <div class="form-group">
            <label for="author">Author</label>
            <form:select path="author" id="author">
                <form:option value="" label="-- Select Author --" />
                <form:options items="${authors}" itemValue="id" itemLabel="name" />
            </form:select>
            <form:errors path="author" cssClass="error" />
        </div>
        <button type="submit" class="btn">Add Book</button>
        <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Cancel</a>
    </form:form>
</div>

</body>
</html>

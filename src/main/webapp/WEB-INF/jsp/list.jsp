<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Library Management</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="navbar">
    <h2>Library Management</h2>
    <div>
        <a href="${pageContext.request.contextPath}/add-author">Add Author</a>
        <a href="${pageContext.request.contextPath}/add-book">Add Book</a>
    </div>
</div>

<div class="container">
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>

    <h2>Books List</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Genre</th>
                <th>Year</th>
                <th>Author</th>
                <th>Nationality</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="book" items="${books}">
                <tr>
                    <td>${book.id}</td>
                    <td>${book.title}</td>
                    <td>${book.genre}</td>
                    <td>${book.publicationYear}</td>
                    <td>${book.author.name}</td>
                    <td>${book.author.nationality}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/edit-book/${book.id}" class="btn btn-warning">Edit</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <h2 style="margin-top: 40px;">Authors List</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Nationality</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="author" items="${authors}">
                <tr>
                    <td>${author.id}</td>
                    <td>${author.name}</td>
                    <td>${author.nationality}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>

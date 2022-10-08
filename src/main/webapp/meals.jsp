<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <thead>
    <tr>
        <td>Date</td>
        <td>Description</td>
        <td>Calories</td>
    </tr>
    </thead>

    <c:forEach items="${meals}" var="meals">
      <%--  <tr bgcolor="green">
        <c:choose>
            <c:when test="${meals.excess==false}">
                <tr bgcolor="#ff4500"
            </c:when>
        </c:choose>--%>
        <tr style="background-color: ${meals.excess ? 'greenyellow':'red'}">
        <td>
            <fmt:parseDate value="${ meals.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                           type="both"/>
            <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/>
        </td>
            <td>${meals.description}</td>
            <td>${meals.calories}</td>
        </tr>
    </c:forEach>

</table>


</body>
</html>
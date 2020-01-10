<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<html>
<head>
    <title>Main page</title>
</head>
<body>

<sql:setDataSource dataSource="jdbc:users" var="db"/>

Hello!


<sql:query var="tb" dataSource="${db}">
    SELECT * FROM users
</sql:query>
<ol>
<c:forEach var="item" items="${tb.rows}">
<li><c:out value="${item.name}"/></li>
</c:forEach>
</ol>

<br><br>

<form action="logout"><input type="submit" value="Log out"/></form>
<form action="add_user.jsp"><input type="submit" value="Add user"/></form>

</body>
</html>
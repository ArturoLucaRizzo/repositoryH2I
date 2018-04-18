<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

	



		<select name="us" id="us" class="form-control">
			<option value="-1">List Users</option>
			<%
			int i = 1;
		%>
			<c:forEach var="u" items="${users}">
				<option value="<%=i%>">${u.getMail()}</option>
				<%
				i++;
			%>
			</c:forEach>
		</select>





	<script src="js/listUsers.js"></script>
</body>
</html>
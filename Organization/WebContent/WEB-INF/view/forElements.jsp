<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="it.h2i.idservice.accountablemodel.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

</head>
<body>

	<table style="width: 85%">
		<thead>
			<tr>
				<th class="sort" data-sort="name"><h3
						style='color: #69bcf0; font-weight: Bold'>Name</h3></th>
				<th class="sort" data-sort="surname"><h3
						style='color: #69bcf0; font-weight: Bold'>surname</h3></th>
				<th class="sort" data-sort="mail"><h3
						style='color: #69bcf0; font-weight: Bold'>mail</h3></th>
				<th colspan="2"><input type="text" class="search"
					placeholder="Search contact" /></th>
			</tr>
		</thead>
		<tbody class="list">

			<c:forEach var="u" items="${users}">
				<tr>


					<td class='id' style='display: none;'>${u.getIduser()}</td>
					<td class='name' name='name' style='color: white;'>${u.getName()}</td>
					<td class='surname' name='surname' style='color: white;'>${u.getSurname()}</td>
					<td class='mail' id="email" name='mail' style='color: white;'>${u.getMail()}</td>
					<td class='edit'><button class="edit-item-btn">Edit</button></td>

				


						<input type="hidden" name="mails" value="${u.getMail()}" />

						<td>
							<button name="rem">Remove</button>
						</td>

					<td style="color: blue;"><c:if test="${u.enable}">
							<a href="enable?mail=${u.mail}" style="color: blue;">Disable</a>
						</c:if> <c:if test="${not u.enable}">
							<a href="enable?mail=${u.mail}" style="color: blue;">Enable</a>
						</c:if></td>
				</tr>
			</c:forEach>


		</tbody>
	</table>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.8/jquery.min.js"></script>
	<script src="js/list.js"></script>

	<script src="js/provalist.js"></script>
	<script src="js/JSonReader.js"></script>


</body>
</html>
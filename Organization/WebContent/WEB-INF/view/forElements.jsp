<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="it.h2i.idservice.accountablemodel.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
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
					<td class='name' id="name" name='name' style='color: white;'>${u.getName()}</td>
					<td class='surname' name='surname' style='color: white;'>${u.getSurname()}</td>
					<td class='mail' id="email" name='mail' style='color: white;'>${u.getMail()}</td>
					<td class='edit'><button class="edit-item-btn">Edit</button></td>

					<td>
						<button class="btnOrg" name="rem">Remove</button>
					</td>

					<td style="color: blue;"><c:if test="${u.enable}">
							<button class="btnOrgRed" name="enable">Disable</button>
						</c:if> <c:if test="${not u.enable}">
							<button class="btnOrg" name="enable">Enable</button>
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
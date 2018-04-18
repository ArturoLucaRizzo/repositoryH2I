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
				<th><h3 style='color: #69bcf0; font-weight: Bold'>ID</h3></th>
				<th><h3 style='color: #69bcf0; font-weight: Bold'>Organization</h3></th>
				<th><h3 style='color: #69bcf0; font-weight: Bold'>P.IVA</h3></th>
				<th><h3 style='color: #69bcf0; font-weight: Bold'>Users</h3></th>

				<th colspan="2"><input type="text" class="search"
					placeholder="Search contact" /></th>
			</tr>
		</thead>
		<tbody class="list">
			<c:set var="i" scope="session" value="${0}" />
			<c:forEach var="o" items="${organizations}">
				<c:set var="i" scope="session" value="${i + 1}" />
				<div type="hidden" id="var"><c:out value="${i}" /></div>
				<tr>

					<td class='id' style='color: white' id="idorganizations"
						name="idorganizations">${o.getIdorganization()}</td>
					<td class='organization' id="name<c:out value="${i}" />" name='organization<c:out value="${i}" />'
						style='color: white;'>${o.getName()}</td>
					<td class='piva' name='piva<c:out value="${i}" />' id="piva<c:out value="${i}" />" style='color: white;'>${o.getPiva()}</td>
					<td class='users' id="users<c:out value="${i}" />" name='users' style='color: white;'>${o.getAppertains().size()}</td>
					<td class='edit'><button class="edit-item-btn">Edit</button></td>

						<td>
						<button class="btnOrg" id="remove<c:out value="${i}"/>" name="rem">Remove</button>
					</td>

					<td><button class="btnOrg" id="viewUser<c:out value="${i}"/>" name="viewUser">View</button></td>


				</tr>


			</c:forEach>
		</tbody>
	</table>
	<form action="addAndEditOrg" method="POST">
		<div>
			<table align="center">
				<td class="organization"><input type="hidden" name="idfield"
					id="id-field" placeholder="ID" /><input type="text"
					id="organization-field" name="organizationfield"
					placeholder="Organization" /></td>
				<td class="piva"><input type="text" name="pivafield"
					id="piva-field" placeholder="P.iva" /></td>
				<td class="add">
					<button class="edit-item-btn" id="add-btn" name="adds" value="true">Add</button>
					<button class="edit-item-btn" id="edit-btn" name="editfield"
						value="true">Edit</button>
				</td>
			</table>
		</div>
		</div>
		<div align="center">
			<h3 style='color: red;'>${error}</h3>
		</div>
	</form>



	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="js/list.js"></script>

	<script src="js/provalist.js"></script>
	<script src="js/JSonReader.js"></script>


</body>
</html>
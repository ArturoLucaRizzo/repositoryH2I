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
				<th><h3 style='color: #69bcf0; font-weight: Bold'>Name</h3></th>
				<th><h3 style='color: #69bcf0; font-weight: Bold'>surname</h3></th>
				<th><h3 style='color: #69bcf0; font-weight: Bold'>mail</h3></th>
				<th colspan="2"><input type="text" class="search"
					placeholder="Search contact" /></th>
			</tr>
		</thead>
		<tbody class="list">
			<c:set var="i" scope="session" value="${0}" />

			<c:forEach var="u" items="${users}">
				<c:set var="i" scope="session" value="${i + 1}" />
				<div type="hidden" id="var">
					<c:out value="${i}" />
				</div>


				<tr>

					<td class='id' style='display: none;'>${u.getIduser()}</td>
					<td class='name' id="name<c:out value="${i}"/>"
						name='name<c:out value="${i}"/>' style='color: white;'>${u.getName()}</td>
					<td class='surname' name='surname<c:out value="${i}"/>'
						style='color: white;'>${u.getSurname()}</td>
					<td class='mail' id="email<c:out value="${i}"/>"
						name='mail<c:out value="${i}"/>' style='color: white;'>${u.getMail()}</td>
					<td class='edit'><button class="edit-item-btn">Edit</button></td>

					<td>
						<button class="btnOrg" id="remove<c:out value="${i}"/>" name="rem">Remove</button>
					</td>

					<td style="color: blue;"><c:if test="${u.enable}">
							<button class="btnOrgRed" id="enable<c:out value="${i}"/>"
								name="enable">Disable</button>
						</c:if> <c:if test="${not u.enable}">
							<button class="btnOrg" id="enable<c:out value="${i}"/>"
								name="enable">Enable</button>
						</c:if></td>
				</tr>

			</c:forEach>



		</tbody>
	</table>
	<br>

	<div>
		<table align="center">
			<td class="name"><input type="hidden" name="idfield"
				id="id-field" /> <input type="text" id="name-field"
				name="namefield" placeholder="Name" /></td>
			<td class="surname"><input type="text" name="surnamefield"
				id="surname-field" placeholder="surname" /></td>
			<td class="mail"><input type="hidden" name="mailfield"
				id="mail-field" placeholder="mail" /></td>

			<td>



				<button class="btnOrg" id="add-btn" name="addbutton" value="true"
					data-toggle="modal" data-target="#myModal">Add</button>
				<button class="edit-item-btn" id="edit-btn" name="editfield"
					value="true">Edit</button>
			</td>
		</table>
	</div>


	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">List Users</h4>
				</div>
				<div class="modal-body" id="boxModel">
				
				</div>
				<div class="modal-footer">
				<button type="button" class="btn btn-default" name="addUser" >Add</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>








	<div align="left" margin-left="-15%">
		<button class="btnOrg" name="return">Back</button>
	</div>


	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="js/list.js"></script>

	<script src="js/provalist.js"></script>
	<script src="js/JSonReader.js"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="it.h2i.idservice.accountablemodel.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!--===============================================================================================-->
<link rel="icon" type="image/png" href="images/icons/favicon.ico" />
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="vendor/animsition/css/animsition.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="vendor/select2/select2.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="vendor/daterangepicker/daterangepicker.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css" href="css/util.css">
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="css/style.css">
<!--===============================================================================================-->
</head>
<link rel="stylesheet" type="text/css" href="css/list.css">
<!--===============================================================================================-->
</head>
<body>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.8/jquery.min.js"></script>

	<script
		src="//cdnjs.cloudflare.com/ajax/libs/list.js/1.5.0/list.min.js"></script>

	<div align="center" id="user">
		<h1 style="color: white;">Users</h1>
		<br> <br>
		<table style="width: 75%">
			<thead>
				<tr>
					<th class="sort" data-sort="name">Name</th>
					<th class="sort" data-sort="surname">surname</th>
					<th class="sort" data-sort="mail">mail</th>
					<th colspan="2"><input type="text" class="search"
						placeholder="Search contact" /></th>
				</tr>
			</thead>
			<tbody class="list">${users}
			</tbody>
		</table>
		<form action="test" method="post">
			<div align="center">
				<table>
					<td class="name"><input type="hidden" name="idfield"
						id="id-field" /> <input type="text" id="name-field"
						name="namefield" placeholder="Name" /></td>
					<td class="surname"><input type="text" name="surnamefield"
						id="surname-field" placeholder="surname" /></td>
					<td class="mail"><input type="text" name="mailfield"
						id="mail-field" placeholder="mail" /></td>
					<td class="add">
						<button id="add-btn" name="add" method="post">Add</button>
						<button id="edit-btn" name="editfield" method="post">Edit</button>
					</td>

				</table>
			</div>
		</form>
	</div>








	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.8/jquery.min.js"></script>
	<script src="js/list.js"></script>

	<script src="js/provalist.js"></script>
</body>
</html>

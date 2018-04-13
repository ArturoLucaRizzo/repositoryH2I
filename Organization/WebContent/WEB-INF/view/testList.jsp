<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="it.h2i.idservice.accountablemodel.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
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
<link rel="stylesheet" type="text/css" href="css/list.css">
<!--===============================================================================================-->
</head>
<body>
	<div align="center" id="user">
		<h1 style="color: white;">Users</h1>
		<br> <br>
		<thead>
			<tr>
				<table style="width: 75%">
					<th class="sort" data-sort="name" style="color: white;">Name</th>
					<th class="sort" data-sort="surname" style="color: white;">Surname</th>
					<th class="sort" data-sort="mail" style="color: white;">Mail</th>
					<th class="sort" data-sort="Enabled" style="color: white;">Enabled</th>
					<th colspan="2"><input type="text" class="search"
						placeholder="Search User" /></th>
					</tr>
					</thead>
					<tbody class="list">${users}
					</thead>
				</table>
				<table>
					<td class="name"><input type="hidden" id="id-field" /> <input
						type="text" id="name-field" placeholder="Name" /></td>
					<td class="surname"><input type="text" id="surname-field"
						placeholder="Surname" /></td>
					<td class="mail"><input type="text" id="mail-field"
						placeholder="Mail" /></td>
					<td class="add">
					<td class="enable"><input type="text" id="enable-field"
						placeholder="enable" /></td>
					<td class="add">
						<button id="add-btn">Add</button>
						<button id="edit-btn">Edit</button>
					</td>
				</table>
	</div>









	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.8/jquery.min.js"></script>
	<script
		src="//cdnjs.cloudflare.com/ajax/libs/list.js/1.5.0/list.min.js"></script>

	<script src="js/provalist.js"></script>
	<script src="js/list.js"></script>
	<!--===============================================================================================-->
	<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
	<!--===============================================================================================-->
	<script src="vendor/animsition/js/animsition.min.js"></script>
	<!--===============================================================================================-->
	<script src="vendor/bootstrap/js/popper.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
	<!--===============================================================================================-->
	<script src="vendor/select2/select2.min.js"></script>
	<!--===============================================================================================-->
	<script src="vendor/daterangepicker/moment.min.js"></script>
	<script src="vendor/daterangepicker/daterangepicker.js"></script>
	<!--===============================================================================================-->
	<script src="vendor/countdowntime/countdowntime.js"></script>
	<!--===============================================================================================-->
	<script src="js/main.js"></script>
	<script src="js/list.js"></script>

</body>
</html>

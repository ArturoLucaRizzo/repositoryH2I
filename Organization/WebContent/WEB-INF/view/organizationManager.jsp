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
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!--===============================================================================================-->
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css" href="css/style.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css" href="css/list.css">
<!--===============================================================================================-->
</head>
<body>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<div align="center" id="organizations">
		<h1 id="title" style="color: white;">Organizations</h1>
		<br> <br>
		<div id="box"></div>
		<br> <br> <br> <br>
		</table>
		<br> <br> <br> <br>
		<div class="modal fade" id="myModalRemove" role="dialog">
			<div class="modal-dialog modal-sm">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Model Remove</h4>
					</div>
					<div class="modal-body" id="message"></div>
					<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>

				</div>

			</div>
		</div>


		<script src="js/forHelper.js"></script>
</body>
</html>
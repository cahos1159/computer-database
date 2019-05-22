<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="AppCdb/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="AppCdb/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="AppCdb/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="${pageContext.request.contextPath}">
				Application - Computer Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id: ${id}</div>
					<h1>Edit Computer</h1>

					<form action="${pageContext.request.contextPath}/computer-edit?id=${id}" method="POST">
						<input type="hidden" value="" id="id" />
						<!-- TODO: Change this value with the computer id -->

						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer Name</label> <input
									type="text" class="form-control" name="name"id="computerName"
									value="${name}"}>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" class="form-control" name="introduced" id="introduced"
									value="${intro}">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control" name="discontinued" id="discontinued"
									value="${disc}">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="companyId"">
									<c:forEach items="${company}" var="company">
										<c:choose>
											<c:when test="${company.name==compName}">
											<option value="${company.id}" selected>${company.name}</option>
											</c:when>
											<c:otherwise>
											<option value="${company.id}">${company.name}</option>
											</c:otherwise>
										</c:choose>
										
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" id="btnSubmit"class="btn btn-primary">
							or <a href="${pageContext.request.contextPath}" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<script src="AppCdb/js/jquery.min.js"></script>
    <script src="AppCdb/js/valideDate.js"></script>
</body>
</html>
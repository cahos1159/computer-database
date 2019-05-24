<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
				<spring:message code="main.header" text="Application - Computer Database" /> </a>
		</div>
	</header>
	<section id="main">
	<div class="container text-left">
    	<a class="btn btn-default" href="${pageContext.request.contextPath}?lang=FR" >FR</a>
    	<a class="btn btn-default" href="${pageContext.request.contextPath}?lang=EN" >EN</a>
    	</div>
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
								<label for="computerName"><spring:message code="main.table.col1" text="Computer name" /></label> <input
									type="text" class="form-control" name="name"id="computerName"
									value="${name}"}>
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message code="main.table.col2" text="Introduced date" /></label> <input
									type="date" class="form-control" name="introduced" id="introduced"
									value="${intro}">
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message code="main.table.col3" text="Discontinued date" /></label> <input
									type="date" class="form-control" name="discontinued" id="discontinued"
									value="${disc}">
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message code="main.table.col4" text="Company" /></label> <select
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
							<input type="submit" value="<spring:message code="main.button.edit" text="Edit" />" id="btnSubmit"class="btn btn-primary">
							or <a href="${pageContext.request.contextPath}" class="btn btn-default"><spring:message code="main.button.cancel" text="Cancel" /></a>
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
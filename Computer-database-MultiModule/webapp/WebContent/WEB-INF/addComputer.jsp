<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="AppCdb/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="AppCdb/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="AppCdb/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}"> <spring:message code="main.header" text="Application - Computer Database" /> </a>
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
                    <h1 name="titre"><spring:message code="computer.add.title" text="Add new Computer" /></h1>
                    <form action="${pageContext.request.contextPath}/computer-add" method="POST">
                    <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="main.table.col1" text="Computer name" /></label>
                                <input type="text" class="form-control" id="computerName" name="computerName" placeholder="Computer name">
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="main.table.col2" text="Introduced date" /></label>
                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduced date">
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="main.table.col3" text="Discontinued date" /></label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date">
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="main.table.col4" text="Company" /></label>
                                <select class="form-control" id="companyId" name="companyId" >
                                	<c:forEach items="${company}" var="company">
                                    <option value="${company.id}">${company.name}</option>
                                    </c:forEach>
                                </select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="<spring:message code="main.button.add" text="Add" />" id="btnSubmit" class="btn btn-primary">
                            or
                            <a href="${pageContext.request.contextPath}" class="btn btn-default"><spring:message code="main.button.cancel" text="Cancel" /></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <script src="../AppCdb/js/jquery.min.js"></script>
    <script src="../AppCdb/js/valideDate.js"></script>
</body>
</html>
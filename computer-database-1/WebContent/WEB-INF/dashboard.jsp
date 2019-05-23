<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            <a class="navbar-brand" href="${pageContext.request.contextPath}"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                ${numberOfComputer} Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Computer name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="${pageContext.request.contextPath}/computer-add">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="${pageContext.request.contextPath}/" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            Computer name
                            <a <c:url value="/" var="url"><c:param name="colonne" value="name"/><c:param name="mode" value="0"/></c:url> href="${url}" ><i class="fa fa-long-arrow-up" aria-hidden="false"></i></a>
                           	<a <c:url value="/" var="url"><c:param name="colonne" value="name"/><c:param name="mode" value="1"/></c:url> href="${url}" ><i class="fa fa-long-arrow-down" aria-hidden="false"></i></a>
                        </th>
                        <th>
                            Introduced date
                             <a <c:url value="/" var="url"><c:param name="colonne" value="introduced"/><c:param name="mode" value="0"/></c:url> href="${url}" ><i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                             <a  <c:url value="/" var="url"><c:param name="colonne" value="introduced"/><c:param name="mode" value="1"/></c:url> href="${url}" ><i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                             <a<c:url value="/" var="url"><c:param name="colonne" value="discontinued"/><c:param name="mode" value="0"/></c:url> href="${url}" ><i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                             <a <c:url value="/" var="url"><c:param name="colonne" value="discontinued"/><c:param name="mode" value="1"/></c:url> href="${url}" ><i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                             <a <c:url value="/" var="url"><c:param name="colonne" value="company_id"/><c:param name="mode" value="0"/></c:url> href="${url}" ><i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                             <a <c:url value="/" var="url"><c:param name="colonne" value="company_id"/><c:param name="mode" value="1"/></c:url> href="${url}" ><i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                	<c:forEach items="${ordi}" var="computer">
                	
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="${computer.id}">
                        </td>
                        
                        <td>
                            <a href="${pageContext.request.contextPath}/computer-edit?id=${computer.id}" onclick="" >${computer.name}</a>
                        </td>
                        
    						
						
                        <td> ${computer.introduction}</td>
                        <td> ${computer.discontinued}</td>	
                        <td> ${computer.companyName}</td>
                    </tr>
					</c:forEach>
              
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
                <li>
                    <a <c:url value="/" var="url"><c:param name="numero" value="${page.numero - 1}"/></c:url>href="${url}" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                  </a>
              </li>
              <li><a  <c:url value="/" var="url"><c:param name="numero" value="${page.numero}"/></c:url>href="${url}">${page.numero}</a></li>
              <li>
                <a  <c:url value="/" var="url"><c:param name="numero" value="${page.numero + 1}"/></c:url>href="${url}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>

        <div class="btn-group btn-group-sm pull-right" role="group" >
            <a class="btn btn-default"  <c:url value="/" var="url"><c:param name="nbOrdiPage" value="10"/></c:url>href="${url}"  >10</a>
            <a class="btn btn-default"  <c:url value="/" var="url"><c:param name="nbOrdiPage" value="50"/></c:url>href="${url}" >50</a>
            <a class="btn btn-default" <c:url value="/" var="url"><c:param name="nbOrdiPage" value="100"/></c:url>href="${url}" >100</a>
        </div>
		</div>
    </footer>
<script src="AppCdb/js/jquery.min.js"></script>
<script src="AppCdb/js/bootstrap.min.js"></script>
<script src="AppCdb/js/dashboard.js"></script>

</body>
</html>
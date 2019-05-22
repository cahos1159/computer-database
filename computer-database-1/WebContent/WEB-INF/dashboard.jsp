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

        <form id="deleteForm" action="${pageContext.request.contextPath}" method="POST">
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
                            <a  href="?mode=0&colonne=name&search=${search}" ><i class="fa fa-long-arrow-up" aria-hidden="false"></i></a>
                           	<a  href="?mode=1&colonne=name&search=${search}" ><i class="fa fa-long-arrow-down" aria-hidden="false"></i></a>
                        </th>
                        <th>
                            Introduced date
                             <a  href="?mode=0&colonne=introduced&search=${search}" ><i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                             <a  href="?mode=1&colonne=introduced&search=${search}" ><i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                             <a  href="?mode=0&colonne=discontinued&search=${search}" ><i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                             <a  href="?mode=1&colonne=discontinued&search=${search}" ><i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                             <a  href="?mode=0&colonne=company_id&search=${search}" ><i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                             <a  href="?mode=1&colonne=company_id&search=${search}" ><i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
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
                        <td> ${computer.company}</td>
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
                    <a href="?page=${previous}&search=${search}&mode=${mode}&colonne=${colonne}" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                  </a>
              </li>
              <li><a href="?page=${page}">${page}</a></li>
              <li>
                <a href="?page=${next}&search=${search}&mode=${mode}&colonne=${colonne}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>

        <div class="btn-group btn-group-sm pull-right" role="group" >
            <a class="btn btn-default" href="?nbOrdiPage=10&search=${search}&mode=${mode}&colonne=${colonne}"  >10</a>
            <a class="btn btn-default"  href="?nbOrdiPage=50&search=${search}&mode=${mode}&colonne=${colonne}" >50</a>
            <a class="btn btn-default" href="?nbOrdiPage=100&search=${search}&mode=${mode}&colonne=${colonne}" >100</a>
        </div>
		</div>
    </footer>
<script src="AppCdb/js/jquery.min.js"></script>
<script src="AppCdb/js/bootstrap.min.js"></script>
<script src="AppCdb/js/dashboard.js"></script>

</body>
</html>
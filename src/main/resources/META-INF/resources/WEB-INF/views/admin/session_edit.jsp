<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript">
	function validateForm() {
		var date = document.getElementById("date").value;
		var date_format = /^(19|20)\d{2}\/(0?[1-9]|1[0-2])\/(0?[1-9]|1\d|2\d|3[01])$/;
		if (!date.match(date_format)) {
			alert("Date is invalid! it should be yyyy/mm/dd");
			return false;
		}
		return true;
	}
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Appointment Management System</title>

    <!-- Bootstrap -->
    <%--<link href="<c:url value='/css/bootstrap.min.css'/>" rel="stylesheet">  --%>
    
    <link href="/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="/css/font-awesome.min.css">
	<link href="/css/animate.min.css" rel="stylesheet">
    <link href="/css/prettyPhoto.css" rel="stylesheet">      
	<link href="/css/main.css" rel="stylesheet">
	 <link href="/css/responsive.css" rel="stylesheet">
	 <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->       
    
  </head>
<body>
	<%@ include file="../header.jsp"%>
	<section id="session">
	<div class="container">
	 <div class="row"> 
		<h2>edit session</h2>
		<h2>Information of Session Id : ${id}</h2>
	</div>
     <div class="row">   
	<form:form modelAttribute="sessionForUpdated" method="POST" class="contact-form"
		enctype="multipart/form-data"
		action="/admin/session_edit/${id}">
		<div class="col-sm-6">
			<div class="form-group">
				<label for="capacity">capacity</label>
				<form:input path="capacity" id="capacity" class="form-control"/>
				<form:errors path="capacity" cssStyle="Color:RED"></form:errors>
			</div>

			<div class="form-group">
				<label for="counselor">counselor</label>
				<form:select path="counselor" id="counselor" class="form-control">
					<form:option value="${sessionForUpdated.counselor.id}"
						label="${sessionForUpdated.counselor.firstName}" />
					<form:options path="counselor" items="${counselors}" />
				</form:select>
				
				<form:errors path="counselor" cssStyle="Color:RED"></form:errors>
			</div>

			<div class="form-group">
				<label for="date">Date</label>
				 <div class='input-group date' id='datetimepicker1'>
					<form:input path="date" id="date" class="form-control"/>
					<span class="input-group-addon">
	                    <span class="glyphicon glyphicon-calendar"></span>
	                </span>
	              </div>
				<form:errors path="date" cssStyle="Color:RED"></form:errors>
			</div>
			 
			<div class="form-group">
				<label for="time">Start Time</label>
				<form:select path="time" id="time" itemValue="time" itemLabel="time" class="form-control">
					<form:option value="${sessionForUpdated.time}"
						label="${sessionForUpdated.time}" />
					<form:options path="time" items="${times}" />
				</form:select>
				<form:errors path="time" cssStyle="Color:RED"></form:errors>
			</div>

			<div class="form-group">
				<label for="location">Location</label>
				<form:select path="location" id="location" itemValue="location"
					itemLabel="location" class="form-control">
					<form:option value="${sessionForUpdated.location.id}"
						label="${sessionForUpdated.location.name}" />
					<form:options path="location" items="${locations}" />
				</form:select>
				<form:errors path="location" cssStyle="Color:RED"></form:errors>
			</div>

			<div class="form-group">
				<label for="duration">Duration</label>
				<form:input path="duration" id="duration" class="form-control"/>
				<form:errors path="duration" cssStyle="Color:RED"></form:errors>
			</div>
			

			<div class="form-group" id="buttons">
				<td><form:button id="btnSubmit" type="submit" onclick="return validateForm();" class="btn btn-primary btn-lg">Update</form:button></td>
			</div>

		</div>
	</form:form>
	</div>
	</div>
	</section>
	<%@ include file="../footer.jsp"%>
	
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="/js/jquery.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/jquery.prettyPhoto.js"></script>
    <script src="/js/jquery.isotope.min.js"></script>   
    <script src="/js/wow.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment.min.js"></script>                       
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
	<script src="/js/main.js"></script>
	<script type="text/javascript">
            $(function () {
                $('#datetimepicker1').datetimepicker({
                	format: 'YYYY/MM/DD',
                	minDate:new Date(),
                });
            });
        </script>
  </body>
</html>
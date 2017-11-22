<header id="header">
        <nav class="navbar navbar-fixed-top" role="banner">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="index.html">TM</a>
                </div>
				
                <div class="collapse navbar-collapse navbar-right">
                    <ul class="nav navbar-nav">

                        <li class="active"><a href="/">Home</a></li>
                        <!-- <li><a href="/about-us">About Us</a></li> -->
                        
                        <sec:authorize access="hasRole('ROLE_CUSTOMER') and isAuthenticated()">
                        	<li><a href="/customer/createappointment">Book Appointment</a></li>
                        	<li><a href="/customer/listappointment">Appointments</a></li>
                        </sec:authorize>
						
						<sec:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
							<li><a href="/admin/sessions">Sessions</a></li>
                        	<li><a href="/admin/appointmentManage">Book Appointment</a></li>
                        	<li><a href="/admin/appointmentDelete">Appointments</a></li>
                        </sec:authorize>
                        
						<c:choose>
						    <c:when test="${pageContext.request.userPrincipal.name==null}">
						        <li><a href="/login">Login</a></li>
						    </c:when>    
						    <c:otherwise>
						    	<li><a onclick="document.forms['logoutForm'].submit()">Logout</a></li>
						        <form id="logoutForm" method="POST" action="${contextPath}/logout">
						            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						        </form>
						    </c:otherwise>
						</c:choose>
                    </ul>
                    <br/>
                    <c:if test="${pageContext.request.userPrincipal.name != null}">
                    	<h4 style="color: white;" align="right">Welcome [<sec:authentication property="principal.username"/>]</h4>
                    </c:if>
                </div>

            </div><!--/.container-->
        </nav><!--/nav-->
		
    </header><!--/header-->
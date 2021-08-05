<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<%@ include file="head.jsp"%>
<body style="overflow-y: scroll">

	<%@include file="header.jsp"%>

	<!-- The flexible grid (content) -->
	<div class="row">

		<%
		if (request.getAttribute("login") != null) {
		%>
		<jsp:include page="nav.jsp" />
		<%
		} else {
		%>
		<jsp:include page="services.jsp" />
		<%
		}
		%>
	
		<div class="col-md-8 col-lg-7">
			<div class="container-fulid">
			
				<%if(request.getAttribute("customer")!=null){ %>
					<div class="col-lg-12" id="logout">
						<p><a class="btn btn-primary active">${customer.firstName} ${customer.lastName}</a> <a href="/finalproject_grouph/logout" class="btn btn-danger">Logout</a></p>
					</div>
					<hr>
				<%} %>
				
				<c:forEach var="view" items="${viewList}">
					<jsp:include page="${view}" />
				</c:forEach>
			</div>
		</div>

	</div>
	
	<%@ include file="footer.jsp"%>

</body>
</html>

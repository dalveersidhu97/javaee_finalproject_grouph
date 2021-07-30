<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<!DOCTYPE html>
<html>
<%@ include file="head.jsp" %>  
<body>

<%@include file="header.jsp" %> 



<!-- The flexible grid (content) -->
<div class="row" id="main_section">
	
	<% 
		if(request.getAttribute("login")!=null){
	%>
	<jsp:include page="nav.jsp" />
	<%
		}else{
	%>
	<jsp:include page="services.jsp" />
	<%		
		}
	%>

	
	
	  <div class="col-sm-8 col-lg-7">
	  <div class="col-sm-12 col-lg-12" id="container">
	     <c:forEach var = "view" items="${viewList}">
	        <jsp:include page="${view}" />
	     </c:forEach>
	     </div>
	  </div>
	  
</div>

<%@ include file="footer.jsp" %>

</body>
</html>

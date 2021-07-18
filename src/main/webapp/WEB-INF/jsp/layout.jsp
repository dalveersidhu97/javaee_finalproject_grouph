<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<!DOCTYPE html>
<html>
<%@ include file="head.jsp" %>  
<body>

<%@include file="header.jsp" %> 

<jsp:include page="nav.jsp" />

<!-- The flexible grid (content) -->
<div class="row">

  <div class="main">
  
     <c:forEach var = "view" items="${viewList}">
        <jsp:include page="${view}" />
     </c:forEach>
     
  </div>
</div>

<%@ include file="footer.jsp" %>

</body>
</html>

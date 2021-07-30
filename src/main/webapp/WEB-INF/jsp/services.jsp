<%@ page import="com.banking.beans.*, java.util.List" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 


<!-- Navigation Bar -->
<div class="col-sm-9 col-lg-2" id="container">
	<div class="list-group list-group-flush">
	
	<h3  class='list-group-item list-group-item-action'>Services</h3>
	
	<a href='/finalproject_grouph'  class='list-group-item list-group-item-action'>Home</a>
	<a href='/finalproject_grouph/transfer/self'  class='list-group-item list-group-item-action'>Self transfer</a>
	<a href='/finalproject_grouph/transfer/by-email'  class='list-group-item list-group-item-action'>Transfer by Email</a>

	<%
		for(UtilityCategory uc : (List<UtilityCategory>)request.getAttribute("categoriesList")){
			String category = uc.getName();
			String slug = String.join("-", category.split(" "));
			
			out.print("<a href='/finalproject_grouph/categories/"+slug+"' class='list-group-item list-group-item-action'>"+category+"</a>");
			
		}
	%>
	</div>
</div>

<%@ page import="com.banking.beans.*, java.util.List" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

	
<h2>Accounts</h2>
	
	<%
	
		for(Account ac : (List<Account>)request.getAttribute("accountsList")){
			String accountType = ac.getType();
			float balance = ac.getBalance();
			
			out.print("<p>"+accountType+" account - $"+balance+"</p>");
			
		}
	%>
	<br>
<hr><br>
<h2>Transaction Categories</h2>

	<%
	
		for(UtilityCategory uc : (List<UtilityCategory>)request.getAttribute("categoriesList")){
			String category = uc.getName();
			String slug = String.join("-", category.split(" "));
			
			out.print("<h2><a href='./categories/"+slug+"'>"+category+"</a></h2>");
			
		}
	%>



    
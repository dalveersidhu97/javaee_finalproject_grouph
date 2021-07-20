<%@ page import="com.banking.beans.*, java.util.List" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<p>${customer.firstName} ${customer.lastName}</p>
<p><a href="./logout">Logout</a></p>

<br><hr><br>	
<h2>Accounts</h2>
	
	<%
	
		for(Account ac : (List<Account>)request.getAttribute("accountsList")){
			String accountType = ac.getType();
			
			float balance = ac.getBalance();
			out.print("<p>"+accountType+" ("+ac.getId()+")"+" account - $"+balance+"</p>");
			
		}
	%>
	<br>
<hr><br>
<h2>Services</h2>
	
	<h2><a href='./transfer/self'>Self transfer</a></h2>

	<%
		for(UtilityCategory uc : (List<UtilityCategory>)request.getAttribute("categoriesList")){
			String category = uc.getName();
			String slug = String.join("-", category.split(" "));
			
			out.print("<h2><a href='./categories/"+slug+"'>"+category+"</a></h2>");
			
		}
	%>
<br><br><br>

    
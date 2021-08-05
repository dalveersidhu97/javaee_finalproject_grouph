<%@ page import="com.banking.beans.*, java.util.List" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<hr>
<center>	
<h2>Accounts</h2><br>
	
	<%
	
		for(Account ac : (List<Account>)request.getAttribute("accountsList")){
			String accountType = ac.getType();
			
			float balance = ac.getBalance();
			out.print("<p>"+accountType+" ("+ac.getId()+")"+" account - $"+balance+"</p>");
			
		}
	%>
	<br>
</center>

    
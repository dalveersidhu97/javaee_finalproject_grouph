<%@ page import="com.banking.beans.*, java.util.List" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<h2>Transactions</h2>
<br>
<table class="table">
	<tr>
		<th>Reference ID</th>
		<th>Remark</th>
		<th>Date</th>
		<th>Amount</th>
		<th>Status</th>
		<th>Action</th>
	</tr>
	<%
		List<Transaction> transactionsList = (List<Transaction>)request.getAttribute("tranactionsList");
	
		for(Transaction t: transactionsList){
			
			
			
			out.print("<tr>");
				out.print("<td>"+t.getId()+"</td>");
				out.print("<td>"+t.getRemark()+"</td>");
				out.print("<td>"+t.getCommitDate()+"</td>");
				out.print("<td>"+t.getAmount()+"</td>");
				out.print("<td>"+t.getStatus()+"</td>");
				out.print("<td><a href='/finalproject_grouph/transaction-details/"+t.getId()+"'>Details</a></td>");
			out.print("</tr>");
		}
	%>
</table>
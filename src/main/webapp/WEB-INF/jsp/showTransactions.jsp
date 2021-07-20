<%@ page import="com.banking.beans.*, java.util.List" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<h2>Transactions</h2>

<table style="border:1px solid black;border-collapse:collaplse;text-align:center;">
	<tr>
		<th>Reference ID</th>
		<th>Remark</th>
		<th>Date</th>
		<th>Amount</th>
		<th>Status</th>
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
			out.print("</tr>");
		}
	%>
</table>
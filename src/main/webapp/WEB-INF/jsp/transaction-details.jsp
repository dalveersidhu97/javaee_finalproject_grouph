<%@ page import="com.banking.beans.*, java.util.List"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<center>
	<h2>Transaction details</h2>
	<br>
	<table class="table-transaction-details">
		<tbody>

			<%
			Transaction t = (Transaction) request.getAttribute("transaction");
			Account fromAc = (Account) request.getAttribute("fromAccount");
			%>

				<tr>
					<th class="text-right">Reference ID</th>
					<td><%=t.getId()%></td>
				</tr>
				<tr>
					<th class="text-right">From account</th>
					<td><%=fromAc.getType()+" ("+t.getFromAccountId()+")"%></td>
				</tr>
				<tr>
					<th class="text-right">Remark</th>
					<td><%=t.getRemark()%></td>
				</tr>
				<tr>
					<th class="text-right">Date</th>
					<td><%=t.getCommitDate()%></td>
				</tr>
				<tr>
					<th class="text-right">Amount</th>
					<td><%=t.getAmount()%></td>
				</tr>

			<%
			for (Transaction.TransactionValue tv : t.getTransactionValues()) {
			%>

				<tr>
					<th class="text-right"><%=tv.getOptionTitle()%></th>
					<td><%=tv.getOptionValue()%></td>
				</tr>

			<%
			}
			%>
			
				<tr>
					<th class="text-right">Status</th>
					<td><%=t.getStatus()%></td>
				</tr>

		</tbody>
	</table>
	<br>
</center>


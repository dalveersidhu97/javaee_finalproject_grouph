<%@ page import="com.banking.beans.*, com.banking.beans.Transaction.*, java.util.List" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<%
		Transaction t = (Transaction)session.getAttribute("transaction");
	%>
	<table>
		<tr><th>Refrence Id</th><td><%=t.getId()%></td></tr>
		<tr><th>Amount</th><td><%=t.getAmount()%></td></tr>
		<tr><th>Remark</th><td><%=t.getRemark()%></td></tr>
	<%
		for(TransactionValue tv : t.getTransactionValues()){
			out.print("<tr><th>Refrence Id</th><td>"+tv.getOptionValue()+"</td></tr>");
		}
	%>
	</table>
</body>
</html>
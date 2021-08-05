<%@ page import="com.banking.beans.*, com.banking.beans.Transaction.*, com.banking.service.*, org.springframework.beans.factory.annotation.Autowired, java.util.List" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

	<h2>Are the Below details correct?</h2><br>
	<%
		WithinBankTransaction t = (WithinBankTransaction)session.getAttribute("transaction");
		String redirect = (String)request.getAttribute("redirect");
		CustomerService cService= (CustomerService)request.getAttribute("customerServiceObject");
		
		Customer receiver = cService.getCustomerFromAccountId(t.getToAccountId());
		
		String name = receiver.getFirstName().toUpperCase() +" "+ receiver.getLastName().toUpperCase();
		
	%>
	
	<table class="table">
		<tbody>
			<tr>
				<th class='text-right'>Refrence Id</th>
				<td><%=t.getId()%></td>
			</tr>
			<tr><th class='text-right col-6'>Amount</th><td><%=t.getAmount()%></td></tr>
			<tr><th class='text-right'>Remark</th><td><%=t.getRemark()%></td></tr>
			<tr><th class='text-right'>Receiver name</th><td><%=name%></td></tr>
			<tr><th class='text-right'>Receiver email</th><td><%=receiver.getEmail().toUpperCase()%></td></tr>
		</tbody>
	</table>
	<a class="btn btn-primary container" href="<%=redirect%>">confirm</a>
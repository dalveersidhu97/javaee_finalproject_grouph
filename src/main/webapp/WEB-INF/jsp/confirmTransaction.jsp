<%@ page import="com.banking.beans.*, com.banking.beans.Transaction.*, com.banking.service.*, org.springframework.beans.factory.annotation.Autowired, java.util.List" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

	<h2>Are the Below details correct?</h2><br>
	<%
		Transaction t = (Transaction)session.getAttribute("transaction");
		String redirect = (String)request.getAttribute("redirect");
		UtilityService uService= (UtilityService)request.getAttribute("utilityServiceObject");
		
		List<TransactionValue> valuesList = t.getTransactionValues();
		String values = "";
		if(valuesList!=null){
			if(valuesList.size()!=0){
				for(TransactionValue v : valuesList){
					values += "<tr><th class='text-right'>"+uService.getCategoryOption(v.getOptionId()).getTitle()+"<th></td>"+v.getOptionValue()+"</tr>";
				}	
			}
		}
		
	%>
	<table class="table">
		<tr><th class='text-right col-6'>Refrence Id</th><td><%=t.getId()%></td></tr>
		<tr><th class='text-right'>Amount</th><td><%=t.getAmount()%></td></tr>
		<tr><th class='text-right'>Remark</th><td><%=t.getRemark()%></td></tr>
		<%=values %>
	</table>
	<a class="btn btn-primary container" href="<%=redirect%>">confirm</a>
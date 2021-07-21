
<%@ page import="com.banking.beans.*, java.util.List" %>
	<div>
		<label>From account</label>
		<select name="accountId" id="accountId">
		  <option value="">- select from account</option>
	<%
	for(Account ac : (List<Account>)request.getAttribute("accountsList")){
		String account = ac.getType()+"-"+ac.getId()+" (" + ac.getBalance()+")";
		out.print("<option value='"+ac.getId()+"'>"+account);
	}
	out.print("</option></select></div>");
	%>
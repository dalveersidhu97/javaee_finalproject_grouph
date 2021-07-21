<%@ page import="com.banking.beans.*, java.util.List" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<h2>Email transfer</h2>

<form class="form" id="regForm" action="../emailTransferProcess" method="post">
	<div>
		<label>From account</label>
		<select name="accountId" id="accountId">
		  <option value="">- select from account</option>
	<%
	for(Account ac : (List<Account>)request.getAttribute("accountsList")){
		String account = ac.getId()+" (" + ac.getBalance()+")";
		out.print("<option value='"+ac.getId()+"'>"+account);
	}
	out.print("</option></select></div>");
	%>

	<div>
		<label>Enter email Id or account number of the receiver</label>
		<input type="text" name="eamilOrAccountId" id="eamil" required placeholder="Enter email or account number"/>
	</div>

	<div>
		<label>Amount</label>
		<input path="amount" name="amount" id="amount" placeholder="Enter amount"/>
	</div>
	
	<div>
		<label>Remark</label>
		<input name="remark" id="remark" placeholder="Enter remark"/>
	</div>
	<div>
		<input type="submit" id="submit" value="Submit">
	</div>
</form>

<% 
	if(request.getParameter("errorMessage")!=null)
		out.print(request.getParameter("errorMessage"));
%>
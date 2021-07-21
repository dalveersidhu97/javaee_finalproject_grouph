<%@ page import="com.banking.beans.*, java.util.List" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<h2>Self transfer</h2>

<form class="form" id="regForm" action="../selfTransferProcess" method="post">

	<jsp:include page="fromAccount.jsp"></jsp:include>
	
	<div>
		<label>To account</label>
		<select name="toAccountId" id="toAccountId">
		  <option value="">- select to account</option>
	<%
	for(Account ac : (List<Account>)request.getAttribute("accountsList")){
		String account = ac.getType()+"-"+ac.getId()+" (" + ac.getBalance()+")";
		out.print("<option value='"+ac.getId()+"'>"+account);
	}
	out.print("</option></select></div>");
	%>

	<div>
		<label>Amount</label>
		<input path="amount" name="amount" id="amount" placeholder="Enter amount"/>
	</div>
	
	<input type='hidden' name='remark' value='Self transfer'/>
	<div>
		<input type="submit" id="submit" value="Submit">
	</div>
</form>

<% 
	if(request.getParameter("errorMessage")!=null)
		out.print(request.getParameter("errorMessage"));
%>
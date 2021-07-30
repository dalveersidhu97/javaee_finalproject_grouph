<%@ page import="com.banking.beans.*, java.util.List" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<h2>Email transfer</h2>

<form class="form" id="regForm" action="../emailTransferProcess" method="post">
		  
 	<jsp:include page="fromAccount.jsp"></jsp:include>

	<div class="form-group">
		<label>Enter email Id or account number of the receiver</label>
		<input class="form-control" type="text" name="eamilOrAccountId" id="eamil" required placeholder="Enter email or account number"/>
	</div>

	<div class="form-group">
		<label>Amount</label>
		<input class="form-control" path="amount" name="amount" id="amount" placeholder="Enter amount"/>
	</div>
	
	<div class="form-group">
		<label>Remark</label>
		<input class="form-control" name="remark" id="remark" placeholder="Enter remark"/>
	</div>
	<div>
		<input class="form-control btn btn-primary" type="submit" id="submit" value="Submit">
	</div>
</form>

<% 
	if(request.getParameter("errorMessage")!=null)
		out.print(request.getParameter("errorMessage"));
%>
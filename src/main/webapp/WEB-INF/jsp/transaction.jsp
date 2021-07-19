<%@ page import="com.banking.beans.*, java.util.List" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<h2>${categoryName}</h2>

<form class="form" id="regForm" action="../transactionProcess" method="post">
	<div>
		<label>From account</label>
		<select name="accountId" id="fromAccount">
		  <option value="">- select account</option>
<%
	for(Account ac : (List<Account>)request.getAttribute("accountsList")){
		String account = ac.getNumber().substring(ac.getNumber().length() - 5)+" (" + ac.getBalance()+")";
		out.print("<option value='"+ac.getId()+"'>"+account+"</option>");
	}
%>
		</select>
	</div>
	
	
<%
	for(CategoryOption op : (List<CategoryOption>)request.getAttribute("optionsList")){
%>

	<div>
		<label for="<%=op.getInputName()%>"><%=op.getTitle()%></label>
		<input name="<%=op.getInputName()+"-"+op.getId()%>" id="<%=op.getInputName()%>" type="<%=op.getInputType()%>" placeholder="Enter <%=op.getTitle().toLowerCase()%>" />
	</div>
<%
	
	}
		
%>
	<input type="hidden" name="categoryName", value="${categoryName}"/>
	
	<div>
		<label path="amount">Amount</label>
		<input path="amount" name="amount" id="amount" placeholder="Enter amount"/>
	</div>
	<div>
		<input type="submit" id="submit" value="Submit">
	</div>
</form>
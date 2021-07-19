<%@ page import="com.banking.beans.CategoryOption, java.util.List" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<h2>${categoryName}</h2>

<form class="form" id="regForm" action="registerProcess" method="post">

<%
	
	for(CategoryOption op : (List<CategoryOption>)request.getAttribute("optionsList")){
		
%>

	<div>
		<label for="<%=op.getInputName()%>"><%=op.getTitle()%></label>
		<input name="<%=op.getInputName()%>" id="<%=op.getInputName()%>" type="<%=op.getInputType()%>" placeholder="Enter <%=op.getTitle().toLowerCase()%>" />
	</div>

<%
	
	}
		
%>

	<div>
		<label path="amount">Amount</label>
		<input path="amount" name="amount" id="amount" placeholder="Enter amount"/>
	</div>
	<div>
		<input type="submit" id="submit" value="Submit">
	</div>
</form>
<%@ page import="com.banking.beans.*, java.util.List" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<h2>${categoryName}</h2>

<form class="form" id="regForm" action="../transactionProcess" method="post">

	<jsp:include page="fromAccount.jsp"></jsp:include>

	<%
	for(CategoryOption op : (List<CategoryOption>)request.getAttribute("optionsList")){
	%>
	<div class="form-group">
		<label for="<%=op.getInputName()%>"><%=op.getTitle()%></label>
		<input class="form-control" name="<%=op.getInputName()%>" id="<%=op.getInputName()%>" type="<%=op.getInputType()%>" placeholder="Enter <%=op.getTitle().toLowerCase()%>" />
	</div>
	<%
	}
	
	%>
	<div class="form-group">
		<label>Amount</label>
		<input class="form-control" path="amount" name="amount" id="amount" placeholder="Enter amount"/>
	</div>
	<%
	if(((String)request.getAttribute("categoryName")).equals("Bank Transfer")){
		out.print("<div class='form-group'><label>Remark</label><input class='form-control' type='text' name='remark' id='remark' placeholder='Enter remark'/></div>");
	}else{
		out.print("<input type='hidden' name='remark' value='"+(String)request.getAttribute("categoryName")+"'/>");
	}
	%>
	<input type="hidden" name="categoryName", value="${categoryName}"/>
	<div>
		<input class="form-control btn btn-primary" type="submit" id="submit" value="Submit">
	</div>
</form>

<% 
	if(request.getParameter("errorMessage")!=null)
		out.print(request.getParameter("errorMessage"));
%>
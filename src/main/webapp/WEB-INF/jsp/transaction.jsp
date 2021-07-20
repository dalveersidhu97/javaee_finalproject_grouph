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
		String account = ac.getId()+" (" + ac.getBalance()+")";
		out.print("<option value='"+ac.getId()+"'>"+account);
	}
	out.print("</option></select></div>");

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
		<label>Amount</label>
		<input path="amount" name="amount" id="amount" placeholder="Enter amount"/>
	</div>
	<%
	if(((String)request.getAttribute("categoryName")).equals("Bank Transfer")){
		out.print("<div><label>Remark</label><input type='text' name='remark' id='remark' placeholder='Enter remark'/></div>");
	}else{
		out.print("<input type='hidden' name='remark' value='"+(String)request.getAttribute("categoryName")+"'/>");
	}
	%>
	<input type="hidden" name="categoryName", value="${categoryName}"/>
	<div>
		<input type="submit" id="submit" value="Submit">
	</div>
</form>

<% 
	if(request.getParameter("errorMessage")!=null)
		out.print(request.getParameter("errorMessage"));
%>
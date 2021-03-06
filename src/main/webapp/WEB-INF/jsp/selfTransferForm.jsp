<%@ page import="com.banking.beans.*, java.util.List"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h2>Self transfer</h2>

<form class="form col-md-8 m-auto" id="regForm" action="../selfTransferProcess"
	method="post">

	<jsp:include page="fromAccount.jsp"></jsp:include>

	<div class="form-group">
		<label>To account</label> <select class="form-control"
			name="toAccountId" id="toAccountId">
			<option value="">- select to account</option>
			<%
			for (Account ac : (List<Account>) request.getAttribute("accountsList")) {
				String account = ac.getType() + "-" + ac.getId() + " (" + ac.getBalance() + ")";
				out.print("<option value='" + ac.getId() + "'>" + account);
			}
			out.print("</option></select></div>");
			%>

			<div class="form-group">
				<label>Amount</label> <input class="form-control" path="amount"
					name="amount" id="amount" placeholder="Enter amount" />
			</div>

			<input type='hidden' name='remark' value='Self transfer' />
			<div>
				<input class="form-control btn btn-primary" type="submit"
					id="submit" value="Submit">
			</div>
</form>
<br>
<%
if (request.getParameter("errorMessage") != null)
	out.print("<p class='text-center'>" + request.getParameter("errorMessage") + "</p>");
%>
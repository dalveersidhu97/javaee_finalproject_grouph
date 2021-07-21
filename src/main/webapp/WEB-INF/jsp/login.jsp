<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h2>Login</h2>
<form:form class="form" id="loginForm" modelAttribute="login"
	action="loginProcess" method="post">
	<div class="form-group">
		<form:label path="username"> Username: </form:label>
		<form:input path="username" class="form-control" name="username" id="username" />
	</div>
	<div class="form-group">
		<form:label path="password"> Password:</form:label>
		<form:password path="password" class="form-control" name="password" id="password" />
	</div>
	<div class="form-group">
		<form:button id="login" class="form-control" name="login">Login</form:button>
	</div>

	<div class="form-group">
		<form:errors path="username" />
		<form:errors path="password" />
	</div>

</form:form>
<br>
<center>${errorMessage}</center>
<center>${message}</center>


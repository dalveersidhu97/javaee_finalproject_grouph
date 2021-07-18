<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<h2>Sign up</h2>
<form:form class="form" id="regForm" modelAttribute="register"
	action="registerProcess" method="post">
	<div>
		<form:label path="username"> Username</form:label>
		<form:input path="username" name="username" id="username" />
	</div>
	<div>
		<form:label path="password"> Password</form:label>
		<form:password path="password" name="password" id="password" />
	</div>
	<div>
		<form:label path="firstName"> FirstName</form:label>
		<form:input path="firstName" name="firstName" id="firstname" />
	</div>
	<div>
		<form:label path="lastName"> LastName</form:label>
		<form:input path="lastName" name="lastName" id="lastname" />
	</div>
	<div>
		<form:label path="email"> Email</form:label>
		<form:input path="email" name="email" id="email" />
	</div>
	<div>
		<form:button id="register" name="register"> Register</form:button>
	</div>
	<form:errors path="username" />
	<form:errors path="password" />
	<form:errors path="firstName" />
	<form:errors path="lastName" />
	<form:errors path="email" />
	<br>
	<form:errors path="username" style="color:red;" />
</form:form>

<p>${errorMessage}</p>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<h2>Sign up</h2>
<form:form class="form col-md-8 m-auto" id="regForm" modelAttribute="register"
	action="registerProcess" method="post">

	<div class="form-group">
		<form:label path="username"> Username</form:label>
		<form:input class="form-control" path="username" name="username"
			id="username" />
	</div>

	<div class="form-group">
		<form:label path="password"> Password</form:label>
		<form:password class="form-control" path="password" name="password"
			id="password" />
	</div>

	<div class="form-group">
		<form:label path="firstName"> FirstName</form:label>
		<form:input class="form-control" path="firstName" name="firstName"
			id="firstname" />
	</div>

	<div class="form-group">
		<form:label path="lastName"> LastName</form:label>
		<form:input class="form-control" path="lastName" name="lastName"
			id="lastname" />
	</div>

	<div class="form-group">
		<form:label path="email"> Email</form:label>
		<form:input class="form-control" path="email" name="email" id="email" />
	</div>
	<div>
		<form:button class="form-control btn btn-primary" id="register"
			name="register"> Register</form:button>
	</div>
	<br>
	<form:errors path="*" style="color:red;" />
</form:form>

<p class="text-center" style="color:red;">${errorMessage}</p>
<p class="text-center">
	Already registered? <a href="./login">Login</a>
</p>

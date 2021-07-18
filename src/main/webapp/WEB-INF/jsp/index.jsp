<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<!DOCTYPE html>
<html>
<head>
<title>Page Title</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
* {
  box-sizing: border-box;
  margin: 0px;
}

/* Style the body */
body {
  font-family: Arial;
  margin: 0;
}

/* Header/logo Title */
.header {
  padding: 30px;
  text-align: center;
  background: #f44336;
  color: white;
}
.header p{padding: 0;padding-top: 5px;}

/* Style the top navigation bar */
.navbar {
  display: flex;
  background-color: #333;
}

/* Style the navigation bar links */
.navbar a {
  color: white;
  padding: 14px 20px;
  text-decoration: none;
  text-align: center;
}

/* Change color on hover */
.navbar a:hover {
  background-color: #ddd;
  color: black;
}

/* Column container */
.row {  
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

/* Main column */
.main {
  flex:80%;
  width: 80%;
  background-color: white;
  padding: 20px;
  background: #eee;
}


/* Footer */
.footer {
  padding: 20px;
  text-align: center;
  background: #ddd;
}

/* Responsive layout - when the screen is less than 700px wide, make the two columns stack on top of each other instead of next to each other */
@media screen and (max-width: 700px) {
  .row, .navbar {   
    flex-direction: column;
  }
}
</style>
</head>
<body>

<!-- Header -->
<div class="header">
  <h1>Online Banking</h1>
  <p>Safe and secure.</p>
</div>

<!-- Navigation Bar -->
<div class="navbar">
  <a href="#">Home</a>
  <a href="#">About</a>
  <a href="#">Contact</a>
  <a href="#">Help</a>
</div>

<!-- The flexible grid (content) -->
<div class="row">

  <div class="main">
  		<h2>Login</h2>
		<form:form class="form" id="loginForm" modelAttribute="login" action="loginProcess" method="post">
			<div>
				<form:label path="username"> Username: </form:label></td>
				<form:input path="username" name="username" id="username" />
			</div>
			<div>
				<form:label path="password"> Password:</form:label>
				<form:password path="password" name="password" id="password" />
			</div>
			<div>
				<form:button id="login" name="login">Login</form:button>
			</div>
		</form:form>
		<br>
		<center>${message}</center>
		
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
			<br>
			<form:errors path="username" style="color:red;"/>
		</form:form>
  </div>
</div>

<!-- Footer -->
<div class="footer">
  <h2>Footer</h2>
</div>

</body>
</html>

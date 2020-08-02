<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList" import="csci310.*"%>

<!DOCTYPE html>

<%
	//Disable Caching
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);

	if(session.getAttribute("uid") != null){
		response.sendRedirect("HomePage.jsp");
	}

	String error_message = (String) session.getAttribute("login_error_message");
	





%>

<html>
<head>
	<!-- <meta charset="UTF-8"> -->
	  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <link rel = "stylesheet" type = "text/css" href="HomePage.css">
  <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <script src="https://kit.fontawesome.com/a076d05399.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- 	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
 -->	
	<!-- Firebase Auth Testing -->
	<script src="https://cdn.firebase.com/libs/firebaseui/3.5.2/firebaseui.js"></script>
	<link type="text/css" rel="stylesheet" href="https://cdn.firebase.com/libs/firebaseui/3.5.2/firebaseui.css" />

	<title>Login</title>
<style>
#login_btn {
	margin: 20px;
	display: inline-block;
	width: 230px;
	font-size: 18px;
	font-family: Montserrat;
	 border-radius: 34px;
	 padding: 7px;
}
</style>
</head>

<body>	
	
	<div>
		<br><br><br>
	    <h1>Login</h1>
	    
		<%
			for(int i = 0; i < 3; i++){%>
			<br>
		<% 	} %>
						
 		<!--  <form action="/index.jsp">-->
		<div class="container">
		
			<%
				for(int i = 0; i < 3; i++){%>
				<br>
			<% 	} %>
			<div class="row">
				<div class="col">
					<h3><b>Log in using email and password.</b></h3>
				</div>
			</div>
						
			<br>
			
			<form action="/UserAuthServlet" method="post">
        	<div class="row">
	        	<div class="col-4">
	        	</div>
        		<div class="col">
					<h3>Email</h3>
        		</div>
        		<div class="col">
        			<input type="email" id="email" name="email">
        		</div>
        		<div class="col-4">
	        	</div>
        	</div>
        	
        	<br>
        	
        	<div class="row">
        		<div class="col-4">
	        	</div>
        		<div class="col">
        			<h3>Password</h3>
        		</div>      
        		<div class="col">
        			<input type="password" id="password" name="password">
        		</div>
        		<div class="col-4">
	        	</div>  	
        	</div>
        	
        	<br>
        	
        	<div class="row">
        		<div class="col">
        			<button id="login_btn">Login</button>
        		</div>
        	</div>
        	
        	<input type="hidden" name="action" value="login">
        	</form>
        	
        	
        	<div class="row">
				<div class="col">
					<div class="col">
						<strong id="login_error" style="color:red"><%if(error_message != null){ %> <%= error_message%> <% } %></strong>
					</div>
				</div>
			</div>
        	
        	
        	<div class="row">
        		<div class="col">
        			<a id="signup_button" href="/signup.jsp">Don't have an account yet? Sign up here!</a>
        		</div>
        	</div>
        </div>
        
 	</div>

 
</body>

</html>
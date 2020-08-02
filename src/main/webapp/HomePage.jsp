 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList" import="java.util.Map" import="csci310.*"%>
<!DOCTYPE html>
<%
	// Disable Caching
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);

	String user = (String) session.getAttribute("uid");
	if(user == null){
		response.sendRedirect("login.jsp");
	}
	String username = (String) session.getAttribute("username");
	// Initializing all values
	Boolean initialized = (Boolean) session.getAttribute("initializedHome");
	if (initialized == null){
		System.out.println("Initializing values");
		session.setAttribute("initializedHome", true);
		session.setAttribute("location", "");
		session.setAttribute("date", "");
		session.setAttribute("temp", 0.0);
		session.setAttribute("desc", "");
		session.setAttribute("img", "");
		session.setAttribute("prompter", "Search for a city's weather!");
		session.setAttribute("last_page", "");
		session.setAttribute("list_activity", new ArrayList<City>());
		session.setAttribute("list_vacation", new ArrayList<City>());
		
		ArrayList<ArrayList<String>> fav_data = new ArrayList<>();
		for(int i = 0; i < 23; i++){
			fav_data.add(new ArrayList<String>());
		}
		session.setAttribute("fav_data", fav_data);	
		session.setAttribute("city_selected", false);
		session.setAttribute("city_data", new ArrayList<String>());
		session.setAttribute("unit", true);
	}
	//session.setAttribute("prompter", "Search for a city's weather!");
	session.setAttribute("last_page", "HomePage.jsp");
	boolean unit_c = (boolean) session.getAttribute("unit");
	/* HttpSession session = request.getSession(); */
	String location = (String)session.getAttribute("location");
	System.out.println(location);
	String date = (String) session.getAttribute("date");
	session.setAttribute("validResp", "invalid");
	if(date != ""){
		String[] arrOfStr = date.split("/");
	    String year = arrOfStr[0];
	    String month = arrOfStr[1];
	    String day = arrOfStr[2];
	    
	    if (month.equals("01")){
	    	month = "January";
	    }
	    if (month.equals("02")){
	    	month = "February";
	    }
	    if (month.equals("03")){
	    	month = "March";
	    }
	    if (month.equals("04")){
	    	month = "April";
	    }
    	
	    if (month.equals("05")){
	    	month = "May";
	    }
	    if (month.equals("06")){
	    	month = "June";
	    }
	    if (month.equals("07")){
	    	month = "July";
	    }
	    if (month.equals("08")){
	    	month = "August";
	    }
	    if (month.equals("09")){
	    	month = "September";
	    }
	    if (month.equals("10")){
	    	month = "October";
	    }
	    if (month.equals("11")){
	    	month = "November";
	    }
	    else if (month.equals("12")){
	    	month = "December";
	    }
	    
	    date = month + " " + day +", " + year;
	}
	Double temp_double = ((Double) session.getAttribute("temp"));
	String temp;
	temp = temp_double.toString() + " ";
	String tempUnit = " ";
	if(!temp.equals("0.0"))
	{
		tempUnit = " °F";
	}
	
	String desc = (String) session.getAttribute("desc");
	String img = (String) session.getAttribute("img");
	String prompter = (String) session.getAttribute("prompter");
	//adding search history
	ArrayList< String > search = (ArrayList<String>)session.getAttribute("searchHistory");
	if(search == null){
		search = new ArrayList<String >();
	}
	
	Map<String, Integer> likes = (Map<String, Integer>) session.getAttribute("likes");
	if(likes == null){
		System.out.println("no likes map");
	}
%>

<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <link rel = "stylesheet" type = "text/css" href="HomePage.css">
  <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <script src="https://kit.fontawesome.com/a076d05399.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<title>HomePage</title>


<style>

.toggle {
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  width: 62px;
  height: 32px;
  display: inline-block;
  position: relative;
  border-radius: 50px;
  overflow: hidden;
  outline: none;
  border: none;
  cursor: pointer;
  background-color: Silver;
  transition: background-color ease 0.3s;
  
h2{
	color: DarkSlateGrey;
	font-family: Montserrat;
}

  
}

.toggle:before {
  content: "°C °F";
  display: block;
  position: absolute;
  z-index: 2;
  width: 28px;
  height: 28px;
  background: Snow;
  left: 2px;
  top: 2px;
  border-radius: 50%;
  font: 10px/28px Helvetica;
  font-size: 16px;
  text-transform: uppercase;
  text-indent: -24px;
  word-spacing: 37px;
  color: ;
  text-shadow: -1px -1px rgba(0,0,0,0.15);
  white-space: nowrap;
  box-shadow: 0 1px 2px rgba(0,0,0,0.2);
  transition: all cubic-bezier(0.3, 1.5, 0.7, 1) 0.3s;
}

.toggle:checked {
  background-color: Silver;
}

.toggle:checked:before {
  left: 32px;
}

.inlineWeather{
	display: flex;
    align-items: center;
    justify-content: center;
}

#inlineForm {
	margin-top: 10px;
	padding-left: 12%;
	padding-right: 12%;
}

#unitChanger {
	margin-right: 5px;
	width: 20%;
	border-radius: 20px;
	height: 35px;
}

.animate:hover {
	  animation-name: iconAnimation;
	  animation-duration: 5s;
	  animation-iteration-count: infinite;
}

@keyframes iconAnimation {
  0%   {background-color:red;}
  25%  {background-color:yellow;}
  50%  {background-color:blue;}
  75%  {background-color:green;}
  100% {background-color:red;}
}

h2{
	color: DarkSlateGrey;
	font-family: Montserrat;
	font-size: 35px;
}

.jumbotron{
  		background-color:SteelBlue;
  		width: 65%;
  		height:30%;
  		padding: 3px;
  
  }
</style>
</head>
<body>

<div class="container">

<%-- <h1><%= username%></h1> --%>
<h5 id="historyHeader" style="font-family: Montserrat; font-size: 20px;">Search History</h5>
<div class = "inlineWeather">

<% int j = 1;
for(String m : search){ 
	String s = m;
%>

<form method="GET" action="/HomePageServlet" id="searchHistoryElements" style=" width: 120px; font-family: Montserrat;">
    <!-- distance sort -->
    <input type="hidden" name="weatherloc" value="<%=s%>">
    <input type="hidden" name="fromSearchHistory" value="fromSearchHistory">
    <input type="hidden" name ="<%=s%>">
    <input type="submit" id="results" value="<%= s %>"class="form-control">
</form>
<% } %>
</div>
<h1 style="text-align:center; padding-top: 10px; padding-bottom: 10px; font-size: 45px;">Weather Planner</h1>
  <div class="jumbotron">
    <h3 id = "prompter" style="color:White; weight:heavy;"><%= prompter %></h3> 
    <h3 id="error"></h3>
		<h2 id="location"  style="color:White; weight:heavy;"><%= location %></h2> <br>
		<h3 id="date"  style="color:White; weight:heavy;"><%= date %></h3>
		<br> <img id="img" src=<%= img %> >
		<%if(!location.equals("") && !location.equals("No Location Found")){ %>
		<div style="display:inline-block"><h3 class="temp" id="temp"  style="color:White; weight:heavy; display:inline-block;"><%= temp%> </h3><h3 class="tempUnit" id="tempUnit"  style="color:White; weight:heavy; display:inline-block;"><%= tempUnit%> </h3>

		</div>
		
		<%} %>

		<h3 id="desc"  style="color:White; font-size:20px;"><%= desc %></h3>
   

  </div>
</div>
<div>

</div>
	<div>
		<form method = "GET" action="/HomePageServlet">
			<input type="text" id="weatherloc" name="weatherloc"
				placeholder="Enter location (city or zip)" style= "font-family: Montserrat;">
			<br>
			<div class = "inlineWeather" id="inlineForm">
			<!-- <button type="button" class="btn btn-primary" onclick="change_unit()" >Change Units</button> -->
			<input class="toggle" type="checkbox" name="toggle" id="toggle" onclick="change_unit()"/>
			<button type="submit" id="submitbutton">Show me the weather</button>				
			</div>
		</form>　

	</div>

	<input type="hidden" id="unit" value="false">

<nav class="navbar navbar-default" name="navName" style="background-color: DimGray; position: fixed; bottom: 0;width: 100%; padding-top: 10px;">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>      
         <span class="icon-bar"></span>    
          <span class="icon-bar"></span>                      
      </button>

		  <form name="formname" action="/UserAuthServlet" method="POST">
				<input type="hidden" name="action" value="signout">

				<button id="signout_btn" type="button" class="navbar-brand" onclick= "callServlet()" style="color: White; background-color: DimGrey; font-size: 15px;" ><i class = "fa fa-fw fa-user"></i>Logout</button>	       

			</form>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav navbar-right">
      <li><a href="HomePage.jsp"  style="color: White;"><i class=" fa fa-fw fa-home"></i> Home</a> </li>
     

      
       <li><a href="Vacation.jsp"  style="color: White;"><i class="fas fa-umbrella-beach"></i> Vacation</a></li> 
       <li><a href="Activity.jsp" style="color: White;"><i class="fas fa-futbol"></i> Activity</a> </li> 
       <li><a href="AnalysisPage.jsp"  style="color: White;"><i class=" fa fa-fw fa-calculator"></i> Analysis 	</a></li>
     	 <!-- <li><a href="SearchHistory.jsp" style="color: White;"><i class="fa fa-fw fa-search"></i>  Search History</a></li> -->
      </ul>
    </div>
  </div>
</nav>


	<script src="https://www.gstatic.com/firebasejs/7.13.1/firebase-app.js"></script>
	<script src="https://www.gstatic.com/firebasejs/7.13.1/firebase-analytics.js"></script>
	<script src="https://www.gstatic.com/firebasejs/7.13.1/firebase-auth.js"></script>
	<script type=text/javascript src="util.js"></script>
	<script>

	if(session.getAttribute("initialized")!=null)
	{
		document.getElementById("img").setAttribute("style").value="height:60px; width:60px;";
	}
	/* 	var firebaseConfig = {
			    apiKey: "AIzaSyB1OjAkutr0z7bNn0NjorgD4Gbe2uZWaXw",
			    authDomain: "cs-310-project-2.firebaseapp.com",
			    databaseURL: "https://cs-310-project-2.firebaseio.com",
			    projectId: "cs-310-project-2",
			    storageBucket: "cs-310-project-2.appspot.com",
			    messagingSenderId: "214844068069",
			    appId: "1:214844068069:web:03f56921d09a7db939440c",
			    measurementId: "G-R018PH3DDR"
			  };
		// Initialize Firebase
		firebase.initializeApp(firebaseConfig);
		firebase.analytics(); */
		

		function callServlet(){
			r = confirm("Are you sure you want to logout?");
			if(r){
				document.formname.submit();
			 }
		  }
		function change_unit(){
			var unit_c = document.getElementById("unit").value
			console.log(unit_c)
			
			var elements = document.getElementsByClassName("temp");
			for (let i = 0; i < elements.length; i++){
				var original = elements[i].innerHTML;
				if (unit_c == "true"){
					console.log("C->F");
					original = Math.round(10 * ((original * 9/5) + 32))/10;
					document.getElementById("unit").value = false;
					document.getElementById("tempUnit").innerHTML = " °F";
				}else{
					console.log("F->C");
					original = Math.round(10 * ((original - 32) * 5 / 9))/10;
					document.getElementById("unit").value = true;
					document.getElementById("tempUnit").innerHTML = " °C";
				}
				elements[i].innerHTML = original;
			}
		}
		
		firebase.auth().onAuthStateChanged(function(user) {
			  if (user) {
			    // User is signed in.
			    var email = user.email;
			    var emailVerified = user.emailVerified;
			    var photoURL = user.photoURL;
			    var isAnonymous = user.isAnonymous;
			    var uid = user.uid;
			    var providerData = user.providerData;
			    
			    console.log("username: " + email);
			    // ...
			  } else {
			    // User is signed out.
			    // ...
			    console.log("not logged in");
			  }
		});
	</script> 
</body>
</html>


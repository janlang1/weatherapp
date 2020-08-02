<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.text.DateFormat" 
    import="java.text.SimpleDateFormat" import="java.util.Calendar" 
    import="java.util.ArrayList" import="java.util.Date" import="csci310.*"%>
<!DOCTYPE html>
<%
	//Disable Caching
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
	String user = (String) session.getAttribute("uid");
	if(user == null){
		response.sendRedirect("login.jsp");
	}
	Boolean initialized = (Boolean) session.getAttribute("initialized");
	if (initialized == null){
		System.out.println("Initializing values");
		session.setAttribute("initialized", true);
		
		session.setAttribute("location", "");
		session.setAttribute("date", "");
		session.setAttribute("temp", 0.0);
		session.setAttribute("analysisDesc", "");
		session.setAttribute("img", "");
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
	
	ArrayList<String> dates = new ArrayList<>();
	Calendar cal = Calendar.getInstance();
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	
	for(int i = 0;i < 5; i++){
		String date = dateFormat.format(cal.getTime());
		dates.add(date);
		
		cal.add(Calendar.DATE, 1);
	}
	

	// Holds the last page
	session.setAttribute("last_page", "AnalysisPage.jsp");
	Boolean city_selected = (Boolean) session.getAttribute("city_selected");
	String desc = (String)session.getAttribute("analysisDesc");
	
	ArrayList<ArrayList<String>> fav_data = (ArrayList<ArrayList<String>>) session.getAttribute("fav_data");
	ArrayList<String> city_data = (ArrayList<String>) session.getAttribute("city_data");
	System.out.println(city_selected);
	System.out.println(city_data);
	String temp = " ";
	String tempUnit = " ";
	if (city_data.size() > 0){
		temp = city_data.get(2);
		tempUnit = " ";
		if(!temp.equals("0.0"))
		{
			tempUnit = " °C";
		}
	}
	
	/* Data needed for each city:
	 *  - city name [0]
	 *  - country [1]
	 *  - current temp [2]
	 *  - current max [3]
	 *  - current min [4]
	 *  - forecasts 1-5 
	 *  	- weather icon [5, 8, 11, 14, 17]
	 *  	- max temp [6, 9, 12, 15, 18]
	 *  	- min temp [7, 10, 13, 16, 19]
	 */
	 
	boolean unit_c = (boolean) session.getAttribute("unit");
	
	boolean fav_empty = (fav_data.get(0).size()==0);
	String city_name, cityCountry, cityMin, cityMax, icon;
	city_name = "";
	if(fav_data.get(0).size()==0){
		icon = "";
	}else{
		icon = fav_data.get(5).get(0);
	}
	ArrayList<String> favorites = (ArrayList<String>) session.getAttribute("favorites_List");
	

/*  	ArrayList<Weather> resultList = (ArrayList<String>)session.getAttribute("titles");
	ArrayList<Image> imageList = (ArrayList<String>)session.getAttribute("titles");


	ArrayList<String> cityName = (ArrayList<String>)session.getAttribute("cityName");
	ArrayList<String> cityCountry = (ArrayList<String>)session.getAttribute("cityCountry");
	ArrayList<Integer> cityMin = (ArrayList<Integer>)session.getAttribute("cityMin");
    ArrayList<Integer> cityMax = (ArrayList<Integer>)session.getAttribute("cityMax"); */

%>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="HomePage.css">
<!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous"> -->
  <script src="https://kit.fontawesome.com/a076d05399.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"

	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

<script

	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<script

	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<script

	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

<link

	href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css"

	rel="stylesheet">

<script

	src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>

<link

	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"

	rel="stylesheet">

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
  <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
<style>


.switch {
  position: relative;
  display: inline-block;
  width: 60px;
  height: 34px;
}
/* Hide default HTML checkbox */
.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}
/* The slider */
.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  -webkit-transition: .4s;
  transition: .4s;
}
.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  -webkit-transition: .4s;
  transition: .4s;
}
input:checked + .slider {
  background-color: #2196F3;
}
input:focus + .slider {
  box-shadow: 0 0 1px #2196F3;
}
input:checked + .slider:before {
  -webkit-transform: translateX(26px);
  -ms-transform: translateX(26px);
  transform: translateX(26px);
}
/* Rounded sliders */
.slider.round {
  border-radius: 34px;
}
#location {
	display: inline-block;
}
#date {
	display: inline-block;
}
#error {
	color: #FF6347;
}
#img {
	width:170px;
	vertical-align: middle;
}
#temp {
	display: inline-block;
}
#weatherloc {
	width: 325px;
	font-size: 18px;
	color: MidnightBlue;
}
#submitbutton {
	margin: 20px;
	display: inline-block;
	width: 230px;
	font-size: 18px;
	font-family: Montserrat;
	 border-radius: 34px;
	 padding: 7px;
}
.btn {
font-family: Montserrat;
 border-radius: 34px;
 margin: 20px;
	display: inline-block;
	width: 162px;
	font-size: 18px;
}
#toggle {
	display: inline-block;
}
body {
	background-color: AliceBlue;
}
h1{
	color: DarkSlateGrey;
	font-family: Montserrat;
}

h2{
	color: DarkSlateGrey;
	font-family: Montserrat;
	font-size: 35px;
}


h3{
	color: DarkSlateGrey;
	font-family: Montserrat;
}
h5{
	color: Black;
	font-family: Montserrat;
}
h6{
	color: Black;
	font-family: Montserrat;
}
div {
	margin: auto;
	text-align: center;
}

span {
	position: absolute;
	bottom: -40px;
	left: 0;
	right: 0;
}
::placeholder {
	color:#C0C0C0;
}

.navbar {
    /* padding-top: 15px; */
    padding-bottom: 30px;
    border: 0;
    border-radius: 0;
    margin-bottom: 0;
    font-size: 12px;
    letter-spacing: 5px;
    background-color: DarkGrey;
    font-color: White;
  }
  .navbar-nav  li a:hover {
    color: #1abc9c !important;
  }
   li{
    color: White; 
  }
  .jumbotron{
  		background-color:SteelBlue;
  		width: 65%;
  		height:30%;
  		padding: 3px;
  	
  
  }
  
@keyframes iconAnimation {
  0%   {background-color:red;}
  25%  {background-color:yellow;}
  50%  {background-color:blue;}
  75%  {background-color:green;}
  100% {background-color:red;}
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

#inlineForm {
	margin-top: 10px;
	padding-left: 12%;
	padding-right: 12%;
}

.toggle:before {
  content: "°F °C";
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
  

}


.col-sm-4 {

	display: inline;

}

.inlineWeather{
	display: flex;
    align-items: center;
    justify-content: center;
    padding: 10px;
}




#weather-space {

	margin: auto;

	text-align: center;

	border: 1px solid MidnightBlue;

	border-radius: 30px;

}



#five-day {

	text-align: center;

	/* margin: auto;

	text-align:center;

	  border: 1px solid MidnightBlue;

  border-radius: 30px; */

}



</style>

<title>AnalysisPage</title>
</head>
<body>

        <h1 id="title" style="text-align: center;padding-top: 15px; padding-bottom: 10px;font-size:50px;"> Weather Analysis </h1>
		<h1 id="error"></h1>
        <div class="container">
            <div class="row">
				
                <div class="col-sm-4">
					<h3>Favorites List</h3>
					<!-- favorites list from session, but also should be sorted alphabetically -->
					<% for(int i = 0; i < favorites.size(); i++){ %>
						<!-- button for city to load forecast -->
						<form method="GET" id="fav_city"  action="AnalysisServlet">
							<input type="submit" id="fav_city_name" value="<%= favorites.get(i) %>"class="form-control" style="font-family: Montserrat;">
							<input type="hidden" name="cityName" value="<%= favorites.get(i) %>">
							<input type="hidden" name="action" value="display_city">
						</form>
						
						<form method="POST" action="/favorites" style="display: inline;">
							<input type="hidden" name="action" value="remove">
							<input type="hidden" name="cityname" value="<%=favorites.get(i) %>">
							<!-- remove button -->
							<div class = "inlineWeather" id="inlineForm">
								<input class="btn btn-danger" type="submit" value="Remove"class="form-control">
								 <input class="toggle" type="checkbox" name="toggle" id="toggle" onclick="change_unit()"/>
								 </div>
						</form>
					<% } %>
                </div>
                <input type="hidden" id="unit" value="true">
                <div class="col-sm-4">
                    <% if(fav_data.get(0).size() == 0) { %>
                        <h1 style="text-align: center;">No locations found.</h1>
					<% }%> 
					<!-- stuff above 5 day forecast -->
					<% if(!city_selected){ %>
						<h1 style="text-align: center;">No locations selected.</h1>	
					<% }%>
					
				<% if(city_selected){ %>
				
				<div class="container-fluid">
				<div id="weather-space">
				
				<h3 style="text-align: center;"> <%=city_data.get(0) %>, <%= java.time.LocalDate.now() %> </h3>
				<div class="inlineWeather">
						<img id="img" src="<%= icon %>"style="width: 150px;">
						<div style="display:inline-block"><h3 class="temp" id="temp"  style="weight:heavy; display:inline-block;"><%= temp%> </h3><h3 class="tempUnit" id="tempUnit"  style="font-size: 20px; display:inline-block;"><%= tempUnit%> </h3>
		</div>
						<%-- <h3  class="temp" id="temp"><%= city_data.get(2) %></h3> --%>
					</div>
					<h3 id="desc"  style="font-size:20px;"><%= desc %></h3>	
						
					<!-- <h3 id="locationDate" name="locationDate"></h3>
					<div class="inlineWeather">
						<img id="weatherImg" style="width: 150px;">
						<h3 id="temp" name="temp"></h3>
					</div>
					<h3 id="conditions"></h3> -->
				</div>
			</div>
			<div id="five-day" style="padding-top: 20px;">
				<div class="inlineWeather" >
					<!-- <div id ="row"> -->
					<div id="container"
						style="border: 1px solid MidnightBlue; border-radius: 30px; padding: 2px">
					<!-- 	<h6 id="date1" name="date1"></h6> -->
					
					<h6><%=dates.get(0) %></h6>
						<div class="inlineWeather">
							<!-- <img id="weatherImg1" style="width: 50px;"> -->
							
							<img id="img" src="<%= city_data.get(5) %>" style="width: 50px;">
										
										<h6 id="tprompt">High: </h6><h6 class="temp" style="padding: 5px;"> <%=city_data.get(6) %></h6>
										<h6 id="tprompt">Low: </h6> <h6 class="temp"style="padding: 5px;"><%=city_data.get(7) %></h6>
						</div>
					</div>
					<div id="container"
						style="border: 1px solid MidnightBlue; border-radius: 30px; padding: 2px">
					<!-- 	<h6 id="date2" name="date2"></h6> -->
						<h6><%=dates.get(1) %></h6>
						<div class="inlineWeather">
						<img id="img"  style="width: 50px;" src="<%= city_data.get(8) %>">
										
										<h6 id="tprompt">High: </h6><h6 class="temp" style="padding: 5px;"><%=city_data.get(9) %></h6>
										<h6 id="tprompt">Low: </h6> <h6 class="temp" style="padding: 5px;"><%=city_data.get(10) %></h6>
							
						</div>
					</div>
					<div id="container"
						style="border: 1px solid MidnightBlue; border-radius: 30px; padding: 2px">
					<!-- 	<h6 id="date3" name="date3"></h6> -->
						<h6><%=dates.get(2) %></h6>
						<div class="inlineWeather">
						<img id="img"  style="width: 50px;" src="<%= city_data.get(11) %>">
										
										<h6 id="tprompt">High: </h6><h6 class="temp" style="padding: 5px;"><%=city_data.get(12) %></h6>
										<h6 id="tprompt">Low: </h6><h6 class="temp" style="padding: 5px;"><%=city_data.get(13) %></h6>
							
						</div>
					</div>
					<div id="container"
						style="border: 1px solid MidnightBlue; border-radius: 30px; padding: 2px">
					<!-- 	<h6 id="date4" name="date4"></h6> -->
						<h6><%=dates.get(3) %></h6>
						<div class="inlineWeather">
						<img id="img" src="<%= city_data.get(14)%>" style="width: 50px;">
										
										<h6 id="tprompt">High: </h6> <h6 class="temp" style="padding: 5px;"><%=city_data.get(15) %></h6>
										<h6 id="tprompt">Low: </h6> <h6 class="temp" style="padding: 5px;"><%=city_data.get(16) %></h6>
						
						</div>
					</div>
					<div id="container"
						style="border: 1px solid MidnightBlue; border-radius: 30px; padding: 2px">
						<!-- <h6 id="date5" name="date5"></h6> -->
						<h6><%=dates.get(4) %></h6>
						<div class="inlineWeather">
						<img id="img" src="<%= city_data.get(17) %>" style="width: 50px;">
										
										<h6 id="tprompt">High: </h6> <h6 class="temp" style="padding: 5px;"><%=city_data.get(18) %></h6>
										<h6 id="tprompt">Low: </h6> <h6 class="temp" style="padding: 5px;"><%=city_data.get(19) %></h6>
							
						</div>
					</div>
				</div>
				

				</div>
					
					
			
                    <%} %>
                
                    <!-- <button class="btn btn-primary" onclick="change_unit()">Unit Change</button> -->
                    <!-- end of else statment -->
				<!-- end of col-8 div -->
				</div>

				<div class="col-sm-4">
					
				 <div id="myCarousel" class="carousel slide" data-ride="carousel">

				 <div class="carousel-inner">

				      <div class="item active">

				        <img  id = "image1" style="width:100%;">

				      </div>

				

				     

				      <div class="item">

				        <img id = "image2" style="width:100%;">

				      </div>

				      <div class="item">

				        <img  id = "image3" style="width:100%;">

				      </div>
				        <div class="item">

				        <img  id = "image4" style="width:100%;">

				      </div>
				        <div class="item">

				        <img  id = "image5" style="width:100%;">

				      </div>
				        <div class="item">

				        <img  id = "image6" style="width:100%;">

				      </div>

				    

				</div> 

				</div>
				
				</div>

			<!-- end of row div -->
			</div>
		<!-- end of container div -->
        </div>
	
	<!-- navbar -->
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

				<button id="signout_btn" type="button" class="navbar-brand" onclick= "callServlet1()" style="color: White; background-color: DimGrey; font-size: 15px;" ><i class = "fa fa-fw fa-user"></i>Logout</button>	       

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
<!-- 	<div class="bg-img">
		<div class="container">
			<div class="topnav">
                <a href="HomePage.jsp"><img src="img/25694.svg"><span>Home</span></a> 
                <a href="Vacation.jsp"><img src="img/vacation.png"><span>Vacation</span></a> 
                <a href="Activity.jsp"><img src="img/activity.svg"><span>Activity</span></a> 
                <a href="AnalysisPage.jsp"><img src="img/analysis.png"><span>Analysis</span> </a>
			</div>
		</div>
	</div> -->
	<script>

	function callServlet1(){
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
		
	</script>	
	
	
	<%

	String validResponse = session.getAttribute("validResp").toString(); 
	System.out.println ("ValidResponses " + validResponse);
	

	if (validResponse.equals("valid") ){
	System.out.println("inLoop");
%>

<script>

console.log("VALRESP");/*
console.log(validResponse); */
document.getElementById("image1").src = "imagesimages1.png";

	document.getElementById("image2").src = "imagesimages2.jpg";

	document.getElementById("image3").src = "imagesimages3.png"; 
	document.getElementById("image4").src = "images4.jpg";
	document.getElementById("image5").src = "images5.jpg";
	document.getElementById("image6").src = "images6.jpg";
	
			</script>

<%
	System.out.println("outsideLoop");
	} 



%>
	
	
</body>

</html>
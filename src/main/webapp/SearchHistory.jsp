<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList" import="java.util.Map" import="csci310.*"%>
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
		session.setAttribute("desc", "");
		session.setAttribute("img", "");
		session.setAttribute("last_page", "");
		session.setAttribute("list_activity", new ArrayList<City>());
		session.setAttribute("list_vacation", new ArrayList<City>());
		ArrayList<ArrayList<String>> fav_data = new ArrayList<>();
		for(int i = 0; i < 23; i++){ fav_data.add(new ArrayList<String>());}
		session.setAttribute("fav_data", fav_data);	
		session.setAttribute("city_selected", false);
		session.setAttribute("city_data", new ArrayList<String>());
		session.setAttribute("unit", true);
	}
	boolean unit_c = (boolean) session.getAttribute("unit");
	session.setAttribute("last_page", "SearchHistory.jsp");
	System.out.println(session.getAttribute("last_page"));
	ArrayList<City> resultList = (ArrayList<City>)session.getAttribute("list_vacation");
	if(resultList == null) {
		System.out.println("first time visiting vacation page");
		resultList = new ArrayList<City>();
	}
	else System.out.println("java code in SearchHistory JSP" + resultList.size());
	ArrayList<String> fav_city_names;
	ArrayList<ArrayList<String>> fav_data = (ArrayList<ArrayList<String>>)session.getAttribute("fav_data");
	if(fav_data == null){
		fav_city_names = new ArrayList<>();
	}else{
		fav_city_names = fav_data.get(0);
	}
	
	//for search
	session.setAttribute("last_page", "SearchHistory.jsp");
	ArrayList< Map<String, Object> > search = (ArrayList< Map<String, Object> >)session.getAttribute("searchHistory");
	
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
<!-- 
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="otherPages.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous"> -->
<title>Search History</title>
</head>
<script>

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
			}else{
				console.log("F->C");
				original = Math.round(10 * ((original - 32) * 5 / 9))/10;
				document.getElementById("unit").value = true;
			}
			elements[i].innerHTML = original;
		}
	}
</script>

<body>
<nav class="navbar navbar-default">
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
				<button type="button" class="navbar-brand" onclick= "callServlet()" style="color: White; background-color: DarkGrey" ><i class = "fa fa-fw fa-user"></i>Logout</button>	       
			</form>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav navbar-right">
      <li><a href="HomePage.jsp"  style="color: White;"><i class=" fa fa-fw fa-home"></i> Home</a> </li>
     

      
       <li><a href="Vacation.jsp"  style="color: White;"><i class="fas fa-umbrella-beach"></i> Vacation</a></li> 
       <li><a href="Activity.jsp" style="color: White;"><i class="fas fa-futbol"></i> Activity</a> </li> 
       <li><a href="AnalysisPage.jsp"  style="color: White;"><i class=" fa fa-fw fa-calculator"></i> Analysis 	</a></li>
     	 <li><a href="SearchHistory.jsp" style="color: White;"><i class="fa fa-fw fa-search"></i>  Search History</a></li>
      </ul>
    </div>
  </div>
</nav>
        <h1 id="title" style="text-align: center;"> Search History </h1>
		<h1 id="error"></h1>
        <div class="container">
            <div class="row">
                <!-- form and information to send to VAAservlet -->
                    <!-- DB table 
					Activity bool, actual activity, number of results, radius, current loc
					Vacation bool, TempLow, TempHigh, number of results, radius, current loc -->
                <div class="col-12" id = "searchHistory">
                    
                    <h5>Search History</h5>
                    <h6> Entry number | Type | Location | Low Temp | High Temp | Distance | Number of Results</h6>
                    <% int j = 1;
                    for(Map<String, Object> m : search){ 
                    	String s = Integer.toString(j++) + " | " + (String)m.get("type_") + " | " 
                    		+(String)m.get("loc_") + " | " +(String)m.get("lowtemp_") + " | " 
                    		+(String)m.get("hightemp_") + " | " + m.get("distance_") + " | " +m.get("numResults_");
                    %>
                    <form method="GET" action="VAAServlet" style="display: inline;">
                        <!-- distance sort -->
                        <input type="hidden" name="searchhistory" value="/SearchHistory.jsp">
                        <input type="hidden" name="jspname" value="<%=(String)m.get("type_")%>">
                        <input type="hidden" name="tempLow" value="<%=(String)m.get("lowtemp_")%>">
                        <input type="hidden" name="tempHigh" value="<%=(String)m.get("hightemp_")%>">
                        <input type="hidden" name="currLocation" value="<%=(String)m.get("loc_")%>">
                        <input type="hidden" name="numResults" value="<%=m.get("numResults_")%>">
                        <input type="hidden" name="radius" value="<%=m.get("distance_")%>">
                        <input type="submit" id="results" value="<%= s %>"class="form-control">
                    </form>
                    <% } %>
                    
					<!-- favorites list from session, but also should be sorted alphabetically -->
					
				</div>
				<input type="hidden" id="unit" value="true">
				<!-- col-8 is bootstrap to make the table bigger than the form on the left -->
                <div class="col-0">
                    
				<!-- end of col-8 div -->
				</div>
			<!-- end of row div -->
			</div>
			<div class="row"> 
			<% if(resultList.size() == 0) { %>
                        <h1 style="text-align: center;">No locations found.</h1>
                    <% } else { %> 
                    <table class="table">
                        <thead>
                            <tr>
                              <th scope="col">#</th>
                              <th scope="col">City</th>
                              <th scope="col">Country</th>
                              <th scope="col">Min Temperature</th>
                              <th scope="col">Max Temperature</th>
                              <th scope="col">Distance
                                <form method="GET" action="utilServlet" style="display: inline;">
                                    <!-- distance sort -->
                                        <input class="btn btn-primary" type="submit" value="Sort"class="form-control">
                                        <input type="hidden" name="util" value="distance">
                                </form>
​
                              </th>
                              <th scope="col">Favorite/Remove</th>
                            </tr>
                        </thead>
                        <tbody>
                    <% for(int i = 0; i < resultList.size(); i++){ System.out.println("here");%>
                        <tr>
                            <th scope="row"><%= i %></th>
                            <td><%= resultList.get(i).city %></td>
                            <td><%= resultList.get(i).country %></td>
                            <td class="temp" id ="testthis"><%= resultList.get(i).min_temp %></td>
                            <td class="temp"><%= resultList.get(i).max_temp %></td>
                            <td><%= resultList.get(i).distance %></td>
                            <% if(resultList.get(i).inFavorites){ %>
                             <% if(fav_city_names.contains(resultList.get(i).city)){ %>
                                <td>&nbsp;<form method="GET" action="/AnalysisServlet">
                                    <input class="btn btn-danger" type="submit" value="Remove"class="form-control">
                                    <input type="hidden" name="action" value="remove">
                                    <input type="hidden" name="cityID" value="<%= i %>">
                                    <input type="hidden" name="cityName" value="<%= resultList.get(i).city %>">
                                    <input type="hidden" name="util" value="fav">
                                </form></td>
                            <% } else { %>
                                <td>&nbsp;<form method="GET" action="/AnalysisServlet">
                                    <input class="btn btn-primary" type="submit" value="Favorite"class="form-control">
                                    <input type="hidden" name="action" value="favorite">
                                    <input type="hidden" name="cityID" value="<%= i %>">
                                    <input type="hidden" name="cityName" value="<%= resultList.get(i).city %>">
                                    <input type="hidden" name="util" value="remove">
                                </form></td>
                            <% } %>
                        </tr>
                    <% } %>
                        </tbody>
                    </table>
                    <button class="btn btn-primary" onclick="change_unit()">Unit Change</button>
                    <!-- end of else statment -->
                    <% } %>
			</div>
		<!-- end of container div -->
        </div>
		
	<!-- navbar from Alex -->
	<div class="bg-img">
		<div class="container">
			<div class="topnav">
                
			</div>
		</div>
	</div>
	
	
</body>
</html> --%>
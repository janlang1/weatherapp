package csci310;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.api.client.http.MultipartContent.Part;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/VAAServlet")
public class VAAServlet extends HttpServlet {
	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FirebaseDatabase database;
	//initialize firebase
	public VAAServlet() {
        super();
        FirebaseOptions options = null;
		try {
			options = new FirebaseOptions.Builder()
				    .setCredentials(GoogleCredentials.getApplicationDefault())
				    .setDatabaseUrl("https://cs-310-project-2.firebaseio.com/")
				    .build();
		} catch (IOException e) {e.printStackTrace();}
		if(FirebaseApp.getApps().isEmpty()) { //<--- check with this line
            FirebaseApp.initializeApp(options);
        }
		database = FirebaseDatabase.getInstance();
        
    }
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		session.setAttribute("vacation_error", "");
		session.setAttribute("activity_error", "");
		List<City> cityArray = new ArrayList<>();
		boolean isVacationPage = true;
		PrintWriter out = response.getWriter();

		String API_KEY = System.getenv("API_KEY");
		String url_str = "http://api.openweathermap.org/data/2.5/box/city?bbox=12,32,15,37,10";
		String lowtemp_input = request.getParameter("tempLow");
		String hightemp_input = request.getParameter("tempHigh");
		String unit = "imperial";
		String radius_str = request.getParameter("radius");

		String activity = request.getParameter("activity");
		String util = request.getParameter("util");
		String jspType = request.getParameter("jspname");
		System.out.println("util: " + util);

		// printer writer used for testing
		// Jung added begin
		//String currentPage = (String) session.getAttribute("page");
		//int currPage = Integer.parseInt(currentPage);
		String pageNum = request.getParameter("pageNum");
		String page = request.getParameter("whatPage");
		System.out.println(page);
		String prevOrNext = request.getParameter("prevNext");
		if(pageNum != null) {
			RequestDispatcher rd;
			isVacationPage = page.equals("vacation") ? true:false ;
			int intPageNum = Integer.parseInt(pageNum);
			if(prevOrNext != null) {
				if(prevOrNext.equals("0")) {
					intPageNum -= 1;
				} else if(prevOrNext.equals("1")) {
					intPageNum += 1;
				}
				pageNum = String.valueOf(intPageNum);
			}
			if(isVacationPage) {
				session.setAttribute("page_vacation", pageNum);
				rd = request.getRequestDispatcher("/Vacation.jsp");
			//	out.print(pageNum + page);
			}
			else {
				session.setAttribute("page_activity", pageNum);
				rd = request.getRequestDispatcher("/Activity.jsp");
			//	out.print(pageNum + page);
			}
			//	out.flush();
	    	
			if(rd!= null) {
				rd.forward(request, response);
			}
//			PrintWriter out = null;
//			out.print(pageNum + page);
//			out.flush();
			return;
		}
		

		//reverse the array only!
		
		
		// Jung added end
		// find which jsp called the servlet (vacation or activity)	
				
		//sorting distance by reversing it
		if(util != null) {
			if(util.equals("distance")) {
				System.out.println("I am sorting cities by distance");
				cityArray = (jspType.equals("activity")) ? (List<City>)session.getAttribute("list_activity"):(List<City>)session.getAttribute("list_vacation");
				//reverseSort(cityArray);
				Collections.reverse(cityArray); 
				RequestDispatcher rd;
				if(jspType.equals("vacation") )rd = request.getRequestDispatcher("/Vacation.jsp");
				else rd = request.getRequestDispatcher("/Activity.jsp");
				if(rd!= null) {
					System.out.println("Forward? hello");
					rd.forward(request, response);}
				return;
			} else if(util.equals("likes")) {
				System.out.println("I am sorting cities by likes");
				cityArray = (jspType.equals("activity")) ? (List<City>)session.getAttribute("list_activity"):(List<City>)session.getAttribute("list_vacation");
				//reverseSort(cityArray);
				Collections.sort(cityArray, new SortbyLikes()); 
				if((boolean)session.getAttribute("toggle")) {
					Collections.reverse(cityArray); 
				}
				session.setAttribute("toggle", !(boolean)session.getAttribute("toggle"));
				
				RequestDispatcher rd;
				if(jspType.equals("vacation") ) rd = request.getRequestDispatcher("/Vacation.jsp");
				else rd = request.getRequestDispatcher("/Activity.jsp");
				if(rd!= null) {
					System.out.println("Forward? hello");
					rd.forward(request, response);}
				return;
			}
		}
		session.setAttribute("toggle", false);
		
		// lowTemp cannot be converted to int and vacation (invalid)
		try {
			Integer.parseInt(lowtemp_input);
		}catch (Exception e) {
			if(jspType.equals("vacation")) {
				session.setAttribute("vacation_error", "Low Temp");
				response.sendRedirect("/Vacation.jsp");
				out.print("invalid input");
				return;				
			}
		}
		
		// highTemp cannot be converted to int and vacation (invalid)
		try {
			Integer.parseInt(hightemp_input);
		}catch (Exception e) {
			if(jspType.equals("vacation")) {
				session.setAttribute("vacation_error", "High Temp");
				response.sendRedirect("/Vacation.jsp");
				out.print("invalid input");
				return;				
			}
		}
						
		// highTemp is lower than lowTemp
		if(jspType.equals("vacation") && Double.parseDouble(lowtemp_input) >= Double.parseDouble(hightemp_input)) { 
			session.setAttribute(jspType + "_error", "High Temp");
			response.sendRedirect("/Vacation.jsp");
			out.print("invalid input");
			return;
		}

		// radius is null or non-number
		int radius;
		try {
			radius = Integer.parseInt(radius_str);
		}catch (Exception e) {
			session.setAttribute(jspType + "_error", "Radius");
			response.sendRedirect(jspTypeToFilename(jspType));
			out.print("invalid input");
			return;				
		}
		if (radius < 0) {
			session.setAttribute(jspType + "_error", "Radius");
			response.sendRedirect(jspTypeToFilename(jspType));
			out.print("invalid input");
			return;
		}
		
		if(jspType.equals("activity")) {
			System.out.println("im here");
			isVacationPage = false;
			// find the temperature ranges from the user input
			if(activity.equals("snow")) {
				lowtemp_input = "0";
				hightemp_input = "40";
			} else if(activity.equals("outdoor")) {
				lowtemp_input = "41";
				hightemp_input = "80";
			} else if(activity.equals("water")) {
				lowtemp_input = "81";
				hightemp_input = "120";
			}else {
				// Acitivity is invalid
				session.setAttribute("activity_error", "Activity");
				response.sendRedirect("/Activity.jsp");
				out.print("invalid input");
				return;	
			}
		}

		

		URL url = new URL(url_str);
		BufferedReader br;

		String str = "";
		String api_response = "";

		String lon = request.getParameter("lon");
		String lat = request.getParameter("lat");
		System.out.println(lon + " " + lat);
		lon = lon.trim();
		lat = lat.trim();
		
		if (lat.equals("")) {
			session.setAttribute(jspType + "_error", "Location");
			response.sendRedirect(jspTypeToFilename(jspType));
			out.print("invalid input");
			return;				
		}

		
		// call api to find cities nearby the current location
		url_str = "http://api.openweathermap.org/data/2.5/box/city?" + getBbox(lon, lat)  + "&appid=" + API_KEY + "&units=" + unit;
		System.out.println(url_str);
		url = new URL(url_str);
		try {
			br = new BufferedReader(new InputStreamReader(url.openStream()));
		} catch(Exception e) {
			System.out.println("Invalid api reference");
			//out.print("Exception");
			return;
		}
//		out.print(lowtemp_input + hightemp_input + loc + numResults + activity);

		str = "";
		api_response = "";
		while(null != (str = br.readLine())) {
			api_response += str;
		}
		System.out.println(api_response);
		
		// convert api response to json and parse the data
		JsonObject jsonObject = new JsonParser().parse(api_response).getAsJsonObject();
		JsonArray jsonArray = (JsonArray) jsonObject.get("list");
		String actualResult = "";
		actualResult += Integer.toString(radius)+ "|";
		
		Map<String, Integer> likes = (Map<String, Integer>) session.getAttribute("likes");
		if(likes == null) {
			System.out.println("no likes map here");
		}
		//out.print(radius);
		for(JsonElement element: jsonArray) {
			//if(cityArray.size() < Integer.parseInt(numResults)) {
				String city = element.getAsJsonObject().get("name").getAsString();
				String id = element.getAsJsonObject().get("id").getAsString();
				String temp_min = element.getAsJsonObject().get("main").getAsJsonObject().get("temp_min").getAsString();
				String temp_max = element.getAsJsonObject().get("main").getAsJsonObject().get("temp_max").getAsString();
				String temp_curr = element.getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString();
				String cityLon = element.getAsJsonObject().get("coord").getAsJsonObject().get("Lon").getAsString();
				String cityLat = element.getAsJsonObject().get("coord").getAsJsonObject().get("Lat").getAsString();
				int distance = findDist(cityLon, cityLat, lon, lat);
				if(distance > radius) {continue;}
				System.out.println(city);
				String country = "";
				// compare the user's requested minimum temp to each cities actual minimum temp, same with maximum temp
				
				if(Double.parseDouble(temp_min) >= Double.parseDouble(lowtemp_input) && Double.parseDouble(temp_max) <= Double.parseDouble(hightemp_input)) {
					// this api call is to find the name of the country
					url_str = "http://api.openweathermap.org/data/2.5/weather?id=" + id + "&appid=" + API_KEY;
					url = new URL(url_str);
					try {
						br = new BufferedReader(new InputStreamReader(url.openStream()));
					} catch(Exception e) {
						System.out.println("Invalid id api reference");
						return;
					}
					str = "";
					api_response = "";
					while(null != (str = br.readLine())) {
						api_response += str;
					}
					//System.out.println(api_response);
					jsonObject = new JsonParser().parse(api_response).getAsJsonObject();
					country = jsonObject.get("sys").getAsJsonObject().get("country").getAsString();
					// add the city to the array
					if(likes.containsKey(city)) {
						System.out.print("CIITY LIKES: ");
						System.out.println(likes.get(city));
						String str3 = String.valueOf(likes.get(city));
						//System.out.println((likes.get(city)).getClass().getName());
						System.out.println("WTF: " + str3);
						System.out.println("SIZE: " + str3.length());
						System.out.println("FRIST LETTER: " + str3.substring(0,1));
						
						String[] p = str3.split(".");
						int i = Integer.parseInt(str3.substring(0,1));
						
						//String x = likes.get(city);
						cityArray.add(new City(city, country, (int)Double.parseDouble(temp_min), (int)Double.parseDouble(temp_max), (int)Double.parseDouble(temp_curr), distance, i));
					} else {
						cityArray.add(new City(city, country, (int)Double.parseDouble(temp_min), (int)Double.parseDouble(temp_max), (int)Double.parseDouble(temp_curr), distance, 0));
					}
					
				}
			
		}
		actualResult += Integer.toString(cityArray.size())+ "|";
		if(cityArray.size() > 0) {
			actualResult += Integer.toString(cityArray.get(0).distance) + "|";
			actualResult += Integer.toString(cityArray.get(cityArray.size()-1).distance)+ "|";
		}
		//soring city array
		Collections.sort(cityArray, new SortbyDistance()); 
 
		
		if (jspType.equals("activity")) {
			session.setAttribute("list_activity", cityArray);			
		}else {
			session.setAttribute("list_vacation", cityArray);
			// Jung added begin
			//session.setAttribute("pagination_list_vacation", tempCityArray);
			// Jung added end
		}
		
		
		RequestDispatcher rd;
		if(isVacationPage) rd = request.getRequestDispatcher("/Vacation.jsp");
		else rd = request.getRequestDispatcher("/Activity.jsp");
		if(rd!= null) {rd.forward(request, response);}
//		out.print(actualResult);
		out.print("VAA Successful");
		out.flush();
		//out1.flush();
    	
	}
	// this function is to create part of the api call
	public String getBbox(String lon, String lat) {
		Integer intLon = (int)(Double.parseDouble(lon));
		Integer intLat = (int)(Double.parseDouble(lat));
		Integer upperLon = intLon + 1;
		Integer lowerLon = intLon - 1;
		Integer upperLat = intLat + 1;
		Integer lowerLat = intLat - 1;
		String bbox = "bbox=" + String.valueOf(lowerLon) + "," + String.valueOf(lowerLat) + "," + String.valueOf(upperLon) + "," + String.valueOf(upperLat) + "," + "10";
		return bbox;
	}
	
	public double deg2rad(double deg) {
		return deg * (Math.PI/180.0);
	}
	
	public int findDist(String lon1, String lat1, String lon2, String lat2) {
		//System.out.println("Lon1:" + lon1 + " Lat1:" + lat1 + " Lon2:" + lon2 + " Lat2:" + lat2);
		int R = 6371;// Radius of the earth in km
		double intLon1 = (Double.parseDouble(lon1));
		double intLat1 = (Double.parseDouble(lat1));
		double intLon2 = (Double.parseDouble(lon2));
		double intLat2 = (Double.parseDouble(lat2));
		double dLat = deg2rad(intLat2-intLat1);
		double dLon = deg2rad(intLon2-intLon1);
		//System.out.println("dLon:" + dLon + " dLat:" + dLat);
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
			    Math.cos(deg2rad(intLat1)) * Math.cos(deg2rad(intLat2)) * 
			    Math.sin(dLon/2) * Math.sin(dLon/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double d = R * c;
		//System.out.println();
		return (int)d;
	}	
	
	private String jspTypeToFilename(String jspType) {
		return ("/" + jspType.substring(0, 1).toUpperCase() + jspType.substring(1) + ".jsp");
	}
}
package csci310;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class AnalysisServlet
 */
@WebServlet("/AnalysisServlet")
public class AnalysisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;	
	private static final String API_KEY = System.getenv("API_KEY");
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnalysisServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    private ArrayList<ArrayList<String>> add_city(HttpServletRequest request) throws ServletException, IOException{
    	// TODO: change lookup by city name to lookup by city ID
    	HttpSession session = request.getSession();
		String last_page = (String) session.getAttribute("last_page");
		String city_name = request.getParameter("cityName");
		
		// Collect data about the city
		/* Data needed for each city:
		 *  - city name [0]
		 *  - country [1]
		 *  - current temp [2]
		 *  - current max [3]
		 *  - current min [4]
		 *  - forecasts x 5
		 *  	- weather icon [5, 8, 11, 14, 17]
		 *  	- max temp [6, 9, 12, 15, 18]
		 *  	- min temp [7, 10, 13, 16, 19]
		 *  (- city_id [20])
		 *  (- city image x 3 [20, 21, 22])
		 */
		
		// Set up data (2d arraylist where each index is a city)	
		ArrayList<ArrayList<String>> fav_data = (ArrayList<ArrayList<String>>) session.getAttribute("fav_data");

		// TODO: get actual unit input from the front end
		String unit = "metric";
		
		// Get current data from API and add it to the array list
		String url_str = "http://api.openweathermap.org/data/2.5/weather?q=" + city_name + "&units=" + unit + "&appid="+ API_KEY + "&units=metric";
		URL url = new URL(url_str);
		BufferedReader br;
		try{
			br = new BufferedReader(new InputStreamReader(url.openStream()));
		} catch (Exception e) {
			System.out.println("Location not found");
			return fav_data;
		}
		String str = "";
		String api_response = "";
		while (null != (str = br.readLine())) {
			api_response += str;
		}
		
		JsonObject jsonObject = new JsonParser().parse(api_response).getAsJsonObject();
		
		Double temp = jsonObject.get("main").getAsJsonObject().get("temp").getAsDouble();
		Double max_temp = jsonObject.get("main").getAsJsonObject().get("temp_min").getAsDouble();
		Double min_temp = jsonObject.get("main").getAsJsonObject().get("temp_max").getAsDouble();
		String desc = jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString();

		session.setAttribute("analysisDesc", desc);
		fav_data.get(2).add(temp.toString());
		fav_data.get(3).add(max_temp.toString());
		fav_data.get(4).add(min_temp.toString());		
		
		
		// Get the fore cast data from API
		url_str = "http://api.openweathermap.org/data/2.5/forecast?q=" + city_name + "&appid=" + API_KEY  + "&units=metric";
		url = new URL(url_str);
		try{
			br = new BufferedReader(new InputStreamReader(url.openStream()));
		} catch (Exception e) {
			System.out.println("Location not found");
			 return fav_data;
		}
		str = "";
		api_response = "";
		while (null != (str = br.readLine())) {
			api_response += str;
		}
		
		jsonObject = new JsonParser().parse(api_response).getAsJsonObject();
		
		
		// Add the data to the arraylist
		fav_data.get(0).add(city_name);
		String country = jsonObject.get("city").getAsJsonObject().get("country").getAsString();
		fav_data.get(1).add(country);
		
		for(int i = 0; i< 5; i++) {
			 /*  	
			  * - weather icon [5, 8, 11, 14, 17]
			  * - max temp [6, 9, 12, 15, 18]
			  * - min temp [7, 10, 13, 16, 19]
			  */
			JsonObject daily_data = jsonObject.get("list").getAsJsonArray().get(i).getAsJsonObject();
			
			max_temp = daily_data.get("main").getAsJsonObject().get("temp_max").getAsDouble();
			min_temp = daily_data.get("main").getAsJsonObject().get("temp_min").getAsDouble();
			
			String icon_url = daily_data.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
			icon_url = "http://openweathermap.org/img/w/" + icon_url + ".png";
			fav_data.get(5 + i*3).add(icon_url);
			fav_data.get(6 + i*3).add(max_temp.toString());
			fav_data.get(7 + i*3).add(min_temp.toString());

		}
		//System.out.println(fav_data);

    	return fav_data;
    }
    
    private ArrayList<ArrayList<String>> remove_city(HttpServletRequest request) throws ServletException, IOException{
    	// TODO: change lookup by city name to lookup by city ID
    	
		String city_name = request.getParameter("cityName");
		ArrayList<ArrayList<String>> fav_data = (ArrayList<ArrayList<String>>) request.getSession().getAttribute("fav_data");
				
		// find index of the city and remove the city data
		for(int i = 0; i < fav_data.get(0).size(); i++) {
			if(fav_data.get(0).get(i).equals(city_name)) {
				for(int j = 0; j < fav_data.size(); j++) {
					fav_data.get(j).remove(i);
				}
			}
		}
		return fav_data;
    }
    
    private ArrayList<String> display_city(HttpServletRequest request) {
    	String city_name = request.getParameter("cityName");
    	ArrayList<String> city_data = new ArrayList<String>();
    	HttpSession session = request.getSession();
    	ArrayList<ArrayList<String>> fav_data = (ArrayList<ArrayList<String>>) session.getAttribute("fav_data");
    	for(int i = 0; i < fav_data.get(0).size(); i++) {
    		if(city_name.equals(fav_data.get(0).get(i))){ 
    			for(int j = 0; j < 20; j++) {
    				System.out.println("DISPLAY CITY:" + fav_data.get(j).get(i));
    				city_data.add(fav_data.get(j).get(i));
    			}
    			break;
    		}
    	}
    	System.out.println("distplay city function");
    	return city_data;
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		String action = request.getParameter("action");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
		String last_page = (String) session.getAttribute("last_page");
		
		System.out.println("in servlet, doing " + action);
		
		ArrayList<ArrayList<String>> fav_data;
		if(action.equals("favorite")) {
			fav_data = add_city(request);
			session.setAttribute("fav_data", fav_data);
			out.print(fav_data.get(0).size());
		}else if(action.equals("display_city")){
			ArrayList<String> city_data = display_city(request);
			session.setAttribute("city_data", city_data);
			session.setAttribute("city_selected", true);
			session.setAttribute("validResp", "valid");
			out.print(city_data.size());
		}else {
			fav_data = remove_city(request);
			session.setAttribute("fav_data", fav_data);
			out.print(fav_data.get(0).size());

		}
		
		// Go back to the original page		
		
		response.sendRedirect(last_page);
		out.flush();
	}
}

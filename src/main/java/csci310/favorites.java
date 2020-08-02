package csci310;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/favorites")
public class favorites extends HttpServlet {
	private static final String API_KEY = System.getenv("API_KEY");
	public favorites() throws IOException {
		super();
		// TODO Auto-generated constructor stub
		FileInputStream serviceAccount = new FileInputStream("src/dataBaseKey.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://cs-310-project-2.firebaseio.com").build();

		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options);

		}
	}
	private ArrayList<ArrayList<String>> add_city(HttpServletRequest request) throws ServletException, IOException{
    	// TODO: change lookup by city name to lookup by city ID
    	HttpSession session = request.getSession();
		String last_page = (String) session.getAttribute("last_page");
		String city_name = request.getParameter("cityname");
		
		System.out.println("add_city 1");
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
		System.out.println("add_city 2");
		
		JsonObject jsonObject = new JsonParser().parse(api_response).getAsJsonObject();
		
		Double temp = jsonObject.get("main").getAsJsonObject().get("temp").getAsDouble();
		Double max_temp = jsonObject.get("main").getAsJsonObject().get("temp_min").getAsDouble();
		Double min_temp = jsonObject.get("main").getAsJsonObject().get("temp_max").getAsDouble();
		String desc = jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString();
		
		System.out.println("add_city 3");
		session.setAttribute("analysisDesc", desc);
		fav_data.get(2).add(temp.toString());
		fav_data.get(3).add(max_temp.toString());
		fav_data.get(4).add(min_temp.toString());	
		
		System.out.println("add_city 4");
		
		
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
		System.out.println("add_city: "+ city_name);

    	return fav_data;
    }
    
    private ArrayList<ArrayList<String>> remove_city(HttpServletRequest request) throws ServletException, IOException{
    	// TODO: change lookup by city name to lookup by city ID
    	
		String city_name = request.getParameter("cityname");
		ArrayList<ArrayList<String>> fav_data = (ArrayList<ArrayList<String>>) request.getSession().getAttribute("fav_data");
				
		// find index of the city and remove the city data
		
		System.out.println("FAV DATA SIZE:" + fav_data.size());
		for(int i = 0; i < fav_data.get(0).size(); i++) {
			
			if(fav_data.get(0).get(i).equals(city_name)) {
				for(int j = 0; j < 20; j++) {
					System.out.println("REMOVING FROM FAV DATA: " + fav_data.get(j).get(i));
					fav_data.get(j).remove(i);
				}
			}
		}
		return fav_data;
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*Semaphore semaphore = new Semaphore(0);
		try {
			semaphore.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		ArrayList<ArrayList<String>> fav_data;

		
		HttpSession session = request.getSession();
		String action = (String)request.getParameter("action");
		String last_page = (String) session.getAttribute("last_page");
		System.out.println("in servlet, doing " + action);
		//String user = "-M54tQXvsJZqT99C8ZNo";
		
		String favoriteCity = request.getParameter("cityname");
		
		String user = "-M53yFx7RADmhkjfz59P";
		user = (String)session.getAttribute("username");
		System.out.println("Username: " + user);
		
		if(action.equals("fav")) {
			fav_data = add_city(request);
			session.setAttribute("fav_data", fav_data);
			// Check if favoriteCity already exists... if true, return and don't update
			// likes
			String url_str = "https://cs-310-project-2.firebaseio.com/users/" + user + "/favorites/" + favoriteCity
					+ ".json";
			URL url = new URL(url_str);
			BufferedReader br;
			String number;
			try {
				br = new BufferedReader(new InputStreamReader(url.openStream()));

				String str = "";
				String api_response = "";
				while (null != (str = br.readLine())) {
					api_response += str;
				}

				if (!api_response.equals("null")) {
					System.out.println("already in database");
					doGet(request, response);
					RequestDispatcher rd = request.getRequestDispatcher("/" + last_page);
					if(rd!= null) {rd.forward(request, response);}
					return;
				}
			} catch (Exception e) {

			}

			// favoriteCity doesn't already exist... add favoriteCity
			final FirebaseDatabase database = FirebaseDatabase.getInstance();
			DatabaseReference favoritesRef = database.getReference("users/" + user + "/favorites/" + favoriteCity);
			favoritesRef.setValueAsync("true");

			// 1. get the current likes (likes/cityname: number)
			String url_str1 = "https://cs-310-project-2.firebaseio.com/likes/" + favoriteCity + ".json";
			URL url1 = new URL(url_str1);
			BufferedReader br1;
			String number1;
			try {
				br1 = new BufferedReader(new InputStreamReader(url1.openStream()));

				String str = "";
				String api_response = "";
				while (null != (str = br1.readLine())) {
					api_response += str;
				}
				if (api_response.equals("null")) {
					number1 = "0";
				} else {
					number1 = api_response;
				}
			} catch (Exception e) {
				number1 = "0";
			}
			// 2. add one to that number
			int result = Integer.parseInt(number1);
			result += 1;
			// 3. push it again
			DatabaseReference likesRef = database.getReference("likes/" + favoriteCity);
			likesRef.setValueAsync(result);
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			doGet(request, response);
//			List<City> temp;
//			if (last_page.equals("activity.jsp")) {
//				temp = (List<City>)session.getAttribute("list_activity");			
//			}else {
//				temp = (List<City>)session.getAttribute("list_vacation");
//
//			}
//			for(int i = 0; i < temp.size(); ++i) {
//				if(temp.get(i).city == favoriteCity) {
//					
//				}
//			}
			
		} else if (action.equals("remove")) {
			fav_data = remove_city(request);
			session.setAttribute("fav_data", fav_data);
			
			final FirebaseDatabase database = FirebaseDatabase.getInstance();

			// check if favoriteCity exists already for specific user
			String url_str = "https://cs-310-project-2.firebaseio.com/users/" + user + "/favorites/" + favoriteCity
					+ ".json";
			URL url = new URL(url_str);
			BufferedReader br;
			String number;
			try {
				br = new BufferedReader(new InputStreamReader(url.openStream()));

				String str = "";
				String api_response = "";
				while (null != (str = br.readLine())) {
					api_response += str;
				}
				
				
				if (api_response.equals("null")) {
					
					doGet(request, response);
					RequestDispatcher rd = request.getRequestDispatcher("/" + last_page);
					if(rd!= null) {rd.forward(request, response);}
					return;
				}
			} catch (Exception e) {

			}
			// favoriteCity doesn't already exist... add favoriteCity
			DatabaseReference favoritesRef = database.getReference("users/" + user + "/favorites/" + favoriteCity);
			favoritesRef.setValueAsync("true");

			// 1. get the current likes (likes/cityname: number)
			String url_str1 = "https://cs-310-project-2.firebaseio.com/likes/" + favoriteCity + ".json";
			URL url1 = new URL(url_str1);
			BufferedReader br1;
			String number1;
			try {
				br1 = new BufferedReader(new InputStreamReader(url1.openStream()));

				String str = "";
				String api_response = "";
				while (null != (str = br1.readLine())) {
					api_response += str;
				}
				if (api_response.equals("null")) {
					number1 = "0";
				} else {
					number1 = api_response;
				}
			} catch (Exception e) {
				number1 = "0";
			}
			// 2. add one to that number
			int result = Integer.parseInt(number1);
			result -= 1;
			// 3. push it again
			DatabaseReference likesRef = database.getReference("likes/" + favoriteCity);
			likesRef.setValueAsync(result);
			
			//remove favoriteCity
			DatabaseReference ref = database.getReference("users/" + user + "/favorites/" + favoriteCity);
			ref.removeValueAsync();
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ArrayList<String> city_data = (ArrayList<String>) session.getAttribute("city_data");
			if(city_data == null) {
				
			} else if (city_data.size() == 0) {
				
			} else {
				if(city_data.get(0).equals(favoriteCity)) {
					System.out.println("DELETED CITY THAT WAS BEING PRINTED");
					session.setAttribute("city_selected", false);
				}
			}
			doGet(request, response);
//			List<City> temp;
//			if (last_page.equals("activity.jsp")) {
//				temp = (List<City>)session.getAttribute("list_activity");			
//			}else {
//				temp = (List<City>)session.getAttribute("list_vacation");
//
//			}
			
			
			
		}
		RequestDispatcher rd = request.getRequestDispatcher("/" + last_page);
		if(rd!= null) {rd.forward(request, response);}
	}



	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("DELETE");

		HttpSession session = request.getSession();
		//String user = (String) session.getAttribute("uid");
		String user = "-M53yFx7RADmhkjfz59P";
		user = (String)session.getAttribute("username");
		System.out.println("Username: " + user);
		String favoriteCity = request.getParameter("cityname");
		final FirebaseDatabase database = FirebaseDatabase.getInstance();

		// check if favoriteCity exists already for specific user
		String url_str = "https://cs-310-project-2.firebaseio.com/users/" + user + "/favorites/" + favoriteCity
				+ ".json";
		URL url = new URL(url_str);
		BufferedReader br;
		String number;
		try {
			br = new BufferedReader(new InputStreamReader(url.openStream()));

			String str = "";
			String api_response = "";
			while (null != (str = br.readLine())) {
				api_response += str;
			}
			
			
			if (api_response.equals("null")) {
				return;
			}
		} catch (Exception e) {

		}
		// favoriteCity doesn't already exist... add favoriteCity
		DatabaseReference favoritesRef = database.getReference("users/" + user + "/favorites/" + favoriteCity);
		favoritesRef.setValueAsync("true");

		// 1. get the current likes (likes/cityname: number)
		String url_str1 = "https://cs-310-project-2.firebaseio.com/likes/" + favoriteCity + ".json";
		URL url1 = new URL(url_str1);
		BufferedReader br1;
		String number1;
		try {
			br1 = new BufferedReader(new InputStreamReader(url1.openStream()));

			String str = "";
			String api_response = "";
			while (null != (str = br1.readLine())) {
				api_response += str;
			}
			if (api_response.equals("null")) {
				number1 = "0";
			} else {
				number1 = api_response;
			}
		} catch (Exception e) {
			number1 = "0";
		}
		// 2. add one to that number
		int result = Integer.parseInt(number1);
		result -= 1;
		// 3. push it again
		DatabaseReference likesRef = database.getReference("likes/" + favoriteCity);
		likesRef.setValueAsync(result);
		
		//remove favoriteCity
		DatabaseReference ref = database.getReference("users/" + user + "/favorites/" + favoriteCity);
		ref.removeValueAsync();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		doGet(request, response);
	}
	
	private String mapFunc(Map.Entry<String, Integer> entry) {
		return entry.getKey() + "-" + entry.getValue();
	}


	//returns object of this format
	/*
	 * { favorites: Arrays.toString(favoritesArray), likes: api_response }
	 */
	
	//set up an endpoint to get the formatted data
	//parse the created JSON on front end to get individual data
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("GET");

		HttpSession session = request.getSession();
		//String user = (String) session.getAttribute("uid");
		String user = "-M53yFx7RADmhkjfz59P";
		user = (String)session.getAttribute("username");
		System.out.println("Username: " + user);
		
		final PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		//get the favorites and put into array
		String url_str = "https://cs-310-project-2.firebaseio.com/users/" + user + "/favorites.json";
		URL url = new URL(url_str);
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(url.openStream()));

			String str = "";
			String api_response = "";
			while (null != (str = br.readLine())) {
				api_response += str;
			}
			
			//empty array default... if response != null, then fill it in
			String favoritesStr = "[]";
			if(!api_response.equals("null")) {
				Gson gson = new Gson();
				Map favoritesMap = gson.fromJson(api_response, Map.class);
				Set<String> favoritesKeys = favoritesMap.keySet();
				String[] favoritesArray = favoritesKeys.toArray(new String[favoritesKeys.size()]);
				ArrayList<String> newfavoritesArray = new ArrayList<>();
				for(int i = 0; i < favoritesArray.length; i++) {
					newfavoritesArray.add(favoritesArray[i]);
				}
				System.out.print("Favorites List for " + user + ": ");
				for(String s : newfavoritesArray) {
					System.out.print(s + ",");
				}
				session.setAttribute("favorites_List", newfavoritesArray);
				System.out.println();
				
			
				//favoritesStr = favoritesArray.length == 0 ? "" : "[\"" + String.join("\", \"", favoritesArray) + "\"]";
			} else {
				ArrayList<String> newfavoritesArray = new ArrayList<>();
				session.setAttribute("favorites_List", newfavoritesArray);
			}
			
			//need favorites in an array of strings
			//likes in a nested array so that each entry in the array has the city and the int 
			
			//get likes and put into object
			String url_str1 = "https://cs-310-project-2.firebaseio.com/likes.json";
	        URL url1 = new URL(url_str1);
	        BufferedReader br1;
	        
	             br1 = new BufferedReader(new InputStreamReader(url1.openStream()));

	             str = "";
	             api_response = "";
	             while (null != (str = br1.readLine())) {
	                 api_response += str;
	             }
	             
	             String likesStr = "{}";
	             if(!api_response.equals("null")) {
	            	 Gson gson = new Gson();
	            	 System.out.println("API REQUEST: " + api_response);
	 				Map<String, Integer> likesMap = gson.fromJson(api_response, Map.class);
	 				session.setAttribute("likes", likesMap);
	 				System.out.println("likesMap size: " + Integer.toString(likesMap.size()) );
	 				//ArrayList<Entry<String, Integer>> likesList = new ArrayList<Entry<String, Integer>>(likesMap.entrySet());
	 				// likesList[i].getKey() or likesList[i].getValue()
	 				//System.out.print("Likes List: ");
//					for(Entry<String, Integer> s : likesList) {
//						System.out.print(s.getKey() + ":" + Integer.toString(s.getValue() )+ ",");
//					}
//					System.out.println();
	 				
	             }
	            /* String favoritesandLikesOutput = "{\"favorites\":" + favoritesStr + ", \"likes\":" + likesStr + "}"; 
	             out.print(favoritesandLikesOutput);
	             out.flush();
	         */
		} catch (Exception e) {
			System.out.println("Location not found");
		}
		
	}
}
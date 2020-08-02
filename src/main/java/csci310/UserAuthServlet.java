package csci310;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class UserAuthServlet
 */
@WebServlet("/UserAuthServlet")
public class UserAuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String API_KEY = System.getenv("API_KEY");

	FirebaseDatabase database;
	HttpSession session;
	HttpServletRequest request;
	HttpServletResponse response;
	boolean process_done = false;
	PrintWriter out;
    
    /**
     * @throws IOException 
     * @see HttpServlet#HttpServlet()
     */
    public UserAuthServlet() throws IOException {
        super();
        
        FirebaseOptions options = new FirebaseOptions.Builder()
			    .setCredentials(GoogleCredentials.getApplicationDefault())
			    .setDatabaseUrl("https://cs-310-project-2.firebaseio.com/")
			    .build();

		if(FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }


		database = FirebaseDatabase.getInstance();
    }
    
    private ArrayList<ArrayList<String>> add_city(String city_name_1) throws IOException{
    	// TODO: change lookup by city name to lookup by city ID
    	System.out.println("add city 1");
		String city_name = city_name_1;
		
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
		//ArrayList<ArrayList<String>> fav_data = (ArrayList<ArrayList<String>>) session.getAttribute("fav_data");

		// TODO: get actual unit input from the front end
		String unit = "metric";
		
		ArrayList<ArrayList<String>> fav_data = (ArrayList<ArrayList<String>>) session.getAttribute("fav_data");

		
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
		
		System.out.println("add city 2");
		
		JsonObject jsonObject = new JsonParser().parse(api_response).getAsJsonObject();
		
		Double temp = jsonObject.get("main").getAsJsonObject().get("temp").getAsDouble();
		Double max_temp = jsonObject.get("main").getAsJsonObject().get("temp_min").getAsDouble();
		Double min_temp = jsonObject.get("main").getAsJsonObject().get("temp_max").getAsDouble();
		String desc = jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString();
		
		session.setAttribute("analysisDesc", desc);
		
		System.out.println("add city 3");

		//session.setAttribute("analysisDesc", desc);
		fav_data.get(2).add(temp.toString());
		System.out.println("add city 3a");
		fav_data.get(3).add(max_temp.toString());
		System.out.println("add city 3b");
		fav_data.get(4).add(min_temp.toString());		
		
		System.out.println("add city 4");
		
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
		
		System.out.println("add city 3");
		
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
		System.out.println("Added city to fav_data: " + city_name);

    	return fav_data;
    }
    


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get parameters from GET method
		session = request.getSession();
		this.response = response;
		this.request = request;
		out = response.getWriter();
		
		process_done = false;
		String action = request.getParameter("action");
		String email, password;
		
		if(action.equals("login")){
			email = request.getParameter("email");
			password = request.getParameter("password");
			
			try {
				boolean result = login(email, password);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}else if(action.equals("signup")) {
			email = request.getParameter("email");
			password = request.getParameter("password");
			
			boolean result;
			try {
				result = signup(email, password);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			
		}else {
			signout(session);
		}		
	}
	
	private boolean login(String email, String password) throws IOException, InterruptedException {
		// TODO: Connect to firebase
		DatabaseReference ref = this.database.getReference("users");
		System.out.println("Login called");
		
		Query query = ref.orderByChild("email").equalTo(email);
	
		
		ref.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
		  @Override
		  public void onDataChange(DataSnapshot dataSnapshot) {
			System.out.println("hello");
		    System.out.println("key: " + dataSnapshot.getKey());
		    System.out.println("val: " + dataSnapshot.getValue());
		   
		    // Email does not exist
		    if(dataSnapshot.getValue() == null) { 
		    	try {
//					login_redirect(false);
		    		System.out.println(dataSnapshot.getKey());
		    		System.out.println(dataSnapshot.getValue());
		    		login_redirect(null,null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    
		    String username = "";
		    for(DataSnapshot ds: dataSnapshot.getChildren()) {
		    	username = ds.getKey();

		    	System.out.print(username);

		    }
		    session.setAttribute("uid", username);
		    
		    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
		    String correct_password = (String) ((Map<String, Object>)map.get(username)).get("password");
		    
		    try {
		    	login_redirect(correct_password,username);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    
		  }

		  @Override
		  public void onCancelled(DatabaseError databaseError) {
		    System.out.println("The read failed: " + databaseError.getCode());
		  }
		});
		
		
		for(int i = 0; i < 20;  i++) {
			TimeUnit.SECONDS.sleep(1);
			if(process_done) {
				break;
			}
			
		}		
		
		return true;
	}
	

	
	private void login_redirect(String correct_password, String username) throws IOException {
		String email = (String) request.getParameter("email");
		String password = hash_password((String) request.getParameter("password"));
		System.out.println("redirecting");
		System.out.println("comparing password: " + password + " - " + correct_password);
		
		
		boolean login_successful = (correct_password != null && correct_password.equals(password)); 
		if (correct_password == null) {
			login_successful = false;
		}
		process_done = false;
		
		if(login_successful) {
			DatabaseReference ref = this.database.getReference("users");
			out.print("login successful");

			resetSession(session);

			session.setAttribute("user", email);
			session.setAttribute("email", email);
			session.setAttribute("username", username);
			ref = this.database.getReference("users/"+username+"/search_history");
			//System.out.println("Login called");
			//Query query = ref.orderByChild("users").equalTo("a");
			
			ref.addListenerForSingleValueEvent(new ValueEventListener() {
				  @Override
				  public void onDataChange(DataSnapshot dataSnapshot) {
					System.out.println("hello inside listener");
					System.out.println("key: " + dataSnapshot.getKey());
					System.out.println("val: " + dataSnapshot.getValue());
					// Email does not exist
					if(dataSnapshot.getValue() == null) { 
					//login_redirect(false);
					System.out.println(dataSnapshot.getValue());
					//login_redirect(null);
					}

					ArrayList< Map<String, Object> > searchhistorystuff = new ArrayList<>();
					Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
					for (String key : map.keySet()) {
						System.out.println(key);
						Map<String, Object> searchterm = (Map<String, Object>)map.get(key);
						for (Map.Entry<String, Object> entry : searchterm.entrySet()) {
						    String key1 = entry.getKey();
						    Object value = entry.getValue();
						    //System.out.println(key1 + ":" + value);
						    String temp = value.toString();
						    
						}
						searchhistorystuff.add(searchterm);
					}
					
					ArrayList<String> sortedsearchhistorystuff = new ArrayList<>();
					for(int i = 0; i < searchhistorystuff.size(); i++) {
						sortedsearchhistorystuff.add("");
					}
					
					
					Map<String, Object> map1 = (Map<String, Object>) dataSnapshot.getValue();
					System.out.print(map1.size());
					for (String key : map1.keySet()) {
						System.out.println(key);
						Map<String, Object> searchterm = (Map<String, Object>)map1.get(key);
						for (Map.Entry<String, Object> entry : searchterm.entrySet()) {
						    String key1 = entry.getKey();
						    Object value = entry.getValue();
						    System.out.println(key1 + ":" + value);
						    String temp = value.toString();						 
						    if(key.equals("0a") ){
						    	System.out.println("adding0");
						    	sortedsearchhistorystuff.set(0, temp);
						    }
						    if(key.equals("1a")) {
						    	System.out.println("adding1");
						    	sortedsearchhistorystuff.set(1, temp);
						    }
						    if(key.equals("2a")) {
						    	System.out.println("adding2");
						    	sortedsearchhistorystuff.set(2, temp);
						    }
						    if(key.equals("3a")) {
						    	System.out.println("adding3");
						    	sortedsearchhistorystuff.set(3, temp);
						    }
						}
					}
					
					for(String s:sortedsearchhistorystuff) {
						System.out.println("sorted:" + s);
					}

					session.setAttribute("searchHistory", sortedsearchhistorystuff);
					System.out.println("process done");
					process_done = true;
					
				}
			    @Override
			    public void onCancelled(DatabaseError databaseError) {
			    	System.out.println("The read failed: " + databaseError.getCode());
			    }
			});
			
			
			//get the favorites and put into array
			String url_str = "https://cs-310-project-2.firebaseio.com/users/" + username + "/favorites.json";
			URL url = new URL(url_str);
			BufferedReader br;
			ArrayList<String> newfavoritesArray = new ArrayList<>();
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
					newfavoritesArray = new ArrayList<>();
					for(int i = 0; i < favoritesArray.length; i++) {
						newfavoritesArray.add(favoritesArray[i]);
					}
					System.out.print("Favorites List for " + username + ": ");
					for(String s : newfavoritesArray) {
						System.out.print(s + ",");
					}
					session.setAttribute("favorites_List", newfavoritesArray);
					System.out.println();
					
					//NEED TO ADD TO SESSION:
					System.out.println("ADDING TO FAV DATA SESSION NOW");
					ArrayList<ArrayList<String>> fav_data;
					for(String s : newfavoritesArray) {
						fav_data = add_city(s);
						session.setAttribute("fav_data", fav_data);
						
					}
					
				
					//favoritesStr = favoritesArray.length == 0 ? "" : "[\"" + String.join("\", \"", favoritesArray) + "\"]";
				} else {
					newfavoritesArray = new ArrayList<>();
					session.setAttribute("favorites_List", newfavoritesArray);
				}

				
				//need favorites in an array of strings
				//likes in a nested array so that each entry in the array has the city and the int 
				
				//get likes and put into object
				System.out.println("1");
				String url_str1 = "https://cs-310-project-2.firebaseio.com/likes.json";
		        URL url1 = new URL(url_str1);
		        BufferedReader br1;
		        
		             br1 = new BufferedReader(new InputStreamReader(url1.openStream()));

		             str = "";
		             api_response = "";
		             while (null != (str = br1.readLine())) {
		                 api_response += str;
		             }
		             System.out.println("2");
		             String likesStr = "{}";
		             if(!api_response.equals("null")) {
		            	 System.out.println("3");
		            	 Gson gson = new Gson();
		            	 System.out.println("API REQUEST: " + api_response);
		 				Map<String, Number> likesMap = gson.fromJson(api_response, Map.class);
		 				for(Map.Entry<String, Number> e : likesMap.entrySet()) {
		 					System.out.println(e.getKey() + ":" + e.getValue());
		 				}
		 				session.setAttribute("likes", likesMap);
		 				System.out.println("likesMap size: " + Integer.toString(likesMap.size()) );
		 				
		 				
		             }
		      
			} catch (Exception e) {
				System.out.println("Location not found");
			}
			
			//I dont think the time wait actually works lol
			for(int i = 0; i < 10;  i++) {
				//System.out.println(i);
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(process_done) {
					break;
				}
			}
			
//			System.out.println("ADDING TO FAV DATA SESSION NOW");
//			ArrayList<ArrayList<String>> fav_data = new ArrayList<ArrayList<String>>();
//			for(String s : newfavoritesArray) {
//				add_city(fav_data, s);
//				
//			}
//			session.setAttribute("fav_data", fav_data);

			System.out.println("I am now redirecting");
			this.response.sendRedirect("HomePage.jsp");

		}else {
			// update error message in session and redirect to login page
			out.print("login unsuccessful");
			session.setAttribute("login_error_message", "input information is incorrect");
			response.sendRedirect("login.jsp");
		}
		out.flush();
	}
	
	
	
	private boolean signup(String email, String password) throws InterruptedException, IOException {

		if(password.length() < 6) {
			signup_redirect(false);
			return true;
		}
		
		// Connect to firebase
		
		DatabaseReference ref = this.database.getReference("users");
		System.out.println("Signup called");
	
		
		ref.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
		  @Override
		  public void onDataChange(DataSnapshot dataSnapshot) {
		    System.out.println("key: " + dataSnapshot.getKey());
		    System.out.println("val: " + dataSnapshot.getValue());
		   
		    // Email does not exist
		    if(dataSnapshot.getValue() == null) { 
		    	try {
		    		signup_redirect(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }else { // Email does exist
		    	try {
		    		signup_redirect(false);
		    	}catch(IOException e) { 
		    		e.printStackTrace();
		    	}
		    }
		  }

		  @Override
		  public void onCancelled(DatabaseError databaseError) {
		    System.out.println("The read failed: " + databaseError.getCode());
		  }
		});
		
		
		for(int i = 0; i < 20;  i++) {
			TimeUnit.SECONDS.sleep(1);
			if(process_done) {
				break;
			}			
		}		
		
		return true;
		
		// TODO: check whether email existis
		
	}
	
	private void signup_redirect(boolean success) throws IOException { 
		String email = request.getParameter("email");
		String password = hash_password(request.getParameter("password"));
		System.out.println("signup_redirect called with " + success);
		process_done = true;
		if(success) {
			// Get DataReference at users
			DatabaseReference ref = this.database.getReference("users");			
			
			// Create a map with user information
			Map<String, Object> user_info = new HashMap<>(); 
			user_info.put("email", email);
			user_info.put("password", password);
			
			// Register user
			DatabaseReference new_user = ref.push();
			String username = new_user.getKey();
			new_user.updateChildrenAsync(user_info);
			
			out.print("signup successful ");
			out.print(password);
			resetSession(session);
			session.setAttribute("uid", username);

			session.setAttribute("email", email);

			session.setAttribute("username", username);

			System.out.println("username: " + username);
			ArrayList<String> newfavoritesArray = new ArrayList<>();
			session.setAttribute("favorites_List", newfavoritesArray);
			
			
			boolean process = false;
			String str = "";
			String api_response = "";
			
			String url_str1 = "https://cs-310-project-2.firebaseio.com/likes.json";
	        URL url1 = new URL(url_str1);
	        BufferedReader br1;
	        
             br1 = new BufferedReader(new InputStreamReader(url1.openStream()));

             str = "";
             api_response = "";
             while (null != (str = br1.readLine())) {
                 api_response += str;
             }
             System.out.println("2");
             String likesStr = "{}";
             if(!api_response.equals("null")) {
            	 System.out.println("3");
            	 Gson gson = new Gson();
            	 System.out.println("API REQUEST: " + api_response);
 				Map<String, Number> likesMap = gson.fromJson(api_response, Map.class);
 				for(Map.Entry<String, Number> e : likesMap.entrySet()) {
 					System.out.println(e.getKey() + ":" + e.getValue());
 				}
 				session.setAttribute("likes", likesMap);
 				System.out.println("likesMap size: " + Integer.toString(likesMap.size()) );
 				process = true;
 				
             } 
			
			response.sendRedirect("HomePage.jsp");
		}else {
			out.print("signup unsuccessful");
			if(request.getParameter("password").length() < 6) {
				session.setAttribute("signup_error_message", "the password must be longer than 5 characters");
			}else {
				session.setAttribute("signup_error_message", "the email is already taken");				
			}
			response.sendRedirect("signup.jsp");
		}
		out.flush();
	}
	
	private void signout(HttpSession session) throws IOException {
		// Resetting 
		session.setAttribute("uid", null);
		session.setAttribute("signup_error_message", null);
		session.setAttribute("login_error_message", null);
		session.setAttribute("email", null);
		out.print("signout successful");
		
		response.sendRedirect("login.jsp");
		out.flush();
	}
	
	private void resetSession(HttpSession session) {
		session.setAttribute("initialized", true);
		session.setAttribute("location", "");
		session.setAttribute("date", "");
		session.setAttribute("temp", 0.0);
		session.setAttribute("desc", "");
		session.setAttribute("img", "");
		session.setAttribute("prompter", "Search for a city's weather!");
		session.setAttribute("last_page", "");
		session.setAttribute("list_activity", new ArrayList<City>());
		session.setAttribute("list_vacation", new ArrayList<City>());
		session.setAttribute("vacation_error", "");
		session.setAttribute("activity_error", "");
		session.setAttribute("searchHistory", null);

		
		ArrayList<ArrayList<String>> fav_data = new ArrayList<>();
		for(int i = 0; i < 23; i++){
			fav_data.add(new ArrayList<String>());
		}
		session.setAttribute("fav_data", fav_data);	
		session.setAttribute("city_selected", false);
		session.setAttribute("city_data", new ArrayList<String>());
		session.setAttribute("unit", true);
	}
	
	private String hash_password (String password) {
		
		String hashed_password = null;
		
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            hashed_password = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
            return null;
        }


		return hashed_password; 
	}
}

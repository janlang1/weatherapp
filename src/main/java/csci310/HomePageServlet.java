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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
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
 * Servlet implementation class HomePageServlet
 */
@WebServlet("/HomePageServlet")
public class HomePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String API_KEY = System.getenv("API_KEY");
	FirebaseDatabase database;
	HttpSession session;
	boolean process_done = false;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomePageServlet() {
        super();
        // TODO Auto-generated constructor stub
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
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get parameters from GET method
		session = request.getSession();
		String loc = request.getParameter("weatherloc");
		System.out.println("loc: " + loc);
		String unit_input = request.getParameter("unit");
		System.out.println(unit_input);
		PrintWriter out = response.getWriter();
		
		// Return if user location input is empty
		if(loc.equals("")){
			out.print("blank submit");
			
			// TODO: implement displaying "No weather data found"
			session.setAttribute("location", "No Location Found");
			session.setAttribute("prompter", " ");
			response.sendRedirect("HomePage.jsp");
			return;
		}
		
		String unit;
		if (unit_input == null) {
			System.out.println("unit input is null -> imperiral");
			unit = "imperial";
		}else{
			System.out.println("user input is not null -> metric");
			unit = "metric";
		}
		
		String url_str;
		
		if (loc.matches(".*\\d.*")) { // includes number
			url_str = "http://api.openweathermap.org/data/2.5/weather?zip=" + loc + "&units=" + unit + "&appid="+ API_KEY;
	 		System.out.println("numbers in the string");
	 		
	 	}else {
	 		url_str = "http://api.openweathermap.org/data/2.5/weather?q=" + loc + "&units=" + unit + "&appid="+ API_KEY;
	 		System.out.println("input was a city name");
	 	}
		// Call API and get data
		URL url = new URL(url_str);
		BufferedReader br;
		try{
			br = new BufferedReader(new InputStreamReader(url.openStream()));
		} catch (Exception e) {
			out.print("Exception");
			System.out.println("Location not found");
			// TODO: implement displaying "No weather data found"
			
			session.setAttribute("location", "No Location Found");
			session.setAttribute("prompter", " ");
			response.sendRedirect("HomePage.jsp");
			return;
		}
		String str = "";
		String api_response = "";
		while (null != (str = br.readLine())) {
			api_response += str;
		}
		
		JsonObject jsonObject = new JsonParser().parse(api_response).getAsJsonObject();
		
		String location = jsonObject.get("name").getAsString();
		double temp = jsonObject.get("main").getAsJsonObject().get("temp").getAsDouble();	
		String desc = jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString();
		String icon = jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();

		int time_difference = jsonObject.get("timezone").getAsInt();
		// Get current time
		Calendar cal = Calendar.getInstance();
		//System.out.println(cal);
		cal.add(Calendar.SECOND, time_difference);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

		String date = dateFormat.format(cal.getTime());
		System.out.println(date);
		
		// Attach information to session
		session.setAttribute("location", location);
		session.setAttribute("date", date);
		session.setAttribute("temp", temp);
		session.setAttribute("desc", desc);
		session.setAttribute("img", "http://openweathermap.org/img/w/" + icon + ".png");
		session.setAttribute("prompter", " ");
		out.print(location);
		String user = (String)session.getAttribute("user");
		String username = (String)session.getAttribute("username");
		System.out.println("uid" + username);
		
		//check if location is already in the search history?
		boolean repeat = false;
		ArrayList< String > search = (ArrayList< String>)session.getAttribute("searchHistory");
		if(search == null){
			System.out.println("NO SEARCH IN SESSION");
			search = new ArrayList< String >();
		}
		for(String m : search){ 
			if(m.equals(location)) {
				System.out.println("REPEAT");
				repeat = true;
			}
		}
		int index = search.size();
		System.out.println(Integer.toString(index));
		
		
		//have all search fields, ready to add to firebase db
		if(!repeat && search.size() < 4) {
			DatabaseReference ref = database.getReference("users/"+username+"/search_history");
			//DatabaseReference searchRef = ref.child("search_histro");
			System.out.println("save search terms called");
			Map<String, Object> search_info = new HashMap<>(); 
			//generate random hex to not override stuff in database
		    String s = Integer.toString(index) + "a";
			search_info.put(s, new SearchTerm(location));
			ref.updateChildrenAsync(search_info);
		}
		if(!repeat && search.size() == 4) {
			//need to push out of queue
			DatabaseReference ref = database.getReference("users/"+username+"/search_history");
			//DatabaseReference searchRef = ref.child("search_histro");
			System.out.println("save search terms called");
			Map<String, Object> search_info = new HashMap<>(); 
			//remove 0th index and move all items up
			search.remove(0);
			search.add(location);
			for(int i = 0; i < search.size(); i++) {
				search_info.put(Integer.toString(i)+ "a", new SearchTerm(search.get(i)) );
			}
			
			//generate random hex to not override stuff in database
		    //String s = Integer.toString(index) + "a";
			//earch_info.put("0a", new SearchTerm(location));
			ref.updateChildrenAsync(search_info);
		} 
		process_done = false;
		DatabaseReference ref = this.database.getReference("users");
		ref = this.database.getReference("users/"+username+"/search_history");
		//System.out.println("Login called");
		//Query query = ref.orderByChild("users").equalTo("a");
		
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			  @Override
			  public void onDataChange(DataSnapshot dataSnapshot) {
				System.out.println("hello");
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
					    System.out.println(key1 + ":" + value);
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
		for(int i = 0; i < 1;  i++) {
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
		//I dont think the time wait actually works lol

		System.out.println("I am now redirecting");
		//end of firebase code
		// Return to the web page with the data
		response.sendRedirect("HomePage.jsp");
		out.flush();
	}
}

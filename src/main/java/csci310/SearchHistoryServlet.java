//package csci310;
//
//import java.io.*;
//import java.net.URL;
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import com.google.gson.*;
//import com.google.api.client.http.MultipartContent.Part;
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//
//
///**
// * Servlet implementation class 
// */
//@WebServlet("/SearchHistoryServlet")
//public class SearchHistoryServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	FirebaseDatabase database;
//	HttpSession session;
//	boolean process_done = false;
//    /**@see HttpServlet#HttpServlet()
//     */
//    public SearchHistoryServlet() {
//        super();
//        FirebaseOptions options = null;
//		try {
//			options = new FirebaseOptions.Builder()
//				    .setCredentials(GoogleCredentials.getApplicationDefault())
//				    .setDatabaseUrl("https://cs-310-project-2.firebaseio.com/")
//				    .build();
//		} catch (IOException e) {e.printStackTrace();}
//		if(FirebaseApp.getApps().isEmpty()) { //<--- check with this line
//            FirebaseApp.initializeApp(options);
//        }
//		database = FirebaseDatabase.getInstance();
//        
//    }
//	/** @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub		
//		PrintWriter out = response.getWriter();
//		session = request.getSession();
//		DatabaseReference ref = this.database.getReference("users/a/search_history");
//		//System.out.println("Login called");
//		
//		//Query query = ref.orderByChild("users").equalTo("a");
//	
//		ref.addListenerForSingleValueEvent(new ValueEventListener() {
//		  @Override
//		  public void onDataChange(DataSnapshot dataSnapshot) {
//			System.out.println("hello");
//		    System.out.println("key: " + dataSnapshot.getKey());
//		    System.out.println("val: " + dataSnapshot.getValue());
//		    // Email does not exist
//		    if(dataSnapshot.getValue() == null) { 
//		    	//					login_redirect(false);
//				System.out.println(dataSnapshot.getValue());
//				//login_redirect(null);
//		    }
//		    ArrayList< Map<String, Object> > searchhistorystuff = new ArrayList<>();
//		    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
//		    for (String key : map.keySet()) {
//		    	System.out.println(key);
//		    	Map<String, Object> searchterm = (Map<String, Object>)map.get(key);
//		    	for (Map.Entry<String, Object> entry : searchterm.entrySet()) {
//		    	    String key1 = entry.getKey();
//		    	    Object value = entry.getValue();
//		    	    System.out.println(key1 + ":" + value);
//		    	}
//		    	searchhistorystuff.add(searchterm);
//		    }
//		    session.setAttribute("searchHistory", searchhistorystuff);
//		    process_done = true;
//		  }
//		  @Override
//		  public void onCancelled(DatabaseError databaseError) {
//		    System.out.println("The read failed: " + databaseError.getCode());
//		  }
//		});
//		for(int i = 0; i < 20;  i++) {
//			try {
//				TimeUnit.SECONDS.sleep(1);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			if(process_done) {break;}
//		}		
//		// Go back to the original page		
//		response.sendRedirect("SearchHistory.jsp");
//		out.flush();
//	}
//
//}

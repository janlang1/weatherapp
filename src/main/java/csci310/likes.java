package csci310;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

@WebServlet("/likes")
public class likes extends HttpServlet{
	 public likes() throws IOException {
	        super();
	        // TODO Auto-generated constructor stub
            FileInputStream serviceAccount =
                new FileInputStream("src/dataBaseKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://cs-310-project-2.firebaseio.com")
                .build();

    		if(FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

	    }
		 
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String favoriteCity = request.getParameter("cityname");
		 final PrintWriter out = response.getWriter();
         response.setContentType("application/json");
         response.setCharacterEncoding("UTF-8");

         String url_str = "https://cs-310-project-2.firebaseio.com/likes/" + favoriteCity + ".json";
         URL url = new URL(url_str);
         BufferedReader br;
         try {
             br = new BufferedReader(new InputStreamReader(url.openStream()));

             String str = "";
             String api_response = "";
             while (null != (str = br.readLine())) {
                 api_response += str;
             }
             
             out.print(api_response);
             out.flush();
         } catch (Exception e) {
             System.out.println("Location not found");
         }
		}
}
	 
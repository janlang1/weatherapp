package csci310;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class AnalysisServletTest extends Mockito{
	
	@Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;
    @Mock
    HttpServletRequest request2;

    @Mock
    HttpServletResponse response2;
    
    @Mock
    HttpSession session;
    
    @Mock
    RequestDispatcher rd;
    
    @Mock
    private static AnalysisServlet servlet;

    @Before
    public void setUp() throws Exception {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        request2 = mock(HttpServletRequest.class);
        response2 = mock(HttpServletResponse.class);
        servlet = new AnalysisServlet();
        session = mock(HttpSession.class);
        rd = mock(RequestDispatcher.class);
        

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("last page")).thenReturn("Activity.jsp");
        when(request.getParameter("cityName")).thenReturn("Los Angeles");
        ArrayList<ArrayList<String>> dummy_data = new ArrayList<>();
        
        for(int i = 0; i < 20; i++) {
        	dummy_data.add(new ArrayList<String>());
        }

        dummy_data.get(0).add("Los Angeles");
        dummy_data.get(0).add("Boston");
        dummy_data.get(0).add("San Francisco");
        
        
        Random random = new Random();
        for(int i = 0; i < 3; i++) {
        	for(int j = 1; j < 20; j++) {
        		String random_num = String.valueOf(random.nextInt());
        		dummy_data.get(j).add(random_num);
        	}
        }

        when(request.getSession().getAttribute("fav_data")).thenReturn(dummy_data);
    }

    @Test
    public void testRemove() throws Exception {
    	when(request.getParameter("action")).thenReturn("remove");
    	
    	StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        when(response.getWriter()).thenReturn(out);
        new AnalysisServlet().doGet(request, response);
        String result = writer.getBuffer().toString();
        Assert.assertEquals("2", result);
    }
    
    @Test
    public void testAdd() throws Exception {
    	when(request.getParameter("cityName")).thenReturn("Chicago");
    	when(request.getParameter("action")).thenReturn("favorite");
    	
		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);
		when(response.getWriter()).thenReturn(out);
        new AnalysisServlet().doGet(request, response);
        String result = writer.getBuffer().toString();
        Assert.assertEquals("4", result);
    }
    
    @Test
    public void testDisplayCity() throws Exception {
    	when(request.getParameter("cityName")).thenReturn("Los Angeles");
    	when(request.getParameter("action")).thenReturn("display_city");
    	StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        when(response.getWriter()).thenReturn(out);
        new AnalysisServlet().doGet(request, response);
        String result = writer.getBuffer().toString();
        Assert.assertEquals("20", result);
        //does not change this test, given that it is included in size of favData, modify session values
    }
    
    @Test
    public void testLocationNotFoundInAddCity() throws Exception{
    	when(request.getParameter("cityName")).thenReturn("cealcje^(#&!(&$(!&aj");
    	when(request.getParameter("action")).thenReturn("favorite");
    	StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        when(response.getWriter()).thenReturn(out);
        new AnalysisServlet().doGet(request, response);
        String result = writer.getBuffer().toString();
        Assert.assertEquals("3", result);
    }
    
    @Test
    public void testRemovingCityNotInList() throws Exception{
    	when(request.getParameter("action")).thenReturn("remove");
    	when(request.getParameter("cityName")).thenReturn("Cape Town");
    	
    	StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        when(response.getWriter()).thenReturn(out);
        new AnalysisServlet().doGet(request, response);
        String result = writer.getBuffer().toString();
        Assert.assertEquals("3", result);
    }
    
    @Test
    public void testDisplayCityNotInList() throws Exception {
    	when(request.getParameter("cityName")).thenReturn("Cape Town");
    	when(request.getParameter("action")).thenReturn("display_city");
    	StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        when(response.getWriter()).thenReturn(out);
        new AnalysisServlet().doGet(request, response);
        String result = writer.getBuffer().toString();
        Assert.assertEquals("0", result);
    }
    
//    @Test
//    public void testImages() throws ServletException, IOException{
//    	when(request.getParameter("cityName")).thenReturn("Los Angeles");
//    	when(request.getParameter("action")).thenReturn("display_images");
//    	StringWriter writer = new StringWriter();
//        PrintWriter out = new PrintWriter(writer);
//        when(response.getWriter()).thenReturn(out);
//        new AnalysisServlet().doGet(request, response);
//        String result = writer.getBuffer().toString();
//        Assert.assertEquals("20", result);
        
//        
//    	City newCity = new City("Fake Location", "", 0,0 ,0,0);
//		//newCity.setLocation("Fake Location");
//		String toggle = null;
//		//set parameters for servlet use
//		when(request.getParameter("cityName")).thenReturn("Los Angeles");
//		when(request.getParameter("action")).thenReturn("display_images");
//        //when(request.getParameter("toggle")).thenReturn(toggle);
//        //write response of servlet doGet to StringWriter
//        StringWriter stringWriter = new StringWriter();
//        PrintWriter writer = new PrintWriter(stringWriter);
//        when(response2.getWriter()).thenReturn(writer);
//        servlet.doGet(request, response2);
//        //make sure city and toggle paramenters were called
//        verify(request2, atLeast(1)).getParameter("city");
//        verify(request2, atLeast(1)).getParameter("toggle");
//        verify(session).setAttribute("validResponse", false);
//        writer.flush();
//        newCity.setLocation("Los Angeles");
//        stringWriter = new StringWriter();
//        writer = new PrintWriter(stringWriter);
//        when(response.getWriter()).thenReturn(writer);
//        servlet.doGet(request, response);
//        verify(session).setAttribute("validResponse", true);
//        //need to check if these attributes are of this type
//        //verify(session).setAttribute("city", searchCity);
//        //verify(session).setAttribute("favCit", favCit);
//        writer.flush();
//        //make sure all five image files were replaced, given each filename is appended to the response when downloaded
//        Assert.assertTrue(stringWriter.toString().contains("Served at: "+request.getContextPath()+" image1.jpg image2.jpg image3.jpg"));
//    }
    
//    
//    @Test
//    public void testRequestDispatcher() throws Exception {
//    	verify(rd).forward(request, response);
//    }
//    @Test
//    public void testWaterActivity() throws Exception {
//    	when(request.getParameter("activity")).thenReturn("water");
//    	 StringWriter writer = new StringWriter();
//         PrintWriter out = new PrintWriter(writer);
//         when(response.getWriter()).thenReturn(out);
//        new VAAServlet().service(request, response);
//        String result = writer.getBuffer().toString();
//        Assert.assertEquals(result, "0100los angeles5water");
//    }
//    @Test
//    public void testOutdoorActivity() throws Exception {
//    	when(request.getParameter("activity")).thenReturn("outdoor");
//    	 StringWriter writer = new StringWriter();
//         PrintWriter out = new PrintWriter(writer);
//         when(response.getWriter()).thenReturn(out);
//        new VAAServlet().service(request, response);
//        String result = writer.getBuffer().toString();
//        Assert.assertEquals(result, "0100los angeles5outdoor");
//    }
//    @Test
//    public void testZipCodeInput() throws Exception {
//    	when(request.getParameter("currLocation")).thenReturn("90007");
//    	StringWriter writer = new StringWriter();
//        PrintWriter out = new PrintWriter(writer);
//        when(response.getWriter()).thenReturn(out);
//       new VAAServlet().service(request, response);
//       String result = writer.getBuffer().toString();
//       Assert.assertEquals(result, "0100900075snow");
//    }
//    @Test
//    public void testException() throws Exception {
//    	when(request.getParameter("currLocation")).thenReturn("sjdafa");
//    	StringWriter writer = new StringWriter();
//        PrintWriter out = new PrintWriter(writer);
//        when(response.getWriter()).thenReturn(out);
//        VAAServlet vs = new VAAServlet();
//       vs.service(request, response);
//       String result = writer.getBuffer().toString();
//       Assert.assertEquals(result, "Exception");
//    }
}

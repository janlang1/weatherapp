package csci310;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class RadiusTest extends Mockito{
	
	@Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;
    
    @Mock
    HttpSession session;
    
    @Mock
    RequestDispatcher rd;

    @Before
    public void setUp() throws Exception {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        rd = mock(RequestDispatcher.class);
        
        when(request.getParameter("tempLow")).thenReturn("0");
        when(request.getParameter("tempHigh")).thenReturn("100");
        when(request.getParameter("currLocation")).thenReturn("los angeles");
        when(request.getParameter("lon")).thenReturn("-118.2726");
        when(request.getParameter("lat")).thenReturn("34.0286");
        
        when(request.getParameter("radius")).thenReturn("100");
        
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/Vacation")).thenReturn(null);
        when(request.getRequestDispatcher("/Activity")).thenReturn(null);
    }
    //Vacation
//    @Test
//    public void testNumberOfResults_V() throws Exception {
//    	when(request.getParameter("activity")).thenReturn(null);
//        when(request.getParameter("jspname")).thenReturn("vacation");
//    	StringWriter writer = new StringWriter();
//        PrintWriter out = new PrintWriter(writer);
//        when(response.getWriter()).thenReturn(out);
//        
//        new VAAServlet().service(request, response);
//        String result = writer.getBuffer().toString();
//        Assert.assertEquals("100|5|27|12|", result);
//    }
    
//    @Test
//    public void testDistanceOfFirstCity_V() throws Exception {
//    	when(request.getParameter("activity")).thenReturn(null);
//        when(request.getParameter("jspname")).thenReturn("vacation");
//    	StringWriter writer = new StringWriter();
//        PrintWriter out = new PrintWriter(writer);
//        when(response.getWriter()).thenReturn(out);
//        
//        new VAAServlet().service(request, response);
//        String result = writer.getBuffer().toString();
//        Assert.assertEquals("VAA Successful", result);
//    }
//    
//    @Test
//    public void testDistanceOfFirstCityReverse_V() throws Exception {
//    	when(request.getParameter("activity")).thenReturn(null);
//        when(request.getParameter("jspname")).thenReturn("vacation");
//    	StringWriter writer = new StringWriter();
//        PrintWriter out = new PrintWriter(writer);
//        when(response.getWriter()).thenReturn(out);
//        new VAAServlet().service(request, response);
//        String result = writer.getBuffer().toString();
//        Assert.assertEquals("VAA Successful", result);
//    }
//    
//    @Test
//    public void testDistanceOfSecondCity_V() throws Exception {
//    	when(request.getParameter("activity")).thenReturn(null);
//        when(request.getParameter("jspname")).thenReturn("vacation");
//    	StringWriter writer = new StringWriter();
//        PrintWriter out = new PrintWriter(writer);
//        when(response.getWriter()).thenReturn(out);
//        
//        new VAAServlet().service(request, response);
//        String result = writer.getBuffer().toString();
//        Assert.assertEquals("VAA Successful", result);
//    }
//    
//    @Test
//    public void testReadingOfRadiusField_V() throws Exception {
//    	when(request.getParameter("activity")).thenReturn(null);
//        when(request.getParameter("jspname")).thenReturn("vacation");
//    	StringWriter writer = new StringWriter();
//    	PrintWriter out1 = new PrintWriter(writer);
//        when(response.getWriter()).thenReturn(out1);
//        new VAAServlet().service(request, response);
//        
//        String result2 = writer.getBuffer().toString();
//        Assert.assertEquals("VAA Successful", result2);
//    }
//    
//    //activity
//    @Test
//    public void testNumberOfResults_A() throws Exception {
//    	when(request.getParameter("activity")).thenReturn("snow");
//        when(request.getParameter("jspname")).thenReturn("activity");
//      
//    	StringWriter writer = new StringWriter();
//        PrintWriter out = new PrintWriter(writer);
//        when(response.getWriter()).thenReturn(out);
//        
//        new VAAServlet().service(request, response);
//        String result = writer.getBuffer().toString();
//        Assert.assertEquals("VAA Successful", result);
//    }
//    
//    @Test
//    public void testReadingOfRadiusField_A() throws Exception {
//    	when(request.getParameter("activity")).thenReturn(null);
//        when(request.getParameter("jspname")).thenReturn("vacation");
//    	StringWriter writer = new StringWriter();
//        PrintWriter out = new PrintWriter(writer);
//        when(response.getWriter()).thenReturn(out);
//        
//        new VAAServlet().service(request, response);
//        String result = writer.getBuffer().toString();
//        Assert.assertEquals("VAA Successful", result);
//    }
//    
//    @Test
//    public void testDistanceOfFirstCity_A() throws Exception {
//    	when(request.getParameter("activity")).thenReturn(null);
//        when(request.getParameter("jspname")).thenReturn("vacation");
//    	StringWriter writer = new StringWriter();
//        PrintWriter out = new PrintWriter(writer);
//        when(response.getWriter()).thenReturn(out);
//        
//        new VAAServlet().service(request, response);
//        String result = writer.getBuffer().toString();
//        Assert.assertEquals("VAA Successful", result);
//    }
//    
//    @Test
//    public void testDistanceOfFirstCityReverse_A() throws Exception {
//    	when(request.getParameter("activity")).thenReturn(null);
//        when(request.getParameter("jspname")).thenReturn("vacation");
//    	StringWriter writer = new StringWriter();
//        PrintWriter out = new PrintWriter(writer);
//        when(response.getWriter()).thenReturn(out);
//        new VAAServlet().service(request, response);
//        String result = writer.getBuffer().toString();
//        Assert.assertEquals("VAA Successful", result);
//    }
}

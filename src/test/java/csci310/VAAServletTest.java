package csci310;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class VAAServletTest extends Mockito{
	
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
        when(request.getParameter("numResults")).thenReturn("5");
        when(request.getParameter("activity")).thenReturn("snow");
        when(request.getParameter("radius")).thenReturn("100");
        when(request.getParameter("jspname")).thenReturn("activity");
        
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/Vacation.jsp")).thenReturn(null);
        when(request.getRequestDispatcher("/Activity.jsp")).thenReturn(null);
       
    }
    
    /*
     * Things to test
     * - activity search
     * - vacation search
     */
    
    @Test
    public void testActivityBasic() throws Exception {
    	StringWriter writer = new StringWriter();
    	PrintWriter out = new PrintWriter(writer);
    	when(response.getWriter()).thenReturn(out);
		new VAAServlet().service(request, response);
		String result = writer.getBuffer().toString();
		Assert.assertEquals("VAA Successful", result);
    }
    
//    @Test
//    public void testVacationBasicSnow() throws Exception {
//    	when(request.getParameter("jspname")).thenReturn("vacation");
//    	StringWriter writer = new StringWriter();
//    	PrintWriter out = new PrintWriter(writer);
//    	when(response.getWriter()).thenReturn(out);
//		new VAAServlet().service(request, response);
//		String result = writer.getBuffer().toString();
//		Assert.assertEquals("VAA Successful", result);
//    }
    
//    @Test
//    public void testActivityOutdoorInRange() throws Exception {
//    	when(request.getParameter("activity")).thenReturn("outdoor");
//    	when(request.getParameter("currLocation")).thenReturn("Minneapolis");
//    	
//    	StringWriter writer = new StringWriter();
//    	PrintWriter out = new PrintWriter(writer);
//    	when(response.getWriter()).thenReturn(out);
//		new VAAServlet().service(request, response);
//		String result = writer.getBuffer().toString();
//		Assert.assertEquals("VAA Successful", result);
//    }
    
    @Test
    public void testActivityWaterInRange() throws Exception {
    	when(request.getParameter("activity")).thenReturn("water");
    	when(request.getParameter("currLocation")).thenReturn("Cape Town");

    	StringWriter writer = new StringWriter();
    	PrintWriter out = new PrintWriter(writer);
    	when(response.getWriter()).thenReturn(out);
		new VAAServlet().service(request, response);
		String result = writer.getBuffer().toString();
		Assert.assertEquals("VAA Successful", result);
    }
        
    @Test
    public void testNonExistingActivity() throws Exception {
    	when(request.getParameter("activity")).thenReturn("random activity");
    	StringWriter writer = new StringWriter();
    	PrintWriter out = new PrintWriter(writer);
    	when(response.getWriter()).thenReturn(out);
		new VAAServlet().service(request, response);
		String result = writer.getBuffer().toString();
		Assert.assertEquals("invalid input", result);
    }
    
    
    // Invalid input test
    	// Activity - currLocation, numResults, activity, radius    
    	// Vacation - tempLow, tempHigh, numResults, currLocation, radius
    
    @Test
    public void testInvalidRadiusInputVacation() throws Exception {
    	when(request.getParameter("jspname")).thenReturn("vacation");
    	when(request.getParameter("radius")).thenReturn("");
    	StringWriter writer = new StringWriter();
    	PrintWriter out = new PrintWriter(writer);
    	when(response.getWriter()).thenReturn(out);
		new VAAServlet().service(request, response);
		String result = writer.getBuffer().toString();
		Assert.assertEquals("invalid input", result);    	
    }
        
    @Test
    public void testInvalidTempHighInputVacation() throws Exception {
    	when(request.getParameter("jspname")).thenReturn("vacation");
    	when(request.getParameter("tempHigh")).thenReturn("");
    	StringWriter writer = new StringWriter();
    	PrintWriter out = new PrintWriter(writer);
    	when(response.getWriter()).thenReturn(out);
		new VAAServlet().service(request, response);
		String result = writer.getBuffer().toString();
		Assert.assertEquals("invalid input", result);    	
    }
    
    @Test
    public void testInvalidTempLowInputVacation() throws Exception {
    	when(request.getParameter("jspname")).thenReturn("vacation");
    	when(request.getParameter("tempLow")).thenReturn("");
    	StringWriter writer = new StringWriter();
    	PrintWriter out = new PrintWriter(writer);
    	when(response.getWriter()).thenReturn(out);
		new VAAServlet().service(request, response);
		String result = writer.getBuffer().toString();
		Assert.assertEquals("invalid input", result);    	
    }    
        
    @Test
    public void testInvalidActivityInputActivity() throws Exception {
    	when(request.getParameter("jspname")).thenReturn("activity");
    	when(request.getParameter("activity")).thenReturn("");
    	StringWriter writer = new StringWriter();
    	PrintWriter out = new PrintWriter(writer);
    	when(response.getWriter()).thenReturn(out);
		new VAAServlet().service(request, response);
		String result = writer.getBuffer().toString();
		Assert.assertEquals("invalid input", result);    	
    }
    
    @Test
    public void testInvalidRadiusInputActivity() throws Exception {
    	when(request.getParameter("jspname")).thenReturn("activity");
    	when(request.getParameter("radius")).thenReturn("");
    	StringWriter writer = new StringWriter();
    	PrintWriter out = new PrintWriter(writer);
    	when(response.getWriter()).thenReturn(out);
		new VAAServlet().service(request, response);
		String result = writer.getBuffer().toString();
		Assert.assertEquals("invalid input", result);    	
    }
    
    
    
//    @Test
//    public void testPaginationForVacation() throws Exception {
//    	when(request.getParameter("pageNum")).thenReturn("1");
//    	when(request.getParameter("whatPage")).thenReturn("vacation");
//    	StringWriter writer = new StringWriter();
//    	PrintWriter out = new PrintWriter(writer);
//    	when(response.getWriter()).thenReturn(out);
//    	new VAAServlet().service(request, response);
//		String result = writer.getBuffer().toString();
//		Assert.assertEquals("1vacation", result);
//    }
//
//    @Test
//    public void testPaginationForActivity() throws Exception {
//    	when(request.getParameter("pageNum")).thenReturn("1");
//    	when(request.getParameter("whatPage")).thenReturn("activity");
//    	StringWriter writer = new StringWriter();
//    	PrintWriter out = new PrintWriter(writer);
//    	when(response.getWriter()).thenReturn(out);
//    	new VAAServlet().service(request, response);
//		String result = writer.getBuffer().toString();
//		Assert.assertEquals("1activity", result);
//    }
//    

//    @Test
//    public void testRequestParameters() throws Exception {
//    	 StringWriter writer = new StringWriter();
//         PrintWriter out = new PrintWriter(writer);
//         when(response.getWriter()).thenReturn(out);
//        new VAAServlet().service(request, response);
//        String result = writer.getBuffer().toString();
//        Assert.assertEquals(result, "0100los angeles5snow");
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
//    	when(request.getParameter("currLocation")).thenReturn("sjdafa!@(&#");
//    	StringWriter writer = new StringWriter();
//        PrintWriter out = new PrintWriter(writer);
//        when(response.getWriter()).thenReturn(out);
//        VAAServlet vs = new VAAServlet();
//       vs.service(request, response);
//       String result = writer.getBuffer().toString();
//       Assert.assertEquals(result, "Exception");
//    }


}

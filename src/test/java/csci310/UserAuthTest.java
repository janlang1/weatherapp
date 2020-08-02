package csci310;

import java.io.PrintWriter;
import java.io.StringWriter;
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

public class UserAuthTest extends Mockito{
	
	@Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;
    
    @Mock
    HttpSession session;
    
    @Mock
    RequestDispatcher rd;
    
    UserAuthServlet servlet;

    @Before
    public void setUp() throws Exception {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        rd = mock(RequestDispatcher.class);
        servlet = new UserAuthServlet();
         
        when(request.getSession()).thenReturn(session);        
    }
    
    //Vacation
    @Test
    public void testSuccessfulLogin() throws Exception {
    	when(request.getParameter("action")).thenReturn("login");
    	when(request.getParameter("email")).thenReturn("example@example.com");
    	when(request.getParameter("password")).thenReturn("password");

    	StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        when(response.getWriter()).thenReturn(out);

    	servlet.doPost(request, response);
    	
        
        String result = writer.getBuffer().toString();
        
    	Assert.assertEquals("login successful", result);
    }
    
    public void testUnsuccessfulLogin() throws Exception {
    	when(request.getParameter("action")).thenReturn("login");
    	when(request.getParameter("email")).thenReturn("example@example.com");
    	when(request.getParameter("password")).thenReturn("wrong_password");
    	StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        when(response.getWriter()).thenReturn(out);


    	servlet.doPost(request, response);
    	
        
        String result = writer.getBuffer().toString();
        
    	Assert.assertEquals("login unsucessful", result);
    }
    
    @Test
    public void testSuccessfulSignup() throws Exception {
    	when(request.getParameter("action")).thenReturn("signup");
    	
    	String email = "example" + getRandomString() + "example.com";
    	when(request.getParameter("email")).thenReturn(email);
    	when(request.getParameter("password")).thenReturn("password");
    	StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        when(response.getWriter()).thenReturn(out);
    	
    	servlet.doPost(request, response);
    	
        
        String result = writer.getBuffer().toString();
        String hashed_password = result.substring(18);
        System.out.println("pass hasehd: " + hashed_password);
        result = result.substring(0, 17);
        System.out.println("result: " + result);

        
        boolean signedup = result.equals("signup successful");
        boolean hashed = !hashed_password.equals("password");
        
    	Assert.assertTrue(signedup && hashed);
    }
    
    @Test
    public void testUnsuccessfulSignup() throws Exception {
    	when(request.getParameter("action")).thenReturn("signup");
    	
    	when(request.getParameter("email")).thenReturn("example@example.com");
    	when(request.getParameter("password")).thenReturn("wrong");
    	StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        when(response.getWriter()).thenReturn(out);
    	
    	servlet.doPost(request, response);
    	
        
        String result = writer.getBuffer().toString();
        
    	Assert.assertEquals("signup unsuccessful", result);
    }
    
    @Test
    public void testSuccessfulSignout() throws Exception {
    	when(request.getParameter("action")).thenReturn("signout");
    	when(session.getAttribute("uid")).thenReturn("example@example.com");
    	StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        when(response.getWriter()).thenReturn(out);

        
    	servlet.doPost(request, response);
    	
        
        String result = writer.getBuffer().toString();
    	Assert.assertEquals("signout successful", result);
    }
    
    @Test
    public void lessThan6CharPassword() throws Exception {
    	when(request.getParameter("action")).thenReturn("signup");
    	when(request.getParameter("email")).thenReturn("whatever@whatever.com");
    	when(request.getParameter("password")).thenReturn("1");
    	StringWriter writer = new StringWriter();
    	PrintWriter out = new PrintWriter(writer);
    	when(response.getWriter()).thenReturn(out);
    	
    	servlet.doPost(request, response);
    	
    	String result = writer.getBuffer().toString();
    	Assert.assertEquals("signup unsuccessful", result);
    }
    
    
    @Test
    public void testSignoutWithoutLoggingin() throws Exception {
    	when(request.getParameter("action")).thenReturn("signout");
    	StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        when(response.getWriter()).thenReturn(out);
        
        servlet.doPost(request, response);
        
        String result = writer.getBuffer().toString();
    	Assert.assertEquals("signout successful", result);
    }
    
    protected String getRandomString() {
        String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder str_builder = new StringBuilder();
        Random rnd = new Random();
        while (str_builder.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * ALPHABET.length());
            str_builder.append(ALPHABET.charAt(index));
        }
        String randStr = str_builder.toString();
        return randStr;
    }

}

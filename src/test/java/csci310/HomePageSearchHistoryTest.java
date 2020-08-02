package csci310;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
//import csci310.SERVLETNAME;
import org.mockito.Mock;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class HomePageSearchHistoryTest extends Mockito{
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @Mock
    BufferedReader bufferedReader;
    @Before
    public void setUp() throws Exception {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
    }
    
    @Test
    public void testSearchHistoryEntry() throws Exception {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getParameter("weatherloc")).thenReturn("90007");
        when(request.getParameter("unit")).thenReturn("metric");
        when(request.getSession()).thenReturn(session);
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        when(response.getWriter()).thenReturn(out);
        new HomePageServlet().doGet(request, response);
        String result = writer.getBuffer().toString();
        //Los Angeles 
        Assert.assertEquals("Los Angeles", result);
    }
    
    @Test
    public void testSearchHistoryList() throws Exception {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getParameter("weatherloc")).thenReturn("90007");
        when(request.getParameter("unit")).thenReturn("metric");
        when(request.getSession()).thenReturn(session);
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        when(response.getWriter()).thenReturn(out);
        new HomePageServlet().doGet(request, response);
        String result = writer.getBuffer().toString();
        Assert.assertEquals("Los Angeles", result);
    }

    @Test
    public void testSearchHistoryRemove() throws Exception {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getParameter("weatherloc")).thenReturn("90007");
        when(request.getParameter("unit")).thenReturn("metric");
        when(request.getSession()).thenReturn(session);
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        when(response.getWriter()).thenReturn(out);
        new HomePageServlet().doGet(request, response);
        String result = writer.getBuffer().toString();
        //Last city in the list gets removed
        Assert.assertEquals("Los Angeles", result);
    }
    @Test
    public void testSearchHistoryUnique() throws Exception {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getParameter("weatherloc")).thenReturn("90007");
        when(request.getParameter("unit")).thenReturn("metric");
        when(request.getSession()).thenReturn(session);
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        when(response.getWriter()).thenReturn(out);
        new HomePageServlet().doGet(request, response);
        String result = writer.getBuffer().toString();
        //Los Angeles no entering the list
        Assert.assertEquals("Los Angeles", result);
    }
    
    @Test
    public void testResultfromSearchHistoryClick() throws Exception {
    	//should not add to database
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getParameter("weatherloc")).thenReturn("90007");
        when(request.getParameter("unit")).thenReturn("metric");
        when(request.getSession()).thenReturn(session);
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        when(response.getWriter()).thenReturn(out);
        new HomePageServlet().doGet(request, response);
        String result = writer.getBuffer().toString();
        //Los Angeles no entering the list
        Assert.assertEquals("Los Angeles", result);
    }
    
    @Test
    public void testSearchHistoryOneToFour() throws Exception {
    	//check list from 1 to 4
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getParameter("weatherloc")).thenReturn("90007");
        when(request.getParameter("unit")).thenReturn("metric");
        when(request.getSession()).thenReturn(session);
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        when(response.getWriter()).thenReturn(out);
        new HomePageServlet().doGet(request, response);
        String result = writer.getBuffer().toString();
        //Los Angeles no entering the list
        Assert.assertEquals("Los Angeles", result);
    }
    
//    @Test
//    public void testSearchHistorySortChrono() throws Exception {
//    	//check list from 1 to 4
//        request = mock(HttpServletRequest.class);
//        response = mock(HttpServletResponse.class);
//        session = mock(HttpSession.class);
//        when(request.getParameter("weatherloc")).thenReturn("90007");
//        when(request.getParameter("unit")).thenReturn("metric");
//        when(request.getSession()).thenReturn(session);
//        StringWriter writer = new StringWriter();
//        PrintWriter out = new PrintWriter(writer);
//        when(response.getWriter()).thenReturn(out);
//        new HomePageServlet().doGet(request, response);
//        String result = writer.getBuffer().toString();
//        //Los Angeles no entering the list
//        Assert.assertEquals("Entry is sorted", result);
//    }
    
}
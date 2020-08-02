//package csci310;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.Request;
//import org.mockito.Mockito;
////import csci310.SERVLETNAME;
//import org.mockito.Mock;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//public class HelloTest extends Mockito{
//    @Mock
//    HttpServletRequest request;
//    @Mock
//    HttpServletResponse response;
//    @Mock
//    HttpSession session;
//    @Mock
//    BufferedReader bufferedReader;
//    @Before
//    public void setUp() throws Exception {
//        request = mock(HttpServletRequest.class);
//        response = mock(HttpServletResponse.class);
//        session = mock(HttpSession.class);
//    }
//    @Test
//    public void testMetric() throws Exception {
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
//        Assert.assertEquals("Los Angeles", result);
//    }
//    @Test
//    public void testImperial() throws Exception {
//        request = mock(HttpServletRequest.class);
//        response = mock(HttpServletResponse.class);
//        session = mock(HttpSession.class);
//        when(request.getParameter("weatherloc")).thenReturn("90007");
//        when(request.getParameter("unit")).thenReturn(null);
//        when(request.getSession()).thenReturn(session);
//        StringWriter writer = new StringWriter();
//        PrintWriter out = new PrintWriter(writer);
//        when(response.getWriter()).thenReturn(out);
//        new HomePageServlet().doGet(request, response);
//        String result = writer.getBuffer().toString();
//        Assert.assertEquals("Los Angeles", result);
//    }
//    @Test
//    public void testCityName() throws Exception {
//        request = mock(HttpServletRequest.class);
//        response = mock(HttpServletResponse.class);
//        session = mock(HttpSession.class);
//        when(request.getParameter("weatherloc")).thenReturn("Los Angeles");
//        when(request.getParameter("unit")).thenReturn(null);
//        when(request.getSession()).thenReturn(session);
//        StringWriter writer = new StringWriter();
//        PrintWriter out = new PrintWriter(writer);
//        when(response.getWriter()).thenReturn(out);
//        new HomePageServlet().doGet(request, response);
//        String result = writer.getBuffer().toString();
//        Assert.assertEquals("Los Angeles", result);
//    }
//    @Test
//    public void testExceptionThrow() throws Exception {
//        request = mock(HttpServletRequest.class);
//        response = mock(HttpServletResponse.class);
//        session = mock(HttpSession.class);
//        bufferedReader = Mockito.mock(BufferedReader.class);
//        when(request.getParameter("weatherloc")).thenReturn("oiajsdoiajsodi*");
//        when(request.getParameter("unit")).thenReturn(null);
//        when(request.getSession()).thenReturn(session);
//        StringWriter writer = new StringWriter();
//        PrintWriter out = new PrintWriter(writer);
//        when(response.getWriter()).thenReturn(out);
//        new HomePageServlet().doGet(request, response);
//        String result = writer.getBuffer().toString();
//        Assert.assertEquals("Exception", result);
//    }
//    @Test
//    public void testBlankLocation() throws Exception {
//        request = mock(HttpServletRequest.class);
//        response = mock(HttpServletResponse.class);
//        session = mock(HttpSession.class);
//        bufferedReader = Mockito.mock(BufferedReader.class);
//        when(request.getParameter("weatherloc")).thenReturn("");
//        when(request.getParameter("unit")).thenReturn(null);
//        when(request.getSession()).thenReturn(session);
//        StringWriter writer = new StringWriter();
//        PrintWriter out = new PrintWriter(writer);
//        when(response.getWriter()).thenReturn(out);
//        new HomePageServlet().doGet(request, response);
//        String result = writer.getBuffer().toString();
//        Assert.assertEquals("blank submit", result);
//    }
//}
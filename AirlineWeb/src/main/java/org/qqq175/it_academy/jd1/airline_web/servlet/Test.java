package org.qqq175.it_academy.jd1.airline_web.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.qqq175.it_academy.jd1.airline_web.util.Settings;

/**
 * Servlet implementation class test
 */
@WebServlet("/test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public Test() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setLocale(new Locale("ru-RU"));
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append("<html><head><meta content='text/html; charset=UTF-8' http-equiv='content-type'></head<body>Served at: ").append(request.getContextPath());
		response.getWriter().append("<br>User at: ").append(request.getRemoteAddr());
		response.getWriter().append("<h2>OK</h2>");

		response.getWriter().append("<pre>").append(getGoogleJSONText(request.getParameter("place"))).append("</pre>");
		
		Settings settings = Settings.getInstance();
		response.getWriter().append("<h4>SETTINGS:</h4>Adj7j44FY175").append(settings.getGoogleAPIKey()).append("qQq7843<br><br>");
		response.getWriter().append(settings.database.getUrl()).append("<br>");
		response.getWriter().append(settings.database.getDriver()).append("<br>");
		response.getWriter().append(settings.database.getUser()).append("<br>");
	//	response.getWriter().append(settings.database.getPassword()).append("<br><br>");
		response.getWriter().append(Integer.toString(settings.database.pool.getMinPoolSize())).append("<br>");
		response.getWriter().append(Integer.toString(settings.database.pool.getAcquireIncrement())).append("<br>");
		response.getWriter().append(Integer.toString(settings.database.pool.getMaxPoolSize())).append("<br>");
		response.getWriter().append(Integer.toString(settings.database.pool.getMaxStatements())).append("<br></body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private String getGoogleJSONText(String place) {
		URL url;
		InputStream inputStream = null;
		BufferedReader bufferedReader;
		StringBuilder result = new StringBuilder();
		String line;

		try {
			url = new URL(
			        "https://maps.googleapis.com/maps/api/geocode/json?address="+ place +"&language=ru&key=AIzaSyAGGx5Ug_iLA3kLs0tEJdLS19unefUOtaE");
			inputStream = url.openStream();

			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			while ((line = bufferedReader.readLine()) != null) {
				result.append(line).append("\n");
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result.toString();

	}
}

/**
 * 
 */
package vam.travel.sample.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vam.travel.sample.command.Command;

/**
 * @author Srinivasan
 *
 */
public class ControlServlet extends HttpServlet {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//iterate through all the request parameters
		//add them to a map
		Map<String,String> requestParams=new HashMap<>();
		
		
		String uri=req.getRequestURI();
		Command command=CommandFactory.getCommand(uri);
		String successURL=command.execute(requestParams)
		req.getServletContext().getRequestDispatcher("/").forward(req, resp);
		
		
	}
}

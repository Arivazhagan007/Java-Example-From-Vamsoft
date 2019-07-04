package com.training.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Properties;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.jasper.tagplugins.jstl.core.Out;

import com.training.SqlConnection;
import com.training.daos.DaoImpl;
import com.training.factory.Factoroy;
import com.training.iface.Command;
import com.training.model.Customer;
import com.training.model.Employee;
import com.training.model.Tour;
import com.traning.process.CustomerLogin;

/**
 * Servlet implementation class TravelController
 */
public class TravelController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	DaoImpl dao = null;
	Properties properties = null;
    public TravelController() {
        super();
        Connection con = SqlConnection.getSqlConnection();
        dao = new DaoImpl(con);
        // TODO Auto-generated constructor stub
    }
    
    

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		
		InputStream inputStream = null;
		
		ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
		inputStream = classLoader.getResourceAsStream("Command.properties");
		properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NullPointerException {
		
//		String action = request.getParameter("action");
		
		
//		if (action.equals("custLogin")) {
//			String userName = request.getParameter("userName");
//			String passWord = request.getParameter("passWord");
//			Customer customer = new Customer(userName, passWord);
//			boolean value =  dao.customerLoginValidate(customer);
//			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
//			dispatcher.forward(request, response);
//			System.out.println(value);
//		}else if (action.equals("custRegister")) {
//			String name = request.getParameter("name");
//			String phoneNum = request.getParameter("phoneNumber");
//			long phoneNumber = Long.parseLong(phoneNum);
//			String userName = request.getParameter("userName");
//			String passWord = request.getParameter("passWord");
//			
//			Customer customer = new Customer(name, phoneNumber, userName, passWord);
//			dao.addCustomer(customer);
//			RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerLogin.jsp");
//			dispatcher.forward(request, response);
//		}else if (action.equals("empLogin")) {
//			String userName = request.getParameter("userName");
//			String passWord = request.getParameter("passWord");
//			Employee employee = new Employee(userName, passWord);
//			boolean value = dao.employeeLoginValidate(employee);
//			System.out.println(value);
//			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
//			dispatcher.forward(request, response);
//			
//		}else if (action.equals("empRegister")) {
//			String name = request.getParameter("name");
//			String phoneNum = request.getParameter("phoneNumber");
//			long phoneNumber = Long.parseLong(phoneNum);
//			String userName = request.getParameter("userName");
//			String passWord = request.getParameter("passWord");
//			Employee employee = new Employee(name, phoneNumber, userName, passWord);
//			dao.addEmployee(employee);
//			RequestDispatcher dispatcher = request.getRequestDispatcher("EmployeeLogin.jsp");
//			dispatcher.forward(request, response);
//		}else if (action.equals("addTour")) {
//			String tourCode = request.getParameter("tourCode");
//			String tourName = request.getParameter("tourName");
//			String boardingPlace = request.getParameter("boardingPlace");
//			String destinationPlace = request.getParameter("destinationPlace");
//			String strtDate = request.getParameter("startingDate");
//			LocalDate startingDate = LocalDate.parse(strtDate);
//			String endDate = request.getParameter("endingDate");
//			LocalDate endingDate = LocalDate.parse(endDate);
//			String placesCovered = request.getParameter("placesCovered");
//			String amtPerPerson = request.getParameter("amountPerPerson");
//			double amountPerPerson = Double.parseDouble(amtPerPerson);
//			Tour tour = new Tour(tourCode, tourName, boardingPlace, destinationPlace, startingDate, endingDate, placesCovered, amountPerPerson);
//			dao.addTour(tour);
//			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
//			dispatcher.forward(request, response);
//		}
		process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NullPointerException {
		// TODO Auto-generated method stub
		
		process(request, response);
	}
	
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException , NullPointerException {
		
		String uriPath = request.getRequestURI();
		String value = uriPath.substring(request.getContextPath().length());
		String[] arr = uriPath.split("/");
		String key=arr[arr.length-1];
		
		String value1 = properties.getProperty(key);
		
		try {
			Class class1=Class.forName(value1);
			Command command = (Command) class1.newInstance();
			String val = command.execute(request, dao);    //returnvalue .class
			String val1 = properties.getProperty(key+val);
			RequestDispatcher dispatcher = request.getRequestDispatcher(val1);
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
//		/TravelOperatorApplication/CustomerLogin.action
		
//		String[] uriSplit = uri.split("/");
//		int uriSplitLength = uriSplit.length;
//		
//		String a = uriSplit[uriSplitLength-1];
//		System.out.println(uriSplit[uriSplitLength]);
//		Command command = Factoroy.getAccess(a);
//		System.out.println(command);
//		if(command!=null){
//		String path=command.execute(request, dao);
//			
//		
//			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
//			
//			dispatcher.forward(request, response);
//		}
//		else{
//			 System.out.println("null");
//		 }
		
		
		
		
	}

}

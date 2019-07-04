package in.vamsoft.emscrm.command;

import in.vamsoft.emscrm.sql.ConnectionPool;
import in.vamsoft.emscrm.sql.SQLHolder;
import in.vamsoft.emscrm.utils.CommandUtil;
import in.vamsoft.emscrm.utils.CustomerDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class EmpNewTicket implements Command{

	public static Logger logger;
	static{
		logger = Logger.getLogger(CommandUtil.MYLOGGER);	
	}


	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		ServletContext context=request.getServletContext();
		HttpSession session=request.getSession();

		CustomerDetails customerDetails = (CustomerDetails) session.getAttribute
				(CommandUtil.SCUSTDETAILS);
		
		if(session!=null) {	
			session.invalidate();	
		}	
		// Create a new session for the user. 
		session = request.getSession(true); 

		//int empId=(int) session.getAttribute(CommandUtil.LOGINUSER);
		int custId=Integer.parseInt(request.getParameter(CommandUtil.CUSTOMERID));
		System.out.println(custId);
		String eventName=request.getParameter(CommandUtil.EVENTNAME);
		String eventDate=request.getParameter(CommandUtil.EVENTDATE);
		String eventTime=request.getParameter(CommandUtil.EVENTTIME);
		String location=request.getParameter(CommandUtil.LOCATION);
		int noOfTicket=Integer.parseInt(request.getParameter(CommandUtil.NOOFTICKET));
		String category=request.getParameter(CommandUtil.CATEGORY);
		double price=Double.parseDouble(request.getParameter(CommandUtil.PRICE));
		double tolalPrice=Double.parseDouble(request.getParameter(CommandUtil.TOTAL_PRICE));

		int rows=newTicket(custId,eventName,eventDate,eventTime,location,noOfTicket,category,price,tolalPrice,request);

		if(rows>0){

			request.setAttribute(CommandUtil.NEW_TICKET, CommandUtil.TICKET_DETAILS_SUCCESS);
			return CommandUtil.SUCCESS;

		}

		else{

			request.setAttribute(CommandUtil.NE_ERROR, CommandUtil.NTICKET_ERROR);
			return CommandUtil.FAILURE;
		}

	}


	private int newTicket(int custId, String eventName, String eventDate,
			String eventTime, String location, int noOfTicket, String category,
			double price, double tolalPrice, HttpServletRequest request) {
		int rows=0;
		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {
			statement=connection.prepareStatement(SQLHolder.INSERTTICKETQUERY);
			System.out.println("Customer id   "+custId);
			System.out.println("Event name   "+eventName);
			System.out.println("Event Date  "+eventDate);
			System.out.println("Event Time   "+eventTime);
			System.out.println("Location   "+location);
			System.out.println("category  "+category);
			System.out.println("Total Price   "+tolalPrice);
			
			statement.setInt(1, custId);
			statement.setString(2, eventName);
			statement.setString(3, eventDate);
			statement.setString(4, eventTime);
			statement.setString(5, location);
			statement.setInt(6, noOfTicket);
			statement.setString(7, category);
			statement.setDouble(8, price);
			statement.setDouble(9, tolalPrice);

			System.out.println(statement);
			rows=statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				statement.close();

			} catch (SQLException e) {
				logger.debug(e);
			}
		}
		return rows;
	}

	
}

package in.vamsoft.emscrm.command;

import in.vamsoft.emscrm.sql.ConnectionPool;
import in.vamsoft.emscrm.sql.SQLHolder;
import in.vamsoft.emscrm.utils.CommandUtil;
import in.vamsoft.emscrm.utils.CustomerDetails;
import in.vamsoft.emscrm.utils.UserNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class CustomerLogin implements Command{

	public static Logger logger;
	static{
		logger = Logger.getLogger(CommandUtil.MYLOGGER);	
	}


	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		logger.debug(CommandUtil.LOGINSTRART);

		ServletContext context = request.getServletContext();
		HttpSession session=request.getSession();

		String emailId =request.getParameter(CommandUtil.EMAILID);
		String password =request.getParameter(CommandUtil.PASSWORD);

		CustomerDetails customerDetails=new CustomerDetails(emailId,password);

		customerDetails=getLogin(customerDetails,request);

		int customerId = customerDetails.getCustomerId();
		session.setAttribute(
				CommandUtil.SCUST_ID, customerId);
		context.setAttribute(
				CommandUtil.SCUST_ID, customerId);

		String custName = customerDetails.getCustomerName();
		session.setAttribute(
				CommandUtil.SCUST_NAME, custName);
		context.setAttribute(
				CommandUtil.SCUST_NAME, custName);
		Date date=new Date();
		SimpleDateFormat ft=new SimpleDateFormat(CommandUtil.DATEFORMAT);
		String s=ft.format(date);
		session.setAttribute(CommandUtil.DATE, s);

		Date dateTime=new Date();
		Date timeStampDate=new java.sql.Timestamp(dateTime.getTime());
		session.setAttribute(CommandUtil.DATETIME, timeStampDate);
		context.setAttribute(CommandUtil.DATETIME, timeStampDate);
		String ipAddress = request.getRemoteAddr();
		String userAgent = request.getHeader(CommandUtil.U_AGENT);


		if(customerDetails.getCustomerName()!=null)
		{	

			boolean isLoggedInUser = checkLoggedInUser(context, emailId);
			if(isLoggedInUser==false){

				session.setAttribute(CommandUtil.SCUSTDETAILS, customerDetails);
				context.setAttribute(CommandUtil.SCUSTDETAILS, customerDetails);

				session.setAttribute(CommandUtil.SEMP_IP, ipAddress);
				session.setAttribute(
						CommandUtil.SEMP_USER_AGENT, userAgent);
				//loginHistory(customerDetails.getCustomerId(),customerDetails.getCustomerName(),
						//ipAddress,userAgent,timeStampDate, request);
				session.setAttribute(CommandUtil.SCUSTDETAILS, customerDetails);
				context.setAttribute(CommandUtil.SCUSTDETAILS, customerDetails);
				return CommandUtil.SUCCESS;
			

			}else {

				request.setAttribute(CommandUtil.ALREADY_LOGIN_ERROR,
						CommandUtil.USER_LOGIN_ERROR_MSG);
				return CommandUtil.FAILURE;

			}
			
		}else
		{

			int attemptCount = checkLoginAttempts(session);
			if(attemptCount>=4){

				try {

					userDisable(emailId, context);

				} catch (UserNotFoundException e) {

					e.printStackTrace();

				}

				request.setAttribute(CommandUtil.BLOCKED_MSG,
						CommandUtil.U_BLOCKED_MSG);
				return CommandUtil.FAILURE;

			}

			request.setAttribute(CommandUtil.LOGIN_ATTEMPT_MSG,
					CommandUtil.U_LOGIN_ATTEMPT_MSG1 + 
					(3-attemptCount) + CommandUtil.ATTEMPT_LEFT);
			return CommandUtil.FAILURE;
		}
	}


	private void loginHistory(int customerId, String customerName,
			String ipAddress, String userAgent, Date timeStampDate,
			HttpServletRequest request) {


		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();



		try {
			statement=connection.prepareStatement(
					SQLHolder.INSERTCUSTOMERLOGINQUERY);
			statement.setInt(1,customerId);
			statement.setString(2, customerName);
			statement.setTimestamp(3,  (Timestamp) timeStampDate);
			statement.setString(4, ipAddress);
			statement.setString(5, userAgent);
			statement.executeUpdate();

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



	}


	private void userDisable(String emailId, ServletContext context)
			throws UserNotFoundException {

		int rows=0;
		PreparedStatement statement=null;
		ConnectionPool connectionPool=(ConnectionPool) 
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {

			statement=connection.prepareStatement(
					SQLHolder.DISABLECUSTBYQUERY);
			statement.setString(1, emailId);
			rows = statement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();

		}
		if (rows <0){
			throw new UserNotFoundException();
		}


	}


	private int checkLoginAttempts(HttpSession session) {

		Object count = session.getAttribute
				(CommandUtil.FAILED_LOGIN_ATTEMPT);
		Integer counter= null; 
		if(count!=null){
			counter = (Integer) count;
		}else{
			counter = new Integer(0);
		}

		counter++;

		session.setAttribute(CommandUtil.FAILED_LOGIN_ATTEMPT, counter);

		return counter;
	}


	private boolean checkLoggedInUser
	(ServletContext context, String emailId) {

		Object users=context.getAttribute(CommandUtil.LOGIN_USER);

		Set<String> loggedUsers= null;
		boolean result;

		if(users!=null){

			loggedUsers= (Set<String>) users;

		}else {

			loggedUsers= new HashSet<>();

		}

		result = loggedUsers.add(emailId);

		logger.debug(loggedUsers.toString());

		context.setAttribute(CommandUtil.LOGIN_USER, loggedUsers);

		//negate result to get the desired effect
		//set returns true if the element is added successfully
		return !result;

	}


	protected CustomerDetails getLogin(CustomerDetails customerDetails,HttpServletRequest request) {

		ResultSet rs=null;
		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {
			statement=connection.prepareStatement(SQLHolder.CUSTOMERLOGINQUERY);
			statement.setString(1,customerDetails.getEmailId());
			statement.setString(2,customerDetails.getPassword());
			System.out.println(statement);
			rs=statement.executeQuery();
			while(rs.next())
			{

				customerDetails.setCustomerId(rs.getInt(CommandUtil.CUSTOMERID));
				customerDetails.setCustomerName(rs.getString(CommandUtil.CUSTOMERNAME));
				customerDetails.setFirstName(rs.getString(CommandUtil.FIRSTNAME));
				customerDetails.setLastName(rs.getString(CommandUtil.LASTNAME));
				customerDetails.setPhoneNo(rs.getString(CommandUtil.PHONENO));
				customerDetails.setEmailId(rs.getString(CommandUtil.EMAILID));
				customerDetails.setPassword(rs.getString(CommandUtil.PASSWORD));
				customerDetails.setAltPhoneNo(rs.getString(CommandUtil.ALT_PHONENO));
				customerDetails.setAltEmailId(rs.getString(CommandUtil.ALT_EMAILID));
				customerDetails.setSecurityQuestion(rs.getString(CommandUtil.SECURITY_QUES));
				customerDetails.setSecurityAnswer(rs.getString(CommandUtil.SECURITY_ANS));
				customerDetails.setDob(rs.getDate(CommandUtil.DOB));
				customerDetails.setPincode(rs.getInt(CommandUtil.PINCODE));
				customerDetails.setState(rs.getString(CommandUtil.STATE));
				customerDetails.setCountry(rs.getString(CommandUtil.COUNTRY));
				customerDetails.setDtStamp(rs.getTimestamp(CommandUtil.DTSTAMP));
				customerDetails.setAddress(rs.getString(CommandUtil.ADDRESS));
				customerDetails.setStatus(rs.getString(CommandUtil.STATUS));
				System.out.println("cust   "+customerDetails);
				connectionPool.returnPoolConnection(connection);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customerDetails;
	}
}

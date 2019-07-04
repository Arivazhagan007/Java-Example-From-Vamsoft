package in.vamsoft.emscrm.command;

import in.vamsoft.emscrm.sql.ConnectionPool;
import in.vamsoft.emscrm.sql.SQLHolder;
import in.vamsoft.emscrm.utils.CommandUtil;
import in.vamsoft.emscrm.utils.CustomerDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;



public class Search implements Command{

	public static Logger logger;
	static{
		logger = Logger.getLogger(CommandUtil.MYLOGGER);	
	}


	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {

		//logger.debug(CommandUtil.LOGINSTRART);

		//ServletContext context = request.getServletContext();
		HttpSession session=request.getSession(false);
		if(session!=null) {	
			session.invalidate();	
		}	
		// Create a new session for the user. 
		session = request.getSession(true); 
		ServletContext context=request.getServletContext();
		//HttpSession session=request.getSession();

		String emailId =request.getParameter(CommandUtil.EMAIL_ID);
		String customerName =request.getParameter(CommandUtil.CUSTOMERNAME);
		String phoneNo =request.getParameter(CommandUtil.PHONENO);


		List<CustomerDetails> details=doSearch(customerName,phoneNo,emailId,request);

		int custId=0;
		String email=null;
		for (CustomerDetails customerDetails2 : details) {

			email =customerDetails2.getEmailId();
			custId=customerDetails2.getCustomerId();
			System.out.println("Cust Id   "+custId);
			System.out.println(email);
		}

		if(email!=null)
		{	

			session.setAttribute(CommandUtil.C_DETAILS, details);
			session.setAttribute(CommandUtil.CUST_ID, custId);
			session.removeAttribute(CommandUtil.DETAILS_NOT_FOUND_ERROR);
			return CommandUtil.SUCCESS;

		}else {

			session.setAttribute(CommandUtil.DETAILS_NOT_FOUND_ERROR,
					CommandUtil.SEARCH_DETAILS_NOT_FOUND_ERROR);
			/*session.removeAttribute(CommandUtil.C_DETAILS);*/
			return CommandUtil.FAILURE;

		}

	}

	private List<CustomerDetails> doSearch(String customerName, String phoneNo,
			String emailId, HttpServletRequest request) {
		List<CustomerDetails> details = new ArrayList<>();
		ResultSet rs=null;
		PreparedStatement statement=null;


		CustomerDetails customerDetails=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {

			statement=connection.prepareStatement(SQLHolder.SEARCHQRY);
			statement.setString(1,customerName);
			statement.setString(2,emailId);
			statement.setString(3,phoneNo);
			System.out.println(statement);
			rs=statement.executeQuery();
			while(rs.next())
			{
				customerDetails = new CustomerDetails();
				customerDetails.setCustomerId(rs.getInt(CommandUtil.CUSTOMERID));
				customerDetails.setCustomerName(rs.getString(CommandUtil.CUSTOMERNAME));
				customerDetails.setPhoneNo(rs.getString(CommandUtil.PHONENO));
				customerDetails.setEmailId(rs.getString(CommandUtil.EMAILID));
				customerDetails.setDtStamp(rs.getTimestamp(CommandUtil.DTSTAMP));
				customerDetails.setAddress(rs.getString(CommandUtil.ADDRESS));

				details.add(customerDetails);
				System.out.println(details);
				connectionPool.returnPoolConnection(connection);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				statement.close();
				rs.close();

			} catch (SQLException e) {
				logger.debug(e);
			}
		}
		return details;
	}




}
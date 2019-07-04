package in.vamsoft.emscrm.command;

import in.vamsoft.emscrm.sql.ConnectionPool;
import in.vamsoft.emscrm.sql.SQLHolder;
import in.vamsoft.emscrm.utils.CommandUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class NewCustomer implements Command{

	
	public static Logger logger;
	static{
		logger = Logger.getLogger(CommandUtil.MYLOGGER);	
	}

	

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {

		HttpSession session=request.getSession(false);
		if(session!=null) {	
			session.invalidate();	
		}	
		// Create a new session for the user. 
		session = request.getSession(true); 


		SimpleDateFormat dateFormat = new SimpleDateFormat(CommandUtil.DFORMAT);
		String customerName=request.getParameter(CommandUtil.CUSTOMERNAME);
		String emailId=request.getParameter(CommandUtil.EMAILID);
		String altEmailId=request.getParameter(CommandUtil.ALT_EMAILID);
		String pwd=request.getParameter(CommandUtil.PASSWORD);
		String phNo=request.getParameter(CommandUtil.PHNO);
		String altPhoneNo=request.getParameter(CommandUtil.ALT_PHONENO);
		String firstName=request.getParameter(CommandUtil.FIRSTNAME);
		String lastName=request.getParameter(CommandUtil.LASTNAME);
		String securityQuestion=request.getParameter(CommandUtil.SECURITY_QUES);
		String securityAnswer=request.getParameter(CommandUtil.SECURITY_ANS);
		
		int pincode=Integer.parseInt(request.getParameter(CommandUtil.PINCODE));
		String state=request.getParameter(CommandUtil.STATE);
		String country=request.getParameter(CommandUtil.COUNTRY);
		String address=request.getParameter(CommandUtil.ADDRESS);
		String strdob=request.getParameter(CommandUtil.DOB);
        java.sql.Date dobDate=null;

        try {
            dobDate = new java.sql.Date(dateFormat.parse(strdob).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
		int rows=customersignup(customerName,emailId,altEmailId,pwd,phNo,altPhoneNo,firstName,lastName,securityQuestion,
				securityAnswer,dobDate,pincode,state,country,address,request);

		if(rows>0){

			session.setAttribute(CommandUtil.NEMP_SUCCESS, CommandUtil.NEWEMP_SUCCESS);
			return CommandUtil.SUCCESS;

		}
		else{
			request.setAttribute(CommandUtil.NE_ERROR, CommandUtil.NEP_ERROR);
			return CommandUtil.FAILURE;
		}

	}

	private int customersignup(String customerName,String emailId,String altEmailId,String pwd,String phNo,String altPhoneNo,
			String firstName,String lastName,String securityQuestion,
			String securityAnswer,Date dobDate, int pincode,String state,String country,String address,
			HttpServletRequest request) {
		int rows=0;
		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {
			statement=connection.prepareStatement(SQLHolder.INSERTCUSTOMERSIGNUPQUERY );
			statement.setString(1,customerName);
			statement.setString(2,emailId);
			statement.setString(3,altEmailId);
			statement.setString(4,pwd);
			statement.setString(5,phNo);
			statement.setString(6,altPhoneNo);
			statement.setString(7,firstName);
			statement.setString(8,lastName);
			statement.setString(9,securityQuestion);
			statement.setString(10,securityAnswer);
			statement.setDate(11, (java.sql.Date) dobDate);
			statement.setInt(12,pincode);
			statement.setString(13,state);
			statement.setString(14,country);
			statement.setString(15,address);
			
			
			rows=statement.executeUpdate();
			System.out.println(statement);
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

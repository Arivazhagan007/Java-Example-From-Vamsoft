package in.vamsoft.emscrm.command;

import in.vamsoft.emscrm.sql.ConnectionPool;
import in.vamsoft.emscrm.sql.SQLHolder;
import in.vamsoft.emscrm.utils.CommandUtil;
import in.vamsoft.emscrm.utils.CustomerDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class CustomerProfile implements Command{
	public static Logger logger;
	static{
		logger = Logger.getLogger(CommandUtil.MYLOGGER);	
	}
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {

		HttpSession session=request.getSession();
		Date date=new Date();
		SimpleDateFormat ft=new SimpleDateFormat(CommandUtil.DATEFORMAT);
		String s=ft.format(date);


		int hidCustId =Integer.parseInt(request.getParameter
				(CommandUtil.HIDCUSTID));

		CustomerDetails customerDetails = new CustomerDetails(hidCustId);

		customerDetails=getCustId(customerDetails,request);

		if(customerDetails!=null){
			session.setAttribute(CommandUtil.PROFILEDETAILS, customerDetails);

			return CommandUtil.SUCCESS;

		}else{
			request.setAttribute(CommandUtil.PROFILE_ERROR, CommandUtil.PROFILE_ERROR_MSG);
			return CommandUtil.FAILURE;
		}


	}


	private CustomerDetails getCustId(CustomerDetails customerDetails,
			HttpServletRequest request) {

		ResultSet rs=null;
		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {
			statement=connection.prepareStatement(SQLHolder.VIEWBYIDQUERY);
			statement.setInt(1,customerDetails.getCustomerId());
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
				connectionPool.returnPoolConnection(connection);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customerDetails;
	}
}


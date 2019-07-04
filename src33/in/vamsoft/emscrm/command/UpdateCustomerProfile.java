package in.vamsoft.emscrm.command;

import in.vamsoft.emscrm.sql.ConnectionPool;
import in.vamsoft.emscrm.sql.SQLHolder;
import in.vamsoft.emscrm.utils.CommandUtil;
import in.vamsoft.emscrm.utils.UserNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class UpdateCustomerProfile implements Command{


	public static Logger logger;
	static{
		logger = Logger.getLogger(CommandUtil.MYLOGGER);	
	}


	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {

		ServletContext context = request.getServletContext();
		HttpSession session=request.getSession();

		String emailId =request.getParameter(CommandUtil.EMAILID);
		String firstName =request.getParameter(CommandUtil.FIRSTNAME);
		String lastName = request.getParameter(CommandUtil.LASTNAME);
		String altPhoneNo = request.getParameter(CommandUtil.ALT_PHONENO);
		String altEmailId = request.getParameter(CommandUtil.ALT_EMAILID);
		int pincode = Integer.parseInt(request.getParameter(CommandUtil.PINCODE));
		String state = request.getParameter(CommandUtil.STATE);
		String country = request.getParameter(CommandUtil.COUNTRY);
		String address = request.getParameter(CommandUtil.ADDRESS);

		int rows = updateProfile(emailId,firstName,lastName,altPhoneNo,
				altEmailId,pincode,state,country,address,request);
		if(rows>0){

			request.setAttribute(CommandUtil.UPDATE_SUCCESS,CommandUtil.UPDATE_SUCCESS_MSG);
			return CommandUtil.SUCCESS;
		}else{
			request.setAttribute(CommandUtil.UPDATE_FAILURE,CommandUtil.UPDATE_FAILURE_MSG);
			return CommandUtil.FAILURE;
		}

		
	}


	private int updateProfile(String emailId, String firstName,
			String lastName, String altPhoneNo, String altEmailId, int pincode,
			String state, String country, String address,
			HttpServletRequest request) {
		int rows=0;
		PreparedStatement statement=null;
		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool) 
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {

			statement=connection.prepareStatement(
					SQLHolder.UPDATECUSTYQUERY);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, altPhoneNo);
			statement.setString(4, altEmailId);
			statement.setInt(5, pincode);
			statement.setString(6, state);
			statement.setString(7, country);
			statement.setString(8, address);
			statement.setString(9, emailId);
			rows = statement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();

		}

		return rows;
	}

}

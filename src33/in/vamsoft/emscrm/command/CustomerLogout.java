package in.vamsoft.emscrm.command;

import in.vamsoft.emscrm.sql.ConnectionPool;
import in.vamsoft.emscrm.sql.SQLHolder;
import in.vamsoft.emscrm.utils.CommandUtil;
import in.vamsoft.emscrm.utils.CustomerDetails;
import in.vamsoft.emscrm.utils.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.print.DocFlavor.STRING;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


public class CustomerLogout implements Command{

	public static Logger logger;
	static
	{
		logger = Logger.getLogger(CommandUtil.MYLOGGER);	
	}


	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		logger.debug(CommandUtil.LOGOUTSTART);

		ServletContext context=request.getServletContext();
		HttpSession session=request.getSession();

		CustomerDetails customerDetails = (CustomerDetails) session.getAttribute
				(CommandUtil.SCUSTDETAILS);

		/*Date date=new Date();
        Date logoutTime=new java.sql.Timestamp(date.getTime());

        Date loginTime=(Date) session.getAttribute(CommandUtil.DATETIME);
        int empId=(int)context.getAttribute(CommandUtil.SEMP_ID);
        String empName= (String) session.getAttribute
                (CommandUtil.SEMP_NAME);
        String ipAddress= (String) session.getAttribute
                (CommandUtil.SEMP_IP);
        String userAgent= (String) session.getAttribute
                (CommandUtil.SEMP_USER_AGENT);*/

		if(customerDetails!=null){

			/*updateLoginHistory(empId,empName,loginTime,
                    logoutTime,ipAddress,userAgent, request);*/
			session.invalidate();
			logger.debug("Employee is null");
			context.removeAttribute("loginuser");
			
			context.removeAttribute(CommandUtil.SCUST_ID);
			context.removeAttribute(CommandUtil.SCUST_NAME);
			context.removeAttribute(CommandUtil.DATETIME);
			context.removeAttribute(CommandUtil.SCUSTDETAILS);
			request.setAttribute(CommandUtil.LOGOUT_SUCCESS, CommandUtil.LOGOUT_SUCCESS_MSG);
			return CommandUtil.SUCCESS;


		}else{

			if(context.getAttribute("loginuser") != null) {


				Set<String> set = new HashSet<String>();

				String email = (String) context.getAttribute(CommandUtil.SEMAIL_ID);
				set.add(email);
				Set<Set<String>> loggedInUsers  = new HashSet<Set<String>>();
				loggedInUsers.add(set);
				logger.debug("users loggedin :: " + loggedInUsers.toString());
			}

			session.invalidate();
			context.removeAttribute(CommandUtil.SCUST_ID);
			context.removeAttribute(CommandUtil.SCUST_NAME);
			context.removeAttribute(CommandUtil.DATETIME);
			context.removeAttribute(CommandUtil.SCUSTDETAILS);
			request.setAttribute(CommandUtil.LOGOUT_SUCCESS, CommandUtil.LOGOUT_SUCCESS_MSG);
			return CommandUtil.SUCCESS;
		}/*else{
			request.setAttribute(CommandUtil.LOGOUT_FAILURE, CommandUtil.LOGOUT_FAILURE_MSG);
		return CommandUtil.FAILURE;*/
	}




	private void updateLoginHistory(int empId, String empName, Date loginTime, Date logoutTime,
			String ipAddress, String userAgent, HttpServletRequest request) {

		int rows=0;
		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {
			statement=connection.prepareStatement(SQLHolder.UPDATELOGOUTTIME);
			statement.setTimestamp(1, (Timestamp) logoutTime);
			statement.setInt(2,empId);
			statement.setString(3, empName);
			statement.setTimestamp(4, (Timestamp) loginTime);
			statement.setString(5, ipAddress);
			statement.setString(6, userAgent);

			logger.debug(statement);
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



	}
}

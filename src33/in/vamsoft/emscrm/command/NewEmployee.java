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

public class NewEmployee implements Command{

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
		String empName=request.getParameter(CommandUtil.EMPNAME);
		String emailId=request.getParameter(CommandUtil.EMAILID);
		String pwd=request.getParameter(CommandUtil.PASSWORD);
		String phNo=request.getParameter(CommandUtil.PHNO);
		String designation=request.getParameter(CommandUtil.DESIGNATION);
		String role=request.getParameter(CommandUtil.ROLE);
		String strdoj=request.getParameter(CommandUtil.DOJ);
		java.sql.Date dojDate=null;

		try {
			dojDate = new java.sql.Date(dateFormat.parse(strdoj).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String status=request.getParameter(CommandUtil.STATUS);

		int rows=newEmployee(empName,emailId,pwd,phNo,designation,role,dojDate,status,request);

		if(rows>0){

			session.setAttribute(CommandUtil.NEMP_SUCCESS, CommandUtil.NEWEMP_SUCCESS);
			return CommandUtil.SUCCESS;

		}
		else{
			request.setAttribute(CommandUtil.NE_ERROR, CommandUtil.NEP_ERROR);
			return CommandUtil.FAILURE;
		}

	}

	private int newEmployee(String empName, String emailId, String pwd,
			String phNo, String designation, String role,
			Date dojDate, String status,
			HttpServletRequest request) {
		int rows=0;
		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {
			statement=connection.prepareStatement(SQLHolder.INSERTEMPLOYEEQUERY);
			statement.setString(1,empName);
			statement.setString(2,emailId);
			statement.setString(3,pwd);
			statement.setString(4,phNo);
			statement.setString(5,designation);
			statement.setString(6, role);
			statement.setDate(7, (java.sql.Date) dojDate);
			statement.setString(8, status);
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

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

public class UpdateEmployeeProfile implements Command{


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

		String phoneNo = request.getParameter(CommandUtil.PHNO);

		String designation = request.getParameter(CommandUtil.DESIGNATION);
		String role = request.getParameter(CommandUtil.ROLE);
		String empName = request.getParameter(CommandUtil.EMPNAME);


		int rows = updateemployeeProfile(emailId,phoneNo,designation,role,empName,request);
		System.out.println(rows);
		if(rows>0){

			request.setAttribute(CommandUtil.UPDATE_SUCCESS,CommandUtil.UPDATE_SUCCESS_MSG);
			return CommandUtil.SUCCESS;
		}else{
			request.setAttribute(CommandUtil.UPDATE_FAILURE,CommandUtil.UPDATE_FAILURE_MSG);
			return CommandUtil.FAILURE;
		}


	}


	private int updateemployeeProfile(String emailId, String phoneNo, String designation, String role,String empName, HttpServletRequest request 
			) {
		int rows=0;
		PreparedStatement statement=null;
		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool) 
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {

			statement=connection.prepareStatement(
					SQLHolder.UPDATEEMPQUERY);
			statement.setString(1, emailId);
			statement.setString(2, phoneNo);
			statement.setString(3, designation);
			statement.setString(4, role);
			statement.setString(5, empName);
System.out.println(statement);
			rows = statement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();

		}

		return rows;
	}

}

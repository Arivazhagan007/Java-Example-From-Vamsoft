package in.vamsoft.emscrm.command;

import in.vamsoft.emscrm.sql.ConnectionPool;
import in.vamsoft.emscrm.sql.SQLHolder;
import in.vamsoft.emscrm.utils.CommandUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class AddIssue implements Command{

	public static SimpleDateFormat sdf = new SimpleDateFormat(CommandUtil.DFORMAT);
	public static Logger logger;
	static{
		logger = Logger.getLogger(CommandUtil.MYLOGGER);	
	}
	

			
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response ) throws SQLException {
		logger.debug(CommandUtil.ADDISSUESTART);
	
		int customer_id =Integer.parseInt(request.getParameter(CommandUtil.CUSTOMERID));	
		System.out.println(customer_id);
		String issue_subject=request.getParameter(CommandUtil.ISSUESUB);
		System.out.println(issue_subject);
		String issue_description =request.getParameter(CommandUtil.ISSUEDESC);
		String issue_status =request.getParameter(CommandUtil.ISSUE_STATUS);				

		int rows=newIssue(customer_id,issue_subject,issue_description,issue_status,request);
		
		if(rows<=0){			
			return CommandUtil.FAILURE;
		}
		else{
			request.setAttribute(CommandUtil.USERERROR, CommandUtil.U_ERRORMSG);
			return CommandUtil.SUCCESS;
		}
	}
	
	


	private int newIssue(int customer_id, String issue_subject,
			String issue_Description, String issue_status, 
			 HttpServletRequest request) {
		int rows=0;

		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool) context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();
		try {
			statement=connection.prepareStatement(SQLHolder.ADDISSUEQUERY);

			statement.setInt(1, customer_id);
			statement.setString(2,issue_subject);
			statement.setString(3,issue_Description); 
			statement.setString(4,issue_status);			
			rows = statement.executeUpdate();
			

		}

		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally{

		}			
		return rows;

	}
}


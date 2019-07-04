package in.vamsoft.empservice.command;

import in.vamsoft.empservice.sql.ConnectionPool;
import in.vamsoft.empservice.sql.SQLHolder;
import in.vamsoft.empservice.utils.CommandUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class NewRole implements Command{
	public static Logger logger;
	static{
		logger = Logger.getLogger(CommandUtil.MYLOGGER);	
	}
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {


		String roleName=request.getParameter(CommandUtil.ROLENAME);
		String descr=request.getParameter(CommandUtil.RDESCR);
		int rows=newRole(roleName,descr,request);
		if(rows>0){
			request.setAttribute(CommandUtil.NR_SUCCESS, CommandUtil.NEWR_SUCCESS);
			return CommandUtil.SUCCESS;
		}
		else{
			request.setAttribute(CommandUtil.NR_ERROR, CommandUtil.NEWR_ERROR);
			return CommandUtil.FAILURE;
		}

	}
	private int newRole(String roleName, String descr,
			HttpServletRequest request) {
		int rows=0;
		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {
			statement=connection.prepareStatement(SQLHolder.NEWROLEQUERY);
			statement.setString(1,roleName);
			statement.setString(2,descr);
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

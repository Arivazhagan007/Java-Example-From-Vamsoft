package in.vamsoft.empservice.command;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import in.vamsoft.empservice.sql.ConnectionPool;
import in.vamsoft.empservice.sql.SQLHolder;
import in.vamsoft.empservice.utils.Attach;
import in.vamsoft.empservice.utils.CommandUtil;
import in.vamsoft.empservice.utils.Role;

import org.apache.log4j.Logger;
@MultipartConfig
public class AttachFile implements Command{

	private final String UPLOAD_DIRECTORY = CommandUtil.UPLOADFOLDER;
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
		session.setAttribute(CommandUtil.DATE, s);
		String role=request.getParameter(CommandUtil.ROLE);
		Part filePart=null;
		try {
			filePart = request.getPart(CommandUtil.FILE);
		} catch (IllegalStateException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (ServletException e) {

			e.printStackTrace();
		}
		String filename = getFilename(filePart);
		try {
			filePart.write(UPLOAD_DIRECTORY + filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int rows=attachFile(filename, UPLOAD_DIRECTORY,role,request);
		if(rows>0){
			Attach atch=lastInsertId(request);
			if(atch!=null){
				String arole=atch.getRole();
				int attachid=atch.getAttachId();
				Role urole=viewRoleByName(arole,request);
				if(urole!=null){
					int roleid=urole.getRoleId();
					int row=newAttachRole(attachid,roleid,request);
					if(row>0){
						request.setAttribute(CommandUtil.U_SUCCESS, CommandUtil.UP_SUCCESS);
						return CommandUtil.SUCCESS;
					}
					else{
						request.setAttribute(CommandUtil.AR_ERROR, CommandUtil.ARD_ERROR);
						return CommandUtil.ATTACH_ROLEFAILURE;
					}
				}
			}
		}
		else{
			request.setAttribute(CommandUtil.FNA_ERROR, CommandUtil.FA_ERROR);
			return CommandUtil.FAILURE;
		}
		request.setAttribute(CommandUtil.FNA_ERROR, CommandUtil.FA_ERROR);
		return CommandUtil.FAILURE;
	}


	private int newAttachRole(int attachid, int roleid, HttpServletRequest request) {
		int rows=0;
		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {
			statement=connection.prepareStatement(SQLHolder.NEWATTACHROLEQUERY);
			statement.setInt(1,attachid);
			statement.setInt(2,roleid);
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


	private Role viewRoleByName(String arole, HttpServletRequest request) {
		Role role=null;
		ResultSet rs=null;
		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();
		try {
			statement=connection.prepareStatement(SQLHolder.VIEWROLEBYNAME);
			statement.setString(1, arole);
			rs=statement.executeQuery();
			while(rs.next())
			{
				role=new Role();
				role.setRoleId(rs.getInt(CommandUtil.ROLEID));
				role.setRoleName(rs.getString(CommandUtil.ROLENAME));
				role.setDescr(rs.getString(CommandUtil.RDESCR));
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

		return role;
	}


	private Attach lastInsertId(HttpServletRequest request) {
		Attach atch=null;
		ResultSet rs=null;
		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {
			statement=connection.prepareStatement(SQLHolder.RETRIVELASTINSERTATACHQUERY);
			rs=statement.executeQuery();

			while(rs.next())
			{
				atch=new Attach();
				atch.setAttachId(rs.getInt(CommandUtil.ATTACHID));
				atch.setFileName(rs.getString(CommandUtil.FILENAME));
				atch.setPath(rs.getString(CommandUtil.PATH));
				atch.setRole(rs.getString(CommandUtil.ROLE));
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
		return atch;

	}


	private int attachFile(String filename, String UPLOAD_DIRECTORY,
			String role, HttpServletRequest request) {
		int rows=0;
		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {
			statement=connection.prepareStatement(SQLHolder.ATTACHQUERY);
			statement.setString(1,filename);
			statement.setString(2,UPLOAD_DIRECTORY);
			statement.setString(3, role);
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


	private String getFilename(Part filePart) {
		for (String cd : filePart.getHeader(CommandUtil.CONDES).split(CommandUtil.SPLIT)) {
			if (cd.trim().startsWith(CommandUtil.FILENAME)) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace(CommandUtil.REPLACE, CommandUtil.EMPTY);
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
			}
		}
		return null;
	}
}


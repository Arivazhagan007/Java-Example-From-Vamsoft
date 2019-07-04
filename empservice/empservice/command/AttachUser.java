package in.vamsoft.empservice.command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.vamsoft.empservice.sql.ConnectionPool;
import in.vamsoft.empservice.sql.SQLHolder;
import in.vamsoft.empservice.utils.Attach;
import in.vamsoft.empservice.utils.CommandUtil;

import org.apache.log4j.Logger;

public class AttachUser implements Command{

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

		String role=request.getParameter(CommandUtil.HROLE);
		List<Attach> atch = viewAttachByRole(role,request);
		if(atch!=null){
			session.setAttribute(CommandUtil.ATTACHUSERDETAILS, atch);

			return CommandUtil.SUCCESS;
		}
		else {
			if(role.equals(CommandUtil.RADMIN)){
				request.setAttribute(CommandUtil.AD_ERROR, CommandUtil.ADETAIL_ERROR);
				return CommandUtil.ADMINFAILURE;
			}else {
				request.setAttribute(CommandUtil.AD_ERROR, CommandUtil.ADETAIL_ERROR);
				return CommandUtil.USERFAILURE;
			}

		}

	}


	private List<Attach> viewAttachByRole(String role,
			HttpServletRequest request) {
		List<Attach> alst=new ArrayList<Attach>();
		Attach atch=null;
		ResultSet rs=null;
		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();
		int attachId=0;
		String fileName,path,arole=null;
		try {
			statement=connection.prepareStatement(SQLHolder.VIEWATTACHBYROLEQUERY);
			statement.setString(1,role);
			rs=statement.executeQuery();
			while(rs.next())
			{
				atch=new Attach();
				attachId=rs.getInt(CommandUtil.ATTACHID);
				fileName=rs.getString(CommandUtil.FILENAME);
				path=rs.getString(CommandUtil.PATH);
				arole=rs.getString(CommandUtil.ROLE);
				atch=new Attach(attachId, fileName, path, arole);
				alst.add(atch);
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
		return alst;
	}

}

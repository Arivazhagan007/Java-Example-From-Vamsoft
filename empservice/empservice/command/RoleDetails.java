package in.vamsoft.empservice.command;

import in.vamsoft.empservice.sql.ConnectionPool;
import in.vamsoft.empservice.sql.SQLHolder;
import in.vamsoft.empservice.utils.CommandUtil;
import in.vamsoft.empservice.utils.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


public class RoleDetails implements Command{

	public static Logger logger;
	static{
		logger = Logger.getLogger(CommandUtil.MYLOGGER);	
	}


	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		HttpSession session=request.getSession();


		List<Role> rlst=roleDetails(request);
		session.setAttribute(CommandUtil.ROLEDETAILS, rlst);
		List<Role> plist = rlst.subList(0, 5);

		request.setAttribute(CommandUtil.SRSUBLIST, plist);
		int size = rlst.size();
		int pages = (int)Math.ceil(size/5.0);
		session.setAttribute(CommandUtil.PAGES, pages);
		if(rlst!=null){
			return CommandUtil.SUCCESS;
		}
		else{
			request.setAttribute(CommandUtil.RNF_ERROR, CommandUtil.RNOTF_ERROR);
			return CommandUtil.FAILURE;
		}

	}


	private List<Role> roleDetails(HttpServletRequest request) {
		List<Role> rlst=new ArrayList<Role>();
		Role role=null;
		ResultSet rs=null;
		PreparedStatement statement=null;
		int roleId=0;
		String roleName,descr=null;
		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {
			statement=connection.prepareStatement(SQLHolder.VIEWALLROLEQUERY);

			rs=statement.executeQuery();
			while(rs.next())
			{
				role=new Role();
				roleId=rs.getInt(CommandUtil.ROLEID);
				roleName=rs.getString(CommandUtil.ROLENAME);
				descr=rs.getString(CommandUtil.RDESCR);

				role=new Role(roleId, roleName, descr);
				rlst.add(role);
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
		return rlst;
	}

}

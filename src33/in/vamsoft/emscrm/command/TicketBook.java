package in.vamsoft.emscrm.command;

import in.vamsoft.emscrm.sql.ConnectionPool;
import in.vamsoft.emscrm.sql.SQLHolder;
import in.vamsoft.emscrm.utils.CommandUtil;
import in.vamsoft.emscrm.utils.EventDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class TicketBook implements Command{
	
	public static Logger logger;
	static{
		logger = Logger.getLogger(CommandUtil.MYLOGGER);	
	}


	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		
		HttpSession session = request.getSession();
		
		int eventId=Integer.parseInt(request.getParameter(CommandUtil.EVENTID));

		
		EventDetails details = getEventDetails(eventId,request);
		
		if(details!=null){
			/*session.setAttribute(CommandUtil.S_CUST_ID, customerId);*/
			session.setAttribute(CommandUtil.EVENT_DETAILS, details);
			System.out.println(session.getAttribute(CommandUtil.EVENT_DETAILS));
			session.removeAttribute(CommandUtil.CUST_ID);
			return CommandUtil.SUCCESS;
			
		}else{
			
			request.setAttribute(CommandUtil.EVENT_DETAILS_FAILURE, CommandUtil.EVENT_DETAILS_FAILURE_MSG);
			return CommandUtil.FAILURE;
			
		}
		
	}

	private EventDetails getEventDetails(int eventId, HttpServletRequest request) {
		
		EventDetails details = null;

		ResultSet rs=null;
		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {
			statement=connection.prepareStatement(SQLHolder.GETONEEVENTDETAIL);

			statement.setInt(1, eventId);
			rs=statement.executeQuery();


			while(rs.next())
			{

				details=new EventDetails();

				details.setEventId(rs.getInt(CommandUtil.EVENTID));
				details.setEventName(rs.getString(CommandUtil.EVENTNAME));
				details.setEventDate(rs.getString(CommandUtil.EVENTDATE));
				details.setEventTime(rs.getString(CommandUtil.EVENTTIME));
				details.setCategory(rs.getString(CommandUtil.CATEGORY));
				details.setLocation(rs.getString(CommandUtil.LOCATION));
				details.setOrganisedBy(rs.getString(CommandUtil.ORGANISHEDBY));
				details.setPrice(rs.getDouble(CommandUtil.PRICE));
				
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
		
		return details;
	}

}

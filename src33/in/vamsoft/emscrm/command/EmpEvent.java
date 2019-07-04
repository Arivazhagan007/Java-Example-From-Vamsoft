package in.vamsoft.emscrm.command;

import in.vamsoft.emscrm.sql.ConnectionPool;
import in.vamsoft.emscrm.sql.SQLHolder;
import in.vamsoft.emscrm.utils.CommandUtil;
import in.vamsoft.emscrm.utils.EventDetails;

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

public class EmpEvent implements Command{

	public static Logger logger;
	static{
		logger = Logger.getLogger(CommandUtil.MYLOGGER);	
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {

		HttpSession session=request.getSession();

		List<EventDetails> details = getEventDetails(request);

		if(details!=null){
			
			
			session.setAttribute(CommandUtil.EVENTLIST, details);
			System.out.println("Session    "+session.getAttribute(CommandUtil.EVENTLIST));
			return CommandUtil.SUCCESS;
		}else {

			session.setAttribute(CommandUtil.EVENT_FAILURE, CommandUtil.EVENT_FAILURE_MSG);
			return CommandUtil.FAILURE;
		}

	}

	private List<EventDetails> getEventDetails(HttpServletRequest request) {

		EventDetails details = null;

		List<EventDetails> eventDetailsList = new ArrayList<>();
		ResultSet rs=null;
		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {
			statement=connection.prepareStatement(SQLHolder.GETEVENTDETAILS);
System.out.println(statement);
			rs=statement.executeQuery();


			while(rs.next())
			{

				System.out.println("In Loop");
				details=new EventDetails();


				details.setEventId(rs.getInt(CommandUtil.EVENTID));
				details.setEventName(rs.getString(CommandUtil.EVENTNAME));
				details.setEventDate(rs.getString(CommandUtil.EVENTDATE));
				details.setEventTime(rs.getString(CommandUtil.EVENTTIME));
				details.setCategory(rs.getString(CommandUtil.CATEGORY));
				details.setLocation(rs.getString(CommandUtil.LOCATION));
				details.setOrganisedBy(rs.getString(CommandUtil.ORGANISHEDBY));
				details.setPrice(rs.getDouble(CommandUtil.PRICE));
				eventDetailsList.add(details);
				System.out.println("Event List  "+eventDetailsList);
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


		return eventDetailsList;
	}

}

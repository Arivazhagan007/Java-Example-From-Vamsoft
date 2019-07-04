package in.vamsoft.emscrm.command;

import in.vamsoft.emscrm.sql.ConnectionPool;
import in.vamsoft.emscrm.sql.SQLHolder;
import in.vamsoft.emscrm.utils.CommandUtil;
import in.vamsoft.emscrm.utils.IssueDetails;
import in.vamsoft.emscrm.utils.TicketDetails;

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

public class ShowDetails implements Command{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {


		HttpSession session=request.getSession();


		int custId=Integer.parseInt(request.getParameter(CommandUtil.CUST_ID));
		List<TicketDetails> details = getTicketDetails(custId,request);
		String location=null;
		int ticketCustId=0;
		for (TicketDetails ticketDetails : details) {

			location = ticketDetails.getLocation();
			ticketCustId = ticketDetails.getCustomerId();
		}

		List<IssueDetails> issueDetails = getIssueDetails(custId,request);
		String subject = null;
		int c_id=0;
		for (IssueDetails issue : issueDetails) {

			subject = issue.getIssueSubject();
			c_id=issue.getCustomerId();
		}




		if(location !=null){
			
			session.setAttribute(CommandUtil.TICKET_CUST_ID, ticketCustId);
			session.setAttribute(CommandUtil.SUCCESS_TICKET, details);
			
			int size = details.size();
			if(size<=5){
				
				List<TicketDetails> plist = details.subList(0,size);

				request.setAttribute(CommandUtil.SUBLIST, plist);

				
				int pages = (int)Math.ceil(size/5.0);
				session.setAttribute(CommandUtil.PAGES, pages);
				
			}else{
				
				List<TicketDetails> plist = details.subList(0,5);

				request.setAttribute(CommandUtil.SUBLIST, plist);

				
				int pages = (int)Math.ceil(size/5.0);
				session.setAttribute(CommandUtil.PAGES, pages);
				
			}
						
			session.removeAttribute(CommandUtil.ALL_TICKET);
			session.removeAttribute(CommandUtil.PAST_TICKET);

			session.removeAttribute(CommandUtil.FAILURE_FUTURE_TICKET);
			session.removeAttribute(CommandUtil.FAILURE_PAST_TICKET);
			session.removeAttribute(CommandUtil.FAILURE_ALL_TICKET);

			if (subject!=null) {

				session.setAttribute(CommandUtil.ISSUE_CUST_ID, c_id);

				session.setAttribute(CommandUtil.SUCCESS_ISSUE, issueDetails);
				
				session.removeAttribute(CommandUtil.ISSUE_HISTORY);
				session.removeAttribute(CommandUtil.SOLVED_ISSUE);

				session.removeAttribute(CommandUtil.FAILURE_PENDING_ISSUE);
				session.removeAttribute(CommandUtil.FAILURE_SOLVED_ISSUE);
				session.removeAttribute(CommandUtil.FAILURE_ALL_ISSUE);

				return CommandUtil.SUCCESS;


			}

			return CommandUtil.SUCCESS;

		}

		if(location==null){

			request.setAttribute(CommandUtil.FAILURE_FUTURE_TICKET, CommandUtil.FAILURE_FUTURE_TICKET_MSG);
			session.removeAttribute(CommandUtil.FAILURE_PAST_TICKET);
			session.removeAttribute(CommandUtil.FAILURE_ALL_TICKET);

			session.removeAttribute(CommandUtil.ALL_TICKET);
			session.removeAttribute(CommandUtil.PAST_TICKET);
			session.removeAttribute(CommandUtil.SUCCESS_TICKET);

			if(subject==null) {

				session.setAttribute(CommandUtil.FAILURE_PENDING_ISSUE, CommandUtil.FAILURE_PENDING_ISSUE_MSG);
				session.removeAttribute(CommandUtil.FAILURE_SOLVED_ISSUE);
				session.removeAttribute(CommandUtil.FAILURE_ALL_ISSUE);

				session.removeAttribute(CommandUtil.SUCCESS_ISSUE);
				session.removeAttribute(CommandUtil.SOLVED_ISSUE);
				session.removeAttribute(CommandUtil.ISSUE_HISTORY);

				return CommandUtil.FAILURE;

			}

			return CommandUtil.FAILURE;

		}
		return CommandUtil.FAILURE;

	}



	private List<IssueDetails> getIssueDetails(int custId, HttpServletRequest request) {

		List<IssueDetails>  details = new ArrayList<>();
		IssueDetails issueDetails = null;

		ResultSet rs=null;
		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {

			statement=connection.prepareStatement(SQLHolder.GETISSUEQRY);
			statement.setInt(1, custId);
			System.out.println(statement);
			rs=statement.executeQuery();
			while(rs.next()) {

				issueDetails=new IssueDetails();

				issueDetails.setIssueId(rs.getInt(CommandUtil.ISSUEID));
				issueDetails.setCustomerId(rs.getInt(CommandUtil.CUSTOMERID));
				issueDetails.setIssueDescription(rs.getString(CommandUtil.ISSUEDESC));
				issueDetails.setIssueSubject(rs.getString(CommandUtil.ISSUESUB));
				issueDetails.setDtStamp(rs.getTimestamp(CommandUtil.DTSTAMP));
				issueDetails.setStatus(rs.getString(CommandUtil.ISSUE_STATUS));

				details.add(issueDetails);
				System.out.println(details);
				connectionPool.returnPoolConnection(connection);

			}

		}catch(SQLException e){

		}

		return details;
	}

	private List<TicketDetails> getTicketDetails(int custId,
			HttpServletRequest request) {

		List<TicketDetails> details = new ArrayList<>();
		TicketDetails ticketDetails = null;

		ResultSet rs=null;
		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {

			statement=connection.prepareStatement(SQLHolder.GETTICKETQRY);
			statement.setInt(1, custId);
			System.out.println(statement);
			rs=statement.executeQuery();
			while(rs.next()) {


				ticketDetails = new TicketDetails();
				ticketDetails.setCustomerId(rs.getInt(CommandUtil.CUSTOMERID));
				ticketDetails.setTicketId(rs.getInt(CommandUtil.TICKETID));
				ticketDetails.setEventName(rs.getString(CommandUtil.EVENTNAME));
				ticketDetails.setEventDate(rs.getString(CommandUtil.EVENTDATE));
				ticketDetails.setEventTime(rs.getString(CommandUtil.EVENTTIME));
				ticketDetails.setLocation(rs.getString(CommandUtil.LOCATION));
				ticketDetails.setNoOfTicket(rs.getInt(CommandUtil.NOOFTICKET));
				ticketDetails.setCategory(rs.getString(CommandUtil.CATEGORY));
				ticketDetails.setPrice(rs.getDouble(CommandUtil.PRICE));
				ticketDetails.setTotalPrice(rs.getDouble(CommandUtil.TOTAL_PRICE));
				ticketDetails.setDtStamp(rs.getTimestamp(CommandUtil.DTSTAMP));
System.out.println(ticketDetails);
				details.add(ticketDetails);
				connectionPool.returnPoolConnection(connection);

			}

		}catch(SQLException e){


		}
		System.out.println("Ticket"+details);
		return details;
	}
}

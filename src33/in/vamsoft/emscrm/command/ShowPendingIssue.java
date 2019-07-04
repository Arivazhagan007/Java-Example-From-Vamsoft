package in.vamsoft.emscrm.command;

import in.vamsoft.emscrm.sql.ConnectionPool;
import in.vamsoft.emscrm.sql.SQLHolder;
import in.vamsoft.emscrm.utils.CommandUtil;
import in.vamsoft.emscrm.utils.IssueDetails;

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

public class ShowPendingIssue implements Command{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {


		HttpSession session=request.getSession();


		int custId=Integer.parseInt(request.getParameter(CommandUtil.CUST_ID));

		List<IssueDetails> issueDetails = getIssueDetails(custId,request);
		String subject = null;
		int c_id=0;
		for (IssueDetails issue : issueDetails) {

			subject = issue.getIssueSubject();
			c_id=issue.getCustomerId();
		}
		Object count = session.getAttribute(CommandUtil.SCUSTDETAILS);
		System.out.println(count);

		if (subject!=null) {

			session.setAttribute(CommandUtil.ISSUE_CUST_ID, c_id);

			session.setAttribute(CommandUtil.SUCCESS_ISSUE, issueDetails);

			session.removeAttribute(CommandUtil.ISSUE_HISTORY);
			session.removeAttribute(CommandUtil.SOLVED_ISSUE);

			session.removeAttribute(CommandUtil.FAILURE_PENDING_ISSUE);
			session.removeAttribute(CommandUtil.FAILURE_SOLVED_ISSUE);
			session.removeAttribute(CommandUtil.FAILURE_ALL_ISSUE);

			if(count!=null){

				return CommandUtil.CUSTSUCCESS;

			}else {

				return CommandUtil.EMPSUCCESS;

			}


		}else{

			request.setAttribute(CommandUtil.FAILURE_PENDING_ISSUE, CommandUtil.FAILURE_PENDING_ISSUE_MSG);
			session.removeAttribute(CommandUtil.FAILURE_SOLVED_ISSUE);
			session.removeAttribute(CommandUtil.FAILURE_ALL_ISSUE);

			session.removeAttribute(CommandUtil.SUCCESS_ISSUE);
			session.removeAttribute(CommandUtil.SOLVED_ISSUE);
			session.removeAttribute(CommandUtil.ISSUE_HISTORY);

			if(count!=null){

				return CommandUtil.CUSTFAILURE;

			}else {

				return CommandUtil.EMPFAILURE;

			}


		}
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

}

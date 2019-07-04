package in.vamsoft.emscrm.command;

import in.vamsoft.emscrm.utils.CommandUtil;
import in.vamsoft.emscrm.utils.TicketDetails;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class PaginationPastTicket implements Command{

	public static Logger logger;
	static{
		logger = Logger.getLogger(CommandUtil.MYLOGGER);	
	}


	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		HttpSession session=request.getSession();
		int pageno=1;
		pageno = Integer.parseInt(
				request.getParameter(CommandUtil.PAGENO));
		int rowsperpage = 5;

		int endidx = pageno * rowsperpage;
		int startidx = endidx - rowsperpage;

		List<TicketDetails> lst = (List<TicketDetails>)
				session.getAttribute(CommandUtil.PAST_TICKET);
		if (endidx >= lst.size()){
			endidx = lst.size();
		}

		List<TicketDetails> plist = lst.subList(startidx, endidx);
		request.setAttribute(CommandUtil.CURRENTPAGE, pageno);
		request.setAttribute(CommandUtil.SUBLIST, plist);
		if(plist!=null){
			return CommandUtil.SUCCESS;
		}
		request.setAttribute(CommandUtil.P_ERROR, CommandUtil.PEMP_ERROR);
		return CommandUtil.FAILURE;
	}
}

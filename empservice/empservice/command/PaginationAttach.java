package in.vamsoft.empservice.command;

import in.vamsoft.empservice.utils.Attach;
import in.vamsoft.empservice.utils.CommandUtil;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class PaginationAttach implements Command{

	public static Logger logger;
	static{
		logger = Logger.getLogger(CommandUtil.MYLOGGER);	
	}


	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		HttpSession session=request.getSession();
		int pageno =1;
		pageno = Integer.parseInt(
				request.getParameter(CommandUtil.PAGENO));
		int rowsperpage = 10;
		int endidx = pageno * rowsperpage;
		int startidx = endidx - rowsperpage;

		List<Attach> lst = (List<Attach>)
				session.getAttribute(CommandUtil.ATTACHDETAILS);
		if (endidx >= lst.size()){
			endidx = lst.size();
		}

		List<Attach> plist = lst.subList(startidx, endidx);

		request.setAttribute(CommandUtil.SASUBLIST, plist);
		session.setAttribute(CommandUtil.CURRENTPAGE, pageno);
		if(plist!=null){
			return CommandUtil.SUCCESS;
		}
		request.setAttribute(CommandUtil.P_ERROR, CommandUtil.PEMP_ERROR);
		return CommandUtil.FAILURE;
	}
}

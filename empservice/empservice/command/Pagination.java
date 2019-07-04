package in.vamsoft.empservice.command;

import in.vamsoft.empservice.utils.CommandUtil;
import in.vamsoft.empservice.utils.Employee;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class Pagination implements Command{

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

		List<Employee> lst = (List<Employee>)
				session.getAttribute(CommandUtil.ALLDETAILS);
		if (endidx >= lst.size()){
			endidx = lst.size();
		}

		List<Employee> plist = lst.subList(startidx, endidx);
		session.setAttribute(CommandUtil.CURRENTPAGE, pageno);
		request.setAttribute(CommandUtil.SUBLIST, plist);
		if(plist!=null){
			return CommandUtil.SUCCESS;
		}
		request.setAttribute(CommandUtil.P_ERROR, CommandUtil.PEMP_ERROR);
		return CommandUtil.FAILURE;
	}
}

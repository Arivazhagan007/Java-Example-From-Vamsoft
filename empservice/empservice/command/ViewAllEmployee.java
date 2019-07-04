package in.vamsoft.empservice.command;

import in.vamsoft.empservice.sql.ConnectionPool;
import in.vamsoft.empservice.sql.SQLHolder;
import in.vamsoft.empservice.utils.CommandUtil;
import in.vamsoft.empservice.utils.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class ViewAllEmployee implements Command{

	public static Logger logger;
	static{
		logger = Logger.getLogger(CommandUtil.MYLOGGER);	
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		HttpSession session=request.getSession();

		List<Employee> lst=viewAllEmpDetails(request);
		session.setAttribute(CommandUtil.ALLDETAILS, lst);
		List<Employee> plist = lst.subList(0,5);

		request.setAttribute(CommandUtil.SUBLIST, plist);

		int size = lst.size();
		int pages = (int)Math.ceil(size/5.0);
		session.setAttribute(CommandUtil.PAGES, pages);
		if(lst!=null){
			return CommandUtil.SUCCESS;
		}else{
			request.setAttribute(CommandUtil.VAE_ERROR, CommandUtil.VAEMP_ERROR);
			return CommandUtil.FAILURE;
		}

	}


	private List<Employee> viewAllEmpDetails(HttpServletRequest request) {
		List<Employee> lst=new ArrayList<Employee>();
		Employee emp=null;
		ResultSet rs=null;
		PreparedStatement statement=null;
		int empId=0;
		String empName,emailId,phNo,pwd,designation,role,status=null;
		Date dol,doj=null;
		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {
			statement=connection.prepareStatement(SQLHolder.VIEWALLEMPQUERY);
			logger.debug(statement);
			rs=statement.executeQuery();
			while(rs.next())
			{
				emp=new Employee();
				empId=rs.getInt(CommandUtil.EMPID);
				empName=rs.getString(CommandUtil.EMPNAME);
				phNo=rs.getString(CommandUtil.PHNO);
				emailId=rs.getString(CommandUtil.EMAILID);
				pwd=rs.getString(CommandUtil.PASSWORD);
				role=rs.getString(CommandUtil.ROLE);
				designation=rs.getString(CommandUtil.DESIGNATION);
				doj=rs.getDate(CommandUtil.DOJ);
				dol=rs.getDate(CommandUtil.DOL);
				status=rs.getString(CommandUtil.STATUS);
				emp=new Employee(empId, empName, phNo, emailId, pwd, designation, role, doj, dol, status);
				System.out.println(emp);
				lst.add(emp);
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
		return lst;
	}
}
